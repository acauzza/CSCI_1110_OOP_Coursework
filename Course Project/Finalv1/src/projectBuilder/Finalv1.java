package projectBuilder;


import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Finalv1 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {

	// Table pane
    TableView<Project> tableView = new TableView<>();
    ObservableList<Project> data =
      FXCollections.observableArrayList(
        new Project("Replace Desktop", "Jon A", "10/5/21", "Amanda"));
    tableView.setItems(data);
    
    TableColumn projectColumn = new TableColumn("Project");
    projectColumn.setMinWidth(110);
    projectColumn.setCellValueFactory(
      new PropertyValueFactory<Project, String>("project"));
    
    TableColumn addedByColumn = new TableColumn("Added By");
    addedByColumn.setMinWidth(110);
    addedByColumn.setCellValueFactory(
      new PropertyValueFactory<Project, String>("addedBy"));

    TableColumn dateAddedColumn = 
      new TableColumn("Date");
    dateAddedColumn.setMinWidth(110);
    dateAddedColumn.setCellValueFactory(
      new PropertyValueFactory<Project, String>("dateAdded"));
    
    TableColumn assignedToColumn = new TableColumn("Assigned To");
    assignedToColumn.setMinWidth(110);
    assignedToColumn.setCellValueFactory(
      new PropertyValueFactory<Project, String>("assignedTo"));
    
    TableColumn completedColumn = new TableColumn("Completed");
    completedColumn.setMinWidth(100);
    completedColumn.setCellValueFactory(
      new Callback<CellDataFeatures<Project, Boolean>,ObservableValue<Boolean>>(){
          @Override public
          ObservableValue<Boolean> call( CellDataFeatures<Project, Boolean> p ){
             return p.getValue().getCompleted(); }});
    completedColumn.setCellFactory(
       new Callback<TableColumn<Project, Boolean>,TableCell<Project, Boolean>>(){
          @Override public
          TableCell<Project, Boolean> call( TableColumn<Project, Boolean> p ){
             return new CheckBoxTableCell<>(); }});

    tableView.getColumns().addAll(projectColumn, addedByColumn,
      dateAddedColumn, assignedToColumn, completedColumn);
    
    MenuBar menuBar = new MenuBar();    
    
    Menu menuFile = new Menu("File");
    Menu menuEdit = new Menu("Edit");
    menuBar.getMenus().addAll(menuFile, menuEdit);
    
    MenuItem menuItemExit = new MenuItem("Exit");
    menuFile.getItems().add(menuItemExit);
    
    MenuItem menuItemRemove = new MenuItem("Remove");
    menuEdit.getItems().add(menuItemRemove);
    
    menuItemExit.setOnAction(e -> System.exit(0));
  
    
    menuItemRemove.setOnAction(e -> {
        Project selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
    });


    FlowPane flowPane = new FlowPane(4, 4);
    TextField tfProject = new TextField();
    TextField tfAddedBy = new TextField();
    TextField tfDateAdded = new TextField();
    TextField tfAssignedTo = new TextField();
    
    Button btAddRow = new Button("Add new Project");
    tfProject.setPrefColumnCount(5);
    tfAddedBy.setPrefColumnCount(5);
    tfDateAdded.setPrefColumnCount(5);
    tfAssignedTo.setPrefColumnCount(5);
    flowPane.getChildren().addAll(new Label("Project: "),
      tfProject, new Label("Added By: "), tfAddedBy, 
      new Label("Date Added:"), tfDateAdded, new Label("Assigned To"), tfAssignedTo, btAddRow);
    
    btAddRow.setOnAction(e -> {
      data.add(new Project(tfProject.getText(), tfAddedBy.getText(), tfDateAdded.getText(), 
    		  tfAssignedTo.getText()));
      tfProject.clear();
      tfAddedBy.clear();
      tfDateAdded.clear();
    });
    
    BorderPane pane = new BorderPane();
    pane.setTop(menuBar);
    pane.setCenter(tableView);
    pane.setBottom(flowPane);
    
    
    Scene scene = new Scene(pane, 700, 500);  
    primaryStage.setTitle("Intern Tracker"); // Set the window title
    primaryStage.setScene(scene); // Place the scene in the window
    primaryStage.show(); // Display the window
  }

  public static class Project {
    private final SimpleStringProperty project;
    private final SimpleStringProperty addedBy;
    private final SimpleStringProperty dateAdded;
    private final SimpleStringProperty assignedTo;
	private final SimpleBooleanProperty completed;


    private Project(String project, String addedBy,
        String dateAdded, String assignedTo) {
      this.project = new SimpleStringProperty(project);
      this.addedBy = new SimpleStringProperty(addedBy);
      this.dateAdded = new SimpleStringProperty(dateAdded);
      this.assignedTo = new SimpleStringProperty(assignedTo);
      this.completed = new SimpleBooleanProperty();
    }

	public ObservableValue<Boolean> getCompleted() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProject() {
      return project.get();
    }

    public void setProject(String project) {
      this.project.set(project);
    }

    public String getAddedBy() {
      return addedBy.get();
    }

    public void setAddedBy(String capital) {
      this.addedBy.set(capital);
    }

    public String getDateAdded() {
      return dateAdded.get();
    }

    public void setDateAdded(String dateAdded) {
      this.dateAdded.set(dateAdded);
    }
    
    public String getAssignedTo() {
        return assignedTo.get();
    }

    public void setAssignedTo(String assignedTo) {
      this.assignedTo.set(assignedTo);
    }

  }
  

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   * line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}