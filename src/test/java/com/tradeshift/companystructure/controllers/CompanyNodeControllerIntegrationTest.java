package com.tradeshift.companystructure.controllers;

import com.tradeshift.companystructure.CompanyStructureApplication;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanyStructureApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CompanyNodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompanyNodeController companyNodeControllerMock;
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
    public void getAllChildrenOfGivenNode_checkReturnedJson_isNotEmpty() throws Exception {
        long nodeId = 10l;
        given(companyNodeControllerMock.getAllChildrenOfGivenNode(nodeId)).willReturn(ResponseEntity.ok(new Resources<>(companyResources)));
        this.mockMvc.perform(get("/api/v1/companynodes/".concat(Long.toString(nodeId)).concat("/children")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(jsonPath("$", is(not(Matchers.empty()))));
    }

    @Test
    public void getChildren_checkReturnedJsonSize_beEquals() throws Exception {
        long nodeId = 10l;
        given(companyNodeControllerMock.getAllChildrenOfGivenNode(nodeId)).willReturn(ResponseEntity.ok(new Resources<>(companyResources)));
        this.mockMvc.perform(get("/api/v1/companynodes/".concat(Long.toString(nodeId)).concat("/children")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(children.size())));
    }

    @Test
    public void getChildren_checkReturnedJson_allChildren_isExist() throws Exception {
        long nodeId = 10l;
        given(companyNodeControllerMock.getAllChildrenOfGivenNode(nodeId)).willReturn(ResponseEntity.ok(new Resources<>(companyResources)));
        this.mockMvc.perform(get("/api/v1/companynodes/".concat(Long.toString(nodeId)).concat("/children")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("child1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("child2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("child3")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("child4")));
    }

    @Test
    public void getChildren_givenNodeIsZero_childrenListIsEmpty() throws Exception {

        this.mockMvc.perform(get("/api/v1/companynodes/".concat("0").concat("/childrens")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void isAlive_checkServerUp_withSuccessMessage() throws Exception {
        given(companyNodeControllerMock.isAlive()).willReturn("container is running");
        this.mockMvc.perform(get("/api/v1/companystructure/isAlive"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(content().string(StringContains.containsString("container is running")));
    }

}
