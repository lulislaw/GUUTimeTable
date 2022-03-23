/*
░░░░░░░░░▄▄▄████████▄▄▄░░░░░░░░░
░░░░░░▄██████████████████▄░░░░░░
░░░░▄██████████████████████▄░░░░
░░▄██████▀▀▀███████▀▀▀███████▄░░
░▄█████▀░░░░░█████░░░░░▀██████▄░
░██████░░░░░░█████░░░░░░███████░
███████░░░░░▄█████░░░░░░████████
███████▄░░░▄███████▄░░░▄████████
█████████████████████▄██████████
████████████████████████████████
░██████████▀▀░░░░░░░░▀▀▀███████░
░▀██████▀░░░░░▄▄▄▄▄▄▄░░░░█████▀░
░░▀████░░▄▄██████████████████▀░░
░░░░▀██████████████████████▀░░░░
░░░░░░▀██████████████████▀░░░░░░
░░░░░░░░░▀▀▀████████▀▀▀░░░░░░░░░
*/
package com.example.guutimetable;


import android.content.Intent;
import cz.msebera.android.httpclient.Header;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;




public class settings extends AppCompatActivity {
    private final static String FILE_NAME_SETTINGS = "settings.txt";
    AsyncHttpClient client;
    Workbook workbook;
    private Button mainbutton,backbutton;
    private ImageButton home,allset,select;
    private String groupname,instname,colvoname,url;
    private Integer colvoiy,colvoig,colvoim,colvoie,colvoio,colvo1kmag,colvo1kmagiis,colvo2kmag,colvo2kmagiis;
    private Integer magbak, course;
    private Integer style,colortext,backgroundcolor;
    private ProgressBar progressBar;
    private TextView background_settings;
    private Spinner s1;
    private String s_settings;
    private Drawable[] textviewerdrawable = new Drawable[6];
    private int[] backgrounddrawable = new int[2];
    private int[] ColorForText = new int[3];
    private LinearLayout downmenu;



      @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings);
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
          downmenu = findViewById(R.id.downmenu_settings);
          background_settings = findViewById(R.id.background_settings);
          home = findViewById(R.id.mainbuttononset);
          allset = findViewById(R.id.settingsbuttonset);
          select = findViewById(R.id.gruppabutonset);
          s1 = findViewById(R.id.spinner2);
          course  = -1;
          magbak = -1;
          url = "";
          select.setColorFilter(ColorForText[0]);
          home.setColorFilter(ColorForText[0]);
          allset.setColorFilter(ColorForText[0]);
          home.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
          select.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
          allset.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
          Bundle arguments = getIntent().getExtras();
          int x = Integer.parseInt(arguments.get("nameMagbak").toString());
          int y = Integer.parseInt(arguments.get("nameCourse").toString());


          course  = y;
          magbak = x;

          Intent intent_to_gruppa = new Intent(settings.this, selection.class);
          Intent intent_to_home = new Intent(settings.this, MainActivity.class);
          Intent intent_to_allset = new Intent(settings.this,allset.class);

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

          select.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  startActivity(intent_to_gruppa);
                  overridePendingTransition(0,0);
              }
          });






          colvoiy = 2;
          colvoig = 2;
          colvoim = 2;
          colvoie = 2;
          colvoio = 2;

          colvo1kmag = 14;
          colvo1kmagiis = 9;
          colvo2kmag = 11;
          colvo2kmagiis = 4;
        if(course == 1)
        {
            colvoiy = 15;
            colvoig = 8;
            colvoim = 13;
            colvoie = 14;
            colvoio = 11;
        }
        else
        if(course == 2)
        {
            colvoiy = 14;
            colvoig = 12;
            colvoim = 15;
            colvoie = 14;
            colvoio = 11;
        }
        else
        if(course == 3)
        {
            colvoiy = 16;
            colvoig = 11;
            colvoim = 12;
            colvoie = 13;
            colvoio = 12;
        }
        else
        if(course == 4)
        {

            colvoiy = 15;
            colvoig = 9;
            colvoim = 9;
            colvoie = 12;
            colvoio = 13;
        }
          String[] iyiiig = new String[colvoiy];
          String[] igyip = new String[colvoig];
          String[] im = new String[colvoim];
          String[] ieif = new String[colvoie];
          String[] iom = new String[colvoio];
          String[] mag1k = new String[colvo1kmag];
          String[] mag1kiis = new String[colvo1kmagiis];
          String[] mag2k = new String[colvo2kmag];
          String[] mag2kiis = new String[colvo2kmagiis];

        progressBar = findViewById(R.id.progressBar2);
        backbutton = findViewById(R.id.backbutton);
        mainbutton = findViewById(R.id.mainbutton);
        Intent intent = new Intent(settings.this, MainActivity.class);

        Intent intenttoback = new Intent(settings.this, selection.class);
          loadsettings();
                  if (magbak == 0) {
                      if (course == 1) {
                          url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FirstCourse1.xls?raw=true";
                      } else if (course == 2) {
                          url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/SecondCourse.xls?raw=true";
                      } else if (course == 3) {
                          url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/ThirdCourse.xls?raw=true";
                      } else if (course == 4) {
                          url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FourCousre.xls?raw=true";
                      }


                  }
                  else
                  if (magbak == 1)
                  {

                          url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FirstSecondMag.xls?raw=true";

                  }

                    String[] arraySpinner = new String[]
                            {
                                  "Выбор института","ИУПСиБК, ИИС, ИГУиП(Соц.)","ИГУиП","ИМ","ИЭиФ","ИОМ"
                            };
                     Spinner s = (Spinner) findViewById(R.id.spinner);
                     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                     android.R.layout.simple_spinner_item, arraySpinner);
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     s.setAdapter(adapter);

      if(magbak == 1)
      {
            if(course == 1) {
                arraySpinner = new String[]
                        {
                                "Выбор института", "1 к. маг ИЭФ, ИОМ, ИМ, ИГУИП ", "1 к. маг ИУПСИБК, ИИС"
                        };

                adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arraySpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(adapter);
            }
            else
            if(course == 2) {
                arraySpinner = new String[]
                        {
                                "Выбор института","2 к. маг ИЭФ, ИОМ, ИМ, ИГУИП", "2 к. маг ИУПСИБК, ИИС "
                        };

                adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arraySpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(adapter);
            }

      }

          mainbutton.setVisibility(View.INVISIBLE);
          mainbutton.setEnabled(false);
          backbutton.setVisibility(View.INVISIBLE);
          backbutton.setEnabled(false);
              s1.setVisibility(View.INVISIBLE);
              s.setVisibility(View.INVISIBLE);

          client = new AsyncHttpClient();
          client.get(url, new FileAsyncHttpResponseHandler(settings.this) {
              @Override
              public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                  s1.setVisibility(View.INVISIBLE);
                  s.setVisibility(View.INVISIBLE);
                  mainbutton.setVisibility(View.INVISIBLE);
                  mainbutton.setEnabled(false);
                  backbutton.setVisibility(View.INVISIBLE);
                  backbutton.setEnabled(false);
              }

              @Override
              public void onSuccess(int statusCode, Header[] headers, File file) {

                  WorkbookSettings ws = new WorkbookSettings();
                  ws.setGCDisabled(true);

                  if(file != null)
                  {
                      try {
                          progressBar.setVisibility(View.INVISIBLE);
                          progressBar.setEnabled(false);
                          s1.setVisibility(View.VISIBLE);
                          s.setVisibility(View.VISIBLE);
                          mainbutton.setVisibility(View.VISIBLE);
                          mainbutton.setEnabled(true);
                          backbutton.setVisibility(View.VISIBLE);
                          backbutton.setEnabled(true);


                          if(magbak == 0) {
                              workbook = workbook.getWorkbook(file);
                              Sheet sheet = workbook.getSheet(0);
                              Sheet sheet2 = workbook.getSheet(1);
                              Sheet sheet3 = workbook.getSheet(2);
                              Sheet sheet4 = workbook.getSheet(3);
                              Sheet sheet5 = workbook.getSheet(4);




                              for (Integer i = 0; i < colvoiy; i++) {
                                  iyiiig[i] = sheet.getCell(4 + i, 6).getContents() + " - " + sheet.getCell(4 + i, 5).getContents();
                              }
                              for (Integer i = 0; i < colvoig; i++) {
                                  igyip[i] = sheet2.getCell(4 + i, 6).getContents() + " - " + sheet2.getCell(4 + i, 5).getContents();
                              }
                              for (Integer i = 0; i < colvoim; i++) {
                                  im[i] = sheet3.getCell(4 + i, 6).getContents() + " - " + sheet3.getCell(4 + i, 5).getContents();
                              }
                              for (Integer i = 0; i < colvoie; i++) {
                                  ieif[i] = sheet4.getCell(4 + i, 6).getContents() + " - " + sheet4.getCell(4 + i, 5).getContents();
                              }
                              for (Integer i = 0; i < colvoio; i++) {
                                  iom[i] = sheet5.getCell(4 + i, 6).getContents() + " - " + sheet5.getCell(4 + i, 5).getContents();
                              }
                          }
                          else
                              if (magbak == 1)
                              {
                                  workbook = workbook.getWorkbook(file);
                                  Sheet sheet = workbook.getSheet(0);
                                  Sheet sheet2 = workbook.getSheet(1);
                                  Sheet sheet3 = workbook.getSheet(2);
                                  Sheet sheet4 = workbook.getSheet(3);



                                  progressBar.setVisibility(View.INVISIBLE);
                                  progressBar.setEnabled(false);
                                  s1.setVisibility(View.VISIBLE);
                                  s.setVisibility(View.VISIBLE);

                                  for (Integer i = 0; i < colvo1kmag; i++) {
                                      mag1k[i] = sheet.getCell(4 + i, 6).getContents() + " - " + sheet.getCell(4 + i, 5).getContents();
                                  }
                                  for (Integer i = 0; i < colvo1kmagiis; i++) {
                                      mag1kiis[i] = sheet2.getCell(4 + i, 6).getContents() + " - " + sheet2.getCell(4 + i, 5).getContents();
                                  }
                                  for (Integer i = 0; i < colvo2kmag; i++) {
                                      mag2k[i] = sheet3.getCell(4 + i, 6).getContents() + " - " + sheet3.getCell(4 + i, 5).getContents();
                                  }
                                  for (Integer i = 0; i < colvo2kmagiis; i++) {
                                      mag2kiis[i] = sheet4.getCell(4 + i, 6).getContents() + " - " + sheet4.getCell(4 + i, 5).getContents();
                                  }

                              }



                      } catch (IOException e) {
                          e.printStackTrace();
                      } catch (BiffException e) {
                          e.printStackTrace();
                      }
                  }


              }
          });
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = s.getSelectedItem().toString();
                instname = String.valueOf(position-1);
                                   if(name == "ИУПСиБК, ИИС, ИГУиП(Соц.)")
                                   {        colvoname = colvoiy.toString();
                                       String[] arraySpinner1;
                                               arraySpinner1 = iyiiig;

                                       ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                       adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                       s1.setAdapter(adapter1);

                                   }
                                   else
                                       if(name == "ИГУиП")
                                       { colvoname = colvoig.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = igyip;
                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "ИМ")
                                       { colvoname = colvoim.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = im;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "ИЭиФ")
                                       { colvoname = colvoie.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = ieif;


                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "ИОМ")
                                       { colvoname = colvoio.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = iom;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "1 к. маг ИЭФ, ИОМ, ИМ, ИГУИП ")
                                       { colvoname = colvo1kmag.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = mag1k;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "1 к. маг ИУПСИБК, ИИС")
                                       { colvoname = colvo1kmagiis.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = mag1kiis;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "2 к. маг ИЭФ, ИОМ, ИМ, ИГУИП")
                                       { colvoname = colvo2kmag.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = mag2k;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }
                                       else
                                       if(name == "2 к. маг ИУПСИБК, ИИС ")
                                       { colvoname = colvo2kmagiis.toString();
                                           String[] arraySpinner1;
                                           arraySpinner1 = mag2kiis;

                                           ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           s1.setAdapter(adapter1);
                                       }



                                   else {
                                       String[] arraySpinner1 = new String[]
                                               {
                                                       "Выбор группы"
                                               };

                                       ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(settings.this, android.R.layout.simple_spinner_item, arraySpinner1);
                                       adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                       s1.setAdapter(adapter1);
                                   }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                   groupname = String.valueOf(position); // не забыть

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });










             mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magbak == 0) {

                    intent.putExtra("nameMagbak1", magbak);
                    intent.putExtra("nameCourse1", course);
                    intent.putExtra("nameSheet", instname);
                    intent.putExtra("nameGroup", groupname);
                    intent.putExtra("nameColvo", colvoname);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                }
                else
                    if(magbak == 1)
                    {

                        intent.putExtra("nameMagbak1", magbak);
                        intent.putExtra("nameCourse1", course);
                        intent.putExtra("nameSheet", instname);
                        intent.putExtra("nameGroup", groupname);
                        intent.putExtra("nameColvo", colvoname);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
            }
        });

            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intenttoback);
                    overridePendingTransition(0,0);
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
        //    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (finset != null)
                    finset.close();
            } catch (IOException ex) {
            //    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
            background_settings.setBackgroundColor(backgrounddrawable[backgroundcolor]);
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

