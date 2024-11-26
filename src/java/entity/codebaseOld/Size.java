/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Size {
    private int sizeId;
    private String sizeName;

    public Size() {
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Size)){
            return false;
        }
        
        Size s = (Size) obj;
        
        return this.sizeId == s.sizeId;
    }

    public Size(int sizeId, String sizeName) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
    }

    public int getSizeId() {
        return sizeId;
    }

    public Size setSizeId(int sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public String getSizeName() {
        return sizeName;
    }

    public Size setSizeName(String sizeName) {
        this.sizeName = sizeName;
        return this;
    }

    @Override
    public String toString() {
        return "Size{" + "sizeId=" + sizeId + ", sizeName=" + sizeName + '}';
    }
    
}
