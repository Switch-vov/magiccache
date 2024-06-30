package com.switchvov.magiccache.server;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.Commands;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author switch
 * @since 2024/06/14
 */
@Slf4j
public class MagicCacheHandler extends SimpleChannelInboundHandler<String> {
    private static final String CRLF = "\r\n";
    private static final String SIMPLE_STR_PREFIX = "+";
    private static final String BULK_STR_PREFIX = "$";
    private static final String ARRAY_PREFIX = "*";
    private static final String INTEGER_PREFIX = ":";
    private static final String ERROR_PREFIX = "-";

    private static final String STR_NULL = BULK_STR_PREFIX + "-1";
    private static final String STR_EMPTY = BULK_STR_PREFIX + "0" + CRLF;

    private static final String ARRAY_NULL = ARRAY_PREFIX + "-1";
    private static final String ARRAY_EMPTY = ARRAY_PREFIX + "0";

    private static final String OK = "OK";

    private final MagicCache cache;

    public MagicCacheHandler(MagicCache cache) {
        this.cache = cache;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String[] args = msg.split(CRLF);
        log.info(" ===>[MagicCache] CacheHandler received: {}", String.join(",", args));
        String cmd = args[2].toUpperCase();

        Command command = Commands.get(cmd);
        if (Objects.nonNull(command)) {
            try {
                Reply<?> reply = command.exec(cache, args);
                log.info(" ===>[MagicCache] CMD[{}] => type:{} value:{}", cmd, reply.getType(), reply.getValue());
                replyContext(ctx, reply);
            } catch (Exception e) {
                Reply<String> reply = Reply.error("EXP exception with msg: '" + e.getMessage() + "'");
                replyContext(ctx, reply);
            }
        } else {
            Reply<String> reply = Reply.error("ERR unsupported command: " + cmd);
            replyContext(ctx, reply);
        }
    }

    private void replyContext(ChannelHandlerContext ctx, Reply<?> reply) {
        switch (reply.getType()) {
            case INT -> integer(ctx, (int) reply.getValue());
            case ERROR -> error(ctx, (String) reply.getValue());
            case SIMPLE_STRING -> simpleString(ctx, (String) reply.getValue());
            case BULK_STRING -> bulkString(ctx, (String) reply.getValue());
            case ARRAY -> array(ctx, (String[]) reply.getValue());
            default -> simpleString(ctx, OK);
        }
    }

    private void array(ChannelHandlerContext ctx, String[] array) {
        writeByteBuf(ctx, arrayEncode(array));
    }

    private static String arrayEncode(Object[] array) {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(array)) {
            sb.append(ARRAY_NULL + CRLF);
        } else if (array.length == 0) {
            sb.append(ARRAY_EMPTY + CRLF);
        } else {
            sb.append(ARRAY_PREFIX + array.length + CRLF);
            for (Object obj : array) {
                if (Objects.isNull(obj)) {
                    sb.append(STR_NULL + CRLF);
                    continue;
                }
                if (obj instanceof Integer i) {
                    sb.append(integerEncode(i));
                    continue;
                }
                if (obj instanceof String s) {
                    sb.append(bulkStringEncode(s));
                    continue;
                }
                if (obj instanceof Object[] os) {
                    sb.append(arrayEncode(os));
                    continue;
                }
            }
        }
        return sb.toString();
    }


    private void error(ChannelHandlerContext ctx, String msg) {
        writeByteBuf(ctx, errorEncode(msg));
    }

    private static String errorEncode(String msg) {
        return ERROR_PREFIX + msg + CRLF;
    }

    private void integer(ChannelHandlerContext ctx, int i) {
        writeByteBuf(ctx, integerEncode(i));
    }

    private static String integerEncode(Integer i) {
        return INTEGER_PREFIX + i + CRLF;
    }

    private void bulkString(ChannelHandlerContext ctx, String content) {
        writeByteBuf(ctx, bulkStringEncode(content));
    }

    private static String bulkStringEncode(String content) {
        return stringEncode(content, c -> BULK_STR_PREFIX + c.getBytes().length + CRLF + c);
    }

    private void simpleString(ChannelHandlerContext ctx, String content) {
        writeByteBuf(ctx, stringEncode(content));
    }

    private static String stringEncode(String content) {
        return stringEncode(content, c -> SIMPLE_STR_PREFIX + c);
    }

    private static String stringEncode(String content, Function<String, String> operator) {
        String ret;
        if (Objects.isNull(content)) {
            ret = STR_NULL;
        } else if (content.isEmpty()) {
            ret = STR_EMPTY;
        } else {
            ret = operator.apply(content);
        }
        return ret + CRLF;
    }

    private void writeByteBuf(ChannelHandlerContext ctx, String content) {
        log.info(" ===>[MagicCache] CacheHandler wrap byte buffer and reply: {}", content);
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }
}
