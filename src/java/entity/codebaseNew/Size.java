/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Size {
    
    private int id;
    private String size;

    public Size() {
    }

    public Size(int id, String size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public Size setId(int id) {
        this.id = id;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Size setSize(String size) {
        this.size = size;
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
        final Size other = (Size) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Size{" + "id=" + id + ", size=" + size + '}';
    }
    
}
