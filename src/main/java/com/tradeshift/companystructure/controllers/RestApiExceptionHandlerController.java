package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.viewmodels.error.RestApiErrorVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by F.Nouri on 1/22/2019.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestApiExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new RestApiErrorVM(BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiErrorVM restApiErrorVM) {
        return new ResponseEntity<>(restApiErrorVM, restApiErrorVM.getStatus());
    }
}
