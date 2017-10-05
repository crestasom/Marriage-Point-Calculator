package com.crestaSom.marriagepointcalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.testappv4.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener,
        android.widget.CompoundButton.OnCheckedChangeListener {

    private static final int READ_FILE = 10;
    LinearLayout mainLayout;
    RelativeLayout buttonLayout, newLayout;
    TextView player, testDisplay, tvWin, tvPlayer, tvSeen, tvPoints, tvResult, tvPayment;
    EditText pointsEarned;
    CheckBox seens;
    int menuId = -1;
    MenuItem action_add;
    RadioButton wins;
    Button calculate, save;
    EditText p1, p2, p3, p4, p5;
    int[] pt;
    int[] pmt;
    int[] pnt;
    TextView[] playerTitle;
    String[] playerName = {"", "", "", "", "", ""};
    String test = "";
    Intent recv, nextIntent;
    List<CheckBox> seenList;
    List<EditText> pointList;
    boolean[] lessPoints = {false, false, false, false, false}, penalty15 = {false, false, false, false, false};
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 8;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    List<TextView> result, payment;
    List<RadioButton> winList;
    ArrayList<String> players;
    TextView total1, total2, total3, total4, total5;
    int seen, unseen, total = 0, playerNo = 6, point, gameType;
    boolean flag = true;
    Map<Integer, ArrayList<Integer>> score;
    // int [][]arr;
    ArrayList<Integer> temp;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    final String PREFKEY = "penaltyList";

    int index = -1;
    TextView save_menu;
    SharedPreferences getPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LOG INFO", "onCreate invoked");
        /*try {*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinarlayout);
        // my_child_toolbar is defined in the menu_action_calculate_view file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_child_toolbar);
        myChildToolbar.setTitle("Point Calculator");
        setSupportActionBar(myChildToolbar);

        score = new HashMap<Integer, ArrayList<Integer>>();
        prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        editor = prefs.edit();
        temp = new ArrayList<Integer>();

        tvWin = (TextView) findViewById(R.id.win);
        tvPlayer = (TextView) findViewById(R.id.player);
        tvSeen = (TextView) findViewById(R.id.seen);
        tvPoints = (TextView) findViewById(R.id.points);
        tvPayment = (TextView) findViewById(R.id.payment);
        tvResult = (TextView) findViewById(R.id.result);


        // arr=new int[5][25];
        playerName = new String[6];
        pnt = new int[6];
        pt = new int[6];
        pmt = new int[6];
        playerTitle = new TextView[6];
        lessPoints = new boolean[6];
        penalty15 = new boolean[6];
        players = new ArrayList<String>();
        seenList = new ArrayList<CheckBox>();
        winList = new ArrayList<RadioButton>();
        pointList = new ArrayList<EditText>();
        result = new ArrayList<TextView>();
        payment = new ArrayList<TextView>();
        testDisplay = (TextView) findViewById(R.id.textView1);
        //	win = (RadioGroup) findViewById(R.id.radioGroup);
        /*if (savedInstanceState != null) {
            players = savedInstanceState.getStringArrayList("players");
        } else {*/
        recv = getIntent();
        Bundle bundle = recv.getExtras();
        if (bundle != null) {
            players = bundle.getStringArrayList("playerList");
        }
        if (players.size() == 0) {
            /*players = bundle.getStringArrayList("playerList");*/
            players = getPlayerList();
               /* getPrefs = getSharedPreferences("players", 0);
                Log.i("pref object", getPrefs.toString());
                String s;
                for (int i = 1; i <= 6; i++) {
                    s = getPrefs.getString("player" + i, "NA");
                    if (!s.equals("NA")) {
                        Log.i("PlayerName"+i,s);
                        players.add(s);
                    }
                }*/
        }

        // }
        // testDisplay.setText(text);
        // players=recv.getStringArrayListExtra("playerName");
        playerNo = players.size();
        for (int i = 0; i < playerNo; i++) {
            score.put(i, new ArrayList<Integer>());
        }
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        buttonLayout = new RelativeLayout(this);
        buttonLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                displayLayout();
            }
        });

        /*} catch (NullPointerException ex) {
            Log.e("Null pointer exception", ex.getMessage());

        }*/
        // calculate=(Button)findViewById(R.id.calculate);
        // calculate.setOnClickListener(this);
        // win=(RadioGroup)findViewById(R.id.win);
        // win.setOnCheckedChangeListener(this);
        // seen1=(CheckBox)findViewById(R.id.seen1);
        // seen1.setOnCheckedChangeListener(this);
        // seen2=(CheckBox)findViewById(R.id.seen2);
        // seen2.setOnCheckedChangeListener(this);
        // seen3=(CheckBox)findViewById(R.id.seen3);
        // seen4=(CheckBox)findViewById(R.id.seen4);
        // seen5=(CheckBox)findViewById(R.id.seen5);
        // seen3.setOnCheckedChangeListener(this);
        // seen4.setOnCheckedChangeListener(this);
        // seen5.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStop() {
        Log.i("LOG INFO", "onStop invoked");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("LOG INFO", "onDestroy invoked");
        super.onDestroy();

    }

    public ArrayList<String> getPlayerList() {
        ArrayList<String> players = new ArrayList<String>();
        File root = new File(Environment.getExternalStorageDirectory(), "data/marriage point calculator");

        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, "players.txt");
        FileInputStream in = null;
        try {
            in = new FileInputStream(gpxfile);
            Log.i("log", in.toString());
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(in));
            String str = "";
            String a[] = {"0", "0", "0", "0", "0"};
            try {
                while ((str = myReader.readLine()) != null) {
                    a = new String[5];
                    //tempList.add(Integer.parseInt(str));
                    //int tmp;
                    //display.setText(str);
                    a = str.split(",");
                    for (int i = 0; i < a.length; i++) {
                        players.add(a[i]);
                    }

                    myReader.close();
                    in.close();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //display.setText(e.getMessage());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            //	display.setText(e.getMessage());
        }

        return players;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Are you sure,You want to exit? All game data will be lost!!!");
        alertDialogBuilder.setNegativeButton("Yes",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),
                                PlayerSelection.class);
                        File text = new File(Environment
                                .getExternalStorageDirectory(),
                                "data/marriage point calculator/score.txt");
                        text.delete();
                        text = new File(Environment
                                .getExternalStorageDirectory(),
                                "data/marriage point calculator/players.txt");
                        text.delete();

                        finish();
                        startActivity(i);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub

        menuId = v.getId();
        menu.add(Menu.NONE, 1, Menu.NONE, "Enable/Disable Less Point");
        //  menu.add(Menu.NONE, 2, Menu.NONE, "Enable/Disable 15 Point Penalty");
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case 1:
                /*Toast.makeText(this, String.valueOf(menuId), Toast.LENGTH_SHORT).show();*/
                if (lessPoints[menuId]) {
                    lessPoints[menuId] = false;
                    playerTitle[menuId].setBackgroundResource(R.drawable.rounded_border);
                    seenList.get(menuId).setChecked(false);
                    seenList.get(menuId).setEnabled(true);


                } else {
                    lessPoints[menuId] = true;
                    penalty15[menuId] = false;
                    seenList.get(menuId).setEnabled(true);
                    pointList.get(menuId).setEnabled(true);
                    pointList.get(menuId).setText("");
                    pointList.get(menuId).setHint("0-100");
                    playerTitle[menuId].setBackgroundResource(R.drawable.rounded_border_green);
                    seenList.get(menuId).setChecked(true);
                    seenList.get(menuId).setEnabled(false);
                }
                break;

            case 2:
                if (penalty15[menuId]) {
                    penalty15[menuId] = false;
                    playerTitle[menuId].setBackgroundResource(R.drawable.rounded_border);
                    seenList.get(menuId).setChecked(false);
                    seenList.get(menuId).setEnabled(true);
                    pointList.get(menuId).setEnabled(true);
                    pointList.get(menuId).setText("");
                    pointList.get(menuId).setHint("0-100");

                } else {
                    penalty15[menuId] = true;
                    //lessPoints[menuId]=false;
                    playerTitle[menuId].setBackgroundResource(R.drawable.rounded_border_red);
                    seenList.get(menuId).setChecked(false);
                    seenList.get(menuId).setEnabled(false);
                    pointList.get(menuId).setEnabled(false);
                    pointList.get(menuId).setText("0");
                    winList.get(menuId).setChecked(false);
                }
                break;
        }

        return super.onContextItemSelected(item);
    }


    void displayLayout() {
        for(int i=0;i<playerNo;i++){
            LinearLayout layout=(LinearLayout) getLayoutInflater().inflate(R.layout.main_activity_row,mainLayout,false);
            mainLayout.addView(layout);


            wins = (RadioButton)layout.findViewById(R.id.win);
            wins.setId(i);
            wins.setTag(R.id.my_radiobutton + i, "");
            wins.setOnCheckedChangeListener(this);
            winList.add(wins);

            playerTitle[i] = (TextView)layout.findViewById(R.id.playerNames);
            playerTitle[i].setText(players.get(i));
            playerTitle[i].setId(i);
            registerForContextMenu(playerTitle[i]);

            seens = (CheckBox)layout.findViewById(R.id.seen);
            seens.setId(i);
            seens.setTag(R.id.my_checkbox + i, "checkbox" + i);
            seenList.add(seens);

            pointsEarned = (EditText)layout.findViewById(R.id.score);
            pointsEarned.setId(i);
            pointsEarned.setTag(R.id.my_edit_text + i, "");
            pointsEarned
                    .setFilters(new InputFilter[]{new FilterInput(0, 100)});
            pointsEarned.setHintTextColor(Color.DKGRAY);
            pointList.add(pointsEarned);

            player = (TextView)layout.findViewById(R.id.result);
            player.setId(i);
            result.add(player);

            player = (TextView)layout.findViewById(R.id.payment);;
            player.setText("0");
            player.setId(i);
            player.setGravity(Gravity.CENTER);
            payment.add(player);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuitems, menu);

        action_add = (MenuItem) menu.findItem(R.id.action_add);
        /*//TextView saveItemMenu=(TextView) menu.findItem(R.id.action_add).getActionView();
        TextView calc = (TextView) menu.findItem(R.id.action_calc).getActionView();
       // calc.setText("Calculate");  // so the int isn't mistaken for a resource id!
        save_menu = (TextView) menu.findItem(R.id.action_add).getActionView();
        //save_menu.setText("Save");  // so the int isn't mistaken for a resource id!
        calc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // do your action here
                seen = Integer.parseInt(prefs.getString("seen", "3"));
                unseen = Integer.parseInt(prefs.getString("unseen", "10"));
                gameType = Integer.parseInt(prefs.getString("game_type", "3"));
                point = Integer.parseInt(prefs.getString("point", "1"));

                for (int x = 0; x < winList.size(); x++) {
                    if (winList.get(x).isChecked()) {
                        index = x;
                    }
                }
                if (index == -1) {
                    *//*Toast.makeText(getApplicationContext(), "Please Select a Winner",
                            Toast.LENGTH_SHORT).show();*//*
                    snackbar = Snackbar.make(coordinatorLayout, "Please Select a Winner", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    int i;
                    boolean flag = false;
                    total = 0;
                    for (i = 0; i < playerNo; i++) {
                        if (seenList.get(i).isChecked() || index == winList.get(i).getId() || gameType == 1) {
                            try {
                                pt[i] = Integer.parseInt(pointList.get(i).getText()
                                        .toString());
                            } catch (NumberFormatException e) {
                                *//*Toast.makeText(getApplicationContext(),
                                        "One or more score is missing",
                                        Toast.LENGTH_SHORT).show();*//*
                                snackbar = Snackbar.make(coordinatorLayout, "One or more score is missing", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                flag = true;
                                break;
                            }

                        } else {
                            pt[i] = 0;
                        }
                        total += pt[i];
                    }
                    if (!flag) {
                        calculatePenenty();
                        calculatePayment();
                        setPayment();
                        save_menu.setTextColor(Color.WHITE);
                        save_menu.setEnabled(true);
                        //action_add.setEnabled(true);
                        //action_add.setIcon(R.drawable.button_save);
                    }
                }


            }
        });
        save_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // do your action here
                // writeScoreFile();
                Snackbar.make(coordinatorLayout, "Save clicked", Snackbar.LENGTH_SHORT).show();
                checkPermission(MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
               *//* int i;
                String penaltyList = "";
                for (int x = 0; x < playerNo; x++) {
                    if (penalty15[x]) {
                        penaltyList += x + ",";
                    }
                }

                editor.putString(PREFKEY, penaltyList);
                editor.putBoolean("flag", true);
                editor.commit();

                flag = false;
                try {
                    File root = new File(
                            Environment.getExternalStorageDirectory(),
                            "data/marriage point calculator");

                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "score.txt");
                    FileWriter writer = new FileWriter(gpxfile, true);

                    String writeText = "";
                    for (i = 0; i < playerNo; i++) {
                        writeText += pmt[i] * point + ",";
                    }
                    // int tmp=writeText.length();

                    writeText += "\n";
                    writer.append(writeText);
                    writer.flush();
                    writer.close();

                    gpxfile = new File(root, "players.txt");
                    writer = new FileWriter(gpxfile);

                    writeText = "";
                    for (i = 0; i < playerNo; i++) {
                        writeText += players.get(i) + ",";
                    }
                    // int tmp=writeText.length();

                    writeText += "\n";
                    writer.append(writeText);
                    writer.flush();
                    writer.close();

                    // Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nextIntent = new Intent(getApplicationContext(),
                        ScoreCard.class);
                // for (i = 0; i < playerNo; i++) {
                // score.get(i).add(pmt[i] * point);
                // nextIntent.putIntegerArrayListExtra("Score" + i,
                // score.get(i));
                // }
                nextIntent.putStringArrayListExtra("playerList", players);
                startActivity(nextIntent);*//*

            }
        });*/
        // menu.add(0, 1, 0, "View Score");
        menu.add(0, R.id.my_setting, 0, "Setting");
        menu.add(0, 2, 0, "Help");
        menu.add(0, 3, 0, "About");
        // return true;
        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkFile() {

        File text = new File(Environment.getExternalStorageDirectory(),
                "data/marriage point calculator/players.txt");
        if (text.exists()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.my_setting) {
            // return true;
            Intent menuIntent = new Intent(getApplicationContext(),
                    SettingsActivity.class);
            startActivityForResult(menuIntent, 100);
        } else if (id == R.id.action_view) {

            if (checkFile() && checkPermission(READ_FILE)) {
                Intent intent = new Intent(getApplicationContext(), ScoreCard.class);
               /* for (int i = 0; i < playerNo; i++) {
                    // score.get(i).add(pmt[i]*point);
                    intent.putIntegerArrayListExtra("Score" + i, score.get(i));
                }*/
                intent.putStringArrayListExtra("playerList", players);
                startActivity(intent);
            } else {
                snackbar = Snackbar.make(coordinatorLayout, "No score has been set!!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } else if (id == 2) {
            startActivity(new Intent(getApplicationContext(),
                    HelpActivity.class));
        } else if (id == 3) {
            startActivity(new Intent(getApplicationContext(),
                    AboutActivity.class));
        } else if (id == R.id.action_calc) {

            seen = Integer.parseInt(prefs.getString("seen", "3"));
            unseen = Integer.parseInt(prefs.getString("unseen", "10"));
            gameType = Integer.parseInt(prefs.getString("game_type", "3"));
            point = Integer.parseInt(prefs.getString("point", "1"));

            for (int x = 0; x < winList.size(); x++) {
                if (winList.get(x).isChecked()) {
                    index = x;
                }
            }
            if (index == -1) {
              /*  Toast.makeText(getApplicationContext(), "Please Select a Winner",
                        Toast.LENGTH_SHORT).show();*/
                snackbar = Snackbar.make(coordinatorLayout, "Please Select a Winner", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                int i;
                boolean flag = false;
                this.total = 0;
                for (i = 0; i < playerNo; i++) {
                    if (seenList.get(i).isChecked() || index == winList.get(i).getId() || gameType == 1) {
                        try {
                            pt[i] = Integer.parseInt(pointList.get(i).getText()
                                    .toString());
                        } catch (NumberFormatException e) {
                           /* Toast.makeText(getApplicationContext(),
                                    "One or more score is missing",
                                    Toast.LENGTH_SHORT).show();*/
                            snackbar = Snackbar.make(coordinatorLayout, "One or more score is missing", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            flag = true;
                            break;
                        }

                    } else {
                        pt[i] = 0;
                    }
                    this.total += pt[i];
                }
                if (!flag) {
                    calculatePenenty();
                    calculatePayment();
                    setPayment();
                    action_add.setEnabled(true);
                    action_add.setIcon(R.drawable.ic_backup_white_24dp);
                }
            }
        } else if (id == R.id.action_add) {


            int i;
            String penaltyList = "";
            for (int x = 0; x < playerNo; x++) {
                if (penalty15[x]) {
                    penaltyList += x + ",";
                }
            }

            editor.putString(PREFKEY, penaltyList);
            editor.putBoolean("flag", true);
            editor.commit();

            flag = false;
            try {
                File root = new File(
                        Environment.getExternalStorageDirectory(),
                        "data/marriage point calculator");

                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, "score.txt");
                FileWriter writer = new FileWriter(gpxfile, true);

                String writeText = "";
                for (i = 0; i < playerNo; i++) {
                    writeText += pmt[i] * point + ",";
                }
                // int tmp=writeText.length();

                writeText += "\n";
                writer.append(writeText);
                writer.flush();
                writer.close();

                gpxfile = new File(root, "players.txt");
                writer = new FileWriter(gpxfile);

                writeText = "";
                for (i = 0; i < playerNo; i++) {
                    writeText += players.get(i) + ",";
                }
                // int tmp=writeText.length();

                writeText += "\n";
                writer.append(writeText);
                writer.flush();
                writer.close();

                // Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nextIntent = new Intent(getApplicationContext(),
                    ScoreCard.class);
            // for (i = 0; i < playerNo; i++) {
            // score.get(i).add(pmt[i] * point);
            // nextIntent.putIntegerArrayListExtra("Score" + i,
            // score.get(i));
            // }
            nextIntent.putStringArrayListExtra("playerList", players);
            startActivity(nextIntent);


        } else if (id == R.id.action_calc) {

            seen = Integer.parseInt(prefs.getString("seen", "3"));
            unseen = Integer.parseInt(prefs.getString("unseen", "10"));
            gameType = Integer.parseInt(prefs.getString("game_type", "3"));
            point = Integer.parseInt(prefs.getString("point", "1"));

            for (int x = 0; x < winList.size(); x++) {
                if (winList.get(x).isChecked()) {
                    index = x;
                }
            }
            if (index == -1) {
                    /*Toast.makeText(getApplicationContext(), "Please Select a Winner",
                            Toast.LENGTH_SHORT).show();*/
                snackbar = Snackbar.make(coordinatorLayout, "Please Select a Winner", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                int i;
                boolean flag = false;
                total = 0;
                for (i = 0; i < playerNo; i++) {
                    if (seenList.get(i).isChecked() || index == winList.get(i).getId() || gameType == 1) {
                        try {
                            pt[i] = Integer.parseInt(pointList.get(i).getText()
                                    .toString());
                        } catch (NumberFormatException e) {
                                /*Toast.makeText(getApplicationContext(),
                                        "One or more score is missing",
                                        Toast.LENGTH_SHORT).show();*/
                            snackbar = Snackbar.make(coordinatorLayout, "One or more score is missing", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            flag = true;
                            break;
                        }

                    } else {
                        pt[i] = 0;
                    }
                    total += pt[i];
                }
                if (!flag) {
                    calculatePenenty();
                    calculatePayment();
                    setPayment();

                    //action_add.setEnabled(true);
                    //action_add.setIcon(R.drawable.button_save);
                }


            }
        } else if (id == R.id.action_add) {

            checkPermission(MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            int i;
            String penaltyList = "";
            for (int x = 0; x < playerNo; x++) {
                if (penalty15[x]) {
                    penaltyList += x + ",";
                }
            }

            editor.putString(PREFKEY, penaltyList);
            editor.putBoolean("flag", true);
            editor.commit();

            flag = false;
            try {
                File root = new File(
                        Environment.getExternalStorageDirectory(),
                        "data/marriage point calculator");

                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, "score.txt");
                FileWriter writer = new FileWriter(gpxfile, true);

                String writeText = "";
                for (i = 0; i < playerNo; i++) {
                    writeText += pmt[i] * point + ",";
                }
                // int tmp=writeText.length();

                writeText += "\n";
                writer.append(writeText);
                writer.flush();
                writer.close();

                gpxfile = new File(root, "players.txt");
                writer = new FileWriter(gpxfile);

                writeText = "";
                for (i = 0; i < playerNo; i++) {
                    writeText += players.get(i) + ",";
                }
                // int tmp=writeText.length();

                writeText += "\n";
                writer.append(writeText);
                writer.flush();
                writer.close();

                // Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nextIntent = new Intent(getApplicationContext(),
                    ScoreCard.class);
            // for (i = 0; i < playerNo; i++) {
            // score.get(i).add(pmt[i] * point);
            // nextIntent.putIntegerArrayListExtra("Score" + i,
            // score.get(i));
            // }
            nextIntent.putStringArrayListExtra("playerList", players);
            startActivity(nextIntent);


        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        seen = Integer.parseInt(prefs.getString("seen", "3"));
        unseen = Integer.parseInt(prefs.getString("unseen", "10"));
        gameType = Integer.parseInt(prefs.getString("game_type", "3"));
        point = Integer.parseInt(prefs.getString("point", "1"));

        for (int x = 0; x < winList.size(); x++) {
            if (winList.get(x).isChecked()) {
                index = x;
            }
        }


        if (index == -1) {
            Toast.makeText(getApplicationContext(), "Please Select a Winner",
                    Toast.LENGTH_SHORT).show();

        } else {
            int i;
            // if (calculate.getText().toString().compareTo("Calculate") == 0) {
            if (v.getId() == 0) {
                this.total = 0;
                // testDisplay.setText(String.valueOf(total));
                for (i = 0; i < playerNo; i++) {
                    if (seenList.get(i).isChecked() || index == winList.get(i).getId() || gameType == 1) {
                        try {
                            pt[i] = Integer.parseInt(pointList.get(i).getText()
                                    .toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(),
                                    "One or more score is missing",
                                    Toast.LENGTH_SHORT).show();
                            // pt[i]=0;
                            return;

                        }

                    } else {
                        pt[i] = 0;
                    }
                    this.total += pt[i];
                }
                calculatePenenty();
                calculatePayment();
                setPayment();
                save.setEnabled(true);

            } else if (v.getId() == 1) {
                // checkPermission();


            }
        }
    }


    public boolean checkPermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("error info", "permission denied");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(coordinatorLayout, "Storage Permission is required to save score.", Snackbar.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        requestCode);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        } else {
            if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE)
                writeScoreFile();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    //&& grantResults[0] == 0) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    writeScoreFile();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(coordinatorLayout, "Storage Permission Denied. Cannot save score.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                return;
            }
            case READ_FILE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    //  writeScoreFile();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(coordinatorLayout, "Storage Permission Denied. Cannot save score.", Snackbar.LENGTH_SHORT);
                    //snackbar.show();
                }
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void writeScoreFile() {
        int i;
        String penaltyList = "";
        for (int x = 0; x < playerNo; x++) {
            if (penalty15[x]) {
                penaltyList += x + ",";
            }
        }

        editor.putString(PREFKEY, penaltyList);
        editor.putBoolean("flag", true);
        editor.commit();

        flag = false;
        try {
            File root = new File(
                    Environment.getExternalStorageDirectory(),
                    "data/marriage point calculator");

            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "score.txt");
            FileWriter writer = new FileWriter(gpxfile, true);

            String writeText = "";
            for (i = 0; i < playerNo; i++) {
                writeText += pmt[i] * point + ",";
            }
            // int tmp=writeText.length();

            writeText += "\n";
            writer.append(writeText);
            writer.flush();
            writer.close();

            gpxfile = new File(root, "players.txt");
            writer = new FileWriter(gpxfile);

            writeText = "";
            for (i = 0; i < playerNo; i++) {
                writeText += players.get(i) + ",";
            }
            // int tmp=writeText.length();

            writeText += "\n";
            writer.append(writeText);
            writer.flush();
            writer.close();
            nextIntent = new Intent(getApplicationContext(),
                    ScoreCard.class);
            // for (i = 0; i < playerNo; i++) {
            // score.get(i).add(pmt[i] * point);
            // nextIntent.putIntegerArrayListExtra("Score" + i,
            // score.get(i));
            // }
            nextIntent.putStringArrayListExtra("playerList", players);
            startActivity(nextIntent);

            // Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub

        Toast.makeText(getApplication(), "" + checkedId, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub

        if (isChecked) {
            for (int x = 0; x < winList.size(); x++) {
                if (buttonView.getId() != x) {
                    winList.get(x).setChecked(false);

                }

            }
        }
        //winList.get(buttonView.getId()).toggle();

    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onResume() {
        // mainLayout.removeAllViews();
        // win.removeAllViews();
        // buttonLayout.removeAllViews();
        // displayLayout();
        if (!flag) {

            for (int i = 0; i < playerNo; i++) {
                payment.get(i).setText("0");
                pointList.get(i).setText("");
                pointList.get(i).setHint("0-100");
                seenList.get(i).setChecked(false);
                payment.get(i).setText("0");
                result.get(i).setText("0");
            }
            // if (win.getCheckedRadioButtonId() != -1) {
            // winList.get(win.getCheckedRadioButtonId()).setChecked(false);;
            // }
            action_add.setEnabled(false);

            save_menu.setEnabled(false);
            //save_menu.setTextColor(getResources().getColor(R.color.save_menu));
            action_add.setIcon(R.drawable.ic_backup_black_24dp);

        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void calculatePenenty() {
        // this.pnt1=this.pnt2=this.pnt3=this.pnt4=this.pnt5=0;
        // testDisplay.setText(String.valueOf(win.getCheckedRadioButtonId()));
        for (int i = 0; i < playerNo; i++) {
            pnt[i] = 0;
        }
        for (int i = 0; i < playerNo; i++) {
            if (seenList.get(i).isChecked() || lessPoints[i] || penalty15[i]) {
                if (i == index || lessPoints[i] || penalty15[i]) {
                    pnt[i] = 0;
                } else {
                    pnt[i] = this.seen;
                }
            } else {
                pnt[i] = this.unseen;

            }
            // testDisplay.setText(String.valueOf(pnt[i]));
            // for ( i = 0; i < 1000000000; i++) ;
        }
        // testDisplay.setText(String.valueOf(pnt[1]));
        // if(seen1.isChecked()){
        // if(win.getCheckedRadioButtonId()==R.id.win1){
        // this.pnt1=0;
        // }else{
        // this.pnt1=this.seen;
        // }
        // }
        // else{
        // this.pnt1=this.unseen;
        // }
        // if(seen2.isChecked()){
        // if(win.getCheckedRadioButtonId()==R.id.win2){
        // this.pnt2=0;
        // }else{
        // this.pnt2=this.seen;
        // }
        // }
        // else{
        // this.pnt2=this.unseen;
        // }
        // if(seen3.isChecked()){
        // if(win.getCheckedRadioButtonId()==R.id.win3){
        // this.pnt3=0;
        // }else{
        // this.pnt3=this.seen;
        // }
        // }
        // else{
        // this.pnt3=this.unseen;
        // }
        // if(seen4.isChecked()){
        // if(win.getCheckedRadioButtonId()==R.id.win4){
        // this.pnt4=0;
        // }else{
        // this.pnt4=this.seen;
        // }
        // }
        // else{
        // this.pnt4=this.unseen;
        // }
        // if(seen5.isChecked()){
        // if(win.getCheckedRadioButtonId()==R.id.win5){
        // this.pnt5=0;
        // }else{
        // this.pnt5=this.seen;
        // }
        // }
        // else{
        // this.pnt5=this.unseen;
        // }
    }

    public void calculatePayment() {
        boolean penaltyFlag = prefs.getBoolean("flag", false);
        String penaltyList = "";
        int[] pListInt = {-1, -1, -1, -1, -1, -1};
        if (penaltyFlag) {
            penaltyList = prefs.getString(PREFKEY, null);
            String[] pList = penaltyList.split(",");
            editor.putBoolean("flag", false);
            editor.commit();
            // Toast.makeText(this,penaltyList,Toast.LENGTH_LONG).show();

        }
        int i, j;
        for (i = 0; i < playerNo; i++) {
            pmt[i] = 0;
        }

        for (i = 0; i < playerNo; i++) {
            if (penaltyList.contains("" + i)) {
                total += unseen;
            }
        }

        // testDisplay.setText(String.valueOf(pnt[1]));
        for (i = 0; i < playerNo; i++) {
            if (i != index && !penalty15[i]) {
                pmt[i] = pt[i] * playerNo - total - pnt[i];
            } else {
                pmt[i] = -total;
            }
            if (penaltyList.contains("" + i)) {
                pmt[i] = -unseen;
            }
        }
        for (i = 0; i < playerNo; i++) {
            if (i == index) {
                // testDisplay.setText("Player"+i+"won");
                pmt[i] = 0;
                for (j = 0; j < playerNo; j++) {
                    if (i != j) {
                        pmt[i] += -pmt[j];
                    }

                }
            }
            // testDisplay.setText(String.valueOf(pmt[i]));
        }

        // testDisplay.setText(String.valueOf(pmt[0]));
        // this.pmt1=this.pmt2=this.pmt3=this.pmt4=this.pmt5=0;
        // switch(win.getCheckedRadioButtonId()){
        // case R.id.win1:
        // this.pmt2=this.pt2*this.playerNo-this.total-this.pnt2;
        // this.pmt3=this.pt3*this.playerNo-this.total-this.pnt3;
        // this.pmt4=this.pt4*this.playerNo-this.total-this.pnt4;
        // this.pmt5=this.pt5*this.playerNo-this.total-this.pnt5;
        // this.pmt1=-this.pmt2-this.pmt3-this.pmt4-this.pmt5;
        // break;
        //
        // case R.id.win2:
        // this.pmt1=this.pt1*this.playerNo-this.total-this.pnt1;
        // this.pmt3=this.pt3*this.playerNo-this.total-this.pnt3;
        // this.pmt4=this.pt4*this.playerNo-this.total-this.pnt4;
        // this.pmt5=this.pt5*this.playerNo-this.total-this.pnt5;
        // this.pmt2=-this.pmt1-this.pmt3-this.pmt4-this.pmt5;
        //
        // break;
        //
        // case R.id.win3:
        // this.pmt2=this.pt2*this.playerNo-this.total-this.pnt2;
        // this.pmt1=this.pt1*this.playerNo-this.total-this.pnt1;
        // this.pmt4=this.pt4*this.playerNo-this.total-this.pnt4;
        // this.pmt5=this.pt5*this.playerNo-this.total-this.pnt5;
        // this.pmt3=-this.pmt2-this.pmt1-this.pmt4-this.pmt5;
        // break;
        //
        // case R.id.win4:
        // this.pmt2=this.pt2*this.playerNo-this.total-this.pnt2;
        // this.pmt3=this.pt3*this.playerNo-this.total-this.pnt3;
        // this.pmt1=this.pt1*this.playerNo-this.total-this.pnt1;
        // this.pmt5=this.pt5*this.playerNo-this.total-this.pnt5;
        // this.pmt4=-this.pmt2-this.pmt3-this.pmt1-this.pmt5;
        //
        // break;
        //
        // case R.id.win5:
        // this.pmt2=this.pt2*this.playerNo-this.total-this.pnt2;
        // this.pmt3=this.pt3*this.playerNo-this.total-this.pnt3;
        // this.pmt4=this.pt4*this.playerNo-this.total-this.pnt4;
        // this.pmt1=this.pt1*this.playerNo-this.total-this.pnt1;
        // this.pmt5=-this.pmt2-this.pmt3-this.pmt4-this.pmt1;
        // break;
        //
        // }
    }

    public void setPayment() {
        for (int i = 0; i < playerNo; i++) {
            result.get(i).setText(String.valueOf(pmt[i]));
            payment.get(i).setText(String.valueOf(pmt[i] * point));
            // temp = score.get(i);
            // temp.add(pmt[i]);
            // score.put(i, temp);
        }
        // testDisplay.setText(score.get(0).toString());
        // temp = score.get(0);
        // temp.add(pmt[0]);
        // score.remove(0);
        // score.put(0, temp);
        // score.get(0).add(pmt[0]);
        // score.get(1).add(pmt[1]);
        // score.get(2).add(pmt[2]);
        // total1.setText(String.valueOf(this.pmt1));
        // total2.setText(String.valueOf(this.pmt2));
        // total3.setText(String.valueOf(this.pmt3));
        // total4.setText(String.valueOf(this.pmt4));
        // total5.setText(String.valueOf(this.pmt5));
    }

}

