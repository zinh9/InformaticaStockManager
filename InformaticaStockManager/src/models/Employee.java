package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmployee")
	private Integer idEmployee;

	@Column(name = "firstName", nullable = false, length = 50)
	private String firstName;

	@Column(name = "lastName", nullable = false, length = 50)
	private String lastName;

	@Column(name = "dateOfBirth", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBith;

	@Column(name = "cpf", nullable = false, unique = true, length = 11)
	private String cpf;

	@Column(name = "gender", nullable = false, length = 10)
	private String gender;

	@Column(name = "registration", nullable = false, unique = true, length = 20)
	private String registration;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "salary", nullable = false)
	private Double salary;

	@Column(name = "contractType", nullable = false, length = 30)
	private String contractType;

	@Column(name = "contractStartDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date contractStartDate;

	@Column(name = "contractEndDate")
	@Temporal(TemporalType.DATE)
	private Date contractEndDate;

	@ManyToOne
	@JoinColumn(name = "idPosition")
	private Position position;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<Contact> contacts;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<Address> addresses;
	
	public Employee() {
	}

	public Employee(String firstName, String lastName, Date dateOfBith, String cpf, String gender, String registration,
			String password, Double salary, String contractType, Date contractStartDate, Date contractEndDate,
			Position position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBith = dateOfBith;
		this.cpf = cpf;
		this.gender = gender;
		this.registration = registration;
		this.password = password;
		this.salary = salary;
		this.contractType = contractType;
		this.contractStartDate = contractStartDate;
		this.contractEndDate = contractEndDate;
		this.position = position;
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBith() {
		return dateOfBith;
	}

	public void setDateOfBith(Date dateOfBith) {
		this.dateOfBith = dateOfBith;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Set<Contact> getContacts() {
	    return contacts;
	}
	
	public Set<Address> getAddresses(){
		return addresses;
	}
	
	@SuppressWarnings("deprecation")
	public int getCurrentAge() {
		int currentYear = LocalDate.now().getYear();
		int dateBirth = getDateOfBith().getYear() + 1900;
		
		int age = currentYear - dateBirth;
		
		return age;
	}
	
	public String getFormatName() {
		String nameCompleted = this.getFirstName() + " " + this.getLastName();
		return nameCompleted;
	}
}
