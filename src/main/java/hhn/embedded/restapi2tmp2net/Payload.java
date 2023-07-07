package hhn.embedded.restapi2tmp2net;

import java.util.Arrays;
import java.util.Iterator;

public class Payload {

    private byte[] payload;

    public Payload(int frameSize){

        payload = new byte[frameSize + 6 + 1];

        payload[0] = (byte) 0x9C;
        payload[1] = (byte) 0xDA;
        payload[2] = (byte) 0xB4;
        payload[3] = (byte) 0x1;
        payload[4] = (byte) 0x1;
        payload[5] = (byte) 0x1;
        payload[6 + frameSize] = (byte) 0x36;

    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] outputBuffer) {
        this.payload = outputBuffer;
    }



    public void fillPayloadColor( int r, int g, int b,int size) {
        for (int i = 0; i < size; i++) {
            setPayload(i+2, (byte) r, (byte) g, (byte) b);
        }
    }

    private void setPayload(int i, final byte RED, final byte GREEN, final byte BLUE) {
        payload[i * 3] = RED;
        payload[i * 3 + 1] = GREEN;
        payload[i * 3 + 2] = BLUE;
    }



    public void fillPayloadData(int r, int g, int b, int[][] data, int width,
                                int height) {
        printArray(data);
        int i = 2;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x < data[0].length && data[y][x] == 1) {
                    setPayload( i, (byte) r, (byte) g, (byte) b);
                } else {
                    setPayload( i, (byte) 0, (byte) 0, (byte) 0);
                }
                i++;
            }
        }
    }


    public byte[] fillPayloadGame(String[] gameValues) {
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

    public byte[] fillPayloadColor(String[] colorValues, int size) {

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
}
