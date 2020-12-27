package ovsyanik.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    private static final Pattern VALID_LOGIN_REGEX = Pattern.compile(
            "^[-A-z0-9_.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile(
            "^[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE);

    public static boolean validationPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }


    public static boolean validationLogin(String login) {
        Matcher matcher = VALID_LOGIN_REGEX.matcher(login);
        return matcher.find();
    }

    public static boolean validationField(String str) {
        return str.equals("") ? true : false;
    }
}
