package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import com.tradeshift.companystructure.repositories.seed.CompanyNodeSeeder;
import org.springframework.stereotype.Service;

@Service
public class CompanyNodeImportServiceImpl implements CompanyNodeImportService {


    private CompanyNodeRepositorySDN companyNodeRepositorySDN;
    private CompanyNodeSeeder companyNodeSeeder;

    public CompanyNodeImportServiceImpl(CompanyNodeRepositorySDN companyNodeRepositorySDN,CompanyNodeSeeder companyNodeSeeder) {
        this.companyNodeRepositorySDN = companyNodeRepositorySDN;
        this.companyNodeSeeder = companyNodeSeeder;
    }

    @Override
    public void clearDatabase() {
        this.companyNodeRepositorySDN.deleteAll();
    }

    @Override
    public CompanyNode importPreData() {
       return companyNodeSeeder.setupWithInitialData();
    }
}
