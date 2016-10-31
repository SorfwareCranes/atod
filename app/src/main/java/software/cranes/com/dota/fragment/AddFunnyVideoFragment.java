package software.cranes.com.dota.fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.dialog.DateDialogFragment;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.RelaxModel;

/**
 * Created by GiangNT - PC on 31/10/2016.
 */

public class AddFunnyVideoFragment extends BaseFragment implements View.OnClickListener {
    private Button btnAddTime;
    private EditText edtTitle;
    private EditText edtLink;
    private Button btnBack;
    private Button btnDelete;
    private Button btnSave;
    private EditText edtId;
    private Button btnLoad;
    private Button btnReset;
    private long time;
    private String id;
    private RelaxModel model;
    private int TYPE;
    private FirebaseDatabase mFirebaseDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(Constant.DATA);
            if (id != null && !id.equals(Constant.NO_IMAGE)) {
                TYPE = Constant.LOAD_DATA;
            }

        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_funny_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        btnAddTime = (Button) view.findViewById(R.id.btnAddTime);
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        edtLink = (EditText) view.findViewById(R.id.edtLink);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        edtId = (EditText) view.findViewById(R.id.edtId);
        btnLoad = (Button) view.findViewById(R.id.btnLoad);
        btnReset = (Button) view.findViewById(R.id.btnReset);

        btnReset.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnAddTime.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        if (TYPE == Constant.LOAD_DATA) {
            loadModel(id);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoad:
                loadModel(CommonUtils.extractVideoIdFromUrl(edtId.getText().toString().trim()));
                break;
            case R.id.btnAddTime:
                setTime();
                break;
            case R.id.btnBack:
                getFragmentManager().popBackStack();
                break;
            case R.id.btnDelete:
                executeDelete();
                break;
            case R.id.btnSave:
                executeSave();
                break;
            case R.id.btnReset:
                resetUi();
                break;
        }
    }

    private void loadModel(String id) {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("relax/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideCircleDialogOnly();
                if (dataSnapshot != null) {
                    model = dataSnapshot.getValue(RelaxModel.class);
                    if (model != null) {
                        TYPE = Constant.LOAD_DATA;
                        edtId.setText("https://www.youtube.com/watch?v=" + dataSnapshot.getKey());
                        edtId.setEnabled(false);
                        edtLink.setText("https://www.youtube.com/watch?v=" + dataSnapshot.getKey());
                        if (model.getTitle() != null) {
                            edtTitle.setText(model.getTitle());
                        }
                        time = model.getTime();
                        btnAddTime.setText(CommonUtils.convertIntDateToString(time));
                    } else {
                        Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }

    private void setTime() {
        DateDialogFragment date = new DateDialogFragment(time, new DateDialogFragment.HandleSetTime() {
            @Override
            public void handleTime(long time) {
                AddFunnyVideoFragment.this.time = time;
                btnAddTime.setText(CommonUtils.convertIntDateToString(time));
            }
        });
        if (date.getDialog() == null || !date.getDialog().isShowing()) {
            date.show(getFragmentManager(), null);
        }
    }

    private void executeDelete() {
        if (TYPE == Constant.LOAD_DATA) {
            showCircleDialogOnly();
            mFirebaseDatabase.getReference("relax/" + CommonUtils.extractVideoIdFromUrl(edtId.getText().toString().trim())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Delete Completed", Toast.LENGTH_SHORT).show();
                        resetUi();
                    }
                }
            });
        }
    }

    private void executeSave() {
        if (!validateInput()) {
            return;
        }
        showCircleDialogOnly();
        Map<String, Object> dataMap = new HashMap<>();
        RelaxModel modelSave = createRelaxModel();
        String idSave = CommonUtils.extractVideoIdFromUrl(edtLink.getText().toString().trim());
        if (TYPE == Constant.LOAD_DATA && model != null) {
            if (!idSave.equals(CommonUtils.extractVideoIdFromUrl(edtId.getText().toString().trim()))) {
                dataMap.put(CommonUtils.extractVideoIdFromUrl(edtId.getText().toString().trim()), null);
            }
        }
        dataMap.put(idSave, modelSave);
        mFirebaseDatabase.getReference("relax").updateChildren(dataMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                hideCircleDialogOnly();
                if (databaseError != null) {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "SAVE Completed", Toast.LENGTH_SHORT).show();
                    resetUi();
                }
            }
        });
    }

    private void resetUi() {
        edtLink.setText(Constant.NO_IMAGE);
        edtTitle.setText(Constant.NO_IMAGE);
        id = null;
        model = null;
        time = 0L;
        btnAddTime.setText("TIME");
        edtId.setText(Constant.NO_IMAGE);
        edtId.setEnabled(true);
        TYPE = Constant.CREATE_DATA;
        model = null;
    }

    private boolean validateInput() {
        if (btnAddTime.getText().toString().startsWith("T")) {
            Toast.makeText(getContext(), "Set time plzz", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtLink.getText().toString().trim().equals(Constant.NO_IMAGE) || !CommonUtils.isYoutube(edtLink.getText().toString().trim())) {
            edtLink.setError("requried");
            return false;
        }
        return true;
    }

    private RelaxModel createRelaxModel() {
        RelaxModel result = new RelaxModel();
        result.setTime(time);
        result.setTitle(edtTitle.getText().toString().trim().equals(Constant.NO_IMAGE) ? null : edtTitle.getText().toString().trim());
        return result;
    }
}