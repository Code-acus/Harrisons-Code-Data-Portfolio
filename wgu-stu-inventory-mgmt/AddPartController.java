// AddPartController

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author Harrison Rogers
 */
public class AddPartController implements Initializable 
{
    
    //Radio button and text fields
    @FXML
    private RadioButton radioInHouse;
    @FXML
    private RadioButton radioOutsourced;
    @FXML
    private TextField partIDField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partInvField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField partCoNameTextField;
   
    private boolean isOutsourced;
    private String exceptionMessage = new String();
    
    @FXML
    private ToggleGroup tgPartsToggle;
    
    //Radio buttons and dynamic fields
    @FXML
    void addPartInHouseRadio(ActionEvent event)
    {
        isOutsourced = false;
        companyNameLabel.setText("Machine ID");
        partCoNameTextField.setPromptText("Machine ID");
        radioOutsourced.setSelected(false);
    }
    
    @FXML
    void addPartOutsourcedRadio(ActionEvent event)
    {
        isOutsourced = true;
        companyNameLabel.setText("Company Name");
        partCoNameTextField.setPromptText("Company Name");
        radioInHouse.setSelected(true);
    }
    

    @FXML
    void handleAddPartSaveButton(ActionEvent event) throws IOException
    {
        String partName = partNameField.getText();
        String partInv = partInvField.getText();
        String partPrice = partPriceField.getText();
        String partMax = partMaxField.getText();
        String partMin = partMinField.getText();
        String partDynamic = partCoNameTextField.getText();
        
        Double dblPartPrice = Double.parseDouble(partPrice);
        int intPartInv = Integer.parseInt(partInv);
        int intPartMax = Integer.parseInt(partMax);
        int intPartMin = Integer.parseInt(partMin);
        
        try
        {
            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), (int) Double.parseDouble(partPrice), exceptionMessage);
        
            if(exceptionMessage.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding part.");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            
            else
            {
                if(tgPartsToggle.equals(radioInHouse))
                {
                    System.out.println("Part name: " + partName);
                    
                    int intPartDynamic = Integer.parseInt(partDynamic);
                    Part part = new InHouse(Inventory.getNextId(), partName, dblPartPrice, intPartInv, intPartMin, intPartMax, intPartDynamic);
                    
                    Inventory.addPart(part);
                }
                
                else
                {
                    Outsourced part = new Outsourced(Inventory.getNextId(), partName, dblPartPrice, intPartInv, intPartMin, intPartMax, partDynamic);
                   
                    Inventory.addPart(part);
                }
                
                Parent partSave = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(partSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error adding part");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains invalid fields." + e);
            alert.showAndWait();
        }
    }
    
    @FXML
    void handleAddPartCancelButton(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel - all data will be lost "  + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK)
        {
            Parent partCancel = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene scene = new Scene(partCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        
        else
        {
            System.out.println("Cancelled.");
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        partIDField.setText("Auto Gen: " + Inventory.getNextId());
    }    
}
