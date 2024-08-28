package controllers.show;

import controllers.AuthorizationAdminController;
import controllers.update.FormToUpdateCustomerDataController;
import dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;

/*
 *  Classe de controle para visualizar detalhadamente os dados de um cliente, podendo também fazer 
 *  a exclusão ou alteração dos dados, com a permissão de um funcionário admin
 */
public class ScreenShowingAllCustomerDataController {
	private Stage stage;
	private Customer customer;
	private CustomerDAO customerDAO;
	
	@FXML
	private Button updateCustomerButton;
	
	@FXML
    private Button deleteCustomerButton;

    @FXML
    private Label setContactType;

    @FXML
    private Label setCpf;

    @FXML
    private Label setEmail;

    @FXML
    private Label setFullAddress;

    @FXML
    private Label setFullName;

    @FXML
    private Label setPhone;
    
    // Método que vai inicializar a tela com os atributos de cliente existentes ou não
    public void initialize(Customer customer) {
    	this.customer = customer;
    	
    	setFullName.setText(customer != null ? customer.getFullName() : "");
    	setCpf.setText(customer != null ? customer.getCpf() : "");
    	
    	setContactType.setText(customer != null && !customer.getContacts().isEmpty() ? customer.getContacts().iterator().next().getType() : "");
    	setPhone.setText(customer != null && !customer.getContacts().isEmpty() ? customer.getContacts().iterator().next().getPhone() : "");
    	setEmail.setText(customer != null && !customer.getContacts().isEmpty() ? customer.getContacts().iterator().next().getEmail() : "");
    	
    	setFullAddress.setText(customer != null && !customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getFullAddress() : "");
    }
    
    // Método que vai redirecionar para a tela de atualização de dados do cliente, passando a entidade de cliente
    @FXML
    private void redirectFormToUpdateCustomerDataScreen() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/update/FormToUpdateCustomerDataScreen.fxml"));
			VBox root = loader.load();
			
			FormToUpdateCustomerDataController formUpdateCustomer = loader.getController();
			formUpdateCustomer.initialize(customer);
			
			stage = (Stage) updateCustomerButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // Método que faz a operação de exclusão de um cliente do banco de dados, com a permissão de um funcionário admin
    @FXML
    private void deleteCustomer() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
            VBox root = loader.load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            AuthorizationAdminController authController = loader.getController();
            
            if (authController.isAdminAuthorized()) {
                customerDAO = new CustomerDAO();
                customerDAO.delete(customer);
                
                stage = (Stage) deleteCustomerButton.getScene().getWindow();
                stage.close();
            }		
            
            
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
