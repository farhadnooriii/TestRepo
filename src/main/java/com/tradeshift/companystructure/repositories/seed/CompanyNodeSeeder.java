package com.tradeshift.companystructure.repositories.seed;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepository;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositoryImpl;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <h1> CompanyNodeSeeder </h1>
 * This class is used to seed data to neo4j database.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-10
 */
public interface CompanyNodeSeeder {

    /**
     * This method is used to seed database with
     * initial data.
     *
     */
     CompanyNode setupWithInitialData();
}
