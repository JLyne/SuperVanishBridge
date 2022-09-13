//
// MIT License
//
// Copyright (c) 2021 Alexander Söderberg & Contributors
// Copyright (c) 2022 James Lyne
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package uk.co.notnull.supervanishbridge.helper;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.captions.CaptionVariable;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import cloud.commandframework.exceptions.parsing.ParserException;
import cloud.commandframework.velocity.VelocityCaptionKeys;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Argument parser for {@link Player players} which can be seen by the command source
 *
 * @param <C> Command sender type
 * @since 1.1.0
 */
public final class VisiblePlayerParser<C> implements ArgumentParser<C, Player> {
        @Override
        public @NonNull ArgumentParseResult<@NonNull Player> parse(
                final @NonNull CommandContext<@NonNull C> commandContext,
                final @NonNull Queue<@NonNull String> inputQueue
        ) {
            final String input = inputQueue.peek();

            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(
                        VisiblePlayerParser.class,
                        commandContext
                ));
            }

            final Player player = commandContext.<ProxyServer>get("ProxyServer").getPlayer(input).orElse(null);

            if (player == null || (commandContext.getSender() instanceof Player
                    && !SuperVanishBridgeHelper.getInstance().canSee((Player) commandContext.getSender(), player))) {
                return ArgumentParseResult.failure(new PlayerParseException(input, commandContext));
            }

            inputQueue.remove();
            return ArgumentParseResult.success(player);
        }

    @Override
    public @NonNull List<@NonNull String> suggestions(
            final @NonNull CommandContext<C> commandContext,
            final @NonNull String input
    ) {
        if (commandContext.getSender() instanceof CommandSource) {
            return SuperVanishBridgeHelper.getInstance()
                    .getUsernameSuggestions(input, (CommandSource) commandContext.getSender());
        } else {
            return commandContext.<ProxyServer>get("ProxyServer").getAllPlayers()
                    .stream().map(Player::getUsername).collect(Collectors.toList());
        }
    }

    @Override
    public boolean isContextFree() {
        return true;
    }

    public static final class PlayerParseException extends ParserException {
        private PlayerParseException(
                final @NonNull String input,
                final @NonNull CommandContext<?> context
        ) {
            super(
                    VisiblePlayerParser.class,
                    context,
                    VelocityCaptionKeys.ARGUMENT_PARSE_FAILURE_PLAYER,
                    CaptionVariable.of("input", input)
            );
        }
    }
}
