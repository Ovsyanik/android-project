package ovsyanik.project.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import ovsyanik.project.R;
import ovsyanik.project.data.Announcement;
import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Announcement> announcements;
    private Context context;

    public AnnouncementAdapter(Context context, List<Announcement> announcements,
                               OnAnnouncementClickListener onAnnouncementClickListener,
                               OnDeleteClickListener onDeleteClickListener) {
        this.announcements = announcements;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onAnnouncementClickListener = onAnnouncementClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_one_news_on_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.ViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        if(announcement.getmImage() != null) {
//            context.getContentResolver().takePersistableUriPermission(Uri.parse(announcement.getImage()),
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            holder.iv_imageView.setImageURI(Uri.parse(announcement.getImage()));
            holder.iv_imageView.setImageBitmap(BitmapFactory.decodeByteArray(
                    announcement.getmImage(),
                    0,
                    announcement.getmImage().length
            ));

        }
        holder.tv_title.setText(announcement.getTitle());
        holder.tv_description.setText(announcement.getDescription());
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ShapeableImageView iv_imageView;
        final TextView tv_title;
        final TextView tv_description;
        final MaterialButton btn_goTo, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_imageView = itemView.findViewById(R.id.image_one_news);
            this.tv_title = itemView.findViewById(R.id.title_one_news);
            this.tv_description = itemView.findViewById(R.id.description_one_news);
            this.btn_goTo = itemView.findViewById(R.id.btn_go_to_one_news);
            this.btn_delete = itemView.findViewById(R.id.btn_delete_one_news);

            if(User.getInstance().getTypeUser() != TypeUser.ADMIN.getName()) {
                btn_delete.setVisibility(View.GONE);
            }

            btn_goTo.setOnClickListener(v -> {
                if(onAnnouncementClickListener != null) {
                    Announcement announcement = announcements.get(getLayoutPosition());
                    onAnnouncementClickListener.onAnnouncementClick(announcement);
                }
            });

            btn_delete.setOnClickListener(v -> {
                if(onDeleteClickListener != null) {
                    Announcement announcement = announcements.get(getLayoutPosition());
                    onDeleteClickListener.onDeleteClick(announcement);
                }
            });
        }
    }

    public interface OnAnnouncementClickListener {
        void onAnnouncementClick(Announcement Announcement);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Announcement Announcement);
    }

    private OnAnnouncementClickListener onAnnouncementClickListener;

    private OnDeleteClickListener onDeleteClickListener;
}