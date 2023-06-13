package hhn.embedded.restapi2tmp2net;


import java.io.Serializable;


public class TMP2NET implements Serializable {
  private final String message;

  private final int r;

  private final int g;

  private final int b;

  private boolean animation;
  ;

  public TMP2NET(String message, int r, int g, int b,boolean animation) {
    this.message = message + "";
    this.r = r;
    this.g = g;
    this.b = b;
    this.animation = animation;
  }


  public String getMessage() {
    return this.message;
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

  public void setAnimation(boolean animation) {
    this.animation = animation;
  }
}

