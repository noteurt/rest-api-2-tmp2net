package hhn.embedded.restapi2tmp2net;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.*;
import java.sql.Time;
import java.util.concurrent.TimeUnit;


@RestController
public class RestApiController {

    private boolean Animation = false;

    private Thread t1 = new Thread();


    @PostMapping(path = "/setMessage", consumes = {"application/json"})
    public void sendMassageUDP(@RequestBody TMP2NET tmp2) throws Exception {
        TMP2NET tmp2NET = new TMP2NET(tmp2.message, tmp2.getIp(), tmp2.port, tmp2.getHeight(), tmp2.getWidth(), tmp2.getR(), tmp2.getG(), tmp2.getB(), tmp2.isAnimation());
        /*
        Animation = false;
        sendMassage(tmp2NET);
        TimeUnit.SECONDS.sleep(1);
        if (tmp2NET.isAnimation()) {
            Animation = true;
            sendMassage(tmp2NET);
        }

         */
        sendMassage(tmp2NET);
    }


    public void sendMassage(TMP2NET tmp2NET) throws IOException, InterruptedException {

        Animation = false;
        TimeUnit.SECONDS.sleep(1);
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = createDatagramPacket(tmp2NET);
        ds.send(dp);


        if (tmp2NET.isAnimation()) {

            Animation = true;
            InetAddress ia = InetAddress.getByName(tmp2NET.getIp());
            int port = tmp2NET.getPort();
            byte[] data = new byte[tmp2NET.getSize() * 3];
            byte[] payload = createImagePayload(data);
           // final int[][][] massageArray = {Letter.convertText(tmp2NET.getMassage())};
            int[][] massageArray = Letter.convertText(tmp2NET.getMassage());

            while (Animation) {
                massageArray = shiftArrayLeft(massageArray);
                fillPayloadData(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), massageArray , tmp2NET.getWidth(), tmp2NET.getHeight());
                DatagramPacket dpAnimated = new DatagramPacket(payload, payload.length, ia, port);
                ds.send(dpAnimated);
                TimeUnit.SECONDS.sleep(1);
            }

            /*
            AnimationThread animationThread = new AnimationThread(tmp2NET,ds);
            Thread t1 = new Thread(animationThread);
            t1.start();

             */
            /*
            Thread newThread = new Thread(() -> {
                while (Animation) {
                    massageArray[0] = shiftArrayLeft(massageArray[0]);
                    fillPayloadData(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), massageArray[0] , tmp2NET.getWidth(), tmp2NET.getHeight());
                    DatagramPacket dpAnimated = new DatagramPacket(payload, payload.length, ia, port);
                    try {
                        ds.send(dpAnimated);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            newThread.start();

             */
        }
    }


    public static byte[] createImagePayload(byte[] data) {
        int frameSize = data.length;
        byte[] outputBuffer = new byte[frameSize + 6 + 1];

        outputBuffer[0] = ((byte)0x9C);
        outputBuffer[1] = ((byte) 0xDA);
        outputBuffer[2] = ((byte) 0xB4);
        outputBuffer[3] = ((byte) 0x1);
        outputBuffer[4] = ((byte) 0x1);
        outputBuffer[5] = ((byte) 0x1);
        outputBuffer[6 + frameSize] = (byte) 0x36;
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



    public static byte[] fillPayloadData (byte [] payload,int r,int g,int b,int[][]data, int width , int height){
        printArray(data);

        int i = 2;
        for (int y = 0 ; y < height ; y++){
            for (int x = 0 ; x < width ; x++){
                if(x >= data[0].length){
                    payload[i*3] = (byte) 0;
                    payload[i*3+1] = (byte) 0;
                    payload[i*3+2] = (byte) 0;
                }else  {
                    if(data[y][x] == 1){
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


    public static void printArray(int[][]data){
        for (int[] x : data) {
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

    public int[][] shiftArrayRight(int[][] data) {

        int[][] shiftBuchstaben = new int[data.length][data[0].length];

        for (int y = 0; y < data.length; y++) {
            shiftBuchstaben[y][0] = data[y][data[0].length - 1];
            for (int x = 1; x < data[0].length; x++) {
                shiftBuchstaben[y][x] = data[y][x - 1];
            }
        }
        return shiftBuchstaben;
    }


    public int[][] shiftArrayLeft(int[][] data) {

        int[][] shiftBuchstaben = new int[data.length][data[0].length];

        for (int y = 0; y < data.length; y++) {
            shiftBuchstaben[y][data[0].length-1] = data[y][0];

            for (int x = 1; x < data[0].length; x++) {
                shiftBuchstaben[y][x - 1] = data[y][x];
            }
        }
        return shiftBuchstaben;
    }

    public DatagramPacket createDatagramPacket(TMP2NET tmp2NET) throws UnknownHostException {
        //get IP for TMP2Protocol
        InetAddress ia = InetAddress.getByName(tmp2NET.getIp());
        //get Port for TMP2Protocol
        int port = tmp2NET.getPort();
        //create new byte Array with the size of the Amount of Pixel(calculated with getSize) times 3 because one pixel
        //has 3 values RGB
        byte[] data = new byte[tmp2NET.getSize() * 3];
        //create new payload byte Array
        byte[] payload = createImagePayload(data);
        //fill payload with all relevant information
        if (tmp2NET.getMassage() == "") {
            fillPayloadColor(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB());
        } else {
            fillPayloadData(payload, tmp2NET.getR(), tmp2NET.getG(), tmp2NET.getB(), Letter.convertText(tmp2NET.getMassage()), tmp2NET.getWidth(), tmp2NET.getHeight());
        }
        //create Datagrampacket to send DatagramSocket
        DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, port);
        return dp;
    }

    public void update(int[] gameValues, int port, String ip) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ia = InetAddress.getByName(ip);
        byte[] data = new byte[gameValues.length * 3];
        byte[] payload = createImagePayload(data);
        payload = fillPayloadGame(payload,gameValues);
        DatagramPacket dp = new DatagramPacket(payload, payload.length, ia, port);
        ds.send(dp);

    }

    public byte[] fillPayloadGame(byte[]payload,int[] gameValues){
        int i = 2;
        for (int x = 0 ; x < gameValues.length ; x++){

            int value = gameValues[x];
            switch (value){
                case 0 :
                    payload[i*3] = (byte) 0;
                    payload[i*3+1] = (byte) 0;
                    payload[i*3+2] = (byte) 0;
                    break;
                case 1:
                    payload[i*3] = (byte) 0;
                    payload[i*3+1] = (byte) 0;
                    payload[i*3+2] = (byte) 0;
                    break;
                case 2:
                    payload[i*3] = (byte) 0;
                    payload[i*3+1] = (byte) 0;
                    payload[i*3+2] = (byte) 0;
                    break;
                case 3:
                    payload[i*3] = (byte) 0;
                    payload[i*3+1] = (byte) 0;
                    payload[i*3+2] = (byte) 0;
                    break;
            }
                i++;
        }
        return payload;
    }
}
