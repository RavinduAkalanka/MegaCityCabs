package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.StaffViewCabController;
import com.bms.dto.CabDTO;


@WebServlet("/staff-view-cab-servlet")
public class StaffViewCabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	StaffViewCabController staffViewCabController = new StaffViewCabController();
       
   
    public StaffViewCabServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageSize = 6; 
        int pageNumber = 1; 
        
        
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }

        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

       
        List<CabDTO> cabDTOList = staffViewCabController.getAllAvailableCabs(pageNumber, pageSize);

        int totalPages = staffViewCabController.getTotalPages(pageSize);

        request.setAttribute("cabList", cabDTOList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("/StaffViewCab/staffViewCab.jsp").forward(request, response);
	}

}
