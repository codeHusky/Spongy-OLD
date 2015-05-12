package com.terminalbit.spongy.util;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;

public class GeneralUtils {
	public static void TeleAndRotate(Player target, Location destination, Vector3d rotation){
		target.setRotation(rotation);
		target.setLocation(destination);
	}
}
