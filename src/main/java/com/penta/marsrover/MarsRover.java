package com.penta.marsrover;

import Directions.Heading;

import java.util.*;

public class MarsRover {

    public static Position getCoordinates(Position position, String commands) throws Exception {
        // Add exception for each obstacle coordinate
        Map<Coordinates, Exception> exceptions = new HashMap<>();
        for(Coordinates coor : position.getObstacles())
            exceptions.put(coor, new ObstacleException());

        Position result = new Position();
        Stack<Position> lastValidPosition = new Stack<>();
        lastValidPosition.push(new Position(position));


        // Check if the input position itself was an obstacle
        try {
            throw exceptions.getOrDefault(position.getCoordinates(), new ValidException());
        }
        catch (ObstacleException exception)
        {
            throw exception;
        }
        catch (ValidException ignored) {}

        // Execute Commands
        for(char ch: commands.toCharArray())
        {
            String command = String.valueOf(ch);

            // Choose which Heading implementation to use according to the name of the heading (EAST, WEST, NORTH, SOUTH)
            Heading dirObj = DirectionFactory.getDirection(position.heading)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Heading!"));
            // call the method according to the current command (F, B, L, R)
            dirObj.getClass().getDeclaredMethod(command, Position.class, Stack.class).invoke(dirObj, position, lastValidPosition);
            // Enter ObstacleException clause if the new Position is an Obstacle, ValidException else
            try {
                throw exceptions.getOrDefault(position.getCoordinates(), new ValidException());
            }
            catch (ObstacleException e)
            {
                lastValidPosition.pop();

                result = lastValidPosition.pop();
                result.heading += " STOPPED";
                System.out.println("Stopped at");
                System.out.println(result);

                break;
            }
            catch (ValidException e)
            {
                result = position;
                System.out.println(result);
            }
        }
        return result;
    }
}

class ObstacleException extends Exception{}

class ValidException extends Exception{}