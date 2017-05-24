package com.uady.proyectofinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterStepOneActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;

    private Bitmap userImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen_alt);
    }


    public void goToSecondRegisrationStep(View v){

        if(areAllFieldsFilled()){

            RegistrationData.getInstance().setName(((EditText)findViewById(R.id.txt_Name)).getText().toString());
            RegistrationData.getInstance().setLastName(((EditText)findViewById(R.id.txt_LastName)).getText().toString());
            RegistrationData.getInstance().setEmail(((EditText)findViewById(R.id.txt_Email)).getText().toString());
            RegistrationData.getInstance().setPhone(((EditText)findViewById(R.id.txt_Phone)).getText().toString());
            RegistrationData.getInstance().setPassword(((EditText)findViewById(R.id.txt_Password)).getText().toString());
            RegistrationData.getInstance().setPhoto(imageToBase64String(this.userImage));

            Intent intent = new Intent(this, RegisterStepTwoActivity.class);
            startActivity(intent);
        }else{

            Toast.makeText(this, "Faltan campos por llenar ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areAllFieldsFilled() {

        return (!((EditText)findViewById(R.id.txt_Name)).getText().toString().equals("") &&
                !((EditText)findViewById(R.id.txt_Email)).getText().toString().equals("") &&
                !((EditText)findViewById(R.id.txt_LastName)).getText().toString().equals("") &&
                !((EditText)findViewById(R.id.txt_Phone)).getText().toString().equals("") &&
                !((EditText)findViewById(R.id.txt_Password)).getText().toString().equals("") &&
                this.userImage != null);

    }


    public void pickAnImage(View v){

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Elige tu foto"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                userImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ImageView imageView = (ImageView) findViewById(R.id.profile_image);
                imageView.setImageBitmap(userImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToBase64String(Bitmap image){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
