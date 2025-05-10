package mailserver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    public void testMessageCreation() {
        User sender = new User("sender");
        User receiver = new User("receiver");
        Message message = new Message("Test", "Hello world", sender, receiver);

        assertEquals("Test", message.getCaption());
        assertEquals("Hello world", message.getText());
        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
    }

    @Test
    public void testMessageWithEmptyText() {
        User sender = new User("sender");
        User receiver = new User("receiver");
        Message message = new Message("Empty", "", sender, receiver);

        assertEquals("", message.getText());
    }

    @Test
    public void testMessageWithSpecialCharacters() {
        User sender = new User("sender");
        User receiver = new User("receiver");
        String text = "Special chars: !@#$%^&*()";
        Message message = new Message("Special", text, sender, receiver);

        assertEquals(text, message.getText());
    }
}