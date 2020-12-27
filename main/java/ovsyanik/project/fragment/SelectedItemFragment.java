package ovsyanik.project.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ovsyanik.project.R;
import ovsyanik.project.data.Announcement;
import ovsyanik.project.data.News;

public class SelectedItemFragment extends Fragment {
    private TextView tv_title, tv_desciption;
    private ImageView iv_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_title = view.findViewById(R.id.title_selected_item);
        tv_desciption = view.findViewById(R.id.description_selected_news);
        iv_image = view.findViewById(R.id.image_selected_item);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getArguments().getParcelable(News.class.getSimpleName()) != null) {
            News news = getArguments().getParcelable(News.class.getSimpleName());

            tv_title.setText(news.getTitle());
            tv_desciption.setText(news.getDescription());
            if (news.getmImage() != null) {
//            getContext().getContentResolver().takePersistableUriPermission(Uri.parse(news.getImage()),
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            iv_image.setImageURI(Uri.parse(news.getImage()));
                iv_image.setImageBitmap(BitmapFactory.decodeByteArray(
                        news.getmImage(),
                        0,
                        news.getmImage().length
                ));
            }
        } else {
            Announcement announcement = getArguments().getParcelable(Announcement.class.getSimpleName());
            tv_title.setText(announcement.getTitle());
            tv_desciption.setText(announcement.getDescription());
            if (announcement.getmImage() != null) {
//            getContext().getContentResolver().takePersistableUriPermission(Uri.parse(news.getImage()),
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            iv_image.setImageURI(Uri.parse(news.getImage()));
                iv_image.setImageBitmap(BitmapFactory.decodeByteArray(
                        announcement.getmImage(),
                        0,
                        announcement.getmImage().length
                ));
            }
        }
    }
}
