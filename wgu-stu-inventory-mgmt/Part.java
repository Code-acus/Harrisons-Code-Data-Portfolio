/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Harrison Rogers
 */
public abstract class Part 
{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) 
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
        this.id = id;
    }
    
    public void setName(String name) 
    {
    }
     
    public void setPrice(double price) 
    {
    }
    
    public void setStock(int stock) 
    {
    }
    
    public void setMin(int min) 
    {
    }
    
    public void setMax(int max) 
    {
    }
    
    public void setPrice(int max) 
    {
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
   
    public static String isPartValid(String name, int min, int max, int inv, double price, String errorMessage)
    {
        if(name == null)
        {
            errorMessage = errorMessage + "Must enter a name.";
        }
        else if(price <= 0)
        {
            errorMessage = errorMessage + "Price must be greater than $0.";
        }
        else if(inv < 1)
        {
            errorMessage = errorMessage + "Inventory must be greater than 0.";
        }
        else if(inv > max)
        {
            errorMessage = errorMessage + "Inventory must be less than the MAX.";
        }
        else if(inv < min || inv > max)
        {
            errorMessage = errorMessage + "Inventory must be between MIN and MAX values.";
        }
        return errorMessage;
    }
}