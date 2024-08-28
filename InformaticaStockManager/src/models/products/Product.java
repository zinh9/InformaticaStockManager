package models.products;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.Address;
import models.Supplier;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProduct")
	private Integer idProduct;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "quantityInStock", nullable = false)
	private Integer quantityInStock;

	@Column(name = "sku", nullable = false, length = 50, unique = true)
	private String sku;

	@ManyToOne
	@JoinColumn(name = "idSupplier")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "idCategory")
	private Category category;

	@Column(name = "createdAt", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updatedAt")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> addresses;

	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String name, String description, Double price, Integer quantityInStock, String sku,
			Supplier supplier, Category category, Date createdAt) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.sku = sku;
		this.supplier = supplier;
		this.category = category;
		this.createdAt = createdAt;
	}


	public Product(String name, String description, Double price, Integer quantityInStock, String sku,
			Supplier supplier, Category category, Date createdAt, Date updatedAt) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.sku = sku;
		this.supplier = supplier;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
