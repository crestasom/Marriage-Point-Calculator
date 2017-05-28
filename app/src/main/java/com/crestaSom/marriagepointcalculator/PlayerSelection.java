package com.crestaSom.marriagepointcalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.example.testappv4.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerSelection extends Activity implements OnClickListener {

	private long mBackPressed;
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	TextView txtPlayer, testDisplay;
	EditText myEditText, playerNo;
	Button b,next;
	boolean flag=true;
	LinearLayout myLayout, hlayout;
	List<EditText> myedittext;
	ArrayList<String> playerLists;
	// String[] playerList;
	int player;
	String test = "";
	Intent nextIntent;
	int maxLength;
	
	
	private boolean checkFile(){
		
		File text = new File(Environment.getExternalStorageDirectory(),
					"data/marriage point calculator/score.txt");
		if(text.exists()){
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getPlayerList(){
		ArrayList<String> players=new ArrayList<String>();
File root = new File(Environment.getExternalStorageDirectory(), "data/marriage point calculator");
        
        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, "players.txt");
        FileInputStream in=null;
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
		String a[]={"0","0","0","0","0"};
		try {
			while ((str=myReader.readLine()) != null) {
				a=new String[5];
				//tempList.add(Integer.parseInt(str));
				//int tmp;
				//display.setText(str);
				a=str.split(",");
				for (int i = 0; i < a.length; i++) {
					players.add(a[i]);
				}
				
				myReader.close();
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//	display.setText(e.getMessage());
		}
		
		return players;
	}

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_selection);
		setTitle("Marriage Point Calculator");
		boolean check=checkFile();
		if(check){
			flag=false;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		      alertDialogBuilder.setMessage("Resume Previous Game?");
//		      super.onBackPressed();
		      alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface arg0, int arg1) {

		 			playerLists=getPlayerList();
		 			player=playerLists.size();
		 			Intent nextIntent = new Intent(getApplicationContext(),
		 					MainActivity.class);
		 			nextIntent.putStringArrayListExtra("playerList", playerLists);
		 			nextIntent.putExtra("playerNo", player);
		 			// nextIntent.put
		 			startActivity(nextIntent);
		         }
		      });
		      
		      alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		        //    finish();
		        	 
		        	 File text = new File(Environment.getExternalStorageDirectory(),
			 					"data/marriage point calculator/score.txt");
			 			 text.delete();
			 			 text = new File(Environment.getExternalStorageDirectory(),
			 					"data/marriage point calculator/players.txt");
			 			 text.delete();
		        	 
		         }
		      });
		      
		      AlertDialog alertDialog = alertDialogBuilder.create();
		      alertDialog.show();
			
		
		}
			//myEditText=(EditText)findViewById(R.id.playerNo);
			//myEditText.setBackgroundResource(R.drawable.rounded_edittext);
			myedittext = new ArrayList<EditText>();
			playerLists = new ArrayList<String>();
			// playerList = new String[5];
			playerNo = (EditText) findViewById(R.id.playerNo);
			playerNo.setHint("2-5");
			playerNo.setHintTextColor(Color.DKGRAY);
			playerNo.requestFocus();
			playerNo.setFilters(new InputFilter[] { new FilterInput("2", "6") }); //input filter implemented here
			testDisplay = (TextView) findViewById(R.id.textView1);
			b = (Button) findViewById(R.id.submit);
			myLayout = (LinearLayout) findViewById(R.id.playerNames);
			b.setOnClickListener(this);	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			flag=true;
			Intent menuIntent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivityForResult(menuIntent, 100);
		}else if(id==2){
			flag=true;
			startActivityForResult(new Intent(getApplicationContext(), HelpActivity.class),100);
		}else if(id==3){
			flag=true;
			startActivityForResult(new Intent(getApplicationContext(), AboutActivity.class),100);
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onBackPressed()
	{
	    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) 
	    { 
	    	System.runFinalizersOnExit(true);
//	    	android.os.Process.killProcess(android.os.Process.myPid());
	        super.onBackPressed(); 
	        
	        
	    }
	    else { Toast.makeText(getBaseContext(), "Are you sure you wany to exit?Tap back button one more time exit.", Toast.LENGTH_SHORT).show(); }

	    mBackPressed = System.currentTimeMillis();
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(!flag){
		finish();
		}
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.submit) {
			myLayout.removeAllViews();
			try{
			player = Integer.parseInt(playerNo.getText().toString());
			} catch (NumberFormatException e) {
				Toast.makeText(getApplicationContext(),
						"Please Enter Number of Player",
						Toast.LENGTH_SHORT).show();
				// pt[i]=0;
				return;

			}
			
			//b.setText("Continue");
			int i = 0;
			txtPlayer = new TextView(getApplicationContext());

			txtPlayer.setTextAppearance(getApplicationContext(), R.style.text_style);
			txtPlayer.setText("Enter Names of Players");
			 txtPlayer.setTextColor(Color.BLACK);
			myLayout.addView(txtPlayer);
			final float scale = this.getResources().getDisplayMetrics().density;
			for (i = 0; i < player; i++) {
				
				int pixels = (int) (70 * scale + 0.5f);
				int pixel = (int) (50 * scale + 0.5f);
				hlayout = new LinearLayout(getApplicationContext());
				hlayout.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				hlayout.setOrientation(LinearLayout.HORIZONTAL);
				txtPlayer = new TextView(getApplicationContext());
				//txtPlayer=(TextView)findViewById(R.id.myTextView);
				txtPlayer.setWidth(pixels);
				txtPlayer.setHeight(pixel);
				txtPlayer.setTextAppearance(getApplicationContext(), R.style.text_style);
				//pixels = (int) (35 * scale + 0.5f);
				//txtPlayer.setHeight(pixels);
				txtPlayer.setText("Player" + String.valueOf(i + 1));
				// txtPlayer.setTextSize(50);
				 txtPlayer.setTextColor(Color.BLACK);
				hlayout.addView(txtPlayer);
				myEditText = new EditText(getApplicationContext());
				pixels = (int) (200 * scale + 0.5f);
				myEditText.setWidth(pixels);
				myEditText.setGravity(Gravity.CENTER_HORIZONTAL);
				pixels = (int) (25 * scale + 0.5f);
				myEditText.setHeight(pixels);
				myEditText.setId(i);
				myEditText.setTextColor(Color.BLACK);
				myEditText.setBackgroundResource(R.drawable.rounded_layout);
				myedittext.add(myEditText);
				myEditText.setSingleLine();
				myEditText.setCursorVisible(true);
				//myEditText.setHeight(pixel);
				//myEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				maxLength = 7;
				InputFilter[] fArray = new InputFilter[1];
				fArray[0] = new InputFilter.LengthFilter(maxLength);
				myEditText.setFilters(fArray);
				hlayout.addView(myEditText);
//				hlayout.setBackgroundResource(R.drawable.rounded_layout);
				myLayout.addView(hlayout);

			}
			myedittext.get(0).requestFocus();
			next=new Button(this);
			next.setBackgroundResource(R.drawable.red_button);
			next.setText("Continue");
			next.setBackgroundResource(R.drawable.custom_button);
			next.setTextColor(Color.WHITE);
			//next.setPadding(0, 5, 0, 0);
			next.setOnClickListener(this);
			next.setId(2);
			int bottom=(int) (30 * scale + 0.5f);
			next.setGravity(Gravity.CENTER);
			next.setWidth(bottom);
			next.setHeight(bottom);
			
			//next.setPadding(0, 0, 0, bottom);
			myLayout.addView(next);
			myLayout.addView(new TextView(this));

			// for (i = 0; i < player; i++) {
			// test += myedittext.get(i).getText().toString();
			//
			// }
			// testDisplay.setText(test);
		} else if (v.getId()==2) {
			for (int i = 0; i < player; i++) {
				// playerList[i] = myedittext.get(i).getText().toString();
				if (myedittext.get(i).getText().toString().compareTo("") == 0) {
					int n = i + 1;
					playerLists.add("P" + n);
				} else {
					playerLists.add(myedittext.get(i).getText().toString());
					// test+=myedittext.get(i).getText().toString();
				}
			}
			// testDisplay.setText(playerList[0]);
			Intent nextIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			flag=false;
			nextIntent.putStringArrayListExtra("playerList", playerLists);
			nextIntent.putExtra("playerNo", player);
			// nextIntent.put
			startActivity(nextIntent);

		}

	}
}
