package db.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.dao.PaintingDao;
import domain.product.Painting;

public class PaintingDaoImpl extends AbstractProductCategoryDao<Painting> implements PaintingDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "PAINTING (PRODID, CANVASTYPE, PAINTTYPE, LENGTH, WIDTH) "
			+ "VALUES (?, ?, ?, ?, ?) ";
	
	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, i.CANVASTYPE, i.PAINTTYPE, i.LENGTH, i.WIDTH "
			+ "FROM PRODUCT p "
			+ "JOIN PAINTING i ON p.PRODID = i.PRODID "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, i.CANVASTYPE, i.PAINTTYPE, i.LENGTH, i.WIDTH "
			+ "FROM PRODUCT p "
			+ "JOIN PAINTING i ON p.PRODID = i.PRODID ";

	private static final String updateQuery = 
			"UPDATE "
			+ "PAINTING "
			+ "SET CANVASTYPE = ?, PAINTTYPE = ?, LENGTH = ?, WIDTH = ? "
			+ "WHERE PRODID = ? ";

	private static final String deleteQuery = 
			"DELETE FROM "
			+ "PAINTING "
			+ "WHERE PRODID = ? ";
		
	public PaintingDaoImpl() {
		super(createQuery, retrieveQuery, retrieveAllQuery, updateQuery, deleteQuery);
	}

	@Override
	protected Painting build(ResultSet rs) throws SQLException {
		Painting painting = new Painting();
		painting.setProdId(rs.getInt(1));
		painting.setName(rs.getString(2));
		painting.setDescription(rs.getString(3));
		painting.setPrice(rs.getDouble(4));
		painting.setSold(rs.getBoolean(5));
		painting.setCanvasType(rs.getString(6));
		painting.setPaintType(rs.getString(7));
		painting.setLength(rs.getDouble(8));
		painting.setWidth(rs.getDouble(9));
		return painting;
	}

	@Override
	protected int createStatement(int index, PreparedStatement statement, Painting product) throws SQLException {
		statement.setString(index++, product.getCanvasType());
		statement.setString(index++, product.getPaintType());
		statement.setDouble(index++, product.getLength());
		statement.setDouble(index++, product.getWidth());
		return index;
	}
	
}
