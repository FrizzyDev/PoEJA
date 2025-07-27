package com.github.frizzy.PoeNinja;

import com.github.frizzy.POJO.NinjaCurrency;
import com.github.frizzy.POJO.NinjaItem;
import com.github.frizzy.POJO.NinjaPOJO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * @author Frizzy
 * @version 0.1-Settlers
 * @since 0.1-Settlers
 */
public class NinjaAPI {

    /**
     * <p>
     * Logger for the API.
     * </p>
     */
    private static Logger log = Logger.getLogger ( NinjaAPI.class.getName ( ) );

    /**
     * <p>
     * The league the API will be retrieving data for.
     * </p>
     */
    private final String league;

    /**
     * <p>
     * The locale the returned data will be in.
     * </p>
     */
    private final String locale;

    /**
     * <p>
     * The Gson instance used to parse the incoming JSON
     * from poe.ninja.
     * </p>
     */
    private Gson gson;

    /**
     * <p>
     * Creates a NinjaAPI instance with the provided league and locale.
     * </p>
     * <p>
     * The league and locale provided will determine what league that data
     * will be related to and the localisation that data is in.
     * </p>
     */
    public NinjaAPI ( final String league , final String locale ) {
        gson = new GsonBuilder ( ).serializeNulls ( ).create ( );
        this.league = league;
        this.locale = locale;
    }

    /**
     * <p>
     * Retrieves the item with the specified name, with data related to the provided league and in the
     * locale specified. The itemType must be the item category.
     * </p>
     * <p>
     * <font color="red"> If no item with the provided name is found, or the process throws an error, null will be returned. </font>
     * </p>
     * <br>
     * <p>
     * <font color="orange">Valid item categories: </font>
     * {@link NinjaConstants#ITEM_CATEGORIES}
     * </p>
     */
    public NinjaItem getItem ( String itemName , String itemType ) {
        String url = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG + itemType + NinjaConstants.LANGUAGE_TAG + locale;

        try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
            Optional < NinjaPOJO > opt = findInJson ( itemName , isr , NinjaItem.class );

            if ( opt.isPresent() ) {
                return ( NinjaItem ) opt.get();
            }

        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return null;
    }

    /**
     * <p>
     * Retrieves a list of all items that match the provided itemName.
     * </p>
     * <p>
     * <font color="red"> The returned list can be empty if no items matching the itemName were found. </font>
     * </p>
     */
    public List< NinjaItem > getItemsOf ( String itemName , String itemType ) {
        String url = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG + itemType + NinjaConstants.LANGUAGE_TAG + locale;

        List< NinjaItem > items = new ArrayList<> ( );

        try ( JsonReader reader = gson.newJsonReader ( new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) ) {
            reader.beginObject ( );
            reader.nextName ( );
            reader.beginArray ( );

            while ( reader.hasNext ( ) ) {
                NinjaItem i = gson.fromJson ( reader , NinjaItem.class );

                if ( i.getName ( ).equalsIgnoreCase ( itemName ) ) {
                    i.setCategory ( itemType );
                    items.add ( i );
                }
            }

        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return items;
    }

    /**
     * <p>
     * Returns the currency of the provided currencyName, if one is found.
     * </p>
     * <p>
     * <font color="red">If no currency is found, this will return null. </font>
     * </p>
     */
    public NinjaCurrency getCurrency ( String currencyName ) {
        final String url = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG + "Currency" + NinjaConstants.LANGUAGE_TAG + locale;

        try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
            Optional < NinjaPOJO > opt = findInJson ( currencyName , isr , NinjaCurrency.class );

            if ( opt.isPresent() ) {
                return ( NinjaCurrency ) opt.get();
            }

        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return null;
    }

    /**
     * <p>
     * Returns the fragment currency of the provided fragmentName, if one is found.
     * </p>
     * <p>
     * <font color="red">If no fragment is found, this will return null. </font>
     * </p>
     */
    public NinjaCurrency getFragmentCurrency ( String fragmentName ) {
        final String url = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG + "Fragment" + NinjaConstants.LANGUAGE_TAG + locale;

        try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
            Optional < NinjaPOJO > opt = findInJson ( fragmentName , isr , NinjaCurrency.class );

            if ( opt.isPresent() ) {
                return ( NinjaCurrency ) opt.get();
            }

        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return null;
    }

    /**
     * <p>
     * Returns a Collection of all currencies from poe.ninja. If removeDuplicates is <font color="aqua"> TRUE </font>, the returned
     * Collection will be of type TreeSet<> and will not allow currencies of matching names. If removeDuplicates
     * is <font color="red"> FALSE </font>, the underlying collection will be an ArrayList.
     * </p>
     * <p>
     * <font color="red"> If this process fails, an empty immutable list will be returned. </font>
     * </p>
     */
    @SuppressWarnings( "unchecked" )
    public Collection< NinjaCurrency > getAllCurrency ( boolean removeDuplicates ) {
        final String url = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG + "Currency" + NinjaConstants.LANGUAGE_TAG + locale;
        Collection< NinjaCurrency > allCurr = removeDuplicates ? new TreeSet<> ( Comparator.comparing ( NinjaCurrency::getName ) ) : new ArrayList<> ( );

        try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
            allCurr.addAll ( ( Collection< NinjaCurrency > ) findAllInJson ( isr , "NinjaCurrency" ) );
        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return Collections.emptyList ( );
    }

    /**
     * <p>
     * Returns a collection of all fragment currencies from poe.ninja. If removeDuplicates is <font color="aqua">TRUE </font>, the returned
     * Collection will be of type TreeSet<> and will not allow fragments of matching names. If removeDuplicates is
     * <font color="red">FALSE </font>, the underlying collection returned will be an ArrayList.
     * </p>
     * <p>
     * <font color="red"> If this process fails, an empty immutable list will be returned. </font>
     * </p>
     */
    @SuppressWarnings( "unchecked" )
    public Collection< NinjaCurrency > getAllFragments ( boolean removeDuplicates ) {
        final String url = NinjaConstants.CURRENCY_URL + league + NinjaConstants.TYPE_TAG + "Fragment" + NinjaConstants.LANGUAGE_TAG + locale;
        Collection< NinjaCurrency > allFrags = removeDuplicates ? new TreeSet<> ( Comparator.comparing ( NinjaCurrency::getName ) ) : new ArrayList<> ( );

        try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
            allFrags.addAll ( ( Collection< NinjaCurrency > ) findAllInJson ( isr , "NinjaCurrency" ) );
        } catch ( IOException | URISyntaxException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return Collections.emptyList ( );
    }

    /**
     * <p>
     * Returns a Map of all items, where the key is the item category and the Collection are the items of
     * that category. The Collection of items for each category will be a TreeSet if removeDuplicates is
     * true, or an ArrayList if false.
     * </p>
     * <p>
     * <font color="green">It should be noted that retrieving all the items from poe.ninja is a time consuming process, and it is
     * recommended to call this method from a worker thread. </font>
     * </p>
     */
    @SuppressWarnings( "unchecked" )
    public Map< String, Collection< NinjaItem > > getAllItems ( boolean removeDuplicates ) {
        Map< String, Collection< NinjaItem > > itemsMap = new HashMap<> ( );

        for ( String category : NinjaConstants.ITEM_CATEGORIES ) {
            final String url = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG + category + NinjaConstants.LANGUAGE_TAG + locale;
            Collection< NinjaItem > items = removeDuplicates ? new TreeSet<> ( Comparator.comparing ( NinjaItem::getName ) ) : new ArrayList<> ( );

            try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {
                Collection< NinjaItem > col = ( Collection< NinjaItem > ) findAllInJson ( isr , "NinjaItem" );

                for ( NinjaItem ni : col ) {
                    ni.setCategory ( category );
                    items.add ( ni );
                }

                itemsMap.put ( category , items );

            } catch ( IOException | URISyntaxException e ) {
                log.severe ( e.getMessage ( ) );
            }
        }

        return itemsMap;
    }

    /**
     * <p>
     * Returns a map of all Path of Exile maps, where the keys are the map category, and the Collection is the maps of that category.
     * </p>
     * <p>
     * If removeDuplicates is <font color="aqua"> TRUE </font>, the Collection of maps will be a TreeSet to prevent duplicate map names.
     * </p>
     */
    @SuppressWarnings( "unchecked" )
    public Map< String, Collection< NinjaItem > > getAllMaps ( boolean removeDuplicates ) {
        String[] categories = { "Map" , "UniqueMap" , "ScourgedMap" , "BlightedMap" , "BlightRavagedMap" };
        Map< String, Collection< NinjaItem > > allMaps = new HashMap<> ( );

        for ( String category : categories ) {
            final String url = NinjaConstants.ITEMS_URL + league + NinjaConstants.TYPE_TAG + category + NinjaConstants.LANGUAGE_TAG + locale;
            Collection< NinjaItem > mapsCollection = removeDuplicates ? new TreeSet<> ( Comparator.comparing ( NinjaItem::getName ) ) : new ArrayList<> ( );

            try ( InputStreamReader isr = new InputStreamReader ( new URI ( url ).toURL ( ).openStream ( ) ) ) {

                mapsCollection.addAll ( ( Collection< NinjaItem > ) findAllInJson ( isr , "NinjaItem" ) );

            } catch ( IOException | URISyntaxException e ) {
                log.severe ( e.getMessage ( ) );
            }

            allMaps.put ( category , mapsCollection );
        }

        return allMaps;
    }

    /**
     * <p>
     * Gets all the items of a specified category via a producer Thread. The passed queue will receive NinjaItems or NinjaCurrencies as the
     * internal Producer receives them.
     * </p>
     * <p>
     * The benefit of using this method is that it is threaded and allows a developer to process items as they come in.
     * </p>
     * <p>
     * Once all items have been retrieved of the desired category, the Producer will stop automatically.
     * </p>
     * <br>
     * <p>
     * <font color="yellow"> It should be noted that this method does not remove duplicates and retrieves all items of a category. </font>
     * <font color="aqua">It is the responsibility of the consumer to allow or remove duplicates. </font>
     * </p>
     * @param category The Category of item or currency that is being obtained.
     * @param queue The queue that receives the parsed POJO's. This queue should also be attached to a consumer.
     */
    public void getAllOf ( NinjaAPIProducer.Category category, BlockingQueue< NinjaPOJO > queue ) {
        final NinjaAPIProducer producer = new NinjaAPIProducer ( league, locale, queue, category );
        producer.start ();
    }

    /**
     * <p>
     * Reads all of the items or currencies in the JSON from the provided InputStreamReader.
     * The returned Collection will contain either NinjaItem or NinjaCurrency, depending on the type
     * provided.
     * </p>
     * <p>
     * <font color="red">If for some reason this method fails, an empty Collection will be returned. </font>
     * </p>
     */
    @SuppressWarnings( "all" )
    private Collection< ? > findAllInJson ( InputStreamReader isr , String type ) {
        try ( JsonReader reader = gson.newJsonReader ( isr ) ) {
            reader.beginObject ( );
            reader.nextName ( );

            switch ( type ) {
                case "NinjaCurrency" -> {
                    return gson.fromJson ( reader , new TypeToken< Collection< NinjaCurrency > > ( ) {
                    }.getType ( ) );
                }
                case "NinjaItem" -> {
                    return gson.fromJson ( reader , new TypeToken< Collection< NinjaItem > > ( ) {
                    }.getType ( ) );
                }
            }

        } catch ( IOException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return Collections.emptyList ( );
    }

    /**
     * <p>
     * Read json method for the various get functions.
     * </p>
     * <p>
     *<font color="red"> If for some reason this method fails, null will be returned. </font>
     * </p>
     *
     * @param name The name of the item that is looked for.
     * @param isr  The InputStreamReader of the poe.ninja URL the JsonReader uses.
     * @param type The class Type. NinjaCurrency or NinjaItem will be passed.
     */
    private Optional < NinjaPOJO > findInJson ( String name , InputStreamReader isr , Type type ) {

        try ( JsonReader reader = gson.newJsonReader ( isr ) ) {
            reader.beginObject ( );
            reader.nextName ( );
            reader.beginArray ( );

            while ( reader.hasNext ( ) ) {
                NinjaPOJO np = gson.fromJson ( reader , type );

                if ( np.getName ( ).equalsIgnoreCase ( name ) ) {
                    return Optional.of( np );
                }
            }

        } catch ( IOException e ) {
            log.severe ( e.getMessage ( ) );
        }

        return Optional.empty();
    }
}

