package ovsyanik.project.data;

public class User {
    private int id;
    private String name;
    private String image;
    private byte[] mImage;
    private String mail;
    private String password;
    private TypeUser typeUser;
    private int inBlackList;

    public User() {
    }

    public User(String name, String image, String mail, String password, TypeUser typeUser, int inBlackList) {
        this.name = name;
        this.image = image;
        this.mail = mail;
        this.password = password;
        this.typeUser = typeUser;
        this.inBlackList = inBlackList;
    }

    public User(int id, String name, String image, String mail, String password, TypeUser typeUser, int inBlackList) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.mail = mail;
        this.password = password;
        this.typeUser = typeUser;
        this.inBlackList = inBlackList;
    }

    public User(String name, byte[] image, String mail, String password, TypeUser typeUser, int inBlackList) {
        this.name = name;
        this.mImage = image;
        this.mail = mail;
        this.password = password;
        this.typeUser = typeUser;
        this.inBlackList = inBlackList;
    }

    public User(int id, String name, byte[] image, String mail, String password, TypeUser typeUser, int inBlackList) {
        this.id = id;
        this.name = name;
        this.mImage = image;
        this.mail = mail;
        this.password = password;
        this.typeUser = typeUser;
        this.inBlackList = inBlackList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeUser() {
        return typeUser.getName();
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public int isInBlackList() {
        return inBlackList;
    }

    public void setInBlackList(int inBlackList) {
        this.inBlackList = inBlackList;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", typeUser=" + typeUser +
                ", inBlackList=" + inBlackList +
                '}';
    }

    private static User mInstance = null;

    public static User getInstance(User user) {
        if(mInstance == null) {
            mInstance = user;
        }
        return mInstance;
    }

    public static User getInstance() {
        if(mInstance == null) {
            mInstance = new User();
        }
        return mInstance;
    }

    public static void setInstanceNull() {
        mInstance = null;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }
}


