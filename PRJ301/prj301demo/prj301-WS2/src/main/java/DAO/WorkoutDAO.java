/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import models.WorkoutDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class WorkoutDAO {

    public List<WorkoutDTO> list(String keyword, String sortCol) {

        List<WorkoutDTO> list = new ArrayList<WorkoutDTO>();

        try {

            Connection con = DBUtils.getConnection();
            String sql = " SELECT WorkoutID, WorkoutName, DurationInMinutes, WorkoutDate FROM Workouts ";

            if (keyword != null && !keyword.isEmpty()) {
                sql += " WHERE WorkoutID like ? OR WorkoutName like ? ";
            }

            if (sortCol != null && !sortCol.isEmpty()) {
                sql += " ORDER BY " + sortCol + " ASC ";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
            }

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {

                    int workoutID = rs.getInt("WorkoutID");
                    int durationInMinutes = rs.getInt("DurationInMinutes");
                    String workoutName = rs.getString("WorkoutName");
                    Date workoutDate = rs.getDate("WorkoutDate");

                    WorkoutDTO workout = new WorkoutDTO();
                    workout.setWorkoutID(workoutID);
                    workout.setWorkoutName(workoutName);
                    workout.setDurationInMinutes(durationInMinutes);
                    workout.setWorkoutDate(workoutDate);

                    list.add(workout);
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
    Load workout with id
     */
    public WorkoutDTO load(int id) {

        String sql = "select WorkoutID, WorkoutName, DurationInMinutes, WorkoutDate from Workouts where WorkoutID = ?";

        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                String workoutName = rs.getString("WorkoutName");
                int durationInMinutes = rs.getInt("DurationInMinutes");
                Date workoutDate = rs.getDate("WorkoutDate");

                WorkoutDTO workout = new WorkoutDTO();
                workout.setWorkoutID(id);
                workout.setWorkoutName(workoutName);
                workout.setDurationInMinutes(durationInMinutes);
                workout.setWorkoutDate(workoutDate);
                return workout;
            }
        } catch (SQLException ex) {
            System.out.println("Query Workout error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /*
    Insert workout and return Id
     */
    public Integer insert(WorkoutDTO workout) throws SQLException {
        String sql = "INSERT INTO Workouts(WorkoutID, WorkoutName, DurationInMinutes, WorkoutDate) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, workout.getWorkoutID());
            ps.setString(2, workout.getWorkoutName());
            ps.setInt(3, workout.getDurationInMinutes());
            ps.setDate(4, workout.getWorkoutDate());

            ps.executeUpdate();
            return workout.getWorkoutID();

        } catch (SQLException ex) {
            // SQL Server specific handling for duplicate key violation
            if (ex.getSQLState().equals("23000") && ex.getErrorCode() == 2627) {
                // Duplicate entry error
                throw new SQLException("Duplicate ID error! The WorkoutID already exists: ");
            } else {
                System.out.println("Insert Workout error: " + ex.getMessage());
            }
        }
        return null;
    }


    /*
 * Update workout and return boolean indicating success
     */
    public boolean update(WorkoutDTO workout) {
        String sql = "UPDATE Workouts SET WorkoutName = ?, DurationInMinutes = ?, WorkoutDate = ? WHERE WorkoutID = ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, workout.getWorkoutName());
            ps.setInt(2, workout.getDurationInMinutes());
            ps.setDate(3, workout.getWorkoutDate());
            ps.setInt(4, workout.getWorkoutID());

            int rowsUpdated = ps.executeUpdate();
            conn.close();

            return rowsUpdated > 0;
        } catch (SQLException ex) {
            System.out.println("Update Workout error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    /*
 * Delete workout by id and return boolean indicating success
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM Workouts WHERE WorkoutID = ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            int rowsDeleted = ps.executeUpdate();
            conn.close();

            return rowsDeleted > 0;
        } catch (SQLException ex) {
            System.out.println("Delete Workout error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        try {

            new WorkoutDAO().list("", "").stream().forEach(System.out::println);

            WorkoutDTO workoutDTO = new WorkoutDTO(1, "Test", 45, Date.valueOf("2022-06-05"));

            System.out.println(new WorkoutDAO().insert(workoutDTO));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
