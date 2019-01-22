package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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
@Service
public class CompanyNodeValidationImpl implements CompanyNodeValidation {

    @Autowired
    private CompanyNodeRepository companyNodeRepository;

    /**
     * This method is used to check that given node
     * is not root node. if given node be root node
     * exception thrown.
     *
     * @param companyNode  This parameter specify given company node.
     */
    @Override
    public void checkNodeIsNotRootNode(CompanyNode companyNode) throws Exception {

        if (companyNode == null)
            throw new Exception("There is problem with node");
        CompanyNode rootNode = this.companyNodeRepository.findRootNode();
        if (rootNode == null)
            throw new Exception("There is problem,check with admin");
        if (companyNode.getId().equals(rootNode.getId()))
            throw new Exception("node can not be root node");
    }

    /**
     * This method is used to check that given node
     * is exist in database. if given node is not exist
     * exception thrown.
     *
     * @param companyNode  This parameter specify given company node.
     */
    @Override
    public void checkNodeIsExist(CompanyNode companyNode) throws Exception {

        if (companyNode == null)
            throw new Exception("There is problem with node");
        CompanyNode node = this.companyNodeRepository.findById(companyNode.getId(), 0);
        if (node == null)
            throw new Exception("node is not exist");
    }

}
