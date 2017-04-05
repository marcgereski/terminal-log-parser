package kz.kase.next.checker.fix;


import kz.kase.fix.MDEntryType;
import kz.kase.fix.messages.MDIncRefresh;
import kz.kase.next.checker.model.domain.QuoteHolder;
import quickfix.Group;
import quickfix.field.StringField;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class FixParserTr implements FixParser{
    @Override
    public List<QuoteHolder> parse(MDIncRefresh md) {
        List<QuoteHolder> result = new ArrayList<>();

        String receivedTime = md.getHeader().getField(new StringField(FIELD_SENDING_TIME)).getValue();
        List<Group> groups = md.getGroups(FIELD_NO_MD_ENTRIES);

        for (Group g : groups) {
            if (!g.isSetField(FIELD_SYMBOL)) continue;

            MDEntryType t = MDEntryType.valueOf(g.getChar(FIELD_MD_ENTRY_TYPE));
            QuoteHolder.Type type = FixParseHelper.convertQuoteType(t);
            if (type == null) continue;

            String symbol = g.getString(FIELD_SYMBOL);
            long qty = (long) g.getDouble(FIELD_MD_ENTRY_SIZE);
            double price = g.getDouble(FIELD_MD_ENTRY_PRICE);
            LocalDateTime dt = FixParseHelper.parse(receivedTime);

            result.add(new QuoteHolder(symbol, type, price, qty, dt.plusHours(6)));
        }

        return result;
    }
}
