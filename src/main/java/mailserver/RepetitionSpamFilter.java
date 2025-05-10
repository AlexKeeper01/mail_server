package mailserver;

import java.util.HashMap;
import java.util.Map;

public class RepetitionSpamFilter implements SpamFilter {
    private final int maxRepetitions;

    public RepetitionSpamFilter(int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    @Override
    public boolean isSpam(Message message) {
        String[] words = message.getText().split("[^a-zA-Zа-яА-Я0-9]+");
        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (wordCounts.containsKey(word)) {
                    wordCounts.put(word, wordCounts.get(word) + 1);
                } else {
                    wordCounts.put(word, 1);
                }
                if (wordCounts.get(word) > this.maxRepetitions) {
                    return true;
                }
            }
        }
        return false;
    }
}