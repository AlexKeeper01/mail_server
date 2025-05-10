package mailserver;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final List<Message> inbox;
    private final List<Message> outbox;
    private final List<Message> spam;
    private SpamFilter spamFilter;

    public User(String username) {
        this.username = username;
        this.inbox = new ArrayList<>();
        this.outbox = new ArrayList<>();
        this.spam = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<Message> getInbox() {
        return new ArrayList<>(inbox);
    }

    public List<Message> getOutbox() {
        return new ArrayList<>(outbox);
    }

    public List<Message> getSpam() {
        return new ArrayList<>(spam);
    }

    public SpamFilter getSpamFilter() {
        return spamFilter;
    }

    public void setSpamFilter(SpamFilter spamFilter) {
        this.spamFilter = spamFilter;
    }

    public void addInbox(Message message) {
        this.inbox.add(message);
    }

    public void addSpam(Message message) {
        this.spam.add(message);
    }

    public void sendMessage(String caption, String text, User receiver) {
        Message message = new Message(caption, text, this, receiver);
        outbox.add(message);
        if (receiver.getSpamFilter() == null){
            receiver.addInbox(message);
        } else {
            if (receiver.getSpamFilter().isSpam(message)) {
                receiver.addSpam(message);
            } else {
                receiver.addInbox(message);
            }
        }
    }
}
