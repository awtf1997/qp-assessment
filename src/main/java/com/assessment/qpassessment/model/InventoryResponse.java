package com.assessment.qpassessment.model;

public class InventoryResponse<T> {

    private Boolean isSuccess;
    private String responseMessage;
    private T responseBody;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
