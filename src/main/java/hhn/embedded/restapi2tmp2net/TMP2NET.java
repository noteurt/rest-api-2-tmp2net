package hhn.embedded.restapi2tmp2net;


import java.io.Serializable;


public class TMP2NET implements Serializable {
  private final String message;

  private final int r;

  private final int g;

  private final int b;
  ;

  public TMP2NET(String message, int r, int g, int b) {
    this.message = message + "";
    this.r = r;
    this.g = g;
    this.b = b;
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

}

