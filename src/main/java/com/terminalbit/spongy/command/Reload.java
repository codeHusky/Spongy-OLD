package com.terminalbit.spongy.command;

import java.io.IOException;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.terminalbit.spongy.Main;

public class Reload implements CommandExecutor {

	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		cS.sendMessage(Texts.of("Spongy's config file has been reloaded."));
		try {
			Main.access.mainConfig.save(Main.access.mConCache);
			Main.access.userConfig.save(Main.access.uConCache);
			Main.access.mConCache = Main.access.mainConfig.load();
			Main.access.uConCache = Main.access.userConfig.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CommandResult.empty();
	}

}
