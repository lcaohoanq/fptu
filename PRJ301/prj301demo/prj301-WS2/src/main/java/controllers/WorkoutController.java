/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAO.WorkoutDAO;
import java.sql.Date;
import java.util.ArrayList;
import models.WorkoutDTO;
import models.UserDTO;

/**
 *
 * @author DUNGHUYNH
 */
public class WorkoutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        String sortCol = request.getParameter("colSort");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usersession") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("WorkoutController: " + action);

        WorkoutDAO workoutDAO = new WorkoutDAO();

        try {
            switch (action != null ? action : "list") {
                case "details":
                    handleDetailsAction(request, response, workoutDAO);
                    break;
                case "edit":
                    handleEditAction(request, response, workoutDAO);
                    break;
                case "create":
                    handleCreateAction(request, response);
                    break;
                case "update":
                    handleUpdateAction(request, response, workoutDAO);
                    break;
                case "insert":
                    handleInsertAction(request, response, workoutDAO);
                    break;
                case "delete":
                    handleDeleteAction(request, response, workoutDAO, keyword, sortCol);
                    break;
                default:
                    handleListAction(request, response, workoutDAO, keyword, sortCol);
                    break;
            }
        } catch (Exception ex) {
            log("Error processing request: " + ex.getMessage(), ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void handleDetailsAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO)
            throws ServletException, IOException {
        Integer id = parseInteger(request.getParameter("id"));
        WorkoutDTO workout = id != null ? workoutDAO.load(id) : null;
        request.setAttribute("object", workout);
        request.getRequestDispatcher("./workoutdetails.jsp").forward(request, response);
    }

    private void handleEditAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO)
            throws ServletException, IOException {
        Integer id = parseInteger(request.getParameter("id"));
        WorkoutDTO workout = id != null ? workoutDAO.load(id) : null;
        request.setAttribute("object", workout);
        request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
    }

    private void handleCreateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("workout", new WorkoutDTO());
        request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
    }

    private void handleUpdateAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String nameStr = request.getParameter("name");
        String durationStr = request.getParameter("duration");
        String dateStr = request.getParameter("date");

        // Validate fields before parsing
        if (nameStr == null || nameStr.isEmpty()
                || durationStr == null || durationStr.isEmpty()
                || dateStr == null || dateStr.isEmpty()) {
            request.setAttribute("msg", "Please fill all fields!");
            request.getRequestDispatcher("./workoutdetails.jsp").forward(request, response);
            return;
        } else if (Integer.parseInt(durationStr) <= 0) {
            request.setAttribute("msg", "Duration must be greater than 0");
            request.getRequestDispatcher("./workoutdetails.jsp").forward(request, response);
            return;
        } else if (!dateStr.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            request.setAttribute("msg", "Date must be follow YYYY-MM-DD");
            request.getRequestDispatcher("./workoutdetails.jsp").forward(request, response);
            return;
        }

        Integer id = parseInteger(idStr);
        String name = nameStr;
        Integer duration = Integer.parseInt(durationStr);
        Date date = Date.valueOf(dateStr);

        System.out.println("Data update parsed: " + new WorkoutDTO(id, name, duration, date));

        WorkoutDTO workout = id != null ? workoutDAO.load(id) : new WorkoutDTO();
        workout.setWorkoutName(name);
        workout.setDurationInMinutes(duration);
        workout.setWorkoutDate(date);
        workoutDAO.update(workout);
        request.setAttribute("workout", workout);
        request.setAttribute("msg", "Update Successfully");
        request.getRequestDispatcher("./workoutdetails.jsp").forward(request, response);
    }

    private void handleInsertAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String nameStr = request.getParameter("name");
        String durationStr = request.getParameter("duration");
        String dateStr = request.getParameter("date");

        // Validate fields before parsing
        if (nameStr == null || nameStr.isEmpty()
                || durationStr == null || durationStr.isEmpty()
                || dateStr == null || dateStr.isEmpty()) {
            request.setAttribute("msg", "Please fill all fields!");
            request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
            return;
        } else if (Integer.parseInt(durationStr) <= 0) {
            request.setAttribute("msg", "Duration must be greater than 0");
            request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
            return;
        } else if (!dateStr.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            request.setAttribute("msg", "Date must be follow YYYY-MM-DD");
            request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
            return;
        }

        Integer id = parseInteger(idStr);
        String name = nameStr;
        Integer duration = Integer.parseInt(durationStr);
        Date date = Date.valueOf(dateStr);

        WorkoutDTO workout = new WorkoutDTO();
        workout.setWorkoutID(id);
        workout.setWorkoutName(name);
        workout.setDurationInMinutes(duration);
        workout.setWorkoutDate(date);

        try {
            workoutDAO.insert(workout);
        } catch (SQLException e) {
            if (e.getMessage().equals("Duplicate ID error! The WorkoutID already exists: ")) {
                request.setAttribute("msg", e.getMessage());
                request.getRequestDispatcher("./workoutedit.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("object", workout);
        response.sendRedirect("./WorkoutController");
    }

    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO,
            String keyword, String sortCol) throws ServletException, IOException {
        Long id = parseLong(request.getParameter("id"));
        if (id != null) {
            workoutDAO.delete(id);
        }
        handleListAction(request, response, workoutDAO, keyword, sortCol);
    }

    private void handleListAction(HttpServletRequest request, HttpServletResponse response, WorkoutDAO workoutDAO,
            String keyword, String sortCol) throws ServletException, IOException {
        System.out.println("keywork: " + keyword);
        System.out.println("sortcol: " + sortCol);
        List<WorkoutDTO> list = workoutDAO.list(keyword, sortCol);
        request.setAttribute("workoutlist", list);
        request.getRequestDispatcher("./workoutlist.jsp").forward(request, response);
    }
    
    private void handleAddToCartAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("AddToCartController").forward(request, response);
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            log("Invalid integer format: " + value, ex);
            return null;
        }
    }

    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            log("Invalid long format: " + value, ex);
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
