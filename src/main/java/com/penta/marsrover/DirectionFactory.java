package com.penta.marsrover;

import Directions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DirectionFactory {
    static Map<String, Heading> directionMap = new HashMap<>();
    static {
        directionMap.put("EAST", new EAST());
        directionMap.put("WEST", new WEST());
        directionMap.put("NORTH", new NORTH());
        directionMap.put("SOUTH", new SOUTH());
    }

    public static Optional<Heading> getDirection(String heading)
    {
        return Optional.ofNullable(directionMap.get(heading));
    }
}
