package kz.kase.next.checker.model.domain;


import kz.kase.next.checker.model.OrderStatus;
import kz.kase.next.checker.model.Side;

import java.util.Date;

public class Order {

    private String symbol;
    private String serial;
    private OrderStatus status;
    private long accId;
    private Side side;
    private double price;
    private long qty;
    private long leavesQty;
    Date creationTime;

    public Order(String symbol, String serial, OrderStatus status, long accId, Side side, double price,
                 long qty, long leavesQty, Date creationTime) {
        this.symbol = symbol;
        this.serial = serial;
        this.status = status;
        this.accId = accId;
        this.side = side;
        this.price = price;
        this.qty = qty;
        this.leavesQty = leavesQty;
        this.creationTime = creationTime;
    }
}
