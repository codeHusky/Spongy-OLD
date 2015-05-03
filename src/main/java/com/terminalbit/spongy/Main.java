package com.terminalbit.spongy;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
//import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;

import com.google.inject.Inject;
import com.terminalbit.spongy.command.Broadcast;
import com.terminalbit.spongy.command.actAsConsole;
import com.terminalbit.spongy.command.simpleTP;
import com.terminalbit.spongy.command.simpleTPHERE;

@Plugin(id = "Spongy", name="Spongy", version="0.1.2")
public class Main {
	@Inject
	private Logger logger;
	
	@Inject
	private Game game;
	
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private File defaultConfig;
	
	@Subscribe
	public void PreInitialization(PreInitializationEvent event){
		Plugin plugin = Main.class.getAnnotation(Plugin.class);
		logger.info("Spongy v" + plugin.version() + " is starting...");
		ConfigurationNode config = null;

		try {
		     if (!defaultConfig.exists()) {
		        defaultConfig.createNewFile();
		        config = configManager.load();

		        config.getNode("version").setValue(1);
		        config.getNode("doStuff").setValue(true);
		        config.getNode("doMoreStuff").setValue(false);
		        configManager.save(config);
		    }
		    config = configManager.load();

		} catch (IOException exception) {
		    exception.printStackTrace();
		}
	}
	
	@Subscribe
	public void Initalization(InitializationEvent event){
		CommandService cmdService = game.getCommandDispatcher();
		cmdService.register(this, new Broadcast(logger, game), "broadcast");
		cmdService.register(this, new actAsConsole(logger, game), "asConsole");
		cmdService.register(this, new simpleTP(logger, game), "tp");
		cmdService.register(this, new simpleTPHERE(logger, game), "tphere");	
	}
	
	@Subscribe
	public void onServerStart(ServerStartedEvent event){
		logger.info("Spongy has started.");
		logger.info("-------------------------------------");
		logger.info("Spongy was created by Lokio27.");
		logger.info("Additional help from Xemiru and");
		logger.info("other great people in the");
		logger.info("Sponge community.");
		logger.info("Please post all crashes and/or bugs");
		logger.info("to the Github Repo.");
		logger.info("-------------------------------------");
	}
	@Subscribe
	public void onPlayerConnection(PlayerJoinEvent event) {
		for(Player player : game.getServer().getOnlinePlayers()){
			//Waiting for implementation...
			//player.playSound(SoundTypes.ORB_PICKUP,player.getLocation().getPosition(),1);
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			game.getCommandDispatcher().process(game.getServer().getConsole(), "playsound random.orb " + player.getName() + " " + x + " " + y + " " + z + " 0.5");
		}
	}
	@SuppressWarnings("deprecation")
	@Subscribe
	public void onMessage(MessageEvent event) {
		//Replace all colorcodes with actual ones
		String original = Texts.toPlain(event.getMessage());
		//TODO: Replace with a better method of parsing.
		event.setMessage(Texts.of(Texts.replaceCodes(original,'&')));
	}
}
