/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Color {
    private int colorId;
    private String colorName;
    private String colorCode;

    public Color() {
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Color)){
            return false;
        }
        
        Color c = (Color) obj;
        
        return this.colorId == c.colorId;
    }

    public Color(int colorId, String colorName, String colorCode) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

    public int getColorId() {
        return colorId;
    }

    public Color setColorId(int colorId) {
        this.colorId = colorId;
        return this;
    }

    public String getColorName() {
        return colorName;
    }

    public Color setColorName(String colorName) {
        this.colorName = colorName;
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
    public String toString() {
        return "Color{" + "colorId=" + colorId + ", colorName=" + colorName + ", colorCode=" + colorCode + '}';
    }
    
}
