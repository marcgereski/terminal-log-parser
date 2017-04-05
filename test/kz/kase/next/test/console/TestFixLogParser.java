package kz.kase.next.test.console;


import kz.kase.next.checker.fix.FixParserTr;
import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.parser.FixLogParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quickfix.ConfigError;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestFixLogParser {
    List<QuoteHolder> quoteHolder = new ArrayList<>();

    @Before
    @Test
    public void fixGwParserReadFileTest() throws ConfigError {
        FixParserTr fixParser = new FixParserTr();
        FixLogParser parser1 = new FixLogParser(fixParser);
        Path file = Paths.get("data/md.txt");
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
