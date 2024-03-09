package ch04;

public class User {
    private String id;
    private String pwd;
    private String email;
    public User(String id, String pwd, String email) {
        this.id = id;
        this.pwd = pwd;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
