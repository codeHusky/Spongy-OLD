package com.terminalbit.spongy.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

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

public class reloadConfig implements CommandCallable {
	private Logger logger;
	//private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	//private ConfigurationNode config = null;
	
    public reloadConfig(Logger logger, Game game, ConfigurationLoader<CommentedConfigurationNode> configManager) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	//this.game = game;
    	this.configManager = configManager;
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
		Boolean didFail = false;
		try {
			configManager.load();
		} catch (IOException e) {
			didFail = true;
		}
		if(didFail){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "The config failed to reload."));
		}else{
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ", TextColors.YELLOW, "The config was reloaded."));
		}
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource arg0) {
		logger.info("testPermission");
		//I guess if it needs, then return true.
		return true;
	}
}