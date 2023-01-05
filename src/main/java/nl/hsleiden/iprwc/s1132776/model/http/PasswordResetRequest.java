package nl.hsleiden.iprwc.s1132776.model.http;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class PasswordResetRequest {

    @NotNull
    @Email
    @Length(min = 5, max = 50)
    private String email;


    public PasswordResetRequest() {

    }

    public PasswordResetRequest(String email,String password) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
