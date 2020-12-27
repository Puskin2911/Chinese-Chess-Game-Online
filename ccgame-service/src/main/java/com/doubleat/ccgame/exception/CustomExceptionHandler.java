
package com.doubleat.ccgame.exception;

import com.doubleat.ccgame.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ResponseError> handleBadCredentialsException(Exception exception) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setMessage(exception.getMessage());
        error.setDetails("Wrong username or password");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UsernameOrEmailHasAlreadyExistsException.class)
    public final ResponseEntity<ResponseError> handleUsernameOrEmailHasAlreadyExistsException(Exception exception) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ResponseError> handleUsernameNotFoundException(Exception exception) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // TODO: RoomNotFoundException check

}
