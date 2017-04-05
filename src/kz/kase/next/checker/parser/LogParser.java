package kz.kase.next.checker.parser;


import kz.kase.next.checker.model.domain.QuoteHolder;

import java.nio.file.Path;
import java.util.List;

public interface LogParser {
    List<String> parseLogToBlocks(Path logfile);
    List<QuoteHolder> parseQuotes(String logMessage);
}
