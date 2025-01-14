package hu.czsoft.data.manager.collection;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * In-memory collection instance
 * @param <T> Item type
 */
@Getter @Setter
public abstract class InMemoryCollection<T> implements CollectionManager<T> {
    protected static final Logger LOGGER = LogManager.getLogger(InMemoryCollection.class);
    private List<T> items = new ArrayList<>();

    @Override
    public Collection<T> get() {
        return items;
    }

    /**
     * @param items Items to add to the collection
     * @return <code>true</code> if successful
     */
    public boolean addRange(Collection<T> items) {
        return getItems().addAll(items);
    }

    public void clear() {
        getItems().clear();
    }
}
