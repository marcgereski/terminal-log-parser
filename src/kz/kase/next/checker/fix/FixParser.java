package kz.kase.next.checker.fix;


import kz.kase.fix.messages.MDIncRefresh;
import kz.kase.next.checker.model.domain.QuoteHolder;

import java.util.List;

public interface FixParser {
    List<QuoteHolder> parse(MDIncRefresh md);
}
