package com.shareride.ridebooking.client.olamaps.exception;

import com.shareride.ridebooking.exception.ApplicationException;
import com.shareride.ridebooking.exception.ErrorCodes;

import java.util.Map;

public class OlaMapsClientException extends ApplicationException {

    public OlaMapsClientException(ErrorCodes errorCode, Map<String, Object> details, Throwable cause) {
        super(errorCode, details, cause);
    }
}
