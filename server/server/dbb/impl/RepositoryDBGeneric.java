/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbb.impl;

import dbb.DBBConnectionFactory;
import dbb.DBRepository;
import domain.GenericEntity;

import java.sql.*;


public class RepositoryDBGeneric implements DBRepository {

    @Override
    public GenericEntity findRecord(GenericEntity entity) throws SQLException {
        try {
            Connection connection = DBBConnectionFactory.getInstance().getConnection();
            String query = "SELECT * FROM " + entity.getClassName() + " WHERE " + entity.getWhereCondition();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            boolean signal = resultSet.next();
            if (signal == true) {
                return entity.getNewRecord(resultSet);
            }
            
            return null;
       
        } catch (SQLException ex) {
            throw new SQLException("Error retrieving user from database");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Long insertRecord(GenericEntity entity) throws SQLException, ClassNotFoundException {
        try {
            String query;
            Connection connection = DBBConnectionFactory.getInstance().getConnection();
            query = "INSERT INTO "+ entity.getClassName() + " (" + entity.getAtrNames() + ") VALUES (" + entity.getAtrValues() + ")";
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                return rs.getLong(1);
            } else {
                return 0L;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void updateRecord(GenericEntity entity, long recordId) throws SQLException, ClassNotFoundException {
        try {
            String query;
            Connection connection = DBBConnectionFactory.getInstance().getConnection();

            query = "UPDATE " + entity.getClassName() + " SET ";
            String[] attributeNames = entity.getAtrNames().split(",");
            String[] attributeValues = entity.getAtrValues().split(",");
            for (int i = 0; i < attributeNames.length; i++) {
                query += attributeNames[i].trim() + " = ?";
                if (i < attributeNames.length - 1) {
                    query += ", ";
                }
            }
            query += " WHERE id = ?";

            System.out.println(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(attributeValues);
            for (int i = 0; i < attributeValues.length; i++) {
                String attributeValue = attributeValues[i].trim().substring(1, attributeValues[i].length()-1);
                preparedStatement.setString(i + 1, attributeValue);

            }
            preparedStatement.setLong(attributeValues.length + 1, recordId);

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }




}
