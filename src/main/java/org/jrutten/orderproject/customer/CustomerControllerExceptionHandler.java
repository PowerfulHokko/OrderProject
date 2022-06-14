package org.jrutten.orderproject.customer;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class CustomerControllerExceptionHandler {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        logger.warning(exception.getMessage());
    }

    @ExceptionHandler(CustomerAlreadyRegisteredException.class)
    public void handleCustomerAlreadyRegisteredException(CustomerAlreadyRegisteredException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        logger.warning(exception.getMessage());
    }
}
