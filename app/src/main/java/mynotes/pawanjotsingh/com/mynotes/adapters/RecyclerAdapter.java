package mynotes.pawanjotsingh.com.mynotes.adapters;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mynotes.pawanjotsingh.com.mynotes.R;
import mynotes.pawanjotsingh.com.mynotes.activities.NoteDetailsActivity;
import mynotes.pawanjotsingh.com.mynotes.dbhelper.DataBaseHelper;
import mynotes.pawanjotsingh.com.mynotes.models.NoteModel;


/**
 * Created by Pawanjot on 8/9/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private DataBaseHelper dataBaseHelper;
    private Context context;
    private List<NoteModel> noteModelList;
    private int getPosition;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView textViewTitle, textViewContent, textViewTag, textViewDate;
        RelativeLayout relativeLayoutCardView;
        ImageButton imageButtonMenu;



        private RecyclerViewHolder(View view) {
            super(view);
            textViewTitle = (TextView) view.findViewById(R.id.idTextViewTitleCard);
            textViewContent = (TextView) view.findViewById(R.id.idTextViewContentCard);
            textViewTag = (TextView) view.findViewById(R.id.idTextViewTagCard);
            textViewDate = (TextView) view.findViewById(R.id.idTextViewLastModified);
            relativeLayoutCardView = (RelativeLayout) view.findViewById(R.id.idRelativeLayoutCardView);
            imageButtonMenu = (ImageButton) view.findViewById(R.id.idImageButtonMenu);

        }
    }

    public RecyclerAdapter(List<NoteModel> noteModelList, Context context) {
        this.noteModelList = noteModelList;
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        creating the View or inflating the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        // getAdapterPosition get the id and the position of the notes
        final NoteModel noteModel = noteModelList.get(holder.getAdapterPosition());
        holder.textViewTitle.setText(noteModel.getTitle());
        holder.textViewContent.setText(noteModel.getContent());

        holder.textViewTag.setSingleLine(true);
        holder.textViewTitle.setSingleLine(true);
        holder.textViewContent.setSingleLine(true);

//        code to remove commas from the tags
        String tagWithoutComma=noteModel.getTag();
        int tagLength=tagWithoutComma.length();
        String tags=tagWithoutComma.replace(","," ");

//        spannable is used to highlight the text
        Spannable spannableText=Spannable.Factory.getInstance().newSpannable(tags);
        spannableText.setSpan(new BackgroundColorSpan(Color.parseColor("#D0D0D0")),0,tagLength,0);

        holder.textViewTag.setText(spannableText);

        holder.textViewDate.setText(noteModel.getDate());


//        code to check if there is value of color stored in database for every particular note and giving each note its specific color, if no value is there then default value is applied which is White color
        final int colour = noteModel.getColor();
        if (colour != 0) {
            holder.relativeLayoutCardView.setBackgroundColor(colour);
        } else {
            holder.relativeLayoutCardView.setBackgroundColor(Color.WHITE);
        }

        holder.relativeLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this ensures that the correct card gets clicked and not any other
                getPosition = holder.getAdapterPosition();
                // this method runs when a specific card is clicked
                showNote(noteModelList.get(holder.getAdapterPosition()));
            }
        });


        // functionality on imageButton click. options button on every card
        holder.imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imageButtonMenu);
                // inflating the menu
                popupMenu.inflate(R.menu.context_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        // switch case for options in the menu
                        switch (item.getItemId()) {

                            // code to share the a particular note's title and content
                            case R.id.idMenuShare:
                                String title=noteModel.getTitle();
                                String content=noteModel.getContent();
                                Intent intentShare = new Intent();
                                intentShare.setAction(Intent.ACTION_SEND);
                                intentShare.setType("text/plain");

                                String titleAndContent="Title: "+title+"\n Content: "+content;
                                intentShare.putExtra(Intent.EXTRA_TEXT,titleAndContent);

                                context.startActivity(intentShare);
                                break;

                            // code to delete a particular note
                            case R.id.idMenuDelete:
                                getPosition = holder.getAdapterPosition();
                                // removeAt() method to delete note
                                removeAt(noteModelList.get(holder.getAdapterPosition()).getId());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    // this method is called when delete option is selected from the menu
    private void removeAt(String id) {

        // deleteNote() is defined and declared in the DataBaseHelper class
        dataBaseHelper.deleteNote(Integer.parseInt(id));
        // removing the note from the list
        noteModelList.remove(getPosition);
        // refreshing the list
        notifyItemRemoved(getPosition);
        // changing the size and positions of cards in the list
        notifyItemRangeChanged(getPosition, noteModelList.size());
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }


    // this methods runs when a user clicks on a specific card or note
    private void showNote(NoteModel noteModel) {

        // Intent to go to NoteDetailsActivity and passing the values
        Intent intent = new Intent(context, NoteDetailsActivity.class);

        intent.putExtra("id", noteModel.getId());
        intent.putExtra("text_title", noteModel.getTitle());
        intent.putExtra("text_content", noteModel.getContent());
        intent.putExtra("text_tag", noteModel.getTag());
        intent.putExtra("text_date", noteModel.getDate());
        intent.putExtra("color", noteModel.getColor());

        context.startActivity(intent);

    }

    // search method. declared in the MainActivity
    public void setFilter(ArrayList<NoteModel> list){
        noteModelList=new ArrayList<>();
        noteModelList.addAll(list);
        notifyDataSetChanged();
    }
}