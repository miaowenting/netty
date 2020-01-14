package io.netty.example.wulei;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 测试类
 * netty-server
 * @author wulei
 */
public class NettyServer {
	
	public static void main(String[] args) {
		EventLoopGroup parentGroup = new NioEventLoopGroup(1);
		EventLoopGroup childGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			final EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(100);
			serverBootstrap.group(parentGroup, childGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
			                         .option(ChannelOption.SO_KEEPALIVE, true)
			                         .childHandler(new ChannelInitializer<SocketChannel>() {
										@Override
										protected void initChannel(SocketChannel ch)
												throws Exception {
											ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters.lineDelimiter()[0]));
											ch.pipeline().addLast(eventExecutors, new SimpleHandler());
										}
									});
			
			ChannelFuture future = serverBootstrap.bind(8080).sync();
			future.channel().closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
		
		
		
		
	}

}
