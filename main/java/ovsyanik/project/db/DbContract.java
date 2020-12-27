package ovsyanik.project.db;

import android.provider.BaseColumns;

public final class DbContract {

    public static final class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "User";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String MAIL = "mail";
        public static final String PASSWORD = "password";
        public static final String TYPE = "type";
        public static final String IN_BLACK_LIST = "inBlackList";
    }

    public static final class NewsTable implements BaseColumns {
        public static final String TABLE_NAME = "News";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String IMAGE = "image";
        public static final String DESCRIPTION = "description";
        public static final String DATE_START = "dateStart";
        public static final String DATE_END = "dateEnd";
    }

    public static final class AnnouncementTable implements BaseColumns {
        public static final String TABLE_NAME = "Announcement";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String IMAGE = "image";
        public static final String DESCRIPTION = "description";
        public static final String DATE_START = "dateStart";
        public static final String DATE_END = "dateEnd";
    }

}
