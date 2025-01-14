package hu.czsoft.data.manager.singleton;

public interface SingletonManager<T> {
    /**
     * Returns the item list stored in the manager
     * @return
     */
    T get();

    /**
     * Returns the item list stored in the manager
     * @return
     */
    T getItem();

    /**
     * Overrides the item list stored in the manager
     * @return
     */
    void setItem(T newItems);

    /**
     * Returns with item implementation class.
     * @return Class of {@code TImpl}
     * @param <TImpl> {@code T}'s implementation
     */
    <TImpl extends T> Class<TImpl> getItemClass();

    /**
     * Add item to items list.
     * @param item Item to be added
     */
    default void add(T item) {
        setItem(item);
    }
}
