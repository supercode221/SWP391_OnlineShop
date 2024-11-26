/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class ProductImage {

    private int id;
    private int productId;
    private String link;

    public ProductImage() {
    }

    public ProductImage(int id, int productId, String link) {
        this.id = id;
        this.productId = productId;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
