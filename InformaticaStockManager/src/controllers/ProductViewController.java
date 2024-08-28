package controllers;

import java.io.IOException;
import java.util.List;

import controllers.show.ScreenShowingAllProductDataController;
import dao.products.ProductDAO;
import models.Supplier;
import models.products.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ProductViewController {
	private Stage stage;
	private Product product;
	private ProductDAO productDAO;
	private ObservableList<Product> listProducts;

	@FXML
	private TableView<Product> productTable;

	@FXML
	private TableColumn<Product, Integer> quantityInStockColumn;

	@FXML
	private TableColumn<Product, Integer> idColumn;

	@FXML
	private TableColumn<Product, Double> priceColumn;

	@FXML
	private TableColumn<Product, String> productColumn;

	@FXML
	private TableColumn<Product, String> skuColumn;

	@FXML
	private TableColumn<Product, String> supplierColumn;

	@FXML
	private Button addNewProductButton;

	@FXML
	private Button searchButton;

	@FXML
	private TextField idField;

	@FXML
	private TextField skuField;

	@FXML
	private Button toGoBackButton;

	@FXML
	private void initialize() {
		listProducts = FXCollections.observableArrayList();
		productDAO = new ProductDAO();
		List<Product> products = productDAO.getAll();

		listProducts.addAll(products);

		productTable.setItems(listProducts);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
		productColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setCellFactory(column -> new TableCell<Product, Double>() {
			@Override
			protected void updateItem(Double price, boolean empty) {
				super.updateItem(price, empty);
				if (empty || price == null) {
					setText(null);
				} else {
					setText(String.format("R$ %.2f", price));
				}
			}
		});
		quantityInStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
		quantityInStockColumn
				.setCellFactory(new Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>>() {

					@Override
					public TableCell<Product, Integer> call(TableColumn<Product, Integer> param) {
						return new TableCell<Product, Integer>() {
							@Override
							protected void updateItem(Integer item, boolean empty) {
								super.updateItem(item, empty);

								if (item == null || empty) {
									setText(null);
								} else {
									setText(item.toString());

									if (item <= 3 || item == 0) {
										setTextFill(Color.RED);
										setStyle("-fx-font-weight: bold;");
									} else {
										setTextFill(Color.BLACK);
										setStyle("-fx-font-weight: normal;");
									}
								}
							}
						};
					}
				});
		skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
		supplierColumn.setCellValueFactory(cellData -> {
			Supplier supplier = cellData.getValue().getSupplier();

			if (supplier != null) {
				return new SimpleStringProperty(supplier.getName());
			} else {
				return new SimpleStringProperty("");
			}
		});
	}

	@FXML
	private void redirectScreenShowingAllProductData() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/show/ScreenShowingAllProductData.fxml"));
			VBox root = loader.load();

			String idProduct = idField.getText();
			String sku = skuField.getText();

			if (!idProduct.isEmpty()) {
				Integer id = Integer.parseInt(idProduct);
				product = productDAO.getProductWithDetails(id);

			} else if (!sku.isEmpty()) {
				product = productDAO.getProductBySku(sku);
				product = productDAO.getProductWithDetails(product.getIdProduct());
			}

			ScreenShowingAllProductDataController ssapdc = loader.getController();
			ssapdc.initialize(product);

			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void redirectAddNewProductScreen() {
		redirectToViewScreen("/views/add/AddNewProductScreen.fxml", addNewProductButton);
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
