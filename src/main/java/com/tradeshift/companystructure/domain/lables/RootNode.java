package com.tradeshift.companystructure.domain.lables;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "RootNode")
public class RootNode extends CompanyNode {

    public RootNode(){}

    public RootNode(Long id){
        super(id);
    }
}
