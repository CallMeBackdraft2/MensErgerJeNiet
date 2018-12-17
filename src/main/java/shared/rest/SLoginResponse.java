package rest.shared;

public class SLoginResponse {

    private String username = "";
    private String password = "";
    private String result = "";

    public void setUsername(String username){ this.username = username; }
    public void setResult(String result){ this.result = result;}
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getResult() { return result; }


}
