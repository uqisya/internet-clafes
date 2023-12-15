package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PCBook;

public class PcBookController {
	public boolean DeleteBookData(int BookID) {
		try {
			PCBook.deleteBookData(BookID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Deleting record");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedData(int PcID, Date date) throws SQLException {
		return PCBook.getPCBookedData(PcID, date);
	}
	
	public boolean assignUsertoNewPC(int BookID, Integer NewPCID, Date date) throws SQLException {
		if(NewPCID == 0) {
			Helper.showAlert(AlertType.ERROR, "PC ID must be filled");
			return false;
		}
		
		if(PCBook.getPCBookedData(NewPCID, date)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		if(!PCController.getPCDetail(NewPCID.toString()).getPc_condition().equals("Usable")) {
			Helper.showAlert(AlertType.ERROR, "PC is unusable");
			return false;
		}
		
		try {
			PCBook.assignUsertoNewPC(BookID, NewPCID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error assigning user");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedDetail(int BookID) throws SQLException {
		return PCBook.getPCBookedDetail(BookID);
	}
	
	public static boolean addNewBook(String PcID, Integer UserID, Date bookedDate) throws SQLException {
		if(PcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Please choose a PC");
			return false;
		}
		
		if(bookedDate == null) {
			Helper.showAlert(AlertType.ERROR, "Please pick a date");
			return false;
		}
		if(!PCController.getPCDetail(PcID).getPc_condition().equals("Usable")) {
			Helper.showAlert(AlertType.ERROR, "PC is unusable");
			return false;
		}
		
		if(PCBook.getPCBookedData(Integer.parseInt(PcID), bookedDate)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		PCBook.addNewBook(Integer.parseInt(PcID), UserID, bookedDate);
		return true;
		
	}
	
	public boolean finishBook(ArrayList<PCBook> pcbooks, Integer StaffID, Integer transactionID) {
		try {
			PCBook.finishBook(pcbooks, StaffID, transactionID);
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error finish booking");
			return false;
		}
		return true;
	}
	
	public static ArrayList<PCBook> getAllPCBookedData(){
		try {
			return PCBook.getAllPCBookedData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public ArrayList<PCBook> getPcBookedByDate(Date date){
		try {
			return PCBook.getPCBookedByDate(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public static PCBook getPCBookedByID(int pcID) throws SQLException {
		return PCBook.getPCBookedByID(pcID);
	}
	
	
	
}
