package kz.kase.next.checker.parser;


import kz.kase.next.checker.fix.FixParserTr;
import quickfix.ConfigError;

public class ParserFactory {
    public static enum ParserType {
        Fix, MD, Term1, Term2, Core
    }

    public static LogParser getParser(ParserType value) {
        LogParser logParser = null;

        switch (value) {
            case Fix:
                try {
                    logParser = new FixLogParser(new FixParserTr());
                } catch (ConfigError configError) {
                    configError.printStackTrace();
                }
                break;
            case Term1:
                logParser = new TerminalLogParser();
                break;
            default:
                logParser = new TerminalLogParser();
        }
        return logParser;
    }
}
