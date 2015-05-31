package com.terminalbit.spongy.command;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;
import com.terminalbit.spongy.util.GeneralUtils;

public class simpleTPHERE implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	
    public simpleTPHERE() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    }
	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		Server server = game.getServer();
		Player caller = server.getPlayer(cS.getName()).get();
		Player destination = (Player) args.getOne("Player").get();
		//if(passed.length() < 1){
		//	cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Format: /tphere <username>"));
		//}else if(!server.getPlayer(passed).isPresent()){
		//	cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Player \"" + passed + "\" does not exist."));
		//}else{
			logger.info(cS.getName() + " brought " + destination.getName() + " to themself.");
			GeneralUtils.TeleAndRotate(destination,caller.getLocation(),caller.getRotation());
		//}		
		return CommandResult.empty();
	}
}