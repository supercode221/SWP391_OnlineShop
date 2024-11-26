/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Address {

    private int id;
    private transient User user;
    private String country;
    private String tinh_thanhpho;
    private String huyen_quan;
    private String xa_phuong;
    private String details;
    private String note;
    private String format;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public Address setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Address setUser(User user) {
        this.user = user;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getTinh_thanhpho() {
        return tinh_thanhpho;
    }

    public Address setTinh_thanhpho(String tinh_thanhpho) {
        this.tinh_thanhpho = tinh_thanhpho;
        return this;
    }

    public String getHuyen_quan() {
        return huyen_quan;
    }

    public Address setHuyen_quan(String huyen_quan) {
        this.huyen_quan = huyen_quan;
        return this;
    }

    public String getXa_phuong() {
        return xa_phuong;
    }

    public Address setXa_phuong(String xa_phuong) {
        this.xa_phuong = xa_phuong;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Address setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Address setNote(String note) {
        this.note = note;
        return this;
    }
    
    public Address setFormat() {
        this.format = this.getFormat();
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
        final Address other = (Address) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Address{"
                + "id=" + id + ", "
                + "country=" + country + ", "
                + "tinh_thanhpho=" + tinh_thanhpho + ", "
                + "huyen_quan=" + huyen_quan + ", "
                + "xa_phuong=" + xa_phuong + ", "
                + "details=" + details + ", "
                + "note=" + note
                + '}';
    }

    public String getFormat() {
        String[] fields = {this.details, this.xa_phuong, this.huyen_quan, this.tinh_thanhpho, this.country};
        return Arrays.stream(fields)
                .filter(this::isNotNull) // Replace `isNull` with `isNotNull` for clarity
                .collect(Collectors.joining(" - "));
    }

    private boolean isNotNull(String field) {
        return field != null && !field.trim().isEmpty();
    }
    
}
