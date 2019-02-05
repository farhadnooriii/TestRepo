package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.constants.companynode.CompanyNodePathMap;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.resourceassembler.CompanyNodeResourceAssembler;
import com.tradeshift.companystructure.services.companynode.CompanyNodeImportService;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = CompanyNodePathMap.API_V1)
public class CompanyNodeController {

    private static Logger logger = Logger.getLogger(CompanyNodeController.class.getName());

    private CompanyNodeService companyNodeService;
    private CompanyNodeImportService companyNodeImportService;
    private CompanyNodeResourceAssembler companyNodeResourceAssembler;

    public CompanyNodeController(CompanyNodeService companyNodeService,
                                 CompanyNodeImportService companyNodeImportService,
                                 CompanyNodeResourceAssembler companyNodeResourceAssembler) {
        this.companyNodeService = companyNodeService;
        this.companyNodeImportService = companyNodeImportService;
        this.companyNodeResourceAssembler = companyNodeResourceAssembler;
    }

    /**
     * This method is used to create company nodes
     * with initial data.
     *
     * @return ResponseEntity<CompanyNode> This return root node.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_INITIALDATA, method = RequestMethod.POST)
    public ResponseEntity<Resource<CompanyNode>> createNodesWithInitialData() {
        try {
            this.companyNodeImportService.clearDatabase();
            CompanyNode rootNode = this.companyNodeImportService.importPreData();
            return ResponseEntity.ok(companyNodeResourceAssembler.toResource(rootNode));
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw ex;
        }
    }

    /**
     * This method is used to find company nodes
     * by id.
     *
     * @return ResponseEntity<CompanyNode> This return company node with HATEOAS Template.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID, method = RequestMethod.GET)
    public ResponseEntity<Resource<CompanyNode>> one(@PathVariable("id") long id) throws Exception {

        try {
            CompanyNode companyNode = this.companyNodeService.one(new CompanyNode(id));
            return ResponseEntity.ok(companyNodeResourceAssembler.toResource(companyNode));
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw ex;
        }
    }

    /**
     * This method is used to get parent of given node
     * by id.
     *
     * @return ResponseEntity<CompanyNode> This return parent of company node with HATEOAS Template.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID_PARENT, method = RequestMethod.GET)
    public ResponseEntity<Resource<CompanyNode>> getParentNodeOfGivenNode(@PathVariable("id") long id) throws Exception {

        try {
            CompanyNode companyNode = this.companyNodeService.getParent(new CompanyNode(id));
            return ResponseEntity.ok(companyNodeResourceAssembler.toResource(companyNode));
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
     * @return ResponseEntity<Resources < CompanyNodeVM>> This return all children
     * through HATEOAS template.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID_CHILDREN, method = RequestMethod.GET)
    public ResponseEntity<Resources<Resource<CompanyNode>>> getAllChildrenOfGivenNode(@PathVariable("id") long id) throws Exception {

        try {
            List<Resource<CompanyNode>> collection = companyNodeService.getAllChildrenWithHeightAndRoot(new CompanyNode(id))
                    .stream()
                    .map(child -> companyNodeResourceAssembler.toResource(child))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(companyNodeResourceAssembler.toResources(collection));
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
     * @return ResponseEntity<CompanyNode> This return given node along updated parent
     * in HATEOAS Template.
     */
    @RequestMapping(value = CompanyNodePathMap.COMPANYNODES_ID_PARENT_PARENTID, method = RequestMethod.PUT)
    public ResponseEntity<Resource<CompanyNode>> changeParentNodeOfGivenNode(
            @PathVariable("id") long id,
            @PathVariable("parentId") long parentId) throws Exception {
        try {
            CompanyNode companyNode = companyNodeService.updateNodeParent(new CompanyNode(id), new CompanyNode(parentId));
            return ResponseEntity.ok(companyNodeResourceAssembler.toResource(companyNode));
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
