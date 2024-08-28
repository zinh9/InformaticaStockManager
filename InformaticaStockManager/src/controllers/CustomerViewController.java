package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import controllers.show.ScreenShowingAllCustomerDataController;
import dao.CustomerDAO;
import models.Address;
import models.Contact;
import models.Customer;
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

/*
 *  Classe de controle de clientes para apresentar todos os clientes armazenados no banco de dados, com opções de ver
 *  detalhadamente seus dados para fazer uma exclusão ou alteração, e também podendo adicionar um novo cliente
 */
public class CustomerViewController {
	private Stage stage;
	private Customer customer;
	private CustomerDAO customerDAO;
	private ObservableList<Customer> listCustomer;
	
	@FXML
	private TableView<Customer> customerTable;
	
	@FXML
	private TableColumn<Customer, String> addressColumn;
	
	@FXML
	private TableColumn<Customer, String> cpfColumn;
	
	@FXML
	private TableColumn<Customer, Integer> idColumn;
	
	@FXML
	private TableColumn<Customer, String> nameColumn;
	
	@FXML
	private TableColumn<Customer, String> phoneColumn;
	
	@FXML
    private Button addNewCustomerButton;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField idField;

    @FXML
    private Button searchButton;


    @FXML
    private Button toGoBackButton;
	
    // Método que vai inicializar a tabela de clientes
	@FXML
	private void initialize() {
		customerDAO = new CustomerDAO();
		
		List<Customer> customers = customerDAO.getAll();
		listCustomer = FXCollections.observableArrayList(customers);
		
		customerTable.setItems(listCustomer);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        nameColumn.setCellValueFactory(cellData -> {
        	return new SimpleStringProperty(cellData.getValue().getFullName());
        });
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        addressColumn.setCellValueFactory(cellData -> {
        	Set<Address> addresses = cellData.getValue().getAddresses();
        	
        	if(!addresses.isEmpty()) {
        		String fullAddress = addresses.iterator().next().getFullAddress();
        		return new SimpleStringProperty(fullAddress);
        	} else {
        		return new SimpleStringProperty("");
        	}
        });
        phoneColumn.setCellValueFactory(cellData -> {
        	Set<Contact> contacts = cellData.getValue().getContacts();
        	
        	if(!contacts.isEmpty()) {
        		String phone = contacts.iterator().next().getPhone();
        		return new SimpleStringProperty(phone);
        	} else {
        		return new SimpleStringProperty("");
        	}
        });
	}
	
	// Método que chama a tela para poder visualizar detalhadamente os dados de um cliente, que não está na tabela
	@FXML
	private void redirectScreenShowingAllCustomerData() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/show/ScreenShowingAllCustomerData.fxml"));
			VBox root = loader.load();
			
			String idCustomer = idField.getText();
			String cpf = cpfField.getText();
			
			if(!idCustomer.isEmpty()) {
				Integer id = Integer.parseInt(idCustomer);
				customer = customerDAO.getCustomerWithDetails(id);
			} else if (!cpf.isEmpty()) {
				customer = customerDAO.getCustomerByCpf(cpf);
				customer = customerDAO.getCustomerWithDetails(customer.getIdCustomer());
			}
			
			if(customer != null) {
				ScreenShowingAllCustomerDataController ssacd = loader.getController();
				ssacd.initialize(customer);
				
				stage = new Stage();
				stage.setScene(new Scene(root));
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Método que vai redirecionar para a tela de adicionar um novo cliente no banco de dados, chamando o método genérico e passando os devidos parametros
	@FXML
	private void redirectAddNewCustomerScreen() {
		redirectToViewScreen("/views/add/AddNewCustomerScreen.fxml", addNewCustomerButton);
	}
	
	// Método que vai redirecionar para a tela principal do sistema, chamando o método genérico e passando os devidos parametros
	@FXML
	public void redirectHomeScreen() {
		redirectToViewScreen("/views/HomeScreen.fxml", toGoBackButton);
	}
	
	// Método genérico para chamar as telas que foram passadas por parametro
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
