package hhn.embedded.restapi2tmp2net;


import java.io.Serializable;


public class TMP2NET implements Serializable {
  private final String MESSAGE;
  private final String IP;
  private final int PORT;

  private final int HEIGHT;

  private final int WIDTH;

  private final int SIZE;

  private final int R;

  private final int G;

  private final int B;

  private final boolean ANIMATION;

  public TMP2NET(String message, String ip, int port, int height, int width, int r, int g, int b,
                 boolean animation) {
    this.MESSAGE = message + "  ";
    this.IP = ip;
    this.PORT = port;
    this.HEIGHT = height;
    this.WIDTH = width;
    this.SIZE = width * height;
    this.R = r;
    this.G = g;
    this.B = b;
    this.ANIMATION = animation;
  }


  public String getMessage() {
    return this.MESSAGE;
  }

  public String getIP() {
    return this.IP;
  }

  public int getPort() {
    return this.PORT;
  }

  public int getHeight() {
    return HEIGHT;
  }

  public int getWidth() {
    return WIDTH;
  }

  public int getSize() {
    return SIZE;
  }

  public int getR() {
    return R;
  }

  public int getG() {
    return G;
  }

  public int getB() {
    return B;
  }

  public boolean isAnimation() {
    return ANIMATION;
  }
}

