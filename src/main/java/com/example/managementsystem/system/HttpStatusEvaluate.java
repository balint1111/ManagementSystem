package com.example.managementsystem.system;

import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.response.BaseRestResponse;
import org.springframework.http.HttpStatus;

public class HttpStatusEvaluate {

    protected HttpStatusEvaluate() {
        throw new IllegalStateException("Utility class");
    }

    public static HttpStatus evaluate(BaseRestResponse response) {
        if (response != null && response.getStatus() != null) {
            String status = response.getStatus();
            return getHttpStatus(status);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private static HttpStatus getHttpStatus(String statusString) {
        if (CommonStatus.OK.toString().equals(statusString)) {
            return HttpStatus.OK;
        } else if (CommonStatus.NOT_FOUND.toString().equals(statusString)) {
            return HttpStatus.NOT_FOUND;
        } else if (CommonStatus.INVALID_INPUT.toString().equals(statusString)) {
            return HttpStatus.BAD_REQUEST;
        } else if (CommonStatus.FORBIDDEN.toString().equals(statusString)
                || CommonStatus.BAD_CREDENTIALS.toString().equals(statusString)) {
            return HttpStatus.FORBIDDEN;
        } else if (CommonStatus.UNAUTHORIZED.toString().equals(statusString)) {
            return HttpStatus.UNAUTHORIZED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}