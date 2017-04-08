/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightcontroller;

import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;


/**
 *
 * @author ykk
 */
public class LightController extends Application {
    private HookRest hook = new HookRest("bcf9faad5e630cb8c86f88ca0cab6108");
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Light Controller");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label username = new Label("username:");
        grid.add(username, 0, 1);
        
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
      
        Button loginBtn = new Button("Log in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
                CharSequence username = userTextField.getCharacters();
                CharSequence pw = pwBox.getText();
                
                
                //TODO:validate password and stuff
                /*if(hook.hookRestLogin(username, pw))
                {
                    System.out.println("token is : " + hook.getToken());
                    home(primaryStage);
                }*/
                home(primaryStage);
                 
            }
        });
    }

    public void home(Stage primaryStage)
    {        
        //control
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(grid, 300, 275);
                
        ArrayList<Light>lights = new ArrayList<Light>();
        hook.getDevice(lights);
        
        ArrayList<Button>onButtons = new ArrayList<Button>();
        ArrayList<Button>offButtons = new ArrayList<Button>();
        
        //populate welcome page without 
        for(int i=0; i<lights.size(); i++)
        {
            grid.add(new Text(lights.get(i).getName()),0,i);
            onButtons.add(new Button("On"));
            offButtons.add(new Button("Off"));
            final Light light = lights.get(i);
            final Button onButton = onButtons.get(i);
            final Button offButton = offButtons.get(i);
           
            onButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                int action = hook.triggerAction(light,"on");    
            }});
            
            offButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                int action = hook.triggerAction(light,"off");    
            }});
        
            grid.add(onButtons.get(i), 2, i);
            grid.add(offButtons.get(i), 3, i);
        }
        
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
