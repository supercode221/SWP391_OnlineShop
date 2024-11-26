/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseOld;

import java.util.List;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Feedback {

    private int FeedbackId;
    private int userId;
    private int orderId;
    private User user;
    private Product product;
    private int productId;
    private int star;
    private String comment;
    private List linkToMedia;
    private String userName;
    private String productName;
    private String status;

    public Feedback() {
    }

    public Feedback(int FeedbackId, int userId, int orderId, User user, Product product, int productId, int star, String comment, List linkToMedia, String userName, String productName, String status) {
        this.FeedbackId = FeedbackId;
        this.userId = userId;
        this.orderId = orderId;
        this.user = user;
        this.product = product;
        this.productId = productId;
        this.star = star;
        this.comment = comment;
        this.linkToMedia = linkToMedia;
        this.userName = userName;
        this.productName = productName;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(int FeedbackId) {
        this.FeedbackId = FeedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List getLinkToMedia() {
        return linkToMedia;
    }

    public void setLinkToMedia(List linkToMedia) {
        this.linkToMedia = linkToMedia;
    }

    @Override
    public String toString() {
        return "Feedback{" + "FeedbackId=" + FeedbackId + ", userId=" + userId + ", orderId=" + orderId + ", productId=" + productId + ", star=" + star + ", comment=" + comment + ", linkToMedia=" + linkToMedia + '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
