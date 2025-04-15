public abstract class User {
    private String userId;
    private String name;
    private String userRole;

    public User(String userId, String name, String userRole) {
        this.userId = userId;
        this.name = name;
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
