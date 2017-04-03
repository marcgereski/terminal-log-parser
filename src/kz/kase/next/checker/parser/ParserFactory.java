package kz.kase.next.checker.parser;


import kz.kase.next.checker.fix.FixParserTr;
import quickfix.ConfigError;

public class ParserFactory {
    public static LogParser getParser(String value) {
        LogParser logParser = null;

        switch (value) {
            case "FixGw":
                try {
                    logParser = new FixGwLogParser(new FixParserTr());
                } catch (ConfigError configError) {
                    configError.printStackTrace();
                }
                break;
        }
        return logParser;
    }
}
