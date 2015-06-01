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
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;
import com.terminalbit.spongy.util.GeneralUtils;

public class Spawn implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationNode config = null;
	
    public Spawn() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    }

	public CommandResult execute(CommandSource cS, CommandContext args)
	throws CommandException {
			World playerWorld = game.getServer().getPlayer(cS.getName()).get().getWorld();
			config = Main.access.mConCache;
			if(config.getNode("spawn").isVirtual()){
				cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ",TextColors.RED, "There is no spawn set. Please ask a server administrator to set one."));
				return CommandResult.empty();
			}
			double x = config.getNode("spawn","position","X").getFloat();
			double y = config.getNode("spawn","position","Y").getFloat();
			double z = config.getNode("spawn","position","Z").getFloat();
			Location spawn = new Location(playerWorld,x,y,z);
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ",TextColors.YELLOW,"Sending you to the spawn."));
			//game.getServer().getPlayer(cS.getName()).get().setLocation(spawn);
			Vector3d desRot = new Vector3d(
					config.getNode("spawn","rotation","X").getFloat(),
					config.getNode("spawn","rotation","Y").getFloat(),
					config.getNode("spawn","rotation","Z").getFloat()
					);
			GeneralUtils.TeleAndRotate(game.getServer().getPlayer(cS.getName()).get(),spawn,desRot);
		return CommandResult.empty();
	}
}