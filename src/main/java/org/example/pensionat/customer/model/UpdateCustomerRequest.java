package org.example.pensionat.customer.model;


public class UpdateCustomerRequest{

    private CreateCustomerRequest request;
    private boolean changePassword;

    public UpdateCustomerRequest(CreateCustomerRequest request, boolean changePassword) {
        this.request = request;
        this.changePassword = changePassword;
    }

    public CreateCustomerRequest getRequest() {
        return request;
    }

    public void setRequest(CreateCustomerRequest request) {
        this.request = request;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }
}
