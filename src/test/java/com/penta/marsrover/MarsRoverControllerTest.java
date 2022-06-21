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



    /*
     * Default Test Case
     *
     * input     = (4, 2, EAST)
     * command   = FLFFFRFLB
     * output:   = (6, 4, NORTH)
     */
    @Test
    void test1() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(4L, 2L),
                "EAST",
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(6L, 4L),
                "NORTH");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Default Test Case with obstacles
     *
     * input     = (4, 2, EAST)
     * command   = FLFFFRFLB
     * obstacles = [{5,3}]
     * output:   = (5, 2, EAST STOPPED)
     */
    @Test
    void test2() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(5L, 3L));
        wrapper = new RequestWrapper(
                new Coordinates(4L, 2L),
                "EAST",
                obstacles,
                "FLFFFRFLB"
        );

        // Response Body
        result = new Position(
                new Coordinates(5L, 2L),
                "EAST STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Obstacle is at the first movement
     *
     * input     = (44, 70, WEST)
     * command   = BBBBB
     * obstacles = [{45,70}]
     * output:   = (44, 70, WEST STOPPED)
     */
    @Test
    void test3() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(45L, 70L));

        wrapper = new RequestWrapper(
                new Coordinates(44L, 70L),
                "WEST",
                obstacles,
                "BBBBB"
        );

        // Response Body
        result = new Position(
                new Coordinates(44L, 70L),
                "WEST STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Obstacle is at the last movement
     *
     * input     = (-20, -15, NORTH)
     * command   = FFFFF
     * obstacles = [{-20,-10}]
     * output:   = (-20, -11, NORTH STOPPED)
     */
    @Test
    void test4() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(-20L, -10L));

        wrapper = new RequestWrapper(
                new Coordinates(-20L, -15L),
                "NORTH",
                obstacles,
                "FFFFF"
        );

        // Response Body
        result = new Position(
                new Coordinates(-20L, -11L),
                "NORTH STOPPED");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Obstacle is at the input itself, and the Command ends the rover in the same coordinate, by turning left only
     *
     * input     = (4, 2, EAST)
     * command   = LL
     * obstacles = [{4,2}]
     * output:   = Bad request
     */
    @Test
    void test5() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(4L, 2L));
        wrapper = new RequestWrapper(
                new Coordinates(4L, 2L),
                "EAST",
                obstacles,
                "LL"
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
     * Obstacle is at the input itself, and the Command move the rover to some valid coordinate, should refuse also, as start position is an obstacle
     *
     * input     = (4, 2, EAST)
     * command   = FF
     * obstacles = [{4,2}]
     * output:   = Bad request
     */
    @Test
    void test6() throws Exception {
        // Request Body
        obstacles = new ArrayList<>();
        obstacles.add(new Coordinates(4L, 2L));
        wrapper = new RequestWrapper(
                new Coordinates(4L, 2L),
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
     * Negative coordinates
     *
     * input     = (-1, -5, WEST)
     * command   = BBRB
     * output:   = (1, -6, NORTH)
     */
    @Test
    void test7() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(-1L, -5L),
                "WEST",
                "BBRB"
        );

        // Response Body
        result = new Position(
                new Coordinates(1L, -6L),
                "NORTH");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Large negative coordinates
     *
     * input     = (-88888888888888880, -77777777777777770, SOUTH)
     * command   = FRFFFFFFFFFFFF
     * output:   = (-88888888888888868, -77777777777777769, EAST)
     */
    @Test
    void test8() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(-88888888888888880L, -77777777777777770L),
                "SOUTH",
                "FRFFFFFFFFFFFF"
        );

        // Response Body
        result = new Position(
                new Coordinates(-88888888888888868L, -77777777777777769L),
                "EAST");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Large positive coordinates
     *
     * input     = (88888888888888880, 77777777777777770, SOUTH)
     * command   = FRFFFFFFFFFFFF
     * output:   = (88888888888888892, -77777777777777771, EAST)
     */
    @Test
    void test9() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(88888888888888880L, 77777777777777770L),
                "SOUTH",
                "FRFFFFFFFFFFFF"
        );

        // Response Body
        result = new Position(
                new Coordinates(88888888888888892L, 77777777777777771L),
                "EAST");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Large negative/positive coordinates
     *
     * input     = (999999999999999999, -5555555555555555, SOUTH)
     * command   = BBRB
     * output:   = (999999999999999998, -5555555555555553, EAST)
     */
    @Test
    void test10() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(999999999999999999L, -5555555555555555L),
                "SOUTH",
                "BBRB"
        );

        // Response Body
        result = new Position(
                new Coordinates(999999999999999998L, -5555555555555553L),
                "EAST");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }


    /*
     * Missing coordinates in request body, should result in bad request Response (same result for missing commands/heading
     *
     * input     = (, , SOUTH)
     * command   = BBRB
     * output:   = Bad request
     */
    @Test
    void test11() throws Exception {
        // Request Body
        wrapper = new RequestWrapper(
                new Coordinates(),
                "SOUTH",
                "BBRB"
        );

        // Response Body
        ErrorEntity result = new ErrorEntity("Bad request");

        // Test
        mvc.perform(post("/coordinate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrapper)))
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

}