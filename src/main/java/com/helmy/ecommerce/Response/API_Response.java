package com.helmy.ecommerce.Response;

import lombok.Data;
@Data
public class API_Response {
    private String message;
    private Object data;

    public API_Response() {

    }

    public API_Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
