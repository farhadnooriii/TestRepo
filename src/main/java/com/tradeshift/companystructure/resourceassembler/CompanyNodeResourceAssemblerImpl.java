package com.tradeshift.companystructure.resourceassembler;

import com.tradeshift.companystructure.constants.CompanyNodePathMap;
import com.tradeshift.companystructure.controllers.CompanyNodeController;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
public class CompanyNodeResourceAssemblerImpl implements CompanyNodeResourceAssembler {

    @Override
    public Resource<CompanyNode> toResource(CompanyNode companyNode) {
        Resource<CompanyNode> companyNodeResource = new Resource<>(companyNode);
        try {
            companyNodeResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CompanyNodeController.class).one(companyNode.getId())).withSelfRel());
            companyNodeResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CompanyNodeController.class).getAllChildrenOfGivenNode(companyNode.getId())).withRel("children"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companyNodeResource;
    }

    @Override
    public Resources<Resource<CompanyNode>> toResources(List<Resource<CompanyNode>> companyNodes) {
        Resources<Resource<CompanyNode>> resources = new Resources<>(companyNodes);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, CompanyNodePathMap.SELF));
        return resources;
    }


//    @Override
//    public Resources<CompanyNode> toResource(List<CompanyNode> companyNodes) {
//        Resources<CompanyNode> companyNodeResources = new Resources<>(companyNodes);
//        try {
//            for()
//            companyNodeResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CompanyNodeController.class).one(companyNode.getId())).withSelfRel());
//            companyNodeResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CompanyNodeController.class).getAllChildrenOfGivenNode(companyNode.getId())).withRel("children"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return companyNodeResource;
//    }
}
