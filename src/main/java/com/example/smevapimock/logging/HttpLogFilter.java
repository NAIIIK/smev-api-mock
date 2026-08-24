package com.example.smevapimock.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class HttpLogFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger("HTTP");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request, 10_000);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(req, res);
        } finally {
            long tookMs = System.currentTimeMillis() - start;

            String reqBody = safeBytesToString(req.getContentAsByteArray(), req.getCharacterEncoding());
            String resBody = safeBytesToString(res.getContentAsByteArray(), res.getCharacterEncoding());

            Map<String, String> reqHeaders = headersMap(request);
            Map<String, String> resHeaders = headersMap(response);

            log.info(">> {} {} query={} headers={} body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    Optional.ofNullable(request.getQueryString()).orElse(""),
                    reqHeaders,
                    truncate(reqBody, 10_000));

            log.info("<< {} {} status={} took={}ms headers={} body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    tookMs,
                    resHeaders,
                    truncate(resBody, 10_000));

            res.copyBodyToResponse();
        }
    }

    private static String safeBytesToString(byte[] bytes, String charsetName) {
        if (bytes == null || bytes.length == 0) return "";
        try {
            java.nio.charset.Charset cs =
                    (charsetName != null && !charsetName.isBlank())
                            ? java.nio.charset.Charset.forName(charsetName)
                            : java.nio.charset.StandardCharsets.UTF_8;
            return new String(bytes, cs);
        } catch (Exception e) {
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    private static Map<String, String> headersMap(HttpServletRequest req) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String n = names.nextElement();
            map.put(n, req.getHeader(n));
        }
        return map;
    }

    private static Map<String, String> headersMap(HttpServletResponse res) {
        Map<String, String> map = new LinkedHashMap<>();
        for (String n : res.getHeaderNames()) {
            map.put(n, res.getHeader(n));
        }
        return map;
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max) + "...(truncated)";
    }
}