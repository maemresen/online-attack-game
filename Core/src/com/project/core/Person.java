package com.project.core;


import com.project.core.utils.Identifiable;

import java.io.Serializable;

/**
 * Models the connection
 */
public class Person implements Serializable, Identifiable {


    // health in the game
    private double health = 10.0;

    // connection id
    private final String connectionId;

    // connection name
    private final String name;

    /**/
    public Person(Connection connection) {
        this(connection.getConnectionId(), connection.getConnectionName());
    }

    private Person(String connectionId, String name) {
        this.connectionId = connectionId;
        this.name = name;
    }

    /* getters */

    public String getConnectionId() {
        return connectionId;
    }

    public double getHealth() {
        return health;
    }

    /* setters */
    public void removeHealth() {

        if (health <= 0) {
            return;
        }
        health -= 1;
    }

    public void addHealth() {
        health += 1;
    }

    /**
     * To reset health of the person
     */
    public void setHealth() {
        this.health = 10;
    }

    /**
     * @return to get name and health info
     */
    public String getInfo() {
        return name + "[" + health + "]";
    }

    /**/
    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (!(object instanceof Person)) return false;
        Person person = (Person) object;

        return (connectionId != null ? connectionId.equals(person.connectionId) : person.connectionId == null) &&
                (name != null ? name.equals(person.name) : person.name == null);
    }


    @Override
    public boolean hasId(String id) {
        return connectionId.equals(id);
    }

}
