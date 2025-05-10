package mailserver;

import java.util.Arrays;
import java.util.List;

public class KeywordsSpamFilter implements SpamFilter {
    private final List<String> keywords;

    public KeywordsSpamFilter(String keywordsInput) {
        this.keywords = Arrays.asList(keywordsInput.toLowerCase().split(" "));
    }

    @Override
    public boolean isSpam(Message message) {
        String caption = message.getCaption().toLowerCase();
        String text = message.getText().toLowerCase();

        for (String keyword : this.keywords) {
            if (caption.contains(keyword) || text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}