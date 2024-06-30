package com.switchvov.magiccache.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author switch
 * @since 2024/06/14
 */
@Slf4j
public class MagicCacheDecoder extends ByteToMessageDecoder {
    private static final AtomicLong COUNTER = new AtomicLong();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info(" ===>[MagicCache] CacheDecoder decode count: {}", COUNTER.incrementAndGet());
        if (in.readableBytes() <= 0) {
            return;
        }
        int count = in.readableBytes();
        int index = in.readerIndex();
        log.info(" ===>[MagicCache] CacheDecoder count: {} index: {}", count, index);

        byte[] bytes = new byte[count];
        in.readBytes(bytes);
        String ret = new String(bytes);
        log.info(" ===>[MagicCache] CacheDecoder ret: {}", ret);

        out.add(ret);
    }
}
