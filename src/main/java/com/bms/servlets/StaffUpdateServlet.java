package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.StaffController;
import com.bms.dto.StaffDTO;


@WebServlet("/staff-update-servlet")
public class StaffUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StaffController staffController = new StaffController();
    public StaffUpdateServlet() {
        super();
        
    }

	
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		
		if (action != null && action.equals("edit")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			StaffDTO staffDTO = staffController.getStaffById(userId);
			
			if (staffDTO != null) {
				request.setAttribute("staff", staffDTO);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Staff/updateStaff.jsp");
                dispatcher.forward(request, response);
			} else {
				response.sendRedirect("staff-servlet?error=Staff not found");
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		// Get the rest of the staff details
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    String contactNo = request.getParameter("contactNo");
	    
	    StaffDTO staffDTO = new StaffDTO(userId,name, email, contactNo, null, null);
	    
	    boolean success = staffController.updateStaff(userId, staffDTO);
	    
	    List<StaffDTO> staffList = staffController.getAllStaff(7, 1);
	    
	    if (success) {
	        request.setAttribute("alertType", "success");
	        request.setAttribute("alertMessage", "Staff updated successfully!");
	    } else {
	        request.setAttribute("alertType", "danger");
	        request.setAttribute("alertMessage", "Failed to update staff Email already exists.");
	    }
		
	    request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("/Staff/updateStaff.jsp").forward(request, response);
	}

}
