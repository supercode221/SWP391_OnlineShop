/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class SpecifyProduct extends Product {
    
    private Color color;
    private Size size;
    private int quantity;

    public SpecifyProduct() {
        super();
    }

    

    public Color getColor() {
        return color;
    }

    public SpecifyProduct setColor(Color color) {
        this.color = color;
        return this;
    }

    public Size getSize() {
        return size;
    }

    public SpecifyProduct setSize(Size size) {
        this.size = size;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public SpecifyProduct setQuantity(int quantity) {
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
        final SpecifyProduct other = (SpecifyProduct) obj;
        if (super.getId() != other.id) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return Objects.equals(this.size, other.size);
    }

    @Override
    public String toString() {
        return "SpecifyProduct{" + "id=" + super.getId() + ", name=" + super.getName() + ", thumbnailImage=" + super.getThumbnailImage() + ", tagList=" + tagList + ", color=" + color + ", size=" + size + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
}
