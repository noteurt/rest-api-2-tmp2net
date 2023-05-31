package hhn.embedded.restapi2tmp2net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Settings {
    private String ip = "";
    private int port = 0;

    private int height = 0;

    private int width= 0;

    private int size;
    private boolean animation = false;

    public Settings(){
        size = width*height;
    }


    public InetAddress getIp() throws UnknownHostException {
        InetAddress ia = InetAddress.getByName(ip);
        return ia;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAnimation() {
        return animation;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }
}

