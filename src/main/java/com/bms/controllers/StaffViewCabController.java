package com.bms.controllers;

import java.util.ArrayList;
import java.util.List;

import com.bms.dao.StaffViewCabDAO;
import com.bms.dao.StaffViewCabDAOImpl;
import com.bms.dto.CabDTO;
import com.bms.model.Cab;

public class StaffViewCabController {
	StaffViewCabDAO staffViewCabDAO = new StaffViewCabDAOImpl();
	
	public List<CabDTO> getAllAvailableCabs(int pageNumber, int pageSize) {
        List<Cab> cabList = staffViewCabDAO.getAllAvailbeleCabs(pageNumber, pageSize);

        // Convert Cab objects to CabDTO objects
        List<CabDTO> cabDTOList = new ArrayList<>();
        for (Cab cab : cabList) {
            cabDTOList.add(new CabDTO(
                cab.getCabId(),
                cab.getModel(),
                cab.getVehicleNo(),
                cab.getOwner(),
                cab.getFuelType(),
                cab.getPricePerKM(),
                cab.isAvailable(),
                cab.getCapacity(),
                cab.getRegistrationDate(),
                cab.getCabImgUrl(),
                cab.getDescription()
            ));
        }

        return cabDTOList;
    }
	
	
	//Get Total Page
    public int getTotalPages(int pageSize) {
        int totalCab = staffViewCabDAO.getTotalCabCount();
        return (int) Math.ceil((double) totalCab / pageSize);
    }
    
    
    // Get Cab By ID
 	public CabDTO getCabById(int cabId) {
         Cab cab = staffViewCabDAO.getCabById(cabId);

         if (cab != null) {
             return new CabDTO(
                 cab.getCabId(),
                 cab.getModel(),
                 cab.getVehicleNo(),
                 cab.getOwner(),
                 cab.getFuelType(),
                 cab.getPricePerKM(),
                 cab.isAvailable(),
                 cab.getCapacity(),
                 cab.getRegistrationDate(),
                 cab.getCabImgUrl(),
                 cab.getDescription()
             );
         }
         return null; 
     }
}
