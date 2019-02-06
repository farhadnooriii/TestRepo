package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.constants.companynode.CompanyNodeHateoasTag;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import com.tradeshift.companystructure.resourceassembler.CompanyNodeResourceAssembler;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class CompanyNodeControllerTest {

    @Mock
    private CompanyNodeService companyNodeServiceMock;
    @Mock
    private CompanyNodeResourceAssembler companyNodeResourceAssemblerMock;
    @InjectMocks
    private CompanyNodeController companyNodeController;

    private List<CompanyNode> children;
    private List<Resource<CompanyNode>> companyResources;

    @Before
    public void init() {
        children = new ArrayList<>();
        children.add(new CompanyNode(1L, "child1"));
        children.add(new CompanyNode(2L, "child2"));
        children.add(new CompanyNode(3L, "child3"));
        children.add(new CompanyNode(4L, "child4"));

        companyResources = new ArrayList<>();
        companyResources.add(new Resource<>(children.get(0)));
        companyResources.add(new Resource<>(children.get(1)));
        companyResources.add(new Resource<>(children.get(2)));
        companyResources.add(new Resource<>(children.get(3)));
    }

    @Test
    public void checkCompanyNodeController_isLoaded() {
        assertThat(this.companyNodeController, is(notNullValue()));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkChildrenList_isNotEmpty() throws Exception {

        CompanyNode companyNode = new CompanyNode(12L);
        BDDMockito.given(companyNodeServiceMock.getChildrenWithHeightAndRoot(companyNode)).willReturn(children);
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(0))).willReturn(companyResources.get(0));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(1))).willReturn(companyResources.get(1));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(2))).willReturn(companyResources.get(2));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(3))).willReturn(companyResources.get(3));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResources(companyResources)).willReturn(new Resources<>(companyResources));
        ResponseEntity<Resources<Resource<CompanyNode>>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody().getContent(), is(not(Matchers.empty())));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkChildrenListSize_beEquals() throws Exception {

        CompanyNode companyNode = new CompanyNode(12L);
        BDDMockito.given(companyNodeServiceMock.getChildrenWithHeightAndRoot(companyNode)).willReturn(children);
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(0))).willReturn(companyResources.get(0));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(1))).willReturn(companyResources.get(1));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(2))).willReturn(companyResources.get(2));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(3))).willReturn(companyResources.get(3));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResources(companyResources)).willReturn(new Resources<>(companyResources));
        ResponseEntity<Resources<Resource<CompanyNode>>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody().getContent(), hasSize(this.children.size()));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkAllChildren_isExist() throws Exception {

        CompanyNode companyNode = new CompanyNode(12L);
        BDDMockito.given(companyNodeServiceMock.getChildrenWithHeightAndRoot(companyNode)).willReturn(children);
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(0))).willReturn(companyResources.get(0));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(1))).willReturn(companyResources.get(1));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(2))).willReturn(companyResources.get(2));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResource(children.get(3))).willReturn(companyResources.get(3));
        BDDMockito.given(companyNodeResourceAssemblerMock.toResources(companyResources)).willReturn(new Resources<>(companyResources));
        ResponseEntity<Resources<Resource<CompanyNode>>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(new ArrayList<>(companyNodes.getBody().getContent()), is(new ArrayList(companyResources)));

    }

    @Test(expected = NodeNotFoundException.class)
    public void getAllChildrenOfGivenNode_companyNodeService_thrownException() throws Exception {

        CompanyNode companyNode = new CompanyNode(10L);
        BDDMockito.given(companyNodeServiceMock.getChildrenWithHeightAndRoot(companyNode)).willThrow(NodeNotFoundException.class);
        companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
    }




}
