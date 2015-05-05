package com.terminalbit.spongy.command;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class actAsConsole implements CommandCallable {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	
    public actAsConsole(Logger logger, Game game) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	this.game = game;
    }

    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return Collections.emptyList();
    }

	public Optional<Text> getHelp(CommandSource arg0) {
		//Not sure what this is ;)
		logger.info("getHelp");
		return help;
	}

	public Optional<Text> getShortDescription(CommandSource arg0) {
		//This too. :)
		logger.info("getShortDescription");
		return desc;
	}

	public Text getUsage(CommandSource arg0) {
		//This is probably for the help. :P
		logger.info("getUsage");
		return usage;
	}

	public Optional<CommandResult> process(CommandSource cS, String passed){
		logger.info(cS.getName() + " called " + passed + " as the console.");
		cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ", TextColors.YELLOW, "You called \"" + passed + "\" as the console."));
		game.getCommandDispatcher().process(game.getServer().getConsole(), passed);
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource cS) {
		return cS.hasPermission("Spongy.actAsConsole");
	}
}