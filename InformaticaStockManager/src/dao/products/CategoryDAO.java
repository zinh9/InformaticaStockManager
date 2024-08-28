package dao.products;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.GenericsDAO;
import models.products.Category;
import util.ConectionDB;

public class CategoryDAO {
	private EntityManager em;
	private GenericsDAO<Category> genericsDAO;
	
	public CategoryDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Category.class);
	}
	
	public void create(Category category) {
		genericsDAO.create(category);
	}
	
	public List<Category> getAll(){
		List<Category> categories = genericsDAO.getAll();
		return categories;
	}
	
	public Category getCategoryById(Integer id) {
		Category category = genericsDAO.getById(id);
		return category;
	}
	
	public Category getCategoryByName(String categoryName) {
		try {
			String jpql = "SELECT c FROM Category c WHERE c.categoryName = :categoryName";
			TypedQuery<Category> query = em.createQuery(jpql, Category.class);
			query.setParameter("categoryName", categoryName);
			
			Category category = query.getSingleResult();
			
			if(category == null) {
				create(category);
			}
			
			return category;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
}
