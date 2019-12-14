package models;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class People implements Serializable
{
    public String name;
    public String height;
    public String mass;
    public String gender;

    @SerializedName("birth_year")
    public String birthYear;

    @SerializedName("hair_color")
    public String hairColor;

    @SerializedName("eye_color")
    public String eyeColor;

    @SerializedName("skin_color")
    public String skinColor;

    @Override
    public String toString()
    {
        return "<h1>" +  name + "</h1>"
                + "<p><b>Birth Year:</b> " + birthYear + "</p>"
                + "<p><b>Gender:</b> " + gender + "</p>"
                + "<p><b>Hair color:</b> " + hairColor +"</p>"
                + "<p><b>Height:</b> " + height + "</p>"
                + "<p><b>Mass:</b> " + mass + "</p>"
                + "<p><b>Skin color:</b> " + skinColor + " </p>";
    }
}
