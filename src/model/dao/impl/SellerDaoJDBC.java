package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn = null;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"insert into seller (Name,Email,BirthDate,BaseSalary,DepartmentId) values (?,?,?,?,?);");
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rows = st.executeUpdate();
			
			if(rows > 0)
				System.out.println(rows + " Rows Affected!");
			else
				throw new DbException("Error, no rows affected!");
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		
		try{
			
			st = conn.prepareStatement(
				"update seller set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? where Id = ? ;");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			int rows = st.executeUpdate();
			
			if(rows > 0)
				System.out.println(rows + " Rows Affected!");
			else
				throw new DbException("Error, no rows affected!");
			
			
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from seller where Id = ?;");
			st.setInt(1, id);
			
		
			int rows = st.executeUpdate();
			
			if(rows > 0)
				System.out.println(rows + " Rows Affected!");
			else
				throw new DbException("Error, no rows affected!");
			
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?;");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Department dep = makeDepartment(rs);
				Seller sell = makeSeller(rs, dep);
				return sell;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<>();
	
		try {
			
			st = conn.prepareStatement(
					"select seller.*, department.Name as DepName from seller inner join department on DepartmentId = department.id order by seller.id");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department department = makeDepartment(rs);
				Seller seller = makeSeller(rs, department);
				
				list.add(seller);
			}
			return list;
			
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sell = new ArrayList<Seller>();
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Name from seller inner join department on DepartmentId = department.id where departmentId = ? order by seller.Id;");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			while (rs.next())
				sell.add(makeSeller(rs, department));

			return sell;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	// Inicializable the department
	private Department makeDepartment(ResultSet rs) throws SQLException {

		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;

	}

	// Inicializable the seller
	private Seller makeSeller(ResultSet rs, Department dep) throws SQLException {

		Seller sell = new Seller();
		sell.setId(rs.getInt("Id"));
		sell.setName(rs.getString("Name"));
		sell.setEmail(rs.getString("Email"));
		sell.setBirthDate(rs.getDate("BirthDate"));
		sell.setBaseSalary(rs.getDouble("BaseSalary"));
		sell.setDepartment(dep);
		return sell;
	}

}
