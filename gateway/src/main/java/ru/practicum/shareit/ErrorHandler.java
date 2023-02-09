package ru.practicum.shareit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.booking.BookingUnsupportedStatusException;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

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

    @Getter
    @RequiredArgsConstructor
    private static class ExceptionMessage {
        private final String error;
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleBookingUnsupportedStatusError(final BookingUnsupportedStatusException e) {
        return new ResponseEntity<>(new ExceptionMessage("Unknown state: " + e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
