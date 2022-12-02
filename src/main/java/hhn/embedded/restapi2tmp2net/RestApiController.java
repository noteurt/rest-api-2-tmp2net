package hhn.embedded.restapi2tmp2net;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestApiController {


  private final AnimationThread animationThread = new AnimationThread();

  public RestApiController() {
    animationThread.start();
  }


  @PostMapping(path = "/setMessage", consumes = {"application/json"})
  public void sendMassageUDP(@RequestBody TMP2NET tmp2) throws Exception {
    TMP2NET tmp2NET =
        new TMP2NET(tmp2.getMessage(), tmp2.getIP(), tmp2.getPort(), tmp2.getHeight(),
            tmp2.getWidth(),
            tmp2.getR(), tmp2.getG(), tmp2.getB(), tmp2.isAnimation());
    animationThread.setTmp2NET(tmp2NET);
  }

  @PostMapping(path = "/setGame", consumes = {"application/json"})
  public void setGameInterface(@RequestBody String game) throws Exception {
    animationThread.update(game);
  }

}
