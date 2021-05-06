package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText email, password, contact;
    TextView signup_link;

    Button login_button;

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact = findViewById(R.id.contact_number);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup_link = findViewById(R.id.signup_link);

        login_button = findViewById(R.id.login_button);

        Intent intent = getIntent();

        try {
            String email_text = intent.getStringExtra("email");
            String password_text = intent.getStringExtra("password");

            email.setText(email_text);
            password.setText(password_text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        signup_link.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), SignUpActivity.class);

            Bundle bundle = new Bundle();

            String email_text = email.getText().toString();
            String password_text = password.getText().toString();

            bundle.putString("email", email_text);
            bundle.putString("password", password_text);

            intent1.putExtras(bundle);

            startActivity(intent1);
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                String contact_text = contact.getText().toString();

                if (!validate_email(email_text)){
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                if(password_text.length() > 8 && password_text.length() < 15){
                    ;
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }

                    try {
                        String hash = toHexString(getSHA(password_text));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public boolean validate_email(String email_id) {

        //Regular Expression
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email_id);

        return matcher.matches();
    }

}