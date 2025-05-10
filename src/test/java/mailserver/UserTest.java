package mailserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user1;
    private User user2;
    private CompositeSpamFilter filter;

    @BeforeEach
    public void setUp() {
        user1 = new User("user1");
        user2 = new User("user2");
        filter = new CompositeSpamFilter();
    }

    @Test
    public void testSendMessage_NormalCase() {
        user1.sendMessage("Hello", "This is a test", user2);

        assertEquals(1, user1.getOutbox().size());
        assertEquals(1, user2.getInbox().size());
        assertEquals(0, user2.getSpam().size());

        Message sentMessage = user1.getOutbox().get(0);
        assertEquals("Hello", sentMessage.getCaption());
        assertEquals("This is a test", sentMessage.getText());
        assertEquals(user1, sentMessage.getSender());
        assertEquals(user2, sentMessage.getReceiver());
    }

    @Test
    public void testSendMessage_SpamCase() {
        filter.addFilter(new SimpleSpamFilter());
        user2.setSpamFilter(filter);

        user1.sendMessage("SPAM", "This is spam message", user2);

        assertEquals(1, user1.getOutbox().size());
        assertEquals(0, user2.getInbox().size());
        assertEquals(1, user2.getSpam().size());
    }

    @Test
    public void testSendMessage_NotSpamAccordingToFilter() {
        User sender = new User("sender");
        User receiver = new User("receiver");

        CompositeSpamFilter filter = new CompositeSpamFilter();
        filter.addFilter(new KeywordsSpamFilter("minecraft"));
        receiver.setSpamFilter(filter);

        sender.sendMessage("Normal", "Это обычное сообщение", receiver);

        assertEquals(1, sender.getOutbox().size());
        assertEquals(0, receiver.getSpam().size());
        assertEquals(1, receiver.getInbox().size());

        Message sentMessage = receiver.getInbox().get(0);
        assertEquals("Normal", sentMessage.getCaption());
        assertEquals("Это обычное сообщение", sentMessage.getText());
        assertEquals(sender, sentMessage.getSender());
        assertEquals(receiver, sentMessage.getReceiver());
    }

    @Test
    public void testSendMessage_EmptyCaption() {
        user1.sendMessage("", "Message with empty caption", user2);

        assertEquals(1, user1.getOutbox().size());
        assertEquals("", user1.getOutbox().get(0).getCaption());
    }

    @Test
    public void testSendMessage_NullReceiver() {
        assertThrows(NullPointerException.class, () -> {
            user1.sendMessage("Test", "Message", null);
        });
    }

    @Test
    public void testSpamFilterReplacement() {
        CompositeSpamFilter newFilter = new CompositeSpamFilter();
        user1.setSpamFilter(newFilter);

        assertSame(newFilter, user1.getSpamFilter());
    }

    @Test
    public void testSpamFilterWithMultipleFilters() {
        filter.addFilter(new SimpleSpamFilter());
        filter.addFilter(new KeywordsSpamFilter("viagra lottery"));
        user2.setSpamFilter(filter);

        // Тест на простое спам-слово
        user1.sendMessage("Normal", "This contains spam", user2);
        assertEquals(1, user2.getSpam().size());

        // Тест на ключевые слова
        user1.sendMessage("Offer", "Buy viagra now", user2);
        assertEquals(2, user2.getSpam().size());
    }
}