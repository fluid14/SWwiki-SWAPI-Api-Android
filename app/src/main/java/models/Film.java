package models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Film implements Serializable
{
    public String title;

    @SerializedName("episode_id")
    public int episodeId;

    @SerializedName("opening_crawl")
    public String openingCrawl;
    public String director;
    public String producer;

    @SerializedName("release_date")
    public String releaseDate;

    @Override
    public String toString()
    {
        return "<h1>" + title + "</h1>"
                + "<p><b>Episode:</b> " + episodeId + "</p>"
                + "<p><b>Director:</b> " + director + "</p>"
                + "<p><b>Producer:</b> " + producer + "</p>"
                + "<p><b>Release date:</b> " + releaseDate + "</p>"
                + "<p><b>Opening crawl:</b> </p>\n" + openingCrawl;
    }


}
