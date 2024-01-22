// HarrisonC482_PA_InventoryParts

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harrison.c482;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author hrogers
 */
public class HarrisonC482_PA_InventoryParts extends Application 
{

private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception 
    {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
                        
        Part part1 = new InHouse(Inventory.getNextId(), "Phillips Head Screw #31", 2.19, 2, 4, 36, 111);
        Part part2 = new InHouse(Inventory.getNextId(), "Phillips Head Screw #12", 1.23, 4, 12, 29, 222);
        Part part3 = new InHouse(Inventory.getNextId(), "Phillips Head Screw #23", .99, 4, 12, 29, 333);
        Part part1A = new Outsourced(Inventory.getNextId(), "Hex Head Screw #77", 3.01, 15, 12, 19, "Ajax");
              
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part1A);
        
        Product product2a = new Product(Inventory.getNextId(), "Foo's NewNImporved Fubar", 23.99, 5, 6, 7);
        Product product66 = new Product(Inventory.getNextId(), "Foo's NewerNImporved Fubar", 19.99, 5, 6, 7);
       
        Inventory.addProduct(product2a);
        Inventory.addProduct(product66);
        
        launch(args);
    }

    public static Stage getStage() 
    {
        return stage;
    }
    
}
