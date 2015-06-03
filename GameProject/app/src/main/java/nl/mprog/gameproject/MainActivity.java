package nl.mprog.gameproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_button = (Button) findViewById(R.id.StartButton);
        start_button.setOnClickListener(this);

        Button leader_button = (Button) findViewById(R.id.LeaderButton);
        leader_button.setOnClickListener(this);
    }

    public void onClick(View v) {

        // Checking which button is clicked and giving it extra information for
        // the next activity
        switch (v.getId()) {
            case R.id.StartButton:
                Intent intent1 = new Intent(this, CharacterActivity.class);
                startActivity(intent1);
                break;

            case R.id.LeaderButton:
                Intent intent2 = new Intent(this, LeaderActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
