package com.uady.proyectofinal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Demon on 09/05/2017.
 */


public class Validaciones {


    public boolean isEmailFormated(String email){
        Pattern p;
        Matcher m;
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        p = Pattern.compile(EMAIL_PATTERN);
        m = p.matcher(email);

        return m.matches();
    }

    public boolean passCheck(String password){
        if(password.isEmpty())
            return false;
        else
            return  true;

    }

}


