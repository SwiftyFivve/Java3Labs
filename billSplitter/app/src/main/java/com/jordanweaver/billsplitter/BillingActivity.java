package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordanweaver on 5/21/15.
 */
public class BillingActivity extends Activity implements View.OnClickListener{


    ArrayList<String> billPhones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,
                BillingFragment.newInstance(), BillingFragment.TAG).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.billButton).setOnClickListener(this);
        findViewById(R.id.addContact).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Log.e("Clicked", v.getId()+"");
        Log.e("Bill Button", R.id.billButton + "");
        if(v.getId() == R.id.billButton){

            EditText dateDue = (EditText) findViewById(R.id.dateDue);
            EditText amountDue = (EditText) findViewById(R.id.amount);
            EditText billMessage = (EditText) findViewById(R.id.billMessage);
            final CheckBox splitCheckBox = (CheckBox) findViewById(R.id.splitEven);

            final String dateDueText = dateDue.getText().toString();
            final String amountDueText = amountDue.getText().toString();
            final String billMessageText = billMessage.getText().toString();

            // set up parse data here
            List<ParseQuery<ParseUser>> queries = new ArrayList<>();

            for(int i = 0; i<billPhones.size(); i++) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("phone", billPhones.get(i));
                queries.add(query);
            }

            ParseQuery<ParseUser> mainQuery = ParseQuery.or(queries);

            mainQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(final List<ParseUser> parseObjects, com.parse.ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < parseObjects.size(); i++) {

                            String phone = parseObjects.get(i).getString("phone");
                            String receiverName = parseObjects.get(i).getString("name");
                            String senderName = ParseUser.getCurrentUser().getString("name");

                            if(splitCheckBox.isChecked()){
                                float splitAmount = Float.parseFloat(amountDueText)/billPhones.size();

                                BillsObject object = new BillsObject(receiverName, senderName, dateDueText,
                                        Float.toString(splitAmount), billMessageText, null);

                                transactionDatabase(parseObjects.get(i), object, phone);
                                if(i == parseObjects.size()-1){
                                    finish();
                                }

                            } else {

                                BillsObject object = new BillsObject(receiverName, senderName,
                                        dateDueText, amountDueText, billMessageText, null);

                                transactionDatabase(parseObjects.get(i), object, phone);
                                finish();
                            }


                        }
                    } else {
                        return;
                    }
                }
             });

        } else if(v.getId() == R.id.addContact){
            EditText billTo = (EditText) findViewById(R.id.friendsName);
            final String billToText = billTo.getText().toString();

            TextView friends = (TextView) findViewById(R.id.sendingTo);

            if(friends.getText().equals("")) {
                friends.setText(billToText);
                billPhones.add(billToText);
                billTo.setText("");

            } else {
                String friendsText = friends.getText().toString();
                friends.setText(friendsText +", "+billToText);
                billPhones.add(billToText);
                billTo.setText("");
            }

        }
    }

    private void transactionDatabase(ParseUser user, final BillsObject object, String billToText){
        final ParseUser currentUser = ParseUser.getCurrentUser();
        Log.e("Other user", user.getUsername());
        ParseObject transObject = new ParseObject("PrivateTransactionData");
        transObject.put("receiverName", object.receiverName);
        transObject.put("due", object.date);
        transObject.put("amount", object.amount);
        transObject.put("message", object.message);
        transObject.put("senderName", object.senderName);
        transObject.put("receiver", billToText);
        transObject.put("sender", currentUser.get("phone"));

        transObject.saveInBackground();

        ParseHelper parseHelper = new ParseHelper();
        parseHelper.loadTransactions(getApplicationContext());

    }


    private void toastError(com.parse.ParseException e){
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    private void toastSuccess(){
        Toast.makeText(this, "Bill was sent",
                Toast.LENGTH_LONG).show();
    }

//            if(!billToText.trim().equals("") && !dateDueText.trim().equals("") &&
//                    amountDueText.trim().equals("")) {

//                if(billMessageText.trim().equals("")){
//                    billMessageText = "Item";
//                }
//
//                PayPalPayment payment = new PayPalPayment(new BigDecimal(amountDueText), "USD",
//                        billMessageText,
//                        PayPalPayment.PAYMENT_INTENT_SALE);
//
//                Intent intent = new Intent(this, PaymentActivity.class);
//
//                // send the same configuration for restart resiliency
//                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//                startActivityForResult(intent, 0);
}
