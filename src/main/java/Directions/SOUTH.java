package Directions;


import com.penta.marsrover.Position;

import java.util.Stack;

public class SOUTH implements Heading {
    @Override
    public void F(Position position, Stack<Position> lastValidCoordinates) {
        position.getCoordinates().y += 1;
        lastValidCoordinates.push(new Position(position));
    }

    @Override
    public void B(Position position, Stack<Position> lastValidCoordinates) {
        position.getCoordinates().y += 1;
        lastValidCoordinates.push(new Position(position));
    }

    @Override
    public void L(Position position, Stack<Position> lastValidCoordinates) {
        position.heading = "WEST";
    }

    @Override
    public void R(Position position, Stack<Position> lastValidCoordinates) {
        position.heading = "EAST";
    }
}
