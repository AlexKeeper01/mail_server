package mailserver;

public interface SpamFilter {
    boolean isSpam(Message message);
}
