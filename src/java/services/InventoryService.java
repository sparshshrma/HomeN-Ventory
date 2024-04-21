package services;

import dataaccess.ItemDB;
import java.util.List;
import models.Category;
import models.Item;
import models.User;


public class InventoryService {
    

    public Item get(int itemId) throws Exception{
        ItemDB itemDB = new ItemDB();
        Item item = itemDB.get(itemId);
        return item;
    }
    

    public List<Item> getAll() throws Exception{
        ItemDB itemDB = new ItemDB();
        List<Item> items = itemDB.getAll();
        
        return items;
    }

    public void insert(String itemname, double price, Category category, User owner) throws Exception {
        ItemDB itemDB = new ItemDB();
        
        Item item = new Item(0, itemname, price);
        
        item.setCategory(category);
        item.setOwner(owner);
        category.getItemList().add(item);
        owner.getItemList().add(item);
        itemDB.insert(item);
    }
    

    public void update(int itemId, String itemname, double price, int categoryId) throws Exception {
        ItemDB itemDB = new ItemDB();
        
        Item item = itemDB.get(itemId);
        item.setItemName(itemname);
        item.setPrice(price);
        Category category = item.getCategory();
        category.setCategoryId(categoryId);
        item.setCategory(category);
        
        itemDB.update(item);
              
    }
    

    public void delete(int itemId) throws Exception{
        ItemDB itemDB = new ItemDB();  
        Item item = itemDB.get(itemId);
        itemDB.delete(item);
    }
}
