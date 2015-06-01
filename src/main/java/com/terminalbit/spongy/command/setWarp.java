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

public class setWarp implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config = null;
	
    public setWarp() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    	
    	this.configManager = Main.access.mainConfig;
    }
	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		this.config = Main.access.mConCache;
		String passed = args.getOne("Warp Name").get().toString();
		passed = passed.toLowerCase();
		try{
		if(Integer.toString(Integer.parseInt(passed)).equals(passed)){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Your warp cannot be just a number."));
			return CommandResult.empty();
		}
		}catch(NumberFormatException e){}
		if(passed.contains(" ")){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Your warp should not contain spaces."));
		}else if(passed.length() > 0) {
			Location playerLoco = game.getServer().getPlayer(cS.getName()).get().getLocation();
			Vector3d playerRot = game.getServer().getPlayer(cS.getName()).get().getRotation();
			config.getNode("warps",passed,"position","x").setValue(playerLoco.getX());
			config.getNode("warps",passed,"position","y").setValue(playerLoco.getY());
			config.getNode("warps",passed,"position","z").setValue(playerLoco.getZ());
			config.getNode("warps",passed,"rotation","x").setValue(playerRot.getX());
			config.getNode("warps",passed,"rotation","y").setValue(playerRot.getY());
			config.getNode("warps",passed,"rotation","z").setValue(playerRot.getZ());
			try {
				configManager.save(config);
			} catch (IOException e) {
			}
			logger.info(cS.getName() + " created/updated the warp \"" + passed + "\"");
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ", TextColors.YELLOW, "The warp \"" + passed + "\" was set."));
		} else {
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Format: /setwarp <warpname>"));
		}
		return CommandResult.empty();
	}
}