package mynotes.pawanjotsingh.com.mynotes.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mynotes.pawanjotsingh.com.mynotes.models.NoteModel;


/**
 * Created by Pawanjot on 8/9/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Notes.db";
    private static final String TABLE_NAME = "myNotes";

    private static final String COLUMN_1 = "id";
    private static final String COLUMN_2 = "title";
    private static final String COLUMN_3 = "content";
    private static final String COLUMN_4 = "tag";
    private static final String COLUMN_5 = "date";
    private static final String COLUMN_6 = "color";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        SQL query to create database
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_2 + " TEXT," + COLUMN_3 + " TEXT," + COLUMN_4 + " TEXT," + COLUMN_5 + " TEXT," + COLUMN_6 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        SQL query to update or upgrade database after dropping the database first
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //        Method to insert data into database with ContentValues class and object
    public boolean insertNote(NoteModel noteModel) {

        try {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
//            getting the values put in by the users and storing them
            contentValues.put(COLUMN_2, noteModel.getTitle());
            contentValues.put(COLUMN_3, noteModel.getContent());
            contentValues.put(COLUMN_4, noteModel.getTag());
            contentValues.put(COLUMN_5, noteModel.getDate());
            contentValues.put(COLUMN_6, noteModel.getColor());

            // inserting values and creating note with those values
            long result = database.insert(TABLE_NAME, null, contentValues);
            if (result != -1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //        Method to retrieve values from the database
    public List<NoteModel> getAllData() {

        List<NoteModel> noteModelList = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        // SQL query to retrieve data
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        // cursor is a selector that starts from the top and goes to the bottom
        while (cursor.moveToNext()) {
            NoteModel noteModel = new NoteModel();
            // retrieves and sets the values that were stored in the database
            noteModel.setId(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
            noteModel.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
            noteModel.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
            noteModel.setTag(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
            noteModel.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
            noteModel.setColor(cursor.getInt(cursor.getColumnIndex(COLUMN_6)));

            // creating the list of notes
            noteModelList.add(noteModel);
        }
        // it is necessary to close the cursor manually because its object remains in the memory utilising memory
        cursor.close();
        // it is necessary to close the database manually because its object remains in the memory utilising memory
        database.close();
        return noteModelList;
    }

    //        Method to edit values of notes and storing them with the help of ContentValues class and object
    public boolean editNote(NoteModel noteModel) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // gets the values being edited
        contentValues.put(COLUMN_1, noteModel.getId());
        contentValues.put(COLUMN_2, noteModel.getTitle());
        contentValues.put(COLUMN_3, noteModel.getContent());
        contentValues.put(COLUMN_4, noteModel.getTag());
        contentValues.put(COLUMN_5, noteModel.getDate());
        contentValues.put(COLUMN_6, noteModel.getColor());

        // updating the database with edited notes
        database.update(TABLE_NAME, contentValues, COLUMN_1 + " = " + Integer.parseInt(noteModel.getId()), null);

        return true;
    }

    //        Method to delete notes from database
    public void deleteNote(int id) {

        SQLiteDatabase database = this.getWritableDatabase();
        // deleting the note with the help of ID
        database.delete(TABLE_NAME, COLUMN_1 + " = " + id, null);
    }
}