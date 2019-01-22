package com.tradeshift.companystructure.repositories.companynode;

import com.google.common.collect.Lists;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.config.NeoConfig;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <h1> CompanyNodeRepositoryImpl </h1>
 * This class is a implementation of CompanyNodeRepository
 * interface. main tasks are crud and query operation on
 * database.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-08
 */
@Repository
public class CompanyNodeRepositoryImpl implements CompanyNodeRepository {

    //region Global

    Configuration configuration = new Configuration.Builder().uri(NeoConfig.SERVER_URI).connectionPoolSize(1500).credentials(NeoConfig.SERVER_USERNAME, NeoConfig.SERVER_PASSWORD).build();
    SessionFactory sessionFactory = new SessionFactory(configuration, NeoConfig.DOMAIN_PACKAGE);
    Session session = sessionFactory.openSession();

    //endregion

    //region Constructor
//    @Autowired
//    public CompanyNodeRepositoryImpl() {
//        Configuration configuration = new Configuration.Builder().uri(NeoConfig.SERVER_URI).connectionPoolSize(1500).credentials(NeoConfig.SERVER_USERNAME, NeoConfig.SERVER_PASSWORD).build();
//        SessionFactory sessionFactory = new SessionFactory(configuration, NeoConfig.DOMAIN_PACKAGE);
//        session = sessionFactory.openSession();
//    }

    //endregion

    //region Query

    /**
     * This method is used to load all nodes with out root and height by depth.
     *
     * @param depth This parameter specify level of load data in object
     * @return List<CompanyNode></CompanyNode> List of CompanyNode.
     */
    @Override
    public List<CompanyNode> findAll(int depth) {

        Collection<CompanyNode> companyNodes = this.session.loadAll(CompanyNode.class, depth);
        if (companyNodes == null || companyNodes.isEmpty())
            return null;
        return Lists.newArrayList(companyNodes);
    }

    /**
     * This method is used to load all nodes within root and height property by depth.
     *
     * @param depth This parameter specify level of load data in object.
     * @return List<CompanyNode></CompanyNode> List of CompanyNode.
     */
    @Override
    public List<CompanyNode> findAllWithHeightAndRoot(int depth) throws Exception {

        List<CompanyNode> companyNodes = this.findAll(depth);
        if (companyNodes != null && !companyNodes.isEmpty())
            for (CompanyNode companyNode : companyNodes)
                this.setRootAndHeight(companyNode);
        return companyNodes;
    }

    /**
     * This method is used to load node by id with out root and height property by depth.
     *
     * @param id    This parameter specify node id.
     * @param depth This parameter specify level of load data in object.
     * @return CompanyNode This return CompanyNode of this id.
     */
    @Override
    public CompanyNode findById(Long id, int depth) {

        return session.load(CompanyNode.class, id, depth);
    }

    /**
     * This method is used to load node by node id with root and height property by depth.
     *
     * @param id    This parameter specify node id.
     * @param depth This parameter specify level of load data in object.
     * @return CompanyNode This return CompanyNode of this id.
     */
    @Override
    public CompanyNode findByIdWithHeightAndRoot(Long id, int depth) throws Exception {

        CompanyNode companyNode = this.findById(id, depth);
        this.setRootAndHeight(companyNode);
        return companyNode;
    }

    /**
     * This method is used to load node by node name with out root and height property by depth.
     *
     * @param name  This parameter specify node name.
     * @param depth This parameter specify level of load data in object.
     * @return CompanyNode This return CompanyNode of this name.
     */
    @Override
    public CompanyNode findByName(String name, int depth) {

        Filters filters = new Filters();
        Filter nameFilter = new Filter("Name", ComparisonOperator.EQUALS, name);
        filters.add(nameFilter);
        Collection<CompanyNode> loadedNodeList = this.session.loadAll(CompanyNode.class, filters, depth);
        if (loadedNodeList == null || loadedNodeList.isEmpty())
            return null;
        return Lists.newArrayList(loadedNodeList).get(0);
    }

    /**
     * This method is used to load node by node name with root and height property by depth.
     *
     * @param name  This parameter specify node name.
     * @param depth This parameter specify level of load data in object.
     * @return CompanyNode This return CompanyNode of this name.
     */
    @Override
    public CompanyNode findByNameWithHeightAndRoot(String name, int depth) throws Exception {

        CompanyNode companyNode = this.findByName(name, depth);
        this.setRootAndHeight(companyNode);
        return companyNode;
    }

    /**
     * This method is used to load all children of given node id
     * as parent id without root and height property by depth.
     *
     * @param nodeId This parameter specify parent node id.
     * @return List<CompanyNode></CompanyNode> This return all children.
     */
    @Override
    public List<CompanyNode> findAllChildrenOfGivenNode(Long nodeId) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("nodeId", nodeId);
        String query = "MATCH (parentNode:CompanyNode)-[:rel]->(child:CompanyNode) WHERE ID(parentNode) = $nodeId RETURN child";
        Result result = this.session.query(query, params);
        if (result == null)
            return null;
        List<CompanyNode> children = new ArrayList<>();
        for (Map<String, Object> row : result) {
            if (row.get("child") != null) {
                children.add((CompanyNode) row.get("child"));
            }
        }
        return children;
    }

    /**
     * This method is used to load all children of given node id
     * as parent id with root and height property by depth.
     *
     * @param nodeId This parameter specify parent node id.
     * @return List<CompanyNode></CompanyNode> This return all children.
     */
    @Override
    public List<CompanyNode> findAllChildrenOfGivenNodeWithHeightAndRoot(Long nodeId) throws Exception {

        List<CompanyNode> companyNodeList = this.findAllChildrenOfGivenNode(nodeId);
        RootNode rootNode = this.findRootNode();
        if (companyNodeList == null || companyNodeList.isEmpty() || rootNode == null)
            return null;
        Long height = this.findHeightOfNode(companyNodeList.get(0).getId(), rootNode.getId());
        for (CompanyNode companyNode : companyNodeList) {
            companyNode.setRoot(rootNode);
            companyNode.setHeight(height);
        }
        return companyNodeList;
    }

    /**
     * This method is used to calculate height of given node.
     * this calculation is done with shortestPath between node
     * and root node and with the length of path the height is find. all this
     * done in pure cypher query.
     *
     * @param nodeId     This parameter specify parent node id.
     * @param rootNodeId This parameter specify root node id.
     * @return Long This return height of node.
     */
    @Override
    public Long findHeightOfNode(Long nodeId, Long rootNodeId) {

        Long height = null;
        if (nodeId.equals(rootNodeId))
            return 0L;
        Map<String, Object> params = new HashMap<>(2);
        params.put("nodeId", nodeId);
        params.put("rootId", rootNodeId);
        String query = "MATCH (companyNode:CompanyNode),(root:CompanyNode), p = shortestPath((companyNode)-[*]-(root)) WHERE ID(companyNode) = $nodeId AND ID(root) = $rootId RETURN length(p) AS height";
        Result result = this.session.query(query, params);
        if (result == null)
            return null;
        for (Map<String, Object> row : result) {
            if (row.get("height") != null) {
                height = (Long) row.get("height");
            }
        }
        return height;
    }

    /**
     * This method is used to calculate height of given node.
     * this calculation is done with shortestPath between node
     * and root node and with the length of path the height is find.
     * in this overload root node is find in method body.
     *
     * @param nodeId This parameter specify parent node id.
     * @return Long This return height of node.
     */
    @Override
    public Long findHeightOfNode(Long nodeId) throws Exception {

        RootNode rootNode = this.findRootNode();
        if (rootNode == null)
            return null;
        return this.findHeightOfNode(nodeId, rootNode.getId());
    }

    /**
     * This method is used to load parent of given node id
     * without root and height property by depth.this is done
     * with in pure cypher query.
     *
     * @param nodeId This parameter specify parent node id.
     * @return CompanyNode This return parent node.
     */
    @Override
    public CompanyNode findParentNodeOfGivenNode(Long nodeId) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("nodeId", nodeId);
        String query = "MATCH (parentNode:CompanyNode)-[:rel]->(child:CompanyNode) WHERE ID(child) = $nodeId RETURN parentNode";
        Result result = this.session.query(query, params);
        if (result == null)
            return null;
        CompanyNode parentNode = null;
        for (Map<String, Object> row : result) {
            if (row.get("parentNode") != null) {
                parentNode = (CompanyNode) row.get("parentNode");
            }
        }
        return parentNode;
    }

    /**
     * This method is used to find root node.this is
     * done with pure cypher query.
     *
     * @return RootNode This return root node.
     */
    @Override
    public RootNode findRootNode() throws Exception {

        Map<String, Object> params = new HashMap<>(1);
        params.put("limit", 1);
        String query = "MATCH (rootNode:RootNode) RETURN rootNode LIMIT $limit";
        Result result = this.session.query(query, params);
        if (result == null)
            throw new Exception("root node is not defined");
        List<RootNode> rootNodes = new ArrayList<>();
        for (Map<String, Object> row : result) {
            if (row.get("rootNode") != null) {
                rootNodes.add((RootNode) row.get("rootNode"));
            }
        }
        RootNode rootNode;
        if (!rootNodes.isEmpty()) {
            if (rootNodes.size() == 1)
                rootNode = rootNodes.get(0);
            else
                throw new Exception("more than one root node is defined");
        } else {
            throw new Exception("root node is not defined");
        }
        return rootNode;
    }

    //endregion

    //region CRUD

    /**
     * This method is used to change parent of
     * given node id.
     *
     * @param nodeId      This parameter specify node id.
     * @param newParentId This parameter specify new parent node id.
     *
     * @return CompanyNode , This return company node along updated parent
     */
    @Transactional
    @Override
    public CompanyNode updateNodeParent(Long nodeId, Long newParentId) {
        Configuration configuration = new Configuration.Builder().uri(NeoConfig.SERVER_URI).connectionPoolSize(1500).credentials(NeoConfig.SERVER_USERNAME, NeoConfig.SERVER_PASSWORD).build();
        SessionFactory sessionFactory = new SessionFactory(configuration, NeoConfig.DOMAIN_PACKAGE);
        session = sessionFactory.openSession();
        CompanyNode parentNode = this.findParentNodeOfGivenNode(nodeId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("parentId", parentNode.getId());
        params.put("nodeId", nodeId);
        params.put("newParentId", newParentId);
        String query = "MATCH (parent:CompanyNode)-[r:rel]->(companyNode:CompanyNode) WHERE ID(parent) = $parentId AND ID(companyNode) = $nodeId WITH companyNode,r DELETE r WITH companyNode MATCH (newParentNode:CompanyNode) WHERE ID(newParentNode) = $newParentId MERGE (newParentNode)-[:rel]->(companyNode) RETURN companyNode,newParentNode";
        Result result = this.session.query(query, params);
        CompanyNode companyNode = null;
        for (Map<String, Object> row : result) {
            if (row.get("companyNode") != null) {
                companyNode = (CompanyNode) row.get("companyNode");
            }
            if (row.get("newParentNode") != null) {
                companyNode.setParentNode((CompanyNode) row.get("newParentNode"));
            }
        }
        return companyNode;
    }

    /**
     * This method is used to create new node along children or parent.
     *
     * @param node  This parameter specify node with its parent or children.
     * @param depth This parameter specify depth.
     */
    @Transactional
    @Override
    public void insert(CompanyNode node, int depth) {
        this.session.save(node, depth);
    }

    /**
     * This method is used to delete all nodes.
     */
    @Transactional
    @Override
    public void deleteAll() {
        this.session.deleteAll(CompanyNode.class);
        this.session.deleteAll(RootNode.class);
    }

    //endregion

    //region Private

    private void setRootAndHeight(CompanyNode companyNode) throws Exception {

        companyNode.setRoot(this.findRootNode());
        Long height = this.findHeightOfNode(companyNode.getId(), companyNode.getRoot().getId());
        companyNode.setHeight(height);
    }

    //endregion

}
