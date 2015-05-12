package com.terminalbit.spongy.util;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;

import com.flowpowered.math.vector.Vector3d;
import com.terminalbit.spongy.Main;

public class GeneralUtils {
	public static void TeleAndRotate(Player target, Location destination, Vector3d rotation){
		target.setRotation(rotation);
		Game game = Main.access.game;
		TeleportHelper myTeleHelper = game.getTeleportHelper();
		if(myTeleHelper.getSafeLocation(destination).isPresent()){
			target.setLocation(myTeleHelper.getSafeLocation(destination).get());
		}else{
			target.setLocation(destination);
		}
		target.setLocation(destination);
	}
}
