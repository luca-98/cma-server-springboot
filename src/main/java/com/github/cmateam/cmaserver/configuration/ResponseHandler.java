package com.github.cmateam.cmaserver.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// Customize lại response trước khi gửi lại cho client 
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (!selectedContentType.getSubtype().equals("json")) {
            return body;
        }
        try {
            Map<?, ?> map = (Map<?, ?>) body;
            map.get("timestamp");
            map.get("status");
            map.get("error");
            map.get("message");
            map.get("path");
            return body;
        } catch (Exception e) {
            // logger.info("The object was not created from Spring error handler");
        }

        HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        int status = res.getStatus();
        String error = HttpStatus.getStatusText(status);
        String path = req.getRequestURI();

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String nowAsISO = sdf.format(date);

        LinkedHashMap<String, Object> responseWrapper = new LinkedHashMap<>();
        responseWrapper.put("timestamp", nowAsISO);
        responseWrapper.put("status", status);
        responseWrapper.put("error", error);
        responseWrapper.put("message", body);
        responseWrapper.put("path", path);

        return responseWrapper;
    }
}
