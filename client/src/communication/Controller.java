package communication;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import domain.Player;
import request.Request;
import request.RequestOperation;
import response.Response;
import response.ResponseStatus;


public class Controller {
    private static Controller instance;
    private final SocketCommunication socketCommunication;
    
    private Controller() {
        socketCommunication = new SocketCommunication();
    }
     
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public Player signup(String nickaname, String password) throws Exception {
        Player player = new Player(-1L, nickaname, password);
        Request request = new Request(RequestOperation.SIGNUP, player);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Player)response.getResult();
        }
        throw response.getException();
    }
    
    public Player login(String nickaname, String password) throws Exception {
        Player player = new Player(-1L, nickaname, password);
        Request request = new Request(RequestOperation.LOGIN, player);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Player) response.getResult();
        }
        throw response.getException();
    }
    public Player changeCredentials(String name, String password) throws Exception {
        Player player = new Player(-1L, name, password);
        Request request = new Request(RequestOperation.CHANGE_CREDENTIALS, player);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Player) response.getResult();
        }
        throw response.getException();
    }


}
