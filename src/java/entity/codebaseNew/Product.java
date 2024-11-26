/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

import java.awt.Image;
import java.util.List;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Product {
    
    protected int id;
    protected String name;
    protected Category category;
    protected List<Tag> tagList;
    protected List<Color> colorList;
    protected List<Size> sizeList;
    protected List<Stock> stockList;
    protected double star;
    protected String description;
    protected String thumbnailImage;
    protected List<Image> imageList;
    protected String status;
    protected int price;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public Product setTagList(List<Tag> tagList) {
        this.tagList = tagList;
        return this;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public Product setColorList(List<Color> colorList) {
        this.colorList = colorList;
        return this;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public Product setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
        return this;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public Product setStockList(List<Stock> stockList) {
        this.stockList = stockList;
        return this;
    }

    public double getStar() {
        return star;
    }

    public Product setStar(double star) {
        this.star = star;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public Product setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
        return this;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public Product setImageList(List<Image> imageList) {
        this.imageList = imageList;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Product setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", tagList=" + tagList + ", colorList=" + colorList + ", sizeList=" + sizeList + ", stockList=" + stockList + ", star=" + star + ", description=" + description + ", thumbnailImage=" + thumbnailImage + ", imageList=" + imageList + ", status=" + status + ", price=" + price + '}';
    }
    
}
