package com.awsome.BarUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.awsome.BarUp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Admin extends AppCompatActivity {
    private static final String FILE_ADMIN = "admin.txt";
    String Getpassword = "";
    String loginName;
    String password;
    long kundenanzahl;

    String name, adress, mail, phone, dataStamp, textinput, placeholder = " ";
    private static final String FILE_NAME = "barUsers.txt";
    ArrayList<String> listData = new ArrayList<>();
    ArrayList<Integer> overDate = new ArrayList<>();  //Zählt die Abgelaufenen Datums hoch


    // List<String> ListErhalte = new ArrayList<>();

    int hochzaehlen;

    //  RecyclerView recyclerView;  MUSS NOCH ÜBERPRÜFT WERDEN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btnlogin= findViewById(R.id.btnlogin);
        btnlogin.setClickable(false);
        //Startet die Funktion auslesen aus den Inhalten im TextFile und Vergleicht das Datum
        Readin();



        //Startet die Funktion auslesen aus den Inhalten im TextFile und Vergleicht das Datum


        //erhalte Passwort
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_ADMIN);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            Getpassword = sb.toString();
            // textinput=sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //erhalte Passwort ende



        //für ladezeiten
        btnlogin.setEnabled(false);
        btnlogin.setEnabled(true);
        //für ladezeiten
        //hide the title Bar in the top lane
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        getSupportActionBar().hide(); //hide the title bar

        //hide the title Bar in the top lane
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginStart();
            }
        });
/*
        ListErhalte.add(aErsterName);
        ListErhalte.add(aNachname);
        ListErhalte.add(geschlecht);
        ListErhalte.add(mail);
        ListErhalte.add(phone);
        ListErhalte.add(zeitstempel);
        ListErhalte.add(abstand);
        ListErhalte.add(abstand);


 */

        btnlogin.setEnabled(true);

    }

    public void loginStart() {

        TextView Tvpassword = findViewById(R.id.password);

        TextView textView6 = findViewById(R.id.textView6);
        ImageView backgroundLogin = findViewById(R.id.backgroundLogin);
        Button btnlogin = findViewById(R.id.btnlogin);


        password = Tvpassword.getText().toString();

        if (password.equals(Getpassword)) {  //Geiste    Location2020


            showListe();

        } else {
            Toast.makeText(this, "Falsches Passwort", Toast.LENGTH_SHORT).show();
        }

    }


    public void showListe() {


        Intent intent = new Intent(Admin.this, ListDataActivity.class);
        // intent.putStringArrayListExtra("listAdmin",(ArrayList<String>)ListErhalte);
        startActivity(intent);


    }

    //Einlesen für Datumsabgleich
    void Readin() {


        FileOutputStream fos = null;
        try {

            FileInputStream fis = null;
            try {
                fis = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;


                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                    listData.add(text); //get jedes Einzellne Element aus dem Stringbuffer

                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            // ArrayList<String> listData= new ArrayList<>();
            //   listData.add(textinput);
            //  ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

            //  mListView.setAdapter(adapter);


        } finally {
            TimelisteMinusOne();
        }


    }

    void TimelisteMinusOne() {
        try {






            ArrayList<String> getDate = new ArrayList<>();

            getDate.add(listData.get(5));
            for (int i = 11; i < listData.size(); i = i + 6) {
                getDate.add(listData.get(i));
            }
            for (int y = 0; y < getDate.size(); y++) {
                String FinalDate = getDate.get(y);//"1-09-2020";  //String den ich brauche zum vergleichen
                Date date1;
                Date date2;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
                String CurrentDate = dates.format(new Date());// Aktuelle Datum zum Vergleichen
                date1 = dates.parse(CurrentDate);
                date2 = dates.parse(FinalDate);
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                String dayDifference = Long.toString(differenceDates); //macht mir die Differenz von der Zeit als Positiver Wert

                //  textView.setText("The difference between two dates is " + dayDifference + " days");


                int dayDifferenceInt = Integer.parseInt(dayDifference);
                if (dayDifferenceInt > 28) {

                    int count = 0;
                    int hoch = 5;
                    while (count != y) {
                        hoch = hoch + 5;
                        count++;
                    }
                    hoch = hoch + y;

                    overDate.add(hoch);
                    NewOutWrite();
                }
            }

        } catch (Exception exception) {

        }
    }

    void NewOutWrite() {
        //ausgeben neuer Text in fos
        FileOutputStream fos = null;
        try {
               //listData
            /*
            placeholder = placeholder += "\n";
            name = name += "\n";
            adress = adress += "\n";
            mail = mail += "\n";
            phone = phone += "\n";
            dataStamp = dataStamp + "\n";

*/
            int getDateFromList=overDate.get(0)-5;


            fos = openFileOutput(FILE_NAME, MODE_PRIVATE); //, MODE_PRIVATE
          //  fos.write(placeholder.getBytes());
           int count=0;
           while (getDateFromList!=count){

               String input=listData.get(count)+"\n";

            fos.write(input.getBytes());
            count++;
           }
            //   mEditText.getText().clear();

        } catch (
                IOException e) {
            e.printStackTrace();

        } finally {
            if (fos != null) { //close file output stream
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Button btnlogin= findViewById(R.id.btnlogin);
            btnlogin.setClickable(true);
        }
    }
}