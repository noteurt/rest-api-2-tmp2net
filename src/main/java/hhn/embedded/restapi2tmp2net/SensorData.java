package hhn.embedded.restapi2tmp2net;

import java.util.ArrayList;
import java.util.List;

public class SensorData {


    private List<RaumController> rooms=new ArrayList<RaumController>();
    public SensorData() {

    }


    public List<RaumController> getRooms() {
        return rooms;
    }

    public boolean checkRoom(String mcAdresse){
      if(this.rooms.isEmpty()){
          return false;
      }
      for (RaumController raum : rooms) {
          if(raum.getmcAdresse().equals(mcAdresse)){
             return true;
          }
      }

      return false;
    }

    public void updateValue(RaumController raum){

        if (checkRoom(raum.getmcAdresse())){
            int index = 0;
            for (RaumController room : rooms) {
                if(room.getmcAdresse().equals(raum.getmcAdresse())){
                    index = rooms.indexOf(room);
                }
            }
            this.rooms.set(index,raum);
        }else{
            this.rooms.add(raum);
        }
    }
}
