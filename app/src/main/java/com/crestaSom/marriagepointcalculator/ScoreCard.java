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
import java.util.Map;
import java.util.Scanner;

import com.example.testappv4.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreCard extends Activity {

    TextView display;
    int scr[][] = new int[50][6];
    LinearLayout mainLayout, scoreCard, round, newLayout, totalDisplay;
    Intent recv;
    int playerNo, recordNo;
    Map<Integer, ArrayList<Integer>> score;
    ArrayList<Integer> temp;
    int[] scoreTotal;
    ArrayList<String> PlayerName;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i, j = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);


        //for reading from file


        PlayerName = new ArrayList<String>();
        score = new HashMap<Integer, ArrayList<Integer>>();
        scoreTotal = new int[6];
        mainLayout = (LinearLayout) findViewById(R.id.playerNames);
        scoreCard = (LinearLayout) findViewById(R.id.scores);
        round = (LinearLayout) findViewById(R.id.round);
        totalDisplay = (LinearLayout) findViewById(R.id.total);
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

//		display = new TextView(this);
//		display.setText(String.valueOf(recordNo)+" "+String.valueOf(playerNo));
//		display.setTextAppearance(getApplicationContext(),
//				R.style.text_style);
//		
//		mainLayout.addView(display);
//		
        }
        displayLayout();
    }

    // @Override
    // public void onBackPressed()
    // {
    // super.onBackPressed();
    // Intent i=new Intent(this,MainActivity.class);
    // startActivity(i);
    // // finish();
    // }

    void displayLayout() {
        int i, n, j;


        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (85 * scale + 0.5f);

        for (i = 0; i < playerNo; i++) {

            display = new TextView(this);
            display.setText(PlayerName.get(i));
            display.setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            display.setWidth(pixels);
            //	display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            display.setGravity(Gravity.CENTER);
            mainLayout.addView(display);
        }
        // display=new TextView(this);
        // display.setText(score.get(0).toString());
        // round.addView(display);
        for (i = 0; i < recordNo; i++) {
            /*n = i + 1;
			display = new TextView(this);
			display.setTextAppearance(getApplicationContext(),
					R.style.text_style);
			display.setText(String.valueOf(n));
			pixels = (int) (55 * scale + 0.5f);
            display.setWidth(pixels);


		//	display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
			round.addView(display);*/
        }


        for (i = 0; i < recordNo; i++) {
            newLayout = new LinearLayout(this);
            newLayout.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.setBackgroundResource(R.drawable.rounded_layout);

            n = i + 1;
            display = new TextView(this);
            display.setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            display.setText(String.valueOf(n));
            pixels = (int) (55 * scale + 0.5f);
            display.setGravity(Gravity.CENTER);
            display.setWidth(pixels);
            newLayout.addView(display);
            pixels = (int) (85 * scale + 0.5f);
            for (j = 0; j < playerNo; j++) {
                display = new TextView(this);
                display.setText(String.valueOf(scr[i][j]));
                //		display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                //display.setText(score.get(j).get(i).toString());

                display.setTextAppearance(getApplicationContext(),
                        R.style.text_style);
                display.setGravity(Gravity.CENTER);
                //scoreTotal[j] += Integer.parseInt(score.get(j).get(i)
//						.toString());
                scoreTotal[j] += scr[i][j];
                display.setWidth(pixels);
                newLayout.addView(display);
            }
            scoreCard.addView(newLayout);
        }
		/*newLayout = new LinearLayout(this);
		newLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		newLayout.setOrientation(LinearLayout.HORIZONTAL);
		scoreCard.addView(newLayout);*/
        display = new TextView(this);
        display.setText("Total");
        display.setTextAppearance(getApplicationContext(), R.style.text_style);
        pixels = (int) (55 * scale + 0.5f);
        display.setWidth(pixels);
        //display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        totalDisplay.addView(display);

        pixels = (int) (85 * scale + 0.5f);

        for (i = 0; i < playerNo; i++) {
            display = new TextView(this);
            display.setText(String.valueOf(scoreTotal[i]));
            //  display.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            display.setTextAppearance(getApplicationContext(),
                    R.style.text_style);
            display.setGravity(Gravity.CENTER);
            display.setWidth(pixels);
            totalDisplay.addView(display);

        }
        // scoreCard.addView(newLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, 1, 0, "Undo Last Score");
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

                Toast.makeText(this,"Last score has been removed from record",Toast.LENGTH_LONG).show();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (id == 2) {
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
        } else if (id == 3) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
