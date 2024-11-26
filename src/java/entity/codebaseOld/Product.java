/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Product {
    private int id;
    private int subCategoryId;
    private String name;
    private String description;
    private double price;
    private float star;
    private String thumbnailImage;
    private String status;

    public Product(){
        
    }
    
    public Product(int id, int subCategoryId, String name, String description, double price, float star, String thumbnailImage, String status) {
        this.id = id;
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.star = star;
        this.thumbnailImage = thumbnailImage;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", subCategoryId=" + subCategoryId + ", name=" + name + ", description=" + description + ", price=" + price + ", star=" + star + ", thumbnailImage=" + thumbnailImage + ", status=" + status + '}';
    }
}
