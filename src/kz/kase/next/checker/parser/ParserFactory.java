package kz.kase.next.checker.parser;


import kz.kase.next.checker.fix.FixParserTr;
import quickfix.ConfigError;

public class ParserFactory {
    public static LogParser getParser(String value) {
        LogParser logParser = null;

        switch (value) {
            case "Fix":
                try {
                    logParser = new FixLogParser(new FixParserTr());
                } catch (ConfigError configError) {
                    configError.printStackTrace();
                }
                break;
            case "Terminal":
                logParser = new TerminalLogParser();
                break;
        }
        return logParser;
    }
}
