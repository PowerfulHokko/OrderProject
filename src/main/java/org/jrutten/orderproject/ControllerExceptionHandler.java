package org.jrutten.orderproject;

import org.jrutten.orderproject.customer.CustomerAlreadyRegisteredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        logger.warning(exception.getMessage());
    }

    @ExceptionHandler(CustomerAlreadyRegisteredException.class)
    public void handleCustomerAlreadyRegisteredException(CustomerAlreadyRegisteredException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_MODIFIED, exception.getMessage());
        logger.warning(exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public void handleNoSuchElementException(NoSuchElementException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
        logger.warning(exception.getMessage());
    }


}
