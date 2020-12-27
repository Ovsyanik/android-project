package ovsyanik.project.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ovsyanik.project.R;
import ovsyanik.project.adapter.AnnouncementAdapter;
import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;
import ovsyanik.project.data.Announcement;
import ovsyanik.project.sql.DeleteAnnouncement;
import ovsyanik.project.sql.GetAnnouncement;
import ovsyanik.project.sql.InsertAnnouncement;
import ovsyanik.project.utils.ValidationHelper;

public class AnnouncementsFragment extends Fragment
        implements AnnouncementAdapter.OnAnnouncementClickListener,
        AnnouncementAdapter.OnDeleteClickListener{
    private static final String TAG = AnnouncementsFragment.class.getSimpleName();
    private static final int GALLERY_REQUEST_CODE = 0;

    private FloatingActionButton btn_AddAnnouncement;
    private ShapeableImageView iv_addAnnouncement;
    private MaterialButton btn_changeImage, btn_dateStart, btn_dateEnd;
    private TextInputEditText et_dateStart, et_dateEnd, et_title, et_description;
    private View viewAnnouncement = null;
    private RecyclerView recyclerView = null;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private AnnouncementAdapter adapter = null;

    private String mCurrentPhotoPath = null;
    private byte[] image = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewAnnouncement = getLayoutInflater().inflate(R.layout.layout_add_news, null, false);
        return inflater.inflate(R.layout.fragment_announcements, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_AddAnnouncement = view.findViewById(R.id.btn_add_announcement);
        btn_changeImage = viewAnnouncement.findViewById(R.id.btn_change_image_news);
        btn_dateStart = viewAnnouncement.findViewById(R.id.btn_date_start_news);
        btn_dateEnd = viewAnnouncement.findViewById(R.id.btn_date_end_news);

        iv_addAnnouncement = viewAnnouncement.findViewById(R.id.image_view_news);

        et_dateStart = viewAnnouncement.findViewById(R.id.til_date_start);
        et_dateEnd = viewAnnouncement.findViewById(R.id.til_date_end);
        et_title = viewAnnouncement.findViewById(R.id.add_title_news);
        et_description = viewAnnouncement.findViewById(R.id.add_description_news);

        recyclerView = getView().findViewById(R.id.recycler_announcements);

        ((MaterialTextView)getActivity().findViewById(R.id.title_main_activity)).setText("Объявления");
    }


    @Override
    public void onStart() {
        super.onStart();

        btn_AddAnnouncement.setOnClickListener( v -> {
            if(User.getInstance().isInBlackList() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if (viewAnnouncement.getParent() != null) {
                    ((ViewGroup) viewAnnouncement.getParent()).removeView(viewAnnouncement);
                }

                builder.setView(viewAnnouncement);
                builder.setPositiveButton("Добавить", (dialog, which) -> {
                    if(validationField(
                            et_title.getText().toString(),
                            et_description.getText().toString(),
                            et_dateStart.getText().toString(),
                            et_dateEnd.getText().toString())) {
                        Announcement announcement = new Announcement(
                                et_title.getText().toString(),
                                image == null ? null : image,
                                et_description.getText().toString(),
                                et_dateStart.getText().toString(),
                                et_dateEnd.getText().toString()
                        );
                        InsertAnnouncement insertAnnouncement = new InsertAnnouncement();
                        insertAnnouncement.execute(announcement);
                        setupAdapter();
                    }
                }).setNegativeButton("Отмена", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(getContext(), "Вы находитесь в чёрном списке!" +
                        " Вы не можете ничего добавлять!", Toast.LENGTH_LONG).show();
            }
        });

        btn_changeImage.setOnClickListener( v -> {
            new Thread(() -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
            }).start();
        });

        btn_dateStart.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), d_1, calendar.get( Calendar.YEAR ),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        btn_dateEnd.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), d_2, calendar.get( Calendar.YEAR ),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        setupAdapter();
    }


    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST_CODE: {
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(selectedImage != null) {
                        iv_addAnnouncement.setImageBitmap(bitmap);
                    }

//                    mCurrentPhotoPath = selectedImage.toString();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                    image = stream.toByteArray();
                }
                break;
            }
        }
    }


    DatePickerDialog.OnDateSetListener d_1 = (view, year, monthOfYear, dayOfMonth ) -> {
        LocalDate date = LocalDate.of( year, monthOfYear + 1, dayOfMonth );
        et_dateStart.setText(date.toString());
    };


    DatePickerDialog.OnDateSetListener d_2 = ( view, year, monthOfYear, dayOfMonth ) -> {
        LocalDate date = LocalDate.of( year, monthOfYear + 1, dayOfMonth );
        et_dateEnd.setText(date.toString());
    };


    @Override
    public void onAnnouncementClick(Announcement announcement) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Announcement.class.getSimpleName(), announcement);
        Navigation.findNavController(getActivity(),
                R.id.nav_host_fragment).navigate(R.id.btn_go_to_one_news, bundle);
    }

    private boolean validationField(String title, String description, String dateStart, String dateEnd) {
        if(ValidationHelper.validationField(title)) {
            Toast.makeText(getContext(),
                    "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        } else if(ValidationHelper.validationField(description)) {
            Toast.makeText(getContext(),
                    "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        } else if(ValidationHelper.validationField(dateStart)) {
            Toast.makeText(getContext(),
                    "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        } else if(ValidationHelper.validationField(dateEnd)) {
            Toast.makeText(getContext(),
                    "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void setupAdapter() {
        GetAnnouncement getAnnouncements = new GetAnnouncement();
        try {
            adapter = new AnnouncementAdapter(
                    getContext(),
                    getAnnouncements.execute(df.format(calendar.getTime())).get(),
                    this::onAnnouncementClick,
                    this::onDeleteClick);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteClick(Announcement Announcement) {
        if(User.getInstance().getTypeUser() == TypeUser.ADMIN.getName()) {
            DeleteAnnouncement deleteAnnouncement = new DeleteAnnouncement();
            deleteAnnouncement.execute(Announcement.getId());
            setupAdapter();
        } else {
            Toast.makeText(getContext(), "Вы не обладаете правами администратора", Toast.LENGTH_LONG).show();
        }
    }


}
