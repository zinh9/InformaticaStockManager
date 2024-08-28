package controllers;

import dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Classe para verificação de administrador para fazer operação de exclusão ou alteração de dados
public class AuthorizationAdminController {
	private Stage stage;
	private EmployeeDAO employeeDAO;
	private boolean adminAuthorized = false;
	
	@FXML
    private Button authorizationButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField registrationField;
    
    @FXML
    private Label incorrect;
    
    // Método que verifica se a matrícula e senha passadas são de um funcionário admin
    @FXML
    public void verifyAdmin() {
    	employeeDAO = new EmployeeDAO();
    	
    	String registration = registrationField.getText();
    	String password = passwordField.getText();
    	
    	adminAuthorized = employeeDAO.verifyAdmin(registration, password);
    	
    	if(!adminAuthorized) {
    		incorrect.setText("Matrícula ou senha incorretas!");
    		return;
    	} else {
    		close();	
    	}
    	
    }
    
    public boolean isAdminAuthorized() {
    	return adminAuthorized;
    }
    
    private void close() {
    	stage = (Stage) authorizationButton.getScene().getWindow();
    	stage.close();
    }
}
