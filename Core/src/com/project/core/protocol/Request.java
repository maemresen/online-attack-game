package com.project.core.protocol;

import com.project.core.protocol.Protocol;
import com.project.core.protocol.RequestType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple Request Protocol that Client use to send request to server
 */
public class Request extends Protocol {


    // parameters of the request
    private final List<Object> parameterList = new ArrayList<>();

    /**
     * @param requestType type of the request
     * @param parameters  parameters sent with request
     */
    public Request(RequestType requestType, Serializable... parameters) {
        super(requestType);
        parameterList.addAll(Arrays.asList(parameters));
    }

    /**/
    public List<Object> getParameterList() {
        return parameterList;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", parameterList=" + parameterList.toString() +
                '}';
    }
}