package mynotes.pawanjotsingh.com.mynotes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import android.graphics.drawable.Icon;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mynotes.pawanjotsingh.com.mynotes.R;
import mynotes.pawanjotsingh.com.mynotes.adapters.RecyclerAdapter;
import mynotes.pawanjotsingh.com.mynotes.dbhelper.DataBaseHelper;
import mynotes.pawanjotsingh.com.mynotes.models.NoteModel;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    DataBaseHelper dataBaseHelper;
    Toolbar toolbar;
    Button button;

    public SharedPreferences sharedPreferences;
    boolean isAppInstalled=true;

    RecyclerView recyclerView;
    List<NoteModel> noteModelList = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Nougat feature. this method is called after long press on the icon of the app
        shortcutNewNote();


//        Code to create a shortcut of the app on installation
        sharedPreferences=getSharedPreferences("isAppInstalled",0);
        isAppInstalled=sharedPreferences.getBoolean("isAppInstalled",true);
        if(isAppInstalled){

            Intent intentShortCut=new Intent(getApplicationContext(),MainActivity.class);
            intentShortCut.setAction(Intent.ACTION_MAIN);

            Intent intent=new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intentShortCut);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"My Notes");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.mipmap.ic_launcher_round));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(intent);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isAppInstalled",false);
            editor.apply();
        }


        toolbar = (Toolbar) findViewById(R.id.idToolbarMain);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        recyclerAdapter = new RecyclerAdapter(noteModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);


        dataBaseHelper = new DataBaseHelper(this);
        button = (Button) findViewById(R.id.idButtonCreateNote);

        // functionality for CREATE BUTTON
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this method is called when CREATE NOTE button is clicked
                createNote();
            }
        });


    }

    public void shortcutNewNote(){


//        code to create nougat exclusive dynamic shortcut
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        Intent intent=new Intent(this,InsertNoteActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this,"shortcut")
                .setShortLabel("New Note")
                .setLongLabel("Create new note")
                .setIcon(Icon.createWithResource(this,R.mipmap.ic_launcher_note_add))
                .setIntent(intent)
                .build();

        shortcutManager.setDynamicShortcuts(Collections.singletonList(shortcutInfo));
    }

    // this method is called when CREATE NOTE button is called
    public void createNote() {
        Intent intent = new Intent(this, InsertNoteActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        showData();

        super.onResume();
    }

    public void showData() {

        // getAllData is defined and declared in the DataBaseHelper class
        List<NoteModel> list = dataBaseHelper.getAllData();
        if (list.size() == 0) {
            // if list is empty then this toast pops up
            Toast.makeText(this, "There is nothing here to see", Toast.LENGTH_SHORT).show();
        } else {
            // clear() clears or empties the list
            noteModelList.clear();
            // addAll() will add all data and create a fresh list
            noteModelList.addAll(list);
            // Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    // creating the search button in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflating the search menu
        getMenuInflater().inflate(R.menu.search__menu,menu);
        MenuItem search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // search method
    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<NoteModel> list= new ArrayList<>();
        for (NoteModel noteModel: noteModelList)
        {
            String tag=noteModel.getTag().toLowerCase();
            if (tag.contains(newText)){
                list.add(noteModel);
            }
        }
        //setFilter() method defined in recyclerAdapter class
        recyclerAdapter.setFilter(list);
        return true;
    }
}
