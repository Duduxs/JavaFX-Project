package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.updateTableView();
		});
		
	}

	@FXML
	public void onMenuItemAboutSellerAciton() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// Load the screen, the Synchronized = the code will be not interrupted
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			// catch the screen, opening the screen in the parameter. 
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// Load the screen (Obsvisouly Vbox bcauz the FXML in the parameter is always a node vbox).
			VBox newVBox = loader.load();
			// Catch the reference of the Principal Screen
			Scene mainScene = Main.getMainScene();
			/* Catch the reference of VBox Principal Screen
			* getRoot -> Get the first element of principal FXMl (ScrollPane) and get their content.
			*/
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			// Save the reference to mainVbox Children. (MenuBar)
			Node mainMenu = mainVBox.getChildren().get(0);
			// Clear all children from mainVbox
			mainVBox.getChildren().clear();
			/* Add the mainMenu (MenuBar) to principal screen and include 
			 * all children from the screen in parameter
			 */
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			// Save for any type a controller for the window, after those things, execute an action of any type.
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
	
		}
	}
	


}
