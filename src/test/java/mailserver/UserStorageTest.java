package mailserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserStorageTest {
    private UserStorage storage;

    @BeforeEach
    public void setUp() {
        storage = new UserStorage();
    }

    @Test
    public void testAddUser() {
        storage.addUser("user1");
        assertTrue(storage.userExists("user1"));
        assertEquals(1, storage.getAllUsers().size());
    }

    @Test
    public void testAddDuplicateUser() {
        storage.addUser("user1");
        storage.addUser("user1"); // Дубликат
        assertEquals(1, storage.getAllUsers().size());
    }

    @Test
    public void testGetNonexistentUser() {
        assertNull(storage.getUser("nonexistent"));
    }

    @Test
    public void testUserExists() {
        storage.addUser("user1");
        assertTrue(storage.userExists("user1"));
        assertFalse(storage.userExists("user2"));
    }

    @Test
    public void testCaseSensitiveUsernames() {
        storage.addUser("User1");
        assertFalse(storage.userExists("user1"));
    }

    @Test
    public void testGetAllUsers() {
        storage.addUser("user1");
        storage.addUser("user2");
        assertEquals(2, storage.getAllUsers().size());
        assertTrue(storage.getAllUsers().containsKey("user1"));
        assertTrue(storage.getAllUsers().containsKey("user2"));
    }
}