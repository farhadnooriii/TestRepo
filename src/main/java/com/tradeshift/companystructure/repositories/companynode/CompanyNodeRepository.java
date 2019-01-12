package com.tradeshift.companystructure.repositories.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Configuration
public interface CompanyNodeRepository {

    //region Query

    List<CompanyNode> findAll(int depth);
    List<CompanyNode> findAllWithHeightAndRoot(int depth) throws Exception;
    CompanyNode findById(Long id,int depth);
    CompanyNode findByIdWithHeightAndRoot(Long id, int depth) throws Exception;
    CompanyNode findByName(String name, int depth);
    CompanyNode findByNameWithHeightAndRoot(String name, int depth) throws Exception;
    List<CompanyNode> findAllChildrenOfGivenNode(Long nodeId);
    List<CompanyNode> findAllChildrenOfGivenNodeWithHeightAndRoot(Long nodeId) throws Exception;
    CompanyNode findParentNodeOfGivenNode(Long nodeId);
    RootNode findRootNode() throws Exception;
    Long findHeightOfNode(Long nodeId, Long rootNodeId) throws Exception;
    Long findHeightOfNode(Long nodeId) throws Exception;

    //endregion

    //region CRUD

    void updateNodeParent(Long nodeId,Long newParentId);
    void insert(CompanyNode companyNode,int depth);
    void deleteAll();

    //endregion
}
