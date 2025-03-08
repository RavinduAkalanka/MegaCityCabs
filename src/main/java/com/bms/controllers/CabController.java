package com.bms.controllers;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.dao.CabDAO;
import com.bms.dao.CabDAOImpl;
import com.bms.dto.CabDTO;
import com.bms.model.Cab;


public class CabController {
	
	private CabDAO cabDAO = new CabDAOImpl();
	
	//Add Cabs
	public boolean addCab(CabDTO cabDTO) {
		
	    // Check if vehicleNo already exists
	    if (cabDAO.isVehicleNoExists(cabDTO.getVehicleNo(), -1)) {
	        return false; 
	    }

	    Date registrationDate = new Date();
	    
	    Cab cab = new Cab(
	            cabDTO.getModel(),
	            cabDTO.getVehicleNo(),
	            cabDTO.getOwner(),
	            cabDTO.getFuelType(),
	            cabDTO.getPricePerKM(),
	            cabDTO.isAvailable(), 
	            cabDTO.getCapacity(),
	            registrationDate,
	            cabDTO.getCabImgUrl(),
	            cabDTO.getDescription()
	    );
	    return cabDAO.addCab(cab);
	}
	
	
	//Get All Cabs
	public List<CabDTO> getAllCabs(int pageNumber, int pageSize){
    	List<Cab> cabList = cabDAO.getAllCabs(pageNumber, pageSize);
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
        int totalCab = cabDAO.getTotalCabCount();
        return (int) Math.ceil((double) totalCab / pageSize);
    }
    
    
    // Get Cab By ID
    public CabDTO getCabById(int cabId) {
        Cab cab = cabDAO.getCabById(cabId);

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
    
    // Update Cab
    public boolean updateCab(int cabId, CabDTO cabDTO) {

        Cab cab = new Cab();
        cab.setCabId(cabId);
        cab.setModel(cabDTO.getModel());
        cab.setVehicleNo(cabDTO.getVehicleNo());
        cab.setOwner(cabDTO.getOwner());
        cab.setFuelType(cabDTO.getFuelType());
        cab.setPricePerKM(cabDTO.getPricePerKM());
        cab.setAvailable(cabDTO.isAvailable());
        cab.setCapacity(cabDTO.getCapacity());
        cab.setCabImgUrl(cabDTO.getCabImgUrl());
        cab.setDescription(cabDTO.getDescription());

        return cabDAO.updateCab(cab);
    }
    
    // Delete Cab
    public boolean deleteCab(int cabId) {
        return cabDAO.deleteCab(cabId);
    }

}
