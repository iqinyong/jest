package jest;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Created by qye on 4/1/2014.
 */
public interface HttpMessage {
  HttpVersion getHttpVersion();
  HttpMessage setHttpVersion(HttpVersion version);

  HttpHeaders getHeader();
  ByteBuf getContent();
}
