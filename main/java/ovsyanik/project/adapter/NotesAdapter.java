package ovsyanik.project.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ovsyanik.project.R;
import ovsyanik.project.data.Notes;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Notes> notes;
    private Context context;

    public NotesAdapter(Context context, List<Notes> notes) {
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_one_notes, parent, false);
        return new NotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        Notes note = notes.get(position);

        holder.viewID = position;
        holder.tv_title.setText(note.getTitle());
        holder.tv_description.setText(note.getDescription());
        holder.tv_dateAction.setText(note.getDateAction());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        final TextView tv_title;
        final TextView tv_description;
        final TextView tv_dateAction;
        int viewID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_title = itemView.findViewById(R.id.title_one_notes);
            this.tv_dateAction = itemView.findViewById(R.id.date_action_one_notes);
            this.tv_description = itemView.findViewById(R.id.description_one_notes);
            this.viewID = -1;

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, this.viewID, "Изменить");
            menu.add(0, 1, this.viewID, "Удалить");
        }
    }
}
