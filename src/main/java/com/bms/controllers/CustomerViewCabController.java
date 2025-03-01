package com.bms.controllers;

import java.util.ArrayList;
import java.util.List;

import com.bms.dao.CustomerViewCabDAO;
import com.bms.dao.CustomerViewCabDAOImpl;
import com.bms.dto.CabDTO;
import com.bms.model.Cab;

public class CustomerViewCabController {
	
	private CustomerViewCabDAO customerViewCabDAO = new CustomerViewCabDAOImpl();
	
	//Get All Cabs
	
	public List<CabDTO> getAllAvailableCabs(int pageNumber, int pageSize) {
        // Fetch available cabs from the DAO with pagination
        List<Cab> cabList = customerViewCabDAO.getAllCabs(pageNumber, pageSize);

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
        int totalCab = customerViewCabDAO.getTotalCabCount();
        return (int) Math.ceil((double) totalCab / pageSize);
    }
    
    // Get Only 3 Cabs
    public List<CabDTO> getLimitedCabs(int limit) {
        List<Cab> cabList = customerViewCabDAO.getLimitedCabs(limit);
        
        
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

}
