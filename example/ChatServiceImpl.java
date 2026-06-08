package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    // Lưu danh sách các Client đang online (Tên -> Đối tượng Callback)
    private final Map<String, ClientCallback> clients = new HashMap<>();

    public ChatServiceImpl() throws RemoteException {
        super();
        System.out.println("Server ChatRoom dang khoi tao...");
    }

    @Override
    public synchronized void registerClient(String clientName, ClientCallback client) throws RemoteException {
        clients.put(clientName, client);
        System.out.println("[" + clientName + "] vua tham gia phong chat.");
        broadcastMessage("HETHONG", clientName + " da tham gia phong chat!");
    }

    @Override
    public synchronized void unregisterClient(String clientName) throws RemoteException {
        clients.remove(clientName);
        System.out.println("[" + clientName + "] da roi phong chat.");
        broadcastMessage("HETHONG", clientName + " da roi phong chat.");
    }

    @Override
    public synchronized void broadcastMessage(String sender, String message) throws RemoteException {
        String formattedMessage = sender + ": " + message;
        
        System.out.println("[LOG SERVER] " + formattedMessage);
        
        // Gửi tin nhắn này tới tất cả mọi người đang online
        for (Map.Entry<String, ClientCallback> entry : clients.entrySet()) {
            try {
                entry.getValue().receiveMessage(formattedMessage);
            } catch (RemoteException e) {
                System.out.println("Loi gui tin toi " + entry.getKey() + ", tien hanh xoa.");
            }
        }
    }
}