package hhn.embedded.restapi2tmp2net;


import java.io.Serializable;


public class TMP2NET implements Serializable {
  private final String message;
  private final String ip;
  private final int port;

  private final int height;

  private final int width;

  private final int size;

  private final int r;

  private final int g;

  private final int b;

  private final boolean animation;

  public TMP2NET(String message, String ip, int port, int height, int width, int r, int g, int b,
                 boolean animation) {
    this.message = message + "";
    this.ip = ip;
    this.port = port;
    this.height = height;
    this.width = width;
    this.size = width * height;
    this.r = r;
    this.g = g;
    this.b = b;
    this.animation = animation;
  }


  public String getMessage() {
    return this.message;
  }

  public String getIP() {
    return this.ip;
  }

  public int getPort() {
    return this.port;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getSize() {
    return size;
  }

  public int getR() {
    return r;
  }

  public int getG() {
    return g;
  }

  public int getB() {
    return b;
  }

  public boolean isAnimation() {
    return animation;
  }
}

