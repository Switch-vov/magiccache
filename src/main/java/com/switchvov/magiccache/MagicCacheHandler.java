package com.switchvov.magiccache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author switch
 * @since 2024/06/14
 */
@Slf4j
public class MagicCacheHandler extends SimpleChannelInboundHandler<String> {
    public static final String CRLF = "\r\n";
    public static final String OK = "+OK" + CRLF;
    public static final String PONG = "+PONG" + CRLF;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String[] args = msg.split(CRLF);
        log.info(" ===>[MagicCache] CacheHandler received: {}", String.join(",", args));
        String cmd = args[2].toUpperCase();

        if ("COMMAND".equals(cmd)) {
            writeByteBuf(ctx, "*2"
                    + CRLF + "$7"
                    + CRLF + "COMMAND"
                    + CRLF + "$4"
                    + CRLF + "PING"
                    + CRLF);
            return;
        } else if ("PING".equals(cmd)) {
            String ret = PONG;
            if (args.length >= 5) {
                ret = "+" + args[4] + CRLF;
            }
            writeByteBuf(ctx, ret);
            return;
        }
        writeByteBuf(ctx, OK);
    }

    private void writeByteBuf(ChannelHandlerContext ctx, String content) {
        log.info(" ===>[MagicCache] CacheHandler wrap byte buffer and reply: {}", content);
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }
}
