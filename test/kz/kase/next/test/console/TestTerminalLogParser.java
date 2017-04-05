package kz.kase.next.test.console;


import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.parser.TerminalLogParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quickfix.ConfigError;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestTerminalLogParser {
    List<QuoteHolder> quoteHolder = new ArrayList<>();

    @Before
    @Test
    public void fixGwParserReadFileTest() throws ConfigError {
        TerminalLogParser parser1 = new TerminalLogParser();
        Path file = Paths.get("data/next.log.2017-03-29");
        List<String> list = parser1.parseLogToBlocks(file);

        for (String data : list) {
            List<QuoteHolder> qoute = parser1.parseQuotes(data);
            quoteHolder.addAll(qoute);
            qoute.forEach(value -> Assert.assertNotNull(value.getSymbol()));
        }
    }

    @Test
    public void quoteHolderTest() {
        quoteHolder.forEach(q->{
            System.out.println(q.getReceivedTime() + " " + q.getSymbol()
                    + " " + q.getType() + " " + q.getPrice() + " " + q.getQty());
        });
    }
}
