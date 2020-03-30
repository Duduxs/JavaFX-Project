package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

	@FXML
	TextField txtId;
	@FXML
	TextField txtName;

	@FXML
	Button btnSave;
	@FXML
	Button btnCancel;
	
	@FXML
	Label lblErrorName;
	
	@FXML
	public void onBtnSaveAction() {
		
	}
	@FXML
	public void onBtnCancelAction() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		
	}
}
