package models;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Planet implements Serializable
{
    public String name;
    public String diameter;
    public String gravity;
    public String population;
    public String climate;
    public String terrain;

    @SerializedName("rotation_period")
    public String rotationPeriod;

    @SerializedName("surface_water")
    public String surfaceWater;

    @Override
    public String toString() {
        return "<h1>" + name + "</h1>"
                + "<p><b>Diameter:</b> " + diameter + "</p>"
                + "<p><b>Rotation period(day period):</b> " + rotationPeriod + "</p>"
                + "<p><b>Gravity:</b> " + gravity + "</p>"
                + "<p><b>Population:</b> " + population + "</p>"
                + "<p><b><p><b>Climate:</b> " + climate + "</p>"
                + "<p><b>Terrain:</b> " + terrain + "</p>"
                + "<p><b>Surface of water(percentage):</b> " + surfaceWater + "</p>";
    }
}
