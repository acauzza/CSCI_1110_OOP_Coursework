// Exercise31_01Server.java: The server can communicate with
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();
private ServerSocket serverSocket;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread(() -> connectToClient()).start();
  }
  public void connectToClient() {
        try {
          serverSocket = new ServerSocket(8000);
          Platform.runLater(() ->
            ta.appendText("Server started at " + new Date() + '\n'));
    
          // Listen for a connection request
          Socket connectToClient = serverSocket.accept();
    
          // Create data input and output streams
          DataInputStream inputFromClient = new DataInputStream(
            connectToClient.getInputStream());
          DataOutputStream outputToClient = new DataOutputStream(
            connectToClient.getOutputStream());
    
          while (true) {
            // Receive data from the client
            double annualInterestRate = inputFromClient.readDouble();
            int numOfYears = inputFromClient.readInt();
            double loanAmount = inputFromClient.readDouble();
            
            Loan calculateLoan = new Loan (annualInterestRate, numOfYears, loanAmount);
            double monthlyPayment = calculateLoan.getMonthlyPayment();
            double totalPayment = calculateLoan.getTotalPayment();
    
            // Send data back to the client
            outputToClient.writeDouble(monthlyPayment);
            outputToClient.writeDouble(totalPayment);
            
            
    
            Platform.runLater(() -> {
                ta.appendText("Annual Interest Rate: " + annualInterestRate + "\n");
                ta.appendText("Number of Years: " + numOfYears + '\n');
                ta.appendText("Loan Amount: " + loanAmount + '\n');
                ta.appendText("Monthly Payment: " + monthlyPayment + '\n');
                ta.appendText("Total Payment: " + totalPayment);
            });
          }
        }
        catch(IOException ex) {
          ex.printStackTrace();
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
