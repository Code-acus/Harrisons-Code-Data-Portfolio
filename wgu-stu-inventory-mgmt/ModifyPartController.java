// ModifyPartController

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import static controller.MainMenuController.partModifyIndex;
import model.Inventory;

import model.Part;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.InHouse;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author hrogers
 */
public class ModifyPartController implements Initializable 
{
    @FXML
    private TextField modPartIDField;
    @FXML
    private TextField modPartNameField;
    @FXML
    private TextField modPartInvField;
    @FXML
    private TextField modPartPriceField;
    @FXML
    private TextField modPartMaxField;
    @FXML
    private TextField modPartMinField;
    @FXML
    private TextField modDynField;
    @FXML
    private Label modDynLabel;
    @FXML
    private RadioButton modInHouseRadio;
    @FXML
    
    private RadioButton modOutsourcedRadio;
    private Part part;
    private boolean isOutsourced;
    private String exceptionMessage = new String();
    private int partID;
    int partIndex = partModifyIndex();
    
    @FXML
    void modPartInHouseRadio(ActionEvent event) throws IOException
    {
        isOutsourced = false;
        modOutsourcedRadio.setSelected(false);
        modDynLabel.setText("Machine ID");
        modDynField.setText("");
        modDynField.setPromptText("Machine ID");
    }
    
    @FXML
    void modPartOutsourcedRadio(ActionEvent event) throws IOException
    {
        isOutsourced = true;
        modInHouseRadio.setSelected(false);
        modDynLabel.setText("Company Name");
        modDynField.setText("");
        modDynField.setPromptText("Company Name");
    }
    
    @FXML
    void handleModPartSaveButton(ActionEvent event) throws IOException
    {
        String partName = modPartNameField.getText();
        String partInv = modPartInvField.getText();
        String partPrice = modPartPriceField.getText();
        String partMax = modPartMaxField.getText();
        String partMin = modPartMinField.getText();
        String partDyn = modDynField.getText();
        
        try
        {
            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if(exceptionMessage.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error modifying part.");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            
            else
            {
                if(modOutsourcedRadio.equals(false))
                {
                    System.out.println("Part name: " + partName);
                    InHouse partIn = new InHouse(partID, partName, Double.parseDouble(partPrice), Integer.parseInt(partInv), Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partDyn));
                    Inventory.getAllParts().add(partIn);
                }
                
                else
                {
                    Outsourced partOut = new Outsourced(partID, partName, Double.parseDouble(partPrice), Integer.parseInt(partInv), Integer.parseInt(partMin), Integer.parseInt(partMax), partDyn);
                    Inventory.getAllParts().add(partOut);
                }
                
                Inventory.getAllParts().remove(part);
                
                Parent partSave = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(partSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error adding part");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains invalid fields." + e);
            alert.showAndWait();
        }
    }
    
    @FXML
    void handleModPartCancelButton(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Cancel Modify");
        alert.setContentText("Are you sure you want to cancel modification?");
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
    }       

    void setPart(Part part) 
    {
        this.part = part;
 
        modPartIDField.setText("Auto Gen: " + partID);
        modPartNameField.setText(part.getName());
        modPartInvField.setText(Integer.toString(part.getStock()));
        modPartPriceField.setText(Double.toString(part.getPrice()));
        modPartMaxField.setText(Integer.toString(part.getMax()));
        modPartMinField.setText(Integer.toString(part.getMin()));
        
        if(part instanceof InHouse)
        {
            modDynLabel.setText("Machine ID");
            modDynField.setText(Integer.toString(((InHouse)part).getMachineId()));
            modInHouseRadio.setSelected(true);
        }
        
        else
        {
            modDynLabel.setText("Company Name");
            modDynField.setText(((Outsourced)part).getCompanyName());
            modOutsourcedRadio.setSelected(true);
        }
    }
}