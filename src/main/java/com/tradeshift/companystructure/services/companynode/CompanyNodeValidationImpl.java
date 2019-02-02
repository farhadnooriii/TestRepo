package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepository;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h1> CompanyNodeValidationImpl </h1>
 * This class is a implementation of CompanyNodeValidation
 * interface. main tasks are serve validation of business rules
 * of Company Node to other parts.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-09
 */
@Component
public class CompanyNodeValidationImpl implements CompanyNodeValidation {

    @Autowired
    private CompanyNodeRepository companyNodeRepository;

    /**
     * This method is used to check that given node
     * is not root node. if given node be root node
     * exception thrown.
     *
     * @param companyNode This parameter specify given company node.
     */
    @Override
    public void checkNodeIsNotRootNode(CompanyNode companyNode) throws Exception {
        this.checkInputNodeIsNotNull(companyNode);
        CompanyNode rootNode = this.companyNodeRepository.findRootNode();
        if (companyNode.getId().equals(rootNode.getId()))
            throw new Exception("node can not be root node");
    }

    /**
     * This method is used to check that given node
     * is exist in database. if given node is not exist
     * exception thrown.
     *
     * @param companyNode This parameter specify given company node.
     */
    @Override
    public void checkNodeIsExist(CompanyNode companyNode) throws Exception {
        this.checkInputNodeIsNotNull(companyNode);
        CompanyNode node = this.companyNodeRepository.findById(companyNode.getId(), 0);
        if (node == null)
            throw new NodeNotFoundException(CompanyNode.class, "id", companyNode.getId().toString());
    }

    /**
     * This method is used to check that given node
     * is not null.
     *
     * @param companyNode This parameter specify given company node.
     */
    @Override
    public void checkInputNodeIsNotNull(CompanyNode companyNode) {
        if (companyNode == null || companyNode.getId() == null)
            throw new NullPointerException("company node or company node id is null");

    }

}
