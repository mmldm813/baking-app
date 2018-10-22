package com.example.michellemedina.bakingapp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockServerDispatcher {
    private String path;
    private int responseCode;
    private String body;

    public MockServerDispatcher(@Nullable String path, int responseCode, @Nonnull String body) {
        this.path = path;
        this.responseCode = responseCode;
        this.body = body;
    }

    /**
     * Return ok response from mock server
     */
    public class RequestDispatcher extends Dispatcher {
        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (path != null) {
                if (request.getPath().equals(path)) {
                    return createResponse();
                }
                return new MockResponse().setResponseCode(404);
            }
            return createResponse();
        }

        private MockResponse createResponse() {
            return new MockResponse().setResponseCode(responseCode).setBody(body);
        }
    }

    /**
     * Return error response from mock server
     */
    class ErrorDispatcher extends Dispatcher {
        @Override
        public MockResponse dispatch(RecordedRequest request) {
            return new MockResponse().setResponseCode(400);
        }
    }
}
