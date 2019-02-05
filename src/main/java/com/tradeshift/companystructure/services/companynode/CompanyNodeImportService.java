package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;

public interface CompanyNodeImportService {

    void clearDatabase();
    CompanyNode importPreData();
}
