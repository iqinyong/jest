package jest;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * Created by qye on 4/1/2014.
 */
public class HttpRequestImpl extends HttpMessageImpl implements HttpRequest {

  FullHttpRequest request;

  public HttpRequestImpl(FullHttpRequest request) {
    super(request);
    this.request = request;
  }

  @Override
  public HttpMethod getMethod() {
    return request.getMethod();
  }

  @Override
  public HttpRequest setMethod(HttpMethod method) {
    request.setMethod(method);
    return this;
  }

  @Override
  public String getUri() {
    return request.getUri();
  }

  @Override
  public HttpRequest setUri(String uri) {
    request.setUri(uri);
    return this;
  }

  @Override
  public List<Cookie> getCookies() {
    // todo: implement it
    return null;
  }

  @Override
  public ByteBuf getBody() {
    return request.content();
  }
}
