/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Permission {
    private int perId;
    private String name;
    private String code;
    private String description;
    private int roleId;

    public Permission() {
    }

    public Permission(int perId, String name, String code, String description, int roleId) {
        this.perId = perId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.roleId = roleId;
    }

    public int getPerId() {
        return perId;
    }

    public void setPerId(int perId) {
        this.perId = perId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Permission{" + "perId=" + perId + ", name=" + name + ", code=" + code + ", description=" + description + ", roleId=" + roleId + '}';
    }
    
}
