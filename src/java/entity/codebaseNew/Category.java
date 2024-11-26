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
public class Category {
    
    private int id;
    private String name;
    private String originalType;
    private transient List<Product> productList;

    public Category() {
        this.id = 0;
        this.productList = null;
    }

    public Category(int id, String name, String originalType) {
        this.id = id;
        this.name = name;
        this.originalType = originalType;
        this.productList = null;
    }

    public int getId() {
        return id;
    }

    public Category setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getOriginalType() {
        return originalType;
    }

    public Category setOriginalType(String originalType) {
        this.originalType = originalType;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
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
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.originalType, other.originalType);
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", originalType=" + originalType + '}';
    }
    
}
