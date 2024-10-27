
package com.github.frizzy.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * POJO representing the currency details in the json response from poe.ninja.
 *
 * @author Frizzy
 * @version 0.1
 * @since 0.1
 */
public class NinjaCurrencyDetails implements Comparable<NinjaCurrencyDetails> {

    @SerializedName("icon")
    private String iconLink = "Unknown";
    @SerializedName("id")
    private Long id = 0L;
    @SerializedName("name")
    private String currencyName = "Unknown";
    @SerializedName("tradeId")
    private String tradeId = "Unknown";

    @Override
    public String toString ( ) {
        String icLink = "Icon Link: " + iconLink;
        String id = "ID: " + this.id;
        String name = "Name: " + currencyName;
        String tID = "Trade ID: " + tradeId;
        String n = "\n";

        return icLink + n + id + n + name + n + tID ;
    }

    public String getIcon() {
        return iconLink;
    }

    public void setIcon(String icon) {
        iconLink = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return currencyName;
    }

    public void setName(String name) {
        currencyName = name;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Override
    public int compareTo ( NinjaCurrencyDetails o ) {
        if ( o.getName ().equalsIgnoreCase ( this.currencyName ) ) {
            return 0;
        } else {
            return -1;
        }
    }
}
