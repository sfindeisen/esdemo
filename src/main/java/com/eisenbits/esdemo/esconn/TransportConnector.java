package com.eisenbits.esdemo.esconn;

import java.net.InetSocketAddress;

import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class TransportConnector extends AbstractConnector {

    public TransportConnector(String host, int port) {
        super(host, port);
    }

    @Override
    protected AbstractClient createClient() {
        return TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, port)));
    }
}