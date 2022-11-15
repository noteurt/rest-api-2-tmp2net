package hhn.embedded.restapi2tmp2net;

import org.springframework.web.bind.annotation.*;


@RestController
public class RestApiController {


    @PostMapping(path = "/setColor", consumes = {"application/json"})
    public void test(@RequestBody TMP2NET tmp2) throws Exception {
        TMP2NET tmp2NET = new TMP2NET(tmp2.message, tmp2.getIp(), tmp2.port, tmp2.getHeight(), tmp2.getWidth(), tmp2.getR(), tmp2.getG(), tmp2.getB());
        tmp2NET.sendColor();
    }

    @PostMapping(path = "/setMessage", consumes = {"application/json"})
    public void sendMassage(@RequestBody TMP2NET tmp2) throws Exception {
        TMP2NET tmp2NET = new TMP2NET(tmp2.message, tmp2.getIp(), tmp2.port, tmp2.getHeight(), tmp2.getWidth(), tmp2.getR(), tmp2.getG(), tmp2.getB());
        tmp2NET.sendMassage();
    }


}
