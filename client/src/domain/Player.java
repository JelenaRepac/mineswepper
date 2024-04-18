/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Player implements GenericEntity{

    private Long id;
    private String name;
    private String password;

    public Player(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Player(String name) {
        this.name = name;
    }

    public Player() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player: " + name;
    }

    @Override
    public String getClassName() {
        return "player";
    }

    @Override
    public String getAtrValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(name).
                append("', '").append(password).
                append("'");
        return sb.toString();
    }

    @Override
    public String getAtrNames() {
        return "name,password";
    }

    @Override
    public String setAtrValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getWhereCondition() {
        return "name='" + name + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) {
        try {
            long dbId = rs.getLong("id");
            String dbname = rs.getString("name");
            String dbPassword = rs.getString("password");

            return new Player(dbId, dbname, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
