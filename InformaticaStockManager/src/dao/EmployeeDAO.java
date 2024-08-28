package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Employee;
import util.BCryptUtil;
import util.ConectionDB;

public class EmployeeDAO {
	private EntityManager em;
	private GenericsDAO<Employee> genericsDAO;
	private BCryptUtil crypt;
	
	public EmployeeDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Employee.class);
	}
	
	public void create(Employee employee) {
		genericsDAO.create(employee);
	}
	
	public List<Employee> getAll(){
		return genericsDAO.getAll();
	}
	
	public Employee getEmployeeById(Integer id) {
		try {
			return genericsDAO.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Employee getEmployeeByRegistration(String registrationEmployee) {
		try {
			String jpql = "SELECT e FROM Employee e WHERE e.registration = :registrationEmployee";
			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
			query.setParameter("registrationEmployee", registrationEmployee);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o funcionário!");
			return null;
		}
	}
	
	public Employee getEmployeeWithDetails(Integer id) {
		try {
			String jpql = "SELECT e FROM Employee e "
					+ "LEFT JOIN FETCH e.position "
					+ "LEFT JOIN FETCH e.contacts "
					+ "LEFT JOIN FETCH e.addresses "
					+ "WHERE e.idEmployee = :id";
			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean verifyAdmin(String registration, String password) {
		try {
			crypt = new BCryptUtil(registration, password);
			
			Employee employee = crypt.validateEmployee();
			
			if(employee == null) {
				return false;
			}
			
			String registrationCheck = employee.getRegistration();
			
			String jpql = "SELECT e FROM Employee e "
					+ "INNER JOIN FETCH e.position "
					+ "WHERE e.registration = :registrationCheck";
			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
			query.setParameter("registrationCheck", registrationCheck);
			
			employee = query.getSingleResult();
			
			if(employee != null && employee.getPosition().isManagerial()) {
				return true;
			}
			
			return false;
			
		} catch (NoResultException e) {
			System.out.println("Não achei o funcionário!");
			return false;
		}
	}
	
	public void update(Employee employee) {
		try {
			Employee employeeCheck = getEmployeeById(employee.getIdEmployee());
			
			if(employeeCheck != null) {
				genericsDAO.update(employee);			
			}	
			
		} catch (Exception e) {
			System.out.println("Não foi possível atualizar!");
			e.printStackTrace();
		}	
	}
	
	public void delete(Employee employee) {
		genericsDAO.delete(employee);
	}
}
