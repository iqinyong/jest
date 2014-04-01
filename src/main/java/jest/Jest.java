package jest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by qye on 4/1/2014.
 todo: http://twitter.github.io/twitter-server/
 Features
 Flags
 Logging
 Metrics
 HTTP Admin interface
 Lifecycle Management
 Extension

 Guice
 */
public class Jest extends ServiceImpl {
  String host = "localhost";
  int port = 8888;

  JestHandler handler = new JestHandler(this);
  HttpServerCodec serverCodec = new HttpServerCodec();
  HttpObjectAggregator aggregator = new HttpObjectAggregator(1048576);

  public Jest(String host, int port) {
    super("jest");
    this.host = host;
    this.port = port;
  }

  public Jest() {
    this(8888);
  }


  public Jest(int port) {
    this("localhost", port);
  }

  public void start() throws InterruptedException {
    // Configure the server.
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new JestChannelInitializer());

      Channel ch = b.bind(host, port).sync().channel();
      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  class JestChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
      // Create a default pipeline implementation.
      ChannelPipeline p = ch.pipeline();

      // Uncomment the following line if you want HTTPS
      //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
      //engine.setUseClientMode(false);
      //p.addLast("ssl", new SslHandler(engine));

      p.addLast("decoder", serverCodec);
      // Uncomment the following line if you don't want to handle HttpChunks.
      p.addLast("aggregator", aggregator);
      // Remove the following line if you don't want automatic content compression.
      //p.addLast("deflater", new HttpContentCompressor());
      p.addLast("handler", handler);
    }
  }
}
