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
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;

public class Party implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config;
	
    public Party() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    	this.configManager = Main.access.mainConfig;
    	try {
			config = configManager.load();
		} catch (IOException e) {}
    }
	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		String passed = args.getOne("Amount").get().toString();
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
		return CommandResult.empty();
	}
}