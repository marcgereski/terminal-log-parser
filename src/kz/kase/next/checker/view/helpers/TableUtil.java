package kz.kase.next.checker.view.helpers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import kz.kase.next.checker.model.domain.QuoteHolder;

@SuppressWarnings("unchecked")
public class TableUtil {


    public static <K, V> void installColumn(TableColumn<K, V> column, final String name,
                                            final Class<? extends TableCell> cellType) {

        column.setCellValueFactory(new PropertyValueFactory(name));
        column.setCellFactory(column1 -> {
            try {
                return cellType.newInstance();

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /*   public static <K, V> void installCompoundColumn(TableColumn<K, V> column, final String name,
                                                       final Class<? extends TableCell> cellType) {

           column.setCellValueFactory(new PropertyValueFactory<K, V>(name));
           column.setCellFactory(new Callback<TableColumn<K, V>, TableCell<K, V>>() {
               @Override
               public TableCell<K, V> call(TableColumn<K, V> column) {
                   Constructor<? extends TableCell>[] constructs =
                           (Constructor<? extends TableCell>[]) cellType.getConstructors();
                   System.out.println(constructs.length);
                   return null;
               }
           });
       }
   */
    public static <K, V> void installAskQuotesColumn(TableColumn<K, V> column, String name,
                                                     final Class<? extends TableCell> cellType) {

        column.setCellValueFactory(new PropertyValueFactory<K, V>(name));

        column.setCellFactory(column1 -> {
            try {
                TableCell cell = cellType.newInstance();
                QuoteHolder quote = (QuoteHolder) cell.getTableRow().getItem();
                if (quote.getType() == QuoteHolder.Type.SELL) {
                    return cell;
                } else {
                    return null;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static <K, V> void installColumnBidQty(TableColumn<K, V> column, String name,
                                                  final Class<? extends TableCell> cellType) {

        column.setCellValueFactory(new PropertyValueFactory<K, V>(name));

        column.setCellFactory(new Callback<TableColumn<K, V>, TableCell<K, V>>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                TableCell cell = null;
                try {
                    cell = cellType.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (cell != null) {
                    cell.setId("bid-qty");
                }
                return cell;
            }
        });
    }

    public static <K, V> void installColumnAskPrice(TableColumn<K, V> column, String name,
                                                    final Class<? extends TableCell> cellType) {

        column.setCellValueFactory(new PropertyValueFactory<K, V>(name));

        column.setCellFactory(new Callback<TableColumn<K, V>, TableCell<K, V>>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                TableCell cell = null;
                try {
                    cell = cellType.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (cell != null) {
                    cell.setId("bid-qty");
                }

                return cell;
            }
        });
    }

}
