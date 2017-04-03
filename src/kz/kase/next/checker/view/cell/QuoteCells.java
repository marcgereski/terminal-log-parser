package kz.kase.next.checker.view.cell;

import javafx.scene.control.TableRow;
import kz.kase.next.checker.model.domain.QuoteHolder;
import kz.kase.next.checker.view.util.FormatUtil;

public class QuoteCells {

    public static final int DEF_PRECISION = 2;
    public static final String GREEN_ID = "green";
    public static final String RED_ID = "red";
    public static final String WHITE_ID = "white";

    public static int getViewPrecision(String instrSymbol) {
        return DEF_PRECISION;
    }

    public static class PriceCell<K> extends DefaultCell<K, Double> {

        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                TableRow row = getTableRow();
                QuoteHolder quote = (QuoteHolder) row.getItem();
                if (quote != null) {
                    setText(FormatUtil.format(item, DEF_PRECISION));
                }
            } else {
                setText("");
            }
        }
    }

    public static class PriceCellOld<K> extends DefaultCell<K, Double> {

        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                TableRow row = getTableRow();
                QuoteHolder quote = (QuoteHolder) row.getItem();

                if (quote != null) {
                    setText(FormatUtil.format(item, DEF_PRECISION));

                    if (quote.getType() == QuoteHolder.Type.BUY) {
                        setId(GREEN_ID);
                    } else {
                        setId(RED_ID);
                    }
                }
            } else {
                setText("");
                setId(WHITE_ID);
            }
        }
    }

    public static class QtyCell<K> extends DefaultCell<K, Long> {
        @Override
        protected void updateItem(Long item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(FormatUtil.format(item));
            } else {
                setText("");
            }
        }
    }

    public static class AskQuoteCell<K> extends DefaultCell<K, Long> {

        @Override
        protected void updateItem(Long item, boolean empty) {
            if (empty) {
                setText("");
                setId(WHITE_ID);
            } else {
                TableRow row = getTableRow();
                if (row != null) {
                    QuoteHolder qh = (QuoteHolder) row.getItem();
                    if (qh != null) {
                        if (qh.getType() == QuoteHolder.Type.BUY) {
                            setId(WHITE_ID);
                            setText("");
                        } else {
                            setText(FormatUtil.format(item));
                            setId(RED_ID);
                        }
                    }
                }
            }
        }
    }

    public static class BidQuoteCell<K> extends DefaultCell<K, Long> {

        @Override
        protected void updateItem(Long item, boolean empty) {

            if (empty) {
                setText("");
                setId(WHITE_ID);
            } else {
                TableRow row = getTableRow();
                if (row != null) {
                    QuoteHolder qh = (QuoteHolder) row.getItem();
                    if (qh != null) {
                        if (qh.getType() == QuoteHolder.Type.SELL) {
                            setId(RED_ID);
                            setText("");
                        } else {
                            setText(FormatUtil.format(item));
                            setId(GREEN_ID);
                        }
                    }
                }
            }
        }
    }

    public static class MyQuoteQtyCell<K> extends DefaultCell<K, Long> {

        @SuppressWarnings("unchecked")
        @Override
        protected void updateItem(Long item, boolean empty) {

            if (empty) {
                setText("");
                setId(WHITE_ID);
            } else {
                TableRow row = getTableRow();
                if (row != null) {
                    final QuoteHolder qh = (QuoteHolder) row.getItem();

                    if (qh == null) {
                        return; //todo проверить почему
                    }
/*                    if(qh.getType()==null){ //todo проверить
                    }*/

                    if (qh.getType() == QuoteHolder.Type.BUY) {
                        setId(GREEN_ID);
                    } else {
                        setId(RED_ID);
                    }
                }
            }
        }
    }
}
