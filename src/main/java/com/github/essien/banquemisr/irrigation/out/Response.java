package com.github.essien.banquemisr.irrigation.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Status status;
    private final String message;
    private final String errorId;
    private final Object data;

    private Response(Status status, String message, String errorId, Object data) {
        this.status = status;
        this.message = message;
        this.errorId = errorId;
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorId() {
        return errorId;
    }

    public Object getData() {
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Status status;
        private String message;
        private String errorId;
        private Object data;

        private Builder() {
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withErrorId(String errorId) {
            this.errorId = errorId;
            return this;
        }

        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public Response build() {
            return new Response(status, message, errorId, data);
        }
    }
}
