package com.jordanweaver.billsplitter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 5/16/15.
 */
public class MainAppActivity extends Activity implements View.OnClickListener,
    BillsFragment.BillingInterface, PendingFragment.BillingInterface,
    HistoryFragment.BillingInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        final String[] tabNames = {"Bills", "Pending", "History"};
        final ActionBar actionBar = getActionBar();

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                if(tab.getText().equals(tabNames[0])){
                    getFragmentManager().beginTransaction().replace(R.id.container,
                            BillsFragment.newInstance(), BillsFragment.TAG).commit();

                } else if(tab.getText().equals(tabNames[1])){
                    getFragmentManager().beginTransaction().replace(R.id.container,
                            PendingFragment.newInstance(), PendingFragment.TAG).commit();

                } else if(tab.getText().equals(tabNames[2])){
                    getFragmentManager().beginTransaction().replace(R.id.container,
                             HistoryFragment.newInstance(), HistoryFragment.TAG).commit();

                }

            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabNames[i])
                            .setTabListener(tabListener));
        }


    }

    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("Ac4n9mIX1zvCFbXqxxh6JVyN8rzlgsnf4CkZNSRFII4fYjDpRTmgR2JDt4bvsHv9PKoxDwKTtNybJzQK");


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.container,
                    SettingFrag.newInstance(),SettingFrag.TAG).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void billingView(Intent intent) {
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                    //Unable to confirm at this point. Would need more time / set up dedicated servers]\\


                    DataBaseHelper helper = new DataBaseHelper();
                    ArrayList<BillsObject> bills = helper.loadData(this, "bills");
                    if(bills == null){
                        bills = new ArrayList<>();
                    }

                    for(int i=0; i<bills.size(); i++){

                        String objectId = bills.get(i).objectId;



                    }

                    helper.saveData(this, bills, "bills");

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");

            DataBaseHelper helper = new DataBaseHelper();
            ArrayList<BillsObject> bills = helper.loadData(this, "bills");
            if(bills == null){
                bills = new ArrayList<>();
            }

            ParseObject parseObject = helper.loadObject(this, "temp");

            if(parseObject != null){
                ParseQuery<ParseObject> query = ParseQuery.getQuery("PrivateTransactionData");
                parseObject.saveInBackground();
            }

            ArrayList<BillsObject> history = helper.loadData(this, "history");

            if(history.size() != 0) {
                Log.e("history size", history.size()+"");
                BillsObject object = history.get(history.size() - 1);

                history.remove(history.size() - 1);
                helper.saveData(this, history, "history");

                bills.add(object);
            }

            helper.saveData(this, bills, "bills");


        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
