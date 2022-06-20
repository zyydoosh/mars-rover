package Directions;

import java.util.Stack;

import com.penta.marsrover.Position;

public class NORTH implements Heading {
    @Override
    public void F(Position position, Stack<Position> lastValidCoordinates) {
        position.getCoordinates().y += 1;
        lastValidCoordinates.push(new Position(position));
    }

    @Override
    public void B(Position position, Stack<Position> lastValidCoordinates) {
        position.getCoordinates().y -= 1;
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
