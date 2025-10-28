package io.jona.framework;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Table {
    private static final AtomicLong idGenerator = new AtomicLong(System.currentTimeMillis());
    protected long nextId() {
        return idGenerator.getAndIncrement();
    }
    protected abstract String getInsert();
    protected abstract String getUpdate();
    protected abstract String getDelete();
    protected abstract Object[] getValues();
}
