package com.codespacepro.whatsify.Repository;

import com.codespacepro.whatsify.Models.Chat;

import java.util.ArrayList;
import java.util.List;

public class LocalUserRepository {
    private static final List<Chat> USERS = new ArrayList<>();

    static {
        USERS.add(new Chat("1", "alice@example.com", "Alice", true));
        USERS.add(new Chat("2", "bob@example.com", "Bob", false));
        USERS.add(new Chat("3", "carol@example.com", "Carol", true));
        USERS.add(new Chat("4", "dave@example.com", "Dave", false));
    }

    public static List<Chat> getUsers() {
        return new ArrayList<>(USERS);
    }

    public static Chat findByEmail(String email) {
        for (Chat c : USERS) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }
}
