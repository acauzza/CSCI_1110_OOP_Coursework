import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Exercise33_09Server extends Application {

  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
  DataOutputStream toClient = null;
  DataInputStream fromClient = null;
  private ServerSocket serverSocket;
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taClient.setWrapText(true);
    //taClient.setDisable(true);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);
    
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    // To complete later
    taServer.setOnKeyPressed(e -> {
        if(e.getCode() == KeyCode.ENTER){
          try {
            // Get message from text area
            String msg = taServer.getText().trim();
            
            // Send message to client
            toClient.writeUTF(msg);
            toClient.flush();
            taServer.setText("");
            
            taClient.appendText("S: " + msg + '\n');
          } catch (IOException ex) {
            System.err.println(ex);
          }
        }
      });
    
    /** Handle button action */
    new Thread( () -> {
        try {
          serverSocket = new ServerSocket(8080);
          Platform.runLater(() ->
            ta.appendText("Server started at " + new Date() + '\n'));
    
          // Listen for a connection request
          Socket socket = serverSocket.accept();
    
          
          fromClient = new DataInputStream(
            socket.getInputStream());
          toClient = new DataOutputStream(
            socket.getOutputStream());
          
      	  while (true){
                        // Get message
                        String msg = fromClient.readUTF();

                        // Display to the text area
                        Platform.runLater(() -> 
                        taServer.appendText("C: " + msg + '\n'));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
              }).start();
    }
  

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
