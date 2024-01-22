// AddProductController

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Product;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Inventory;
import static model.Inventory.getAllParts;
import model.Part;

/**
 * FXML Controller class
 *
 * @author hrogers
 */
public class AddProductController implements Initializable 
{

    private final ObservableList<Part> Parts = FXCollections.observableArrayList();

    @FXML
    private TextField productSearch;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInvField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    @FXML
    private TableView<Part> vProductAdd;
    @FXML
    private TableColumn<Part, Integer> vProductAddID;
    @FXML
    private TableColumn<Part, String> vProductAddName;
    @FXML
    private TableColumn<Part, Integer> vProductAddInv;
    @FXML
    private TableColumn<Part, Double> vProductAddPrice;
    @FXML
    private TableView<Part> vProductDelete;
    @FXML
    private TableColumn<Part, Integer> vProductDeleteID;
    @FXML
    private TableColumn<Part, String> vProductDeleteName;
    @FXML
    private TableColumn<Part, Integer> vProductDeleteInv;
    @FXML
    private TableColumn<Part, Double> vProductDeletePrice;

    private String exceptionMessage = new String();
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private Product product;

   
    @FXML
    void handleProductSearch(ActionEvent event) 
    {
        String term = productSearch.getText();
        ObservableList<Part> partList = Inventory.getAllParts();
        ObservableList<Part> newPartList = FXCollections.observableArrayList();

        for (Part p : partList) 
        {
            if (p.getName().contains(term)) 
            {
                newPartList.add(p);
            }
        }

        if (newPartList.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part match found");
            alert.setHeaderText("No Part Names found matching " + term);
            alert.showAndWait();
        } 
        
        else 
        {
            vProductAdd.getItems().clear();
            vProductAdd.getItems().addAll(newPartList);
        }
    }

    @FXML
    void handleProductAddButton(ActionEvent event) 
    {
        Part p = vProductAdd.getSelectionModel().getSelectedItem();
       
        if (p == null) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm");
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a part to add.");
            alert.showAndWait();
            return;
        }
        vProductDelete.getItems().add(p);
    }

    @FXML
    void handleAddProductDeleteButton(ActionEvent event) 
    {
        Part part = vProductAdd.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Confirm delete");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            System.out.println("Deletion successful.");
            Parts.remove(part);
        } 
        
        else 
        {
            System.out.println("Operation cancelled.");
        }
    }

    @FXML
    void handleAddProductSaveButton(ActionEvent event) throws IOException 
    {
        int productId = Integer.parseInt(productIDField.getText().substring(9));
        String productName = productNameField.getText();
        String productInv = productInvField.getText();
        String productPrice = productPriceField.getText();
        String productMax = productMaxField.getText();
        String productMin = productMinField.getText();

        try 
        {
            ObservableList<Part> currentParts = vProductDelete.getItems();
           
            exceptionMessage = Product.isProductValid(productName, Double.parseDouble(productPrice), Integer.parseInt(productInv), Integer.parseInt(productMin), Integer.parseInt(productMax),
                    currentParts, exceptionMessage);

            if (exceptionMessage.length() > 0) 
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding product.");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } 
            
            else 
            {
                System.out.println("Product name: " + productName);
                Product newProduct = new Product(productId, productName, Double.parseDouble(productPrice), Integer.parseInt(productInv), Integer.parseInt(productMax), Integer.parseInt(productMin));
               
                Inventory.addProduct(newProduct);

                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } 
        
        catch (NumberFormatException e) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding product.");
            alert.setContentText("Form contains invalid fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddProductCancel(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            Parent addProductCancel = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
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
        productIDField.setText("AutoGen: " + Inventory.getNextId());
        vProductAddID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        vProductAddName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
        vProductAddInv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStock()));
        vProductAddPrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPrice()));
        vProductDeleteID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        vProductDeleteName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
        vProductDeleteInv.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStock()));
        vProductDeletePrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPrice()));

        updatePartTableViewAdd();
        updatePartTableViewDelete();

        productList = Inventory.getAllProducts();
    }

    public void updatePartTableViewDelete() 
    {
        vProductDelete.setItems(Parts);
    }

    public void updatePartTableViewAdd() 
    {
        vProductAdd.setItems(getAllParts());
    }
}
