package model;

import io.netty.channel.Channel;


import java.util.concurrent.ConcurrentHashMap;

public class ClientMapSingleton {
    public static volatile ClientMapSingleton instance;
    // volatile 保证了变量的修改对所有线程立即可见，还能防止指令重排序。编译器和处理器可能会对指令进行重排序以优化性能，但这可能导致多线程环境下的问题。volatile 确保在读写操作前后，指令不会被重排序。
    // 存储客户端连接的 Map
    private final ConcurrentHashMap<String, Channel> clients;

    // 私有构造函数，防止外部实例化
    private ClientMapSingleton() {
        clients = new ConcurrentHashMap<>();
    }
    public static ClientMapSingleton getInstance() {
        if (instance == null) {
            synchronized (ClientMapSingleton.class) {
                if (instance == null) {
                    instance = new ClientMapSingleton();
                }
            }
        }
        return instance;
    }
    public ConcurrentHashMap<String, Channel> getAllClients() {
        return clients;
    }
    public void addClient(String clientId, Channel channel) {
        clients.put(clientId, channel);
    }
    public void removeClient(String clientId) {
            clients.remove(clientId);
    }
    public Channel getClient(String clientId) {
        return clients.get(clientId);
    }
}
