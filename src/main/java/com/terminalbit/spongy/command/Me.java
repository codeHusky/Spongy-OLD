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
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;

public class Me implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> userConfig;
	private ConfigurationNode config;
    public Me() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    	this.userConfig = Main.access.userConfig;
    }

	@SuppressWarnings("deprecation")
	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		String passed = args.getOne("Action").get().toString();
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
		
		return CommandResult.empty();
	}
}