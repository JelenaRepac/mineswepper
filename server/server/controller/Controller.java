/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dbb.impl.RepositoryDBGeneric;
import domain.GenericEntity;
import operation.AbstractGenericOperation;
import operation.impl.ChangeCredentialsOperation;
import operation.impl.LoginOperation;
import operation.impl.SignupOperation;
import repository.Repository;

import java.sql.SQLException;

public class Controller {
    private static Controller instance;
    private Repository repository;
    
    private Controller() {
        repository = new RepositoryDBGeneric();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public GenericEntity signup(GenericEntity entity) throws SQLException, Exception {
        AbstractGenericOperation so = new SignupOperation();
        so.execute(entity);
        return ((SignupOperation) so).getObject();
    }
    
    public GenericEntity login(GenericEntity entity) throws SQLException, Exception {
        AbstractGenericOperation so = new LoginOperation();
        so.execute(entity);
        return ((LoginOperation) so).getObject();
    }

    public GenericEntity changeCredentials(GenericEntity entity) throws SQLException, Exception {
        AbstractGenericOperation so = new ChangeCredentialsOperation();
        so.execute(entity);
        return ((ChangeCredentialsOperation) so).getObject();
    }
}
