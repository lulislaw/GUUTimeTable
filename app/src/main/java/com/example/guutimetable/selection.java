package com.example.guutimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.IOException;

public class selection extends AppCompatActivity {
            private final static String FILE_NAME_SETTINGS = "settings.txt";
            private Button magistr,bakalavr,contin;
            private Spinner spinnercoursa;
            private Integer style,colortext,backgroundcolor;
            private Integer magbak, course;
            private TextView textcourse,background_select;
            private ImageButton home,allset,select;
            private String s_settings;
            private Drawable[] textviewerdrawable = new Drawable[6];

            private int[] backgrounddrawable = new int[2];
            private int[] ColorForText = new int[3];
            private LinearLayout downmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        backgrounddrawable[0]= 0xFFFFFFFF;
        backgrounddrawable[1]= 0xFF282d3a;
        textviewerdrawable[0]= getDrawable(R.drawable.roundcorners);
        textviewerdrawable[1]= getDrawable(R.drawable.darkdrawable);
        textviewerdrawable[2]= getDrawable(R.drawable.pinkdrawable);
        textviewerdrawable[3]= getDrawable(R.drawable.bluedrawable);
        textviewerdrawable[4]= getDrawable(R.drawable.reddrawable);
        textviewerdrawable[5]= getDrawable(R.drawable.reddarkdrawable);
        ColorForText[1] = 0xFFFFFFFF;
        ColorForText[0] = 0xFF111111;



        magbak = -1;
        course = -1;
        background_select = findViewById(R.id.background_select);
        downmenu = findViewById(R.id.downmenu_select);
        home = findViewById(R.id.mainbuttononselection);
        allset = findViewById(R.id.settingsbuttonselection);
        select = findViewById(R.id.gruppabutonselection);
        magistr = findViewById(R.id.buttonmag);
        contin = findViewById(R.id.continueselect);
        bakalavr = findViewById(R.id.buttonbak);
        spinnercoursa = findViewById(R.id.spinnercursa);
        textcourse = findViewById(R.id.nomercursatext);
        spinnercoursa.setVisibility(View.INVISIBLE);
        textcourse.setVisibility(View.INVISIBLE);
        contin.setVisibility(View.INVISIBLE);
        magbak = -1;
        select.setColorFilter(ColorForText[0]);
        home.setColorFilter(ColorForText[0]);
        allset.setColorFilter(ColorForText[0]);
        home.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
        select.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
        allset.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
        Intent intent = new Intent(selection.this, settings.class);

        Intent intent_to_home = new Intent(selection.this, MainActivity.class);
        Intent intent_to_allset = new Intent(selection.this,allset.class);
loadsettings();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_to_home);
                overridePendingTransition(0,0);
            }
        });

        allset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_to_allset);
                overridePendingTransition(0,0);
            }
        });








        magistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magistr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttontrue)));
                bakalavr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonfalse)));

             //   Toast.makeText(selection.this, "Магистратура", Toast.LENGTH_SHORT).show();
                String[] arraySpinner1 = new String[]
                        {
                                "1", "2"

                        };

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(selection.this, android.R.layout.simple_spinner_item, arraySpinner1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnercoursa.setAdapter(adapter1);


                magbak = 1;
                spinnercoursa.setVisibility(View.VISIBLE);
                textcourse.setVisibility(View.VISIBLE);


            }
        });


        bakalavr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magistr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonfalse)));
                bakalavr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttontrue)));
              //  Toast.makeText(selection.this, "Бакалавриат", Toast.LENGTH_SHORT).show();
                String[] arraySpinner1 = new String[]
                        {
                                "1","2","3","4"

                        };

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(selection.this, android.R.layout.simple_spinner_item, arraySpinner1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnercoursa.setAdapter(adapter1);
                magbak = 0;
                spinnercoursa.setVisibility(View.VISIBLE);
                textcourse.setVisibility(View.VISIBLE);

            }
        });

        spinnercoursa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = position + 1;
                contin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    intent.putExtra("nameMagbak", magbak);
                    intent.putExtra("nameCourse", course);
                    startActivity(intent);
                overridePendingTransition(0,0);

            }
        });
        SharedPreferences.Editor editor = getSharedPreferences("PreferencesName", MODE_PRIVATE).edit();
        editor.putInt("magbak", magbak);
        editor.putInt("course", course);
        editor.apply();




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
       //     Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
            background_select.setBackgroundColor(backgrounddrawable[backgroundcolor]);
            textcourse.setTextColor(ColorForText[colortext]);
            home.setColorFilter(ColorForText[colortext]);
            allset.setColorFilter(ColorForText[colortext]);
            select.setColorFilter(ColorForText[colortext]);
            home.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));
            allset.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));
            select.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[backgroundcolor]));

        }


        catch (Exception e)
        {

        }
    }



}