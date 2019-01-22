package com.tradeshift.companystructure.repositories;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepository;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositoryImpl;
import com.tradeshift.companystructure.repositories.seed.CompanyNodeSeeder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyNodeRepositoryIntegrationTest {

    private CompanyNodeRepository companyNodeRepository;

    public CompanyNodeRepositoryIntegrationTest(){
        companyNodeRepository = new CompanyNodeRepositoryImpl();
    }

    @BeforeClass
    public static void run() {
       CompanyNodeSeeder.setupWithInitialData();
    }

    @Test
    public void findAll_checkAllNodesCount_beSixteen() {

        Collection<CompanyNode> companyNodes = companyNodeRepository.findAll(1);
        Assert.assertEquals(companyNodes.size(), 16);
    }

    @Test
    public void findAllWithHeightAndRoot_checkEveryNodeHaveRoot_notToBeNull() throws Exception {

        Collection<CompanyNode> companyNodes = companyNodeRepository.findAllWithHeightAndRoot(0);
        companyNodes.forEach(c -> Assert.assertNotNull(c.getRoot()));
    }

    @Test
    public void findAllWithHeightAndRoot_checkEveryNodeExceptRootNode_haveHeightMoreThanZero() throws Exception {

        RootNode rootNode = this.companyNodeRepository.findRootNode();
        Collection<CompanyNode> companyNodes = companyNodeRepository.findAllWithHeightAndRoot(0);
        for (CompanyNode companyNode : companyNodes)
            if (!companyNode.getId().equals(rootNode.getId())) {
                if (companyNode.getHeight() > 0)
                    Assert.assertTrue(true);
                else
                    Assert.fail();
            }
    }

    @Test
    public void findById_checkByName_beEqual() {

        List<CompanyNode> companyNodeList = companyNodeRepository.findAll(0);
        CompanyNode companyNode = companyNodeRepository.findById(companyNodeList.get(0).getId(), 0);
        Assert.assertEquals(companyNodeList.get(0).getName(), companyNode.getName());
    }

    @Test
    public void findByIdWithHeightAndRoot_checkHaveRoot_notToBeNull() throws Exception {

        List<CompanyNode> companyNodeList = companyNodeRepository.findAll(0);
        CompanyNode companyNode = companyNodeRepository.findByIdWithHeightAndRoot(companyNodeList.get(4).getId(), 0);
        Assert.assertNotNull(companyNode.getRoot());
    }

    @Test
    public void findByIdWithHeightAndRoot_checkHeightValue_beEqual() throws Exception {

        List<CompanyNode> companyNodeList = companyNodeRepository.findAllWithHeightAndRoot(0);
        CompanyNode companyNode = companyNodeRepository.findByIdWithHeightAndRoot(companyNodeList.get(4).getId(), 0);
        Assert.assertEquals(companyNodeList.get(4).getHeight(), companyNode.getHeight());
    }

    @Test
    public void findByName_checkIsSales_beEqual() {

        CompanyNode salesNode = companyNodeRepository.findByName("Sales", 0);
        Assert.assertEquals(salesNode.getName(), "Sales");
    }

    @Test
    public void findByNameWithHeightAndRoot_checkRoot_notToBeNull() throws Exception {

        CompanyNode salesNode = companyNodeRepository.findByNameWithHeightAndRoot("Sales", 0);
        Assert.assertNotNull(salesNode.getRoot());
    }

    @Test
    public void findAllChildrenOfGivenNode_ceoAsGivenNode_oneLevelChildren_childrenSizeBeFour() {

        CompanyNode ceoNode = companyNodeRepository.findByName("CEO", 0);
        List<CompanyNode> ceoChildrenNodes = companyNodeRepository.findAllChildrenOfGivenNode(ceoNode.getId());
        Assert.assertEquals(ceoChildrenNodes.size(), 4);
    }

    @Test
    public void findAllChildrenOfGivenNode_hrAsGivenNode_oneLevelChildren_checkByChildrenName() {

        CompanyNode hrNode = companyNodeRepository.findByName("HR", 0);
        List<CompanyNode> hrChildrenNodes = companyNodeRepository.findAllChildrenOfGivenNode(hrNode.getId());
        for (CompanyNode childNode : hrChildrenNodes) {
            if (childNode.getName().equals("Office3") || childNode.getName().equals("Office4"))
                Assert.assertTrue(true);
            else
                Assert.fail();
        }
    }

    @Test
    public void findAllChildrenOfGivenNodeWithHeightAndRoot_ceoNode_checkChildrenRoot_notToBeNull() throws Exception {

        CompanyNode ceoNode = companyNodeRepository.findByName("CEO", 0);
        List<CompanyNode> ceoChildrenNodes = companyNodeRepository.findAllChildrenOfGivenNodeWithHeightAndRoot(ceoNode.getId());
        ceoChildrenNodes.forEach(c -> Assert.assertNotNull(c.getRoot()));
    }

    @Test
    public void findHeightOfNode_ceoHeight_beZero() throws Exception {

        CompanyNode ceoNode = companyNodeRepository.findByName("CEO", 0);
        long height = companyNodeRepository.findHeightOfNode(ceoNode.getId());
        Assert.assertEquals(height, 0);
    }

    @Test
    public void findHeightOfNode_hrHeight_beTwo() throws Exception {

        CompanyNode hrNode = companyNodeRepository.findByName("HR", 0);
        long height = companyNodeRepository.findHeightOfNode(hrNode.getId());
        Assert.assertEquals(height, 2);
    }

    @Test
    public void findParentNodeOfGivenNode_hrNode_beGAndA() {

        CompanyNode gAndANode = companyNodeRepository.findByName("G & A", 0);
        CompanyNode hrNode = companyNodeRepository.findByName("HR", 0);
        CompanyNode hrParent = companyNodeRepository.findParentNodeOfGivenNode(hrNode.getId());
        Assert.assertEquals(hrParent.getId(), gAndANode.getId());
    }

    @Test
    public void findParentNodeOfGivenNode_rootNode_beNull() throws Exception {

        CompanyNode rootNode = companyNodeRepository.findRootNode();
        CompanyNode rootParent = companyNodeRepository.findParentNodeOfGivenNode(rootNode.getId());
        Assert.assertNull(rootParent);
    }


    @Test
    public void findRootNode_checkCEO_beTrue() throws Exception {

        CompanyNode rootNode = companyNodeRepository.findRootNode();
        Assert.assertEquals(rootNode.getName(), "CEO");
    }

    @Test
    public void updateNodeParent_changeHRParent_newParentBeSales() {

        CompanyNode hrNode = companyNodeRepository.findByName("HR", 0);
        CompanyNode salesNode = companyNodeRepository.findByName("Sales", 0);
        companyNodeRepository.updateNodeParent(hrNode.getId(), salesNode.getId());
        CompanyNode hrNodeParent = companyNodeRepository.findParentNodeOfGivenNode(hrNode.getId());
        Assert.assertEquals(hrNodeParent.getId(), salesNode.getId());
    }

}
