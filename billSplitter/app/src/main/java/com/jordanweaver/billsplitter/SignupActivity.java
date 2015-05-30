package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

/**
 * Created by jordanweaver on 5/13/15.
 */
public class SignupActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,
                SignUpFragment.newInstance(), SignUpFragment.TAG).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.submitButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.submitButton){

            EditText fullNameText = (EditText) findViewById(R.id.fullNameText);
            EditText usernameText = (EditText) findViewById(R.id.usernameSignUpText);
            EditText emailText = (EditText) findViewById(R.id.emailText);
            EditText phoneText = (EditText) findViewById(R.id.phoneText);
            EditText passwordText = (EditText) findViewById(R.id.createPasswordText);
            EditText rePasswordText = (EditText) findViewById(R.id.reTypePasswordText);

            String fullName = fullNameText.getText().toString();
            String username = usernameText.getText().toString();
            String email = emailText.getText().toString();
            String phone = phoneText.getText().toString();
            String password = passwordText.getText().toString();
            String rePassword = rePasswordText.getText().toString();

            if(!email.trim().equals("") && !phone.trim().equals("") && !password.trim().equals("")
                    && !rePassword.trim().equals("") && !username.trim().equals("")){

                if(password.equals(rePassword)){
                    //Create account here

                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.put("receiver", fullName);
                    user.put("phone", phone);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                toastSuccess();
                                finish();
                            } else {
                                toastError(e);
                            }
                        }
                    });



                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill out entire form.\nPlease Log In", Toast.LENGTH_LONG).show();
            }


        }

    }

    private void toastError(com.parse.ParseException e){
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
    private void toastSuccess(){
        Toast.makeText(this, "You have successfully created an account", Toast.LENGTH_LONG).show();
    }

}
