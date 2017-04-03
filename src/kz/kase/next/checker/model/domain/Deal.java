package kz.kase.next.checker.model.domain;


import kz.kase.next.checker.model.Side;

public class Deal {
    private long ref;
    private String serial;
    private String buyOrderSerial;
    private long instrId;
    private String accName;
    private String sellOrderSerial;
    private String sellUser;
    private String buyUser;
    private Side side;
    private double price;
    private long qty;
    private long leavesQty;

    public Deal(long ref, String serial, String buyOrderSerial, long instrId, String accName, String sellOrderSerial,
                String sellUser, String buyUser, Side side, double price, long qty, long leavesQty) {
        this.ref = ref;
        this.serial = serial;
        this.buyOrderSerial = buyOrderSerial;
        this.instrId = instrId;
        this.accName = accName;
        this.sellOrderSerial = sellOrderSerial;
        this.sellUser = sellUser;
        this.buyUser = buyUser;
        this.side = side;
        this.price = price;
        this.qty = qty;
        this.leavesQty = leavesQty;
    }
}
