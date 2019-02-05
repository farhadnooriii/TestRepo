package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <h1> CompanyNodeServiceImpl </h1>
 * This class is a implementation of CompanyNodeService
 * interface. main tasks are serve business rules of
 * Company Node to other parts.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-09
 */
@Service
public class CompanyNodeServiceImpl implements CompanyNodeService {


    private CompanyNodeValidation companyNodeValidation;

    private CompanyNodeRepositorySDN companyNodeRepositorySDN;

    public CompanyNodeServiceImpl(CompanyNodeValidation companyNodeValidation, CompanyNodeRepositorySDN companyNodeRepositorySDN) {
        this.companyNodeValidation = companyNodeValidation;
        this.companyNodeRepositorySDN = companyNodeRepositorySDN;
    }

    /**
     * This method is used to get all children of
     * given company node.
     *
     * @param companyNode This parameter specify CompanyNode.
     * @return List<CompanyNode></CompanyNode> This return all children
     */
    @Override
    public List<CompanyNode> getAllChildren(CompanyNode companyNode) throws Exception {
        this.companyNodeValidation.checkNodeIsExist(companyNode);
        return companyNodeRepositorySDN.findAllChildrenOfGivenNode(companyNode.getId());
    }

    @Override
    public Long getHeightOfNode(Long nodeId) throws Exception {

        RootNode rootNode = this.companyNodeRepositorySDN.findRootNode();
        return this.companyNodeRepositorySDN.findHeightBetweenTwoNode(nodeId, rootNode.getId());
    }

    @Override
    public List<CompanyNode> getAllChildrenWithHeightAndRoot(CompanyNode companyNode) throws Exception {

        List<CompanyNode> children = this.getAllChildren(companyNode);
        RootNode rootNode = this.companyNodeRepositorySDN.findRootNode();
        if (children.isEmpty())
            return children;
        Long height = this.companyNodeRepositorySDN.findHeightBetweenTwoNode(children.get(0).getId(), rootNode.getId());
        for (CompanyNode child : children) {
            child.setRoot(rootNode);
            child.setHeight(height);
        }
        return children;
    }

    /**
     * This method is used to update parent of given
     * node.
     *
     * @param companyNode This parameter specify CompanyNode.
     * @param parentNode  This parameter specify new parent node.
     */
    @Override
    public CompanyNode updateNodeParent(CompanyNode companyNode, CompanyNode parentNode) throws Exception {
        this.companyNodeValidation.checkNodeIsExist(companyNode);
        this.companyNodeValidation.checkNodeIsExist(parentNode);
        this.companyNodeValidation.checkNodeIsNotRootNode(companyNode);
        return companyNodeRepositorySDN.updateNodeParent(companyNode.getId(), parentNode.getId());
    }



//    private void setRootAndHeight(CompanyNode companyNode) throws Exception {
//        if(companyNode!=null) {
//            companyNode.setRoot(this.companyNodeRepositorySDN.findRootNode());
//            Long height = this.findHeightOfNode(companyNode.getId(), companyNode.getRoot().getId());
//            companyNode.setHeight(height);
//        }
//    }

}
