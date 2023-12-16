package view.admin;

import controller.PCController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MainStage;
import model.Job;
import model.PC;
import model.UserSession;
import view.MenuAdmin;
import view.MenuComputerTechnician;
import view.MenuCustomer;
import view.MenuOperator;
import view.ViewPC;

public class ViewAllStaffJob {

	Stage primaryStage;
	Scene scene;
	
    public ViewAllStaffJob(Job job) {
		initialize(job);
		addEventListener(job);
    }
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	TextField pcConditionInput;
	Button deleteButton, updateButton;
	Label currentPCID, currentPCCondition;
	
	VBox container = new VBox(20);  // Set vertical spacing between elements
	private void initialize(Job jobs) {
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("About The PC");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    
	    // PC ID
	    Label pcIDLabel = new Label("PC ID: ");
	    currentPCID = new Label(pc.getPc_ID().toString());
	    currentPCID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCID = new HBox();
	    containerPCID.getChildren().addAll(pcIDLabel, currentPCID);
	    
	    //  PC condition
	    Label pcConditionLabel = new Label("PC Condition: ");
	    currentPCCondition = new Label(pc.getPc_condition());
	    currentPCCondition.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCCondition = new HBox();
	    containerPCCondition.getChildren().addAll(pcConditionLabel, currentPCCondition);

	    // Add Button
	    deleteButton = new Button("DELETE PC");
	    updateButton = new Button("UPDATE PC");
	    
	    HBox containerButton = new HBox();
	    containerButton.getChildren().addAll(deleteButton, updateButton);
	    
	    // pc condition
	    Label conditionTitle = new Label("Update Current PC Condition:");
	    pcConditionInput = new TextField();
	    pcConditionInput.setPromptText("Insert Current PC Condition");

	    // Add elements to the container
	    switch(UserSession.getCurrentUserRole()) {
		case "Customer":
			container.getChildren().add(MenuCustomer.createMenu());
			break;
		case "Computer Technician":
			container.getChildren().add(MenuComputerTechnician.createMenu());
			break;
		case "Operator":
			container.getChildren().add(MenuOperator.createMenu(UserSession.getCurrentUser()));
			break;
		case "Admin":
			container.getChildren().add(MenuAdmin.createMenu());
			break;
		default:
			Helper.showAlert(AlertType.ERROR, "Invalid role");
	}
	    
	    container.getChildren().addAll(pageTitle, containerPCID, containerPCCondition, conditionTitle, pcConditionInput);
	    
	    if(UserSession.getCurrentUser().getUserRole().equals("Admin")) {
	    	container.getChildren().add(containerButton);
	    }

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	
	private void addEventListener(Job jobs) {
		deleteButton.setOnAction(event -> {
	        String pc_id = pc.getPc_ID().toString();
	        
	        PCController.deletePC(pc_id);
	        
	    	ViewPC viewPCPage = new ViewPC();
	    	viewPCPage.show();
		});


		updateButton.setOnAction(event -> {
		    String pc_id = pc.getPc_ID().toString();

		    // Move this line inside the event handler
		    String pc_condition = pcConditionInput.getText();

		    PCController.updatePCCondition(pc_id, pc_condition);
		    
		    PC getNewUpdatePC = PCController.getPCDetail(pc_id);
		    
		    ViewPCDetail ViewPCDetail = new ViewPCDetail(getNewUpdatePC);
		    ViewPCDetail.show();
		});

	}

	
    public static ViewAllStaffJob getInstance(Job jobs) {
    	return new ViewAllStaffJob(jobs);    	
    }
}