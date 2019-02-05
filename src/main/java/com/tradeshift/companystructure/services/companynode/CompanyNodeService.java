package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyNodeService {

    CompanyNode one(CompanyNode companyNode) throws NodeNotFoundException;
    List<CompanyNode> getAllChildren(CompanyNode companyNode) throws Exception;
    CompanyNode updateNodeParent(CompanyNode companyNode, CompanyNode parentNode) throws Exception;
    Long getHeightOfNode(Long nodeId) throws Exception;
    List<CompanyNode> getAllChildrenWithHeightAndRoot(CompanyNode companyNode) throws Exception;
}
