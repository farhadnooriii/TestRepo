package com.tradeshift.companystructure.services;

import com.tradeshift.companystructure.controllers.CompanyNodeController;
import com.tradeshift.companystructure.domain.lables.CompanyNode;
import com.tradeshift.companystructure.repositories.companynode.CompanyNodeRepositorySDN;
import com.tradeshift.companystructure.repositories.exceptions.NodeNotFoundException;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidation;
import com.tradeshift.companystructure.services.companynode.CompanyNodeValidationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
@RunWith(MockitoJUnitRunner.class)
public class CompanyNodeValidationTest {

    @InjectMocks
    private CompanyNodeValidationImpl companyNodeValidation;

    @Mock
    private CompanyNodeRepositorySDN companyNodeRepositorySDN;

    @Test(expected = NodeNotFoundException.class)
    public void checkNodeIsExist_checkReturnType_isEmpty_throwNodeNotFountException() throws Exception {
        Long id = 0L;
        BDDMockito.given(companyNodeRepositorySDN.findById(id)).willReturn(Optional.empty());
        companyNodeValidation.checkNodeIsExist(new CompanyNode(id));
    }

}
