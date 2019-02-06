package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    private final CompanyNodeRepositorySDN companyNodeRepositorySDN;

    public CompanyNodeValidationImpl(final CompanyNodeRepositorySDN companyNodeRepositorySDN) {
        this.companyNodeRepositorySDN = companyNodeRepositorySDN;
    }

    /**
     * This method is used to check that given node
     * is not root node. if given node be root node
     * exception thrown.
     *
     * @param companyNode This parameter specify given company node.
     */
    @Override
    public void checkNodeIsNotRootNode(CompanyNode companyNode) throws Exception {
        RootNode rootNode = this.companyNodeRepositorySDN.findRootNode()
                .orElseThrow(()-> new NodeNotFoundException(RootNode.class, "Root Node Not Fount"));
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
        Optional<CompanyNode> node = this.companyNodeRepositorySDN.findById(companyNode.getId(), 0);
        node.orElseThrow(() -> new NodeNotFoundException(CompanyNode.class));
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
