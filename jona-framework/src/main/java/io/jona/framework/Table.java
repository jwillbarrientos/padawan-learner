package io.jona.framework;

public abstract class Table {
    protected abstract String getDelete();
    protected abstract String getInsert();
    protected abstract Object[] getValues();
}
