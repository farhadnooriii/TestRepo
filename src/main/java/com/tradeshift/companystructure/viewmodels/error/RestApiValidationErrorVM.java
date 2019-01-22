package com.tradeshift.companystructure.viewmodels.error;

import com.tradeshift.companystructure.viewmodels.error.RestApiSubErrorVM;

/**
 * Created by F.Nouri on 1/22/2019.
 */
public class RestApiValidationErrorVM extends RestApiSubErrorVM {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    RestApiValidationErrorVM(String object, String message) {
        this.object = object;
        this.message = message;
    }

    RestApiValidationErrorVM(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
