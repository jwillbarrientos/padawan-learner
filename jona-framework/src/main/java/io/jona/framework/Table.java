package io.jona.framework;

import lombok.Getter;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Table {
    @Getter
    private final AtomicLong id = new AtomicLong(System.currentTimeMillis() + 1);
    protected abstract String getDelete(int id);
    protected abstract String getInsert();
    protected abstract Object[] getValues();
}
