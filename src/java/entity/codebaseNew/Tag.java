/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Tag {
    
    private int id;
    private String name;
    private String color;

    public Tag() {
    }

    public Tag(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Tag setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Tag setColor(String color) {
        this.color = color;
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
        final Tag other = (Tag) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name=" + name + ", color=" + color + '}';
    }
    
}
