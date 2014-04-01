package jest;

import io.netty.handler.codec.http.*;

/**
 * Created by qye on 4/1/2014.
 */
public class HttpResponseImpl extends HttpMessageImpl implements HttpResponse {

  FullHttpResponse response;

  public HttpResponseImpl(FullHttpResponse response) {
    super(response);
    this.response = response;
  }

  @Override
  public HttpResponseStatus getStatus() {
    return response.getStatus();
  }

  @Override
  public HttpResponse setStatus(HttpResponseStatus status) {
    response.setStatus(status);
    return this;
  }
}
