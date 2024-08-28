package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import controllers.show.ScreenShowingAllEmployeeDataController;
import dao.EmployeeDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Contact;
import models.Employee;
import models.Position;


public class EmployeeViewController {
	private Stage stage;
	private Employee employee;
	private EmployeeDAO employeeDAO;
	private ObservableList<Employee> listEmployees;
	
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> emailColumn;
    
    @FXML
    private TableColumn<Employee, Integer> idColumn;
    
    @FXML
    private TableColumn<Employee, String> nameColumn;
    
    @FXML
    private TableColumn<Employee, String> positionColumn;
    
    @FXML
    private TableColumn<Employee, String> registrationColumn;
    
    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TextField idField;
    
    @FXML
    private TextField registrationField;

    @FXML
    private Button sarchButton;

    @FXML
    private Button toGoBackButton;
    
    @FXML
    private Button addNewEmployeeButton;
    
    @FXML
    private void initialize() {
		listEmployees = FXCollections.observableArrayList();
		employeeDAO = new EmployeeDAO();
		List<Employee> employees = employeeDAO.getAll();
		
		listEmployees.addAll(employees);
		
		employeeTable.setItems(listEmployees);
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormatName()));
		registrationColumn.setCellValueFactory(new PropertyValueFactory<>("registration"));
		positionColumn.setCellValueFactory(cellData -> {
			Position position = cellData.getValue().getPosition();
			return new SimpleStringProperty(position != null ? position.getPositionName() : "");
		});
		emailColumn.setCellValueFactory(cellData -> {
		    Set<Contact> contacts = cellData.getValue().getContacts();
		    
		    if(!contacts.isEmpty()) {
		    	String email = contacts.iterator().next().getEmail();
		    	return new SimpleStringProperty(email != null ? email : "");
		    } else {
		    	return new SimpleStringProperty("");
		    }
		});
		phoneColumn.setCellValueFactory(cellData -> {
			Set<Contact> contacts = cellData.getValue().getContacts();
			
			if(!contacts.isEmpty()) {
				String phone = contacts.iterator().next().getPhone();
				return new SimpleStringProperty(phone != null ? phone : "");
			} else {
				return new SimpleStringProperty("");
			}
		});
	}
    
    @FXML
    private void redirectScreenShowingAllEmployeeData() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/show/ScreenShowingAllEmployeeData.fxml"));
    		VBox root = loader.load();
    		
    		String idEmployee = idField.getText();
    		String registration = registrationField.getText();
    		
    		if(!idEmployee.isEmpty()) {
    			Integer id = Integer.parseInt(idEmployee);
    			employee = employeeDAO.getEmployeeWithDetails(id);
    			
    		} else if(!registration.isEmpty()) {
    			employee = employeeDAO.getEmployeeByRegistration(registration);
    			employee = employeeDAO.getEmployeeWithDetails(employee.getIdEmployee());
    		}
    		
    		if(employee != null) {
    			ScreenShowingAllEmployeeDataController ssaedc = loader.getController();
    			ssaedc.initialize(employee);
    			
    			stage = new Stage();
    			stage.setScene(new Scene(root));
    			stage.show();
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void redirectAddNewEmployeeScreen() {
    	redirectToViewScreen("/views/add/AddNewEmployeeScreen.fxml", addNewEmployeeButton);
    }
    
    @FXML
    public void redirectHomeScreen() {
    	redirectToViewScreen("/views/HomeScreen.fxml", toGoBackButton);
    }
    
    private void redirectToViewScreen(String fxmlPath, Button sourceButton) {
		try {
			VBox root = FXMLLoader.load(getClass().getResource(fxmlPath));
			
	        stage = (Stage) sourceButton.getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.setMaximized(false);
	        stage.setMaximized(true);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
