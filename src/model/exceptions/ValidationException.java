package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	// Save the erros in each field on departmentform.fxml, 1 - camp name, 2 - error msg
	private Map<String, String> errors = new HashMap<>();
	
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors(){
		return errors;
	}
	// Add error in my collection
	public void addError(String fieldName, String errorText) {
		errors.put(fieldName, errorText);
	}

}
