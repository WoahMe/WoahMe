package woahme.teamwork.com.woahme.Models;

/**
 * Created by newmast on 1/16/2016.
 */
public class LoginResponseModel {
    private String username;
    private String token;

    public LoginResponseModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }
}
