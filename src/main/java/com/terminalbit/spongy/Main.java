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




import com.google.inject.Inject;
import com.terminalbit.spongy.command.Broadcast;
import com.terminalbit.spongy.command.actAsConsole;
import com.terminalbit.spongy.command.simpleTP;

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
		cmdService.register(this, new Broadcast(logger, game), "broadcast");
		cmdService.register(this, new actAsConsole(logger, game), "asConsole");
		cmdService.register(this, new simpleTP(logger, game), "tp");
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
		String username = event.getPlayer().getName();
		//testing some stuph
		//event.setJoinMessage(Texts.of(TextColors.GOLD,username + " joined DE GAME!"));
		logger.info(username + " joined ;)");
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
