package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhap ten cua ban: ");
            String name = sc.nextLine();

            // Kết nối tới Server trên localhost
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatService server = (ChatService) registry.lookup("ChatRoomService");

            // Tạo đối tượng Callback để nhận tin nhắn từ Server đổ về
            ClientCallback callback = new ClientCallback() {
                @Override
                public void receiveMessage(String message) throws RemoteException {
                    // In tin nhắn nhận được ra màn hình
                    System.out.println("\n" + message);
                    System.out.print("Nhap tin nhan: "); // Giữ giao diện nhập
                }
            };

            // Xuất bản đối tượng callback từ xa
            UnicastRemoteObject.exportObject(callback, 0);

            // Đăng ký tên với Server
            server.registerClient(name, callback);

            // Vòng lặp gửi tin nhắn
            while (true) {
                System.out.print("Nhap tin nhan: ");
                String msg = sc.nextLine();

                if (msg.equalsIgnoreCase("exit")) {
                    server.unregisterClient(name);
                    break;
                }
                
                // Gửi tin nhắn lên Server để phát đi
                server.broadcastMessage(name, msg);
            }

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}