package com.example.guutimetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;


import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;                 //Клиент для подключения к гиту
    Workbook workbook;                      //Книга эксель

    private Cell asdfasdfasdf;
    private final static String FILE_NAME = "content.txt";                                      // файл для сохранения информации о группе
    private final static String FILE_NAME_SETTINGS = "settings.txt";
    private Cell time1;                                                                         // Ячейка для чтения с экселя(время)
    private Integer firstnumberrow, firstnumbercollumn;                                         // Номера ряда и коллонны с которых начинаем
    private String[] stringsGroups = new String[49];                                            // массив строк для всех пар
    private String weeks, url, specsave, s_idgruppi, s_colvogrup, s_cousre, s_magbak, s_idsheet, s_settings;      // Строки неделя, ссылка на гит, строкасохранения, строки id
    private Integer idsheet, idgruppi, colvogrup, cousre, magbak,idstyle,idtextcolor,idbackgroundcolor; // id для поиска ячеек эксель
    private String[] namegroups = new String[16];                                               // массив строк для имен всех групп с листа
    private ScrollView scroll;                                                                  // ссылка на xml скролл
    private TextView week, nameofgroup, nowweek,background;                                     // ссылка на xml текста Неделя, Имя группы, текущая неделя
    private TextView[] groups = new TextView[25];                                               // ссылка на xml массив для текстов
    private TextView[] days = new TextView[6];                                                  // кнопки 1.Смена недели(чет/нечет) 2.Переход в настройки группы 3. Скролл на сегодняшний день
    private ProgressBar progressBar;
    private ImageButton gruppa, settingsbutton1, todaybutton,settingsall;
    private LinearLayout setbar;
    private Drawable[] textviewerdrawable = new Drawable[6];
    private int[] backgrounddrawable = new int[2];
    private int[] ColorForText = new int[3];
    private TextView[] groupsmagsfordelete = new TextView[10];
    private String[] stringsGroupsMags = new String[29];
    private Integer maxcharstosetsize = 125;
    private Integer daystart = 37;







    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        url = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;


        Integer nowyear = LocalDate.now().getYear();
        Integer weekofyear = (LocalDate.now().getDayOfYear() - LocalDate.ofYearDay(nowyear, daystart).getDayOfYear()) / 7 + 1;
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
        idsheet = 0;
        idgruppi = 4;
        colvogrup = 15;                             //базовые значения  (затычка)
        cousre = 1;
        magbak = 0;
        idstyle = 0;


        Intent intent = new Intent(MainActivity.this, selection.class);         // переход на страницу настройки
        Intent intenttoset = new Intent(MainActivity.this, allset.class);
        specsave = idsheet.toString() + idgruppi.toString() + colvogrup.toString() + cousre.toString() + magbak.toString();     //создание строки сохранения




        try {
            Bundle arguments = getIntent().getExtras();
            int x = Integer.parseInt(arguments.get("nameSheet").toString());
            int y = Integer.parseInt(arguments.get("nameGroup").toString());
            int z = Integer.parseInt(arguments.get("nameColvo").toString());
            int x1 = Integer.parseInt(arguments.get("nameMagbak1").toString());
            int y1 = Integer.parseInt(arguments.get("nameCourse1").toString());

            idsheet = x;
            idgruppi = y;
            colvogrup = z;
            cousre = y1;
            magbak = x1;

            s_idsheet = idsheet.toString();                                                             //все это конвертирует информацию для строки сохранения
            if (idgruppi >= 10) {
                s_idgruppi = idgruppi.toString();
            } else {
                s_idgruppi = "x" + idgruppi.toString();
            }
            if (colvogrup >= 10) {
                s_colvogrup = colvogrup.toString();
            } else {
                s_colvogrup = "x" + colvogrup.toString();
            }
            s_cousre = cousre.toString();
            s_magbak = magbak.toString();

            specsave = s_idsheet.toString() + s_idgruppi.toString() + s_colvogrup.toString() + s_cousre.toString() + s_magbak.toString();  //создание строки сохранения

            FileOutputStream fos = null;                                                            //начало сохранения строки
            try {
                String text = specsave;
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(text.getBytes());



            } catch (IOException ex) {
              //  Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException ex) {
               //     Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        FileInputStream fin = null;                                                             // начало загрузки строки сохранения

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            specsave = text;


        } catch (IOException ex) {
          //  Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
             //   Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        try {                                                                                               // разбор строки на составляющии
            if (specsave != "") {

                idsheet = specsave.charAt(0) - '0';
                if (specsave.charAt(1) == 'x') {
                    idgruppi = specsave.charAt(2) - '0';

                } else {
                    idgruppi = (specsave.charAt(1) - '0') * 10 + (specsave.charAt(2) - '0');

                }
                if (specsave.charAt(3) == 'x') {
                    colvogrup = specsave.charAt(4) - '0';

                } else {
                    colvogrup = (specsave.charAt(3) - '0') * 10 + (specsave.charAt(4) - '0');

                }

                cousre = specsave.charAt(5) - '0';
                magbak = specsave.charAt(6) - '0';



            } else {

            }

        } catch (Exception e) {
            startActivity(intent);
            overridePendingTransition(0,0);
            e.printStackTrace();
        }


        if (magbak == 0) {                                                                                                          //подключение к гиту
                    if (cousre == 1) {
                        url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FirstCourse1.xls?raw=true";
                    } else if (cousre == 2) {
                        url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/SecondCourse.xls?raw=true";
                    } else if (cousre == 3) {
                        url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/ThirdCourse.xls?raw=true";
                    } else if (cousre == 4) {
                        url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FourCousre.xls?raw=true";
                    }
                }
                else
                    if (magbak == 1)
                    {
                        url = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/FirstSecondMag.xls?raw=true";
                    }
            setbar = findViewById(R.id.setbar);
            settingsall = findViewById(R.id.settingsall);
            background = findViewById(R.id.background);
            progressBar = findViewById(R.id.progressBar2);
            scroll = findViewById(R.id.scrollView2);
            week = findViewById(R.id.nedelya);
            todaybutton = findViewById(R.id.todaybutton);
            nowweek = findViewById(R.id.nowweek);
            gruppa = findViewById(R.id.gruppam);
            nameofgroup = findViewById(R.id.nameofgroup);
            days[0] = findViewById(R.id.Day1);
            days[1] = findViewById(R.id.Day2);
            days[2] = findViewById(R.id.Day3);
            days[3] = findViewById(R.id.Day4);
            days[4] = findViewById(R.id.Day5);
            days[5] = findViewById(R.id.Day6);
            groups[1] = findViewById(R.id.group1_1_1);
            groups[2] = findViewById(R.id.group1_2_1);
            groups[3] = findViewById(R.id.group1_3_1);                                              //подключение всех с xml
            groups[4] = findViewById(R.id.group1_4_1);
            groups[5] = findViewById(R.id.group2_1_1);
            groups[6] = findViewById(R.id.group2_2_1);
            groups[7] = findViewById(R.id.group2_3_1);
            groups[8] = findViewById(R.id.group2_4_1);
            groups[9] = findViewById(R.id.group3_1_1);
            groups[10] = findViewById(R.id.group3_2_1);
            groups[11] = findViewById(R.id.group3_3_1);
            groups[12] = findViewById(R.id.group3_4_1);
            groups[13] = findViewById(R.id.group4_1_1);
            groups[14] = findViewById(R.id.group4_2_1);
            groups[15] = findViewById(R.id.group4_3_1);
            groups[16] = findViewById(R.id.group4_4_1);
            groups[17] = findViewById(R.id.group5_1_1);
            groups[18] = findViewById(R.id.group5_2_1);
            groups[19] = findViewById(R.id.group5_3_1);
            groups[20] = findViewById(R.id.group5_4_1);
            groups[21] = findViewById(R.id.group6_1_1);
            groups[22] = findViewById(R.id.group6_2_1);
            groups[23] = findViewById(R.id.group6_3_1);
            groups[24] = findViewById(R.id.group6_4_1);
            settingsbutton1 = findViewById(R.id.setweek);
            groupsmagsfordelete[0] = findViewById(R.id.group5_4_1);
            groupsmagsfordelete[1] = findViewById(R.id.group1_3_1);
            groupsmagsfordelete[2] = findViewById(R.id.group1_4_1);                                              //подключение всех с xml
            groupsmagsfordelete[3] = findViewById(R.id.group2_3_1);
            groupsmagsfordelete[4] = findViewById(R.id.group2_4_1);
            groupsmagsfordelete[5] = findViewById(R.id.group3_3_1);
            groupsmagsfordelete[6] = findViewById(R.id.group3_4_1);
            groupsmagsfordelete[7] = findViewById(R.id.group4_3_1);
            groupsmagsfordelete[8] = findViewById(R.id.group4_4_1);
            groupsmagsfordelete[9] = findViewById(R.id.group5_3_1);





            scroll.setAlpha(0);




            nowweek.setText(weekofyear.toString()+"  ");
            firstnumberrow = 8;
            firstnumbercollumn = 17;
            gruppa.setColorFilter(ColorForText[0]);
            todaybutton.setColorFilter(ColorForText[0]);
            settingsall.setColorFilter(ColorForText[0]);
            todaybutton.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
            gruppa.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));
            settingsall.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[0]));



        if(weekofyear % 2 == 0)
            weeks = "Чёт.";
        else
            weeks = "Нечёт.";

        week.setText(weeks);

            gruppa.setOnClickListener(new View.OnClickListener() {                              //кнопка перехода на настройки
                @Override
                public void onClick(View v) {



                    intent.putExtra("specsave", specsave);

                    startActivity(intent);
                    overridePendingTransition(0,0);

                }
            });



            todaybutton.setOnClickListener(new View.OnClickListener() {                     //кнопка скролла
                @Override
                public void onClick(View v) {

                    firstnumberrow = 0;

                    if(day_of_week == 1)                                                            //посчитать!
                        scroll.smoothScrollTo(0,0);
                    else
                    if(day_of_week == 2)
                        scroll.smoothScrollTo(0,groups[4].getBottom() + 200);
                    else
                    if(day_of_week == 3)
                        scroll.smoothScrollTo(0,groups[8].getBottom() + 200);
                    else
                    if(day_of_week == 4)
                        scroll.smoothScrollTo(0,groups[12].getBottom() + 200);
                    else
                    if(day_of_week == 5)
                        scroll.smoothScrollTo(0,groups[16].getBottom() + 200);
                    else
                    if(day_of_week == 6)
                        scroll.smoothScrollTo(0,groups[20].getBottom() + 200);
                    else
                        scroll.smoothScrollTo(0,0);

                    if(weekofyear % 2 == 0)
                    {
                        weeks = "Чёт.";
                        week.setText(weeks);
                        firstnumberrow+=2;
                        for(Integer i = 1; i < 25; i++)
                        {
                            groups[i].setText(stringsGroups[firstnumberrow]);
                            firstnumberrow+=2;
                            if(groups[i].getText().length() >= maxcharstosetsize)
                            {
                                groups[i].setTextSize(1, (groups[i]).getTextSize()/3);
                            }
                        }
                    }
                    else
                    if(weekofyear % 2 != 0)
                    {
                        firstnumberrow+=1;
                        weeks = "Нечёт.";
                        week.setText(weeks);
                        for(Integer i = 1; i < 25; i++)
                        {
                            groups[i].setText(stringsGroups[firstnumberrow]);
                            firstnumberrow+=2;
                            if(groups[i].getText().length() >= maxcharstosetsize)
                            {
                                groups[i].setTextSize(1, (groups[i]).getTextSize()/3);
                            }
                        }
                    }
                    else
                    {

                    }


                }
            });




        settingsall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intenttoset);
                overridePendingTransition(0,0);

            }
        });

            settingsbutton1.setOnClickListener(new View.OnClickListener() {                         //кнопка смены недели
            @Override
            public void onClick(View v) {

               firstnumberrow = 0;


                    if(weeks == "Чёт.")
                 {


                     firstnumberrow+=1;
                     weeks = "Нечёт.";
                     week.setText(weeks);
                         for(Integer i = 1; i < 25; i++)
                            {

                                    groups[i].setText(stringsGroups[firstnumberrow]);
                                    firstnumberrow+=2;
                                    if(groups[i].getText().length() >= maxcharstosetsize)
                                    {
                                        groups[i].setTextSize(1, (groups[i]).getTextSize()/3);
                                    }

                            }
                 }
                    else

                    {
                        weeks = "Чёт.";
                        week.setText(weeks);
                        firstnumberrow+=2;
                        for(Integer i = 1; i < 25; i++)
                        {

                            groups[i].setText(stringsGroups[firstnumberrow]);
                            firstnumberrow+=2;
                            if(groups[i].getText().length() >= maxcharstosetsize)
                            {
                                groups[i].setTextSize(1, (groups[i]).getTextSize()/3);
                            }

                        }
                    }

            }
        });



        FileInputStream finset = null;                                                             // начало загрузки строки сохранения настр

        try {
            finset = openFileInput(FILE_NAME_SETTINGS);
            byte[] bytes = new byte[finset.available()];
            finset.read(bytes);
            String text = new String(bytes);
            s_settings = text;
            if(s_settings == "")
            {
                s_settings = "000";
            }



        } catch (IOException ex) {
           // Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (finset != null)
                    finset.close();
            } catch (IOException ex) {
              //  Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        try {
            idstyle = s_settings.charAt(0) - '0';
            idtextcolor = s_settings.charAt(1) - '0';
            idbackgroundcolor = s_settings.charAt(2) - '0';

        }
        catch (Exception e)
        {

        }
        try {
            week.setTextColor(ColorForText[idtextcolor]);
            nameofgroup.setTextColor(ColorForText[idtextcolor]);
            nowweek.setTextColor(ColorForText[idtextcolor]);
            settingsbutton1.setColorFilter(ColorForText[idtextcolor]);
            background.setBackgroundColor(backgrounddrawable[idbackgroundcolor]);
            gruppa.setColorFilter(ColorForText[idtextcolor]);
            todaybutton.setColorFilter(ColorForText[idtextcolor]);
            settingsall.setColorFilter(ColorForText[idtextcolor]);
            todaybutton.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[idbackgroundcolor]));
            gruppa.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[idbackgroundcolor]));
            settingsall.setBackgroundTintList(ColorStateList.valueOf(backgrounddrawable[idbackgroundcolor]));
            setbar.setBackgroundColor(backgrounddrawable[idbackgroundcolor]);
            for(Integer i =0; i < 6; i++)
            {
                days[i].setTextColor(ColorForText[idtextcolor]);
            }


            for(Integer i = 1; i <= 24; i++)
            {

                groups[i].setBackground(textviewerdrawable[idstyle]);
                groups[i].setTextColor(ColorForText[idtextcolor]);


            }
            }


           catch (Exception e)
                {

                }






        if(day_of_week == 1)
            scroll.smoothScrollTo(0,0);
        else
        if(day_of_week == 2)
            scroll.smoothScrollTo(0,groups[4].getBottom() + 200);
        else
        if(day_of_week == 3)
            scroll.smoothScrollTo(0,groups[8].getBottom() + 200);
        else
        if(day_of_week == 4)
            scroll.smoothScrollTo(0,groups[12].getBottom() + 200);
        else
        if(day_of_week == 5)
            scroll.smoothScrollTo(0,groups[16].getBottom() + 200);
        else
        if(day_of_week == 6)
            scroll.smoothScrollTo(0,groups[20].getBottom() + 200);
        else
            scroll.smoothScrollTo(0,0);





        if(magbak == 1) {
            for (Integer i = 0; i < 10; i++) {
                groupsmagsfordelete[i].getLayoutParams().height = 0;
                groupsmagsfordelete[i].requestLayout();
            }
        }


        client = new AsyncHttpClient();                                                                                                                                                         //работа с гитом и подключением к нему
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(MainActivity.this, "Download Failed!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {

                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if (file != null) {
                        try {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setEnabled(false);
                        scroll.setAlpha(1);
                        workbook = workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(idsheet);

                        for (Integer i = 0; i < colvogrup; i++)                                                                                                                                    //получение имен всех групп
                        {
                            namegroups[i] = sheet.getCell(4 + i, 5).getContents().toString() + " " + cousre.toString() + "-" + sheet.getCell(4 + i, 6).getContents().toString();
                        }
                        firstnumbercollumn = idgruppi + 4;
                        nameofgroup.setText(namegroups[idgruppi]);                                                                                                                                   //смена текста в xml группы
                        if(namegroups[idgruppi].length() > 30)
                            nameofgroup.setTextSize(1, (nameofgroup.getTextSize()/3));
                        if (weekofyear % 2 == 0) {
                            firstnumberrow = 9;

                        } else {
                            firstnumberrow = 8;
                        }

                        if(magbak == 0) {
                            for (Integer i = 1; i < 49; i++) {
                                Integer a = i;
                                String sss = sheet.getCell(firstnumbercollumn, 7 + a).getContents();                        //получаем ячейки групп и сохраняем их в массив строк
                                time1 = sheet.getCell(2, i + 7);
                                if (sss == "") {
                                    stringsGroups[i] = "\n\nЗанятий нет";
                                } else {
                                    stringsGroups[i] = "\n" + time1.getContents() + "\n\n" + sheet.getCell(firstnumbercollumn, 7 + a).getContents();
                                    for (Integer w = 8; w < 20; w++) {

                                        String s1 = w.toString() + " н";
                                        String s2 = w.toString() + " нед.";
                                        String s3 = w.toString() + "н";
                                        if (sss.contains(s1) | sss.contains(s2) | sss.contains(s3)) {
                                            if (w < weekofyear) {
                                                // Проверка на неделях

                                                stringsGroups[i] = "\n\nЗанятия закончились";
                                            }

                                        }

                                    }
                                }

                                /**/
                            }

                        }
                        else
                            if(magbak == 1)
                            {
                                for (Integer i = 1; i < 29; i++) {
                                    Integer a = i;
                                    String sss = sheet.getCell(firstnumbercollumn, 7 + a).getContents();                        //получаем ячейки групп и сохраняем их в массив строк
                                    time1 = sheet.getCell(2, i + 7);
                                    if (sss == "") {
                                        stringsGroupsMags[i] = "\n\nЗанятий нет";
                                    } else {
                                        stringsGroupsMags[i] = "\n" + time1.getContents() + "\n\n" + sheet.getCell(firstnumbercollumn, 7 + a).getContents();
                                        for (Integer w = 8; w < 20; w++) {

                                            String s1 = w.toString() + " н";
                                            String s2 = w.toString() + " нед.";
                                            String s3 = w.toString() + "н";
                                            if (sss.contains(s1) | sss.contains(s2) | sss.contains(s3)) {
                                                if (w < weekofyear) {
                                                    // Проверка на неделях

                                                    stringsGroupsMags[i] = "\n\nЗанятия закончились";
                                                }

                                            }

                                        }
                                    }



                                    /**/
                                }
                                    for(Integer i = 1; i < 5; i++)
                                    {

                                        stringsGroups[i]=stringsGroupsMags[i];
                                        stringsGroups[i+8]=stringsGroupsMags[i+4];
                                        stringsGroups[i+16]=stringsGroupsMags[i+8];
                                        stringsGroups[i+24]=stringsGroupsMags[i+12];
                                        stringsGroups[i+32]=stringsGroupsMags[i+16];
                                    }
                                    for(Integer i = 41; i < 49; i++)
                                    {
                                        stringsGroups[i]=stringsGroupsMags[i-20];
                                    }



                            }

            /**/






                        todaybutton.callOnClick();


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }


            }
        });



    }

 }











