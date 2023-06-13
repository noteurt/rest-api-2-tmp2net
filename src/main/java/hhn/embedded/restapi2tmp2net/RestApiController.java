package hhn.embedded.restapi2tmp2net;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;


@RestController
public class RestApiController {

  private SensorData sensorData;


  private final AnimationThread animationThread = new AnimationThread();

  public RestApiController() throws FileNotFoundException {
    animationThread.start();
    sensorData = new SensorData();
  }

  @PostMapping(path = "/setSettings", consumes = {"application/json"})
  public void setSettings(@RequestBody Settings settings) throws Exception {
    animationThread.setSettings(settings);
  }

  @GetMapping(path = "/getSettings")
  public Settings getSettings()  {
    return animationThread.getSettings();
  }


  @PostMapping(path = "/setMessage", consumes = {"application/json"})
  public void sendMassageUDP(@RequestBody TMP2NET tmp2) throws Exception {
    animationThread.setGameActive(false);
    animationThread.setColorActive(false);
    animationThread.setTmp2NET(tmp2);
    animationThread.setSending(true);
  }

  @PostMapping(path = "/setColors", consumes = {"application/json"})
  public void setColors(@RequestBody String colors) throws Exception {
    animationThread.setSending(false);
    animationThread.setGameActive(false);
    animationThread.setColor(colors);
    animationThread.setColorActive(true);
  }

  @PostMapping(path = "/setGame", consumes = {"application/json"})
    public void setGameInterface(@RequestBody String game) throws Exception {
    animationThread.setSending(false);
    animationThread.setColorActive(false);
    animationThread.update(game);
    animationThread.setGameActive(true);
  }

  @PostMapping(path = "/sendData", consumes = {"application/json"})
  public void sendData(@RequestBody NodeData data){
    String sensorValues = data.getValues();
    String[]valuesArray = sensorValues.split(" ");
    RaumController roomData = new RaumController(data.getMacAdresse(),valuesArray[0],valuesArray[1],valuesArray[2],valuesArray[3]);
    this.sensorData.updateValue(roomData);
  }


  @GetMapping(path = "/getData")
  public SensorData getData(){
    return this.sensorData;
  }

  @PostMapping("/stop")
  public void stop(){
    animationThread.setSending(false);
    animationThread.setGameActive(false);
  }

}
