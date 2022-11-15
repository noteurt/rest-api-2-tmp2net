package hhn.embedded.restapi2tmp2net;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;


public class TMP2NET implements Serializable {
    public String message;
    public String ip;
    public int port;

    private static final int TPM2_NET_HEADER_SIZE = 6;
    private static final byte START_BYTE = (byte) 0x9C;
    private static final byte DATA_FRAME = (byte) 0xDA;
    private static final byte HIGH_FRAME = (byte) 0xB4;
    private static final byte LOW_FRAME = (byte) 0x1;

    private static final byte PACKET_NUMBER = (byte) 0x1;

    private static final byte TOTAL_PACKET_COUNT = (byte) 0x1;
    private static final byte BLOCK_END = (byte) 0x36;

    private  int height;

    private  int width;

    private  int size;

    private int r;

    private int g;

    private int b;

    public TMP2NET(){

    }


    public TMP2NET(String message, String ip, int port, int height, int width, int r, int g, int b){
        this.message = message;
        this.ip = ip;
        this.port = port;
        this.height = height;
        this.width = width;
        this.size = width*height;
        this.r = r;
        this.g = g;
        this.b = b;
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

    public void sendMassage() throws IOException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ia = InetAddress.getByName(getIp());
        int port = getPort();
        byte [] data = new byte[getSize()*3];
        byte []payload = createImagePayload(data);
        fillPayloadMessage(payload,getR(),getG(),getB(),getMassage(),getWidth(),getHeight());
        DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, port);
        ds.send(dp);
    }

    public void sendColor() throws IOException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ia = InetAddress.getByName(getIp());
        int port = getPort();
        byte [] data = new byte[getSize()*3];
        byte []payload = createImagePayload(data);
        fillPayloadColor(payload,getR(),getG(),getB());
        DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, port);
        System.out.println(data);
        ds.send(dp);
    }


    public static byte[] createImagePayload(byte[] data) {
        int frameSize = data.length;
        byte[] outputBuffer = new byte[frameSize + TPM2_NET_HEADER_SIZE + 1];

        outputBuffer[0] = (START_BYTE);
        outputBuffer[1] = (DATA_FRAME);
        outputBuffer[2] = (HIGH_FRAME);
        outputBuffer[3] = (LOW_FRAME);
        outputBuffer[4] = (PACKET_NUMBER);
        outputBuffer[5] = (TOTAL_PACKET_COUNT);
        outputBuffer[TPM2_NET_HEADER_SIZE + frameSize] = BLOCK_END;
        return outputBuffer;
    }

    public static byte[] fillPayloadColor (byte [] payload,int r,int g,int b){

        for(int i = 6;i < payload.length-1;i=i+3){
            payload[i] = (byte) r;
            payload[i+1] = (byte)g ;
            payload[i+2] = (byte) b;
        }
        return payload;
    }

    

    public static byte[] fillPayloadMessage (byte [] payload,int r,int g,int b,String message, int width , int height){

        int i = 2;
        int[][]buchstaben = Letter.convertText(message);
        printArray(buchstaben);


        for (int y = 0 ; y < height ; y++){
            for (int x = 0 ; x < width ; x++){
               if(x >= buchstaben[0].length){
                   payload[i*3] = (byte) 0;
                   payload[i*3+1] = (byte) 0;
                   payload[i*3+2] = (byte) 0;
               }else  {
                   if(buchstaben[y][x] == 1){
                       payload[i*3] = (byte) r;
                       payload[i*3+1] = (byte) g ;
                       payload[i*3+2] = (byte) b ;
                   }else {
                       payload[i*3] = (byte) 0;
                       payload[i*3+1] = (byte) 0;
                       payload[i*3+2] = (byte) 0;
                   }
               }
               i++;
            }
        }
        return payload;
    }


    public static void printArray(int[][]buchstaben){
        for (int[] x : buchstaben) {
            for (int y : x) {
                if (y == 1) {
                    System.out.print(y + " ");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}

