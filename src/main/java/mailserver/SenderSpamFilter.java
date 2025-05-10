package mailserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SenderSpamFilter implements SpamFilter {
    private final List<String> sendersNames;

    public SenderSpamFilter(String sendersInput) {
        this.sendersNames = Arrays.asList(sendersInput.split(" "));
    }

    @Override
    public boolean isSpam(Message message) {
        String messageSender = message.getSender().getUsername();
        for (String senderName : sendersNames){
            if (messageSender.equals(senderName)) {
                return true;
            }
        }
        return false;
    }
}