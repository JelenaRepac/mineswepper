package operation.impl;

import domain.GenericEntity;
import domain.Player;
import operation.AbstractGenericOperation;


public class ChangeCredentialsOperation extends AbstractGenericOperation<Player,Player> {
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
           repository.updateRecord(entity, player.getId());

            object = entity;
        } else {
            throw new RuntimeException("ERROR");
        }
    }



    public GenericEntity getObject() {
        return null;
    }
}
