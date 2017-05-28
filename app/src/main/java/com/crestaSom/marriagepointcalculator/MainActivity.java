package com.crestaSom.marriagepointcalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.testappv4.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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

public class MainActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener,
		android.widget.CompoundButton.OnCheckedChangeListener {

	LinearLayout mainLayout, newLayout;
	RelativeLayout buttonLayout;
	TextView player, testDisplay;
	EditText pointsEarned;
	CheckBox seens;
	RadioButton wins;
	Button calculate, save;
	EditText p1, p2, p3, p4, p5;
	int[] pt;
	int[] pmt;
	int[] pnt;
	String[] playerName;
	String test = "";
	Intent recv, nextIntent;
	List<CheckBox> seenList;
	List<EditText> pointList;
	int []lessPoints;
	List<TextView> result, payment;
	List<RadioButton> winList;
	ArrayList<String> players;
	TextView total1, total2, total3, total4, total5;
	int seen, unseen, total = 0, playerNo = 6, point,gameType;
	boolean flag = true;
	RadioGroup win;
	CheckBox seen1, seen2, seen3, seen4, seen5;
	Map<Integer, ArrayList<Integer>> score;
	// int [][]arr;
	ArrayList<Integer> temp;
	SharedPreferences prefs;

	int index=-1;

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			score = new HashMap<Integer, ArrayList<Integer>>();
			prefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			temp = new ArrayList<Integer>();
			// arr=new int[5][25];
			playerName = new String[6];
			pnt = new int[6];
			pt = new int[6];
			pmt = new int[6];
			lessPoints=new int[6];
			players = new ArrayList<String>();
			seenList = new ArrayList<CheckBox>();
			winList = new ArrayList<RadioButton>();
			pointList = new ArrayList<EditText>();
			result = new ArrayList<TextView>();
			payment = new ArrayList<TextView>();
			testDisplay = (TextView) findViewById(R.id.textView1);
		//	win = (RadioGroup) findViewById(R.id.radioGroup);
			if (savedInstanceState != null) {
				players = savedInstanceState.getStringArrayList("players");
			} else {
				recv = getIntent();
				Bundle bundle = recv.getExtras();
				players = bundle.getStringArrayList("playerList");
			}
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
			displayLayout();
		} catch (NullPointerException ex) {
			Log.e("Null pointer exception", ex.getMessage());

		}
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
		menu.add(Menu.NONE, 1, Menu.NONE, "Less Point");
		menu.add(Menu.NONE, 2, Menu.NONE, "15 Point Penalty");
		super.onCreateContextMenu(menu, v, menuInfo);
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case 1:
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		    int nameId = (int)info.id;
			//lessPoints[nameId]=1;
			Toast.makeText(this, String.valueOf(nameId), Toast.LENGTH_SHORT).show();

		case 2:
			Toast.makeText(this, "Marked", Toast.LENGTH_LONG).show();
		}

		return super.onContextItemSelected(item);
	}

	
	
	
	

	void displayLayout() {
		final float scale = this.getResources().getDisplayMetrics().density;
		int pixels = (int) (40 * scale + 0.5f);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0,0,0,15);

		for (int i = 0; i < playerNo; i++) {
			newLayout = new LinearLayout(this);
			newLayout.setLayoutParams(params);
			newLayout.setOrientation(LinearLayout.HORIZONTAL);
			int minHeight = (int) (15 * scale + 0.5f);
			newLayout.setMinimumHeight(minHeight);

			wins = new RadioButton(this);
			wins.setId(i);
			wins.setOnCheckedChangeListener(this);
			wins.setHeight(pixels);
			wins.setGravity(Gravity.CENTER);
			winList.add(wins);
			newLayout.addView(wins);
			player = new TextView(this);
			player.setText(players.get(i));
			player.setId(i);
			player.setBackgroundResource(R.drawable.rounded_border);
			registerForContextMenu(player);
			player.setTextColor(Color.BLACK);
			player.setGravity(Gravity.CENTER);
			player.setTextAppearance(getApplicationContext(),
					R.style.text_style);

			pixels = (int) (75 * scale + 0.5f);
			player.setWidth(pixels);
			player.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			newLayout.addView(player);

			seens = new CheckBox(this);
			seens.setId(i);
			seens.setGravity(Gravity.CENTER);
			seenList.add(seens);
			newLayout.addView(seens);

			pointsEarned = new EditText(this);
			pointsEarned.setWidth(pixels);
			pointsEarned.setBackgroundResource(R.drawable.rounded_border);
			pointsEarned.setId(i);

			pointsEarned.setInputType(InputType.TYPE_CLASS_NUMBER);
			pointsEarned
					.setFilters(new InputFilter[] { new FilterInput(0, 60) });
			pointsEarned.setHint("0-60");
			pointsEarned.setHintTextColor(Color.DKGRAY);
			pointsEarned.setGravity(Gravity.CENTER);
			// pointsEarned.setBackgroundResource(R.drawable.rounded_edittext);
			pixels = (int) (60 * scale + 0.5f);
			pointsEarned.setWidth(pixels);
			// int right=(int)(15*scale+0.5f);
			// pointsEarned.setPadding(0, 0, right, 0);
			pointList.add(pointsEarned);

			newLayout.addView(pointsEarned);

			player = new TextView(this);
			player.setText("0");
			player.setTextAppearance(getApplicationContext(),
					R.style.text_style);
			player.setTextColor(Color.BLACK);
			pixels = (int) (50 * scale + 0.5f);
			player.setWidth(pixels);
			player.setGravity(Gravity.CENTER);
			player.setId(i);
			result.add(player);
			newLayout.addView(player);

			player = new TextView(this);
			player.setText("0");
			player.setTextColor(Color.BLACK);
			player.setTextAppearance(getApplicationContext(),
					R.style.text_style);
			pixels = (int) (50 * scale + 0.5f);
			player.setWidth(pixels);
			player.setId(i);
			player.setGravity(Gravity.CENTER);
			payment.add(player);
			newLayout.addView(player);
			newLayout.setBackgroundResource(R.drawable.rounded_layout);
			newLayout.setPadding(0,25,0,25);


			mainLayout.addView(newLayout);

		}
		RelativeLayout.LayoutParams parm=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		parm.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		calculate = new Button(this);
		calculate.setText("Calculate");
		calculate.setTextColor(Color.WHITE);
		calculate.setId(0);
		pixels = (int) (100 * scale + 0.5f);
		calculate.setWidth(pixels);
		calculate.setBackgroundResource(R.drawable.custom_button);
		calculate.setOnClickListener(this);
		buttonLayout.addView(calculate,parm);

		parm=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		parm.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


		save = new Button(this);
		save.setText("Save");
		save.setBackgroundResource(R.drawable.custom_button);
		save.setWidth(pixels);
		save.setTextColor(Color.WHITE);
		save.setId(1);
		save.setOnClickListener(this);
		save.setEnabled(false);
		buttonLayout.addView(save,parm);
		mainLayout.addView(buttonLayout);
		mainLayout.addView(new TextView(this));
		// mainLayout.addView(win);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 1, 0, "View Score");
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
			Intent intent = new Intent(getApplicationContext(), ScoreCard.class);
			for (int i = 0; i < playerNo; i++) {
				// score.get(i).add(pmt[i]*point);
				intent.putIntegerArrayListExtra("Score" + i, score.get(i));
			}
			intent.putStringArrayListExtra("playerList", players);
			startActivity(intent);
		} else if (id == 2) {
			startActivity(new Intent(getApplicationContext(),
					HelpActivity.class));
		} else if (id == 3) {
			startActivity(new Intent(getApplicationContext(),
					AboutActivity.class));
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

		for(int x=0;x<winList.size();x++){
			if(winList.get(x).isChecked()){
				index=x;
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
					if (seenList.get(i).isChecked()|| index == winList.get(i).getId() || gameType==1) {
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

			}
			else if (v.getId() == 1) {

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
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		Toast.makeText(getApplication(),""+checkedId,Toast.LENGTH_SHORT).show();


	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if(isChecked) {
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
				pointList.get(i).setHint("0-60");
				seenList.get(i).setChecked(false);
				payment.get(i).setText("0");
				result.get(i).setText("0");
			}
			// if (win.getCheckedRadioButtonId() != -1) {
			// winList.get(win.getCheckedRadioButtonId()).setChecked(false);;
			// }
			save.setEnabled(false);
		}
		super.onResume();
	}

	public void calculatePenenty() {
		// this.pnt1=this.pnt2=this.pnt3=this.pnt4=this.pnt5=0;
		// testDisplay.setText(String.valueOf(win.getCheckedRadioButtonId()));
		for (int i = 0; i < playerNo; i++) {
			pnt[i] = 0;
		}
		for (int i = 0; i < playerNo; i++) {
			if (seenList.get(i).isChecked()) {
				if (i==index) {
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
		int i, j;
		for (i = 0; i < playerNo; i++) {
			pmt[i] = 0;
		}
		// testDisplay.setText(String.valueOf(pnt[1]));
		for (i = 0; i < playerNo; i++) {
			if (i!=index) {
				pmt[i] = pt[i] * playerNo - total - pnt[i];
			}
		}
		for (i = 0; i < playerNo; i++) {
			if (i==index) {
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
