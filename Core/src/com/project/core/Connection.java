package com.project.core;


import com.project.core.protocol.IOModule;
import com.project.core.utils.Identifiable;
import com.project.core.utils.IdentifierHelper;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Models the both connections server-client and client-server
 */
public abstract class Connection extends Thread implements Serializable, Identifiable {

    // stores id and name of the connection
    private Person person;

    /* Provides Simple IO operations */
    protected IOModule ioModule;

    // id of the connection
    private final String connectionId;
    // name of the connection
    protected String connectionName;


    protected Connection(Socket socket) throws IOException {
        connectionId = IdentifierHelper.generateIdentifier();
        ioModule = new IOModule(socket, this);
    }


    /**
     * To close connection
     */
    public void closeConnection() {
        ioModule.closeConnection();
    }

    /**
     * @return is connection dead or not
     */
    protected boolean isDead() {
        return ioModule.isDead();
    }


    @Override
    public void run() {
        ioModule.startQueueListener();
        initConnection();
        initConnectionLoop();
    }

    /**
     * To Initialize Connection
     */
    protected abstract void initConnection();

    /**
     * To set Connection loop
     */
    protected abstract void initConnectionLoop();

    /* Setters */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**/
    String getConnectionId() {
        return connectionId;
    }

    String getConnectionName() {
        return connectionName;
    }

    /**
     * @return Id and Name of the connection under instance of person
     */
    public Person getPerson() {
        if (person == null) {
            person = new Person(this);
        }
        return person;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Connection)) return false;
        Connection that = (Connection) object;
        return connectionId.equals(that.connectionId);
    }


    @Override
    public boolean hasId(String id) {
        return connectionId.equalsIgnoreCase(id);
    }


    @Override
    public String toString() {
        return getPerson().toString();
    }


    /*-------------------------------------------------------------------------------------------
        * To Control Messages sent by the other side
        * For the server side, using to arrange public messages will be sent to the client
        * For the client side, using to arrange public messages received from the server
    -------------------------------------------------------------------------------------------*/

    /* Messages */
    private final LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public void addMessage(String message) {
        if (messageQueue.contains(message)) {
            return;
        }

        messageQueue.add(message);
    }

    public String getMessage() {
        return messageQueue.poll();
    }


    /* In-Game Messages */
    private final LinkedBlockingQueue<String> gameMessageQueue = new LinkedBlockingQueue<>();

    public void addGameMessage(String message) {
        if (gameMessageQueue.contains(message)) {
            return;
        }

        gameMessageQueue.add(message);
    }

    public String getGameMessage() {

        return gameMessageQueue.poll();

    }


    /*---------------------------------------------------------------------------------------------------
        * To Control Connected and Disconnected Clients
        * For the server side, using to arrange new/dead connections info will be sent to the client
        * For the client side, using to arrange new/dead connections info received from the server
    ---------------------------------------------------------------------------------------------------*/

    /* New Connections */
    private final LinkedBlockingQueue<Person> newConnectionQueue = new LinkedBlockingQueue<>();

    public final void addNewConnection(Person person) {
        newConnectionQueue.add(person);
    }

    public final Person getNewConnection() {
        return newConnectionQueue.poll();
    }

    /* Disconnections */
    private final LinkedBlockingQueue<Person> deadConnectionQueue = new LinkedBlockingQueue<>();

    public final void addDeadConnection(Person person) {
        deadConnectionQueue.add(person);
    }

    public final Person getDeadConnection() {
        return deadConnectionQueue.poll();
    }


}
