package org.wisdom.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

import java.net.URI;

import static io.netty.handler.codec.http.HttpUtil.is100ContinueExpected;

/**
 * @Description:
 * @author: 59823
 * @Date: 2020-07-12 11:51
 */

/*
说明
1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final Logger logger = Logger.getLogger(this.getClass());


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
        logger.error("current clientNum = " + "exception = {} ", cause);
        ReferenceCountUtil.release(cause);
    }

    //channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //100 Continue
        if (is100ContinueExpected(msg)) {
            ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
        }
        // // logger.debug("对应的channel=" + ctx.channel() + " pipeline=" + ctx.pipeline() + " 通过pipeline获取channel" + ctx.pipeline().channel());
        // // logger.debug("当前ctx的handler=" + ctx.handler());

        //判断 msg 是不是 httprequest请求

        // logger.debug("ctx 类型=" + ctx.getClass());

        // logger.debug("pipeline hashcode" + ctx.pipeline().hashCode() + " TestHttpServerHandler hash=" + this.hashCode());

        // logger.debug("msg 类型=" + msg.getClass());
        // logger.debug("客户端地址" + ctx.channel().remoteAddress());

        //获取到
        // HttpRequest httpRequest = (HttpRequest) msg;
        // //获取uri, 过滤指定的资源
        // URI uri = new URI(httpRequest.uri());
        // if ("/favicon.ico".equals(uri.getPath())) {
        // //     logger.debug("请求了 favicon.ico, 不做响应");
        //     return;
        // }
        // //回复信息给浏览器 [http协议]
        // // logger.debug(httpRequest);
        ByteBuf content = msg.content().copy();
        // Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
        // httpRequest.
        // String s = ((AggregatedFullHttpRequest) httpRequest).content().toString(CharsetUtil.UTF_8);
        //构造一个http的相应，即 httpresponse
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        //将构建好 response返回
        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);

    }


}
