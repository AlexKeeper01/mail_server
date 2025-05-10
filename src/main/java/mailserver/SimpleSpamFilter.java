package mailserver;

public class SimpleSpamFilter implements SpamFilter {
    @Override
    public boolean isSpam(Message message) {
        String caption = message.getCaption().toLowerCase();
        String text = message.getText().toLowerCase();
        return caption.contains("spam") || text.contains("spam");
    }
}