package dev.edi.socialmedia;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
        // required for JSON deserialization
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
