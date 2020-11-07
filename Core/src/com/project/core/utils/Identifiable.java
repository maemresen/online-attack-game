package com.project.core.utils;

/**
 * Simple identifiable object.
 */
public interface Identifiable {

    /**
     * To check if the object has id of {@param id}
     *
     * @param id id will be controlled with the object
     * @return is the same id or not
     */
    boolean hasId(String id);
}
