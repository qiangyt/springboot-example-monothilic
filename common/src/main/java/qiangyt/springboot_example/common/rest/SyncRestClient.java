package qiangyt.springboot_example.common.rest;

import java.util.Map;
import java.util.function.Function;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import lombok.Getter;
import qiangyt.springboot_example.common.error.WrappedException;

/*
 *
 * @author
 *
 */
@Getter
public class SyncRestClient extends RestClientBase {

    private final RestTemplate restTemplate;

    public SyncRestClient(RestTemplateBuilder restTemplateBuilder, String baseUrl, boolean convertToInternalError) {
        super(baseUrl, convertToInternalError);
        this.restTemplate = templateBuilder(restTemplateBuilder).build();
    }

    public RestTemplateBuilder templateBuilder(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
            .messageConverters(messageConverter());
    }

    protected <REQ,RESP> RESP doSend(REQ requestBody, Function<HttpEntity<REQ>,ResponseEntity<RESP>> function) {
        ResponseEntity<RESP> resp;
        try {
            HttpEntity<REQ> reqEntity = createRequestEntity(requestBody);
            resp = function.apply(reqEntity);
        } catch (ResourceAccessException rae) {
            throw new WrappedException(rae);
        }

        return convertResponse(resp);
    }

    public <T> T send(String path, HttpMethod method, Object requestBody, Class<T> responseType, Object... uriVariables) {
        String url = buildUrl(path);
        return doSend(requestBody, reqEntity -> getRestTemplate().exchange(url, method, reqEntity, responseType, uriVariables));
    }

    public <T> T send(String path, HttpMethod method, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        String url = buildUrl(path);
        return doSend(requestBody, reqEntity -> getRestTemplate().exchange(url, method, reqEntity, responseType, uriVariables));
    }

    public <T> T GET(String path, Class<T> responseType, Object... uriVariables) {
        return GET(path, null, responseType, uriVariables);
    }

    public <T> T GET(String path, Class<T> responseType, Map<String, ?> uriVariables) {
        return GET(path, null, responseType, uriVariables);
    }

    public <T> T GET(String path, Object requestBody, Class<T> responseType, Object... uriVariables) {
        return send(path, HttpMethod.GET, requestBody, responseType, uriVariables);
    }

    public <T> T GET(String path, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        return send(path, HttpMethod.GET, requestBody, responseType, uriVariables);
    }

    public <T> T POST(String path, Object requestBody, Class<T> responseType, Object... uriVariables) {
        return send(path, HttpMethod.POST, requestBody, responseType, uriVariables);
    }

    public <T> T POST(String path, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        return send(path, HttpMethod.POST, requestBody, responseType, uriVariables);
    }

    public <T> T PUT(String path, Object requestBody, Class<T> responseType, Object... uriVariables) {
        return send(path, HttpMethod.PUT, requestBody, responseType, uriVariables);
    }

    public <T> T PUT(String path, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        return send(path, HttpMethod.PUT, requestBody, responseType, uriVariables);
    }

    public <T> T DELETE(String path, Class<T> responseType, Object... uriVariables) {
        return send(path, HttpMethod.DELETE, null, responseType, uriVariables);
    }

    public <T> T DELETE(String path, Object requestBody, Class<T> responseType, Object... uriVariables) {
        return send(path, HttpMethod.DELETE, requestBody, responseType, uriVariables);
    }

    public <T> T DELETE(String path, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        return send(path, HttpMethod.DELETE, requestBody, responseType, uriVariables);
    }

    public <T> T DELETE(String path, Class<T> responseType, Map<String, ?> uriVariables) {
        return send(path, HttpMethod.DELETE, null, responseType, uriVariables);
    }

}
