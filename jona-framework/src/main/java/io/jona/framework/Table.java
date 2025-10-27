package io.jona.framework;

import lombok.Getter;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Table {
    @Getter
    private final AtomicLong idGenerator = new AtomicLong(System.currentTimeMillis() + 1);
    protected abstract String getInsert();
    protected abstract String getUpdate();
    protected abstract String getDelete(long id);
    protected abstract Object[] getValues();
}
