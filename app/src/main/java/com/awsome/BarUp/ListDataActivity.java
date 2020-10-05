package com.awsome.BarUp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.awsome.BarUp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {
    private static final String FILE_NAME = "barUsers.txt";
    String textinput;
    ArrayList<String> listData = new ArrayList<>();


    private static final String TAG = "ListDataActivity";
    //  DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        Button passwordChange = findViewById(R.id.passwordChange);
        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordChange = new Intent(ListDataActivity.this, Anmeldung.class);
                startActivity(passwordChange);
            }
        });

        Button btnMailsend = findViewById(R.id.btnMailsend);
        btnMailsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMail = new Intent(ListDataActivity.this, SendMail.class);
                intentMail.putExtra("textinput", textinput);
                startActivity(intentMail);

            }
        });

        Button btncopy = findViewById(R.id.btncopy);

        btncopy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //  ArrayList<String> listData = getIntent().getStringArrayListExtra("listAdmin");


                String listString = "";

                for (String s : listData) {
                    listString += s + "\n";
                }


                //kopierfunktion
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText", listString);  //man kann hier als EditText alles schreiben ist egal
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Text wurde kopiert",
                        Toast.LENGTH_SHORT).show();
                //kopierfunktion ende


            }
        });

        //hide the title Bar in the top lane
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        getSupportActionBar().hide(); //hide the title bar

        //hide the title Bar in the top lane


        mListView = findViewById(R.id.listView);
        //  mDatabaseHelper=new DatabaseHelper(this);

        populateListView();

    }

    private void populateListView() {




        // ArrayList<String> listData = getIntent().getStringArrayListExtra("listAdmin");


        //ausgeben neuer Text in fos
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


                // mEditText.setText(sb.toString());
                textinput = sb.toString();

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
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

            mListView.setAdapter(adapter);




        } finally {

        }

    }



}