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

    public CompanyNode() {}

    public CompanyNode(Long id) {
        this.id = id;
    }

    public CompanyNode(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyNode)) return false;
        CompanyNode companyNode = (CompanyNode) o;
        return id != null && id.equals(companyNode.id);
    }

    @Override
    public int hashCode() {
        return 15;
    }
}
