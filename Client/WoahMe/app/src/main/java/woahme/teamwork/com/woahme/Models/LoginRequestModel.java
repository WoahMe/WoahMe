package woahme.teamwork.com.woahme.Models;

/**
 * Created by newmast on 1/16/2016.
 */
public class LoginRequestModel {
    private String username;
    private String password;
    private String grant_type;

    public LoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.grant_type = "password";
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }
}
