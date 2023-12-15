package view;

import controller.UserController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;

public class Register {
	private static Register register;
	
	public static Register getInstance() {
		return register = register == null ? new Register() : register;
	}
	
	private Register() {
		initialize();
		addEventListener();
	}
	
	private Scene scene;
	private VBox vb;
	private Label registerTitle, usernameTitle, passwordTitle, ageTitle, confTitle;
	private TextField usernameInput, ageInput;
	private PasswordField passwordInput, confInput;
	private Button registerButton;
	private Hyperlink loginHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);
		registerTitle = new Label("Register");
		usernameTitle = new Label("Username");
		passwordTitle = new Label("Password");
		confTitle = new Label("Confirm Password");
		ageTitle = new Label("Age");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");
		confInput = new PasswordField();
		confInput.setPromptText("Confirm your password here");
		ageInput = new TextField();
		ageInput.setPromptText("Input your age here");
		registerButton = new Button("Register");
		loginHyperlink = new Hyperlink("Already have an account? Login Here!");
		
		vb.getChildren().addAll(registerTitle, usernameTitle, usernameInput, passwordTitle, 
				passwordInput, confTitle, confInput, ageTitle, ageInput, registerButton, loginHyperlink);
		registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}
	
	private void addEventListener() {
		registerButton.setOnMouseClicked(e -> {
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			int age = Integer.parseInt(ageInput.getText());
			String confpass = confInput.getText();
			String statusRegister = "";
			
			if(UserController.AddNewUser(username, password, age, confpass)) {
				statusRegister = "Registration Successful";
			}
			else {
				statusRegister= "Registration Failed";
			}
			Helper.showAlert(AlertType.INFORMATION, statusRegister);
			
			Login login = Login.getInstance();
			login.show();
		});
		loginHyperlink.setOnAction(e -> {
			Login loginPage = Login.getInstance();
			loginPage.show();
		});
	}

}
