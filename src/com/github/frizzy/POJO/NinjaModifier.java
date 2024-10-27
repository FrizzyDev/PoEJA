
package com.github.frizzy.POJO;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NinjaModifier {

    @SerializedName ( "text" )
    private String modText = "Unknown";

    @SerializedName ( "optional" )
    private boolean modOptional;

    public String getModText ( ) {
        return modText;
    }

    @Override
    public String toString ( ) {
        return modText;
    }
}
