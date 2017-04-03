package kz.kase.next.checker.fix;


import kz.kase.fix.MDEntryType;
import kz.kase.next.checker.model.domain.QuoteHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixParseHelper {
    public static LocalDateTime parse(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        return LocalDateTime.parse(date, formatter);
    }

    public static QuoteHolder.Type convertQuoteType(MDEntryType type) {
        if (type == MDEntryType.BID) {
            return QuoteHolder.Type.BUY;
        } else if (type == MDEntryType.OFFER) {
            return QuoteHolder.Type.SELL;
        }
        return null;
    }
}
