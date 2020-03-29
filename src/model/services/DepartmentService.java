package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll(){
		List<Department> list = new ArrayList<>();
		
		list.add(new Department(1,"Ball"));
		list.add(new Department(2,"Playstation"));
		list.add(new Department(3,"Cellphone"));
		list.add(new Department(4,"Duolingo"));
		list.add(new Department(5,"Kagami"));
		
		return list;
	}
}
