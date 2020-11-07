package com.project.core.protocol;


import java.io.Serializable;

/**
 * Simple protocol to provide communication between server-client
 */
public abstract class Protocol implements Serializable {

    /**
     * Type of the request
     */
    final RequestType requestType;

    Protocol(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
