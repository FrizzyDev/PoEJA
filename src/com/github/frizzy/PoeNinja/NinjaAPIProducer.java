package com.github.frizzy.PoeNinja;

import com.github.frizzy.POJO.NinjaCurrency;
import com.github.frizzy.POJO.NinjaItem;
import com.github.frizzy.POJO.NinjaPOJO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * <p>
 * NinjaAPIProducer is a producer thread that should be used in a producer/consumer pattern.
 * It will retrieve all items of a specific category and offer them to a provided queue as the JSON
 * is converted to a POJO.
 * </p>
 * <p>
 * It supports all items and currencies, including different item types such as Unique Weapons, Blighted Maps,
 * etc. See {@link Category} for the item category.
 * </p>
 *
 * @author Frizzy
 * @version 0.1-Settlers
 * @since 0.1-Settlers
 */
public class NinjaAPIProducer extends Thread implements Runnable {

    /**
     * Logger for NinjaAPIProducer.
     */
    private static final Logger log = Logger.getLogger ( NinjaAPIProducer.class.getName () );


    /**
     * An enum of valid categories for the NinjaAPIProducer.
     */
    public enum Category { ITEMS, CURRENCIES, FRAGMENTS, MAPS }

    /**
     * The league the poe.ninja data will be related to.
     */
    private final String league;

    /**
     * The locale the poe.ninja data will be returned in.
     */
    private final String locale;

    /**
     * The queue that NinjaPOJO's will be offered to.
     */
    private final BlockingQueue<NinjaPOJO> queue;

    /**
     * The gson instance used to parse the incoming JSON.
     */
    private Gson gson;

    /**
     * <p>
     * An array of categories of the item types that will be searched for, based on what NinjaAPIProducer.Category
     * was passed.
     * </p>
     * <br>
     * <p>
     * Some item categories have multiple sub-categories. If Category Items was passed, the ninjaCategories will be the
     * sub-categories.
     * </p>
     */
    private String[] ninjaCategories;

    /**
     * <p>
     * The base URL for the producer.
     * </p>
     */
    private String urlBase;

    /**
     * Boolean flag determining if the producer should run or not.
     */
    private volatile boolean isRunning = false;

    /**
     * Constructs the NinjaAPIProducer to retrieve data of the provided category, related to the provided league,
     * and in the provided locale. The queue provided will receive POJO's as the Producer parses them.
     */
    public NinjaAPIProducer ( final String league , final String locale, final BlockingQueue<NinjaPOJO> queue, Category category) {
        this.league = league;
        this.locale = locale;
        this.queue = queue;
        this.gson = new GsonBuilder ( ).serializeNulls ( ).setPrettyPrinting ( ).create ( );

        determineNeededNinjaCategories ( category );
    }

    /**
     * Determines the content of the ninjaCategories array, so it is known what exactly is being retrieved.
     */
    private void determineNeededNinjaCategories ( Category category ) {

        switch ( category ) {
            case ITEMS -> {
                ninjaCategories = NinjaConstants.ITEM_CATEGORIES ;
                urlBase = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG;
            }
            case MAPS -> {
                ninjaCategories = NinjaConstants.MAP_CATEGORIES;
                urlBase = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG;
            }
            case FRAGMENTS -> {
                ninjaCategories = new String[1];
                ninjaCategories[0] = "Fragments";
                urlBase = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG;
            }
            case CURRENCIES -> {
                ninjaCategories[0] = "Currencies";
                urlBase = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG;
            }
        }
    }

    @Override
    public void run ( ) {
        while ( isRunning ) {
            for ( String nc : ninjaCategories ) {
                final String url = urlBase + nc + NinjaConstants.LANGUAGE_TAG + locale;

                try ( JsonReader reader = gson.newJsonReader ( new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) ) {
                    reader.beginObject ();
                    reader.nextName ();
                    reader.beginArray ();

                    while ( reader.hasNext () ) {
                        NinjaPOJO pojo;

                        if ( url.startsWith ( NinjaConstants.ITEMS_URL ) ) {
                            pojo = gson.fromJson ( reader, NinjaItem.class );
                        } else {
                            pojo = gson.fromJson ( reader, NinjaCurrency.class );
                        }

                        synchronized ( queue ) {
                            boolean taken = queue.offer ( pojo );

                            if ( !taken )
                                log.warning ( "POJO offered to queue was not taken." );
                        }
                    }

                } catch ( IOException | URISyntaxException e ) {
                    log.severe ( e.getMessage () );
                }
            }
        }

        stopProducer ();
    }

    /**
     * Starts the producer.
     */
    @Override
    public void start ( ) {
        isRunning = true;
        super.start ( );
    }

    /**
     * Stops the producer. This is internally called once all item categories are fully processed.
     */
    public void stopProducer ( ) {
        isRunning = false;
    }
}
