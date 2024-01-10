package com.beeecommerce.exception;

import com.beeecommerce.constance.ConstraintName;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.exception.core.ApplicationRuntimeException;
import com.beeecommerce.exception.core.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${app.exception.print-stack-trace}")
    private Boolean printStackTrace;

    @Value("${app.exception.show-class-name}")
    private Boolean showClassName;

    private void printStackTrace(Exception ex) {
        if (printStackTrace) {
            ex.printStackTrace();
        }
    }

    @ExceptionHandler({ApplicationException.class, ApplicationRuntimeException.class})
    public ResponseEntity<ErrorDetails> handlerApplicationException(ApplicationException ex, HttpServletRequest request) {

        printStackTrace(ex);

        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .statusCode(ex.getStatus().value())
                .status(ex.getStatus().getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(new Date())
                .exceptionClass(showClassName ? ex.getClass().getSimpleName() : "")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(ex.getStatus())
                .body(errorDetails);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetails> handle(DataIntegrityViolationException exception, HttpServletRequest request) {
        printStackTrace(exception);

        StringBuilder message = new StringBuilder();

        String errorMessage = exception.getMessage();

        Map<String, String> constraintName = ConstraintName.getConstraintName();


        for (String key : constraintName.keySet()) {
            if (errorMessage.contains(key)) {
                message.append(constraintName.get(key));
                break;
            }
        }

        if (message.isEmpty()) {
            logger.warn("Using default message for data integrity violation");
            message.append("Data integrity violation");
        }


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorDetails
                                .builder()
                                .timestamp(new Date())
                                .message(message.toString())
                                .statusCode(500)
                                .status("INTERNAL_SERVER_ERROR")
                                .exceptionClass(exception.getClass().getSimpleName())
                                .path(request.getRequestURI())
                                .build()
                );
    }


    // Catch all exceptions in spring security
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorDetails> handlerDisabledException(DisabledException ex, HttpServletRequest request) {

        printStackTrace(ex);

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .statusCode(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message("Tài khoản chưa kích hoạt!Vui lòng kiểm tra email!")
                .timestamp(new Date())
                .exceptionClass(showClassName ? ex.getClass().getSimpleName() : "")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(errorDetails);
    }

    @ExceptionHandler(SerializationException.class)
    public ResponseEntity<ErrorDetails> handlerSerializationException(DisabledException ex, HttpServletRequest request) {

        printStackTrace(ex);

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .statusCode(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message("Lỗi bộ nhớ cache!")
                .timestamp(new Date())
                .exceptionClass(showClassName ? ex.getClass().getSimpleName() : "")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(errorDetails);
    }


}
