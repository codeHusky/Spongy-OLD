package com.terminalbit.spongy.command;

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
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;

public class actAsConsole implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	
    public actAsConsole() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    }

	public CommandResult execute(CommandSource cS, CommandContext args) throws CommandException {
		String passed = args.getOne("command").get().toString();
		logger.info(cS.getName() + " called " + passed + " as the console.");
		cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ", TextColors.YELLOW, "You called \"" + passed + "\" as the console."));
		game.getCommandDispatcher().process(game.getServer().getConsole(), passed);
		return CommandResult.empty();
	}
}