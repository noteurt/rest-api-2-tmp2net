package hhn.embedded.restapi2tmp2net;

public class RaumController {

    private String mcAdresse;

    private int temp;

    private boolean light;

    public RaumController(String mcAdresse,int temp, boolean light){
        this.mcAdresse = mcAdresse;
        this.temp = temp;
        this.light = light;
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

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }


}
