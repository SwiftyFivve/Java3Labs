package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.software.shell.fab.ActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 5/20/15.
 */
public class BillsFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = "BillsFragment.TAG";

    public static BillsFragment newInstance(){
        BillsFragment billsFragment = new BillsFragment();
        return billsFragment;
    }

    public interface BillingInterface{
        public void billingView(Intent intent);
    }

    BillingInterface mBilling;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BillingInterface){
            mBilling = (BillingInterface)activity;
        } else {
            throw new IllegalArgumentException("Please make sure activity implements " +
                    "Billing Interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.billing_list_layout, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ListView billsList = (ListView) getActivity().findViewById(R.id.mainBillsList);
        billsList.setAdapter(new billAdapter());

        ActionButton actionButton = (ActionButton) getActivity().findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.fab_material_blue_900));
        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));
        actionButton.setOnClickListener(this);

        //getActivity().findViewById(R.id.listPayButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mainBillsList+1){
            Intent intent = new Intent(getActivity(), BillingActivity.class);
            mBilling.billingView(intent);
        } else if(v.getId() == R.id.listPayButton){

//            PayPalPayment payment = new PayPalPayment(new BigDecimal(amountDueText), "USD",
//                        billMessageText,
//                        PayPalPayment.PAYMENT_INTENT_SALE);
//
//                Intent intent = new Intent(this, PaymentActivity.class);

        }
    }



    class billAdapter extends BaseAdapter {

        DataBaseHelper helper = new DataBaseHelper();
        ArrayList<BillsObject> theData = helper.loadData(getActivity(), "bills");

        @Override
        public int getCount() {
            if(theData==null) {
                return 0;
            } else {
                return theData.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return theData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position+20;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.bills_cell,
                        parent, false);
            }

            TextView billName = (TextView) convertView.findViewById(R.id.billNameText);
            TextView moneyText = (TextView) convertView.findViewById(R.id.billMoneyText);
            TextView dateDue = (TextView) convertView.findViewById(R.id.billDateOwed);
            TextView message = (TextView) convertView.findViewById(R.id.messageText);
            billName.setText("To: "+theData.get(position).senderName);
            moneyText.setText("$"+theData.get(position).amount);
            dateDue.setText("Due: "+theData.get(position).date);
            message.setText(theData.get(position).message);

            Button payButton = (Button) convertView.findViewById(R.id.listPayButton);



            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayPalPayment payment = new PayPalPayment(new BigDecimal(
                            theData.get(position).amount), "USD", theData.get(position).message,
                        PayPalPayment.PAYMENT_INTENT_SALE);

                    ArrayList<BillsObject> historyArray = helper.loadData(getActivity(), "history");

                    if(historyArray == null){
                        historyArray = new ArrayList<BillsObject>();
                    }

                    Log.e("position", position+"");
                    Log.e("Array position", theData.get(position)+"");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("PrivateTransactionData");
                    query.getInBackground(theData.get(position).objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, com.parse.ParseException e) {
                            if (e == null) {
                                // object will be your game score
                                helper.saveObject(getActivity(), object, "temp");
                                object.deleteInBackground();
                            } else {
                                // something went wrong
                            }
                        }
                    });

                    historyArray.add(theData.get(position));
                    helper.saveData(getActivity(), historyArray, "history");

                    theData.remove(position);

                    helper.saveData(getActivity(), theData, "bills");



                    Intent intent = new Intent(getActivity(), PaymentActivity.class);

                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);



                    mBilling.billingView(intent);
                }
            });

            return convertView;
        }
    }

    private static PayPalConfiguration config = new PayPalConfiguration()

            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("Ac4n9mIX1zvCFbXqxxh6JVyN8rzlgsnf4CkZNSRFII4fYjDpRTmgR2JDt4bvsHv9PKoxDwKTtNybJzQK");

}
