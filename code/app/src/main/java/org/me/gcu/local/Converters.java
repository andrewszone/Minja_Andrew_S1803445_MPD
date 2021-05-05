package org.me.gcu.local;

import androidx.room.TypeConverter;

import org.me.gcu.models.Image;
import org.me.gcu.models.Item;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String itemsToJson(List<Item> items){
        return new Gson().toJson(items);
    }

    @TypeConverter
    public static List<Item> jsonToItems(String json){
        return Arrays.asList(new Gson().fromJson(json, Item[].class));
    }

    @TypeConverter
    public static String imageToJson(Image image){
        return new Gson().toJson(image);
    }

    @TypeConverter
    public static Image imageFromJson(String json){
        return new Gson().fromJson(json, Image.class);
    }
}
