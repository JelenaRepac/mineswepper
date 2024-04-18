package start;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;

import client.HandleClientRequest;


public class Server extends Thread{




    private int port;
    private ServerSocket serverSocket;
    private ArrayList<HandleClientRequest> players;


    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        this.players = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                HandleClientRequest handleClientRequest = new HandleClientRequest(socket);
                players.add(handleClientRequest);
                handleClientRequest.start();
            }
            System.out.println("No longer accepting connections.");
        } catch (Exception ex) {
            System.out.println("Server has been stopped!");
        }
    }

    public void stopServer() throws IOException {
        serverSocket.close();
        for (HandleClientRequest player : players) {
            player.getSocket().close();

        }
    }
}
