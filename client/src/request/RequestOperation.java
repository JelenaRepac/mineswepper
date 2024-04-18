/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import java.io.Serializable;


public enum RequestOperation implements Serializable{
    LOGIN,
    SIGNUP,
    SEND_MOVE,
    RECEIVE_MOVE,
    PLAY_GAME,
    CHANGE_CREDENTIALS
}
