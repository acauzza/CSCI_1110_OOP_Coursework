import javafx.application.Application;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Exercise33_09Client extends Application {
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
  private DataOutputStream toServer = null;
  private DataInputStream fromServer = null;
  private Socket socket;
private Socket socket2;
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taClient.setWrapText(true);
    
    //taServer.setDisable(true);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
            
    taClient.setOnKeyPressed(e -> {
        if(e.getCode() == KeyCode.ENTER){
          try {
            // Get message from text area
            String msg = taClient.getText().trim();
            
            // Send message to server
            toServer.writeUTF(msg);
            toServer.flush();
            taClient.setText("");
            
            taServer.appendText("C: " + msg + '\n');
          } catch (IOException ex) {
            System.err.println(ex);
          }
        }
      });
    
    try {
        socket2 = new Socket("localhost", 8080);
        
        // Create input and output streams to server
        toServer   = new DataOutputStream(socket2.getOutputStream());
        fromServer = new DataInputStream(socket2.getInputStream());
        
        // Start a new thread for receiving messages
        new Thread(() -> {
          try {
              while (true){
                  // Get message
                  String msg = fromServer.readUTF();

                  // Display to the text area
                  taServer.appendText("S: " + msg + '\n');
              }
          } catch (IOException ex) {
              ex.printStackTrace();
          }
        }).start();
    } catch (IOException e1) {
		e1.printStackTrace();
	}

  }
      

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
