package kz.kase.next.checker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kz.kase.next.checker.model.InstrSymbols;
import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.model.eventbus.EventBus;
import kz.kase.next.checker.model.eventbus.EventMessage;
import kz.kase.next.checker.parser.LogParser;
import kz.kase.next.checker.parser.ParserFactory;
import quickfix.ConfigError;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainGui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String symbol = InstrSymbols.USDKZT_TOD;

        ResourceBundle bundle = ResourceBundle.getBundle("message", new Locale("en"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/quotes.fxml"), bundle);
        Parent root = loader.load();

        primaryStage.setTitle(symbol);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        LogParser logParser = ParserFactory.getParser("FixGw");
        Path file = Paths.get("data/md.txt");
        loadData(logParser, file, symbol);
    }

    public void loadData(LogParser logParser, Path file, String symbol) throws ConfigError {
        MainConsole console = new MainConsole(logParser, file);

        List<QuoteHolder> allQuotes = console.getAllQuotes();
        List<QuoteHolder> quotes = allQuotes.stream().filter(q -> q.getSymbol().equals(symbol))
                .collect(Collectors.toList());
        EventBus.getInstance().fireOnEvent(EventMessage.QUOTES_LIST, quotes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
