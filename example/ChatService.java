package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    // Client gọi hàm này để đăng ký tên và nhận tin nhắn
    void registerClient(String clientName, ClientCallback service) throws RemoteException;
    
    // Client gọi hàm này để hủy đăng ký khi thoát
    void unregisterClient(String clientName) throws RemoteException;
    
    // Client gọi hàm này để gửi tin nhắn lên Server, Server sẽ phát cho các máy khác
    void broadcastMessage(String clientName, String message) throws RemoteException;
}