package jest;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

/**
 * Created by qye on 4/1/2014.
 */
public class HttpMessageImpl implements HttpMessage {
  FullHttpMessage message;

  public HttpMessageImpl(FullHttpMessage message) {
    this.message = message;
  }

  @Override
  public HttpVersion getHttpVersion() {
    return message.getProtocolVersion();
  }

  @Override
  public HttpMessage setHttpVersion(HttpVersion version) {
    message.setProtocolVersion(version);
    return this;
  }

  @Override
  public HttpHeaders getHeader() {
    return message.headers();
  }

  @Override
  public ByteBuf getContent() {
    return message.content();
  }
}
