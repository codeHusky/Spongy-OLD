package com.terminalbit.spongy.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
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
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import com.terminalbit.spongy.Main;
import com.terminalbit.spongy.util.GeneralUtils;

public class Warp implements CommandExecutor {
	private Logger logger;
	private Game game;
	private Text hT = Texts.of("Help Text");
	private Text dT = Texts.of("Description!");
	private Text usage = Texts.of("Usage!! :D");
	private Optional<Text> help = Optional.of(hT);
	private Optional<Text> desc = Optional.of(dT);
	private ConfigurationNode config = null;
	
	public Warp() {
    	//Gets the, you know, stuff from the main class.
    	this.logger = Main.access.logger;
    	this.game = Main.access.game;
    }

	public CommandResult execute(CommandSource cS, CommandContext args)
			throws CommandException {
		String passed = "";
		if(args.getOne("Warp Name").isPresent()){
			passed = args.getOne("Warp Name").get().toString();
		}
		config = Main.access.mConCache;
		passed = passed.toLowerCase();
		try{
		if(Integer.toString(Integer.parseInt(passed)).equals(passed)){
			int startNum = 0;
			try{
				startNum = Integer.parseInt(passed) - 1;
			}catch(NumberFormatException e){}
			cS.sendMessage(Texts.of(TextColors.BLUE,"Listing warps " + (startNum + 1) + " thru " + (startNum + 5) + ":"));
			int warpNum = 0;
			for(Object child: config.getNode("warps").getChildrenMap().keySet()){
				if(warpNum < (5 + startNum)&&warpNum>=startNum){
					cS.sendMessage(Texts.of(TextColors.GOLD,"   Warp " + (warpNum+1) + ": ", TextColors.YELLOW,child.toString()));
				}
				warpNum++;
			}
			return CommandResult.empty();
		}
		}catch(NumberFormatException e){}
		if(passed.length() <= 0){
			int startNum = 0;
			cS.sendMessage(Texts.of(TextColors.BLUE,"Listing warps " + (startNum + 1) + " thru " + (startNum + 5) + ":"));
			int warpNum = 0;
			for(Object child: config.getNode("warps").getChildrenMap().keySet()){
				if(warpNum < (5 + startNum)&&warpNum>=startNum){
					cS.sendMessage(Texts.of(TextColors.GOLD,"   Warp " + (warpNum+1) + ": ", TextColors.YELLOW,child.toString()));
				}
				warpNum++;
			}
		}else if(passed.contains(" ")){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Your warp should not contain spaces."));
		}else if(config.getNode("warps",passed).isVirtual()){
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "The warp \"" + passed + "\" does not exist."));
		}else if(passed.length() > 0) {
			Location destinationPos = new Location(
					game.getServer().getPlayer(cS.getName()).get().getWorld(),
					config.getNode("warps",passed,"position","x").getFloat(),
					config.getNode("warps",passed,"position","y").getFloat(),
					config.getNode("warps",passed,"position","z").getFloat()
			);
			Vector3d destinationRot = new Vector3d (
					config.getNode("warps",passed,"rotation","x").getFloat(),
					config.getNode("warps",passed,"rotation","y").getFloat(),
					config.getNode("warps",passed,"rotation","z").getFloat()
			);
			Player caller = game.getServer().getPlayer(cS.getName()).get();
			GeneralUtils.TeleAndRotate(caller,destinationPos,destinationRot);
			logger.info(cS.getName() + " warped to " + passed);
			cS.sendMessage(Texts.of(TextColors.GOLD,"Success: ", TextColors.YELLOW, "Sending you to \"" + passed + "\"."));
		} else {
			cS.sendMessage(Texts.of(TextColors.DARK_RED,"Error: ", TextColors.RED, "Format: /warp <warpname>"));
			cS.sendMessage(Texts.of(TextColors.DARK_BLUE,"Note: ", TextColors.BLUE, "Looking for a list of warps? That feature is coming soon, so stay tuned for more updates!"));
		}
		return CommandResult.empty();
	}
}