package io.netty.example.wulei;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author: wulei
 * Version: 1.0
 * Create Date Time: 2019/12/29 5:43 PM.
 * Update Date Time:
 */
public class RouterServerHandler extends ChannelInboundHandlerAdapter {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();
    PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf reqMsg = (ByteBuf) msg;
        final byte[] body = new byte[reqMsg.readableBytes()];
        executorService.execute(() -> {
            ByteBuf rspMsg = allocator.heapBuffer(body.length);
            rspMsg.writeBytes(body);
            ctx.writeAndFlush(rspMsg);
        });
    }



}
