package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.dialog.DateTimeDialog;
import software.cranes.com.dota.dialog.SuggestDialogFragment;
import software.cranes.com.dota.interfa.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProfessionMatchFragment extends BaseFragment implements View.OnClickListener {
    private EditText edtMatchId;
    private Button btnLoadGame;
    private AutoCompleteTextView actTeamA, actTeamB, actTournament, actRound;
    private Button btnSuggestTeamA;
    private Button btnSuggestTeamB;
    private EditText edtOddsTeamA;
    private EditText edtOddsTeamB;
    private EditText edtBo;
    private Button btnAddTime;
    private RadioButton rbEnd;
    private RadioButton rbLive;
    private RadioButton rbUpcoming;
    private EditText edtNumberChanel;
    private Button btnGenerateChanel;
    private LinearLayout llChanel;
    private EditText edtGameData;
    private Button btnInputData;
    private LinearLayout llData;
    private Button btnBack;
    private Button btnDelete;
    private Button btnSave;
    private Button btnSuggestPlayer;
    private AutoCompleteTextView actPlayer1A;
    private Button btnListPlayer1A;
    private AutoCompleteTextView actPlayer1B;
    private Button btnListPlayer1B;
    private AutoCompleteTextView actPlayer2A;
    private Button btnListPlayer2A;
    private AutoCompleteTextView actPlayer2B;
    private Button btnListPlayer2B;
    private AutoCompleteTextView actPlayer3A;
    private Button btnListPlayer3A;
    private AutoCompleteTextView actPlayer3B;
    private Button btnListPlayer3B;
    private AutoCompleteTextView actPlayer4A;
    private Button btnListPlayer4A;
    private AutoCompleteTextView actPlayer4B;
    private Button btnListPlayer4B;
    private AutoCompleteTextView actPlayer5A;
    private Button btnListPlayer5A;
    private AutoCompleteTextView actPlayer5B;
    private Button btnListPlayer5B;
    private TextView tvPhotoA, tvPhotoB;
    private Button btnLoadPhoto;
    private String matchId;
    private int TYPE = Constant.CREATE_DATA;
    private FirebaseDatabase mFirebaseDatabase;
    private List<String> suggestTeam, suggestPlayer, suggestListPlayer, suggestListTeam;
    private SuggestDialogFragment suggestDialogFragment;
    private long time;

    public AddProfessionMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().get(Constant.DATA) != null) {
            matchId = (String) getArguments().get(Constant.DATA);
            TYPE = Constant.LOAD_DATA;
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        suggestTeam = new ArrayList<>();
        suggestPlayer = new ArrayList<>();
        suggestListPlayer = new ArrayList<>();
        suggestListTeam = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_profession, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        edtMatchId = (EditText) view.findViewById(R.id.edtMatchId);
        btnLoadGame = (Button) view.findViewById(R.id.btnLoadGame);
        actTeamA = (AutoCompleteTextView) view.findViewById(R.id.actTeamA);
        btnSuggestTeamA = (Button) view.findViewById(R.id.btnSuggestTeamA);
        actTeamB = (AutoCompleteTextView) view.findViewById(R.id.actTeamB);
        btnSuggestTeamB = (Button) view.findViewById(R.id.btnSuggestTeamB);
        actTournament = (AutoCompleteTextView) view.findViewById(R.id.actTournament);
        actRound = (AutoCompleteTextView) view.findViewById(R.id.actRound);
        edtOddsTeamA = (EditText) view.findViewById(R.id.edtOddsTeamA);
        edtOddsTeamB = (EditText) view.findViewById(R.id.edtOddsTeamB);
        edtBo = (EditText) view.findViewById(R.id.edtBo);
        btnAddTime = (Button) view.findViewById(R.id.btnAddTime);
        rbEnd = (RadioButton) view.findViewById(R.id.rbEnd);
        rbLive = (RadioButton) view.findViewById(R.id.rbLive);
        rbUpcoming = (RadioButton) view.findViewById(R.id.rbUpcoming);
        edtNumberChanel = (EditText) view.findViewById(R.id.edtNumberChanel);
        btnGenerateChanel = (Button) view.findViewById(R.id.btnGenerateChanel);
        llChanel = (LinearLayout) view.findViewById(R.id.llChanel);
        edtGameData = (EditText) view.findViewById(R.id.edtGameData);
        btnInputData = (Button) view.findViewById(R.id.btnInputData);
        llData = (LinearLayout) view.findViewById(R.id.llData);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSuggestPlayer = (Button) view.findViewById(R.id.btnSuggestPlayer);
        actPlayer1A = (AutoCompleteTextView) view.findViewById(R.id.actPlayer1A);
        btnListPlayer1A = (Button) view.findViewById(R.id.btnListPlayer1A);
        actPlayer1B = (AutoCompleteTextView) view.findViewById(R.id.actPlayer1B);
        btnListPlayer1B = (Button) view.findViewById(R.id.btnListPlayer1B);
        actPlayer2A = (AutoCompleteTextView) view.findViewById(R.id.actPlayer2A);
        btnListPlayer2A = (Button) view.findViewById(R.id.btnListPlayer2A);
        actPlayer2B = (AutoCompleteTextView) view.findViewById(R.id.actPlayer2B);
        btnListPlayer2B = (Button) view.findViewById(R.id.btnListPlayer2B);
        actPlayer3A = (AutoCompleteTextView) view.findViewById(R.id.actPlayer3A);
        btnListPlayer3A = (Button) view.findViewById(R.id.btnListPlayer3A);
        actPlayer3B = (AutoCompleteTextView) view.findViewById(R.id.actPlayer3B);
        btnListPlayer3B = (Button) view.findViewById(R.id.btnListPlayer3B);
        actPlayer4A = (AutoCompleteTextView) view.findViewById(R.id.actPlayer4A);
        btnListPlayer4A = (Button) view.findViewById(R.id.btnListPlayer4A);
        actPlayer4B = (AutoCompleteTextView) view.findViewById(R.id.actPlayer4B);
        btnListPlayer4B = (Button) view.findViewById(R.id.btnListPlayer4B);
        actPlayer5A = (AutoCompleteTextView) view.findViewById(R.id.actPlayer5A);
        btnListPlayer5A = (Button) view.findViewById(R.id.btnListPlayer5A);
        actPlayer5B = (AutoCompleteTextView) view.findViewById(R.id.actPlayer5B);
        btnListPlayer5B = (Button) view.findViewById(R.id.btnListPlayer5B);
        tvPhotoA = (TextView) view.findViewById(R.id.tvPhotoA);
        tvPhotoB = (TextView) view.findViewById(R.id.tvPhotoB);
        btnLoadPhoto = (Button) view.findViewById(R.id.btnLoadPhoto);

        btnSuggestPlayer.setOnClickListener(this);
        btnListPlayer1A.setOnClickListener(this);
        btnListPlayer1B.setOnClickListener(this);
        btnListPlayer2A.setOnClickListener(this);
        btnListPlayer2B.setOnClickListener(this);
        btnListPlayer3A.setOnClickListener(this);
        btnListPlayer3B.setOnClickListener(this);
        btnListPlayer4A.setOnClickListener(this);
        btnListPlayer4B.setOnClickListener(this);
        btnListPlayer5A.setOnClickListener(this);
        btnListPlayer5B.setOnClickListener(this);
        btnLoadGame.setOnClickListener(this);
        btnSuggestTeamA.setOnClickListener(this);
        btnSuggestTeamB.setOnClickListener(this);
        btnAddTime.setOnClickListener(this);
        rbEnd.setOnClickListener(this);
        rbLive.setOnClickListener(this);
        rbUpcoming.setOnClickListener(this);
        btnGenerateChanel.setOnClickListener(this);
        btnInputData.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnLoadPhoto.setOnClickListener(this);
        setupUi();
    }

    /*
        load data for actTeamA, actTeamB, actTournament, actRound;
        if type = load_data ->
            actTournament and actRound not load
     */
    private void setupUi() {

        if (TYPE == Constant.LOAD_DATA) {
            edtMatchId.setText(matchId);
            setupForLoadMatch();
        } else {
            setupForCreateNew();
        }
    }

    private void setupForCreateNew() {
        // for actTournament
        loadDataAutoCompleteText(actTournament, "profession/suggest/tour");
        // for actRound
        loadDataAutoCompleteText(actRound, "profession/suggest/round");
        // for actTeamA, actTeamB
        loadSuggestTeam();
        // for actPlayer
        loadSuggestPlayer();
    }

    private void setupForLoadMatch() {
        if (actTournament.getText().toString().trim().isEmpty()) {
            // for actTournament
            loadDataAutoCompleteText(actTournament, "profession/suggest/tour");
        }
        if (actRound.getText().toString().trim().isEmpty()) {
            // for actRound
            loadDataAutoCompleteText(actRound, "profession/suggest/round");
        }
        if (actTeamA.getText().toString().trim().equals(Constant.TBD) || actTeamB.getText().toString().trim().equals(Constant.TBD)) {
            loadSuggestTeam();
        }
        if (actPlayer1A.getText().toString().trim().isEmpty() || actPlayer1B.getText().toString().trim().isEmpty() || actPlayer5A.getText().toString().trim().isEmpty() || actPlayer5B.getText().toString().trim().isEmpty()) {
            loadSuggestPlayer();
        }
    }

    /*
        load suggest for AutoCompleteTextView
        for actTournament, actRound
     */
    private void loadDataAutoCompleteText(final AutoCompleteTextView actText, String pathData) {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference(pathData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> teamList = (List<String>) dataSnapshot.getValue();
                AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, teamList);
                actText.setAdapter(adapter);
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadGame:

                break;
            case R.id.btnSuggestTeamA:
                showListDataTeam(actTeamA);
                break;
            case R.id.btnSuggestTeamB:
                showListDataTeam(actTeamB);
                break;
            case R.id.btnSuggestPlayer:
                executeLoadPlayer(actTeamA, 1);
                executeLoadPlayer(actTeamB, 2);
                break;
            case R.id.btnListPlayer1A:
                showListDataPlayer(actPlayer1A);
                break;
            case R.id.btnListPlayer2A:
                showListDataPlayer(actPlayer2A);
                break;
            case R.id.btnListPlayer3A:
                showListDataPlayer(actPlayer3A);
                break;
            case R.id.btnListPlayer4A:
                showListDataPlayer(actPlayer4A);
                break;
            case R.id.btnListPlayer5A:
                showListDataPlayer(actPlayer5A);
                break;
            case R.id.btnListPlayer1B:
                showListDataPlayer(actPlayer1B);
                break;
            case R.id.btnListPlayer2B:
                showListDataPlayer(actPlayer2B);
                break;
            case R.id.btnListPlayer3B:
                showListDataPlayer(actPlayer3B);
                break;
            case R.id.btnListPlayer4B:
                showListDataPlayer(actPlayer4B);
                break;
            case R.id.btnListPlayer5B:
                showListDataPlayer(actPlayer5B);
                break;
            case R.id.btnAddTime:
                showDialogChoiceTime(time);
                break;
            case R.id.btnLoadPhoto:

                break;
            case R.id.btnGenerateChanel:

                break;
            case R.id.btnInputData:

                break;
            case R.id.btnBack:

                break;
            case R.id.btnDelete:

                break;
            case R.id.btnSave:

                break;
            default:
                break;

        }
    }

    /*
        for btnListTeam
        use when suggest data not have
        load data from joindota database
     */
    private void showListDataTeam(final AutoCompleteTextView act) {
        showCircleDialogOnly();
        if (suggestListTeam != null && suggestListTeam.size() != 0) {
            suggestDialogFragment = new SuggestDialogFragment(suggestListTeam, new SuggestDialogFragment.HanldeName() {
                @Override
                public void handleChoice(String str) {
                    act.setText(str);
                }
            });
            hideCircleDialogOnly();
            suggestDialogFragment.show(getFragmentManager(), null);
        } else {
            mFirebaseDatabase.getReference("joindota/suggest_team").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    suggestListTeam = (List<String>) dataSnapshot.getValue();
                    hideCircleDialogOnly();
                    if (suggestListTeam != null) {
                        suggestDialogFragment = new SuggestDialogFragment(suggestListTeam, new SuggestDialogFragment.HanldeName() {
                            @Override
                            public void handleChoice(String str) {
                                act.setText(str);
                            }
                        });
                        suggestDialogFragment.show(getFragmentManager(), null);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideCircleDialogOnly();
                    Toast.makeText(getContext(), "can't get data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*
    for btn ListPlayer
    use when suggest data not have
    load data from joindota database
    */
    private void showListDataPlayer(final AutoCompleteTextView act) {
        if (suggestListPlayer != null && suggestListPlayer.size() != 0) {
            suggestDialogFragment = new SuggestDialogFragment(suggestListPlayer, new SuggestDialogFragment.HanldeName() {
                @Override
                public void handleChoice(String str) {
                    act.setText(str);
                }
            });
            suggestDialogFragment.show(getFragmentManager(), null);
        } else {
            showCircleDialogOnly();
            mFirebaseDatabase.getReference("joindota/suggest_player").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    suggestListPlayer = (List<String>) dataSnapshot.getValue();
                    hideCircleDialogOnly();
                    if (suggestListPlayer != null) {
                        suggestDialogFragment = new SuggestDialogFragment(suggestListPlayer, new SuggestDialogFragment.HanldeName() {
                            @Override
                            public void handleChoice(String str) {
                                act.setText(str);
                            }
                        });
                        suggestDialogFragment.show(getFragmentManager(), null);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideCircleDialogOnly();
                    Toast.makeText(getContext(), "can't get data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // for actTeamA, actTeamB
    private void loadSuggestTeam() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("profession/suggest/team").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestTeam = (List<String>) dataSnapshot.getValue();
                if (suggestTeam != null) {
                    AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestTeam);
                    actTeamA.setAdapter(adapter);
                    actTeamB.setAdapter(adapter);
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }

    // for actPlayer
    private void loadSuggestPlayer() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("profession/suggest/player").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestPlayer = (List<String>) dataSnapshot.getValue();
                if (suggestPlayer != null) {
                    AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestPlayer);
                    actPlayer1A.setAdapter(adapter);
                    actPlayer2A.setAdapter(adapter);
                    actPlayer3A.setAdapter(adapter);
                    actPlayer4A.setAdapter(adapter);
                    actPlayer5A.setAdapter(adapter);
                    actPlayer1B.setAdapter(adapter);
                    actPlayer2B.setAdapter(adapter);
                    actPlayer3B.setAdapter(adapter);
                    actPlayer4B.setAdapter(adapter);
                    actPlayer5B.setAdapter(adapter);
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }

    /*

        Load player from database to actPlayer base on team name
        && load id photo for team
        typeTeam = 1 -> teamA
        typeTeam = 2 -> teamB
     */
    private void executeLoadPlayer(AutoCompleteTextView act, final int typeTeam) {
        final String teamName = act.getText().toString().trim();
        if (teamName.isEmpty() || teamName.equals(Constant.TBD)) {
            return;
        }
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("joindota/suggest_team_player/" + CommonUtils.escapeKey(teamName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    if (map != null && map.size() != 0) {
                        if (typeTeam == 1) {
                            tvPhotoA.setText(map.get("id_photo"));
                            int i = 1;
                            for (String str : map.keySet()) {
                                if (str.equals("id_photo")) {
                                    continue;
                                }
                                switch (i) {
                                    case 1:
                                        actPlayer1A.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 2:
                                        actPlayer2A.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 3:
                                        actPlayer3A.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 4:
                                        actPlayer4A.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 5:
                                        actPlayer5A.setText(CommonUtils.unescapeKey(str));
                                        break;
                                }
                                i++;
                            }
                        } else {
                            tvPhotoB.setText(map.get("id_photo"));
                            int i = 1;
                            for (String str : map.keySet()) {
                                if (str.equals("id_photo")) {
                                    continue;
                                }
                                switch (i) {
                                    case 1:
                                        actPlayer1B.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 2:
                                        actPlayer2B.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 3:
                                        actPlayer3B.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 4:
                                        actPlayer4B.setText(CommonUtils.unescapeKey(str));
                                        break;
                                    case 5:
                                        actPlayer5B.setText(CommonUtils.unescapeKey(str));
                                        break;
                                }
                                i++;
                            }
                        }

                    }
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
                Toast.makeText(getContext(), "no data for team " + teamName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // show dialog choice time for game
    private void showDialogChoiceTime(long time_start) {
        new DateTimeDialog(time_start, new DateTimeDialog.HandleClickOnDialog() {
            @Override
            public void handlePickerOk(long time) {
                AddProfessionMatchFragment.this.time = time;
                btnAddTime.setText(CommonUtils.convertintDateTimeToString(time));
            }
        }).show(getFragmentManager(), null);

    }
}
