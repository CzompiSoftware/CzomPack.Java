package hu.czsoft.data.manager.collection;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stored collection instance
 * @param <T> Item type
 */
@Getter @Setter
public abstract class StoredCollection<T> implements CollectionManager<T> {
    protected static final Logger LOGGER = LogManager.getLogger(StoredCollection.class);
    private List<T> items = new ArrayList<>();

    /**
     * Represents the file fullName of the dataset stored by this manager.
     * @return Filename of stored dataset
     */
    abstract Path getFileName();

    /**
     * Represents the default dataset of this manager
     * @return Default dataset
     */
    abstract String getDefaultConfig();

    @Override
    public Collection<T> get() {
        return items;
    }

    /**
     * Load file from disk.
     * @throws IOException
     */
    public void load() throws IOException {
        var itemsJson = Files.readString(getFileName());
        setItems(List.of(new Gson().fromJson(itemsJson, getItemClass())));
    }

    /**
     * Save file to disk.
     * @throws IOException
     */
    public void save() throws IOException {
        if (!Files.exists(getFileName(), LinkOption.NOFOLLOW_LINKS)) {
            Files.writeString(getFileName(), getDefaultConfig(), StandardOpenOption.CREATE);
        } else if(!getItems().isEmpty()) {
            Files.writeString(getFileName(), new Gson().toJson(getItems()), StandardOpenOption.WRITE);
        }
    }

    /**
     * Save and load file.
     * @throws IOException
     */
    public void reload() throws IOException {
        save();
        load();
    }

    /**
     * Add item to items list and save it to disk.
     * @param item Item to be added
     */
    @Override
    public void add(T item) {
        CollectionManager.super.add(item);
        try {
            reload();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
