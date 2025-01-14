package hu.czsoft.data.manager.collection;

import java.util.Collection;
import java.util.List;

/**
 * Base collection instance
 * @param <T> Item type
 */
public interface CollectionManager<T> {
    /**
     * Returns the item list stored in the manager
     * @return A collection with items in it
     */
    Collection<T> getItems();

    Collection<T> get();

    /**
     * Overrides the item collection stored in the manager
     */
    void setItems(List<T> newItems);

    /**
     * Returns with item implementation class.
     * @return Class of {@code TImpl}
     * @param <TImpl> {@code T}'s implementation
     */
    <TImpl extends T> Class<TImpl[]> getItemClass();

    /**
     * Add item to items list.
     * @param item Item to be added
     */
    default void add(T item) {
        var items = new java.util.ArrayList<>(getItems());
        items.add(item);
        setItems(items);
    }

    void clear();
}
