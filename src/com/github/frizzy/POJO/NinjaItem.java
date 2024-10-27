package com.github.frizzy.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class NinjaItem implements NinjaPOJO, Comparable<NinjaItem> {

    @Override
    public String toString ( ) {
        String n = "\n";
        String id = "Item id: " + itemId.toString ();
        String name = "Item name: " + itemName;
        String type = "Item type: " + itemType;
        String base = "Item base: " + itemBaseType;
        String text = "Item flavour text: " + n + itemFlavourText;
        String link = "Icon link: " + itemIcon;
        String variant = "Item variant: " + itemVariant;
        String details = "Item details id: " + itemDetailsId;
        String chaosValue = "Chaos value: " + itemChaosValue;
        String exaltedValue = "Exalted value: " + itemExaltedValue;
        String divineValue = "Divine value: " + itemDivineValue;
        String lvlReq = "No level requirement";
        String stackSize = "Stack size not applicable";
        String mapTier = "Map tier not applicable";
        String imModsStr = "";
        String exModsStr = "";
        String separator = "-----------------------------------------------------------";

        if ( itemLevelRequirement != null ) {
            lvlReq = "Item level requirement: " + itemLevelRequirement;
        }

        if ( itemStackSize != null ) {
            stackSize = "Item stack size: " + itemStackSize;
        }

        if ( itemMapTier != null ) {
            mapTier = "Item Map tier: " + itemMapTier;
        }

        if ( imMods != null ) {
            StringBuilder builder = new StringBuilder (  );

            for ( NinjaModifier m : imMods ) {
                builder.append ( m.getModText ( ) ).append ( n );
            }

            imModsStr = builder.toString ();
        }

        if ( exMods != null ) {
            StringBuilder builder = new StringBuilder (  );

            for ( NinjaModifier m : exMods ) {
                builder.append ( m.getModText ( ) ).append ( n );
            }

            exModsStr = builder.toString ();
        }

        return id + n + name + n + type + n + base + n + text + n + link + n + variant + n + details + n + chaosValue + n + exaltedValue + n + divineValue + n + lvlReq + n + stackSize + n + mapTier + n + imModsStr + n + exModsStr  + separator;
    }

    @Override
    public int compareTo ( NinjaItem o ) {
        return itemName.compareTo ( o.getName () );
    }

    /**
     *
     */
    public void setCategory ( String category ) {
        this.category = category;
    }

    /**
     *
     */
    public String getCategory ( ) {
        return this.category;
    }

    /**
     *
     */
    public String getItemDetailsId ( ) {
        return itemDetailsId;
    }

    /**
     *
     */
    public String getItemIcon ( ) {
        return itemIcon;
    }

    /**
     *
     */
    public String getItemType ( ) {
        return itemType;
    }

    /**
     *
     */
    public String getItemBaseType ( ) {
        return itemBaseType;
    }

    /**
     *
     */
    public String getItemFlavourText ( ) {
        return itemFlavourText;
    }

    /**
     *
     */
    public Long getItemId ( ) {
        return itemId;
    }

    /**
     *
     */
    public Integer getItemLevelRequirement ( ) {
        return itemLevelRequirement;
    }

    /**
     *
     */
    public Integer getItemStackSize ( ) {
        return itemStackSize;
    }

    /**
     *
     */
    public Integer getItemMapTier ( ) {
        return itemMapTier;
    }

    /**
     *
     */
    public Double getItemChaosValue ( ) {
        return itemChaosValue;
    }

    /**
     *
     */
    public Double getItemExaltedValue ( ) {
        return itemExaltedValue;
    }

    /**
     *
     */
    public Double getItemDivineValue ( ) {
        return itemDivineValue;
    }

    /**
     *
     */
    public List<NinjaModifier> getImplicitModifiers ( ) {
        return imMods;
    }

    /**
     *
     */
    public List<NinjaModifier> getExplicitModifiers ( ) {
        return exMods;
    }

    /**
     *
     */
    public String getItemVariant ( ) {
        return itemVariant;
    }

    /**
     *
     */
    public List<String> getDivCardDrops ( ) {
        return divCardDrops;
    }

    /**
     *
     */
    public void setDivCardDrops ( List<String> divCardDrops ) {
        this.divCardDrops = divCardDrops;
    }

    /**
     *
     */
    @SerializedName ( "category" )
    private String category = "Unknown";

    @SerializedName ( "id" )
    private Long itemId = 0L;

    @SerializedName ( "name" )
    private String itemName = "Unknown";

    @SerializedName ( "icon" )
    private String itemIcon = "Unknown";

    @SerializedName ( "itemType" )
    private String itemType = "Unknown";

    @SerializedName ( "baseType" )
    private String itemBaseType = "Unknown";

    @SerializedName ( "flavourText" )
    private String itemFlavourText = "Unknown";

    @SerializedName ( "variant" )
    private String itemVariant = "Unknown";

    @SerializedName ( "detailsId" )
    private String itemDetailsId = "Unknown";

    @SerializedName ( "levelRequired" )
    private Integer itemLevelRequirement = 0;

    @SerializedName ( "stackSize" )
    private Integer itemStackSize = 0;

    @SerializedName ( "mapTier" )
    private Integer itemMapTier = 0;

    @SerializedName ( "chaosValue" )
    private Double itemChaosValue = 0.0;

    @SerializedName ( "exaltedValue" )
    private Double itemExaltedValue = 0.0;

    @SerializedName ( "divineValue" )
    private Double itemDivineValue = 0.0;

    @SerializedName ( "implicitModifiers" )
    private List<NinjaModifier> imMods = Collections.emptyList ( );

    @SerializedName ( "explicitModifiers" )
    private List<NinjaModifier> exMods = Collections.emptyList ( );

    private List<String> divCardDrops = Collections.emptyList ();

    @Override
    public String getName ( ) {
        return itemName;
    }

    @Override
    public void setName ( String name ) {
        this.itemName = name;
    }
}
