package hu.czsoft.data.manager.singleton;

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

/**
 * Stored manager instance
 * @param <T> Item type
 */
@Getter
public abstract class StoredSingleton<T> implements SingletonManager<T> {
    private static final Logger LOGGER = LogManager.getLogger(StoredSingleton.class);
    private final Path fileName;
    @Setter private T item;

    public StoredSingleton() {
        this( null );
    }
    public StoredSingleton(String fileName) {
        this.fileName = Path.of(fileName);
    }


    /**
     * Represents the file fullName of the dataset stored by this manager.
     * @return Filename of stored dataset
     */
    protected abstract Path getFileName();

    /**
     * Represents the default dataset of this manager
     * @return Default dataset
     */
    protected abstract String getDefaultConfig();

    @Override
    public T get() {
        return getItem();
    }

    /**
     * Load file from disk.
     * @throws IOException
     */
    public void load() throws IOException {
        var itemsJson = Files.readString(getFileName());
        setItem(new Gson().fromJson(itemsJson, getItemClass()));
    }

    /**
     * Save file to disk.
     * @throws IOException
     */
    public void save() throws IOException {
        if (!Files.exists(getFileName(), LinkOption.NOFOLLOW_LINKS)) {
            Files.writeString(getFileName(), getDefaultConfig(), StandardOpenOption.CREATE);
        } else if(getItem() != null) {
            Files.writeString(getFileName(), new Gson().toJson(getItem()), StandardOpenOption.WRITE);
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
        SingletonManager.super.add(item);
        try {
            reload();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
