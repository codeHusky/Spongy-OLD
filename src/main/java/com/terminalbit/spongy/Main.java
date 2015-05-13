package com.terminalbit.spongy;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tile.Sign;
import org.spongepowered.api.data.manipulators.DisplayNameData;
import org.spongepowered.api.data.manipulators.tileentities.SignData;
//import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.BlockPlaceEvent;
import org.spongepowered.api.event.block.tile.SignChangeEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;

import static org.spongepowered.api.util.command.args.GenericArguments.*;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;
import com.terminalbit.spongy.command.Broadcast;
import com.terminalbit.spongy.command.Me;
import com.terminalbit.spongy.command.Nick;
import com.terminalbit.spongy.command.Party;
import com.terminalbit.spongy.command.Spawn;
import com.terminalbit.spongy.command.Warp;
import com.terminalbit.spongy.command.actAsConsole;
import com.terminalbit.spongy.command.reloadConfig;
import com.terminalbit.spongy.command.setSpawn;
import com.terminalbit.spongy.command.setWarp;
import com.terminalbit.spongy.command.simpleTP;
import com.terminalbit.spongy.command.simpleTPHERE;

@Plugin(id = "Spongy", name = "Spongy", version = "0.4")
public class Main {
	@Inject
	public Logger logger;

	@Inject
	public Game game;

	public static Main access;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private File configDir;
	
	//private File mainConfigFile = new File(this.configDir, "config.conf");
	//private File userConfigFile = new File(this.configDir, "user.conf");
	
	public ConfigurationLoader<CommentedConfigurationNode> mainConfig = null;
	public ConfigurationLoader<CommentedConfigurationNode> userConfig = null;


	private boolean useJoinSound;
	public ConfigurationNode config = null;

	public ConfigurationLoader<CommentedConfigurationNode> getLoader(String configName) throws IOException {
	    File configFile = new File(this.configDir, configName);
	    if(!configFile.exists()){
	    	configFile.getParentFile().mkdirs();
	    	configFile.createNewFile();
	    }
	    return HoconConfigurationLoader.builder().setFile(configFile).build();
	}
	
	@Subscribe
	public void PreInitialization(PreInitializationEvent event) {
		try {
			mainConfig = this.getLoader("config.conf");
			userConfig = this.getLoader("userdata.conf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Plugin plugin = Main.class.getAnnotation(Plugin.class);
		logger.info("Spongy v" + plugin.version() + " is starting...");
		try {
			config = mainConfig.load();
			config.getNode("version").setValue(plugin.version());
			if(config.getNode("playJoinSound").isVirtual()){
				config.getNode("playJoinSound").setValue(false);
			}
			if(config.getNode("enableParty").isVirtual()){
				config.getNode("enableParty").setValue(false);
			}
			useJoinSound = config.getNode("playJoinSound").getBoolean();
			mainConfig.save(config);

		} catch (IOException exception) {
			exception.printStackTrace();
		}
		access = this;
	}

	@Subscribe
	public void Initalization(InitializationEvent event) {
		CommandService cmdService = game.getCommandDispatcher();
		CommandSpec broadcastSpec = CommandSpec
				.builder()
				.setDescription(Texts.of("Broadcast Desc"))
				// .setPermission("spongy.chat.broadcast") <-- Let's not... :)
				.setArguments(optional(remainingJoinedStrings(Texts.of("all"))))
				.setExecutor(new Broadcast()).build();
		cmdService.register(this, broadcastSpec, "broadcast");
		cmdService.register(this, new actAsConsole(logger, game), "asconsole");
		cmdService.register(this, new simpleTP(logger, game), "tp");
		cmdService.register(this, new simpleTPHERE(logger, game), "tphere");
		cmdService.register(this, new setWarp(logger, game, mainConfig),
				"setwarp");
		cmdService
				.register(this, new Warp(logger, game, mainConfig), "warp");
		cmdService.register(this,
				new reloadConfig(logger, game, mainConfig),
				"reloadSpongyConfig");
		cmdService.register(this, new Spawn(logger, game, mainConfig),
				"spawn");
		cmdService.register(this, new setSpawn(logger, game, mainConfig),
				"setspawn");
		cmdService.register(this, new Nick(logger, game, userConfig),
				"nick");
		cmdService.register(this, new Me(logger, game, userConfig),
				"me");
		cmdService.register(this, new Party(logger, game, mainConfig),
				"party");
	}

	@Subscribe
	public void onServerStart(ServerStartedEvent event) {
		/*
		 * try { config = configManager.load();
		 * if(config.getNode("spawn").isVirtual()){
		 * logger.info("Assigning spawn location."); logger.info(
		 * "Assuming default world is called \"world\" to work around an unimplemented method."
		 * ) World world = game.getServer().getWorld("world").get(); Location
		 * worldSpawn = world.getSpawnLocation();
		 * config.getNode("spawn","position","X").setValue(worldSpawn.getX());
		 * config.getNode("spawn","position","Y").setValue(worldSpawn.getY());
		 * config.getNode("spawn","position","Z").setValue(worldSpawn.getZ()); }
		 * configManager.save(config); } catch (IOException e) {
		 * logger.error("Failed to set spawn location. ;( I cri."); }
		 */
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
		for (Player player : game.getServer().getOnlinePlayers()) {
			// Waiting for implementation...
			// player.playSound(SoundTypes.ORB_PICKUP,player.getLocation().getPosition(),1);
			if (useJoinSound) {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				game.getCommandDispatcher().process(
						game.getServer().getConsole(),
						"playsound random.orb " + player.getName() + " " + x
								+ " " + y + " " + z + " 0.5");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Subscribe(order = Order.LAST)
	public void onMessage(MessageEvent event) {
		// Replace all colorcodes with actual ones
		String original = Texts.toLegacy(event.getMessage(),'&');
		ConfigurationNode thisConfig = null;
		try {
			thisConfig = userConfig.load();
		} catch (IOException e) {
			logger.error("Failed to load userConfig");
		}
		try{
			//temporary nickname implementation
			//sloppy as crap.
			//also need a config entry on how people want this to be formatted.
			String username = thisConfig.getNode(event.getSource().getIdentifier(),"nickname").getString();
			String edited = original.replace(event.getSource().getName(), username);
			original = edited;	
		}catch(NullPointerException e){
		}
		try {
			userConfig.save(thisConfig);
		} catch (IOException e) {
		}
		original = "&r"  + original;
		//logger.info("In /config/Spongy/config.conf, please change \"imcom");
		event.setMessage(Texts.of(Texts.fromLegacy(original, '&')));
	}
	
	/*@SuppressWarnings("deprecation")
	@Subscribe
	public void onSignEdit(SignChangeEvent event){
		logger.info("Sign Edited!");
		//le epic sause.
		SignData BlockLoco = event.getSign().getSignData();
		String line0 = BlockLoco.getLine(0).toString();
		String line1 = BlockLoco.getLine(1).toString();
		String line2 = BlockLoco.getLine(2).toString();
		String line3 = BlockLoco.getLine(3).toString();
		BlockLoco.setLine(0, Texts.fromLegacy(line0,'&'));
		BlockLoco.setLine(0, Texts.fromLegacy(line1,'&'));
		BlockLoco.setLine(0, Texts.fromLegacy(line2,'&'));
		BlockLoco.setLine(0, Texts.fromLegacy(line3,'&'));
		event.getSign().offer(BlockLoco);
	}
	@SuppressWarnings("deprecation")
	@Subscribe
	public void onBlockPlace(BlockPlaceEvent event){
		event.setCancelled(true);
	}*/
}
