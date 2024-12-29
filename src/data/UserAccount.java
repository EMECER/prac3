package data;

public class UserAccount {
    private String username;

    public UserAccount (String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String newUser){
        this.username = newUser;
    }

}
