// Product Controller

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Harrison Rogers
 */
public class Product
{       
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product()
    {
    }
    
    public Product(int id, String name, double price, int stock, int min, int max) 
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }    
    
    public void setId(int id) 
    {
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public void setPrice(double price) 
    {
        this.price = price;
    }

    public void setStock(int stock) 
    {
        this.stock = stock;
    }
    
    public void setMin(int min) 
    {
        this.min = min;
    }
    
    public void setMax(int max) 
    {
        this.max = max;
    }
    
    public void setPrice(int price) 
    {
        this.price = price;
    }
    
    public int getId() 
    {
        return id; 
    }

    public String getName() 
    {
        return name;
    }

    public double getPrice() 
    {
        return price;
    }

    public int getStock() 
    {
        return stock;
    }

    public int getMin() 
    {
        return min;
    }

    public int getMax() 
    {
        return max;
    }
   
    public void deleteAssociatedPart(Part part)
    {
    }
 
    public ObservableList<Part> getAllAssociatedParts() 
    {
        return associatedParts;
    }
    
    public void addAssociatedPart(Part associatedPart)
    {
        this.associatedParts.add(associatedPart);
    }
    
    public boolean removeAssociatedPart(int id)
    {
        for(Part p : associatedParts)
        {
            if(p.getId() == id)
            {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public Part lookupAssociatedPart(int id)
    {
        for(Part p : associatedParts)
        {
            if(p.getId() == id)
            {
                return p;
            }
        }
        return null;
    }
    
    public static String isProductValid(String name, double price, int inv, int min, int max, ObservableList<Part> associatedParts, String errorMessage)
    {
        double partsSum = 0.00;
        for(int i = 0; i <  associatedParts.size(); i++)
        {
            partsSum = partsSum + associatedParts.get(i).getPrice();
        }
        
        if(name == null)
        {
            errorMessage = errorMessage + "Must enter a name.";
        }
        
        else if(price <= 0)
        {
            errorMessage = errorMessage + "Price must be greater than $0.";
        }
        
        else if(price < partsSum)
        {
            errorMessage = errorMessage + "Product price must be greater than sum of the parts price.";
        }
        
        else if(associatedParts.size() < 1)
        {
            errorMessage = errorMessage + "Product must contain at least 1 part.";
        }
        
        else if(inv < 1)
        {
            errorMessage = errorMessage + "Inventory must be greater than 0.";
        }
        
        else if(max < min)
        {
            errorMessage = errorMessage + "Inventory MIN must be less than the MAX.";
        }
        
        else if(inv < min || inv > max)
        {
            errorMessage = errorMessage + "Inventory must be between MIN and MAX values.";
        }
        
        return errorMessage;
    }
}