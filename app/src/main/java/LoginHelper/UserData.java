package LoginHelper;


public class UserData {
    private String first_name;
    private String last_name;
    private String email;
    private String id;

    public String getId() { return id; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() { return last_name; }
    public String getEmail() { return email; }

    public void setId(String id) { this.id = id; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public void setEmail(String email) { this.email = email; }
    public UserData(){ }

    public UserData(String first_name, String last_name, String email, String id){
        this.first_name=first_name;
        this.id=id;
        this.last_name=last_name;
        this.email=email;
    }
}
