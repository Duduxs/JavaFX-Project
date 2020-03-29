package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn = null;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into department (Name) values (?)");
			st.setString(1, obj.getName());

			int rows = st.executeUpdate();

			if (rows > 0)
				System.out.println(rows + " rows affected!");
			else
				throw new DbException("Error! No rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update department set Name = ? where Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			int rows = st.executeUpdate();

			if (rows > 0)
				System.out.println(rows + " rows affected!");
			else
				throw new DbException("Error! No rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from department where Id = ?");
			st.setInt(1, id);
			int rows = st.executeUpdate();

			if (rows > 0)
				System.out.println(rows + " rows affected!");
			else
				throw new DbException("Error! No rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from department where Id = ? order by Id");
			st.setInt(1, id);

			rs = st.executeQuery();

			while (rs.next()) {
				Department dep = makeDepartment(rs);
				return dep;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			List<Department> list = new ArrayList<>();
			st = conn.prepareStatement("select * from department order by Id");
			rs = st.executeQuery();
			
			while(rs.next()) {
				list.add(makeDepartment(rs));
				
			}
			return list;
			
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Department makeDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;

	}

}
