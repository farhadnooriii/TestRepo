package com.tradeshift.companystructure.viewmodels.companynode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.springframework.hateoas.ResourceSupport;

public class CompanyNodeVM extends ResourceSupport {

    private final CompanyNode companyNode;

    @JsonCreator
    public CompanyNodeVM(@JsonProperty("companyNode")final CompanyNode companyNode){
        this.companyNode = companyNode;
    }

    public CompanyNode getCompanyNode() {
        return companyNode;
    }
}
