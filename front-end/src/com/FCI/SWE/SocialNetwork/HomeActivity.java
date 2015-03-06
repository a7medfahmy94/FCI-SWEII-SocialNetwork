package com.FCI.SWE.SocialNetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	TextView helloTextView;
    Button logout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle extras = getIntent().getExtras();
		String status = extras.getString("status");
		String name = "",welcome="Hello";
		
		if(extras.containsKey("name")){
			name = extras.getString("name");
			welcome = "Welcome " + name;
		}
		helloTextView = (TextView) findViewById(R.id.helloText);
		String text = status + " ... " + welcome;
		helloTextView.setText(text);
        //edited by rania sayed
        logout =  (Button)findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View arg0) {
                                          // TODO Auto-generated method stub
                                          Intent logoutIntent = new Intent(getApplicationContext(),MainActivity.class);
                                          startActivity(logoutIntent);
                                      }
                                  }

        );
	}

}
