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
import ovsyanik.project.data.News;
import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;
import ovsyanik.project.sql.DeleteNews;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<News> news;
    private Context context;

    public NewsAdapter(Context context, List<News> news, OnNewsClickListener onNewsClickListener,
                       OnDeleteClickListener onDeleteClickListener) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onNewsClickListener = onNewsClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_one_news_on_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        News currentNews = news.get(position);
        if(currentNews.getmImage() != null) {
//            context.getContentResolver().takePersistableUriPermission(Uri.parse(currentNews.getImage()),
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            holder.iv_imageView.setImageURI(Uri.parse(currentNews.getImage()));
            holder.iv_imageView.setImageBitmap(BitmapFactory.decodeByteArray(
                    currentNews.getmImage(),
                    0,
                    currentNews.getmImage().length
            ));
        }
        holder.tv_title.setText(currentNews.getTitle());
        holder.tv_description.setText(currentNews.getDescription());
    }

    @Override
    public int getItemCount() {
        return news.size();
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
                if(onNewsClickListener != null) {
                    News currentNews = news.get(getLayoutPosition());
                    onNewsClickListener.onNewsClick(currentNews);
                }
            });

            btn_delete.setOnClickListener(v -> {
                if(onDeleteClickListener != null) {
                    News currentNews = news.get(getLayoutPosition());
                    onDeleteClickListener.onDeleteClick(currentNews);
                }
            });
        }
    }

    public interface OnNewsClickListener {
        void onNewsClick(News currentNews);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(News currentNews);
    }

    private OnNewsClickListener onNewsClickListener;

    private OnDeleteClickListener onDeleteClickListener;
}