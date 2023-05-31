package hhn.embedded.restapi2tmp2net;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AnimationThread extends Thread {
  //TODO Jede Methode außer einzeilige Getter und Setter sollen ein JavaDoc haben. (wegen Doku, CodeStyle und damit wir uns schnell daran erinnern, was geschrieben wurde, nachdem wir den Code über Weihnachten nicht angefasst haben) Auch interne Kommentare sind nicht schlecht, wenn es um die Behandlung von Randfällen geht.
  private TMP2NET tmp2NET;

  private  String[] gameValues;

  private  String[] colorValues;

  private boolean sending = false;

  private boolean gameActive = false;

  private boolean colorActive = false;



  public AnimationThread() {
    this.tmp2NET = new TMP2NET("", "192.168.137.253", 65506, 2, 6, 0, 0, 0, false);
  }

  public static byte[] createImagePayload(byte[] data) {
    //TODO Payload soll eine eigene Klasse haben. Diese ist schon zu lang und 50% gehört zum Payload.
    int frameSize = data.length;
    byte[] outputBuffer = new byte[frameSize + 6 + 1];

    outputBuffer[0] = (byte) 0x9C;
    outputBuffer[1] = (byte) 0xDA;
    outputBuffer[2] = (byte) 0xB4;
    outputBuffer[3] = (byte) 0x1;
    outputBuffer[4] = (byte) 0x1;
    outputBuffer[5] = (byte) 0x1;
    outputBuffer[6 + frameSize] = (byte) 0x36;
    return outputBuffer;
  }

  public static void printArray(int[][] data) {
    for (int[] x : data) {
      for (int y : x) {
        if (y == 1) {
          System.out.print(y + " ");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  @Override
  public void run() {
    //noinspection InfiniteLoopStatement
    while (true) {
      if (sending) {
        try {
          sendMessage();
        } catch (IOException e) {
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        try {
          //noinspection BusyWait
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      if(gameActive){
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

      if(colorActive){
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

  public void runIdea() {
    //TODO Hab die Klasse zufällig gefunden. Sieht eigtl. deutlich sauberer als eine Thread.sleep Schleife aus
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    if (sending) {
      executor.scheduleAtFixedRate(() -> {
        try {
          sendMessage();
        } catch (IOException e) {
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }, 0, 1, TimeUnit.SECONDS);
    }
  }

  public void setTmp2NET(TMP2NET tmp2NET) throws IOException, InterruptedException {
    this.tmp2NET = tmp2NET;
      sending = false;
      Thread.sleep(1000);
      sending = true;

  }

  public void sendMessage() throws IOException, InterruptedException {
    InetAddress ia = InetAddress.getByName(tmp2NET.getIP());
    int port = tmp2NET.getPort();
    byte[] data = new byte[tmp2NET.getSize() * 3];
    byte[] payload = createImagePayload(data);
    int[][] messageArray = Letter.convertText(tmp2NET.getMessage());

    try (DatagramSocket ds = new DatagramSocket()) {
      while (sending) {
       if(tmp2NET.isAnimation()){
         messageArray = shiftArrayLeft(messageArray);
       }
       if(tmp2NET.getMessage().isBlank()){
          fillPayloadOneColor(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(),tmp2NET.getSize());
        }else {
         fillPayloadData(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), messageArray,
                 tmp2NET.getWidth(), tmp2NET.getHeight());
       }
          DatagramPacket dpAnimated = new DatagramPacket(payload, payload.length, ia, port);
          ds.send(dpAnimated);
          TimeUnit.MILLISECONDS.sleep(500);
      }
    }
  }

  public void fillPayloadOneColor(byte[] payload, int r, int g, int b,int size) {
    for (int i = 0; i < size; i++) {
      setPayload(payload, i+2, (byte) r, (byte) g, (byte) b);
    }
  }

  public void fillPayloadData(byte[] payload, int r, int g, int b, int[][] data, int width,
                              int height) {
    printArray(data);
    int i = 2;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x < data[0].length && data[y][x] == 1) {
          setPayload(payload, i, (byte) r, (byte) g, (byte) b);
        } else {
          setPayload(payload, i, (byte) 0, (byte) 0, (byte) 0);
        }
        i++;
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

  public DatagramPacket createDatagramPacket(TMP2NET tmp2NET) throws UnknownHostException {
    // Get IP for TMP2Protocol
    InetAddress ia = InetAddress.getByName(tmp2NET.getIP());
    // Get Port for TMP2Protocol
    int port = tmp2NET.getPort();
    // Create new byte Array with the size of the amount of pixels (calculated with getSize() times 3 because one pixel
    // has 3 values due to RGB)
    byte[] data = new byte[tmp2NET.getSize() * 3];
    // Create new payload byte Array
    byte[] payload = createImagePayload(data);

    // Fill payload with all relevant information
    if (tmp2NET.getMessage().isBlank()) {
      fillPayloadOneColor(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(),tmp2NET.getSize());
    } else {
      int[][] messageArray = Letter.convertText(tmp2NET.getMessage());
      fillPayloadData(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), messageArray,
          tmp2NET.getWidth(), tmp2NET.getHeight());
    }

    // Create DatagramPacket to send DatagramSocket
    return new DatagramPacket(payload, payload.length, ia, port);
  }

  public void update(String game) throws IOException {
    game = game.replace("[", "");
    game = game.replace("]", "");
    gameActive = true;
    gameValues = game.split(",");
  }


  public void setColor(String colors) throws IOException {
    colors = colors.replace("[", "");
    colors = colors.replace("]", "");
    colorActive = true;
    colorValues = colors.split(",");
  }


  public void sendGameData() throws UnknownHostException {
    while(gameActive) {
      InetAddress ia = InetAddress.getByName(tmp2NET.getIP());
      byte[] data = new byte[tmp2NET.getSize() * 3];
      byte[] payload = createImagePayload(data);
      payload = fillPayloadGame(payload, gameValues);
      DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, tmp2NET.getPort());
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
      printGame(gameValues);
    }
  }

  public void sendColorData() throws UnknownHostException {
    while(colorActive) {
      InetAddress ia = InetAddress.getByName(tmp2NET.getIP());
      byte[] data = new byte[tmp2NET.getSize() * 3];
      byte[] payload = createImagePayload(data);
      // size nicht size von Payload sondern anzahl Pixel
      payload = fillPayloadColor(payload, colorValues, colorValues.length/3);
      DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, tmp2NET.getPort());
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
  }



  public byte[] fillPayloadGame(byte[] payload, String[] gameValues) {
    int newLine = gameValues.length / 5;

    Iterator<String> gameValuesIterator = Arrays.stream(gameValues).iterator();

    int i = 2;

    for (int line = 0; line < 5; line++) {
      for (int x = 0; x < newLine; x++) {
        String value = gameValuesIterator.next();
        switch (value) {
          case "0" -> setPayloadBlue(payload, i);
          case "2" -> setPayloadRed(payload, i);
          case "3" -> setPayloadGreen(payload, i);
          default -> setPayloadBlack(payload, i);
        }
        i++;
      }
      for (int y = newLine; y < 36; y++) {
        setPayloadBlue(payload, i);
        i++;
      }
    }
    return payload;
  }


  public byte[] fillPayloadColor(byte[] payload, String[] colorValues, int size) {

    for (int i = 0; i < size; i++) {
      setPayload(payload, i+2, (byte) Integer.parseInt(colorValues[i*3]), (byte) Integer.parseInt(colorValues[i*3+1]), (byte) Integer.parseInt(colorValues[i*3+2]));
    }
    return payload;
  }

  private void setPayload(byte[] payload, int i, final byte RED, final byte GREEN,
                          final byte BLUE) {
    payload[i * 3] = RED;
    payload[i * 3 + 1] = GREEN;
    payload[i * 3 + 2] = BLUE;
  }

  private void setPayloadGreen(byte[] payload, int i) {
    setPayload(payload, i, (byte) 255, (byte) 0, (byte) 0);
  }

  private void setPayloadBlue(byte[] payload, int i) {
    setPayload(payload, i, (byte) 0, (byte) 0, (byte) 255);
  }

  private void setPayloadRed(byte[] payload, int i) {
    setPayload(payload, i, (byte) 0, (byte) 255, (byte) 0);
  }

  private void setPayloadBlack(byte[] payload, int i) {
    setPayload(payload, i, (byte) 0, (byte) 0, (byte) 0);
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

  public boolean isColorActiveActive() {
    return colorActive;
  }

  public void setColorActive(boolean colorActive) {
    this.colorActive = colorActive;
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

}