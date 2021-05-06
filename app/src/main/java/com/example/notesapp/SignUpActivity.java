package com.example.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText email, password, contact, name;
    TextView login_link;

    Button signup_button;

    String name_text, email_text, password_text, contact_text;

    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new SQLiteDatabaseHandler(this);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        contact = findViewById(R.id.contact_number);

        login_link = findViewById(R.id.login_link);

        signup_button = findViewById(R.id.signup_button);

        Intent intent = getIntent();

        try {
            String email_text = intent.getStringExtra("email");
            String password_text = intent.getStringExtra("password");

            email.setText(email_text);
            password.setText(password_text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        login_link.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);

            Bundle bundle = new Bundle();

            String email_text = email.getText().toString();
            String password_text = password.getText().toString();

            bundle.putString("email", email_text);
            bundle.putString("password", password_text);

            intent1.putExtras(bundle);

            startActivity(intent1);
        });


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_text = name.getText().toString();
                email_text = email.getText().toString();
                password_text = password.getText().toString();
                contact_text = contact.getText().toString();

                String hash = null;

                if (!validate_email(email_text)){
                    Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                if(!validate_password(password_text)){
                    Toast.makeText(SignUpActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }


                try {
                    hash = toHexString(getSHA(password_text));
                    Toast.makeText(SignUpActivity.this, hash, Toast.LENGTH_SHORT).show();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                UserDetails.name = name_text;
                UserDetails.password_hash = hash;
                UserDetails.contact = contact_text;
                UserDetails.email = email_text;


                // create some players
                UserDetails player1 = new UserDetails(name_text, email_text, hash, contact_text);

                // add them
                db.addUser(player1);

                // list all players
//                List<UserDetails> players = db.allUsers();
//
//                if (players != null) {
//                    String[] itemsNames = new String[players.size()];
//
//                    for (int i = 0; i < players.size(); i++) {
//                        itemsNames[i] = players.get(i).toString();
//                        Toast.makeText(getApplicationContext(), Arrays.toString(itemsNames), Toast.LENGTH_SHORT).show();
//                    }
//
//                }

//                try {
//                    SQLiteDatabase dbb = openOrCreateDatabase("UserInfo", MODE_PRIVATE, null);
//
//                    Cursor resultSet = dbb.rawQuery("Select * from User", null);
//                    resultSet.moveToFirst();
//                    String username = resultSet.getString(0);
//                    String password = resultSet.getString(1);
//
//                    Toast.makeText(SignUpActivity.this, username + password, Toast.LENGTH_SHORT).show();
//
//                    resultSet.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

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

    private boolean validate_password(String password) {

        //Regular Expression
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}";        ;
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}