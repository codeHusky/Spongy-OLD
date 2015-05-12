package com.terminalbit.spongy.command;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
//import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;

import com.terminalbit.spongy.util.GeneralUtils;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;

public class simpleTP implements CommandCallable {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	
    public simpleTP(Logger logger, Game game) {
    	//Gets the, you know, stuff from the main class.
    	this.logger = logger;
    	this.game = game;
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
		Server server = game.getServer();
		Player caller = server.getPlayer(cS.getName()).get();
		Location destination = null;
		if(passed.length() < 1){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Format: /tp <username>"));
		}else if(!server.getPlayer(passed).isPresent()){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Player \"" + passed + "\" does not exist."));
		}else{
			logger.info(cS.getName() + " teleported to " + passed);
			//destination.getWorld().getBlock(destination.getLocation().getBlock());
			destination = server.getPlayer(passed).get().getLocation();
			Vector3d desRot = server.getPlayer(passed).get().getRotation();
			//caller.setLocation(destination.getLocation());
			GeneralUtils.TeleAndRotate(caller,destination,desRot);
		}	
		return Optional.of(CommandResult.empty());
	}

	public boolean testPermission(CommandSource cS) {
		return cS.hasPermission("Spongy.tp");
	}
}