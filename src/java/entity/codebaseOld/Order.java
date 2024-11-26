/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Order {
    private int id;
    private int productId;
    private int quantity;
    private double totalPrice;
    private int colorId;
    private int sizeId;
    private int billId;
    private boolean isFeedbacked;

    public Order() {
    }

    public Order(int id, int productId, int quantity, double totalPrice, int colorId, int sizeId, int billId, boolean isFeedbacked) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.billId = billId;
        this.isFeedbacked = isFeedbacked;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public boolean isIsFeedbacked() {
        return isFeedbacked;
    }

    public void setIsFeedbacked(boolean isFeedbacked) {
        this.isFeedbacked = isFeedbacked;
    }
}