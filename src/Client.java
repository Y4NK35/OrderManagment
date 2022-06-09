import java.io.Serializable;
import java.util.Locale;

public class Client implements Comparable<Client>, Serializable {
    private String companyName;
    private String companyNip;
    private String cityName;
    private String postCode;
    Client() {
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyNip(String companyNip) {
        this.companyNip = companyNip;
    }
    public String getCompanyNip(){
        return companyNip;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName(){
        return cityName;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getPostCode(){
        return postCode;
    }

    @Override
    public int compareTo(Client o) {
        return this.companyName.toUpperCase(Locale.ROOT).compareTo(o.companyName);
    }
    public void setDefaultClient(){
        companyName = "Company name";
        companyNip = "000 000 00 00";
        cityName = "City";
        postCode = "00-000";
    }
}
