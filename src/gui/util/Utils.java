package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	//Acces the stage where my event controller are;
	public static Stage currentStage(ActionEvent event ) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
}
