package hhn.embedded.restapi2tmp2net;


import java.io.Serializable;


public class TMP2NET implements Serializable {
    public String message;
    public String ip;
    public int port;

    private  int height;

    private  int width;

    private  int size;

    private int r;

    private int g;

    private int b;

    private boolean animation;

    public TMP2NET(){

    }


    public TMP2NET(String message, String ip, int port, int height, int width, int r, int g, int b,boolean animation){
        this.message = message +"  ";
        this.ip = ip;
        this.port = port;
        this.height = height;
        this.width = width;
        this.size = width*height;
        this.r = r;
        this.g = g;
        this.b = b;
        this.animation = animation;
    }



    public String getMassage (){
        return this.message;
    }

    public String getIp (){
        return this.ip;
    }

    public int getPort (){
        return this.port;
    }

    public int getHeight() {
        return height;
    }

    public  int getWidth() {
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

