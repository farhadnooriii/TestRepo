package com.tradeshift.companystructure.repositories.seed;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyNodeSeederImpl implements CompanyNodeSeeder {

    private final CompanyNodeRepositorySDN companyNodeRepositorySDN;

    public CompanyNodeSeederImpl(final CompanyNodeRepositorySDN companyNodeRepositorySDN){
        this.companyNodeRepositorySDN = companyNodeRepositorySDN;
    }
    /**
     * This method is used to seed database with
     * initial data.
     *
     */
    @Override
    public CompanyNode setupWithInitialData() {

//        CompanyNodeRepository companyNodeRepository = new CompanyNodeRepositoryImpl();
//        companyNodeRepository.deleteAll();

        RootNode ceoNode = new RootNode();
        ceoNode.setName("CEO");

        CompanyNode techOpsNode = new CompanyNode();
        techOpsNode.setName("Tech Ops");
        CompanyNode rAnddNode = new CompanyNode();
        rAnddNode.setName("R & D");
        CompanyNode gamificationNode = new CompanyNode();
        gamificationNode.setName("Gamification");
        gamificationNode.setParentNode(techOpsNode);
        rAnddNode.setParentNode(techOpsNode);
        techOpsNode.getChildrenNodes().add(gamificationNode);
        techOpsNode.getChildrenNodes().add(rAnddNode);
        techOpsNode.setParentNode(ceoNode);
        ceoNode.getChildrenNodes().add(techOpsNode);

        CompanyNode gAndaNode = new CompanyNode();
        gAndaNode.setName("G & A");
        CompanyNode hrNode = new CompanyNode();
        hrNode.setName("HR");
        CompanyNode adminNode = new CompanyNode();
        adminNode.setName("Admin");
        CompanyNode office3 = new CompanyNode();
        office3.setName("Office3");
        CompanyNode office4 = new CompanyNode();
        office4.setName("Office4");
        office3.setParentNode(hrNode);
        office4.setParentNode(hrNode);
        adminNode.setParentNode(gAndaNode);
        hrNode.setParentNode(gAndaNode);
        hrNode.getChildrenNodes().add(office3);
        hrNode.getChildrenNodes().add(office4);
        gAndaNode.getChildrenNodes().add(adminNode);
        gAndaNode.getChildrenNodes().add(hrNode);
        gAndaNode.setParentNode(ceoNode);
        ceoNode.getChildrenNodes().add(gAndaNode);

        CompanyNode salesAndMarketingNode = new CompanyNode();
        salesAndMarketingNode.setName("Sales And Marketing");
        CompanyNode salesNode = new CompanyNode();
        salesNode.setName("Sales");
        CompanyNode corporateMarketingNode = new CompanyNode();
        corporateMarketingNode.setName("Corporate Marketing");
        CompanyNode productMarketingNode = new CompanyNode();
        productMarketingNode.setName("Product Marketing");
        CompanyNode office1 = new CompanyNode();
        office1.setName("Office1");
        CompanyNode office2 = new CompanyNode();
        office2.setName("Office2");
        productMarketingNode.setParentNode(salesAndMarketingNode);
        productMarketingNode.getChildrenNodes().add(office1);
        productMarketingNode.getChildrenNodes().add(office2);
        office1.setParentNode(productMarketingNode);
        office2.setParentNode(productMarketingNode);
        corporateMarketingNode.setParentNode(salesAndMarketingNode);
        salesNode.setParentNode(salesAndMarketingNode);
        salesAndMarketingNode.getChildrenNodes().add(productMarketingNode);
        salesAndMarketingNode.getChildrenNodes().add(corporateMarketingNode);
        salesAndMarketingNode.getChildrenNodes().add(salesNode);
        salesAndMarketingNode.setParentNode(ceoNode);
        ceoNode.getChildrenNodes().add(salesAndMarketingNode);

        CompanyNode financeNode = new CompanyNode();
        financeNode.setName("Finance");
        financeNode.setParentNode(ceoNode);
        ceoNode.getChildrenNodes().add(financeNode);

//        companyNodeRepository.insert(ceoNode, 3);
        companyNodeRepositorySDN.save(ceoNode,3);
        return ceoNode;
    }
}
