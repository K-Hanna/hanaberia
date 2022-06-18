package com.hanaberia.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pattern;
    private Matcher matcher;
    private String valid;

    public boolean isUserNameValid(String userName){
        valid = "[A-za-z0-9]{2,}";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    public boolean isEmailValid(String email){
        valid = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isPhoneValid(String phone){
        valid = "[0-9]{3}+[- ]?+[0-9]{3}+[- ]?+[0-9]{3}";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean arePasswordsMatching(String password, String confirm){
        return password.equals(confirm);
    }
}
