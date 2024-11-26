/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

import codebase.hashFunction.SHA256_Encryptor;

/**
 *
 * Hoang Anh Dep Trai
 */
public class User {
    private int id;
    private String firstName;
    private String LastName;
    private String email;
    private String encodedPassword;
    private String phoneNumber;
    private String description;
    private String avatarImage;
    private int changeLodID;
    private int roleId;
    private String status;

    public User() {
    }

    public User(int id, String firstName, String LastName, String email, String encodedPassword, String phoneNumber, String description, String avatarImage, int changeLodID, int roleId, String status) {
        this.id = id;
        this.firstName = firstName;
        this.LastName = LastName;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.avatarImage = avatarImage;
        this.changeLodID = changeLodID;
        this.roleId = roleId;
        this.status = status;
    }

    public User(int id, String avatarImage, int roleId, String status) {
        this.id = id;
        this.avatarImage = avatarImage;
        this.roleId = roleId;
        this.status = status;
    }

    public User(int id, String firstName, String LastName, String phoneNumber, String avatarImage, int chanLogID, int roleId) {
        this.id = id;
        this.firstName = firstName;
        this.LastName = LastName;
        this.phoneNumber = phoneNumber;
        this.avatarImage = avatarImage;
        this.changeLodID = chanLogID;
        this.roleId = roleId;
    }
    
    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return LastName;
    }

    public User setLastName(String LastName) {
        this.LastName = LastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public User setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
        return this;
    }

    public User setPassword(String password) {
        this.encodedPassword = SHA256_Encryptor.sha256Hash(password);
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public User setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
        return this;
    }

    public int getChangeLodID() {
        return changeLodID;
    }

    public User setChangeLodID(int changeLodID) {
        this.changeLodID = changeLodID;
        return this;
    }

    public int getRoleId() {
        return roleId;
    }

    public User setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", LastName=" + LastName + ", email=" + email + ", encodedPassword=" + encodedPassword + ", phoneNumber=" + phoneNumber + ", description=" + description + ", avatarImage=" + avatarImage + ", changeLodID=" + changeLodID + ", roleId=" + roleId + ", status=" + status + '}';
    }

    public int getUserID() {
        return this.id;
    }
}
