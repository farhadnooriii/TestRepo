package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositoryImpl;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import com.tradeshift.companystructure.services.companynode.CompanyNodeServiceImpl;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <h1> CompanyNodeController </h1>
 * This class is used to serve http rest api.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-11
 */
@RestController
@RequestMapping("/api/v1")
public class CompanyNodeController {

    private static Logger logger = Logger.getLogger(CompanyNodeController.class.getName());
    @Autowired
    private CompanyNodeService companyNodeService;

    /**
     * This method is used to get all children of
     * given node id.
     *
     * @param id  This parameter specify node id.
     * @return ResponseEntity<List<CompanyNode>> This return all children
     */
    @RequestMapping(value = "/companynodes/{id}/childrens",method = RequestMethod.GET)
    public ResponseEntity<List<CompanyNode>> getAllChildrenOfGivenNode(@PathVariable("id") long id) throws Exception {

        if(id==0)
            return ResponseEntity.ok(new ArrayList<>());
        try {
            return ResponseEntity.ok(companyNodeService.getAllChildren(new CompanyNode(id)));
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw e;
        }
    }

    /**
     * This method is used to change parent of
     * given node id.
     *
     * @param id      This parameter specify node id.
     * @param parentId    This parameter specify new parent node id.
     * @return ResponseEntity<CompanyNode> This return given node along updated parent.
     */
    @RequestMapping(value = "/companynodes/{id}/parent/{parentId}",method = RequestMethod.PUT)
    public ResponseEntity<CompanyNode> changeParentNodeOfGivenNode(@PathVariable("id") long id,@PathVariable("parentId") long parentId) throws Exception {
        try {
           CompanyNode companyNode = companyNodeService.updateNodeParent(new CompanyNode(id), new CompanyNode(parentId));
           return ResponseEntity.ok(companyNode);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw e;
        }
    }

    /**
     * This method is used to test container is alive or not.
     *
     * @return String This return string of message just for test.
     */
    @GetMapping("/isAlive")
    public String isAlive() {

        String msg = "Every Thing Ok and container is running...";
        logger.log(java.util.logging.Level.INFO, msg);
        return msg;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String exceptionHandler(Exception e) {
        return e.getMessage();
    }

}
