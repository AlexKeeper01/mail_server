package mailserver;

import java.util.HashMap;
import java.util.Map;

public class UserStorage{
    private final Map<String, User> users;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    public void addUser(String username) {
        users.put(username, new User(username));
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }
}
