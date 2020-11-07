package com.project.core.utils;

public class IdentifierHelper {

    /**
     * Stores Next Identifier
     */
    private static int COUNTER = 0;


    /**
     * To generate unique identifiers
     * <p>
     * When and identifier is requested it gets the value at COUNTER and increment COUNTER
     * So that, everything that calls the method gets and unique identifier
     *
     * @return new identifier
     */
    public synchronized static String generateIdentifier() {
        return String.valueOf(++COUNTER);
    }


}
