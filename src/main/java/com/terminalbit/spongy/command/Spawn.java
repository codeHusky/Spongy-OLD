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
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.base.Optional;

public class Spawn implements CommandCallable {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config = null;
	
    public Spawn(Logger logger, Game game, ConfigurationLoader<CommentedConfigurationNode> configManager) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	this.game = game;
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
		try{
			World playerWorld = game.getServer().getPlayer(cS.getName()).get().getWorld();
			config = configManager.load();
			if(config.getNode("spawn").isVirtual()){
				cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ",TextColors.RED, "There is no spawn set. Please ask a server administrator to set one."));
				configManager.save(config);
				return Optional.of(CommandResult.empty());
			}
			double x = config.getNode("spawn","position","X").getFloat();
			double y = config.getNode("spawn","position","Y").getFloat();
			double z = config.getNode("spawn","position","Z").getFloat();
			Location spawn = new Location(playerWorld,x,y,z);
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ",TextColors.YELLOW,"Sending you to the spawn."));
			game.getServer().getPlayer(cS.getName()).get().setLocation(spawn);
			configManager.save(config);
		}catch(IOException e){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ",TextColors.RED, "An error occured."));
		}
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource arg0) {
		logger.info("testPermission");
		//I guess if it needs, then return true.
		return true;
	}
}