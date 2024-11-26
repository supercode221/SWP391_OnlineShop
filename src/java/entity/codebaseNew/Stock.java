/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

import java.util.Objects;

/**
 *
 * @author Nguyễn Nhật Minh
 */
class Stock {
    
    private Product product;
    private Size size;
    private Color color;
    private int quantity;

    public Stock() {
    }

    public Stock(Size size, Color color, int quantity) {
        this.product = null;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Stock setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Size getSize() {
        return size;
    }

    public Stock setSize(Size size) {
        this.size = size;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Stock setColor(Color color) {
        this.color = color;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Stock setQuantity(int quantity) {
        this.quantity = quantity;
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
        final Stock other = (Stock) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.size, other.size)) {
            return false;
        }
        return Objects.equals(this.color, other.color);
    }

    @Override
    public String toString() {
        return "Stock{" + "product=" + product + ", size=" + size + ", color=" + color + ", quantity=" + quantity + '}';
    }
    
}
