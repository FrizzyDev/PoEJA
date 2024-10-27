
package com.github.frizzy.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * POJO for currency and fragment json responses from poe.ninja
 *
 * @author Frizzy
 * @version 0.1
 * @since 0.1
 */
public class NinjaCurrency implements NinjaPOJO, Comparable<NinjaCurrency> {

    /**
     *
     */
    @SerializedName("chaosEquivalent")
    private Double chaosEquivalent = 0.0;

    /**
     *
     */
    @SerializedName("currencyTypeName")
    private String currencyName = "Unknown";

    /**
     *
     */
    @SerializedName("detailsId")
    private String detailsId = "Unknown";

    /**
     *
     */
    @SerializedName( "cDetails" )
    private NinjaCurrencyDetails cDetails;

    /**
     *
     */
    public Double getChaosEquivalent() {
        return chaosEquivalent;
    }

    /**
     *
     */
    public void setChaosEquivalent(Double chaosEquivalent) {
        this.chaosEquivalent = chaosEquivalent;
    }

    /**
     *
     */
    public void setCurrencyName ( String currencyTypeName) {
        currencyName = currencyTypeName;
    }

    /**
     *
     */
    public String getDetailsId() {
        return detailsId;
    }

    /**
     *
     */
    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    /**
     *
     */
    public NinjaCurrencyDetails getCurrencyDetails () {
        return cDetails;
    }

    /**
     *
     */
    public void setCurrencyDetails ( NinjaCurrencyDetails details ) {
        this.cDetails = details;
    }

    /**
     *
     */
    @Override
    public String toString ( ) {
        String cName = "Currency Name: " + currencyName;
        String cEquiv = "Chaos Equivalent: " + chaosEquivalent;
        String details = "Details ID: " + detailsId;
        String separator = "----------------------";
        String n = "\n";
        String c = "Details below: ";
        String cd = "";

        if ( cDetails != null ) {
            cd = cDetails.toString ();
        }

        return cName + n + cEquiv + n + details + n + c + n + cd + n + separator;
    }

    /**
     *
     */
    @Override
    public int compareTo ( NinjaCurrency o ) {
        if ( o.getName ().equalsIgnoreCase ( this.currencyName ) ) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String getName ( ) {
        return currencyName;
    }

    @Override
    public void setName ( String name ) {
        this.currencyName = name;
    }


}
