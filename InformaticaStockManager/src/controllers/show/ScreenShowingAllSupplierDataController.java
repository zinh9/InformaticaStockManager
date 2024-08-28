package controllers.show;

import controllers.AuthorizationAdminController;
import controllers.update.FormToUpdateSupplierDataController;
import dao.SupplierDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Supplier;

public class ScreenShowingAllSupplierDataController {
	private Stage stage;
	private Supplier supplier;
	private SupplierDAO supplierDAO;

	@FXML
	private Button updateSupplierButton;
	
	@FXML
	private Button deleteSupplierButton;

	@FXML
	private Label setContactName;

	@FXML
	private Label setContactType;

	@FXML
	private Label setEmail;

	@FXML
	private Label setName;

	@FXML
	private Label setNotes;

	@FXML
	private Label setPhone;

	@FXML
	private Label setRegistration;

	@FXML
	private Label setAddress;

	public void initialize(Supplier supplier) {
		this.supplier = supplier;

		setName.setText(supplier != null ? supplier.getName() : "");
		setContactName.setText(supplier != null ? supplier.getContactName() : "");
		setNotes.setText(supplier != null ? supplier.getNotes() : "");
		setRegistration.setText(supplier != null ? supplier.getRegistrationNumber() : "");
		setContactType.setText(supplier != null && !supplier.getContacts().isEmpty()
				? supplier.getContacts().iterator().next().getType() : "");
		setPhone.setText(supplier != null && !supplier.getContacts().isEmpty()
				? supplier.getContacts().iterator().next().getPhone() : "");
		setEmail.setText(supplier != null && !supplier.getContacts().isEmpty()
				? supplier.getContacts().iterator().next().getEmail() : "");
		setAddress.setText(supplier != null && !supplier.getAddresses().isEmpty()
				? supplier.getAddresses().iterator().next().getFullAddress() : "");
	}
	
	@FXML
	private void redirectFormToUpdateSupplierDataScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/update/FormToUpdateSupplierDataScreen.fxml"));
			VBox root = loader.load();
			
			FormToUpdateSupplierDataController formUpdateSupplier = loader.getController();
			formUpdateSupplier.initialize(supplier);
			
			stage = (Stage) updateSupplierButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteSupplier() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
			VBox root = loader.load();

			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.showAndWait();

			AuthorizationAdminController authController = loader.getController();

			if (authController.isAdminAuthorized()) {
				supplierDAO = new SupplierDAO();
				supplierDAO.delete(supplier);
				
				stage = (Stage) deleteSupplierButton.getScene().getWindow();
				stage.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
