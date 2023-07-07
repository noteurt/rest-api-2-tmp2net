package hhn.embedded.restapi2tmp2net;

public class RaumController {

    private String macAdresse;
    private String temp;

    private String airpressure;

    private String humidity;
    private boolean light;

    public RaumController(String mcAdresse,String temp,String airPressure, String humidity ,String light){
        this.macAdresse = mcAdresse;
        this.temp = temp;
        this.airpressure = airPressure;
        this.humidity = humidity;
        if(Integer.parseInt(light) != 1){
            this.light = false;
        }else{
            this.light = true;
        }
    }

    public String getmacAdresse() {
        return macAdresse;
    }

    public void setmacAdresse(String mcAdresse) {
        this.macAdresse = mcAdresse;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }


    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public String getAirpressure() {
        return airpressure;
    }

    public void setAirpressure(String airpressure) {
        this.airpressure = airpressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }


}
