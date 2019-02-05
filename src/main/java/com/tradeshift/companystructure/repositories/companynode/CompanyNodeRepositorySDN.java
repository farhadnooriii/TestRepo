package com.tradeshift.companystructure.repositories.companynode;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyNodeRepositorySDN extends Neo4jRepository<CompanyNode,Long> {

    @Query("MATCH (parentNode:CompanyNode)-[:rel]->(child:CompanyNode) WHERE ID(parentNode) = {0} RETURN child")
    List<CompanyNode> findAllChildrenOfGivenNode(Long nodeId);
    @Query("MATCH (parent:CompanyNode)-[r:rel]->(companyNode:CompanyNode) WHERE ID(companyNode) = {0} WITH companyNode,r DELETE r WITH companyNode MATCH (newParentNode:CompanyNode) WHERE ID(newParentNode) = {1} MERGE (newParentNode)-[:rel]->(companyNode) RETURN companyNode")
    CompanyNode updateNodeParent(Long nodeId,Long newParentId);
    @Query("MATCH (rootNode:RootNode) RETURN rootNode LIMIT 1")
    RootNode findRootNode() throws Exception;
    @Query("MATCH (parentNode:CompanyNode)-[:rel]->(child:CompanyNode) WHERE ID(child) = {0} RETURN parentNode")
    Optional<CompanyNode> findParentNodeOfGivenNode(Long nodeId);
    @Query("MATCH (companyNode:CompanyNode),(root:CompanyNode), p = shortestPath((companyNode)-[*]-(root)) WHERE ID(companyNode) = {0} AND ID(root) = {1} RETURN length(p) AS height")
    Long findHeightBetweenTwoNode(Long firstNodeId, Long secondNodeId);

}
