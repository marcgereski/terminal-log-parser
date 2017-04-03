package kz.kase.next.checker.view.cell;

import javafx.scene.control.TableCell;


public abstract class DefaultCell<K, V> extends TableCell<K, V> {

    @Override
    protected void updateItem(V v, boolean b) {
        super.updateItem(v, b);
    }

    protected String getString(String key) {
        return key;
    }

}
