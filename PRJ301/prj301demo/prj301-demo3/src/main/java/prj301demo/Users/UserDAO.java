/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301demo.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import prj301demo.utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class UserDAO {

    public UserDTO login(String username, String password) {

        UserDTO user = null;
        String sql = "SELECT username, password, name FROM users ";
        sql += " WHERE username = ? AND password = ?";
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            Connection conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, username);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        System.out.println(new UserDAO().login("dung", "dung"));
    }

}
