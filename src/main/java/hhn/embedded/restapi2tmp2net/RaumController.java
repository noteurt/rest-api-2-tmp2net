package hhn.embedded.restapi2tmp2net;

public class RaumController {

    private String macAdresse;
    private int temp;

    private int airpressure;

    private int humidity;
    private boolean light;

    public RaumController(String mcAdresse,String temp,String airPressure, String humidity ,String light){
        this.macAdresse = mcAdresse;
        this.temp = Integer.parseInt(temp);
        this.airpressure = Integer.parseInt(airPressure);
        this.humidity = Integer.parseInt(humidity);
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

    public int getAirpressure() {
        return airpressure;
    }

    public void setAirpressure(int airpressure) {
        this.airpressure = airpressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }


}
