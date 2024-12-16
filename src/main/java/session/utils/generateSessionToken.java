package session.utils;

import java.util.UUID;

public class generateSessionToken {
    public static String get(){
        UUID uuid = UUID.randomUUID();
         return uuid.toString().substring(0, 8);
    }

}