package com.github.hotire.springkafka.interview.client;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @see java.nio.channels.spi.AbstractSelectableChannel
 * @see java.nio.channels.SocketChannel
 */
public class SocketChannelCore {

    /**
     * @see java.nio.channels.SelectionKey.OP_READ
     */
    private static int OP_READ;
    /**
     * @see java.nio.channels.SelectionKey.OP_WRITE
     */
    private static int OP_WRITE;
    /**
     * @see java.nio.channels.SelectionKey.OP_CONNECT
     */
    private static int OP_CONNECT;
    /**
     * @see java.nio.channels.SelectionKey.OP_ACCEPT
     */
    private static int OP_ACCEPT;


    /**
     * @see java.nio.channels.SocketChannel#register(Selector, int)
     */
    public final SelectionKey register(Selector sel, int ops) {
        return null;
    }
}
