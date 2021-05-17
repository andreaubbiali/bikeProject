package bikeProject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller_Private_Area {

	@FXML
	private ListView<String> lstCreditCard;

	@FXML
	private Text txtListCreditCard;

	@Override
	public void init() {
		System.out.println("parte");

		ObservableList<String> seasonList = FXCollections.<String>observableArrayList("Spring", "Summer", "Fall",
				"Winter");

		lstCreditCard.getItems().addAll(seasonList);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
