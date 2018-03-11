/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Asus
 */
public class GamePlay extends Application {
    private static int initialCredit = 10; // game starts with 10 coins per players initially.
    private static int totalBetAmount; // this will save total betamount till the end.
    private static  int totalLossAmount; // thiswill save the toatl amount of bet when match loss.
    private static int betAmount; // all the bet amount will be stored.
    static boolean isSpining = true; // cheak the reels are spinning or not , to stop the threads.

    private  static  int noOfTry = 0; // stores the number of rounds played by the perticular player.
    private static int noOfWin = 0; // stores the number of rounds won by the perticular player.
    private static int onOfLoss = 0; // stores the number of rounds won by the perticular player.
    
    private int reelOneVal = 0; // stores the values of sybmols when the reel one stops rolling.
    private int reelTwoVal = 0; // stores the values of sybmols when the reel two stops rolling.
    private int reelThreeVal = 0; // stores the values of sybmols when the reel three stops rolling.
    
    @Override
    public void start(Stage primaryStage) {

        Reel reel1 = new Reel(); // creating Reel objects
        Reel reel2 = new Reel();
        Reel reel3 = new Reel();
        
        Label lblTitle = new Label("WELCOME TO SLOT CASINO"); // to set the the title label.
        lblTitle.setStyle("-fx-border-color: white;");
        lblTitle.setStyle("-fx-font-size: 12pt;");
        lblTitle.setStyle("-fx-text-fill: white;");
        BorderPane border = new BorderPane(); // creating a border pane to place all the objects.

        HBox addTitle = new HBox(); // creating a HBox to place the title label and set it to top of the border pane.
        addTitle.setPadding(new Insets(40, 40, 40, 650));
        addTitle.setSpacing(0);
        addTitle.setStyle("-fx-background-color: #336699;");
        border.setTop(addTitle);
        addTitle.getChildren().add(lblTitle);

//        Image image1 = new Image("Images/bell.png");
//        Image image2 = new Image("Images/redseven.png");
//        Image image3 = new Image("Images/cherry.png");
        
        ImageView iView1 = new ImageView(reel1.images.get(0).getImage()); // creating three image views to hold the Symbols in the three wheels.
        ImageView iView2 = new ImageView(reel2.images.get(0).getImage());
        ImageView iView3 = new ImageView(reel3.images.get(0).getImage());
        
        iView1.setFitHeight(410);
        iView1.setFitWidth(410);
        
        iView2.setFitHeight(410);
        iView2.setFitWidth(410);
        
        iView3.setFitHeight(410);
        iView3.setFitWidth(410);
        
        Label lblReel1 = new Label(); // setting the image views to the labels
        lblReel1.setGraphic(iView1);
        lblReel1.setStyle("-fx-border-color: white;");
        
        
        Label lblReel2 = new Label();
        lblReel2.setGraphic(iView2);
        lblReel2.setStyle("-fx-border-color: white;");
        
        Label lblReel3 = new Label();
        lblReel3.setGraphic(iView3);
        lblReel3.setStyle("-fx-border-color: white;");
        
        HBox reelBox = new HBox(); // creating a HBox to place all three label which hold the symbols andit set to the center of border pane.
        reelBox.setPadding(new Insets(150, 40, 10, 265));
        reelBox.setSpacing(10);
        border.setCenter(reelBox);
        reelBox.getChildren().addAll(lblReel1,lblReel2,lblReel3);
        reelBox.setMinHeight(700);
        reelBox.setMinWidth(700);
        
        Button addCoin = new Button("Add Coin"); // creating buttons
        Button betOnce = new Button("Bet Once");
        Button betMax = new Button("Bet Max");
        Button btnReset = new Button("Bet Reset");
        Button btnStatics = new Button("Satistics");
        Button btnSpin = new Button("Spin");
        Button btnPayout = new Button("Payout");
        
        Label lblCredit = new Label("The Credit Amount : " + initialCredit + "$"); // creating a labels to hold the credit area.
        lblCredit.setStyle("-fx-text-fill: white;");
        Label lblBet = new Label("Current Bet amount is : " + betAmount + "$"); // creating a label to hold the bet amount.
        lblBet.setStyle("-fx-text-fill: white;");
        Label lblResult = new Label("Status"); // creating a label to hold the status of the player (win or loss).
        lblResult.setStyle("-fx-text-fill: white;");
        
        
        HBox addLbl = new HBox(); // creating a HBox to place all three labels which hold the cradit , bet amount and game status.
        addLbl.getChildren().addAll(lblCredit,lblResult,lblBet);
        addLbl.setPadding(new Insets(0,20,50,275));
        addLbl.setSpacing(200);

        HBox addButton = new HBox(); // creating a HBox to place all the sevan buttons.
        addButton.getChildren().addAll(addCoin,betOnce,betMax,btnSpin,btnReset,btnStatics,btnPayout);
        addButton.setPadding(new Insets(0,40,40,300));
        addButton.setSpacing(5);
        
        VBox dislpayObject = new VBox(); // creating a VBox to hold both addlbl and addButton HBox.
        dislpayObject.getChildren().addAll(addLbl,addButton);
        dislpayObject.setPadding(new Insets(0,10,10,10));
        border.setBottom(dislpayObject);
        border.setMinHeight(500);
        border.setMinWidth(500);
        //------------------------------------------------------------------------------------------------------------------------------------
        addCoin.setOnAction(new EventHandler<ActionEvent>() { // button to add the coins if the credit are low.
            @Override
            public void handle(ActionEvent event) {
                if( initialCredit > 3 || betAmount != 0) { // playe can add coin if the credit level is lower than 3 and if the bet amount should be 0.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("YOU MADE A MISTAKE!!!");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("You can add coins if credit level is low than 3 or reset the bet amount and try again.");
                    alert.showAndWait();
                } else {
                    initialCredit += 1;
                    lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                }
            }
        });
        
        betOnce.setOnAction(new EventHandler<ActionEvent>() { // button to add a credit bet amount and less it from player's credit.
            @Override
            public void handle(ActionEvent event) {
               if(initialCredit ==0 || betAmount != 0){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("INSUFFICIENT CREDIT");
                   alert.setHeaderText("Flow our commands to get better experence:)");
                   alert.setContentText("Your credit level is insufficient to play, please add coin to countinue OR Reset your bet amount.");
                   alert.showAndWait();


                      lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                      lblBet.setText("Current Bet amount is : " + betAmount + "$");
                } else {

                lblReel1.setStyle("-fx-border-color: orange;");
                lblReel2.setStyle("-fx-border-color: orange;");
                lblReel3.setStyle("-fx-border-color: orange;");
                initialCredit -=1;
                betAmount += 1;
                lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                lblBet.setText("Current Bet amount is : " + betAmount + "$");
                   totalBetAmount += betAmount;
               }
            }
        });
        
        betMax.setOnAction(new EventHandler<ActionEvent>() { // button to add three credit bet amount and less it from player's credit.
            @Override
            public void handle(ActionEvent event) {
                if(initialCredit < 3 || betAmount != 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("INSUFFICIENT CREDIT");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("Your credit level is insufficient to play, please add coin to countinue OR reset you bet amount.");
                    alert.showAndWait();

                      lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                      lblBet.setText("Current Bet amount is : " + betAmount + "$");
                } else {

                lblReel1.setStyle("-fx-border-color: orange;");
                lblReel2.setStyle("-fx-border-color: orange;");
                lblReel3.setStyle("-fx-border-color: orange;");
                initialCredit -=3;
                betAmount += 3;
                lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                lblBet.setText("Current Bet amount is : " + betAmount + "$");
                    totalBetAmount =totalLossAmount + betAmount;
                }
            }
        });
        
        btnSpin.setOnAction(new EventHandler<ActionEvent>() { // button to roll the reels which conatains the symbols.
            
            @Override
            public void handle(ActionEvent event) {
                isSpining = true;
                if (betAmount == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("YOU MADE A MISTAKE");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("You need to bet the credit first then you can play.");
                    alert.showAndWait();
                } else {
                Thread thread3 = new Thread(new Runnable() { // creating the thread
                @Override
                public void run() {
                    while (isSpining) { // while it is true reels will roll.
                        int randomNumber = (int) Math.floor(Math.random() * reel3.images.size()); // generates the random number according to the imahes arraylist.
                        ImageView iv3 = new ImageView(reel3.images.get(randomNumber).getImage());
                        reelThreeVal = reel3.images.get(randomNumber).getValue();
                        Platform.runLater(new Runnable() { // this method will help to replace the symbols during the thread running.
                            @Override
                            public void run() {
                                lblReel3.setGraphic(iv3);
                            }

                        });
                        iv3.setFitHeight(410);
                        iv3.setFitWidth(410);
                        try {
                            Thread.currentThread().sleep(30);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            });
                    
                Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isSpining){
                    int randomNumber = (int) Math.floor(Math.random() * reel2.images.size());
                            ImageView iv2 = new ImageView(reel2.images.get(randomNumber).getImage());
                            reelTwoVal = reel2.images.get(randomNumber).getValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lblReel2.setGraphic(iv2);
                                }
                                
                            });
                            iv2.setFitHeight(410);
                            iv2.setFitWidth(410);
                            try {
                                Thread.currentThread().sleep(30);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                }
            });
                
                Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isSpining){
                    int randomNumber = (int) Math.floor(Math.random() * reel1.images.size());
                            ImageView iv1 = new ImageView(reel1.images.get(randomNumber).getImage());
                            reelOneVal = reel1.images.get(randomNumber).getValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lblReel1.setGraphic(iv1);
                                }
                                
                            });
                            iv1.setFitHeight(410);
                            iv1.setFitWidth(410);
                            try {
                                Thread.currentThread().sleep(30);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                    }
            });

                    thread1.start(); // starting threads
                    thread2.start();
                    thread3.start();

                    noOfTry = noOfTry + 1; // keep counting of the rounds that a perticular player plays.

                    lblReel1.setStyle("-fx-border-color: white;");
                    lblReel2.setStyle("-fx-border-color: white;");
                    lblReel3.setStyle("-fx-border-color: white;");
                    betOnce.setVisible(true);
                }


            }

        });
        
        btnReset.setOnAction(new EventHandler<ActionEvent>() { // button is use to cancel the bet amount before playing.
            @Override
            public void handle(ActionEvent event) {
                if (betAmount == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("YOU MADE A MISTAKE!!!");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("First you need to bet before reset the credit.");
                    alert.showAndWait();
                } else {

                    if (betAmount > 0) {
                        int amount = betAmount;
                        initialCredit += amount;
                        betAmount = 0;
                    } else {
                        betAmount = 0;
                    }
                    lblReel1.setStyle("-fx-border-color: red;");
                    lblReel2.setStyle("-fx-border-color: red;");
                    lblReel3.setStyle("-fx-border-color: red;");
                    lblCredit.setText("The Credit Amount: " + initialCredit + "$");
                    lblBet.setText("Current Bet amount is : " + betAmount + "$");
                }
            }
        });
        
      lblReel1.setOnMouseClicked(new EventHandler<MouseEvent>() { // to stop the rolling by click the reels
      @Override
      public void handle(MouseEvent e) {
        isSpining = false;
          if(betAmount==0){
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("YOU MADE A MISTAKE!!!");
              alert.setHeaderText("Flow our commands to get better experence:)");
              alert.setContentText("Please play and click the reels to stop them.");
              alert.showAndWait();
          } else {
              matchSymbol(reelOneVal, reelTwoVal, reelThreeVal, lblResult, lblCredit, lblBet, lblReel1, lblReel2, lblReel3);
          }
      }
    });
      
      
      lblReel2.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        isSpining = false;
        if(betAmount==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("YOU MADE A MISTAKE!!!");
            alert.setHeaderText("Flow our commands to get better experence:)");
            alert.setContentText("Please play and click the reels to stop them.");
            alert.showAndWait();
        } else {
            matchSymbol(reelOneVal, reelTwoVal, reelThreeVal, lblResult, lblCredit, lblBet, lblReel1, lblReel2, lblReel3);
        }
      }
     });
      
      lblReel3.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        isSpining = false;
          if(betAmount==0){
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("YOU MADE A MISTAKE!!!");
              alert.setHeaderText("Flow our commands to get better experence:)");
              alert.setContentText("Please play and click the reels to stop them.");
              alert.showAndWait();
          } else {
              matchSymbol(reelOneVal, reelTwoVal, reelThreeVal, lblResult, lblCredit, lblBet, lblReel1, lblReel2, lblReel3);
          }
      }
    });
      
      btnStatics.setOnAction(new EventHandler<ActionEvent>() { // button is to view the player statistics in a pie chart view.
            @Override
            public void handle(ActionEvent event) {
                if(noOfTry == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("YOU MADE A MISTAKE!!!");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("First you need to play atlest a round to cheak your statistic.");
                    alert.showAndWait();
                } else {
                    createChart();
                }
            }
      });

      btnPayout.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              PrintWriter payoutWriter = null;
              try {
                  payoutWriter = new PrintWriter("PAYOUT.txt");
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              }
              int profit = ((initialCredit)-totalBetAmount-totalLossAmount);
              payoutWriter.println("                            The payout Ratio calculation");
              payoutWriter.println("--------------------------------------------------------------------------------------");
              payoutWriter.println("No of Try\tBet Amount\tWin Amount\tLoss Amount\tProfit");
              payoutWriter.println("--------------------------------------------------------------------------------------");
              payoutWriter.println( noOfTry+"           \t"+totalBetAmount+"         \t"+(initialCredit)+"        \t"+totalLossAmount+"     \t"+profit);
              payoutWriter.println("The Final payout ratio is : " + profit/noOfTry*100);
              payoutWriter.close();
          }
      });

        //-------------------------------------------------------------------------------------------------------------------------------------
        
        Scene scene = new Scene(border, 1800, 1100); // creating the scene and adding the borderpane which hold all the layout.
        primaryStage.setTitle("MY CASINO SLOT MACHINE - 2016323");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        
        scene.getStylesheets().add(GamePlay.class.getResource("Machine.css").toExternalForm()); // adding the external css styling sheet to the game.
        primaryStage.show();
    }
    public  void createChart(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("PLAYER STATISTICS");
        alert.setHeaderText("Flow our commands to get better experence:)");
        alert.setContentText("No of round played : " + noOfTry + "\nNo of rounds won : " + noOfWin + "\nNo of rounds lost : " + onOfLoss);
        alert.showAndWait();
        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Winning", noOfWin);
        PieChart.Data slice2 = new PieChart.Data("Lossing", onOfLoss);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        Button btnPrintD = new Button("print");
        btnPrintD.setMinWidth(80);
        btnPrintD.setMinHeight(50);
        VBox chatStatistic = new VBox(pieChart,btnPrintD);
       // chatStatistic.getStylesheets().add(GamePlay.class.getResource("Machine.css").toExternalForm());
        chatStatistic.setPadding(new Insets(20,20,20,20));

        btnPrintD.setOnAction(new EventHandler<ActionEvent>() { // button to print the player's statistics in a text file named "player_statistics".
            @Override
            public void handle(ActionEvent event) {

                if(noOfTry == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("YOU MADE A MISTAKE!!!");
                    alert.setHeaderText("Flow our commands to get better experence:)");
                    alert.setContentText("First you need to play atlest a round to get print your statistic printed.");
                    alert.showAndWait();
                } else {
                    PrintWriter writer = null;
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println(dtf.format(now)); //2016/11/16 12:08:43
                        writer = new PrintWriter(dtf.format(now)+".txt", "UTF-8");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }



                    writer.println("--------------------PLAYER DETAILS----------------------");
                    writer.println("Number of time played : " + noOfTry);
                    writer.println("Number of time Won : " + noOfWin);
                    writer.println("Number of time lost : " + onOfLoss);
                    writer.println("Number of crdits Won : " + (initialCredit - 10));
                    writer.println("Average of crdits Won : " + ((initialCredit - 10)/noOfTry));

                    writer.close();
                }
            }
        });

        Scene scene2 = new Scene(chatStatistic, 500, 500);
        Stage staticsStage = new Stage();
        staticsStage.setScene(scene2);
        staticsStage.setTitle("The player Staticsstics - 2016323");
        staticsStage.show();
    }
    public void matchSymbol(int x,int y,int z,Label lbl,Label lbl1,Label lbl2,Label reel1,Label reel2,Label reel3){ // method to calculate the winning and lossing and winning credits.
        if((reelOneVal == reelTwoVal) && (reelTwoVal == reelThreeVal) && (reelOneVal == reelThreeVal)){
            noOfWin = noOfWin +1;
            initialCredit = (initialCredit + (3 * x));
            betAmount = 0;
            lbl.setText("You Won");
            reel3.setStyle("-fx-border-color: green;");
            reel1.setStyle("-fx-border-color: green;");
            reel2.setStyle("-fx-border-color: green;");
        } else if (reelOneVal == reelTwoVal){
            noOfWin = noOfWin + 1;
            initialCredit = (initialCredit + (2 * x));
            betAmount = 0;
            lbl.setText("You Won");
            reel2.setStyle("-fx-border-color: green;");
            reel1.setStyle("-fx-border-color: green;");
        } else if(reelTwoVal == reelThreeVal){
            noOfWin = noOfWin +1;
            initialCredit = (initialCredit + (2 * y));
            betAmount = 0;
            lbl.setText("You Won");
            reel3.setStyle("-fx-border-color: green;");
            reel2.setStyle("-fx-border-color: green;");
        } else if(reelOneVal == reelThreeVal){
            noOfWin = noOfWin + 1;
            initialCredit = (initialCredit + (2 * z));
            betAmount = 0;
            lbl.setText("You Won");
            reel3.setStyle("-fx-border-color: green;");
            reel1.setStyle("-fx-border-color: green;");
        } else {
            onOfLoss = onOfLoss + 1;
            totalLossAmount += betAmount;
            betAmount = 0;
            lbl.setText("You Lost");
            reel3.setStyle("-fx-border-color: red;");
            reel1.setStyle("-fx-border-color: red;");
            reel2.setStyle("-fx-border-color: red;");
        }

        lbl2.setText("Current Bet amount is : " + betAmount + "$");
        lbl1.setText("The Credit Amount: " + initialCredit + "$");
       
         
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
