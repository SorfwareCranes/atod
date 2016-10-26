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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.dialog.DateTimeDialog;
import software.cranes.com.dota.dialog.GameDialogFragment;
import software.cranes.com.dota.dialog.SuggestDialogFragment;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GameModel;
import software.cranes.com.dota.model.LiveChanelModel;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.model.TeamModel;

import static android.R.attr.mode;
import static android.R.id.list;
import static android.os.Build.VERSION_CODES.M;


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
    private EditText edtResultA, edtResultB;
    private Button btnAddTime;
    private RadioButton rbEnd;
    private RadioButton rbLive;
    private RadioButton rbUpcoming;
    private EditText edtNumberChanel;
    private Button btnGenerateChanel;
    private EditText edtGameData;
    private Button btnInputData;
    private LinearLayout llChanelLive, llListGames;
    private Button btnBack;
    private Button btnDelete;
    private Button btnSave;
    private EditText edtPhotoA, edtPhotoB;
    private Button btnLoadPhoto;
    private String matchId;
    private int TYPE = Constant.CREATE_DATA;
    private FirebaseDatabase mFirebaseDatabase;
    private List<String> suggestTeam, suggestListTeam;
    private SuggestDialogFragment suggestDialogFragment;
    private long time;
    private HashMap<String, GameModel> gameModelMapOld, gameModelMapNew;
    private MatchModel matchModel;
    private TeamModel teamAModel, teamBModel;
    private Map<String, String> imageNameHeroesMap;

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
        edtGameData = (EditText) view.findViewById(R.id.edtGameData);
        btnInputData = (Button) view.findViewById(R.id.btnInputData);
        llChanelLive = (LinearLayout) view.findViewById(R.id.llChanelLive);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        llListGames = (LinearLayout) view.findViewById(R.id.llListGames);

        edtPhotoA = (EditText) view.findViewById(R.id.edtPhotoA);
        edtPhotoB = (EditText) view.findViewById(R.id.edtPhotoB);
        btnLoadPhoto = (Button) view.findViewById(R.id.btnLoadPhoto);
        edtResultA = (EditText) view.findViewById(R.id.edtResultA);
        edtResultB = (EditText) view.findViewById(R.id.edtResultB);

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
        // for actTournament
        loadDataAutoCompleteText(actTournament, "profession/suggest/tour");
        // for actRound
        loadDataAutoCompleteText(actRound, "profession/suggest/round");
        if (TYPE == Constant.LOAD_DATA) {
            edtMatchId.setText(matchId);
            setupForLoadMatch();
        } else {
            setupForCreateNew();
        }
    }

    private void setupForCreateNew() {
        // for actTeamA, actTeamB
        loadSuggestTeam();

    }

    private void setupForLoadMatch() {
        loadModelFollowId(matchId);
        if (actTeamA.getText().toString().isEmpty() || actTeamA.getText().toString().equals(Constant.TBD) || actTeamB.getText().toString().isEmpty() || actTeamB.getText().toString().equals(Constant.TBD)) {
            actTeamA.setEnabled(true);
            actTeamB.setEnabled(true);
            loadSuggestTeam();
        } else {
            actTeamA.setEnabled(false);
            actTeamB.setEnabled(false);
        }

        // from matchId -> load data for MatchModel
        // from MatchModel -> load data for gameModelMapOld
        // from gameModelMapOld -> uncapse playerName, convert photo_heroes to heroName
    }

    private void loadModelFollowId(String matchId) {
        gameModelMapOld = new HashMap<>();
        gameModelMapNew = new HashMap<>();
        showCircleDialog();
        FirebaseDatabase.getInstance().getReference("profession/match/" + matchId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    matchModel = dataSnapshot.getValue(MatchModel.class);
                    if (matchModel != null) {
                        setViewForUi(matchModel);
                        if (matchModel.getGames() != null && matchModel.getGames().size() > 0) {
                            for (final String gameId : matchModel.getGames()) {
                                showCircleDialogOnly();
                                FirebaseDatabase.getInstance().getReference("profession/games/" + gameId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            GameModel model = dataSnapshot.getValue(GameModel.class);
                                            if (model != null) {
                                                model.setTeamA(unescapeMap(model.getTeamA()));
                                                model.setTeamB(unescapeMap(model.getTeamB()));
                                                gameModelMapOld.put(gameId, model);
                                                gameModelMapNew.put(gameId, model);
                                            }
                                        }
                                        hideCircleDialogOnly();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        hideCircleDialogOnly();
                                    }
                                });
                            }
                        }
                    }
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Can't Load Data", Toast.LENGTH_SHORT).show();
                hideCircleDialogOnly();
            }
        });
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
                if (teamList != null && getActivity() != null) {
                    AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, teamList);
                    actText.setAdapter(adapter);
                }
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
            case R.id.btnAddTime:
                showDialogChoiceTime(time);
                break;
            case R.id.btnLoadPhoto:
                executeLoadPhotoId(actTeamA, Constant.A_WIN);
                executeLoadPhotoId(actTeamB, Constant.B_WIN);
                break;
            case R.id.btnGenerateChanel:
                generateChanel();
                break;
            case R.id.btnInputData:
                handleAddAGame();
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

    // for actTeamA, actTeamB
    private void loadSuggestTeam() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("profession/suggest/team").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestTeam = (List<String>) dataSnapshot.getValue();
                if (suggestTeam != null && getActivity() != null) {
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


    /*

        Load player from database to actPlayer base on team name
        && load id photo for team
        typeTeam = 1 -> teamA
        typeTeam = 2 -> teamB
     */
    private void executeLoadPhotoId(AutoCompleteTextView act, final int typeTeam) {
        final String teamName = act.getText().toString().trim();
        if (teamName.isEmpty() || teamName.equals(Constant.TBD)) {
            return;
        }
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("joindota/suggest_team_player/" + CommonUtils.escapeKey(teamName) + "/id_photo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    String value = (String) dataSnapshot.getValue();
                    if (typeTeam == Constant.A_WIN) {
                        edtPhotoA.setText(value);
                    } else {
                        edtPhotoB.setText(value);
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

    // handle check number input when generateChanel
    private void generateChanel() {
        int number;
        try {
            number = Integer.valueOf(edtNumberChanel.getText().toString().trim());
        } catch (NumberFormatException ex) {
            edtNumberChanel.setText(Constant.NO_IMAGE);
            edtNumberChanel.setError("Required");
            return;
        }
        if (number > 0) {
            generateChanelLive(number, null);
        }
    }

    // add number Chanel to Layout : if (list != null : use for load data)
    private void generateChanelLive(int number, List<LiveChanelModel> list) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Button btnDeleteChanel;
        EditText edtLiveLink, edtLanguageLive;
        for (int i = 0; i < number; i++) {
            final View view = inflater.inflate(R.layout.live_chanel_layout, null, false);
            if (list != null && list.size() > i && list.get(i) != null) {

                if (list.get(i).getLanguage() != null) {
                    edtLanguageLive = (EditText) view.findViewById(R.id.edtLanguageLive);
                    edtLanguageLive.setText(list.get(i).getLanguage());
                }
                if (list.get(i).getVideoId() != null) {
                    edtLiveLink = (EditText) view.findViewById(R.id.edtLiveLink);
                    edtLiveLink.setText("https://www.youtube.com/watch?v=" + list.get(i).getVideoId());
                }
            }

            btnDeleteChanel = (Button) view.findViewById(R.id.btnDeleteChanel);
            btnDeleteChanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llChanelLive.removeView(view);
                }
            });
            llChanelLive.addView(view);
        }
        edtNumberChanel.setText(Constant.NO_IMAGE);
    }

    // check number to input
    private void handleAddAGame() {
        String gameId = edtGameData.getText().toString().trim();
        try {
            Integer.valueOf(gameId);
        } catch (NumberFormatException ex) {
            edtGameData.setText(Constant.NO_IMAGE);
            edtGameData.setError("Requried");
            return;
        }
        if (!validateInputData()) {
            return;
        }
        if (actTeamA.getText().toString().trim().equals("TBD") || actTeamB.getText().toString().trim().equals("TBD")) {
            Toast.makeText(getContext(), "team name not validate", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((gameModelMapNew == null || gameModelMapNew.isEmpty()) && Integer.valueOf(gameId) != 1) {
            Toast.makeText(getContext(), "you mut add game 1 first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gameModelMapNew == null) {
            gameModelMapNew = new HashMap<>();
        }
        if (gameModelMapOld == null) {
            gameModelMapOld = new HashMap<>();
        }
        GameDialogFragment gameDialogFragment = new GameDialogFragment(TYPE, edtMatchId.getText().toString() + gameId, gameModelMapNew, actTeamA.getText().toString(), actTeamB.getText().toString(), new GameDialogFragment.HandleCreateGame() {
            @Override
            public void executeAddGame(int type, String id, GameModel gameModel) {
                gameModelMapNew.put(id, gameModel);
                // so list game to screen
                addGamesToUi(gameModelMapNew);
            }
        });
        gameDialogFragment.show(getFragmentManager(), null);
    }

    // validate value input with no data player name
    private boolean validateInputData() {
        String requried = "requried";
        if (actTeamA.getText().toString().trim().isEmpty()) {
            actTeamA.setError(requried);
            return false;
        }
        if (actTeamB.getText().toString().trim().isEmpty()) {
            actTeamB.setError(requried);
            return false;
        }
        if (actTournament.getText().toString().trim().isEmpty()) {
            actTournament.setError(requried);
            return false;
        }
        if (actRound.getText().toString().trim().isEmpty()) {
            actRound.setError(requried);
            return false;
        }
        if (edtBo.getText().toString().trim().isEmpty()) {
            edtBo.setError(requried);
            return false;
        }
        if (btnAddTime.getText().toString().startsWith("T")) {
            Toast.makeText(getContext(), "Set Time Please", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!rbEnd.isChecked() && !rbLive.isChecked() && !rbUpcoming.isChecked()) {
            Toast.makeText(getContext(), "Set Live or End or Upcoming", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPhotoA.getText().toString().trim().startsWith("P") || edtPhotoB.getText().toString().trim().startsWith("P")) {
            Toast.makeText(getContext(), "Set Photo ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addGamesToUi(final HashMap<String, GameModel> map) {
        while (llListGames.getChildCount() > 0) {
            llListGames.removeAllViews();
        }
        if (map != null && map.size() > 0) {
            TextView tv;
            Button btn;
            LayoutInflater inflater = getLayoutInflater(null);
            for (final String key : map.keySet()) {
                if (inflater != null) {
                    final View view = inflater.inflate(R.layout.list_game_layout, null, false);
                    tv = (TextView) view.findViewById(R.id.tvGameId);
                    btn = (Button) view.findViewById(R.id.btnGameDelete);
                    tv.setText(key);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            map.remove(key);
                            llListGames.removeView(view);
                        }
                    });
                    llListGames.addView(view);
                }
            }
        }
    }

    // set value for edittext base value of MatchModel Object
    private void setViewForUi(MatchModel model) {
        if (model.getTeamA() != null) {
            if (model.getTeamA().getName() != null) {
                actTeamA.setText(model.getTeamA().getName());
            }
            if (model.getTeamA().getPhotoId() != null) {
                edtPhotoA.setText(model.getTeamA().getPhotoId());
            }
        }

        if (model.getTeamA() != null) {
            if (model.getTeamB().getName() != null) {
                actTeamB.setText(model.getTeamA().getName());
            }
            if (model.getTeamB().getPhotoId() != null) {
                edtPhotoB.setText(model.getTeamA().getPhotoId());
            }
        }

        if (model.getBeta() > 0) {
            edtOddsTeamA.setText(String.valueOf(model.getBeta()));
        }
        if (model.getBetb() > 0) {
            edtOddsTeamB.setText(String.valueOf(model.getBetb()));
        }
        if (model.getBo() > 0) {
            edtBo.setText(String.valueOf(model.getBo()));
        }
        edtResultA.setText(String.valueOf(matchModel.getRa()));
        edtResultB.setText(String.valueOf(matchModel.getRb()));
        if (model.getTour() != null) {
            actTournament.setText(model.getTour());
        }
        if (model.getRound() != null) {
            actRound.setText(model.getRound());
        }
        if (model.getTime() > 0) {
            btnAddTime.setText(CommonUtils.convertintDateTimeToString(time));
        }
        if (model.getStatus() == Constant.LIVE) {
            rbLive.setChecked(true);
        } else if (model.getStatus() == Constant.END) {
            rbEnd.setChecked(true);
        } else if (model.getStatus() == Constant.UPCOMING) {
            rbUpcoming.setChecked(true);
        }
        if (model.getLiveList() != null && model.getLiveList().size() > 0) {
            generateChanelLive(model.getLiveList().size(), model.getLiveList());
        }

    }


    // convert nameplayer and heroes image to heroes name
    private HashMap<String, String> unescapeMap(HashMap<String, String> map) {
        HashMap<String, String> result = new HashMap<>();
        if (map == null && map.isEmpty()) {
            return result;
        }
        for (String key : map.keySet()) {
            result.put(CommonUtils.unescapeKey(key), imageNameHeroesMap.get(key));
        }
        return result;
    }
    // create data for imageNameHeroesMap;
}
