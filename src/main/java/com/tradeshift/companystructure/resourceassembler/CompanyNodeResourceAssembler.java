package com.tradeshift.companystructure.resourceassembler;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;

import java.util.List;

public interface CompanyNodeResourceAssembler extends ResourceAssembler<CompanyNode, Resource<CompanyNode>> {

    Resources<Resource<CompanyNode>> toResources(List<Resource<CompanyNode>> companyNodes);
}
