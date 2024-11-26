/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Color {
    
    private int id;
    private String name;
    private String colorCode;

    public Color() {
    }

    public Color(int id, String name, String colorCode) {
        this.id = id;
        this.name = name;
        this.colorCode = colorCode;
    }

    public int getId() {
        return id;
    }

    public Color setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Color setName(String name) {
        this.name = name;
        return this;
    }

    public String getColorCode() {
        return colorCode;
    }

    public Color setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
        final Color other = (Color) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Color{" + "id=" + id + ", name=" + name + ", colorCode=" + colorCode + '}';
    }
    
}
