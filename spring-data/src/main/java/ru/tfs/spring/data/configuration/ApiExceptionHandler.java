package ru.tfs.spring.data.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tfs.spring.data.dto.ApiErrorResponse;
import ru.tfs.spring.data.exception.UserPhoneUniqueException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserPhoneUniqueException.class})
    protected ResponseEntity<Object> handleValidationError(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                    .errorCode("VALIDATION_ERROR")
                    .errorMessage(ex.getMessage()).build()
                );

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleExistError(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode("NOT_EXIST_ERROR")
                        .errorMessage(ex.getMessage()).build()
                );
    }

    @ExceptionHandler(value = {NullPointerException.class})
    protected ResponseEntity<Object> handleNpeError(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode("NPE_ERROR")
                        .errorMessage(ex.getMessage()).build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode("ARGUMENT_NOT_VALID_ERROR")
                        .errorMessage(ex.getMessage()).build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode("SOME_MISTAKE")
                        .errorMessage(ex.getMessage()).build()
                );
    }
}
