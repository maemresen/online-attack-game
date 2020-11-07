package com.project.core.protocol;

import com.project.core.Connection;
import sun.misc.Queue;

import java.io.*;
import java.net.Socket;

/**
 * Simple IO operations under server-client connection...
 */
public class IOModule {

    // connection
    private Connection connection;

    // connection is dead or not
    private boolean dead;

    /*---------------------------------------
        I/O Streams
    ---------------------------------------*/

    // input stream
    private InputStream inputStream;

    // output stream
    private OutputStream outputStream;


    /*---------------------------------------
        Queue for I/O
    ---------------------------------------*/

    // for inputs comes from the other side
    private final Queue<Object> inputQueue = new Queue<>();

    // for outputs will be sent to the other side
    private final Queue<Object> outputQueue = new Queue<>();


    /**/
    public IOModule(Socket socket, Connection connection) throws IOException {
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        this.dead = false;
        this.connection = connection;
    }

    /* Queue */
    public void startQueueListener() {

        new Thread(() -> {

            // to listen inputs and add them into the input queue
            while (true) {

                if (dead) {
                    return;
                }

                Object obj = waitObject();
                if (obj == null) {
                    continue;
                }
                inputQueue.enqueue(obj);
            }
        }).start();

        new Thread(() -> {

            // to get next output from the output queue and sent to the other side
            while (true) {

                if (dead) {
                    return;
                }
                Object obj;
                try {
                    obj = outputQueue.dequeue();
                    sendObject(obj);
                } catch (InterruptedException | IOException ignored) {
                }
            }

        }).start();
    }

    /* Input */

    /**
     * To wait an object to read from the other side
     *
     * @return read object
     */
    private Object waitObject() {

        do {
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(inputStream);
                return ois.readObject();
            } catch (IOException | ClassNotFoundException ignored) {
                closeConnection();
            }
        } while (dead);
        return null;

    }


    /* Output */

    /**
     * To send {@param obj} to the other side
     *
     * @param obj object will be sent to the other side
     * @throws IOException ...
     */
    private void sendObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(obj);
    }

    /* Dequeue Input */

    /**
     * To get next input from the input queue
     *
     * @return next input comes from the other side
     * @throws InterruptedException ...
     */
    public Object getNext() throws InterruptedException {
        return inputQueue.dequeue();
    }

    /* Enqueue Output */

    /**
     * To add {@param obj} to the output queue to sent it later
     *
     * @param obj object will be added to the queue
     */
    public void send(Object obj) {
        outputQueue.enqueue(obj);
    }

    /**/

    /**
     * @return is connection dead or not
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * to close I/O operations and connection
     */
    public void closeConnection() {

        if (dead) {
            return;
        }
        dead = true;
        connection.closeConnection();

    }

    @Override
    public String toString() {
        return "IOModule{" +
                "inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                ", dead=" + dead +
                ", inputQueue=" + inputQueue +
                ", outputQueue=" + outputQueue +
                '}';
    }
}
