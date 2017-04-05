package kz.kase.next.checker.parser;

import kz.kase.next.checker.model.domain.QuoteHolder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TerminalLogParser implements LogParser {
    private final DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<String> parseLogToBlocks(Path logfile) {
        List<String> lines = new ArrayList<>();
        List<String> tmpLines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logfile.toFile()));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                tmpLines.add(tmp);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        Iterator<String> iterator = tmpLines.iterator();

        String time = "";
        while (iterator.hasNext()) {
            String t = iterator.next();
            if (t.matches("^(\\d\\d:\\d\\d:\\d\\d,\\d\\d)(.*)")) {
                time = t.split(" ")[0];

                if (iterator.hasNext())
                    t = iterator.next();
            }

            if (t.contains("Quote :")) {
                String s = (iterator.next() + "\n" + iterator.next() + "\n"
                        + iterator.next() + "\n" + iterator.next() + "\n" + time)
                        .replaceAll("]", "")
                        .replaceAll(" ", "").replaceAll("\\[", "");
                lines.add(s);
            }
        }

        return lines;
    }

    @Override
    public List<QuoteHolder> parseQuotes(String logMessage) {
        List<QuoteHolder> list = new ArrayList<>();

        String[] s = logMessage.split("\n");
        if (s.length < 5) return new ArrayList<>();

        String symbol = s[0].split(":")[1];
        Double price = Double.parseDouble(s[1].split(":")[2]);
        Long qty = Long.parseLong(s[2].split(":")[2]);
        QuoteHolder.Type type = convert(s[3].split(":")[2]);

        String dateTime = LocalDate.now().format(formatDate);
        LocalDateTime dt = LocalDateTime.parse(dateTime + " " + s[4], formatDateTime);

        list.add(new QuoteHolder(symbol, type, price, qty, dt));

        return list;
    }

    private QuoteHolder.Type convert(String type) {
        return QuoteHolder.Type.valueOf(type);
    }
}
