package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.StaffController;

@WebServlet("/staff-delete-servlet")
public class StaffDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StaffController staffController = new StaffController();
    public StaffDeleteServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		boolean isDeleted = staffController.deleteStaff(userId);

        // Redirect to the staff-servlet with a success or error message
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/staff-servlet?alertType=success&alertMessage=Staff deleted successfully!");
        } else {
            response.sendRedirect(request.getContextPath() + "/staff-servlet?alertType=danger&alertMessage=Failed to delete staff.");
        }
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
