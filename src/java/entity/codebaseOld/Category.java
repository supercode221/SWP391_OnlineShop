/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Category {
    private int id;
    private String name;
    private String originalType;

    public Category() {
    }

    public Category(int id, String name, String originalType) {
        this.id = id;
        this.name = name;
        this.originalType = originalType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalType() {
        return originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", originalType=" + originalType + '}';
    }
    
}
