package kz.kase.next.checker;

import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.parser.LogParser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class MainConsole {

    private List<QuoteHolder> quoteHolder = new ArrayList<>();

    public MainConsole(LogParser logParser, Path file) {
            List<String> list = logParser.parseLogToBlocks(file);

            for (String data : list) {
                List<QuoteHolder> qoute = logParser.parseQuotes(data);
                quoteHolder.addAll(qoute);
            }
    }

    public List<QuoteHolder> getAllQuotes() {
        return quoteHolder;
    }


}
