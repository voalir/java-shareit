package ru.practicum.shareit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemValidateException;
import ru.practicum.shareit.user.exception.UserAccessException;
import ru.practicum.shareit.user.exception.UserEmailEmployed;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUser(final UserNotFoundException e) {
        return new ResponseEntity<>(
                Map.of("user found error", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleItem(final ItemNotFoundException e) {
        return new ResponseEntity<>(
                Map.of("item found error", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidate(final ValidationException e) {
        return new ResponseEntity<>(
                Map.of("validate error", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleInternalError(final RuntimeException e) {
        return new ResponseEntity<>(
                Map.of("internal error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserDataError(final UserEmailEmployed e) {
        return new ResponseEntity<>(
                Map.of("user email duplicate error", e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserAccessError(final UserAccessException e) {
        return new ResponseEntity<>(
                Map.of("user access error", e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleItemValidateError(final ItemValidateException e) {
        return new ResponseEntity<>(
                Map.of("item validate error", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
