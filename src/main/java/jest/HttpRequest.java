package jest;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.HttpMethod;

import java.util.List;

/**
 * Created by qye on 3/31/2014.
 */
public interface HttpRequest extends HttpMessage{
  HttpMethod getMethod();
  HttpRequest setMethod(HttpMethod method);

  String getUri();
  HttpRequest setUri(String uri);

  List<Cookie> getCookies();
  ByteBuf getBody();
}
