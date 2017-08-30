package mynotes.pawanjotsingh.com.mynotes.activities;


import android.content.Intent;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mynotes.pawanjotsingh.com.mynotes.R;
import mynotes.pawanjotsingh.com.mynotes.dbhelper.DataBaseHelper;
import mynotes.pawanjotsingh.com.mynotes.models.NoteModel;

public class InsertNoteActivity extends AppCompatActivity {

    DataBaseHelper databasehelper;
    Toolbar toolbar;
    ImageButton imageButtonDone;
    EditText editTextTitle, editTextContent, editTextTag;
    CheckedTextView checkedTextViewRed, checkedTextViewBlue, checkedTextViewGreen, checkedTextViewWhite,checkedTextViewYellow;
    int color,colorRed,colorBlue,colorGreen,colorWhite,colorYellow;

    // colors to choose from to give to the note
    private enum Colors{RED,BLUE,GREEN,WHITE,YELLOW}
    Colors currentBackgroundColor=Colors.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert__note_);

        databasehelper = new DataBaseHelper(this);
        imageButtonDone = (ImageButton) findViewById(R.id.idImageButtonDone);
        editTextTitle = (EditText) findViewById(R.id.idEditTextTitle);
        editTextContent = (EditText) findViewById(R.id.idEditTextContent);
        editTextTag = (EditText) findViewById(R.id.idEditTextTag);

        checkedTextViewRed= (CheckedTextView) findViewById(R.id.idCheckedTextViewRed);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        checkedTextViewRed.setCheckMarkDrawable(null);
        checkedTextViewBlue= (CheckedTextView) findViewById(R.id.idCheckTextViewBlue);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        checkedTextViewBlue.setCheckMarkDrawable(null);
        checkedTextViewGreen= (CheckedTextView) findViewById(R.id.idCheckedTextViewGreen);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        checkedTextViewGreen.setCheckMarkDrawable(null);
        checkedTextViewWhite=(CheckedTextView) findViewById(R.id.idCheckedTextViewWhite);
        checkedTextViewYellow=(CheckedTextView) findViewById(R.id.idCheckedTextViewYellow);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        checkedTextViewYellow.setCheckMarkDrawable(null);


        toolbar = (Toolbar) findViewById(R.id.idToolbarInsertNote);
        setSupportActionBar(toolbar);
        //It makes the back button!
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // setting the title of the toolbar
        getSupportActionBar().setTitle("Create");

        // functionality of back button of toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // method that is called when the back button is pressed
                onBackPressed();
            }
        });

        colorRed=Color.parseColor("#FF8A8A");
        colorBlue=Color.parseColor("#8AEFFF");
        colorGreen=Color.parseColor("#9AFF8A");
        colorWhite=Color.parseColor("#ffffff");
        colorYellow=Color.parseColor("#FFE88A");


        // functionality for imageButton upon button click
        imageButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this method is called when done imageButton is clicked
                done();
            }
        });

        // if the user clicks on red checkedTextView then following happens
        checkedTextViewRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setting the background color of InsertNoteActivity
                getWindow().getDecorView().setBackgroundColor(colorRed);
                // setting the background color of cardView to red
                currentBackgroundColor=Colors.RED;
                // check mark is set to red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

                // setting the check marks to null of other checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                checkedTextViewGreen.setCheckMarkDrawable(null);
                checkedTextViewWhite.setCheckMarkDrawable(null);
                checkedTextViewYellow.setCheckMarkDrawable(null);

            }
        });

        // if the user clicks on blue checkedTextView then following happens
        checkedTextViewBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of InsertNoteActivity
                getWindow().getDecorView().setBackgroundColor(colorBlue);
                // setting the background color of cardView to blue
                currentBackgroundColor=Colors.BLUE;
                // check mark is set to blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

                // setting the check marks to null of other checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(null);
                checkedTextViewGreen.setCheckMarkDrawable(null);
                checkedTextViewWhite.setCheckMarkDrawable(null);
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on green checkedTextView then following happens
        checkedTextViewGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of InsertNoteActivity
                getWindow().getDecorView().setBackgroundColor(colorGreen);
                // setting the background color of cardView to green
                currentBackgroundColor=Colors.GREEN;
                // check mark is set to green checkTextView
                checkedTextViewGreen.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

                // setting the check marks to null of other checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                checkedTextViewRed.setCheckMarkDrawable(null);
                checkedTextViewWhite.setCheckMarkDrawable(null);
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on white checkTextView then following happens
        checkedTextViewWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of InsertNoteActivity
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                // setting the background color of cardView to white
                currentBackgroundColor=Colors.WHITE;
                // check mark is set to white checkTextView
                checkedTextViewWhite.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

                // setting the check marks to null of other checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                checkedTextViewRed.setCheckMarkDrawable(null);
                checkedTextViewGreen.setCheckMarkDrawable(null);
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on white checkTextView then following happens
        checkedTextViewYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of InsertNoteActivity
                getWindow().getDecorView().setBackgroundColor(colorYellow);
                // setting the background color of cardView to white
                currentBackgroundColor=Colors.YELLOW;
                // check mark is set to yellow checkTextView
                checkedTextViewYellow.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

                // setting the check marks to null of other checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                checkedTextViewRed.setCheckMarkDrawable(null);
                checkedTextViewGreen.setCheckMarkDrawable(null);
                checkedTextViewWhite.setCheckMarkDrawable(null);
            }
        });

    }

    // this method is called when back button is pressed
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // method to get the current date and time, when the note is created, edited or saved
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //this method is called when done imageView button is called
    public void done() {

        // switch case to determine the current background color and setting the color of cardView
        switch (currentBackgroundColor) {
            case RED: color=colorRed;
                break;
            case BLUE: color=colorBlue;
                break;
            case GREEN: color=colorGreen;
                break;
            case WHITE: color=colorWhite;
                break;
            case YELLOW:color=colorYellow;

        }

        NoteModel noteModel = new NoteModel();
        noteModel.setTitle(editTextTitle.getText().toString());
        noteModel.setContent(editTextContent.getText().toString());
        noteModel.setTag(editTextTag.getText().toString());
        noteModel.setDate(getDateTime());
        noteModel.setColor(color);

        if (editTextTitle.getText().toString().isEmpty() && editTextContent.getText().toString().isEmpty()) {
            // this toast pops up if both title and content field are left blank
            Toast.makeText(this, "Please insert a value in title or content", Toast.LENGTH_SHORT).show();
            return;
        }

        // this editNote() method is the one that is declared and defined in the DataBaseHelper class
        boolean isInserted = databasehelper.insertNote(noteModel);
        if (isInserted) {
            // this toast pops up when the note is saved successfully
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else
            // this toast pops up when the note fails to be saved
            Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();

    }

}

