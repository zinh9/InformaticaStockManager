package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Customer;
import util.ConectionDB;

// Classe DAO de cliente, fazendo operações simples de GenericsDAO e operações específicas para o acesso aos dados
public class CustomerDAO {
	private EntityManager em;
	private GenericsDAO<Customer> genericsDAO;

	public CustomerDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Customer.class);
	}

	// Método de criar um novo cliente, passando para GenericsDAO
	public void create(Customer customer) {
		genericsDAO.create(customer);
	}
	
	// Método de recuperar todos os registros de cliente
	public List<Customer> getAll() {
		return genericsDAO.getAll();
	}

	public Customer getCustomerByCpf(String cpf) {
		try {
			String jpql = "SELECT c FROM Customer c WHERE c.cpf = :cpf";
			TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
			query.setParameter("cpf", cpf);

			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o cliente!");
			return null;
		}
	}
	
	public Customer getCustomerById(Integer id) {
		try {
			return genericsDAO.getById(id);
		} catch (NoResultException e) {
			System.out.println("Não achei o cliente!");
			return null;
		}
	}
	
	public Customer getCustomerWithDetails(Integer id) {
		try {
			String jpql = "SELECT c FROM Customer c "
					+ "LEFT JOIN FETCH c.contacts "
					+ "LEFT JOIN FETCH c.addresses "
					+ "WHERE c.idCustomer = :id";
			TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o cliente!");
			return null;
		}
	}
	
	public void update(Customer customer) {
		genericsDAO.update(customer);
	}
	
	public void delete(Customer customer) {
		genericsDAO.delete(customer);
	}
}
