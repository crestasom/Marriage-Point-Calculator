package com.crestaSom.marriagepointcalculator;

/*import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.testappv4.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.junit.Assert.*;*/

/**
 * Created by user on 6/11/2017.
 */

//@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  /*  @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class, true, false);
    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {

        Intent newIntent=new Intent();
        ArrayList<String> name=new ArrayList<String>();
        name.add("");
        name.add("");
        name.add("");
        name.add("");
        name.add("");
        name.add("");


        newIntent.putStringArrayListExtra("playerList",name);
        activityActivityTestRule.launchActivity(newIntent);

        mainActivity = activityActivityTestRule.getActivity();
        mainActivity.seen=3;
        mainActivity.unseen=10;

        onView(withTagKey(R.id.my_checkbox)).perform(click());
//        onView(withTagKey(R.id.my_checkbox+2)).perform(click());
        onView(withTagKey(R.id.my_edit_text)).perform(typeText("5"));
        onView(withTagKey(R.id.my_radiobutton)).perform(click());
        mainActivity.lessPoints[1]=true;
        onView(withTagKey(R.id.my_edit_text+1)).perform(typeText("5"));
  //      onView(withTagKey(R.id.my_edit_text+2)).perform(typeText("5"));
       // onView(withTagKey(R.id.my_checkbox+4)).perform(click());
    }

    @Test
    public void testCalculatePenalty() {

        mainActivity.calculatePenenty();
        int penalty[] = {3, 0, 3, 10, 10, 10};
        for (int i = 0; i < mainActivity.pnt.length; i++) {
            assertEquals(penalty[i], mainActivity.pnt[i]);
        }

    }

    @Test
    public void testCalculatePayment(){
        mainActivity.calculatePayment();
        int payment[]={48,15,12,-25,-25,-25};
        for(int i=0;i<mainActivity.pnt.length;i++){
            assertEquals(payment[i], mainActivity.pmt[i]);
        }

    }

    @After
    public void tearDown() throws Exception {

        mainActivity = null;
    }*/

}