package com.penta.marsrover;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MarsRoverController {

    @PostMapping("coordinate")
    public ResponseEntity coordinate(@RequestBody RequestWrapper requestWrapper) {
        try {
            Position result = MarsRover.getCoordinates(new Position(requestWrapper.getCoordinates(), requestWrapper.heading, requestWrapper.obstacles), requestWrapper.commands);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorEntity error = new ErrorEntity("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
