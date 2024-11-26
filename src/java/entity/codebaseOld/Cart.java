/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Cart {
    private int cartId;
    private int userId;
    private String productName;
    private String productDes;
    private double productPrice;
    private int quantity;

    public Cart() {
    }

    public Cart(int cartId, int userId, 
            String productName, String productDes, 
            double productPrice, int quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productName = productName;
        this.productDes = productDes;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" + "cartId=" + cartId + ", userId=" + userId + ", productName=" + productName + ", productDes=" + productDes + ", productPrice=" + productPrice + ", quantity=" + quantity + '}';
    }
}
