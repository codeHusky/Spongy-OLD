package com.terminalbit.spongy.command;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.terminalbit.spongy.Main;

public class MainCommand implements CommandExecutor {

	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		cS.sendMessage(Texts.of("Spongy v" + Main.access.version));
		return CommandResult.empty();
	}

}
