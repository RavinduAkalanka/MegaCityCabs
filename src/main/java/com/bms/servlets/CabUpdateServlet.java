package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.CabController;
import com.bms.dto.CabDTO;

@WebServlet("/cab-update-servlet")
public class CabUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CabController cabController = new CabController();

    public CabUpdateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Handle the "edit" action to display the edit form
        if (action != null && action.equals("edit")) {
            int cabId = Integer.parseInt(request.getParameter("cabId"));
            CabDTO cabDTO = cabController.getCabById(cabId);

            if (cabDTO != null) {
                request.setAttribute("cab", cabDTO);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Cab/updateCab.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("cab-servlet?error=Cab not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the cab ID from the request
        int cabId = Integer.parseInt(request.getParameter("cabId"));
        
        CabDTO existingCab = cabController.getCabById(cabId);

        // Retrieve the updated cab details from the request
        String model = request.getParameter("model");
        String vehicleNo = request.getParameter("vehicleNo");
        String owner = request.getParameter("owner");
        String fuelType = request.getParameter("fuelType");
        double pricePerKM = Double.parseDouble(request.getParameter("pricePerKM"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String cabImgUrl = request.getParameter("cabImgUrl");
        String description = request.getParameter("description");

        // Preserve the existing availability status
        boolean isAvailable = existingCab.isAvailable();

        // Create a CabDTO object with the updated details
        CabDTO cabDTO = new CabDTO(
            cabId, model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity,
            existingCab.getRegistrationDate(), cabImgUrl, description
        );


        // Update the cab in the database
        boolean success = cabController.updateCab(cabId, cabDTO);

        // Retrieve the updated list of cabs
        List<CabDTO> cabList = cabController.getAllCabs(1, 7);

        // Set alert messages based on the update result
        if (success) {
            request.setAttribute("alertType", "success");
            request.setAttribute("alertMessage", "Cab updated successfully!");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alertMessage", "Failed to update cab. Vehicle number may already exist.");
        }

        // Set the cab list and forward the request to the updateCab.jsp page
        request.setAttribute("cabList", cabList);
        request.getRequestDispatcher("/Cab/updateCab.jsp").forward(request, response);
    }
}