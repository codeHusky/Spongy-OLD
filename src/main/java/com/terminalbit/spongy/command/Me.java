package com.terminalbit.spongy.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class Me implements CommandCallable {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> userConfig;
	private ConfigurationNode config;
    public Me(Logger logger, Game game, ConfigurationLoader<CommentedConfigurationNode> userConfig) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	this.game = game;
    	this.userConfig = userConfig;
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

	@SuppressWarnings("deprecation")
	public Optional<CommandResult> process(CommandSource cS, String passed){
		try {
			config = userConfig.load();
			if(passed.length() == 0){
				cS.sendMessage(Texts.of("Usage: /me <content>"));
			}else {
				String nickname = config.getNode(cS.getIdentifier(),"nickname").getString();
				if(nickname.equals("null")){
					nickname = cS.getName();
				}
				game.getServer().broadcastMessage(Texts.of(Texts.fromLegacy("&5* " + nickname + "&5 " + passed, '&')));
			}
			userConfig.save(config);
		} catch (IOException e) {
		}
		
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource arg0) {
		logger.info("testPermission");
		//I guess if it needs, then return true.
		return true;
	}
}