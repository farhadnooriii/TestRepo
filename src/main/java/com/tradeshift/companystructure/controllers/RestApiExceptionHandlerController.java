package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.repositories.exceptions.CompanyNodeNotFoundException;
import com.tradeshift.companystructure.viewmodels.error.RestApiErrorVM;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by F.Nouri on 1/22/2019.
 */
@ControllerAdvice
public class RestApiExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Convert restApiErrorVM to ResponseEntity.
     *
     * @param restApiErrorVM the CompanyNodeNotFoundException
     * @return the ResponseEntity<RestApiErrorVM>
     */
    private ResponseEntity<Object> buildResponseEntity(RestApiErrorVM restApiErrorVM) {
        return new ResponseEntity<>(restApiErrorVM, restApiErrorVM.getHttpStatus());
    }

    /**
     * Handles When entity not found.
     *
     * @param ex the CompanyNodeNotFoundException
     * @return the RestApiErrorVM object
     */
    @ExceptionHandler(CompanyNodeNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CompanyNodeNotFoundException ex) {
        RestApiErrorVM restApiErrorVM = new RestApiErrorVM(HttpStatus.NOT_FOUND);
        restApiErrorVM.setMessage(ex.getMessage());
        return buildResponseEntity(restApiErrorVM);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the RestApiErrorVM object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        RestApiErrorVM restApiErrorVM = new RestApiErrorVM(HttpStatus.BAD_REQUEST);
        restApiErrorVM.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        restApiErrorVM.setDebugMessage(ex.getMessage());
        restApiErrorVM.setExceptionClass(ex.getClass().getName());
        return buildResponseEntity(restApiErrorVM);
    }

    /**
     * Handle Exception, handle ResourceNotFoundException.class
     *
     * @param ex the Exception
     * @return the RestApiErrorVM object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleMethodResourceNotFound(ResourceNotFoundException ex) {
        RestApiErrorVM restApiErrorVM = new RestApiErrorVM(HttpStatus.NOT_FOUND);
        restApiErrorVM.setMessage(String.format("The path not found"));
        restApiErrorVM.setDebugMessage(ex.getMessage());
        restApiErrorVM.setExceptionClass(ex.getClass().getName());
        return buildResponseEntity(restApiErrorVM);
    }

    /**
     * Handle DataAccessResourceFailureException.class exception.
     *
     * @param ex the Exception
     * @return the RestApiErrorVM object
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    protected ResponseEntity<Object> handleMethodDatabaseAccessFailure(DataAccessResourceFailureException ex) {
        RestApiErrorVM restApiErrorVM = new RestApiErrorVM(HttpStatus.BAD_REQUEST);
        restApiErrorVM.setMessage(String.format("Database connection failed"));
        restApiErrorVM.setDebugMessage(ex.getMessage());
        restApiErrorVM.setExceptionClass(ex.getClass().getName());
        return buildResponseEntity(restApiErrorVM);
    }

    /**
     * Handle Exception.class exception.
     *
     * @param ex the Exception
     * @return the RestApiErrorVM object
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleDefaultMethod(Exception ex) {
        RestApiErrorVM restApiErrorVM = new RestApiErrorVM(HttpStatus.BAD_REQUEST);
        restApiErrorVM.setMessage(String.format("There is Exception. Contact to Administrator"));
        restApiErrorVM.setDebugMessage(ex.getMessage());
        restApiErrorVM.setExceptionClass(ex.getClass().getName());
        return buildResponseEntity(restApiErrorVM);
    }
}
