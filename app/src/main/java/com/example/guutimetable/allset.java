package com.example.guutimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;



    public class allset extends AppCompatActivity {
    private final static String FILE_NAME_SETTINGS = "settings.txt";
    private final static String FILE_NAME = "settings.txt";
    private ImageButton home,allset,select;
    private Button classic,pink,blue,dark,whitered,darkred;
    private Integer style,colortext,backgroundcolor;
    private Drawable[] textviewerdrawable = new Drawable[6];
    private int[] backgrounddrawable = new int[2];
    private int[] ColorForText = new int[3];
    private LinearLayout downmenu,backforthemes;
    private TextView background_allset,text_tems;
    private String s_settings;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allset);
        classic = findViewById(R.id.classicstyle);
        dark = findViewById(R.id.darkstyle);
        pink = findViewById(R.id.pinkstyle);
        blue = findViewById(R.id.bluestyle);
        whitered = findViewById(R.id.whiteredstyle);
        darkred= findViewById(R.id.darkredstyle);
        home = findViewById(R.id.mainbuttononallset);
        allset = findViewById(R.id.settingsbuttonallset);
        select = findViewById(R.id.gruppabutonallset);
        downmenu = findViewById(R.id.downmenu_allset);
        background_allset = findViewById(R.id.backgroundallset);
        text_tems = findViewById(R.id.text_tems);
        backforthemes = findViewById(R.id.backforthemes);
        Intent intent_to_gruppa = new Intent(allset.this, selection.class);
        Intent intent_to_home = new Intent(allset.this, MainActivity.class);


        backgrounddrawable[0]= 0xFFFFFFFF;
        backgrounddrawable[1]= 0xFF282d3a;
        textviewerdrawable[0]= getDrawable(R.drawable.roundcorners);
        textviewerdrawable[1]= getDrawable(R.drawable.darkdrawable);
        textviewerdrawable[2]= getDrawable(R.drawable.pinkdrawable);
        textviewerdrawable[3]= getDrawable(R.drawable.bluedrawable);
        textviewerdrawable[4]= getDrawable(R.drawable.reddrawable);
        textviewerdrawable[5]= getDrawable(R.drawable.reddarkdrawable);
        ColorForText[1] = 0xFFFFFFFF;
        ColorForText[0] = 0xFF282d3a;
        select.setColorFilter(ColorForText[0]);
        home.setColorFilter(ColorForText[0]);
        allset.setColorFilter(ColorForText[0]);
        home.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
        select.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
       allset.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));



    loadsettings();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent_to_home);
                overridePendingTransition(0,0);

            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent_to_gruppa);
                overridePendingTransition(0,0);
            }
        });


        classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 0;
                colortext = 0;
                backgroundcolor = 0;
                savesettings();

            }
        });
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 1;
                colortext = 1;
                backgroundcolor = 1;
                savesettings();

            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 2;
                colortext = 0;
                backgroundcolor = 0;
                savesettings();

            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 3;
                colortext = 1;
                backgroundcolor = 1;
                savesettings();

            }
        });
        whitered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 4;
                colortext = 0;
                backgroundcolor = 0;
                savesettings();

            }
        });
        darkred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = 5;
                colortext = 1;
                backgroundcolor = 1;
                savesettings();

            }
        });








    }
public void loadsettings()
{
    FileInputStream finset = null;                                                             // начало загрузки строки сохранения настр

    try {
        finset = openFileInput(FILE_NAME_SETTINGS);
        byte[] bytes = new byte[finset.available()];
        finset.read(bytes);
        String text = new String(bytes);
        s_settings = text;




    } catch (IOException ex) {
      //  Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    } finally {
        try {
            if (finset != null)
                finset.close();
        } catch (IOException ex) {
         //   Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    try {
        style = s_settings.charAt(0) - '0';
        colortext = s_settings.charAt(1) - '0';
        backgroundcolor = s_settings.charAt(2) - '0';

    }
    catch (Exception e)
    {

    }
    try {

        downmenu.setBackgroundColor(backgrounddrawable[backgroundcolor]);
        background_allset.setBackgroundColor(backgrounddrawable[backgroundcolor]);
        text_tems.setTextColor(ColorForText[colortext]);
        home.setColorFilter(ColorForText[colortext]);
        allset.setColorFilter(ColorForText[colortext]);
        select.setColorFilter(ColorForText[colortext]);
        backforthemes.setBackground(textviewerdrawable[style]);
        home.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));
        allset.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));
        select.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));



    }


    catch (Exception e)
    {

    }
}




    public void savesettings()
    {


        FileOutputStream fos = null;                                                            //начало сохранения строки
        try {
            String text = style.toString() +colortext.toString()+backgroundcolor.toString();
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());


            loadsettings();

        } catch (IOException ex) {
        //    Toast.makeText(allset.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
            //    Toast.makeText(allset.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

        }





