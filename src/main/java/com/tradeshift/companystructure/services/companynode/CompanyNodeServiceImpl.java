package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import org.springframework.stereotype.Service;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;

import java.util.List;
import java.util.Optional;


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


    private final CompanyNodeValidation companyNodeValidation;

    private final CompanyNodeRepositorySDN companyNodeRepositorySDN;

    public CompanyNodeServiceImpl(final CompanyNodeValidation companyNodeValidation,
                                  final CompanyNodeRepositorySDN companyNodeRepositorySDN) {
        this.companyNodeValidation = companyNodeValidation;
        this.companyNodeRepositorySDN = companyNodeRepositorySDN;
    }

    @Override
    public CompanyNode one(CompanyNode companyNode) throws NodeNotFoundException {

        return this.companyNodeRepositorySDN.findById(companyNode.getId())
                .orElseThrow(() -> new NodeNotFoundException(CompanyNode.class));
    }

    @Override
    public CompanyNode getParent(CompanyNode companyNode) throws NodeNotFoundException {

        return this.companyNodeRepositorySDN.findParentNodeOfGivenNode(companyNode.getId())
                .orElseThrow(() -> new NodeNotFoundException(CompanyNode.class, "id", companyNode.getId().toString()));
    }

    @Override
    public RootNode getRoot() throws Exception {

        return this.companyNodeRepositorySDN.findRootNode()
                .orElseThrow(()-> new NodeNotFoundException(RootNode.class,"Root Node Not Found"));
    }

    /**
     * This method is used to get all children of
     * given company node.
     *
     * @param companyNode This parameter specify CompanyNode.
     * @return List<CompanyNode></CompanyNode> This return all children
     */
    @Override
    public List<CompanyNode> getChildren(CompanyNode companyNode) throws Exception {

        this.companyNodeValidation.checkInputNodeIsNotNull(companyNode);
        this.companyNodeValidation.checkNodeIsExist(companyNode);
        return companyNodeRepositorySDN.findAllChildrenOfGivenNode(companyNode.getId());
    }

    @Override
    public Long getHeight(Long nodeId) throws Exception {

        RootNode rootNode = this.getRoot();
        return this.companyNodeRepositorySDN.findHeightBetweenTwoNode(nodeId, rootNode.getId());
    }

    @Override
    public List<CompanyNode> getChildrenWithHeightAndRoot(CompanyNode companyNode) throws Exception {

        List<CompanyNode> children = this.getChildren(companyNode);
        RootNode rootNode = this.getRoot();
        if (children.isEmpty())
            return children;
        Long height = this.companyNodeRepositorySDN.findHeightBetweenTwoNode(children.get(0).getId(), rootNode.getId());
        children.forEach(child ->
                {
                    child.setRoot(rootNode);
                    child.setHeight(height);
                }

        );
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

        this.companyNodeValidation.checkInputNodeIsNotNull(companyNode);
        this.companyNodeValidation.checkInputNodeIsNotNull(parentNode);
        this.companyNodeValidation.checkNodeIsExist(companyNode);
        this.companyNodeValidation.checkNodeIsExist(parentNode);
        this.companyNodeValidation.checkNodeIsNotRootNode(companyNode);
        return companyNodeRepositorySDN.updateNodeParent(companyNode.getId(), parentNode.getId());
    }

    private void setRootAndHeight(CompanyNode companyNode) throws Exception {
        if (companyNode != null) {
            companyNode.setRoot(this.getRoot());
            Long height = this.companyNodeRepositorySDN.findHeightBetweenTwoNode(companyNode.getId(), companyNode.getRoot().getId());
            companyNode.setHeight(height);
        }
    }

}
