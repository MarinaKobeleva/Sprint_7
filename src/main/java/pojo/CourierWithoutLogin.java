package pojo;

public class CourierWithoutLogin {

    private String password;

    public CourierWithoutLogin(String password) {
        this.password = password;
    }

    public CourierWithoutLogin() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
