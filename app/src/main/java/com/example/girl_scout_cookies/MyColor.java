package com.example.girl_scout_cookies;

import static android.graphics.Color.parseColor;
import static android.graphics.Color.rgb;

import android.graphics.Color;
import android.graphics.ColorSpace;

public class MyColor {

    int color;
    String name;

     public MyColor( int r, int g, int b, String nameParam ){
         this.name = nameParam;
         this.color = (0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);



     };

     public String toString(){
         return this.name;
     }

     public int getColor(){
         return this.color;
     }

}
