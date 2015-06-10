package nl.mprog.gameproject;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class CharacterActivity extends ListActivity {

    // Pictures i want to use of the folder mipmap-mpdi.
    Integer[] imgid = {R.mipmap.angle_sprite, R.mipmap.flyingwoman_sprite, R.mipmap.monster_girl_sprite};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character2);
        CustomListAdapter adapter = new CustomListAdapter(this, imgid);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            Intent intent0 = new Intent(this, GameActivity.class);
            intent0.putExtra("Image", imgid[position]);
            startActivity(intent0);
            finish();

        }
    }
