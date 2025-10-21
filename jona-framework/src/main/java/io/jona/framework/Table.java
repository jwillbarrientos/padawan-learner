package io.jona.framework;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class Table {
    @Getter
    private AtomicLong id = new AtomicLong(System.currentTimeMillis() + 1);
    protected abstract String getDelete(int id);
    protected abstract String getInsert();
    protected abstract Object[] getValues();
}
