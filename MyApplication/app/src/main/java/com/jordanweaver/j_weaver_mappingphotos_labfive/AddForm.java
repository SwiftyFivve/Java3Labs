package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jordanweaver on 4/18/15.
 */
public class AddForm extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddForm.TAG";

    public static AddForm newInstance(){
        AddForm addForm = new AddForm();
        return addForm;
    }

    public interface CameraSnap{
        public void handleCamera();
    }

    CameraSnap mCamera;

    public interface SaveInfo{
        public void saveButton(String _title, String _extra);
    }
    SaveInfo mSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.cameraButton).setOnClickListener(this);
        getActivity().findViewById(R.id.saveButton).setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof CameraSnap && activity instanceof SaveInfo){
            mCamera = (CameraSnap)activity;
            mSave = (SaveInfo)activity;
        } else {
            throw new IllegalArgumentException
                    ("Main Activity needs to Implement Camera Snap Interface || Save Interface");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_fragment, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.cameraButton){
            mCamera.handleCamera();

        } else if(v.getId() == R.id.saveButton){

            EditText titleText = (EditText)getActivity().findViewById(R.id.titleText);
            EditText extraText = (EditText)getActivity().findViewById(R.id.extraText);

            String title = titleText.getText().toString();
            String extra = extraText.getText().toString();

            if(!title.trim().equals("") && !extra.trim().equals("")){
                mSave.saveButton(title, extra);
            }
        }
    }
}
