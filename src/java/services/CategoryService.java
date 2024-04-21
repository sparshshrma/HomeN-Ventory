package services;

import dataaccess.CategoryDB;
import java.util.List;
import models.Category;


public class CategoryService {

    public List<Category> getAll() throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        List<Category> categories = categoryDB.getAll();
        return categories;
    }

    
    public Category get(int id) throws Exception{
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(id);
        return category;
    } 
    

    public void insert(String categoryName) throws Exception{
        CategoryDB categoryDB = new CategoryDB();
        Category category = new Category(0, categoryName);
        categoryDB.insert(category); 
    }
    

    public void update(int categoryId, String categoryName) throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryId);
        category.setCategoryName(categoryName);
        
        categoryDB.update(category);
        
    }
}
