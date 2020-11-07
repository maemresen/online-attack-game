package com.project.server.request;

import com.project.core.protocol.RequestType;
import com.project.core.protocol.Response;
import com.project.server.ClientConnection;
import com.project.server.Shared;

import java.io.Serializable;

/**
 * Simple model to handle and get a response from the request
 *
 * @param <T> type of the value of response that obtained after handled the request
 */
public abstract class RequestHandler<T> implements Serializable {

    // type of the request
    private final RequestType requestType;

    // client that makes the request
    protected final ClientConnection clientConnection;

    /**/

    /**
     * @param requestType      type of the request
     * @param clientConnection client that makes the request
     */
    protected RequestHandler(RequestType requestType, ClientConnection clientConnection) {
        this.requestType = requestType;
        this.clientConnection = clientConnection;
    }

    /**/
    protected Shared getShared() {
        return clientConnection.getShared();
    }

    /**
     * @return the response of the request
     */
    public Response<T> getResponse() {
        T responseValue = handle();
        return new Response<>(requestType, responseValue);
    }

    /**
     * To print response to the console
     *
     * @param responseValue value of the response
     */
    protected void printResponse(T responseValue) {
        System.out.println("Response => ( owner={" + clientConnection.toString() + "}, type={" + requestType + "} => value={" + responseValue + "} )");
    }

    /**
     * To handle and get response from the request
     *
     * @return response value
     */
    protected abstract T handle();


}
