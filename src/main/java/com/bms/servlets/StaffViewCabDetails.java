package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.StaffViewCabController;
import com.bms.dto.CabDTO;


@WebServlet("/staff-view-cab-details")
public class StaffViewCabDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	StaffViewCabController staffViewCabController = new StaffViewCabController();
    
    public StaffViewCabDetails() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cabId = Integer.parseInt(request.getParameter("cabId"));

        CabDTO cabDTO = staffViewCabController.getCabById(cabId);

        request.setAttribute("cab", cabDTO);

        request.getRequestDispatcher("/StaffViewCab/viewCabDetails.jsp").forward(request, response);
	}


}
