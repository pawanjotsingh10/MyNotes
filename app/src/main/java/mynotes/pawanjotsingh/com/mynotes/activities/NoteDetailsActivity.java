package mynotes.pawanjotsingh.com.mynotes.activities;


import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.CheckedTextView;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mynotes.pawanjotsingh.com.mynotes.R;

import mynotes.pawanjotsingh.com.mynotes.dbhelper.DataBaseHelper;
import mynotes.pawanjotsingh.com.mynotes.models.NoteModel;

public class NoteDetailsActivity extends AppCompatActivity {

    DataBaseHelper  dataBaseHelper;
    FloatingActionButton floatingActionButtonEdit,floatingActionButtonSave;
    EditText editTextTitle,editTextContent,editTextTag;
    String stringTitle,stringContent,stringTag;
    TextView textViewColors;
    CheckedTextView checkedTextViewRed,checkedTextViewBlue,checkedTextViewGreen,checkedTextViewWhite,checkedTextViewYellow;
    Toolbar toolbar;
    int color,colorRed,colorBlue,colorGreen,colorWhite,colorYellow;
    String id="";
    // colors to choose from to give to the note
    private enum Colors{RED, BLUE, GREEN,WHITE,BLACK,YELLOW}
    Colors currentBackgroundColour=Colors.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        textViewColors=(TextView) findViewById(R.id.idTextViewColors);
        dataBaseHelper=new DataBaseHelper(this);
        floatingActionButtonSave=(FloatingActionButton) findViewById(R.id.idFloatingActionButtonSave);
        floatingActionButtonEdit=(FloatingActionButton) findViewById(R.id.idFloatingActionButtonEdit);

        editTextTitle=(EditText) findViewById(R.id.idEditTextTitle);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        editTextTitle.setInputType(InputType.TYPE_NULL);

        editTextContent=(EditText) findViewById(R.id.idEditTextContent);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        editTextContent.setInputType(InputType.TYPE_NULL);
        // to make the editText multi-line, if true, the text comes into single line
        editTextContent.setSingleLine(false);


        editTextTag=(EditText) findViewById(R.id.idEditTextTag);
        // setting the inputType as NULL in order to make the editText behave like textView and not take any values initially
        editTextTag.setInputType(InputType.TYPE_NULL);


        toolbar=(Toolbar) findViewById(R.id.idToolbarNoteDetails);
        setSupportActionBar(toolbar);
        // setDisplayHomeAsUpEnabled is used to create the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // setting the title of toolbar
        getSupportActionBar().setTitle("Edit");

        // setting the CheckMarks to null initially
        checkedTextViewRed=(CheckedTextView) findViewById(R.id.idCheckedTextViewRed);
        checkedTextViewRed.setCheckMarkDrawable(null);

        checkedTextViewBlue=(CheckedTextView) findViewById(R.id.idCheckTextViewBlue);
        checkedTextViewBlue.setCheckMarkDrawable(null);

        checkedTextViewGreen=(CheckedTextView) findViewById(R.id.idCheckedTextViewGreen);
        checkedTextViewGreen.setCheckMarkDrawable(null);

        checkedTextViewWhite=(CheckedTextView) findViewById(R.id.idCheckedTextViewWhite);
        checkedTextViewWhite.setCheckMarkDrawable(null);

        checkedTextViewYellow=(CheckedTextView) findViewById(R.id.idCheckedTextViewYellow);
        checkedTextViewYellow.setCheckMarkDrawable(null);



        // setting the functionality of back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this method comes into action when clicked on the back button
                onBackPressed();
            }
        });


        colorRed=Color.parseColor("#FF8A8A");
        colorBlue=Color.parseColor("#8AEFFF");
        colorGreen=Color.parseColor("#9AFF8A");
        colorWhite=Color.parseColor("#ffffff");
        colorYellow=Color.parseColor("#FFE88A");



//        getting intent from the recyclerAdapter class
        stringTitle=getIntent().getStringExtra("text_title");
        stringContent=getIntent().getStringExtra("text_content");
        stringTag=getIntent().getStringExtra("text_tag");
        id = getIntent().getStringExtra("id");
        // storing the value of color to color and giving the default value as White
        color=getIntent().getIntExtra("color", Color.WHITE);
        // setting the background color as the value stored in the variable color
        getWindow().getDecorView().setBackgroundColor(color);

        editTextTitle.setText(stringTitle);
        editTextContent.setText(stringContent);
        editTextTag.setText(stringTag);


        // functionality for FAB save button
        floatingActionButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this method comes into action when save FAB is clicked
               save();
            }
        });

        // functionality for FAB edit button
        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setting the visibility to visible after the edit FAB button is clicked
                floatingActionButtonSave.setVisibility(View.VISIBLE);
                // setting the animation to move the FAB save button to left
                Animation animationLeft=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_left);
                // initiating the animation on save FAB
                floatingActionButtonSave.startAnimation(animationLeft);

                // setting the visibility to visible after the edit FAB button is clicked
                textViewColors.setVisibility(View.VISIBLE);
                // setting the animation to move down
                Animation animationDown= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
                //initiating the animation
                textViewColors.startAnimation(animationDown);

                // setting the visibility to visible after the edit FAB button is clicked
                checkedTextViewRed.setVisibility(View.VISIBLE);
                // initiating the animation
                checkedTextViewRed.startAnimation(animationDown);

                // setting the visibility to visible after the edit FAB button is clicked
                checkedTextViewBlue.setVisibility(View.VISIBLE);
                // initiating the animation
                checkedTextViewBlue.startAnimation(animationDown);

                // setting the visibility to visible after the edit FAB button is clicked
                checkedTextViewGreen.setVisibility(View.VISIBLE);
                // initiating the animation
                checkedTextViewGreen.startAnimation(animationDown);

                // setting the visibility to visible after the edit FAB button is clicked
                checkedTextViewWhite.setVisibility(View.VISIBLE);
                // initiating the animation
                checkedTextViewWhite.startAnimation(animationDown);
                // this method comes into action when the edit FAB button is clicked

                // setting the visibility to visible after the edit FAB button is clicked
                checkedTextViewYellow.setVisibility(View.VISIBLE);
                // initiating the animation
                checkedTextViewYellow.startAnimation(animationDown);
                editNote();

            }
        });

        // if color equals red then a check mark is set to the red checkedTextView
        if(color==colorRed){
            checkedTextViewRed.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
        }
        // if color equals green then a check mark is set to the green checkedTextView
        else if(color==colorGreen){
            checkedTextViewGreen.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
        }
        // if color equals blue then a check mark is set to the blue checkedTextView
        else if(color==colorBlue){
            checkedTextViewBlue.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
        }
        // if color equals yellow then a check mark is set to the yellow checkedTextView
        else if(color==colorYellow){
            checkedTextViewYellow.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
        }else
            // if color equals white then a check mark is set to the white checkedTextView
            checkedTextViewWhite.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);

        // if the user clicks on red checkedTextView then following happens
        checkedTextViewRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of cardView to red
                currentBackgroundColour=Colors.RED;
                // check mark is set to the red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
                // setting the background color red
                getWindow().getDecorView().setBackgroundColor(colorRed);

                // setting the check mark to null of blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                // setting the check mark to null of green checkedTextView
                checkedTextViewGreen.setCheckMarkDrawable(null);
                // setting the check mark to null of white checkedTextView
                checkedTextViewWhite.setCheckMarkDrawable(null);
                // setting the check mark to null of yellow checkedTextView
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on blue checkedTextView then following happens
        checkedTextViewBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of cardView to blue
                currentBackgroundColour=Colors.BLUE;
                // check mark is set to the blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
                // setting the background color blue
                getWindow().getDecorView().setBackgroundColor(colorBlue);

                // setting the check mark to null of red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(null);
                // setting the check mark to null of green checkedTextView
                checkedTextViewGreen.setCheckMarkDrawable(null);
                // setting the check mark to null of white checkedTextView
                checkedTextViewWhite.setCheckMarkDrawable(null);
                // setting the check mark to null of yellow checkedTextView
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on green checkedTextView then following happens
        checkedTextViewGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of cardView to green
               currentBackgroundColour = Colors.GREEN;
                // check mark is set to the green checkedTextView
                checkedTextViewGreen.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
                // setting the background color green
                getWindow().getDecorView().setBackgroundColor(colorGreen);

                // setting the check mark to null of blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                // setting the check mark to null of red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(null);
                // setting the check mark to null of white checkedTextView
                checkedTextViewWhite.setCheckMarkDrawable(null);
                // setting the check mark to null of yellow checkedTextView
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on white checkedTextView then following happens
        checkedTextViewWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of cardView to white
                currentBackgroundColour=Colors.WHITE;
                // check mark is set to the white checkedTextView
                checkedTextViewWhite.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
                // setting the background color white
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);

                // setting the check mark to null of blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                // setting the check mark to null of red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(null);
                // setting the check mark to null of green checkedTextView
                checkedTextViewGreen.setCheckMarkDrawable(null);
                // setting the check mark to null of yellow checkedTextView
                checkedTextViewYellow.setCheckMarkDrawable(null);
            }
        });

        // if the user clicks on white checkedTextView then following happens
        checkedTextViewYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting the background color of cardView to white
                currentBackgroundColour=Colors.YELLOW;
                // check mark is set to the white checkedTextView
                checkedTextViewYellow.setCheckMarkDrawable(R.mipmap.ic_done_black_24dp);
                // setting the background color to yellow
                getWindow().getDecorView().setBackgroundColor(colorYellow);

                // setting the check mark to null of blue checkedTextView
                checkedTextViewBlue.setCheckMarkDrawable(null);
                // setting the check mark to null of red checkedTextView
                checkedTextViewRed.setCheckMarkDrawable(null);
                // setting the check mark to null of green checkedTextView
                checkedTextViewGreen.setCheckMarkDrawable(null);
                // setting the check mark to null of white checkedTextView
                checkedTextViewWhite.setCheckMarkDrawable(null);

            }
        });
    }

    // method that comes into action when back button is pressed
    @Override
    public void onBackPressed() {
        // intent to go back to the mainActivity
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    // method that comes into action when edit FAB button is clicked
    public void editNote(){

        // setting the input type as Text to make the edit text mutable
        editTextTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextTitle.requestFocus();

        // setting the input type as Text to make the edit text mutable
        editTextContent.setInputType(InputType.TYPE_CLASS_TEXT);
        // making the content multi-line
        editTextContent.setSingleLine(false);

        // setting the input type as Text to make the edit text mutable
        editTextTag.setInputType(InputType.TYPE_CLASS_TEXT);

    }

    // method that comes into action when save FAB button is clicked
    public void save() {

        // switch case to determine the current background color and setting the color of cardView
        switch (currentBackgroundColour) {

            case RED:
                color = colorRed;
                break;
            case BLUE:
                color = colorBlue;
                break;
            case GREEN:
                color = colorGreen;
                break;
            case WHITE:
                color = colorWhite;
                break;
            case YELLOW:
                color=colorYellow;
                break;
        }


        final NoteModel noteModel = new NoteModel();
        noteModel.setTitle(editTextTitle.getText().toString());
        noteModel.setContent(editTextContent.getText().toString());
        noteModel.setTag(editTextTag.getText().toString());
        noteModel.setId(id);
        noteModel.setDate(getDateTime());
        noteModel.setColor(color);


        // this editNote() method is the one that is declared and defined in the DataBaseHelper class
        boolean isModified=dataBaseHelper.editNote(noteModel);
        if(isModified){
            // if edits are saved successfully then this toast pops up
            Toast.makeText(this, "Modifications saved", Toast.LENGTH_SHORT).show();
          finish();

        }
        // if edits are not saved then this toast pops up
        else Toast.makeText(this, "Unable to save modifications", Toast.LENGTH_SHORT).show();
    }

    // method to get current time and date, when the note is saved,created or edited
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
