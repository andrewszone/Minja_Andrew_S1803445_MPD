//S1803445
package org.me.gcu.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Channel {
    @PrimaryKey
    private int id = 1;
    private Image image;
    private List<Item> items;
    private String lastBuildDate;
    private String link;
    private String description;
    private String language;
    private String title;

    public int getId() { return id; }

    public void setId(int id) {this.id = id; }

    public Image getImage () { return image; }

    public void setImage (Image image) { this.image = image; }

    public List<Item> getItems () { return items; }

    public void setItems (List<Item> items) { this.items = items; }

    public String getLastBuildDate () { return lastBuildDate; }

    public void setLastBuildDate (String lastBuildDate) { this.lastBuildDate = lastBuildDate; }

    public String getLink () { return link; }

    public void setLink (String link) { this.link = link; }

    public String getDescription () { return description; }

    public void setDescription (String description) { this.description = description; }

    public String getLanguage () { return language; }

    public void setLanguage (String language) { this.language = language; }

    public String getTitle () { return title; }

    public void setTitle (String title) { this.title = title; }

    @Override
    public String toString() {
        return "ClassPojo [image = "+image+", item = "+items+", lastBuildDate = "+lastBuildDate+", link = "+link+", description = "+description+", language = "+language+", title = "+title+"]";
    }
}
