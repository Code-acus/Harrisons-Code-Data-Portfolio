/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import harrison.c482.HarrisonC482_PA_InventoryParts;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Harrison Rogers
 */
public class MainMenuController implements Initializable 
{
    
    Stage stage;
    Parent scene; 
    static Product selectedProduct;
  
    @FXML
    private TableView<Part> vParts;
    @FXML
    private TableColumn<Part, Integer> vPartsIDColumn;
    @FXML
    private TableColumn<Part, String> vPartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> vPartsInvColumn;
    @FXML
    private TableColumn<Part, Double> vPartsPriceColumn;

    @FXML
    private TableView<Product> vProducts;
    @FXML
    private TableColumn<Product, Integer> vProductsIDColumn;
    @FXML
    private TableColumn<Product, String> vProductsNameColumn;
    @FXML
    private TableColumn<Product, Integer> vProductsInvColumn;
    @FXML
    private TableColumn<Product, Double> vProductsPriceColumn;

    @FXML
    private TextField txtSearchParts;

    @FXML
    private TextField txtSearchProducts;

    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;

    public static int partModifyIndex() 
    {
        return modifyPartIndex;
    }

    public static int productModifyIndex() 
    {
        return modifyProductIndex;
    }
    
    @FXML
    private Button buttonFXID;
    
    @FXML
    private Button buttonFXID2;

    public MainMenuController() 
    {
    }

   
    @FXML
    void partSearchHandler(ActionEvent event) 
    {
        String term = txtSearchParts.getText();
        ObservableList<Part> partList = Inventory.getAllParts();
        ObservableList<Part> newPartList = FXCollections.observableArrayList();

        for (Part p : partList) 
        {
            if (p.getName().contains(term)) 
            {
                newPartList.add(p);
            }
        }
        vParts.getItems().clear();
        vParts.getItems().addAll(newPartList);
    }

    @FXML
    void productSearchHandler(ActionEvent event) 
    {
        String term = txtSearchProducts.getText();
        ObservableList foundProducts = Inventory.lookupProduct(term);

        if (foundProducts.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Product match found");
            alert.setHeaderText("No Product Names found matching " + term);
            alert.showAndWait();
        } 
        
        else 
        {
            vProducts.setItems(foundProducts);
        }
    }

    @FXML
    void exitHandler(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm exit.");
        alert.setHeaderText("Confirm exit.");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            System.exit(0);
        }
    }

    @FXML
    void addPartHandler(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene)); 
        stage.show();
    }

    @FXML
    void modifyPartHandler(ActionEvent event) throws IOException 
    {
        Stage stage; 
        Parent root;       
        stage = (Stage) buttonFXID.getScene().getWindow();
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ModifyPartController controller = loader.getController();
        Part part = vParts.getSelectionModel().getSelectedItem();
        controller.setPart(part);
    }

    @FXML
    void deletePart(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene)); 
        stage.show();
        
        Part part = vParts.getSelectionModel().getSelectedItem();
        part = Inventory.lookupPart(part.getId());
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part delete");
        alert.setHeaderText("Confirm delete?");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (part != null && result.get() == ButtonType.OK) 
        {
            Inventory.getAllParts().remove(part);
        } 
        
        else 
        {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part delete error!");
            alert.setHeaderText("Part cannot be removed.");
            alert.setContentText("This part is used in a product.");
            alert.showAndWait();
        }
    }

    @FXML
    void addProductHandler(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene)); 
        stage.show();
        
        showAddProductWindow(event);
    }

    @FXML
    void modifyProductHandler(ActionEvent event) throws IOException 
    {
        selectedProduct = vProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
        {
            TODO: Alert User
        return;
        }
 
        Stage stage; 
        Parent root;       
        stage = (Stage) buttonFXID2.getScene().getWindow();
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
    }

    @FXML
            
    void deleteProductHandler(ActionEvent event) throws IOException 
    {
        Product product = vProducts.getSelectionModel().getSelectedItem();

        if (validateDeleteProduct(product)) 
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Product delete error!");
            alert.setHeaderText("Product cannot be removed.");
            alert.setContentText("This product contains a part.");
            alert.showAndWait();
        } 
        
        else 
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product delete");
            alert.setHeaderText("Confirm delete?");
            alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) 
            {
                validateRemoveAssociatedPart(product);
                updateProductTable();
                System.out.println("Part " + product.getName() + " was removed.");
            } 
            
            else 
            {
                System.out.println("Part " + product.getName() + " was not removed.");
            }
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
        vPartsIDColumn.setCellValueFactory(cellData ->
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getId());
        });
                
        vPartsNameColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getName());
        });
        
        vPartsInvColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getStock());
        });
        
        vPartsPriceColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getPrice());
        });

        vProductsIDColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getId());
        });
        
        vProductsNameColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getName());
        });
        
        vProductsInvColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getStock());
        });
        
        vProductsPriceColumn.setCellValueFactory(cellData -> 
        {
            return new ReadOnlyObjectWrapper(cellData.getValue().getPrice());
        });

        updatePartTable();
        updateProductTable();
    }

    public void updatePartTable() 
    {
        vParts.setItems(Inventory.getAllParts());                         
    }

    private void showModifyPartWindow(ActionEvent event) throws IOException 
    {
        modifyPart = vParts.getSelectionModel().getSelectedItem();
        if(modifyPart == null)
        {
            TODO: Put in alert
            return;
        }
        modifyPartIndex = getAllParts().indexOf(modifyPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("view/ModifyPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    private void showAddPartWindow(ActionEvent event) throws IOException 
    {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("view/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    public void updateProductTable() 
    {
        vProducts.setItems(getAllProducts());
    }

    private void showModifyProductWindow(ActionEvent event) throws IOException 
    {
        modifyProduct = vProducts.getSelectionModel().getSelectedItem();
        modifyProductIndex = getAllProducts().indexOf(modifyProduct);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    }

    private void showAddProductWindow(ActionEvent event) throws IOException 
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = HarrisonC482_PA_InventoryParts.getStage();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    public void setMainMenuController() 
    {
        updatePartTable();
        updateProductTable();
    }

    public void setInitialTableView() 
    {
        baseParts();
        baseProducts();
    }

    public static Part getModifyPart() 
    {
        return modifyPart;
    }

    public static Product getSelectedProduct() 
    {
        return selectedProduct;
    }

    private boolean validateDeleteProduct(Product product) 
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private void validateRemoveAssociatedPart(Product product) 
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private void baseParts() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void baseProducts() 
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
