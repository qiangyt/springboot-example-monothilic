package qiangyt.springboot_example.common.rest;

import lombok.Getter;
import qiangyt.springboot_example.common.error.*;
import qiangyt.springboot_example.common.json.JsonHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/*
 *
 * @author
 *
 */
@Getter
public class RestClientBase {

    private final String baseUrl;

    private final boolean convertToInternalError;

    public RestClientBase(String baseUrl, boolean convertToInternalError) {
        this.baseUrl = normalizedUrl(baseUrl);
        this.convertToInternalError = convertToInternalError;
    }

    public String normalizedUrl(String url) {
        if (url == null) {
            return "";
        }

        int len = url.length();
        if (len == 0 || url.charAt(len - 1) != '/') {
            return url;
        }

        return url.substring(0, len - 1);
    }

    public String buildUrl(String path) {
        String base = getBaseUrl();
        if (base.length() == 0) {
            return path;
        }

        StringBuilder r = new StringBuilder(base.length() + "/".length() + path.length());
        r.append(base).append("/").append(path);
        return r.toString();
    }

    public HttpMessageConverter<?> messageConverter() {
        ObjectMapper mapper = JsonHelper.buildMapper();
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    public HttpHeaders createRequestHeaders() {
        HttpHeaders r = new HttpHeaders();
        r.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return r;
    }

    public <T> HttpEntity<T> createRequestEntity(T requestBody) {
        HttpHeaders headers = createRequestHeaders();
        return new HttpEntity<>(requestBody, headers);
    }

    protected <T> T convertResponse(ResponseEntity<T> response) {
        HttpStatus status = response.getStatusCode();
        if (status.is2xxSuccessful()) {
            return response.getBody();
        }

        BaseException ex;
        String msg = String.format("%d %s: %s", status.value(), status.name(), status.getReasonPhrase());

        if (isConvertToInternalError()) {
            ex = new InternalException(msg);
        } else {
            if( HttpStatus.FORBIDDEN.equals(status)) {
                ex = new ForbiddenException(msg);
            } else if( HttpStatus.NOT_FOUND.equals(status)) {
                ex = new NotFoundException(msg);
            } else if( HttpStatus.UNAUTHORIZED.equals(status)) {
                ex = new UnauthorizedException(msg);
            } else if( status.is4xxClientError()) {
                ex = new BadRequestException(msg);
            } else {
                ex = new InternalException(msg);
            }
        }

        throw ex;
    }

}
