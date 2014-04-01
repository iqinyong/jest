package jest;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URL;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by qye on 4/1/2014.
 */
public class JestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  Jest jest;

  public JestHandler(Jest jest) {
    this.jest = jest;
  }

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
    if (!request.getDecoderResult().isSuccess()) {
      sendError(ctx, BAD_REQUEST);
      return;
    }

    URL url = new URL(request.getUri());
    HttpRequestImpl requestImpl = new HttpRequestImpl(request);
    DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
    HttpResponseImpl responseImpl = new HttpResponseImpl(fullHttpResponse);

    // todo: check corner conditions
    String path = url.getPath();
    String query = url.getQuery();
    String[] dirs;
    if(path.endsWith(Constants.SEARCH_STR)) {
      requestImpl.setMethod(Constants.SEARCH);
      path = path.substring(0, path.length() - Constants.SEARCH_STR.length());
      dirs = path.split("/");
    }else {
      dirs = path.split("/");
    }

    if(!jest.validate(dirs, 0)) {
      sendError(ctx, BAD_REQUEST);
      return;
    }

    jest.apply(requestImpl, responseImpl, dirs, 0, query);
  }

  private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
    FullHttpResponse response = new DefaultFullHttpResponse(
        HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
    response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

    // Close the connection as soon as the error message is sent.
    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
  }
}