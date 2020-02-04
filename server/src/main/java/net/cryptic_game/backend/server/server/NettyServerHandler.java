package net.cryptic_game.backend.server.server;

import net.cryptic_game.backend.base.netty.EventLoopGropHandler;
import net.cryptic_game.backend.base.netty.NettyCodec;

import java.util.HashSet;
import java.util.Set;

public class NettyServerHandler {

    private final Set<NettyServer> servers;
    private final EventLoopGropHandler eventLoopGropHandler;

    public NettyServerHandler() {
        this.servers = new HashSet<>();
        this.eventLoopGropHandler = new EventLoopGropHandler();
    }

    public void addServer(final String name, final String host, final int port, final NettyCodec nettyCodec) {
        this.servers.add(new NettyServer(name, host, port, this.eventLoopGropHandler, nettyCodec));
    }

    public void start() {
        this.servers.forEach(NettyServer::start);
    }
}
