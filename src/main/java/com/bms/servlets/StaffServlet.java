package com.bms.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.StaffController;
import com.bms.dto.StaffDTO;

@WebServlet("/staff-servlet")
public class StaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StaffController staffController = new StaffController();

    public StaffServlet() {
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

        String searchName = request.getParameter("searchName");
        List<StaffDTO> staffList;

        if (searchName != null && !searchName.isEmpty()) {

            staffList = staffController.searchStaffByName(searchName);
        } else {
            staffList = staffController.getAllStaff(pageNumber, pageSize);
        }

        int totalPages = staffController.getTotalPages(pageSize);

        request.setAttribute("staffList", staffList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName); 

        request.getRequestDispatcher("/Staff/staff.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contactNo");
        String password = request.getParameter("password");

        Date createDate = new Date(); // Current date

        StaffDTO staffDTO = new StaffDTO(name, email, contactNo, password, createDate);

        boolean success = staffController.addStaff(staffDTO);

        List<StaffDTO> staffList = staffController.getAllStaff(1, 7);

        if (success) {
            request.setAttribute("alertType", "success");
            request.setAttribute("alertMessage", "Staff added successfully!");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alertMessage", "Failed to add staff or Email already exists.");
        }

        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("/Staff/addStaff.jsp").forward(request, response);
    }

}
