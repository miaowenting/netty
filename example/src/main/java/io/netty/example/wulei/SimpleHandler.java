package io.netty.example.wulei;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * 测试类
 * netty-server's handler
 * read data
 * @author wulei
 */
public class SimpleHandler extends ChannelInboundHandlerAdapter{
	static ThreadPoolExecutor threadPoolExecutor =
			new ThreadPoolExecutor(10, 300, 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		threadPoolExecutor.execute(() -> {
			// 业务处理逻辑代码省略
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				//
			}
		});

		System.out.println("开始读取数据============");
		if(msg instanceof ByteBuf){
			ByteBuf req = (ByteBuf)msg;
			String content = req.toString(Charset.defaultCharset());
			System.out.println(content);


		}
		
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	
	
	

}
