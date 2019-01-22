package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyNodeService {

    List<CompanyNode> getAllChildren(CompanyNode companyNode) throws Exception;
    CompanyNode updateNodeParent(CompanyNode companyNode, CompanyNode parentNode) throws Exception;
}
