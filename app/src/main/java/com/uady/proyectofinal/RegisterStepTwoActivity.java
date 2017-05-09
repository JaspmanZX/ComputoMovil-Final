package com.uady.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;

public class RegisterStepTwoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen_2);
    }


    public void goToThirdRegistrationStep(View view){

        long date = ((CalendarView)findViewById(R.id.calendarView)).getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        RegistrationData.getInstance().setBirthday(stringifyCalendar(calendar));

        /*
        Intent intent = new Intent(this, RegisterStepThreeActivity.class);
        startActivity(intent);
        */

    }

    private String stringifyCalendar(Calendar calendar) {

        return calendar.get(Calendar.YEAR) + "-" +
                calendar.get(Calendar.MONTH) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
    }
}
