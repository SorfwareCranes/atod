package software.cranes.com.dota.dialog;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.adapter.Suggest_Adapter;
import software.cranes.com.dota.interfa.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private int type;
    private FirebaseDatabase mFirebaseDatabase;
    private AutoCompleteTextView actSuggest;
    private ListView lvSuggest;
    private Button btnBack;
    private Button btnSave;
    private HanldeName mHanldeName;

    public interface HanldeName {
        void handleChoice(String str);
    }

    public SuggestDialogFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SuggestDialogFragment(int type, HanldeName mHanldeName) {
        this.type = type;
        this.mHanldeName = mHanldeName;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_suggest, null, false);
        findViews(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void findViews(View view) {
        showCircleDialogOnly();
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        actSuggest = (AutoCompleteTextView) view.findViewById(R.id.actSuggest);
        lvSuggest = (ListView) view.findViewById(R.id.lvSuggest);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        String url = Constant.NO_IMAGE;
        switch (type) {
            case Constant.TYPE_PLAYER:
                tvTitle.setText("Player Suggest");
                url = "joindota/suggest_player";
                break;

            default:
                break;
        }
        mFirebaseDatabase.getReference(url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideCircleDialogOnly();
                List<String> listData = (List<String>) dataSnapshot.getValue();
                Suggest_Adapter adapter = new Suggest_Adapter(listData);
                lvSuggest.setAdapter(adapter);
                lvSuggest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        actSuggest.setText((String) lvSuggest.getAdapter().getItem(position));
                    }
                });
                AutoCompleteAdapter autoAdapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listData);
                actSuggest.setAdapter(autoAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialog();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.dismiss();
                break;
            case R.id.btnSave:
                if (mHanldeName != null) {
                    mHanldeName.handleChoice(actSuggest.getText().toString());
                }
                this.dismiss();
                break;
        }
    }
}