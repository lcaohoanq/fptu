/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301demo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prj301demo.utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class StudentDAO{
    
    public List<StudentDTO> list(String keyword, String sortCol){
                
        List<StudentDTO> list = new ArrayList<StudentDTO>();
        
        try {

                Connection con = DBUtils.getConnection();            
                String sql = " SELECT id, firstname, lastname, age FROM student ";

                if (keyword != null && !keyword.isEmpty()){
                    sql += " WHERE firstname like ? OR lastname like ? ";
                }

                if (sortCol != null && !sortCol.isEmpty()){
                    sql += " ORDER BY " + sortCol + " ASC ";
                }

                
                PreparedStatement stmt = con.prepareStatement(sql);
                
                if (keyword != null && !keyword.isEmpty()){
                    stmt.setString(1, "%" + keyword + "%");
                    stmt.setString(2, "%" + keyword + "%");
                }
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs != null){
                    while (rs.next()){
                        
                        int id = rs.getInt("id");
                        String firstname = rs.getString("firstname");
                        String lastname = rs.getString("lastname");
                        int age = rs.getInt("age");
                        
                        StudentDTO student = new StudentDTO();
                        student.setId(id);
                        student.setFirstname(firstname);
                        student.setLastname(lastname);
                        student.setAge(age);
                        
                        list.add(student);
                    }
                }
                con.close();
            } catch (SQLException ex) {                
                System.out.println("Error in servlet. Details:" + ex.getMessage());
                ex.printStackTrace();
                
            }
        return list;
    }
    /*
    Load student with id
    */
    public StudentDTO load(int id){

        String sql = "select id, firstname, lastname, age from student where id = ?";
    
        try {
            
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);                      
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                                    
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    int age = rs.getInt("age");

                    StudentDTO student = new StudentDTO();
                    student.setId(id);
                    student.setFirstname(firstname);
                    student.setLastname(lastname);
                    student.setAge(age);
                    return student;
            }
        }
        catch (SQLException ex) {
            System.out.println("Query Student error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    /*
    Insert student and return Id
    */    
    public Long insert(StudentDTO student){
        String sql = "INSERT INTO student( id, firstname, lastname, age) "               
                + " VALUES (?, ?, ?, ?)";    
        try {
            
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);                      
            
            ps.setInt(1, student.getId());
            ps.setString(2, student.getFirstname());
            ps.setString(3, student.getLastname());
            ps.setInt(4, student.getAge());

            ps.executeUpdate();
           
	}
        catch (SQLException ex) {
            System.out.println("Insert Student error!" + ex.getMessage());
        }
        return null;
    }
    
    /*
 * Update student and return boolean indicating success
     */
    public boolean update(StudentDTO student) {
        String sql = "UPDATE student SET firstname = ?, lastname = ?, age = ? WHERE id = ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getLastname());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getId());

            int rowsUpdated = ps.executeUpdate();
            conn.close();

            return rowsUpdated > 0;
        } catch (SQLException ex) {
            System.out.println("Update Student error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    /*
 * Delete student by id and return boolean indicating success
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            int rowsDeleted = ps.executeUpdate();
            conn.close();

            return rowsDeleted > 0;
        } catch (SQLException ex) {
            System.out.println("Delete Student error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }
    
}
