//S1803445
package org.me.gcu.utils;

import org.me.gcu.models.Channel;
import org.me.gcu.models.Image;
import org.me.gcu.models.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XmlParser {

    private Channel channel;
    private String value;
    private Image image;
    private final ArrayList<Item> items = new ArrayList<>();
    private Item item;
    private boolean inChannel = false;
    private boolean inImage = false;
    private boolean inItem = false;

    public Channel parse(InputStream stream){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(stream, null);

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT){
                String tag = parser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if (tag.equalsIgnoreCase("channel")) {
                            channel = new Channel();
                            inChannel = true;
                            inImage = inItem = false;
                        }
                        else if (tag.equalsIgnoreCase("image")){
                            image = new Image();
                            inImage = true;
                            inChannel = inItem = false;
                        }
                        else if (tag.equalsIgnoreCase("item")){
                            item = new Item();
                            inItem = true;
                            inChannel = inImage = false;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        value = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tag.equalsIgnoreCase("item")) items.add(item);
                        else if (tag.equalsIgnoreCase("image")) channel.setImage(image);
                        else if(tag.equalsIgnoreCase("channel")) channel.setItems(items);
                        else {
                            // Get the data
                            if (inChannel) getChannelAttr(tag);
                            else if(inImage) getImageAttr(tag);
                            else if(inItem) getItemAttr(tag);
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return channel;
    }

    private void getChannelAttr(String tag) {
        if (tag.equalsIgnoreCase("title"))
            channel.setTitle(value);
        else if (tag.equalsIgnoreCase("link"))
            channel.setLink(value);
        else if (tag.equalsIgnoreCase("language"))
            channel.setLanguage(value);
        else if (tag.equalsIgnoreCase("description"))
            channel.setDescription(value);
        else if (tag.equalsIgnoreCase("lastBuildDate"))
            channel.setLastBuildDate(value);
    }

    private void getImageAttr(String tag) {
        if (tag.equalsIgnoreCase("title"))
            image.setTitle(value);
        else if (tag.equalsIgnoreCase("url"))
            image.setUrl(value);
        else if(tag.equalsIgnoreCase("link"))
            image.setLink(value);
    }

    private void getItemAttr(String tag) {
        if (tag.equalsIgnoreCase("title"))
            item.setTitle(value);
        else if (tag.equalsIgnoreCase("description")){
            item.setDescription(value);
            item.setMagnitude(value);
            item.setLocation(value);
            item.setDepth(value);
            item.setDepthValue(item.getDepth());
        }
        else if (tag.equalsIgnoreCase("link"))
            item.setLink(value);
        else if (tag.equalsIgnoreCase("pubDate"))
            item.setPubDate(value);
        else if (tag.equalsIgnoreCase("category"))
            item.setCategory(value);
        else if (tag.equalsIgnoreCase("lat"))
            item.setLat(value);
        else if (tag.equalsIgnoreCase("long"))
            item.setLng(value);
    }

}
