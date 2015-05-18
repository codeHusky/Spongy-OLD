package com.terminalbit.spongy.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;

public class Party implements CommandCallable {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config;
	
    public Party(Logger logger, Game game, ConfigurationLoader<CommentedConfigurationNode> userConfig) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	this.game = game;
    	this.configManager = userConfig;
    	try {
			config = configManager.load();
		} catch (IOException e) {}
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
		//do stuff here when command is fired
		try {
			config = configManager.load();
		} catch (IOException e) {}
		if(config.getNode("enableParty").getBoolean()){
		int bombCount = 10;
		if(Integer.toString(Integer.parseInt(passed)).equals(passed)){
			bombCount = Integer.parseInt(passed);
		}
		Player caller = game.getServer().getPlayer(cS.getName()).get();
		World playerWorld = caller.getWorld();
		Location entityLocation = caller.getLocation();
		for(int i = 0; i < bombCount; i++){
		Optional<Entity> entity = playerWorld.createEntity(EntityTypes.PRIMED_TNT, new Vector3d(entityLocation.getX(),entityLocation.getY(),entityLocation.getZ()));
		if(entity.isPresent()){
			PrimedTNT tnt = (PrimedTNT) entity.get();
			//FuseData fuse = tnt.getFuseData();
			//fuse.setFuseDuration(40);
			//tnt.offer(fuse);
			playerWorld.spawnEntity(tnt);
			
		}
		}
		cS.sendMessage(Texts.of("KaBewhm! " + passed + " tnt spawned."));
		}else{
			cS.sendMessage(Texts.of("Party is disabled. Enable it in /config/Spongy/config.conf. Please note that /party is a sloppy plugin that was made for fun and isn't very well implemented."));
		}
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource arg0) {
		logger.info("testPermission");
		//I guess if it needs, then return true.
		return true;
	}
}