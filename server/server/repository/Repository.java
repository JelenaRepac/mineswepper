/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;


import domain.GenericEntity;

import java.sql.SQLException;

public interface Repository {
    GenericEntity findRecord(GenericEntity entity) throws SQLException;
    Long insertRecord(GenericEntity entity) throws SQLException, ClassNotFoundException;

    void updateRecord(GenericEntity entity,long recordId) throws SQLException, ClassNotFoundException;

}
