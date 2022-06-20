package com.penta.marsrover;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class MarsRoverControllerTest {

    @Autowired
    private MockMvc mvc;
    ObjectMapper mapper = new ObjectMapper();

    Position result;

    RequestWrapper wrapper;

    List<Coordinates> obstacles;


    // No Obstacles
    @Test
    void coordinate1() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(6, 4),
                "NORTH");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    // Obstacles
    @Test
    void coordinate2() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(5, 3));
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                obstacles,
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(5, 2),
                "EAST STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    /*
    * Test Case when input position is an obstacle, and Command is keeping it in the same obstacle coordinate, by turning left only
    */
    @Test
    void coordinate3() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(4, 2));
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                obstacles,
                "FF"
        );

        // Response Body
        ErrorEntity result = new ErrorEntity("Bad request");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    /*
     * Test Case when input position is an obstacle, and Command is moving the position to a valid coordinate, should refuse also, as start position is an obstacle
     */
    @Test
    void coordinate4() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(4, 2));
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                obstacles,
                "FF"
        );

        // Response Body
        ErrorEntity result = new ErrorEntity("Bad request");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    @Test
    void coordinate5() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(5, 3));
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                obstacles,
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(5, 2),
                "EAST STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    @Test
    void coordinate6() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(5, 3));
        wrapper = new RequestWrapper(
                new Coordinates(4, 2),
                "EAST",
                obstacles,
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(5, 2),
                "EAST STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }
}