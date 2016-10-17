package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;

import static software.cranes.com.dota.R.id.btnChoiceHeroes;


/**
 * A simple {@link BaseFragment} subclass.
 */
public class AddPublicMatchFragment extends BaseFragment implements View.OnClickListener {
    private Button btnAddTime;
    private AutoCompleteTextView actPlayer;
    private Button btnChoicePlayer;
    private AutoCompleteTextView actHeroes;
    private EditText edtTitleGame;
    private EditText edtLinkHighlight;
    private EditText edtLinkFull;
    private Button btnBack;
    private Button btnDelete;
    private Button btnSave;
    private EditText edtLoadGame;
    private Button btnLoadGame;
    private FirebaseDatabase mFirebaseDatabase;
    private List<String> suggestPlayer;
    private List<String> suggestHeroes;

    public AddPublicMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        suggestPlayer = new ArrayList<>();
        suggestHeroes = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_public_match, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        btnAddTime = (Button) view.findViewById(R.id.btnAddTime);
        actPlayer = (AutoCompleteTextView) view.findViewById(R.id.actPlayer);
        btnChoicePlayer = (Button) view.findViewById(R.id.btnChoicePlayer);
        actHeroes = (AutoCompleteTextView) view.findViewById(R.id.actHeroes);

        edtTitleGame = (EditText) view.findViewById(R.id.edtTitleGame);
        edtLinkHighlight = (EditText) view.findViewById(R.id.edtLinkHighlight);
        edtLinkFull = (EditText) view.findViewById(R.id.edtLinkFull);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        edtLoadGame = (EditText) view.findViewById(R.id.edtLoadGame);
        btnLoadGame = (Button) view.findViewById(R.id.btnLoadGame);

        btnAddTime.setOnClickListener(this);
        btnChoicePlayer.setOnClickListener(this);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnLoadGame.setOnClickListener(this);

        loadPlayerName();
        loadHeroes();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTime:

                break;
            case R.id.btnChoicePlayer:

                break;
            case R.id.btnBack:

                break;
            case R.id.btnDelete:

                break;
            case R.id.btnSave:

                break;
            case R.id.btnLoadGame:

                break;
        }
    }

    private void loadPlayerName() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/sugest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestPlayer = (ArrayList<String>) dataSnapshot.getValue();
                if (suggestPlayer != null && suggestPlayer.size() > 0) {
                    AutoCompleteAdapter adapter = new AutoCompleteAdapter(getContext(), android.R.layout.simple_list_item_1, suggestPlayer);
                    actPlayer.setAdapter(adapter);
                }

                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHeroes() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("heroes_suggest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestHeroes = (List<String>) dataSnapshot.getValue();
                AutoCompleteAdapter adapter = new AutoCompleteAdapter(getContext(), android.R.layout.simple_list_item_1, suggestHeroes);
                actHeroes.setAdapter(adapter);
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}