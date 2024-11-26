/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal.codebase;

import entity.codebaseOld.Bill;
import entity.codebaseOld.Order;
import java.util.List;

/**
 *
 * @author HP
 */
public class Checkout {
    private OrderDAO orderDAO;

    public Checkout(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public boolean checkout(List<Order> orders, Bill bill) {
        int billId = orderDAO.createBill(bill);
        if (billId == -1) {
            return false;
        }
        for (Order order : orders) {
            order.setBillId(billId);
            if (!orderDAO.createOrder(order)) {
                return false;
            }
        }
        return true;
    }
}
