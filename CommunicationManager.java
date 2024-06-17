package quiz5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommunicationManager {

    private static final String REMOTE_SERVICE_URL = "http://example.com/api/chatbot";

    public String sendDataToRemoteService(String jsonData) {
        try {

            URL url = new URL(REMOTE_SERVICE_URL);
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


    public void handleResponse(String response) {
        System.out.println("Received response from remote service:");
        System.out.println(response);

    }
}