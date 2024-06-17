package quiz5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserInteractionManager {

    private CommunicationManager communicationManager;

    public UserInteractionManager(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
    }

    public void interactWithUser(String userId, String messageContent) {

        LocalDateTime timestamp = LocalDateTime.now();
        String sessionId = "sess_12345";


        ChatbotInteraction interaction = new ChatbotInteraction();
        interaction.setRequestId(generateRequestId());
        interaction.setTimestamp(timestamp);

        ChatbotInteraction.User user = new ChatbotInteraction.User();
        user.setUserId(userId);
        user.setName("John Doe");
        user.setLanguage("en");

        ChatbotInteraction.Location location = new ChatbotInteraction.Location();
        location.setCountry("USA");
        location.setRegion("NY");
        location.setCity("New York");
        user.setLocation(location);

        interaction.setUser(user);

        ChatbotInteraction.Session session = new ChatbotInteraction.Session();
        session.setSessionId(sessionId);
        session.setIsNewSession(false);

        interaction.setSession(session);

        ChatbotInteraction.Message message = new ChatbotInteraction.Message();
        message.setMessageId(generateMessageId());
        message.setContent(messageContent);
        message.setMessageType("text");
        message.setAttachments(null);

        interaction.setMessage(message);

        ChatbotInteraction.Context context = new ChatbotInteraction.Context();
        context.setPreviousMessages(null);
        context.setAdditionalContext("");

        interaction.setContext(context);


        String jsonData = convertInteractionToJson(interaction);


        String response = communicationManager.sendDataToRemoteService(jsonData);


        System.out.println("Received response from CommunicationManager: " + response);
    }

    private String generateRequestId() {

        return "req_" + System.currentTimeMillis();
    }

    private String generateMessageId() {

        return "msg_" + System.currentTimeMillis();
    }

    private String convertInteractionToJson(ChatbotInteraction interaction) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(interaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
