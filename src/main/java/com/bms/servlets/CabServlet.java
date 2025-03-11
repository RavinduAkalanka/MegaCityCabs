package com.bms.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.CabController;
import com.bms.dto.CabDTO;


@WebServlet("/cab-servlet")
public class CabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CabController cabController = new CabController();
       
    
    public CabServlet() {
        super();
        
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageSize = 7;
        int pageNumber = 1;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        String searchModel = request.getParameter("searchModel");
        List<CabDTO> cabList;

        if (searchModel != null && !searchModel.isEmpty()) {
            cabList = cabController.searchCabByModel(searchModel);
        } else {
            cabList = cabController.getAllCabs(pageNumber, pageSize);
        }

        int totalPages = cabController.getTotalPages(pageSize);

        request.setAttribute("cabList", cabList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchModel", searchModel); 

        request.getRequestDispatcher("/Cab/cab.jsp").forward(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String model = request.getParameter("model");
        String vehicleNo = request.getParameter("vehicleNo");
        String owner = request.getParameter("owner");
        String fuelType = request.getParameter("fuelType");
        double pricePerKM = Double.parseDouble(request.getParameter("pricePerKM"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String cabImgUrl = request.getParameter("cabImgUrl");
        String description = request.getParameter("description");
        
        boolean isAvailable = true;
        
        Date registrationDate = new Date();
        
        CabDTO cabDTO = new CabDTO(model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity, registrationDate, cabImgUrl, description);
        
        boolean success = cabController.addCab(cabDTO);
        
        cabController.getAllCabs(1, 7);

        if (success) {
            request.setAttribute("alertType", "success");
            request.setAttribute("alertMessage", "Cab added successfully!");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alertMessage", "Failed to add cab or Vehicle number already exists.");
        }
        
        request.getRequestDispatcher("/Cab/addCab.jsp").forward(request, response);
	}

}
