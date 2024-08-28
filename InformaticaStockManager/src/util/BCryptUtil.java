package util;

import org.mindrot.jbcrypt.BCrypt;

import dao.EmployeeDAO;
import models.Employee;

public class BCryptUtil {
	private String registration;
	private String password;
	private EmployeeDAO employeeDAO;
	
	public BCryptUtil() {
		employeeDAO = new EmployeeDAO();
	}
	
	public BCryptUtil(String registration, String password) {
		this.registration = registration;
		this.password = password;
        employeeDAO = new EmployeeDAO();
	}
	
	public Employee validateEmployee() {
		Employee employee = employeeDAO.getEmployeeByRegistration(registration);
		
		if(employee != null && BCrypt.checkpw(password, employee.getPassword())) {
			return employee;
		}
		
		return null;
	}
		
	public String createPasswordHash(String password) {
		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		return passwordHash;
	}
}
