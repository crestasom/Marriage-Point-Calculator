package com.crestaSom.marriagepointcalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.testappv4.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ScoreCard extends AppCompatActivity {

    TextView display;
    int scr[][] = new int[50][6];
    LinearLayout mainLayout, scoreCard, round, newLayout;
    RelativeLayout totalDisplay;
    Intent recv;
    int playerNo, recordNo;
    Map<Integer, ArrayList<Integer>> score;
    ArrayList<Integer> temp;
    int[] scoreTotal;
    ArrayList<String> PlayerName;

    TextView[] playerList;
    TextView undo;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i, j = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_child_toolbar);
        myChildToolbar.setTitle("Score Card");
        setSupportActionBar(myChildToolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for reading from file

        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinarlayout);

        PlayerName = new ArrayList<String>();
        score = new HashMap<Integer, ArrayList<Integer>>();
        scoreTotal = new int[6];
        mainLayout = (LinearLayout) findViewById(R.id.playerNames);
        scoreCard = (LinearLayout) findViewById(R.id.scores);
        round = (LinearLayout) findViewById(R.id.round);
        totalDisplay = (RelativeLayout) findViewById(R.id.total);
        recv = getIntent();
        Bundle bundle = recv.getExtras();
        PlayerName = bundle.getStringArrayList("playerList");
        playerNo = PlayerName.size();
        temp = new ArrayList<Integer>();
        // display.setText(String.valueOf(playerNo));
        for (i = 0; i < playerNo; i++) {
            temp = new ArrayList<Integer>();
            //temp = bundle.getIntegerArrayList("Score" + i);
            score.put(i, temp);
            scoreTotal[i] = 0;
        }
        //recordNo = score.get(0).size();
//		
        File root = new File(Environment.getExternalStorageDirectory(), "data/marriage point calculator");

        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, "score.txt");
        if (gpxfile.exists()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(gpxfile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //display.setText(e.getMessage());
            }
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(in));
            String str = "";
            String a[] = {"0", "0", "0", "0", "0"};


            try {
                while ((str = myReader.readLine()) != null) {
                    a = new String[6];
                    //tempList.add(Integer.parseInt(str));
                    //int tmp;
                    //display.setText(str);
                    a = str.split(",");
                    for (i = 0; i < a.length; i++) {
                        //temp=new ArrayList<Integer>();
                        //temp=score.get(i);
                        //temp.add(Integer.parseInt(a[i]));
                        //score.put(i, temp);
                        //score.get(j).add(Integer.parseInt(a[i]));
                        scr[j][i] = Integer.parseInt(a[i]);
                    }
//			if(tmp>t){
//				t=tmp;
                    j++;
                }
                recordNo = j;
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                //display.setText(e.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                //	display.setText(e.getMessage());
            }


        }


        int n;


        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (85 * scale + 0.5f);
        LinearLayout tempLayout;
        playerList = new TextView[6];

        for (i = 0; i < playerNo; i++) {

            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));

            tempLayout.setGravity(Gravity.CENTER);
            playerList[i] = new TextView(this);
            int ln = PlayerName.get(i).length();
            if (ln > 4) {
                playerList[i].setText(PlayerName.get(i).substring(0, 4));
            } else {
                playerList[i].setText(PlayerName.get(i));
            }
            playerList[i].setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            //playerList[i].setGravity(Gravity.CENTER);
            tempLayout.addView(playerList[i]);
            mainLayout.addView(tempLayout);
        }


        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                displayLayout();
            }
        });

    }

    // @Override
    // public void onBackPressed()
    // {
    // super.onBackPressed();
    // Intent i=new Intent(this,CalculatorActivity.class);
    // startActivity(i);
    // // finish();
    // }

    void displayLayout() {
        int i, j;
        LinearLayout tempLayout;
        RelativeLayout.LayoutParams params;
        int pixels;
        int scale = 0;
        int n;
        int x[] = new int[6];
        int[] location = new int[2];
        TextView game = (TextView) findViewById(R.id.textView3);
        game.setGravity(Gravity.CENTER);
        game.getLocationOnScreen(location);
        int x1 = location[0] + game.getMeasuredWidth() / 2;

        for (i = 0; i < playerNo; i++) {

            playerList[i].getLocationOnScreen(location);
            // x[i] = location[0] + playerList[i].getMeasuredWidth() / 2;
            x[i] = location[0];
        }

        RelativeLayout newLayoutR;
        for (i = 0; i < recordNo; i++) {
            newLayoutR = new RelativeLayout(this);
            newLayoutR.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            newLayoutR.setBackgroundResource(R.drawable.rounded_layout);
            newLayout = new LinearLayout(this);
            newLayout.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.setBackgroundResource(R.drawable.rounded_layout);

            n = i + 1;

            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));

            tempLayout.setGravity(Gravity.CENTER);

            display = new TextView(this);
            display.setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            display.setText(String.valueOf(n));
            pixels = (int) (55 * scale + 0.5f);
            display.setGravity(Gravity.CENTER);
            display.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            // display.setWidth(pixels);
            //tempLayout.addView(display);
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = x1;
            newLayoutR.addView(display, params);
            pixels = (int) (85 * scale + 0.5f);
            for (j = 0; j < playerNo; j++) {
                params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.leftMargin = x[j];
                tempLayout = new LinearLayout(this);
                tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                tempLayout.setGravity(Gravity.CENTER);
                display = new TextView(this);
                display.setText(String.valueOf(scr[i][j]));
                display.setTextAppearance(getApplicationContext(),
                        R.style.text_style);
                display.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                //display.setGravity(Gravity.CENTER);
                scoreTotal[j] += scr[i][j];
                //tempLayout.addView(display);
                newLayoutR.addView(display, params);
            }
            scoreCard.addView(newLayoutR);
        }

        newLayoutR = new RelativeLayout(this);
        newLayoutR.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        //newLayout.setOrientation(LinearLayout.HORIZONTAL);
        scoreCard.addView(newLayoutR);
        tempLayout = new LinearLayout(this);
        tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.7f));


        params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x1-game.getMeasuredWidth()/2;
        tempLayout.setGravity(Gravity.CENTER);
        display = new TextView(this);
        display.setText("Total");

        display.setTextAppearance(getApplicationContext(), R.style.text_style);

        //display.setWidth(pixels);
        //display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        //newLayoutR.addView(display,params);
        totalDisplay.addView(display, params);

        pixels = (int) (85 * scale + 0.5f);

        for (i = 0; i < playerNo; i++) {
            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));

            tempLayout.setGravity(Gravity.CENTER);
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = x[i];
            display = new TextView(this);
            display.setText(String.valueOf(scoreTotal[i]));
            //  display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            display.setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            display.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            display.setGravity(Gravity.CENTER);
            //display.setWidth(pixels);
            //tempLayout.addView(display);
            totalDisplay.addView(display, params);

        }
        // scoreCard.addView(newLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuitems_score, menu);
        /*undo = (TextView) menu.findItem(R.id.action_undo).getActionView();
        undo.setText("Undo Score");
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScoreCard.this);
                alertDialogBuilder
                        .setMessage("Are you sure you want to undo last score?");
                alertDialogBuilder.setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // Toast.makeText(CalculatorActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();

                                try {
                                    RandomAccessFile f = new RandomAccessFile(Environment.getExternalStorageDirectory().toString() + "/data/marriage point calculator/score.txt", "rw");
                                    long length = f.length() - 1;
                                    byte b;
                                    do {
                                        length -= 1;
                                        System.out.println(length + "");
                                        f.seek(length);
                                        b = f.readByte();
                                    } while (b != 10 && length > 0);
                                    if (length > 0) {
                                        f.setLength(length - 1);
                                    } else {
                                        f.setLength(0);
                                    }

                                   // Toast.makeText(ScoreCard.this, "Last score has been removed from record", Toast.LENGTH_LONG).show();
                                    snackbar=Snackbar.make(coordinatorLayout,"Last score has been removed from record",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    finish();
                                } catch (IOException e) {

                                    //Toast.makeText(ScoreCard.this, "No score is saved", Toast.LENGTH_LONG).show();
                                    snackbar=Snackbar.make(coordinatorLayout,"No score is saved",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }
                        });

                alertDialogBuilder.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });*/
        //menu.add(0, 1, 0, "Undo Last Score");
        menu.add(0, 2, 0, "Help");
        menu.add(0, 3, 0, "About");
        // return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.d("check message","message1");
            // return true;
            Intent menuIntent = new Intent(getApplicationContext(),
                    SettingsActivity.class);
                    startActivityForResult(menuIntent, 100);
                } else if (id == 1) {
                    try {
                        RandomAccessFile f = new RandomAccessFile(Environment.getExternalStorageDirectory().toString() + "/data/marriage point calculator/score.txt", "rw");
                        long length = f.length() - 1;
                        byte b;
                        do {
                            length -= 1;
                            System.out.println(length + "");
                            f.seek(length);
                            b = f.readByte();
                        } while (b != 10 && length > 0);
                        if (length > 0) {
                    f.setLength(length - 1);
                } else {
                    f.setLength(0);
                }

              //  Toast.makeText(this, "Last score has been removed from record", Toast.LENGTH_LONG).show();
                Snackbar snackbar=Snackbar.make(getCurrentFocus(),"Welcome",Snackbar.LENGTH_LONG);
                snackbar.show();
                finish();
            } catch (IOException e) {
               // Toast.makeText(this, "Last score has been removed from record", Toast.LENGTH_LONG).show();
                Snackbar snackbar=Snackbar.make(getCurrentFocus(),"Welcome",Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } else if (id == 2) {
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
        } else if (id == 3) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }else if(id==android.R.id.home){
            return false;
           // finish();
        }else  if (id==R.id.action_undo){
            Log.d("check message","message");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScoreCard.this);
            alertDialogBuilder
                    .setMessage("Are you sure you want to undo last score?");
            alertDialogBuilder.setNegativeButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Toast.makeText(CalculatorActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();

                            try {
                                RandomAccessFile f = new RandomAccessFile(Environment.getExternalStorageDirectory().toString() + "/data/marriage point calculator/score.txt", "rw");
                                long length = f.length() - 1;
                                byte b;
                                do {
                                    length -= 1;
                                    System.out.println(length + "");
                                    f.seek(length);
                                    b = f.readByte();
                                } while (b != 10 && length > 0);
                                if (length > 0) {
                                    f.setLength(length - 1);
                                } else {
                                    f.setLength(0);
                                }

                                // Toast.makeText(ScoreCard.this, "Last score has been removed from record", Toast.LENGTH_LONG).show();
                                snackbar=Snackbar.make(coordinatorLayout,"Last score has been removed from record",Snackbar.LENGTH_LONG);
                                snackbar.show();
                                finish();
                            } catch (IOException e) {

                                //Toast.makeText(ScoreCard.this, "No score is saved", Toast.LENGTH_LONG).show();
                                snackbar=Snackbar.make(coordinatorLayout,"No score is saved",Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        }
                    });

            alertDialogBuilder.setPositiveButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
