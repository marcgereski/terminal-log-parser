package kz.kase.next.checker.parser;

import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.messages.MDIncRefresh;
import kz.kase.next.checker.fix.FixParser;
import kz.kase.next.checker.model.domain.QuoteHolder;
import quickfix.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FixGwLogParser implements LogParser {
    public static final String FIX50 = "FIX50_ext.xml";
    public static final String USER_DIR = "lib/quickfix";
    public static final String DICT_HOME = USER_DIR + "/etc/";
    private final DataDictionary dictionary;
    private final MessageFactory factory;
    private final FixParser fixParser;

    public FixGwLogParser(FixParser fixParser) throws ConfigError {
        dictionary = new DataDictionary(DICT_HOME + "/" + FIX50);
        factory = new KaseFixMessageFactory();
        this.fixParser = fixParser;
    }

    @Override
    public List<String> parseLogToBlocks(Path logfile) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logfile.toFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return lines;
        }
        return lines;
    }

    @Override
    public List<QuoteHolder> parseQuotes(String fixMessage) {
        MDIncRefresh data;
        try {
            data = (MDIncRefresh) MessageUtils.parse(factory, dictionary, fixMessage);
        } catch (InvalidMessage invalidMessage) {
            invalidMessage.printStackTrace();
            return null;
        }
        return fixParser.parse(data);
    }
}
