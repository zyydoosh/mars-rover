package com.penta.marsrover;

import Directions.Heading;

import java.util.*;

public class MarsRover {

    public static Position getCoordinates(Position position, String commands) throws Exception {

        Map<Coordinates, Exception> exceptions = new HashMap<>();
        for(Coordinates coor : position.getObstacles())
            exceptions.put(coor, new ObstacleException());

        Position result = new Position();
        Stack<Position> lastValidPosition = new Stack<>();
        lastValidPosition.push(new Position(position));


        try {
            throw exceptions.getOrDefault(position.getCoordinates(), new ValidException());
        }
        catch (ObstacleException exception)
        {
            throw exception;
        }
        catch (ValidException ignored) {}

        for(char ch: commands.toCharArray())
        {
            String command = String.valueOf(ch);
            System.out.println("\ncommand = " + command);

//            Heading dirObj = (Heading) Class.forName(heading).getDeclaredConstructor().newInstance();
            Heading dirObj = DirectionFactory.getDirection(position.heading)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Heading!"));
            dirObj.getClass().getDeclaredMethod(command, Position.class, Stack.class).invoke(dirObj, position, lastValidPosition);
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

class ObstacleException extends Exception{

}
class ValidException extends Exception{

}