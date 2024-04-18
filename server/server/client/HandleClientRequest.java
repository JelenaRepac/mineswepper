/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controller.Controller;
import dbb.DBBConnectionFactory;
import domain.GenericEntity;
import request.Receiver;
import request.Request;
import response.Response;
import response.ResponseStatus;
import response.Sender;

import java.net.Socket;

public class HandleClientRequest extends Thread{
    private Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    


    public HandleClientRequest(Socket socket) {
        this.socket = socket;    
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    @Override
    public void run() {
        try {
            handleRequest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private void handleRequest() throws Exception {
        while (!isInterrupted()) {
            try{
                Request request = (Request) receiver.receive();
                Response response = new Response();
            
                try {
                    System.out.println("Operation: " + request.getOperation());
                    switch(request.getOperation()){
                            case SIGNUP:
                                try {
                                    GenericEntity object = Controller.getInstance().signup(request.getData());

                                    response.setResult(object);
                                    response.setStatus(ResponseStatus.SUCCESS);
                                } catch (Exception ex) {
                                    response.setStatus(ResponseStatus.ERROR);
                                    response.setException(ex);
                                }

                                sender.send(response);
                                break;
                            case LOGIN:
                                try {
                                    GenericEntity object = Controller.getInstance().login((GenericEntity) request.getData());

                                    response.setResult(object);
                                    response.setStatus(ResponseStatus.SUCCESS);

                                } catch (Exception ex) {
                                    response.setStatus(ResponseStatus.ERROR);
                                    response.setException(ex);
                                }

                                sender.send(response);
                                break;
                        case CHANGE_CREDENTIALS:
                            try {
                                GenericEntity object = Controller.getInstance().changeCredentials((GenericEntity) request.getData());

                                response.setResult(object);
                                response.setStatus(ResponseStatus.SUCCESS);

                            } catch (Exception ex) {
                                response.setStatus(ResponseStatus.ERROR);
                                response.setException(ex);
                            }

                            sender.send(response);

                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Player has disconnected.");
                DBBConnectionFactory.getInstance().closeConnection();
            }
        }
    }
}
