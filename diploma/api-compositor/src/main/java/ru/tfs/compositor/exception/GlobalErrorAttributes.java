package ru.tfs.compositor.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = new LinkedHashMap<>();
        Throwable ex = super.getError(request);

        map.put("ERROR_CODE", getStatus());
        map.put("ERROR_MESSAGE", ex.getMessage());
        return map;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
