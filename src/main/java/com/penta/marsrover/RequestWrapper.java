package com.penta.marsrover;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;


public class RequestWrapper {
    private Coordinates coordinates;

    public String heading;
    @Nullable
    List<Coordinates> obstacles;

    String commands;

    public RequestWrapper() {
    }

    public RequestWrapper(Coordinates coordinates, String heading, String commands) {
        this.coordinates = coordinates;
        this.heading = heading;
        this.commands = commands;
    }

    public RequestWrapper(Coordinates coordinates, String heading, List<Coordinates> obstacles, String commands) {
        this.coordinates = coordinates;
        this.heading = heading;
        this.obstacles = obstacles;
        this.commands = commands;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Coordinates> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Coordinates> obstacles) {
        this.obstacles = obstacles;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "RequestWrapper{" +
                "coordinates=" + coordinates +
                ", heading='" + heading + '\'' +
                ", obstacles=" + obstacles +
                ", commands='" + commands + '\'' +
                '}';
    }
}
