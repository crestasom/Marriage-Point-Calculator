package com.crestaSom.marriagepointcalculator;


import java.io.File;

import com.example.testappv4.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Welcome_Activity extends Activity implements OnClickListener {
	Button newGame,resumeGame;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		newGame=(Button)findViewById(R.id.newGame);
		resumeGame=(Button)findViewById(R.id.resumeGame);
		newGame.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//return true;
			Intent menuIntent=new Intent(getApplicationContext(),SettingsActivity.class);
			startActivity(menuIntent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.newGame){
		Intent nextIntent=new Intent(getApplicationContext(),PlayerSelection.class);
		File text = new File(Environment.getExternalStorageDirectory(),
					"data/marriage point calculator/score.txt");
		if(text.exists()){
			text.delete();
		}
		//nextIntent.putExtra("from", btn.getText());
		startActivity(nextIntent);
		}else if(v.getId()==R.id.newGame){
			
		}
	}
}
