package ovsyanik.project.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ovsyanik.project.R;
import ovsyanik.project.adapter.NotesAdapter;
import ovsyanik.project.data.Notes;
import ovsyanik.project.data.User;
import ovsyanik.project.utils.GsonHelper;


public class NotesFragment extends Fragment {
    private static final String TAG = NotesFragment.class.getSimpleName();

    private RecyclerView recyclerView = null;
    private FloatingActionButton btn_addNotes;
    private TextInputEditText et_dateAction, et_title, et_description;
    private MaterialButton btn_dateAction;
    private View viewNotes = null;
    private final Calendar calendar = Calendar.getInstance();

    List<Notes> list1= new ArrayList<>();
    private List<Notes> list = null;
    private GsonHelper gsonHelper = new GsonHelper();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewNotes = getLayoutInflater().inflate(R.layout.layout_add_notes, null, false);
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        btn_addNotes = view.findViewById(R.id.btn_add_notes);
        recyclerView = view.findViewById(R.id.recycler_notes);

        btn_dateAction = viewNotes.findViewById(R.id.btn_date_action);
        et_title = viewNotes.findViewById(R.id.add_title_notes);
        et_dateAction = viewNotes.findViewById(R.id.et_date_action);
        et_description = viewNotes.findViewById(R.id.add_description_notes);

        ((MaterialTextView)getActivity().findViewById(R.id.title_main_activity)).setText("Заметки");
    }

    @Override
    public void onStart() {
        super.onStart();

        btn_addNotes.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if(viewNotes.getParent() != null) {
                ((ViewGroup) viewNotes.getParent()).removeView(viewNotes); // <- fix
            }
            builder.setView(viewNotes);
            builder.setPositiveButton("Добавить", (dialog, which) -> {
                Notes note = new Notes(
                        list.size() + 1,
                        et_title.getText().toString(),
                        et_description.getText().toString(),
                        et_dateAction.getText().toString(),
                        User.getInstance().getId()
                );
                list.add(note);
                setupAdapter();
            }).setNegativeButton("Отмена", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btn_dateAction.setOnClickListener(v ->
                new DatePickerDialog(getContext(), d_1, calendar.get( Calendar.YEAR ),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());


        list = gsonHelper.readNotes(getContext());

        setupAdapter();
    }


    @Override
    public void onPause() {
        super.onPause();

        gsonHelper.writeNotes(getContext(), list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        gsonHelper.writeNotes(getContext(), list);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case 0:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                if(viewNotes.getParent() != null) {
                    ((ViewGroup) viewNotes.getParent()).removeView(viewNotes);
                }

                Notes note1 = list1.get(item.getOrder());
                et_title.setText(note1.getTitle());
                et_description.setText(note1.getDescription());
                et_dateAction.setText(note1.getDateAction());

                builder1.setView(viewNotes);
                builder1.setPositiveButton("Изменить", (dialog, which) -> {
                    Notes note = new Notes(
                            note1.getId(),
                            et_title.getText().toString(),
                            et_description.getText().toString(),
                            et_dateAction.getText().toString(),
                            User.getInstance().getId()
                    );
                    list.set(list.indexOf(note1), note);
                    setupAdapter();
                }).setNegativeButton("Отмена", null);
                AlertDialog dialog = builder1.create();
                dialog.show();
                return true;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Вы серьезно хотите удалить?");
                builder.setPositiveButton("Да", (dialogInterface, i) -> {
                    list.remove(list1.get(item.getOrder()));
                    setupAdapter();
                });

                builder.setNegativeButton("Нет", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener d_1 = (view, year, monthOfYear, dayOfMonth ) -> {
        LocalDate date = LocalDate.of( year, monthOfYear + 1, dayOfMonth );
        et_dateAction.setText(date.toString());
    };

    public void setupAdapter() {
        list1.clear();
        for (Notes note : list) {
            if(note.getUser() == User.getInstance().getId()) {
                list1.add(note);
            }
        }

        NotesAdapter adapter = new NotesAdapter(getContext(), list1);
        recyclerView.setAdapter(adapter);
    }
}