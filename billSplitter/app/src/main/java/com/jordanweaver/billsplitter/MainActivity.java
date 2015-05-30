package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    Context context;

    Boolean loginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;


        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "09BfHxs1LMXr9bvfkDnlLlMraDaJ8TisLPkfGi4h",
                "v0lCddJruxQI5fkbNjFBEmR8m1pFtTqkqKtpz6RO");
        ParseUser.enableRevocableSessionInBackground();


            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser!=null)

            {
                // do stuff with the user
                loginScreen = false;
                Intent intent = new Intent(context, MainAppActivity.class);
                startActivity(intent);
                ParseHelper parseHelper = new ParseHelper();
                parseHelper.loadTransactions(this);
                finish();

            }

            else

            {
                // show the sign up or login screen
                loginScreen = true;
                getFragmentManager().beginTransaction().replace(R.id.container,
                        LoginFragment.newInstance(), LoginFragment.TAG).commit();
            }
        }

        @Override
    protected void onResume() {
        super.onResume();


        if(loginScreen) {
            findViewById(R.id.createAccountButton).setOnClickListener(this);
            findViewById(R.id.loginButton).setOnClickListener(this);
            findViewById(R.id.forgotButton).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.createAccountButton){
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.loginButton){
            EditText userName = (EditText) findViewById(R.id.usernameText);
            EditText password = (EditText) findViewById(R.id.passwordText);

            final String usernameText = userName.getText().toString();
            final String passwordText = password.getText().toString();


            if(!usernameText.trim().equals("") && !passwordText.trim().equals("")){

                ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {

                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (parseUser != null) {
                            Log.e(parseUser.getUsername(), "WORKED!");

                            LoginObject object = new LoginObject(usernameText, passwordText);

                            LoginFileStorage fileStorage = new LoginFileStorage();
                            //Fix Global context
                            fileStorage.saveMethod(context, object);
                            Intent intent = new Intent(context, MainAppActivity.class);
                            startActivity(intent);
                            ParseHelper parseHelper = new ParseHelper();
                            parseHelper.loadTransactions(getApplicationContext());
                            finish();


                        } else {
                            toastError(e);

                            findViewById(R.id.forgotButton).setVisibility(View.VISIBLE);

                        }
                    }
                });

            }


        } else if (v.getId() == R.id.forgotButton){
            final AlertDialog.Builder forgotPasswordDialog = new AlertDialog.Builder(this);
            forgotPasswordDialog.setTitle("Would you like to reset your Password?");
            forgotPasswordDialog.setCancelable(false);
            final EditText emailField = new EditText(getApplicationContext());
            emailField.setInputType(InputType.TYPE_CLASS_TEXT);
            emailField.setTextColor(Color.parseColor("#000000"));
            forgotPasswordDialog.setView(emailField);
            forgotPasswordDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String email = emailField.getText().toString();
                    if(!email.trim().equals("")) {
                        ParseUser.requestPasswordResetInBackground(emailField.getText().toString(),
                                new RequestPasswordResetCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        if (e == null) {
                                            toastSuccess();
                                        } else {
                                            toastError(e);
                                        }
                                    }
                        });
                    }
                }
            });

            forgotPasswordDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = forgotPasswordDialog.create();
            dialog.show();
        }


    }

    private void toastError(com.parse.ParseException e){
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    private void toastSuccess(){
        Toast.makeText(this, "An Email was successfully sent to your address",
                Toast.LENGTH_LONG).show();
    }
}
