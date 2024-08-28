package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Position;
import util.ConectionDB;

public class PositionDAO {
	private EntityManager em;
	private GenericsDAO<Position> genericsDAO;
	
	public PositionDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Position.class);
	}
	
	public void create(Position position) {
		genericsDAO.create(position);
	}
	
	public List<Position> getAll(){
		List<Position> positions = genericsDAO.getAll();
		return positions;
	}
	
	public Position getPositionName(String position) {
		try {
			String jpql = "SELECT p FROM Position p WHERE p.positionName = :position";
			TypedQuery<Position> query = em.createQuery(jpql, Position.class);
			query.setParameter("position", position);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
}
