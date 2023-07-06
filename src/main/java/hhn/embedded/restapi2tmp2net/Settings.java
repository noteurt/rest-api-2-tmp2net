package hhn.embedded.restapi2tmp2net;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;




public class Settings {
    private String ip = "";
    private int port = 0;

    private int height = 0;

    private int width= 0;

    private int size;

    private static final String FILE_PATH = "settingsData.json";

    public Settings() throws FileNotFoundException {

        String relativePath = "src/main/java/hhn/embedded/restapi2tmp2net/settingsData.json";
        String absolutePath = System.getProperty("user.dir") + "/" + relativePath;
        String filePath = absolutePath.replace("/", "\\");

        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            StringBuilder jsonBuilder = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                jsonBuilder.append((char) character);
            }

            fileReader.close();

            String jsonString = jsonBuilder.toString();

            jsonString = jsonString.substring(1, jsonString.length() - 1);
            String[] fieldStrings = jsonString.split(",");

            for (String fieldString : fieldStrings) {
                String[] keyValue = fieldString.split(":");
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();

                switch (key) {
                    case "ip": setIp(value); break;
                    case "width": setWidth(Integer.parseInt(value));break;
                    case "height": setHeight(Integer.parseInt(value)); break;
                    case "port": setPort(Integer.parseInt(value)); break;
                    default: break;
                }
            }


            size = width * height;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    public void saveSettings(Settings settings) throws IOException {

        String jsonData = "{\n" +
                "  \"ip\" : " + "\"" + settings.ip + "\",\n" +
                "  \"width\" : " + settings.getWidth() + ",\n" +
                "  \"height\" : " + settings.getHeight() + ",\n" +
                "  \"port\" : " + settings.getPort() + "\n" +
                "}";

        String relativePath = "src/main/java/hhn/embedded/restapi2tmp2net/settingsData.json";
        String absolutePath = System.getProperty("user.dir") + "/" + relativePath;
        String filePath = absolutePath.replace("/", "\\");

        FileWriter fileWriter = new FileWriter(FILE_PATH);
        fileWriter.write(jsonData);
        fileWriter.close();

    }
}

