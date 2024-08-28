package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Position")
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPosition")
	private Integer idPosition;

	@Column(name = "positionName", nullable = false, length = 50)
	private String positionName;

	@Column(name = "description")
	private String description;

	@Column(name = "responsibilities")
	private String responsibilities;

	@Column(name = "isManagerial", nullable = false)
	private Boolean isManagerial;

	@OneToMany(mappedBy = "position")
	private List<Employee> employees;

	public Position() {
		// TODO Auto-generated constructor stub
	}

	public Position(String positionName, String description, String responsibilities, boolean isManagerial) {
		this.positionName = positionName;
		this.description = description;
		this.responsibilities = responsibilities;
		this.isManagerial = isManagerial;
	}

	public int getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(int idPosition) {
		this.idPosition = idPosition;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public boolean isManagerial() {
		return isManagerial;
	}

	public void setManagerial(boolean isManagerial) {
		this.isManagerial = isManagerial;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
