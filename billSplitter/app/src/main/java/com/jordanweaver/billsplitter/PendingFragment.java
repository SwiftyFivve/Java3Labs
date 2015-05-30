package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.PendingIntent;
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

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 5/20/15.
 */
public class PendingFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = "PendingFragment.TAG";

    public static PendingFragment newInstance(){
        PendingFragment pendingFragment = new PendingFragment();
        return pendingFragment;
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

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mainBillsList+1){
            Intent intent = new Intent(getActivity(), BillingActivity.class);
            mBilling.billingView(intent);
        }
    }

    class billAdapter extends BaseAdapter {

        DataBaseHelper helper = new DataBaseHelper();
        ArrayList<BillsObject> theData = helper.loadData(getActivity(), "pending");

        @Override
        public int getCount() {
            return theData.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.pending_cell,
                        parent, false);
            }

            TextView billName = (TextView) convertView.findViewById(R.id.billNameText);
            TextView moneyText = (TextView) convertView.findViewById(R.id.billMoneyText);
            TextView dateDue = (TextView) convertView.findViewById(R.id.billDateOwed);
            TextView message = (TextView) convertView.findViewById(R.id.messageText);
            billName.setText(theData.get(position).receiverName);
            moneyText.setText("$" + theData.get(position).amount);
            dateDue.setText("Due: "+theData.get(position).date);
            message.setText(theData.get(position).message);

            Boolean status = false;

            Button refundButton = (Button) convertView.findViewById(R.id.refundPayButton);
            Button acceptButton = (Button) convertView.findViewById(R.id.acceptPayButton);

            if(status){
                refundButton.setEnabled(true);
                acceptButton.setEnabled(true);
            } else {
                refundButton.setBackgroundColor(getResources().getColor(R.color.fab_material_grey_500));
                acceptButton.setBackgroundColor(getResources().getColor(R.color.fab_material_grey_500));
            }


            return convertView;
        }
    }

}
