package com.penta.marsrover;


import java.util.Objects;

public class Coordinates {
    public Long x;

    public Long y;

    public Coordinates() {
    }

    public Coordinates(Coordinates coordinates) {
        this(coordinates.x, coordinates.y);
    }

    public Coordinates(Long x, Long y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
