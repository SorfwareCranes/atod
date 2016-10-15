package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 15/10/2016.
 */

public class UserModel {
    String name;
    String email;
    String type;

    public UserModel() {
    }

    public UserModel(String name, String email, String type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
