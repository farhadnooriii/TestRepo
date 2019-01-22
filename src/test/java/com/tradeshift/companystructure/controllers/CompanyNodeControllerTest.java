package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.services.companynode.CompanyNodeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
    @InjectMocks
    private CompanyNodeController companyNodeController;
    private List<CompanyNode> children;

    @Before
    public void init() {
        children = new ArrayList<>();
        children.add(new CompanyNode(1L, "child1"));
        children.add(new CompanyNode(2L, "child2"));
        children.add(new CompanyNode(3L, "child3"));
        children.add(new CompanyNode(4L, "child4"));
    }

    @Test
    public void checkCompanyNodeController_isLoaded() {
        assertThat(this.companyNodeController, is(notNullValue()));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkChildrenList_isNotEmpty() throws Exception {

        CompanyNode companyNode = new CompanyNode(10L);
        BDDMockito.given(companyNodeServiceMock.getAllChildren(companyNode)).willReturn(children);
        ResponseEntity<List<CompanyNode>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody(), is(not(Matchers.empty())));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkChildrenListSize_beEquals() throws Exception {

        CompanyNode companyNode = new CompanyNode(10L);
        BDDMockito.given(companyNodeServiceMock.getAllChildren(companyNode)).willReturn(children);
        ResponseEntity<List<CompanyNode>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody(), hasSize(this.children.size()));
    }

    @Test
    public void getAllChildrenOfGivenNode_checkAllChildren_isExist() throws Exception {

        CompanyNode companyNode = new CompanyNode(10L);
        BDDMockito.given(companyNodeServiceMock.getAllChildren(companyNode)).willReturn(children);
        ResponseEntity<List<CompanyNode>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody(), is(children));
    }

    @Test
    public void getAllChildrenOfGivenNode_givenNodeIsZero_childrenListShouldEmpty() throws Exception {

        ResponseEntity<List<CompanyNode>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(0);
        assertThat(companyNodes.getBody(), is(Matchers.empty()));
    }

    @Test
    public void getAllChildrenOfGivenNode_thrownException_childrenListIsEmpty() throws Exception {

        CompanyNode companyNode = new CompanyNode(10L);
        BDDMockito.given(companyNodeServiceMock.getAllChildren(companyNode)).willThrow(Exception.class);
        ResponseEntity<List<CompanyNode>> companyNodes = companyNodeController.getAllChildrenOfGivenNode(companyNode.getId());
        assertThat(companyNodes.getBody(), is(Matchers.empty()));
    }




}
