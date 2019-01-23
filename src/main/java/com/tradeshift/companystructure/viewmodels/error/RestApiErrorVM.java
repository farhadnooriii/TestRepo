package com.tradeshift.companystructure.viewmodels.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by F.Nouri on 1/22/2019.
 */
public class RestApiErrorVM {

    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String exceptionClass;
    private String message;
    private String debugMessage;
    private List<RestApiSubErrorVM> restApiSubErrorVMS;

    private RestApiErrorVM() {
        timestamp = LocalDateTime.now();
    }

    public RestApiErrorVM(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public RestApiErrorVM(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public RestApiErrorVM(HttpStatus httpStatus, String message, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<RestApiSubErrorVM> getRestApiSubErrorVMS() {
        return restApiSubErrorVMS;
    }

    public void setRestApiSubErrorVMS(List<RestApiSubErrorVM> restApiSubErrorVMS) {
        this.restApiSubErrorVMS = restApiSubErrorVMS;
    }

    private void addSubError(RestApiSubErrorVM restApiSubErrorVM) {
        if (restApiSubErrorVMS == null)
            restApiSubErrorVMS = new ArrayList<>();
        restApiSubErrorVMS.add(restApiSubErrorVM);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new RestApiValidationErrorVM(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new RestApiValidationErrorVM(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

   public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
