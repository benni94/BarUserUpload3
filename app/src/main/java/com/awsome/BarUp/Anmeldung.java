package com.awsome.BarUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.awsome.BarUp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Anmeldung extends AppCompatActivity {
    private static final String FILE_ADMIN = "admin.txt";
    private static final String FILE_ADMIN_MAIL = "admin_Mail.txt";
    String password;
    String adminMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmeldung);

        //Dark Mode
        final Button btndarkmodeAcitvate = findViewById(R.id.btndarkmodeAcitvate);
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
            btndarkmodeAcitvate.setText("Dark Mode OFF");

            //   btnToggleDark.setImageResource(R.drawable.sun);


            // btnToggleDark.setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btndarkmodeAcitvate.setText("Dark Mode ON");

            //  btnToggleDark.setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY);
            //  Drawable invertedDrawable = new BitmapDrawable(getResources(), String.valueOf(btnToggleDark));
            //  btnToggleDark.setImageResource(R.drawable.moon);

        }
        // ImageButton btnToggleDark    = findViewById(R.id.btnToggleDark);


        btndarkmodeAcitvate.setOnClickListener(
                new View.OnClickListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                            // change text of Button
                            //  btnToggleDark.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                            btndarkmodeAcitvate.setText("Aktiviere Dark Mode");
                        } else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                            // change text of Button
                            //   btnToggleDark.setText(
                            //           "Disable Dark Mode");
                            btndarkmodeAcitvate.setText("Deaktiviere Dark Mode");
                        }
                    }
                });


        /////End Dark Mode


        Button btnUebernehmen = findViewById(R.id.btnUebernehmen);


        getMailto();
        getPasswordto();


        //hide the title Bar in the top lane
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        getSupportActionBar().hide(); //hide the title bar

        //hide the title Bar in the top lane

        btnUebernehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendMailto();
                sendPasswordto();

                Intent backToMain = new Intent(Anmeldung.this, MainActivity.class);
                startActivity(backToMain);

            }

        });


    }

    void getMailto() {

        TextView newMailAd = findViewById(R.id.newMailAd);
        // adminMail = newMailAd.getText().toString();
        FileInputStream fis2 = null;
        try {
            fis2 = openFileInput(FILE_ADMIN_MAIL);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader br2 = new BufferedReader(isr2);
            StringBuilder sb2 = new StringBuilder();
            String text2;
            while ((text2 = br2.readLine()) != null) {
                sb2.append(text2);
            }
            newMailAd.setText(sb2.toString()); // adminMail = sb2.toString();
            // textinput=sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    void getPasswordto() {
        TextView newPassword = findViewById(R.id.newPassword);

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
            newPassword.setText(sb.toString());//  password = sb.toString();
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
    }

    void sendMailto() {
        final TextView newMailAd = findViewById(R.id.newMailAd);
        adminMail = newMailAd.getText().toString();
        FileInputStream fis2 = null;
        try {
            fis2 = openFileInput(FILE_ADMIN_MAIL);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader br2 = new BufferedReader(isr2);
            StringBuilder sb2 = new StringBuilder();
            String text2;
            while ((text2 = br2.readLine()) != null) {
                sb2.append(text2);
            }
            adminMail = sb2.toString();
            // textinput=sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } //ausgeben neuer Text in adminMAil

        FileOutputStream fos2 = null;
        try {


            fos2 = openFileOutput(FILE_ADMIN_MAIL, MODE_PRIVATE); //, MODE_PRIVATE
            fos2.write(newMailAd.getText().toString().getBytes());


            //   mEditText.getText().clear();

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (fos2 != null) { //close file output stream
                try {
                    fos2.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }


        }

    }

    void sendPasswordto() {
        final TextView newPassword = findViewById(R.id.newPassword);
        password = newPassword.getText().toString();
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
            password = sb.toString();
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
        } //ausgeben neuer Text in fos

        FileOutputStream fos = null;
        try {


            fos = openFileOutput(FILE_ADMIN, MODE_PRIVATE); //, MODE_PRIVATE
            fos.write(newPassword.getText().toString().getBytes());


            //   mEditText.getText().clear();
            Toast.makeText(Anmeldung.this, "Passwort wurde übernommen", Toast.LENGTH_SHORT).show();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
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

            //////////////////
        }
    }
}