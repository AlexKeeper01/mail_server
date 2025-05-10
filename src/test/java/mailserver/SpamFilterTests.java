package mailserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpamFilterTests {
    private User sender;
    private User receiver;

    @BeforeEach
    public void setUp() {
        sender = new User("sender");
        receiver = new User("receiver");
    }

    @Test
    public void testSimpleSpamFilter() {
        SimpleSpamFilter filter = new SimpleSpamFilter();

        Message spam1 = new Message("SPAM", "Normal text", sender, receiver);
        Message spam2 = new Message("Normal", "This is spam", sender, receiver);
        Message normal = new Message("Hello", "Normal message", sender, receiver);

        assertTrue(filter.isSpam(spam1));
        assertTrue(filter.isSpam(spam2));
        assertFalse(filter.isSpam(normal));
    }

    @Test
    public void testKeywordsSpamFilter() {
        KeywordsSpamFilter filter = new KeywordsSpamFilter("viagra lottery winner");

        Message spam1 = new Message("Offer", "Buy viagra now", sender, receiver);
        Message spam2 = new Message("Congrats", "You are a lottery winner", sender, receiver);
        Message normal = new Message("Hello", "Normal message", sender, receiver);

        assertTrue(filter.isSpam(spam1));
        assertTrue(filter.isSpam(spam2));
        assertFalse(filter.isSpam(normal));
    }

    @Test
    public void testKeywordsSpamFilterCaseInsensitive() {
        KeywordsSpamFilter filter = new KeywordsSpamFilter("viagra");

        Message spam1 = new Message("Offer", "BUY VIAGRA NOW", sender, receiver);
        Message spam2 = new Message("Offer", "This is ViAgRa", sender, receiver);

        assertTrue(filter.isSpam(spam1));
        assertTrue(filter.isSpam(spam2));
    }

    @Test
    public void testRepetitionsSpamFilter() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(3);

        String repeatedText = "hello hello hello hello";
        Message spam = new Message("Hi", repeatedText, sender, receiver);
        Message normal = new Message("Hi", "hello hello hello", sender, receiver);

        assertTrue(filter.isSpam(spam));
        assertFalse(filter.isSpam(normal));
    }

    @Test
    public void testSenderSpamFilter() {
        User spammer = new User("spammer");
        SenderSpamFilter filter = new SenderSpamFilter("spammer");

        Message spam = new Message("Hi", "Hello", spammer, receiver);
        Message normal = new Message("Hi", "Hello", sender, receiver);

        assertTrue(filter.isSpam(spam));
        assertFalse(filter.isSpam(normal));
    }

    @Test
    public void testCompositeSpamFilter() {
        CompositeSpamFilter filter = new CompositeSpamFilter();
        filter.addFilter(new SimpleSpamFilter());
        filter.addFilter(new KeywordsSpamFilter("viagra"));

        Message spam1 = new Message("SPAM", "Normal text", sender, receiver);
        Message spam2 = new Message("Offer", "Buy viagra", sender, receiver);
        Message normal = new Message("Hello", "Normal message", sender, receiver);

        assertTrue(filter.isSpam(spam1));
        assertTrue(filter.isSpam(spam2));
        assertFalse(filter.isSpam(normal));
    }

    @Test
    public void testEmptyCompositeFilter() {
        CompositeSpamFilter filter = new CompositeSpamFilter();
        Message normal = new Message("Hello", "Normal message", sender, receiver);

        assertFalse(filter.isSpam(normal));
    }
}