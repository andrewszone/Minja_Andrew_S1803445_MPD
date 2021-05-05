//S1803445
package org.me.gcu.utils;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class ColorCode {
    private int color;
    private final List<String> colors = Arrays.asList("#3498DB", "#27AE60", "#DC7633", "#8E44AD", "#F4D03F", "#B03A2E");

    public int getColor() { return color; }

    public void setColor(double magnitude) {
        double i = 0.5;
        int index = 0;
        int color = Color.parseColor("#B03A2E");
        while (i <= 3){
            if (magnitude <= i) {
                color = Color.parseColor(colors.get(index));
                break;
            }
            index++;
            i += 0.5;
        }
        this.color = color;
    }
}
