/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseNew;

import java.util.List;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Role {

    private int id;
    private String name;
    private int weight;
    private List<Permission> permissionList;

    public Role() {
        this.weight = 0;
    }

    public Role(String name) {
        this.name = name;
        this.weight = 0;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
        this.weight = 0;
    }

    public Role(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public Role(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public Role setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Role setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public Role setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
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
        final Role other = (Role) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name=" + name + ", weight=" + weight + ", permissionList=" + permissionList + '}';
    }
    
    public Role addPermission(Permission permission) {
        this.permissionList.add(permission);
        return this;
    }
    
    public Role removePermission(Permission permission) {
        this.permissionList.remove(permission);
        return this;
    }

}
