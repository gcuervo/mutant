package com.colon.mutantproject.web.exception;


import com.colon.mutantproject.service.exception.DnaBaseException;
import com.colon.mutantproject.service.exception.DnaFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler  {

    @ExceptionHandler(DnaFormatException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(DnaFormatException ex, WebRequest request) {
        String error = "Malformed DNA matrix request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(DnaBaseException.class)
    protected ResponseEntity<Object> handleHttpMessage(DnaBaseException ex, WebRequest request) {
        String error = "Malformed DNA base request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleHttpMessageNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        String error = "Not Found request";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
