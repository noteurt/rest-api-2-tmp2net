package hhn.embedded.restapi2tmp2net;

public class RaumController {

    private String mcAdresse;

    private int temp;

    public RaumController(String mcAdresse,int temp){
        this.mcAdresse = mcAdresse;
        this.temp = temp;
    }

    public String getmcAdresse() {
        return mcAdresse;
    }

    public void setmcAdresse(String mcAdresse) {
        this.mcAdresse = mcAdresse;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

}
