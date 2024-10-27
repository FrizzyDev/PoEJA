package com.github.frizzy.PoeNinja;

/**
 * A class of constants used by the NinjaAPI class.
 *
 * @author Frizzy
 * @version 0.1-Settlers
 * @since 0.1-Settlers
 */
public final class NinjaConstants {

    /**
     * English locale.
     */
    public static final String EN_LOCALE = "en";

    /**
     * Brazilian locale.
     */
    public static final String PT_LOCALE = "pt";

    /**
     * Russian locale.
     */
    public static final String RU_LOCALE = "ru";

    /**
     * Thai locale.
     */
    public static final String TH_LOCALE = "th";

    /**
     * German locale.
     */
    public static final String GE_LOCALE = "ge";

    /**
     * French locale.
     */
    public static final String FR_LOCALE = "fr";

    /**
     * Spanish locale.
     */
    public static final String ES_LOCALE = "es";

    /**
     * Korean locale.
     */
    public static final String KO_LOCALE = "ko";

    /**
     * The URL to pull currency data from poe.ninja.
     */
    public static final String CURRENCY_URL = "https://poe.ninja/api/data/currencyoverview?league=";

    /**
     * The URL to pull item data from poe.ninja.
     */
    public static final String ITEMS_URL = "https://poe.ninja/api/data/itemoverview?league=";

    /**
     * Language tag used when building a poe.ninja link.
     */
    public static final String LANGUAGE_TAG = "&language=";

    /**
     * Type tag used when building a poe.ninja link.
     */
    public static final String TYPE_TAG = "&type=";

    /**
     * Used for various pricing functions.
     */
    public static final String DIVINE_ORB = "Divine Orb";

    /**
     * <p>
     * The item categories array used by NinjaAPI.getAllItems
     * </p>
     * <p>
     * <font color="yellow"> TODO: Determine the best way of having these values here locally but checked for validity externally. </font>
     * </p>
     */
    public static final String[] ITEM_CATEGORIES = { "Artifact" , "BaseType" , "Beast" , "ClusterJewel" , "DeliriumOrb" , "DivinationCard" ,
                                                     "Essence" , "Fossil" , "HelmetEnchant" , "Incubator" , "Invitation" , "KalguuranRune" , "Oil" , "Omen" , "Resonator" ,
                                                     "Scarab" , "SkillGem" , "Tattoo" , "UniqueAccessory" , "UniqueArmour" , "UniqueFlask" , "UniqueJewel" , "UniqueRelic" ,
                                                     "UniqueWeapon" , "Vial" };

    /**
     * <p>
     * The map categories used when retrieving all maps from poe.ninja.
     * </p>
     * <p>
     * <font color="yellow"> TODO: Determine a way to make legacy categories optional, like ScourgedMap.
     * </p>
     */
    public static final String[] MAP_CATEGORIES = { "Map" , "UniqueMap" , "ScourgedMap" , "BlightedMap" , "BlightRavagedMap" };

    /**
     * Constants class. Does not need initialization.
     */
    private NinjaConstants ( ) {

    }
}
