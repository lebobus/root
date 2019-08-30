package me.lebobus.root.utils;

import org.apache.commons.lang.math.IntRange;
import org.bukkit.Location;

public class Cuboid {

    public static boolean inCuboid(Location origin, Location l1, Location l2) {
        return new IntRange(l1.getX(), l2.getX()).containsDouble(origin.getX())
                && new IntRange(l1.getY(), l2.getY()).containsDouble(origin.getY())
                && new IntRange(l1.getZ(), l2.getZ()).containsDouble(origin.getZ());
    }

}
