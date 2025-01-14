package hu.czsoft.data.manager.singleton;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base singleton instance
 * @param <T> Item type
 */
@Getter @Setter
public abstract class InMemorySingleton<T> implements SingletonManager<T> {
    private static final Logger LOGGER = LogManager.getLogger(InMemorySingleton.class);
    private T item;

    public T get() {
        return getItem();
    }
}
