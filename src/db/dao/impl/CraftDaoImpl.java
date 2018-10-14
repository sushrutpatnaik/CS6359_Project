package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dao.DaoException;
import db.dao.CraftDao;
import domain.product.Craft;

public class CraftDaoImpl implements CraftDao {
	
	private static final String createQuery = 
			"INSERT INTO "
			+ "CRAFT (PRODID, USAGE, LENGTH, WIDTH, HEIGHT) "
			+ "VALUES (?, ?, ?, ?, ?) ";

	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, i.USAGE, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN CRAFT i ON p.PRODID = i.PRODID "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, i.USAGE, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN CRAFT i ON p.PRODID = i.PRODID ";

	private static final String updateQuery = 
			"UPDATE "
			+ "CRAFT "
			+ "SET USAGE = ?, LENGTH = ?, WIDTH = ?, HEIGHT = ? "
			+ "WHERE PRODID = ? ";

	private static final String deleteQuery = 
			"DELETE FROM "
			+ "CRAFT "
			+ "WHERE PRODID = ? ";
	
	@Override
	public void create(Connection connection, Craft product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(createQuery);
			statement.setInt(1, product.getProdId());
			statement.setString(2, product.getUsage());
			statement.setDouble(3, product.getLength());
			statement.setDouble(4, product.getWidth());
			statement.setDouble(5, product.getHeight());
			statement.executeUpdate();
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public Craft retrieve(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Craft painting = buildCraft(rs);
			return painting;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Craft> retrieveAll(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveAllQuery);
			rs = statement.executeQuery();
			ArrayList<Craft> paintings = new ArrayList<Craft>();

			while (rs.next()) {
				Craft painting = buildCraft(rs);
				paintings.add(painting);
			}
			return paintings;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public int update(Connection connection, Craft product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateQuery);
			statement.setString(1, product.getUsage());
			statement.setDouble(2, product.getLength());
			statement.setDouble(3, product.getWidth());
			statement.setDouble(4, product.getHeight());
			statement.setInt(5, product.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to update product!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public int delete(Connection connection, Craft product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteQuery);
			statement.setInt(1, product.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to delete product!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	private static Craft buildCraft(ResultSet rs) throws SQLException {		
		Craft craft = new Craft();
		craft.setProdId(rs.getInt(1));
		craft.setName(rs.getString(2));
		craft.setDescription(rs.getString(3));
		craft.setPrice(rs.getDouble(4));
		craft.setSold(rs.getBoolean(5));
		craft.setUsage(rs.getString(6));
		craft.setLength(rs.getDouble(7));
		craft.setWidth(rs.getDouble(8));
		craft.setHeight(rs.getDouble(9));
		return craft;
	}
	
}
