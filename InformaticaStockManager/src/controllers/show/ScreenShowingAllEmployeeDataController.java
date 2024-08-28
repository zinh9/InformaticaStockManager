package controllers.show;

import controllers.AuthorizationAdminController;
import controllers.update.FormToUpdateEmployeeDataController;
import dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Employee;


public class ScreenShowingAllEmployeeDataController {
	private Stage stage;
	private Employee employee;
	private EmployeeDAO employeeDAO;

	@FXML
	private Label setDateOfBirth;
	
	@FXML
	private Label setAge;
	
    @FXML
    private Label setContactType;

    @FXML
    private Label setContractType;

    @FXML
    private Label setCpf;

    @FXML
    private Label setDateEndContract;

    @FXML
    private Label setDateStartContract;

    @FXML
    private Label setEmail;

    @FXML
    private Label setFullAddress;

    @FXML
    private Label setFullName;

    @FXML
    private Label setGender;

    @FXML
    private Label setPhone;

    @FXML
    private Label setPosition;

    @FXML
    private Label setRegistration;

    @FXML
    private Label setSalary;
    
    @FXML
    private Button deleteEmployeeButton;
    
    @FXML
    private Button updateEmployeeButton;
    
	public void initialize(Employee employeeWithDetails) {
    	this.employee = employeeWithDetails;
    	
    	setFullName.setText(employee != null ? employee.getFormatName() : "");
    	setAge.setText(employee != null ? String.valueOf(employee.getCurrentAge()) : "");
    	setDateOfBirth.setText(employee != null ? employee.getDateOfBith().toString() : "");
    	setCpf.setText(employee != null ? employee.getCpf() : "");
    	setGender.setText(employee != null ? employee.getGender() : "");
    	setRegistration.setText(employee != null ? employee.getRegistration() : "");
    	setSalary.setText(employee != null ? String.valueOf(employee.getSalary()) : "");
    	setContractType.setText(employee != null ? employee.getContractType() : "");
    	setDateStartContract.setText(employee != null && employee.getContractStartDate() != null ? employee.getContractStartDate().toString() : "");
    	setDateEndContract.setText(employee != null && employee.getContractEndDate() != null ? employee.getContractEndDate().toString() : "");
    	setPosition.setText(employee != null && employee.getPosition() != null ? employee.getPosition().getPositionName() : "");

    	setContactType.setText(employee != null && !employee.getContacts().isEmpty() ? employee.getContacts().iterator().next().getType() : "");
    	setPhone.setText(employee != null && !employee.getContacts().isEmpty() ? employee.getContacts().iterator().next().getPhone() : "");
    	setEmail.setText(employee != null && !employee.getContacts().isEmpty() ? employee.getContacts().iterator().next().getEmail() : "");

    	setFullAddress.setText(employee != null && !employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getFullAddress() : "");
	}
    
    @FXML
    private void deleteEmployee() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
            VBox root = loader.load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            AuthorizationAdminController authController = loader.getController();
            
            if (authController.isAdminAuthorized()) {
                employeeDAO = new EmployeeDAO();
                employeeDAO.delete(employee);
                
                stage = (Stage) deleteEmployeeButton.getScene().getWindow();
                stage.close();
                
            } else {
                stage.close();
            }
            
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void redirectFormToUpdateEmployeeDataScreen() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/update/FormToUpdateEmployeeDataScreen.fxml"));
			VBox root = loader.load();
			
			FormToUpdateEmployeeDataController formUpdateEmployee = loader.getController();
			formUpdateEmployee.initialize(employee);
			
			stage = (Stage) updateEmployeeButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
