/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Permission {
    
    private int id;
    private String permissionNode;

    public Permission() {
    }

    public Permission(int id, String permissionNode) {
        this.id = id;
        this.permissionNode = permissionNode;
    }

    public int getId() {
        return id;
    }

    public Permission setId(int id) {
        this.id = id;
        return this;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

    public Permission setPermissionNode(String permissionNode) {
        this.permissionNode = permissionNode;
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
        final Permission other = (Permission) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Permission{" + "id=" + id + ", permissionNode=" + permissionNode + '}';
    }
    
}
