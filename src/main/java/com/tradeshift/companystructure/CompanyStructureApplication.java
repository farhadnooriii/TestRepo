package com.tradeshift.companystructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = {"com.tradeshift.companystructure"})
@EnableNeo4jRepositories("com.tradeshift.companystructure.repositories.companynode")
public class CompanyStructureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyStructureApplication.class, args);

	}

}

