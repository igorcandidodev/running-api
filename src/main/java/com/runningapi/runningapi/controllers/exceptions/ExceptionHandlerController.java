package com.runningapi.runningapi.controllers.exceptions;

import com.runningapi.runningapi.dto.response.StandardErrorResponseDto;
import com.runningapi.runningapi.exceptions.TrainingPerformedException;
import com.runningapi.runningapi.exceptions.UserException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    public static final String TIME_ZONE = "America/Sao_Paulo";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        var error = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TrainingPerformedException.class)
    public ResponseEntity<StandardErrorResponseDto> handleTrainingPerformedException(TrainingPerformedException ex, HttpServletRequest request) {
        var error = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                HttpStatus.BAD_REQUEST.value(),
                "Erro - Treino Realizado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<StandardErrorResponseDto> handleUserException(UserException ex, HttpServletRequest request) {
        var error = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                HttpStatus.BAD_REQUEST.value(),
                "Erro - Usuário",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandardErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Erro de validação.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getBindingResult().getFieldErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(e.getMessage());
        var standardError = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<StandardErrorResponseDto> handle404Error(HttpServletRequest request) {
        String error = "Rota não encontrada.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        var standardError = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)), status.value(), error, "Rota não encontrada.", request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponseDto> handleException(Exception ex, HttpServletRequest request) {
        var error = new StandardErrorResponseDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(500).body(error);
    }
}
