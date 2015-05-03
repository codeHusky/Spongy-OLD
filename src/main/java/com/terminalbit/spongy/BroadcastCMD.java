package com.terminalbit.spongy;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;
import com.google.inject.Inject;

public class BroadcastCMD implements CommandCallable {
	@Inject
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	
    public BroadcastCMD(Logger logger, Game game) {
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
		int spaceCount = passed.split(" ").length-1;
		if(spaceCount > 0){
			String channel = passed.split(" ")[0];
			String content = passed.replace(channel + " ","");
			cS.sendMessage(Texts.of(TextColors.GOLD,"You Broadcasted \"",TextColors.RESET, content, TextColors.GOLD,"\""));
			game.getServer().broadcastMessage(Texts.of("§4" + channel + " §r" + content));
			return Optional.of(CommandResult.success());
		}else if(passed.replaceAll("\\s+","").length() == 0){
			cS.sendMessage(Texts.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "Format: /broadcast <channel name> <content>"));
			return Optional.of(CommandResult.empty());
		}else{
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED ,"You need to have more than one parameter."));
			return Optional.of(CommandResult.empty());
		}
	}

	public boolean testPermission(CommandSource arg0) {
		// TODO Auto-generated method stub
		logger.info("testPermission");
		return true;
	}
}