package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositoryImpl;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import com.tradeshift.companystructure.services.companynode.CompanyNodeServiceImpl;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidationImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/companystructure")
public class CompanyNodeController {

    private static Logger logger = Logger.getLogger(CompanyNodeController.class.getName());
    private final CompanyNodeService companyNodeService;

    public CompanyNodeController() {
        this.companyNodeService = new CompanyNodeServiceImpl(new CompanyNodeRepositoryImpl(),new CompanyNodeValidationImpl(new CompanyNodeRepositoryImpl()));
    }

    /**
     * This method is used to get all children of
     * given node id.
     *
     * @param nodeId  This parameter specify node id.
     * @return List<CompanyNode></CompanyNode> This return all children
     */
    @GetMapping("/getChildren")
    public List<CompanyNode> getAllChildrenOfGivenNode(@RequestParam(value = "nodeId") Long nodeId) {

        if (nodeId == null)
            return null;
        CompanyNode companyNode = new CompanyNode();
        companyNode.setId(nodeId);
        try {
            return companyNodeService.getAllChildren(companyNode);
        } catch (Exception e) {
            logger.info("There is Exception in getAllChildrenOfGivenNode: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method is used to change parent of
     * given node id.
     *
     * @param nodeId      This parameter specify node id.
     * @param parentNodeId    This parameter specify new parent node id.
     * @return String This return string of message just for test.
     */
    @GetMapping("/changeParent")
    public String changeParentNodeOfGivenNode(@RequestParam(value = "nodeId") Long nodeId, @RequestParam(value = "parentNodeId") Long parentNodeId) {

        String msgSuccess = "Successfully Done";
        String msgFail = "There Is Problem And Not Done";
        if (nodeId == null || parentNodeId == null)
            return msgFail;
        CompanyNode companyNode = new CompanyNode();
        companyNode.setId(nodeId);
        CompanyNode parentNode = new CompanyNode();
        parentNode.setId(parentNodeId);
        try {
            companyNodeService.updateNodeParent(companyNode, parentNode);
            return msgSuccess;
        } catch (Exception e) {
            logger.info("There is Exception in changeParentNodeOfGivenNode: " + e.getMessage());
            return msgFail;
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
}
