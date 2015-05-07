package com.terminalbit.spongy.command;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.terminalbit.spongy.Main;


public class Broadcast implements CommandExecutor  {
	private Game game;
	
	public CommandResult execute(CommandSource cS, CommandContext args) throws CommandException{
		game = Main.access.game;
		if(cS.hasPermission("spongy.chat.broadcast")){
			String broadcastContent = args.getOne("content").toString();
			String broadcaster = args.getOne("broadcaster").toString();
			cS.sendMessage(Texts.of(TextColors.GOLD,"You Broadcasted \"",TextColors.RESET, broadcastContent, TextColors.GOLD,"\""));
			game.getServer().broadcastMessage(Texts.of("§4" + broadcaster + " §r" + broadcastContent));
			return CommandResult.success();
		}else{
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ",TextColors.RED, "You do not have permission to use /broadcast"));
		}
		return CommandResult.empty();
	}
}