
// Emmy Eriksson.
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Register extends Application {

	private MenuButton menuButton = new MenuButton("Välj värdesak:");
	private MenuItem jewellery, stock, device;
	private ArrayList<Valuables> all = new ArrayList<>();
	private TextArea display;
	private RadioButton nameButton;
	private RadioButton valueButton;
	private Button stockCrasch;

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		primaryStage.setTitle("Sakregister");
		display = new TextArea();
		display.setPrefSize(800, 400);
		ScrollPane scroll = new ScrollPane(display);
		display.setEditable(false);
		root.setCenter(scroll);
		root.setStyle("-fx-font-size: 18px");

		Label valueLabel = new Label("Värdesaker");
		valueLabel.setStyle("-fx-font-weight: bold");
		root.setTop(valueLabel);

		VBox right = new VBox();
		right.getChildren().add(new Label("Sortering"));
		nameButton = new RadioButton("Namn");
		nameButton.setSelected(true);
		valueButton = new RadioButton("Värde");
		right.getChildren().addAll(nameButton, valueButton);
		right.setStyle("-fx-font-weight: bold");
		right.setPadding(new Insets(5));
		right.setSpacing(15);
		root.setRight(right);

		ToggleGroup group = new ToggleGroup();
		nameButton.setToggleGroup(group);
		valueButton.setToggleGroup(group);

		FlowPane bottom = new FlowPane();
		root.setBottom(bottom);
		Label newLabel = new Label("Ny:");
		bottom.getChildren().add(newLabel);

		bottom.getChildren().add(menuButton);
		jewellery = new MenuItem("Smycke");
		stock = new MenuItem("Aktie");
		device = new MenuItem("Apparat");
		menuButton.getItems().addAll(jewellery, stock, device);
		jewellery.setOnAction(new JewelleryHandler());
		stock.setOnAction(new StockHandler());
		device.setOnAction(new DeviceHandler());

		Button showBtn = new Button("Visa");
		bottom.getChildren().add(showBtn);
		stockCrasch = new Button("Börschkrasch");
		bottom.getChildren().add(stockCrasch);
		stockCrasch.setOnAction(new StockMarketCrashHandler());
		showBtn.setOnAction(new ShowHandler());

		bottom.setAlignment(Pos.CENTER);
		bottom.setHgap(5);
		bottom.setPadding(new Insets(5));
		bottom.setStyle("-fx-font-weight: bold");

		Scene scene = new Scene(root);
		scene.setFill(Color.BLUE);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	private boolean checkIfNameIsEmpty(String name) {
		if (name.trim().isEmpty()) {
			Alert msg = new Alert(AlertType.ERROR, "ERROR - Tomt namn!");
			msg.setHeaderText("ERROR");
			msg.showAndWait();
			return true;

		}
		return false;
	}

	private boolean checkIfNegativeNumber(int number) {
		if (number <= 0) {
			Alert msg = new Alert(AlertType.ERROR, "ERROR - talet får inte var mindre eller lika med 0");
			msg.setHeaderText("ERROR");
			msg.showAndWait();
			return true;
		}
		return false;
	}

	class JewelleryHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent ave) {
			try {

				FormulaJewelery fj = new FormulaJewelery();
				fj.setTitle("Nytt smycke");
				fj.setHeaderText("Registrering av smycke");
				Optional<ButtonType> result = fj.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

					String name = fj.getName();
					int stones = fj.getNumberOfStones();
					if (checkIfNameIsEmpty(name) || checkIfNegativeNumber(stones)) {
						return;
					}
					String type = fj.getType();

					Jewellery s = new Jewellery(name, stones, type);
					all.add(s);
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR);
				msg.setContentText("Error - Fel inmatning!");
				msg.setHeaderText("ERROR!");
				msg.showAndWait();
			}
		}

	}

	class StockHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent ave) {
			try {

				FormulaStock fs = new FormulaStock();
				fs.setTitle("Ny aktie");
				fs.setHeaderText("Registrering av aktie");
				Optional<ButtonType> result = fs.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

					String name = fs.getName();
					if (checkIfNameIsEmpty(name)) {
						return;
					}
					int quantity = fs.getQuantity();
					int course = fs.getStockPrice();
					if (checkIfNegativeNumber(quantity) || checkIfNegativeNumber(course)) {
						return;
					}

					Stock a = new Stock(name, quantity, course);
					all.add(a);
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR);
				msg.setHeaderText("ERROR!");
				msg.setContentText("Error - Fel inmatning!");
				msg.showAndWait();

			}
		}
	}

	class DeviceHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent ave) {
			try {

				FormulaDevice fd = new FormulaDevice();
				fd.setTitle("Ny apparat");
				fd.setHeaderText("Registrering av apparat");
				Optional<ButtonType> result = fd.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

					String name = fd.getName();
					int price = fd.getCostPrice();
					int condition = fd.getCondition();
					if (checkIfNameIsEmpty(name) | checkIfNegativeNumber(price) || checkIfNegativeNumber(condition)) {
						return;
					}

					Device app = new Device(name, price, condition);
					all.add(app);

				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR);
				msg.setContentText("Error - Fel inmatning!");
				msg.setHeaderText("ERROR!");
				msg.showAndWait();
			}
		}
	}

	class FormulaJewelery extends Alert {

		private TextField nameField = new TextField();
		private TextField stoneField = new TextField();
		private CheckBox goldBox = new CheckBox();

		FormulaJewelery() {
			super(AlertType.CONFIRMATION);
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Namn: "), nameField);
			grid.addRow(1, new Label("Stenar: "), stoneField);
			grid.addRow(2, goldBox, new Label("Av guld"));

			getDialogPane().setContent(grid);

		}

		public String getType() {
			if (goldBox.isSelected())
				return " av guld";
			else
				return " av silver";
		}

		public String getName() {
			return nameField.getText();
		}

		public int getNumberOfStones() {
			return Integer.parseInt(stoneField.getText());
		}
	}

	class FormulaDevice extends Alert {
		private TextField nameField = new TextField();
		private TextField priceField = new TextField();
		private TextField condition = new TextField();

		public FormulaDevice() {
			super(AlertType.CONFIRMATION);
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("namn: "), nameField);
			grid.addRow(1, new Label("pris: "), priceField);
			grid.addRow(2, new Label("Skick: "), condition);

			getDialogPane().setContent(grid);
		}

		public String getName() {
			return nameField.getText();
		}

		public int getCostPrice() {
			return Integer.parseInt(priceField.getText());
		}

		public int getCondition() {
			return Integer.parseInt(condition.getText());
		}
	}

	class FormulaStock extends Alert {
		private TextField nameField = new TextField();
		private TextField numberField = new TextField();
		private TextField courseField = new TextField();

		public FormulaStock() {
			super(AlertType.CONFIRMATION);
			setHeaderText("Registrering av aktie");
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Namn:"), nameField);
			grid.addRow(1, new Label("Antal: "), numberField);
			grid.addRow(2, new Label("Kurs: "), courseField);

			getDialogPane().setContent(grid);

		}

		public String getName() {
			return nameField.getText();
		}

		public int getQuantity() {
			return Integer.parseInt(numberField.getText());
		}

		public int getStockPrice() {
			return Integer.parseInt(courseField.getText());
		}

	}

	class ShowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent ave) {
			display.setText("");

			NameComparator nameCmp = new NameComparator();
			ValueComparator valueCmp = new ValueComparator();

			if (nameButton.isSelected())
				Collections.sort(all, nameCmp);
			else if (valueButton.isSelected())
				Collections.sort(all, valueCmp);

			for (Valuables v : all)
				display.appendText(v.toString() + "\n");
		}

	}

	class NameComparator implements Comparator<Valuables> {
		public int compare(Valuables v1, Valuables v2) {
			return v1.getName().compareToIgnoreCase(v2.getName());
		}
	}

	class ValueComparator implements Comparator<Valuables> {
		public int compare(Valuables value1, Valuables value2) {
			if (value1.getValue() < value2.getValue())
				return -1;
			if (value1.getValue() > value2.getValue())
				return 1;
			return 0;

		}
	}

	class StockMarketCrashHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent ave) {

			for (Valuables x : all)
				if (x instanceof Stock)
					((Stock) x).setStockPrice();
		}
	}
}
