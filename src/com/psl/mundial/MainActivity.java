package com.psl.mundial;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import dao.SheetsDataSource;
import dto.Sheet;

public class MainActivity extends ActionBarActivity implements TabListener {

	public final static String EXTRA_MESSAGE = "com.psl.mundial.MESSAGE";
	public final static int NUMBER_OF_ITEMS = 640;
	public final static int NUMBER_OF_ITEMS_PER_PAGE = 100;
	
	private SheetsDataSource datasource;
	
	//http://www.technotalkative.com/android-tab-bar-example-1/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     
        DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		
		datasource = new SheetsDataSource(this);
	    datasource.open();
		
		List<Sheet> sheets = datasource.getAllSheets();
		
        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(width, height, NUMBER_OF_ITEMS, NUMBER_OF_ITEMS_PER_PAGE, this, sheets, datasource);
		ViewPager pager = (ViewPager)findViewById(R.id.myViewPager);
		pager.setAdapter(pageAdapter);				
            	
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();            
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    /*
    //On click listener for the send button
    public void sendMessage(View view){
    	//Starts an activity called DisplayMessageActivity
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	//Gets the view with id edit_message
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	
    	//Catches up the message written in the text field
    	String message = editText.getText().toString();
    	
    	//attach message to the intent
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }*/
    
//    @Override
//    protected void onStart() {
//      db = dbOpener.getWritableDatabase();
//      super.onResume();
//    }
//    
    @Override
    protected void onResume() {
      datasource.open();
      super.onResume();
    }

    @Override
    protected void onPause() {
      datasource.close();
      super.onPause();
    }


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
//		Log.d("SimpleActionBarTabsActivity","tab " 
//                + String.valueOf(arg0.getPosition()) + " clicked");
		arg1.add(new Fragment(), "asdas");
		Toast.makeText(this, "Tab" + arg0.getText() + "selected", Toast.LENGTH_SHORT)
        .show();
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
