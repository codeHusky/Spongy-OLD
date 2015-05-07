package com.terminalbit.spongy;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
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
import org.spongepowered.api.util.command.spec.CommandSpec;
import static org.spongepowered.api.util.command.args.GenericArguments.*;

import com.google.inject.Inject;
import com.terminalbit.spongy.command.Broadcast;
import com.terminalbit.spongy.command.Spawn;
import com.terminalbit.spongy.command.Warp;
import com.terminalbit.spongy.command.actAsConsole;
import com.terminalbit.spongy.command.reloadConfig;
import com.terminalbit.spongy.command.setSpawn;
import com.terminalbit.spongy.command.setWarp;
import com.terminalbit.spongy.command.simpleTP;
import com.terminalbit.spongy.command.simpleTPHERE;

@Plugin(id = "Spongy", name="Spongy", version="0.3")
public class Main {	
	@Inject
	public Logger logger;
	
	@Inject
	public Game game;
	
	public static Main access;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	public ConfigurationLoader<CommentedConfigurationNode> configManager;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private File defaultConfig;
	
	private boolean useJoinSound;
	public ConfigurationNode config = null;
	@Subscribe
	public void PreInitialization(PreInitializationEvent event){
		Plugin plugin = Main.class.getAnnotation(Plugin.class);
		logger.info("Spongy v" + plugin.version() + " is starting...");
		try {
		     if (!defaultConfig.exists()) {
		    	defaultConfig.getParentFile().mkdirs();
		        defaultConfig.createNewFile();
		        config = configManager.load();
		        config.getNode("version").setValue(plugin.version());
				config.getNode("playJoinSound").setValue(false);
		        configManager.save(config);
		    }
		    config = configManager.load();
		    config.getNode("version").setValue(plugin.version());
		    useJoinSound = config.getNode("playJoinSound").getBoolean();
		    configManager.save(config);

		} catch (IOException exception) {
		    exception.printStackTrace();
		}
		access = this;
	}
	
	@Subscribe
	public void Initalization(InitializationEvent event){
		CommandService cmdService = game.getCommandDispatcher();
		CommandSpec broadcastSpec = CommandSpec.builder()
				.setDescription(Texts.of("Broadcast Desc"))
				//.setPermission("spongy.chat.broadcast") <-- Let's not... :)
				.setArguments(optional(remainingJoinedStrings(Texts.of("all"))))
				.setExecutor(new Broadcast())
				.build();
		cmdService.register(this, broadcastSpec, "broadcast");
		cmdService.register(this, new actAsConsole(logger, game), "asconsole");
		cmdService.register(this, new simpleTP(logger, game), "tp");
		cmdService.register(this, new simpleTPHERE(logger, game), "tphere");	
		cmdService.register(this, new setWarp(logger, game,configManager), "setwarp");	
		cmdService.register(this, new Warp(logger, game,configManager), "warp");	
		cmdService.register(this, new reloadConfig(logger, game,configManager), "reloadSpongyConfig");
		cmdService.register(this, new Spawn(logger, game,configManager), "spawn");
		cmdService.register(this, new setSpawn(logger, game,configManager), "setspawn");
	}
	
	@Subscribe
	public void onServerStart(ServerStartedEvent event){
		/*try {
			config = configManager.load();
			if(config.getNode("spawn").isVirtual()){
				logger.info("Assigning spawn location.");
				logger.info("Assuming default world is called \"world\" to work around an unimplemented method.")
				World world = game.getServer().getWorld("world").get();
				Location worldSpawn = world.getSpawnLocation();
				config.getNode("spawn","position","X").setValue(worldSpawn.getX());
				config.getNode("spawn","position","Y").setValue(worldSpawn.getY());
				config.getNode("spawn","position","Z").setValue(worldSpawn.getZ());
			}
			configManager.save(config);
		} catch (IOException e) {
			logger.error("Failed to set spawn location. ;( I cri.");
		}*/
		logger.info("Spongy has started.");
		logger.info("-------------------------------------");
		logger.info("Spongy was created by Lokio27.");
		logger.info("Additional help from Xemiru and");
		logger.info("other great people in the");
		logger.info("Sponge community.");
		logger.info("Please post all crashes and/or bugs");
		logger.info("to the Github Repo.");
		logger.info("-------------------------------------");
		logger.info(game.getPlatform().toString());
	}
	@Subscribe
	public void onPlayerConnection(PlayerJoinEvent event) {
		for(Player player : game.getServer().getOnlinePlayers()){
			//Waiting for implementation...
			//player.playSound(SoundTypes.ORB_PICKUP,player.getLocation().getPosition(),1);
			if(useJoinSound){
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				game.getCommandDispatcher().process(game.getServer().getConsole(), "playsound random.orb " + player.getName() + " " + x + " " + y + " " + z + " 0.5");
			}
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
