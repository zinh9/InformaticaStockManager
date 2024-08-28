package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAddress")
	private Integer idAddress;

	@Column(name = "street", nullable = false, length = 50)
	private String street;

	@Column(name = "postalCode", nullable = false, length = 9)
	private String postalCode;

	@Column(name = "number", nullable = false, length = 10)
	private String number;

	@Column(name = "complement", nullable = false, length = 20)
	private String complement;

	@Column(name = "neighborhood", nullable = false, length = 50)
	private String neighborhood;
	

	@Column(name = "city", nullable = false, length = 50)
	private String city;
	
	@Column(name = "state", nullable = false, length = 2)
	private String state;
	
	@Column(name = "country", nullable = false, length = 20)
	private String country;

	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "idSupplier")
	private Supplier supplier;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(String street, String postalCode, String number, String complement, String neighborhood, String city,
			String state, String country, Employee employee) {
		super();
		this.street = street;
		this.postalCode = postalCode;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.country = country;
		this.employee = employee;
	}

	public Address(String street, String postalCode, String number, String complement, String neighborhood, String city,
			String state, String country, Customer customer) {
		super();
		this.street = street;
		this.postalCode = postalCode;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.country = country;
		this.customer = customer;
	}
	
	public Address(String street, String postalCode, String number, String complement, String neighborhood, String city,
			String state, String country, Supplier supplier) {
		super();
		this.street = street;
		this.postalCode = postalCode;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.country = country;
		this.supplier = supplier;
	}

	public Integer getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getFullAddress() {
		return String.format("%s, %s, %s, %s, %s - %s", 
		        street, number, neighborhood, city, state, country);
	}
	
	public String getNeighborhood() {
		return neighborhood;
	}
	
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}