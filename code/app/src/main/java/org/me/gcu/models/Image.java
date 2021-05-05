//S1803445
//Andrew Minja
package org.me.gcu.models;

public class Image {
    private String link;
    private String title;
    private String url;

    public String getLink () { return link; }

    public void setLink (String link) { this.link = link; }

    public String getTitle () { return title; }

    public void setTitle (String title) { this.title = title; }

    public String getUrl () { return url; }

    public void setUrl (String url) { this.url = url; }

    @Override
    public String toString() {
        return "ClassPojo [link = "+link+", title = "+title+", url = "+url+"]";
    }
}
