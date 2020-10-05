package com.awsome.BarUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awsome.BarUp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String name, adress, mail, phone, dataStamp, textinput, placeholder=" \n";
    private static final String FILE_NAME = "barUsers.txt";


    long maxid = 0;
    long kundenhoch;
    private static final int PICK_FROM_GALLERY = 101;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle readdInstanceState) {
        super.onCreate(readdInstanceState);
        setContentView(R.layout.activity_main);

        /////Dark Mode




        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        //  Context context= "test";
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
         //   btnToggleDark.setImageResource(R.drawable.sun);


           // btnToggleDark.setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);


          //  btnToggleDark.setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY);
          //  Drawable invertedDrawable = new BitmapDrawable(getResources(), String.valueOf(btnToggleDark));
          //  btnToggleDark.setImageResource(R.drawable.moon);

        }



        /////End Dark Mode




        fileExists();






        //hide the title Bar in the top lane
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
            getSupportActionBar().hide(); //hide the title bar

            //hide the title Bar in the top lane

            TextView showPdf = findViewById(R.id.showPdf);
            showPdf.setPaintFlags(showPdf.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            showPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showPdfClass = new Intent(MainActivity.this, ShowPdf.class);
                    startActivity(showPdfClass);
                }
            });

            ImageView login = findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAdmin();
                }
            });


            Button next = findViewById(R.id.next);


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        erhalteDaten();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

        }


    public void erhalteDaten() throws IOException {
        TextView Tvname = findViewById(R.id.Name);
          TextView Tvadress = findViewById(R.id.adress);
        TextView Tvmail = findViewById(R.id.mail);
        TextView TvphoneNumber = findViewById(R.id.phone);
        name = Tvname.getText().toString();
        adress = Tvadress.getText().toString();
        mail = Tvmail.getText().toString();
        phone = TvphoneNumber.getText().toString();
       // String sex = "";


        CheckBox acceptCheckbox = findViewById(R.id.acceptCheckbox);
        //   CheckBox feacceptCheckbox = findViewById(R.id.feacceptCheckbox);

        if (acceptCheckbox.isChecked()==false) {

            Toast.makeText(this, "Bitte akzeptiere die Datenschutzvereinbarungen", Toast.LENGTH_SHORT).show();

        } else if (name.matches("")) {
            Toast.makeText(this, "Bitte Vor- und Nachname Eingeben", Toast.LENGTH_SHORT).show();

        } else if (mail.matches("") && phone.matches("")) {
            Toast.makeText(this, "Bitte Mail oder Telefonnummer Eingeben", Toast.LENGTH_SHORT).show();
        } else {


            @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dataStamp =    s.format(new Date());// "01-09-2020";  s.format(new Date());

            // KundenHelper kundenHelper = new KundenHelper(firstName, seccondName, mail, phone, sex, dataStamp);
            int reminder = (int) maxid;
            String smaxid = Integer.toString(reminder);

            read();






            //    mDatabase.child(smaxid).setValue(kundenHelper);
            //Activity neu anstoßen

            name = "";
            adress = "";
            mail = "";
            phone = "";

            Tvname.setText(name);
            Tvadress.setText(adress);
            Tvmail.setText(mail);
            TvphoneNumber.setText(phone);
            acceptCheckbox.setChecked(false);
            Toast.makeText(this, "Eintrag wurde hinzugefügt.", Toast.LENGTH_LONG).show();
            //Activity neu anstoßen ende


        }
    }

    public void startAdmin() {
        Intent admin = new Intent(this, Admin.class);
        //  admin.putStringArrayListExtra()
        startActivity(admin);
    }

    public void read() throws IOException {


        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            // mEditText.setText(sb.toString());
            textinput = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        } finally {
            save();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }}

      public void save() {
          //ausgeben neuer Text in fos
          FileOutputStream fos = null;
          try {

           placeholder = placeholder ;
              name = name += "\n";
              adress = adress += "\n";
              mail = mail += "\n";
              phone = phone += "\n";
              dataStamp = dataStamp + "\n";

              fos = openFileOutput(FILE_NAME, MODE_PRIVATE); //, MODE_PRIVATE
              fos.write(placeholder.getBytes());

              fos.write(name.getBytes());
              fos.write(adress.getBytes());
              fos.write(mail.getBytes());
              fos.write(phone.getBytes());
              //   fos.write(sex.getBytes());
              fos.write(dataStamp.getBytes());


              fos.write(textinput.getBytes());

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
          }
      }

          public void fileExists() {

              File f = new File("/data/data/com.awsome.BarUp/files/barUsers.txt");


              if (!f.exists()) {


                  FileOutputStream fos = null;
                  try {

                      placeholder = placeholder;
                      String   name = "\n";
                      String   adress = "\n";
                      String  mail = "\n";
                      String  phone = "\n";
                      String  dataStamp =  "02-10-2010" +"\n";

                      fos = openFileOutput(FILE_NAME, MODE_PRIVATE); //, MODE_PRIVATE
                     fos.write(placeholder.getBytes());
                      fos.write(name.getBytes());
                      fos.write(adress.getBytes());
                      fos.write(mail.getBytes());
                      fos.write(phone.getBytes());
                      //   fos.write(sex.getBytes());
                      fos.write(dataStamp.getBytes());


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

                      }}


              }

}}
