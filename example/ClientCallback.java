package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    // Server sẽ gọi hàm này của Client để hiển thị tin nhắn mới
    void receiveMessage(String message) throws RemoteException;
}