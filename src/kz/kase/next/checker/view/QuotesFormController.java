package kz.kase.next.checker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.model.eventbus.EventBus;
import kz.kase.next.checker.view.cell.QuoteCells;
import kz.kase.next.checker.view.comparator.QuoteComparators;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static kz.kase.next.checker.view.helpers.TableUtil.installColumn;

public class QuotesFormController implements Initializable, EventBus.Listener {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final double DELTA = 0.0000001;
    private QuoteModel quoteModel;
    public static final Comparator<QuoteHolder> BID_COMPARATOR = new QuoteComparators.BidComparator();
    private List<QuoteHolder> quoteList = new ArrayList<>();
    private Iterator<QuoteHolder> iterator;

    @FXML
    private TableView<QuoteHolder> oldStyleQuotesTable;
    @FXML
    private VBox quotesPanel;
    @FXML
    private TableColumn<QuoteHolder, Long> bidColumn;
    @FXML
    private TableColumn<QuoteHolder, Long> askColumn;
    @FXML
    private TableColumn<QuoteHolder, Double> priceColumn;
    @FXML
    private TableColumn<QuoteHolder, Long> my;
    @FXML
    private Label labelDateTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getInstance().addListener(this);
        initOldStyleQuotesTable();
        updateQuotesStyle();
        setQuoteModel();
    }

    private void initOldStyleQuotesTable() {
        oldStyleQuotesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        installColumn(priceColumn, "price", QuoteCells.PriceCellOld.class);
        installColumn(askColumn, "qty", QuoteCells.AskQuoteCell.class);
        installColumn(bidColumn, "qty", QuoteCells.BidQuoteCell.class);
        installColumn(my, "qty", QuoteCells.MyQuoteQtyCell.class);
    }

    private void updateQuotesStyle() {
        if (!quotesPanel.getChildren().contains(oldStyleQuotesTable)) {
            quotesPanel.getChildren().add(oldStyleQuotesTable);
        }
    }

    private void setQuoteModel() {
        quoteModel = new QuoteModel(BID_COMPARATOR, 0);
        oldStyleQuotesTable.setItems(quoteModel.getData());
    }

    private void setQuote(QuoteHolder quote) {
        labelDateTime.setText(quote.getReceivedTime().plusHours(6).format(formatter));
        quoteModel.addQuote(quote);
        quoteModel.update();
    }

    @Override
    public void onEvent(String eventName, Object data) {
        quoteList = (List<QuoteHolder>) data;
        iterator = quoteList.iterator();
    }

    private static class QuoteModel {
        public static final int DIGITS_AFTER_POINT = 2;

        private ObservableList<QuoteHolder> bidQuotes = FXCollections.observableArrayList();
        private ObservableList<QuoteHolder> askQuotes = FXCollections.observableArrayList();

        private ObservableList<QuoteHolder> filteredData =
                FXCollections.observableArrayList();

        private final Comparator<QuoteHolder> comparator;
        private boolean updated = false;

        private int depth;

        private QuoteModel(Comparator<QuoteHolder> comparator, int depth) {
            this.comparator = comparator;
            this.depth = depth;

        }

        public void addQuote(QuoteHolder q) {
            if (q.getType() == QuoteHolder.Type.BUY) {
                Optional<QuoteHolder> bid = bidQuotes.stream().filter(b -> b.getPrice() == q.getPrice()).findFirst();
                bid.ifPresent(quoteHolder -> bidQuotes.remove(quoteHolder));
                if (q.getQty() > 0) bidQuotes.add(q);
            } else {
                Optional<QuoteHolder> ask = askQuotes.stream().filter(b -> b.getPrice() == q.getPrice()).findFirst();
                ask.ifPresent(quoteHolder -> askQuotes.remove(quoteHolder));
                if (q.getQty() > 0) askQuotes.add(q);
            }
            updated = true;
        }

        public void removeQuote(double price) {
            QuoteHolder q = getQuote(price);

            if (q != null) {
                if (bidQuotes.contains(q)) {
                    bidQuotes.remove(q);
                } else if (askQuotes.contains(q)) {
                    askQuotes.remove(q);
                }

            }
        }


        private QuoteHolder getQuote(double price) {
            for (QuoteHolder q : bidQuotes) {
                if (Math.abs(q.getPrice() - price) < DELTA)
                    return q;
            }
            for (QuoteHolder q : askQuotes) {
                if (Math.abs(q.getPrice() - price) < DELTA)
                    return q;
            }

            return null;
        }

        public List<QuoteHolder> getAllQuotes() {
            List<QuoteHolder> allQuotes = new ArrayList<>();
            allQuotes.addAll(askQuotes);
            allQuotes.addAll(bidQuotes);
            return allQuotes;
        }

        public boolean update() {
            if (updated) {
                removeSamePriceQuote();
                askQuotes.sort(comparator);
                bidQuotes.sort(comparator);
                filteredData.clear();

                if (depth > 0) {
                    updateWithDepth();
                } else {
                    updateWithoutDepth();
                }
                filteredData.sort(comparator);
                updated = false;
                return true;
            }
            return false;
        }

        private void updateWithDepth() {
            if (!askQuotes.isEmpty()) {
                if (askQuotes.size() > depth) {
                    int k = 0;
                    for (int i = askQuotes.size() - 1; k < depth; i--) {
                        filteredData.add(askQuotes.get(i));
                        k++;
                    }
                } else {
                    filteredData.addAll(askQuotes);
                }
            }
            if (!bidQuotes.isEmpty()) {
                if (bidQuotes.size() > depth) {
                    for (int i = 0; i < depth; i++) {
                        filteredData.add(bidQuotes.get(i));
                    }
                } else {
                    filteredData.addAll(bidQuotes);
                }
            }

        }

        private void updateWithoutDepth() {
            if (!askQuotes.isEmpty()) {
                filteredData.addAll(askQuotes);
            }
            if (!bidQuotes.isEmpty()) {
                filteredData.addAll(bidQuotes);
            }
        }


        private void removeSamePriceQuote() {
            QuoteHolder tempQt = null;
            List<QuoteHolder> bidDelList = new ArrayList<>();
            List<QuoteHolder> askDelList = new ArrayList<>();
            for (QuoteHolder q : bidQuotes) {
                if (tempQt != null) {
                    if (q.getPrice() == tempQt.getPrice()) {
                        bidDelList.add(q);
                    }
                }
                tempQt = q;
            }

            for (QuoteHolder q : bidDelList) {
                bidQuotes.removeAll(q);
            }
            for (QuoteHolder q : askQuotes) {
                if (tempQt != null) {
                    if (q.getPrice() == tempQt.getPrice()) {
                        askDelList.add(q);
                    }
                }
                tempQt = q;
            }

            for (QuoteHolder q : askDelList) {
                askQuotes.removeAll(q);
            }
        }


        public ObservableList<QuoteHolder> getData() {
            return filteredData;
        }

        public int getPriceMaxDigits() {
            int maxDigits = 0;
            for (QuoteHolder q : filteredData) {
                String strPrc = Double.toString(q.getPrice());
                int idx = strPrc.indexOf(".");
                int digits = idx >= 0 ? idx : strPrc.length();
                digits += DIGITS_AFTER_POINT + 1;

                if (digits > maxDigits) {
                    maxDigits = digits;
                }
            }
            return maxDigits;
        }

        public int getQtyMaxDigits() {
            int maxDigits = 0;
            for (QuoteHolder q : filteredData) {
                int digits = Long.toString(q.getQty()).length();
                if (digits > maxDigits) {
                    maxDigits = digits;
                }
            }
            return maxDigits;
        }

        public void clearAll() {
            filteredData.clear();
            bidQuotes.clear();
            askQuotes.clear();
        }

    }

    @FXML
    public void nextQuote() {
        if (iterator.hasNext())
            setQuote(iterator.next());
    }
}
