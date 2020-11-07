package com.project.core.protocol;


/**
 * Simple Response Protocol that Server use to send response of the request made by Client
 *
 * @param <T> type of the value of the response
 */
public class Response<T> extends Protocol {

    private final T responseValue;


    public Response(RequestType requestType, T responseValue) {
        super(requestType);
        this.responseValue = responseValue;
    }


    public T getResponseValue() {
        return responseValue;
    }

    @Override
    public String toString() {
        return "GameResponse{" +
                "requestType=" + requestType.toString() +
                ", responseValue=" + responseValue.toString() +
                '}';
    }
}
