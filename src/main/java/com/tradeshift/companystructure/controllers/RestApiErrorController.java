package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.constants.RestApiErrorRestPathMap;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class RestApiErrorController implements ErrorController {

    private static Logger logger = Logger.getLogger(RestApiErrorController.class.getName());

    @RequestMapping(RestApiErrorRestPathMap.ERROR)
    public String handleError() {
        logger.warning("Resource not found");
        throw new ResourceNotFoundException("Resource not found");
    }

    @Override
    public String getErrorPath() {
        return RestApiErrorRestPathMap.ERROR;
    }
}
