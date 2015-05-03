package com.terminalbit.spongy;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

@Plugin(id = "Spongy", name="Spongy", version="0.1.2")
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
		logger.info("other great people in the");
		logger.info("Sponge community.");
		logger.info("Please post all crashes and/or bugs");
		logger.info("to the Github Repo.");
		logger.info("-------------------------------------");
	}
	@Subscribe
	public void onPlayerConnection(PlayerJoinEvent event) {
		//String username = event.getPlayer().getName();
		//logger.info("Player " + username + " joined.");
		//game.getServer().broadcastMessage(Texts.of("/broadcast TEST1 TEST2"));
		//event.getPlayer().sendMessage(Texts.of(TextColors.GOLD,""));
	}
	@SuppressWarnings("deprecation")
	@Subscribe
	public void onPlayerMessage(MessageEvent event) {
		//Replace all colorcodes with actual ones
		String original = Texts.toPlain(event.getMessage());
		event.setMessage(Texts.of(Texts.replaceCodes(original,'&')));
	}
}
