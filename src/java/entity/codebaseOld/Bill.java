/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Bill {
    private int id;
    private double totalPrice;
    private Date publishDate;
    private String status;
    private int customerId;
    private int salerId;
    private int shipperId;
    private int addressId;
    private int shipMethodId;
    private int paymentMethodId;
    private boolean isFeedbacked;
    private boolean isCanceledPending;

    public Bill() {
    }

    public Bill(int id, double totalPrice, Date publishDate, String status, int customerId, int salerId, int shipperId, int addressId, int shipMethodId, int paymentMethodId, boolean isFeedbacked, boolean isCanceledPending) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.publishDate = publishDate;
        this.status = status;
        this.customerId = customerId;
        this.salerId = salerId;
        this.shipperId = shipperId;
        this.addressId = addressId;
        this.shipMethodId = shipMethodId;
        this.paymentMethodId = paymentMethodId;
        this.isFeedbacked = isFeedbacked;
        this.isCanceledPending = isCanceledPending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSalerId() {
        return salerId;
    }

    public void setSalerId(int salerId) {
        this.salerId = salerId;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(int shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public boolean isIsFeedbacked() {
        return isFeedbacked;
    }

    public void setIsFeedbacked(boolean isFeedbacked) {
        this.isFeedbacked = isFeedbacked;
    }

    public boolean isIsCanceledPending() {
        return isCanceledPending;
    }

    public void setIsCanceledPending(boolean isCanceledPending) {
        this.isCanceledPending = isCanceledPending;
    }

    
}
