// ModifyProductController

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Part;
import model.Product;
import static controller.MainMenuController.productModifyIndex;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Inventory;

/**
 * FXML Controller class
 *
 * @author Harrison Rogers
 */
public class ModifyProductController implements Initializable 
{

    Stage stage;
    Parent root;

    private ObservableList<Part> modCurrentParts = FXCollections.observableArrayList();

    @FXML
    private TextField modProductSearch;
    @FXML
    private TextField modProductIDField;
    @FXML
    private TextField modProductNameField;
    @FXML
    private TextField modProductInvField;
    @FXML
    private TextField modProductPriceField;
    @FXML
    private TextField modProductMaxField;
    @FXML
    private TextField modProductMinField;
    
    @FXML
    private TableView<Part> vModUpperPartTable;
    @FXML
    private TableColumn<Part, Integer> vModProductAddID;
    @FXML
    private TableColumn<Part, String> vModProductAddName;
    @FXML
    private TableColumn<Part, Integer> vModProductAddInv;
    @FXML
    private TableColumn<Part, Double> vModProductAddPrice;
    
    @FXML
    private TableView<Part> vModLowerPartTable;
    @FXML
    private TableColumn<Part, Integer> vModProductDeleteID;
    @FXML
    private TableColumn<Part, String> vModProductDeleteName;
    @FXML
    private TableColumn<Part, Integer> vModProductDeleteInv;
    @FXML
    private TableColumn<Part, Double> vModProductDeletePrice;
    
    private String exceptionMessage = new String();
    private Product product;
    private int productID;
    private final int productIndex = productModifyIndex();
    
    @FXML
    private Button Add;
    @FXML
    private Button Save;
    @FXML
    private Button Cancel;
    @FXML
    private Button Delete;

    //Search bar and button
    @FXML
    void handleModProductSearch(ActionEvent event) 
    {
        String term = modProductSearch.getText();
        ObservableList foundParts = Inventory.lookupPart(term);

        if (foundParts.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part match found");
            alert.setHeaderText("No Part Names found matching " + term);
            alert.showAndWait();
        } 
        
        else 
        {
            vModUpperPartTable.setItems(foundParts);
        }
    }

    @FXML
    void handleModProductAddButton(ActionEvent event) 
    {
        Part part = vModUpperPartTable.getSelectionModel().getSelectedItem();
        modCurrentParts.add(part);
    }

    @FXML
    void handleModProductDeleteButton(ActionEvent event) 
    {
        Part part = vModLowerPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Confirm delete");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            product.getAllAssociatedParts().remove(part);
            System.out.println("Deletion successful.");
            
        } 
        
        else 
        {
            System.out.println("Operation cancelled.");
        }
    }

    @FXML
    void handleModProductSaveButton(ActionEvent event) throws IOException 
    {
        String productName = modProductNameField.getText();
        String productInv = modProductInvField.getText();
        String productPrice = modProductPriceField.getText();
        String productMax = modProductMaxField.getText();
        String productMin = modProductMinField.getText();

        try 
        {
            System.out.println("Counts = " + exceptionMessage.length());
            if (productName.equals("")) {
                exceptionMessage += "Product name, ";
            }

            if (productInv.equals("")) {
                exceptionMessage += "Product inventory, ";
            }

            if (productPrice.equals("")) {
                exceptionMessage += "Product price, ";
            }

            if (productMax.equals("")) {
                exceptionMessage += "Product maximum, ";
            }

            if (productMin.equals("")) {
                exceptionMessage += "Product minumum, ";
            }

            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding product.");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
                return;
            }

            product.setName(productName);
            product.setPrice(Double.parseDouble(productPrice));
            product.setMax(Integer.parseInt(productMax));
            product.setMin(Integer.parseInt(productMin));
            product.setStock(Integer.parseInt(productInv));
            
            while (product.getAllAssociatedParts().size() != 0)
                    product.getAllAssociatedParts().remove(0);
            
                    product.getAllAssociatedParts().addAll(modCurrentParts);

            System.out.println("Product name: " + productName);

            Inventory.updateProduct(productIndex, product);

            Parent modProductSaveParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene scene = new Scene(modProductSaveParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } 
        
        catch (NumberFormatException e) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error modifying product.");
            alert.setContentText("Form contains invalid fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleModProductCancel(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            stage = (Stage) Cancel.getScene().getWindow();
 
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/view/MainMenu.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } 
        
        else 
        {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        product = MainMenuController.getSelectedProduct();

        productID = product.getId();
        
        modProductIDField.setText("Auto Gen: " + productID);
        modProductNameField.setText(product.getName());
        modProductInvField.setText(Integer.toString(product.getStock()));
        modProductPriceField.setText(Double.toString(product.getPrice()));
        modProductMaxField.setText(Integer.toString(product.getMax()));
        modProductMinField.setText(Integer.toString(product.getMin()));
        

        vModProductAddID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
      
        vModProductAddName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
        
        vModProductAddInv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStock()));
        
        vModProductAddPrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPrice()));
        
        vModProductDeleteID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        
        vModProductDeleteName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
       
        vModProductDeleteInv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStock()));
        
        vModProductDeletePrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPrice()));
        
        vModUpperPartTable.setItems(Inventory.getAllParts());
        modCurrentParts.addAll(product.getAllAssociatedParts());
        vModLowerPartTable.setItems(modCurrentParts);
    }

    void setProduct(Product product) 
    {
        this.product = product;

        modProductIDField.setText("Auto Gen: " + product.getId());
        modProductNameField.setText(product.getName());
        modProductInvField.setText(Integer.toString(product.getStock()));
        modProductPriceField.setText(Double.toString(product.getPrice()));
        modProductMaxField.setText(Integer.toString(product.getMax()));
        modProductMinField.setText(Integer.toString(product.getMin()));

        vModUpperPartTable.setItems(Inventory.getAllParts());
    }
}
