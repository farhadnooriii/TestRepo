package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.springframework.stereotype.Service;

@Service
public interface CompanyNodeValidation  {

    void checkNodeIsNotRootNode(CompanyNode companyNode) throws Exception;
    void checkNodeIsExist(CompanyNode companyNode) throws Exception;
    void checkInputNodeIsNotNull(CompanyNode companyNode);
}
