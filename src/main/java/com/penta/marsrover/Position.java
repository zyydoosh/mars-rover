package com.penta.marsrover;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Position {

    private Coordinates coordinates;
    public String heading;

    @JsonIgnore
    List<Coordinates> obstacles;

    public Position() {
    }

    // Deep Copy
    public Position(Position position) {
        this(new Coordinates(position.coordinates), position.heading, position.obstacles);
    }

    public Position(Coordinates coordinates, String heading) {
        this.coordinates = coordinates;
        this.heading = heading;
    }

    public Position(Coordinates coordinates, String heading, List<Coordinates> obstacles) {
        this.coordinates = coordinates;
        this.heading = heading;
        this.obstacles = obstacles;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Coordinates> getObstacles() {
        return Optional.ofNullable(obstacles).orElse(new ArrayList<>());
    }

    public void setObstacles(List<Coordinates> obstacles) {
        this.obstacles = obstacles;
    }

    @Override
    public String toString() {
        return "Position{" +
                "coordinates=" + coordinates +
                ", heading='" + heading + '\'' +
                '}';
    }
}
