package com.rigoberto.console.config;

import com.rigoberto.console.dtos.ApiErrorDto;
import com.rigoberto.console.exceptions.NotFoundException;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

//https://www.baeldung.com/exception-handling-for-rest-with-spring
@ControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    // ---------------
    // 400 Bad Request
    // ---------------

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        final String message = Optional.ofNullable(ex.getRequiredType())
                .map(Class::getName)
                .map(className -> String.format("%s should be of type %s", ex.getName(), className))
                .orElseGet(ex::getMessage);
        return handleExceptionInternal(ex, message, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        Optional<String> message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();

                    return String.format("%s has error: %s", fieldName, errorMessage);
                }).findFirst();
        return handleExceptionInternal(ex, message.orElse(""), BAD_REQUEST, request);
//        return handleExceptionInternal(ex, BAD_REQUEST, request);
    }

    // ---------------
    // 401 Unauthorized
    // ---------------

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {
//        return handleExceptionInternal(ex, UNAUTHORIZED, request);
//    }

    // ---------------
    // 403 Access Denied
    // ---------------

//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
//        return handleExceptionInternal(ex, FORBIDDEN, request);
//    }

    // ---------------
    // 404 Bad Request
    // ---------------

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, NOT_FOUND, request);
    }

    // -------------------------
    // 500 Internal Server Error
    // -------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), INTERNAL_SERVER_ERROR, request);
    }

    // --------------
    // Helper methods
    // --------------

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpStatus httpStatus, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), httpStatus, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, String errorMessage, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);
    }

    @NotNull
    protected ResponseEntity<Object> handleExceptionInternal(@NotNull Exception ex,
                                                             @Nullable Object body,
                                                             @NotNull HttpHeaders headers,
                                                             @NotNull HttpStatus status,
                                                             @NotNull WebRequest webRequest) {
        // Log
        final String errorId = UUID.randomUUID().toString();
        final HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        final String path = String.format("%s %s", request.getMethod(), request.getRequestURI());
//        logger.error(String.format("%s returned for request %s, errorId is %s", status, path, errorId), ex);

        // Create API error
        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.setErrorId(errorId);
        apiErrorDto.setMessage(Optional.ofNullable(body)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElseGet(ex::getMessage));
        apiErrorDto.setHttpStatus(status);
        apiErrorDto.setTimestamp(new Date(System.currentTimeMillis()));
        apiErrorDto.setPath(path);

        // Return
        return new ResponseEntity<>(apiErrorDto, headers, status);
    }
}
