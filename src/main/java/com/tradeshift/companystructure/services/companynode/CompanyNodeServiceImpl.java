package com.tradeshift.companystructure.services.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CompanyNodeRepository companyNodeRepository;
    @Autowired
    private CompanyNodeValidation companyNodeValidation;

    /**
     * This method is used to get all children of
     * given company node.
     *
     * @param companyNode This parameter specify CompanyNode.
     * @return List<CompanyNode></CompanyNode> This return all children
     */
    @Override
    public List<CompanyNode> getAllChildren(CompanyNode companyNode) throws Exception {
        this.companyNodeValidation.checkInputNodeIsNotNull(companyNode);
        this.companyNodeValidation.checkNodeIsExist(companyNode);
        return companyNodeRepository.findAllChildrenOfGivenNodeWithHeightAndRoot(companyNode.getId());
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
        return companyNodeRepository.updateNodeParent(companyNode.getId(), parentNode.getId());
    }

}
