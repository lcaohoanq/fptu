/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import models.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class UserDAO{
    
    public UserDTO login(String username, String password){
        
        UserDTO user = null;
        try {

                Connection con = DBUtils.getConnection();            
                String sql = " SELECT * FROM users ";
                sql += " WHERE username = ?  AND password = ?";
                               
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs != null){
                    if (rs.next()){
                        
                        user = new UserDTO();                        
                        user.setUsername(rs.getString("username"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                    }
                }
                con.close();
            } catch (SQLException ex) {                
                System.out.println("Error in servlet. Details:" + ex.getMessage());
                ex.printStackTrace();
                
            }
            return user;
        
    }
    
    public static void main(String[] args) {
        try {
            
            System.out.println(new UserDAO().login("dung", "dung"));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
