package com.netty.rpc.client.handler;

import com.netty.rpc.codec.Beat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;
import java.time.LocalTime;

/**
 * 客户端心跳机制
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                System.out.println("10秒了，需要发送消息给服务端了" + LocalTime.now());
//                ByteBuf buffer = getByteBuf(ctx);
                //发送心跳消息，并在发送失败时关闭该连接
//                ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                ctx.writeAndFlush(Beat.BEAT_PING).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获的异常：" + cause.getMessage());
        ctx.channel().close();
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        String time = "heartbeat:客户端心跳数据：" + LocalTime.now();
        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = time.getBytes(Charset.forName("utf-8"));
        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
