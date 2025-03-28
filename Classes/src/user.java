public class user{
    String userName;
    String userID;
    String userRole;

    public user(String userName, String userID, String userRole) {
        this.userName = userName;
        this.userID = userID;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserID() {
        return userID;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public int viewPoints(){
        return 0;
    }
    public String viewStatus(){
        return "";
    }
}