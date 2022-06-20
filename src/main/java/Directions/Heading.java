package Directions;


import java.util.Stack;
import com.penta.marsrover.Position;

import java.util.Map;


public interface Heading {

    // Forward command
    void F(Position position, Stack<Position> lastValidCoordinates);
    void B(Position position, Stack<Position> lastValidCoordinates);
    void L(Position position, Stack<Position> lastValidCoordinates);
    void R(Position position, Stack<Position> lastValidCoordinates);

}
