package quiz6;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecialCommunicationManager {

    private String commonServiceUrl;
    private String specialServiceUrl;

    public SpecialCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        this.commonServiceUrl = commonServiceUrl;
        this.specialServiceUrl = specialServiceUrl;
    }

    public String sendDataToRemoteService(String jsonData, boolean isSpecialService) {
        String serviceUrl = isSpecialService ? specialServiceUrl : commonServiceUrl;

        try {

            URL url = new URL(serviceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            try (InputStream is = connection.getInputStream()) {

                StringBuilder response = new StringBuilder();
                int data;
                while ((data = is.read()) != -1) {
                    response.append((char) data);
                }
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}