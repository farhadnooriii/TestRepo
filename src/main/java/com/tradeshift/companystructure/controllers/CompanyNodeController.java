package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.constants.CompanyNodePathMap;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import com.tradeshift.companystructure.viewmodels.companynode.CompanyNodeVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <h1> CompanyNodeController </h1>
 * This class is used to serve http rest api.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-11
 */
@RestController
@RequestMapping(value = CompanyNodePathMap.API_V1, produces = "application/hal+json")
public class CompanyNodeController {

    private static Logger logger = Logger.getLogger(CompanyNodeController.class.getName());
    @Autowired
    private CompanyNodeService companyNodeService;

    /**
     * This method is used to get all children of
     * given node id.
     *
     * @param id This parameter specify node id.
     * @return ResponseEntity<List < CompanyNode>> This return all children
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID_CHILDREN, method = RequestMethod.GET)
    public ResponseEntity<List<CompanyNode>> getAllChildrenOfGivenNode(@PathVariable("id") long id) throws Exception {

        try {
            return ResponseEntity.ok(companyNodeService.getAllChildren(new CompanyNode(id)));
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw ex;
        }
    }

    /**
     * This method is used to get all children of
     * given node id.
     *
     * @param id This parameter specify node id.
     * @return ResponseEntity<Resources < CompanyNodeVM>> This return all children through HATEOAS template
     */
    @RequestMapping(value = CompanyNodePathMap.RES_COMPANYNODES_ID_CHILDREN, method = RequestMethod.GET)
    public ResponseEntity<Resources<CompanyNodeVM>> getAllChildrenOfGivenNodeInRes(@PathVariable("id") long id) throws Exception {

        try {
            List<CompanyNodeVM> collection = companyNodeService.getAllChildren(new CompanyNode(id))
                    .stream()
                    .map(CompanyNodeVM::new)
                    .collect(Collectors.toList());
            Resources<CompanyNodeVM> resources = new Resources<>(collection);
            String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
            resources.add(new Link(uriString, CompanyNodePathMap.SELF));
            return ResponseEntity.ok(resources);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw ex;
        }
    }

    /**
     * This method is used to change parent of
     * given node id.
     *
     * @param id       This parameter specify node id.
     * @param parentId This parameter specify new parent node id.
     * @return ResponseEntity<CompanyNode> This return given node along updated parent.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID_PARENT_PARENTID, method = RequestMethod.PUT)
    public ResponseEntity<CompanyNode> changeParentNodeOfGivenNode(
            @PathVariable("id") long id,
            @PathVariable("parentId") long parentId) throws Exception {
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
}
