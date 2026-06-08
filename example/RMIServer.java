package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ChatService server = new ChatServiceImpl();
            registry.rebind("ChatRoomService", server);
            System.out.println("Server ChatRoom da san sang tren cong 1099...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}