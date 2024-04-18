/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.impl;

import domain.GenericEntity;
import domain.Player;
import operation.AbstractGenericOperation;


public class LoginOperation extends AbstractGenericOperation<Player, Player> {
    
    GenericEntity object = null;
    
    @Override
    protected void preconditions(Player entity) throws Exception {
        if(entity.getname() == null || entity.getname().isEmpty()) {
            throw new Exception("name is required");
        }
        if(entity.getPassword()== null || entity.getPassword().isEmpty()) {
            throw new Exception("Password is required");
        }
    }

    @Override
    protected void executeOperation(Player entity) throws Exception {
        Player  player = (Player) repository.findRecord(new Player(entity.getname()));
        if(player != null) {
            if(!player.getPassword().equals(entity.getPassword())) {
                throw new RuntimeException("Bad password!");
            }
            object = player;
        } else {
           throw new RuntimeException("Invalid name!");
        }
    }

    public GenericEntity getObject() {
        return object;
    }
}
