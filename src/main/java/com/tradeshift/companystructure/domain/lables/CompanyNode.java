package com.tradeshift.companystructure.domain.lables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;
import java.util.ArrayList;
import java.util.List;

@NodeEntity(label = "CompanyNode")
public class CompanyNode  {

    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Index
    @Property(name = "Name")
    private String name;
    @Relationship(type = "rel")
    @JsonIgnore
    private List<CompanyNode> childrenNodes = new ArrayList<>();
    @Relationship(type = "rel", direction = Relationship.INCOMING)
    @JsonIgnore
    private CompanyNode parentNode;
    @Transient
    private RootNode root;
    @Transient
    private Long height;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CompanyNode> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(List<CompanyNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public CompanyNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(CompanyNode parentNode) {
        this.parentNode = parentNode;
    }

    public RootNode getRoot() {
        return root;
    }

    public void setRoot(RootNode root) {
        this.root = root;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }
}
