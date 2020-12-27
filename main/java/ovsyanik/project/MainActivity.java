package ovsyanik.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ovsyanik.project.data.User;
import ovsyanik.project.sql.DeleteAnnouncementAndNews;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView tv_nameUser, tv_mailUser;
    private RoundedImageView iv_imageUser;

    public static NavController navController;

    private Calendar calendar = Calendar.getInstance();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);

        tv_nameUser = mNavigationView.getHeaderView(0).findViewById(R.id.name_user_profile);
        tv_mailUser = mNavigationView.getHeaderView(0).findViewById(R.id.mail_user_profile);
        iv_imageUser = mNavigationView.getHeaderView(0).findViewById(R.id.image_profile);

        DeleteAnnouncementAndNews deleteAnnouncementAndNews = new DeleteAnnouncementAndNews();
        deleteAnnouncementAndNews.execute(df.format(calendar.getTime()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        tv_nameUser.setText(User.getInstance().getName());
        tv_mailUser.setText(User.getInstance().getMail());
        if(User.getInstance().getmImage() != null) {
//            getContentResolver().takePersistableUriPermission(Uri.parse(User.getInstance().getImage()),
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            iv_imageUser.setImageBitmap(BitmapFactory.decodeByteArray(
                    User.getInstance().getmImage(),
                    0,
                    User.getInstance().getmImage().length
            ));
        }

        findViewById(R.id.menu_icon).setOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mNavigationView, navController);

        mNavigationView.getMenu().findItem(R.id.item3).setOnMenuItemClickListener(v -> {
            User.setInstanceNull();
            Intent intent = new Intent(this, AuthorizationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}