package com.terminalbit.spongy;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.message.MessageEvent;
//import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.text.Texts;

import com.google.inject.Inject;

@Plugin(id = "Spongy", name="Spongy", version="0.1")
public class Main {
	@Inject
	private Logger logger;
	
	@Inject
	private Game game;
	
	@Subscribe
	public void onServerStart(ServerStartedEvent event){
		Plugin plugin = Main.class.getAnnotation(Plugin.class);
		logger.info("Spongy v" + plugin.version() + " is starting...");
		CommandService cmdService = game.getCommandDispatcher();
		cmdService.register(this, new BroadcastCMD(logger, game), "message", "broadcast");
		logger.info("Spongy has started.");
		logger.info("-------------------------------------");
		logger.info("Spongy was created by Lokio27.");
		logger.info("Additional help from Xemiru and");
		logger.info("other great people on the");
		logger.info("IRC channel.");
		logger.info("Please post all crashes and/or bugs");
		logger.info("to the forum post.");
		logger.info("-------------------------------------");
	}
	//@Subscribe
	//public void onPlayerConnection(PlayerJoinEvent event) {
		//String username = event.getPlayer().getName();
		/*logger.info("Player " + username + " joined.");
		game.getServer().broadcastMessage(Texts.of("/broadcast TEST1 TEST2"));*/
	//}
	@Subscribe
	public void onPlayerMessage(MessageEvent event) {
		//Replace all colorcodes with actual ones
		//TODO: Make a better mechanism for this
		String original = Texts.toPlain(event.getMessage());
		original = original.replaceAll("&4","§4");
		original = original.replaceAll("&c","§c");
		original = original.replaceAll("&6","§6");
		original = original.replaceAll("&e","§e");
		original = original.replaceAll("&2","§2");
		original = original.replaceAll("&a","§a");
		original = original.replaceAll("&b","§b");
		original = original.replaceAll("&3","§3");
		original = original.replaceAll("&1","§1");
		original = original.replaceAll("&9","§9");
		original = original.replaceAll("&d","§d");
		original = original.replaceAll("&8","§8");
		original = original.replaceAll("&5","§5");
		original = original.replaceAll("&f","§f");
		original = original.replaceAll("&7","§7");
		original = original.replaceAll("&l","§l");
		original = original.replaceAll("&n","§n");
		original = original.replaceAll("&o","§o");
		original = original.replaceAll("&k","§k");
		original = original.replaceAll("&m","§m");
		original = original.replaceAll("&r","§r");
		event.setMessage(Texts.of(original));
	}
}
