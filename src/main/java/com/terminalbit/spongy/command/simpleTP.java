package com.terminalbit.spongy.command;


import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
//import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;

import com.terminalbit.spongy.Main;
import com.terminalbit.spongy.util.GeneralUtils;
import com.flowpowered.math.vector.Vector3d;

public class simpleTP implements CommandExecutor {
	private Logger logger = Main.access.logger;
	private Game game = Main.access.game;

	public CommandResult execute(CommandSource cS, CommandContext args){
		Server server = game.getServer();
		Player caller = server.getPlayer(cS.getName()).get();
		Player passedSTEP = (Player) args.getOne("Player").get();
		String passed = passedSTEP.getName();
		Location destination = null;
		if(passed.length() < 1){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Format: /tp <username>"));
		}else if(!server.getPlayer(passed).isPresent()){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Player \"" + passed + "\" does not exist."));
		}else{
			logger.info(cS.getName() + " teleported to " + passed);
			destination = server.getPlayer(passed).get().getLocation();
			Vector3d desRot = server.getPlayer(passed).get().getRotation();
			GeneralUtils.TeleAndRotate(caller,destination,desRot);
		}	
		return CommandResult.empty();
	}
}