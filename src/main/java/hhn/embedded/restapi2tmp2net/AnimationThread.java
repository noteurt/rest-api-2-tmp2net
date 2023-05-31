package hhn.embedded.restapi2tmp2net;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;


public class AnimationThread extends Thread {
  //TODO Jede Methode außer einzeilige Getter und Setter sollen ein JavaDoc haben. (wegen Doku, CodeStyle und damit wir uns schnell daran erinnern, was geschrieben wurde, nachdem wir den Code über Weihnachten nicht angefasst haben) Auch interne Kommentare sind nicht schlecht, wenn es um die Behandlung von Randfällen geht.
  private Settings settings;

  private TMP2NET tmp2NET;

  private  String[] gameValues;

  private  String[] colorValues;

  private boolean sending = false;

  private boolean gameActive = false;

  private boolean colorActive = false;

  public AnimationThread(){
    settings = new Settings();
    tmp2NET = new TMP2NET("",0,0,0);
  }

  @Override
  public void run() {
    //noinspection InfiniteLoopStatement
    while (true) {
      if (sending) {
        try {
          sendMessage();
        }  catch (InterruptedException | IOException e) {
          Thread.currentThread().interrupt();
        }
        try {
          //noinspection BusyWait
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      if (gameActive) {
        try {
          sendGameData();
        } catch (UnknownHostException e) {
          throw new RuntimeException(e);
        }
        try {
          //noinspection BusyWait
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      if (colorActive) {
        try {
          sendColorData();
        } catch (UnknownHostException e) {
          throw new RuntimeException(e);
        }
        try {
          //noinspection BusyWait
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  /*
  public void runIdea() {
    //TODO Hab die Klasse zufällig gefunden. Sieht eigtl. deutlich sauberer als eine Thread.sleep Schleife aus
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    if (sending) {
      executor.scheduleAtFixedRate(() -> {
        try {
          //sendMessage();
        } catch (IOException e) {
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }, 0, 1, TimeUnit.SECONDS);
    }
  }

   */

    public void sendColorData() throws UnknownHostException {
      while(colorActive) {
        Payload payload = new Payload(settings.getSize()*3);
        // size nicht size von Payload, sondern anzahl Pixel
        payload.fillPayloadColor(colorValues, colorValues.length/3);
        createDatagramPacket(payload);
      }
    }

  private void createDatagramPacket(Payload payload) throws UnknownHostException {
    DatagramPacket dp = new DatagramPacket(payload.getPayload(), payload.getPayload().length, settings.getIp(), settings.getPort());
    try (DatagramSocket ds = new DatagramSocket()) {
      ds.send(dp);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      //noinspection BusyWait
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }


  public void sendMessage() throws IOException, InterruptedException {
    Payload payload = new Payload(settings.getSize()*3);
    int[][] messageArray = Letter.convertText(tmp2NET.getMessage());

    try (DatagramSocket ds = new DatagramSocket()) {
      while (sending) {
       if(settings.isAnimation()){
         messageArray = shiftArrayLeft(messageArray);
       }
       if(tmp2NET.getMessage().isBlank()){
          payload.fillPayloadColor( tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(),settings.getSize());
        }else {
         payload.fillPayloadData( tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), messageArray,
                 settings.getWidth(), settings.getHeight());
       }
          DatagramPacket dpAnimated = new DatagramPacket(payload.getPayload(), payload.getPayload().length, settings.getIp(), settings.getPort());
          ds.send(dpAnimated);
          TimeUnit.MILLISECONDS.sleep(500);
      }
    }
  }




  public int[][] shiftArrayRight(int[][] data) {
    return shiftArray(data, 0, 0, data[0].length - 1, 1);
  }

  private int[][] shiftArray(int[][] data, int targetPos, int srcStart, int srcEnd, int destStart) {
    int[][] shiftLetters = new int[data.length][data[0].length];

    for (int y = 0; y < data.length; y++) {
      shiftLetters[y][targetPos] = data[y][srcEnd];
      if (data[0].length - 1 >= 0) {
        System.arraycopy(data[y], srcStart, shiftLetters[y], destStart, data[0].length - 1);
      }
    }
    return shiftLetters;
  }


  public int[][] shiftArrayLeft(int[][] data) {
    return shiftArray(data, data[0].length - 1, 1, 0, 0);
  }


  public void update(String game)  {
    sending = false;
    game = game.replace("[", "");
    game = game.replace("]", "");
    gameActive = true;
    gameValues = game.split(",");

  }

  public void sendGameData() throws UnknownHostException {
    while(gameActive) {
      Payload payload = new Payload(settings.getSize());
      payload.fillPayloadGame(gameValues);
      createDatagramPacket(payload);
      printGame(gameValues);
    }
  }


  public boolean isSending() {
    return sending;
  }

  public void setSending(boolean sending) {
    this.sending = sending;
  }

  public boolean isGameActive() {
    return gameActive;
  }

  public void setGameActive(boolean gameActive) {
    this.gameActive = gameActive;
  }

  public void setColor(String colors) {
    colors = colors.replace("[", "");
    colors = colors.replace("]", "");
    colorActive = true;
    colorValues = colors.split(",");
  }

  public void printGame(String[]gameValues){
    for(int i = 0 ;i <gameValues.length;i++){
        System.out.print(gameValues[i]);
      if((i+1)%6 == 0){
        System.out.println();
      }
    }
    System.out.println();

  }

  public void setSettings(Settings settings) {
    this.settings = settings;
  }

  public void setTmp2NET(TMP2NET tmp2NET) {
    this.tmp2NET = tmp2NET;
  }

  public boolean isColorActive() {
    return colorActive;
  }

  public void setColorActive(boolean colorActive) {
    this.colorActive = colorActive;
  }
}