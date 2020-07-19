package org.wisdom.netty.http;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.apache.log4j.Logger;

/**
 * @Description:
 * @author: 59823
 * @Date: 2020-07-12 11:47
 */
public class NioHttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
        //HttpServerCodec 说明
        //1. HttpServerCodec 是netty 提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("httpAggregator", new HttpObjectAggregator(512 * 10240)); // http 消息聚合器
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

        logger.info("ok~~~~");
    }
}
