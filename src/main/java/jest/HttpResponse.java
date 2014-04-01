package jest;

import io.netty.handler.codec.http.*;

/**
 * Created by qye on 3/31/2014.
 */
public interface HttpResponse {
  HttpResponseStatus getStatus();
  HttpResponse setStatus(HttpResponseStatus status);
}
