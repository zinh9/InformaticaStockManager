package controllers;

import java.io.IOException;

import util.BCryptUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
	private Stage stage;

	@FXML
	private TextField registrationFild;

	@FXML
	private PasswordField passwordFild;

	@FXML
	private Button enterButton;
	
	@FXML
	private Label errorMessage;

	@FXML
	public void eventEnterButton() {
		String registration = registrationFild.getText();
		String password = passwordFild.getText();
		
		BCryptUtil verification = new BCryptUtil(registration, password);
		
		if(verification.validateEmployee() != null) {
			redirectHomeScreen();
			return;
		} else {
			errorMessage.setText("Matrícula ou senha incorretas. Tente novamente!");
		}
	}

	public void redirectHomeScreen() {
		try {
			VBox root = FXMLLoader.load(getClass().getResource("/views/HomeScreen.fxml"));
			
			stage = (Stage) enterButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
