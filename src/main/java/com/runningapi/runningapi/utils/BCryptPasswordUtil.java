package com.runningapi.runningapi.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPasswordUtil {

    /**
     * Hashes a password using BCrypt.
     *
     * @param password the password to hash
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks if a raw password matches a hashed password.
     *
     * @param rawPassword the raw password
     * @param hashedPassword the hashed password
     * @return true if they match, false otherwise
     */
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
