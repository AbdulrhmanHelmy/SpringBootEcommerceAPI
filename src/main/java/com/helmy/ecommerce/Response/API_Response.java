package com.helmy.ecommerce.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
