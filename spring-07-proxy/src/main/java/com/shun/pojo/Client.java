package com.shun.pojo;

public class Client {
    public static void main(String[] args) {
        Host host = new Host();

        ProxyInvocationHandler pih = new ProxyInvocationHandler(host);

        Rent proxy = (Rent)pih.getProxy();

        proxy.rent();
    }
}
