package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;

	private DepartmentService service;

	//List that save a objects for event atualization
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;

	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;

	@FXML
	private Label lblErrorName;

	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (entity == null)
			throw new IllegalStateException("Entity was null");

		if (service == null)
			throw new IllegalStateException("Service was null");

		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}
	//Get text of the txtField and put in Department
	private Department getFormData() {
		Department obj = new Department();

		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			
			exception.addError("name", "Field cannot be empty");
		}
		obj.setName(txtName.getText());
		
		//if exists an error, them throw a exception error for i can see.
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);

	}
// Update the text field
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());

	}
	
	private void setErrorMessages(Map<String,String> errors) {
		//Camp name
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			lblErrorName.setText(errors.get("name"));
		}
	}

}
