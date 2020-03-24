package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction!");
	}
	
	@FXML
	public void onMenuItemDepartmentSellerAction() {
		System.out.println("onMenuDepartmentSellerAction!");
	}
	
	@FXML
	public void onMenuItemAboutSellerAciton() {
		System.out.println("onMenuAboutSellerAction!");
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}