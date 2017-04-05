package kz.kase.next.checker.model.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuoteHolder {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public enum Type {BUY, SELL}

    private final String symbol;
    private final Type type;
    private final double price;
    private final long qty;
    private final LocalDateTime receivedTime;

    public QuoteHolder(String symbol, Type type, Double price, Long qty, LocalDateTime receivedTime) {
        this.symbol = symbol;
        this.type = type;
        this.price = price;
        this.qty = qty;
        this.receivedTime = receivedTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public long getQty() {
        return qty;
    }

    public LocalDateTime getReceivedTime() {
        return receivedTime;
    }

    @Override
    public String toString() {
        return receivedTime.format(formatter) + "  " + type.name() + "  price: " + price + "  qty: " + qty;
    }


}
