package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.BillController;
import com.bms.dto.BillDTO;


@WebServlet("/get-bill-servlet")
public class GetBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BillController billController = new BillController();
	
    public GetBillServlet() {
        super();
        
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId"));

        BillDTO billDTO = billController.getBillById(billId);
            request.setAttribute("bill", billDTO);
            request.getRequestDispatcher("/Bill/bill.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
