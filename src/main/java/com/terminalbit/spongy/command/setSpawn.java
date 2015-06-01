package com.terminalbit.spongy.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

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
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;

public class setSpawn implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config = null;
	
    public setSpawn() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    	this.configManager = Main.access.mainConfig;
    }
	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		try{
			config = Main.access.mConCache;
			Location playerLocation = game.getServer().getPlayer(cS.getName()).get().getLocation();
			Vector3d playerRot = game.getServer().getPlayer(cS.getName()).get().getRotation();
			config.getNode("spawn","position","X").setValue(playerLocation.getX());
			config.getNode("spawn","position","Y").setValue(playerLocation.getY());
			config.getNode("spawn","position","Z").setValue(playerLocation.getZ());
			config.getNode("spawn","rotation","X").setValue(playerRot.getX());
			config.getNode("spawn","rotation","Y").setValue(playerRot.getY());
			config.getNode("spawn","rotation","Z").setValue(playerRot.getZ());
			configManager.save(config);
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ",TextColors.YELLOW,"The spawn was set."));
		}catch(IOException e){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ",TextColors.RED, "Failed to set spawn."));
		}
		return CommandResult.empty();
	}
}