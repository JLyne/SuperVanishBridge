package uk.co.notnull.supervanishbridge.helper;

import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;

@SuppressWarnings("unused")
public class CloudHelper {
	public static void registerVisiblePlayerArgument(CommandManager<CommandSource> commandManager) {
		commandManager.parserRegistry().registerNamedParserSupplier("visibleplayer",
																	options -> new VisiblePlayerParser<>());

		commandManager.parserRegistry().registerSuggestionProvider("visibleplayers", (
        		CommandContext<CommandSource> commandContext,
                String input
        ) -> SuperVanishBridgeHelper.getInstance().getUsernameSuggestions(input, commandContext.getSender()));
	}
}
