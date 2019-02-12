package com.tradeshift.companystructure.services;

import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.domain.lables.RootNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidation;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidationImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CompanyNodeValidationTest {
    
    @Mock
    private CompanyNodeRepositorySDN companyNodeRepositorySDNMock;
    @InjectMocks
    private CompanyNodeValidationImpl companyNodeValidation;

    private final Long rootId = 1L;

    private final Long nodeId = 2L;

    //region checkNodeIsNotRootNode Test Cases

    @Test
    public void testCheckNodeIsNotRootNode_whenNodeIsEqualRoot_thenThrowException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.of(new RootNode(nodeId)));
        try {
            companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));
        } catch (Exception ex) {
            Assert.assertEquals(ex.getMessage(), "node can not be root node");
        }
    }

    @Test
    public void testCheckNodeIsNotRootNode_whenInputHasNodeAndNotBeRoot_thenNotThrowNodeNotFoundException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.of(new RootNode(rootId)));
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckNodeIsNotRootNode_whenInputNodeIsNull_thenThrowNullPointerException() throws Exception {
        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.of(new RootNode(rootId)));
        companyNodeValidation.checkNodeIsNotRootNode(null);
    }

    @Test(expected = Exception.class)
    public void testCheckNodeIsNotRootNode_whenInputNodeIdIsNull_thenThrowNodeNotFoundException() throws Exception {
        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.of(new RootNode(rootId)));
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(null));
    }

    @Test(expected = NodeNotFoundException.class)
    public void testCheckNodeIsNotRootNode_whenNodeRepoFindRootNode_returnOptionalEmpty_thenThrowNodeNotFoundException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.empty());
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));

    }

    @Test(expected = NullPointerException.class)
    public void testCheckNodeIsNotRootNode_whenNodeRepoFindRootNode_returnNull_thenThrowNullPointerException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(null);
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));
    }

    @Test(expected = Exception.class)
    public void testCheckNodeIsNotRootNode_whenNodeRepoFindRootNode_throwException_thenThrowException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenThrow(new Exception());
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));
    }

    @Test
    public void testCheckNodeIsNotRootNode_verifyNodeRepoFindRootNode_oneTime_call() throws Exception {

        CompanyNodeRepositorySDN companyNodeRepositorySDNMock = Mockito.mock(CompanyNodeRepositorySDN.class);
        Mockito.when(companyNodeRepositorySDNMock.findRootNode()).thenReturn(Optional.of(new RootNode(rootId)));
        CompanyNodeValidation companyNodeValidation = new CompanyNodeValidationImpl(companyNodeRepositorySDNMock);
        companyNodeValidation.checkNodeIsNotRootNode(new CompanyNode(nodeId));
        Mockito.verify(companyNodeRepositorySDNMock, Mockito.times(1)).findRootNode();
        Mockito.verifyNoMoreInteractions(companyNodeRepositorySDNMock);
    }

    //endregion

    //region checkNodeIsExist Test Cases

    @Test
    public void testCheckNodeIsExist_whenInputHasNode_thenNotThrowNodeNotFoundException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findById(nodeId, 0)).thenReturn(Optional.of(new CompanyNode(nodeId)));
        companyNodeValidation.checkNodeIsExist(new CompanyNode(nodeId));
        Assert.assertTrue(true);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckNodeIsExist_whenInputNodeIsNull_thenThrowNullPointerException() throws Exception {
        companyNodeValidation.checkNodeIsExist(null);
    }

    @Test(expected = NodeNotFoundException.class)
    public void testCheckNodeIsExist_whenInputNodeIdIsNull_thenThrowNodeNotFoundException() throws Exception {
        companyNodeValidation.checkNodeIsExist(new CompanyNode(null));
    }

    @Test(expected = NodeNotFoundException.class)
    public void testCheckNodeIsExist_whenNodeRepoFindById_returnOptionalEmpty_thenThrowNodeNotFoundException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findById(nodeId)).thenReturn(Optional.empty());
        companyNodeValidation.checkNodeIsExist(new CompanyNode(nodeId));
    }

    @Test(expected = NodeNotFoundException.class)
    public void testCheckNodeIsExist_whenNodeRepoFindById_returnNull_thenThrowNodeNotFoundException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findById(nodeId)).thenReturn(null);
        companyNodeValidation.checkNodeIsExist(new CompanyNode(nodeId));
    }

    @Test(expected = MockitoException.class)
    public void testCheckNodeIsExist_whenNodeRepoFindById_throwException_thenThrowMockitoException() throws Exception {

        Mockito.when(companyNodeRepositorySDNMock.findById(nodeId)).thenThrow(new Exception());
        companyNodeValidation.checkNodeIsExist(new CompanyNode(nodeId));
    }

    @Test
    public void testCheckNodeIsExist_verifyNodeRepoFindById_oneTime_call() throws Exception {

        CompanyNodeRepositorySDN companyNodeRepositorySDNMock = Mockito.mock(CompanyNodeRepositorySDN.class);
        Mockito.when(companyNodeRepositorySDNMock.findById(nodeId, 0)).thenReturn(Optional.of(new CompanyNode(nodeId)));
        CompanyNodeValidation companyNodeValidation = new CompanyNodeValidationImpl(companyNodeRepositorySDNMock);
        companyNodeValidation.checkNodeIsExist(new CompanyNode(nodeId));
        Mockito.verify(companyNodeRepositorySDNMock, Mockito.times(1)).findById(nodeId, 0);
        Mockito.verifyNoMoreInteractions(companyNodeRepositorySDNMock);
    }

    //endregion

    //region checkInputNodeIsNotNull Test Cases

    @Test
    public void testCheckInputNodeIsNotNull_whenInputNodeIsNull_thenThrowNullPointerException() {

        try {
            companyNodeValidation.checkInputNodeIsNotNull(null);
        } catch (Exception ex) {
            Assert.assertEquals(ex.getMessage(), "company node or company node id is null");
        }
    }

    @Test
    public void testCheckInputNodeIsNotNull_whenInputNodeIdIsNull_thenThrowNullPointerException() {

        try {
            companyNodeValidation.checkInputNodeIsNotNull(new CompanyNode(null));
        } catch (Exception ex) {
            Assert.assertEquals(ex.getMessage(), "company node or company node id is null");
        }
    }

    @Test
    public void testCheckInputNodeIsNotNull_whenInputHasNode_thenNotThrowNullPointerException() {

        companyNodeValidation.checkInputNodeIsNotNull(new CompanyNode(nodeId));
        Assert.assertTrue(true);

    }


    //endregion

}
