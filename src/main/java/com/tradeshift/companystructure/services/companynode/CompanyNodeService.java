package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyNodeService {

    CompanyNode one(CompanyNode companyNode) throws NodeNotFoundException;
    CompanyNode getParent(CompanyNode companyNode) throws NodeNotFoundException;
    RootNode getRoot() throws Exception;
    List<CompanyNode> getChildren(CompanyNode companyNode) throws Exception;
    CompanyNode updateNodeParent(CompanyNode companyNode, CompanyNode parentNode) throws Exception;
    Long getHeight(Long nodeId) throws Exception;
    List<CompanyNode> getChildrenWithHeightAndRoot(CompanyNode companyNode) throws Exception;
}
