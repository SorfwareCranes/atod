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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.dialog.DateTimeDialog;
import software.cranes.com.dota.dialog.GameDialogFragment;
import software.cranes.com.dota.dialog.SuggestDialogFragment;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GameModel;
import software.cranes.com.dota.model.LiveChanelModel;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.model.TeamModel;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


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
    private HashMap<String, String> nameImageHeroesMap;
    private TextView tvSumGames;

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
        loadHeroesData();
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
        tvSumGames = (TextView) view.findViewById(R.id.tvSumGames);

        edtPhotoA = (EditText) view.findViewById(R.id.edtPhotoA);
        edtPhotoB = (EditText) view.findViewById(R.id.edtPhotoB);
        btnLoadPhoto = (Button) view.findViewById(R.id.btnLoadPhoto);
        edtResultA = (EditText) view.findViewById(R.id.edtResultA);
        edtResultB = (EditText) view.findViewById(R.id.edtResultB);

        btnLoadGame.setOnClickListener(this);
        btnSuggestTeamA.setOnClickListener(this);
        btnSuggestTeamB.setOnClickListener(this);
        btnAddTime.setOnClickListener(this);
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
            edtMatchId.setEnabled(false);
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
                        if (matchModel.getSum() > 0) {
                            for (int i = 1; i <= matchModel.getSum(); i++) {
                                showCircleDialogOnly();
                                final int finalI = i;
                                FirebaseDatabase.getInstance().getReference("profession/games/" + matchModel.getId() + String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            GameModel model = dataSnapshot.getValue(GameModel.class);
                                            if (model != null) {
                                                model.setTmA(unescapeMap(model.getTmA()));
                                                model.setTmB(unescapeMap(model.getTmB()));
                                                gameModelMapOld.put(matchModel.getId() + String.valueOf(finalI), model);
                                                gameModelMapNew.put(matchModel.getId() + String.valueOf(finalI), model);
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
                loadModelFollowId(edtMatchId.getText().toString().trim());
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
                executeSave();
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

                if (list.get(i).getLa() != null) {
                    edtLanguageLive = (EditText) view.findViewById(R.id.edtLanguageLive);
                    edtLanguageLive.setText(list.get(i).getLa());
                }
                if (list.get(i).getLv() != null) {
                    edtLiveLink = (EditText) view.findViewById(R.id.edtLiveLink);
                    edtLiveLink.setText("https://www.youtube.com/watch?v=" + list.get(i).getLv());
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

    /*
        check number to input
        if game not have -> create game and add to gameModelMapNew
        if game have -> load game exits and add to gameModelMapNew
        afer that show list game to UI in linearlayout llListGames
        handle gameModelMapNew to compare gameModelMapOld to handle save, updte and delete data
      */
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
        GameDialogFragment gameDialogFragment = new GameDialogFragment(TYPE, edtMatchId.getText().toString() + gameId, gameModelMapNew, actTeamA.getText().toString(), actTeamB.getText().toString(), nameImageHeroesMap, new GameDialogFragment.HandleCreateGame() {
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

    /*
    show list game to ui base on gamemodel
     */
    private void addGamesToUi(final HashMap<String, GameModel> map) {
        while (llListGames.getChildCount() > 0) {
            llListGames.removeAllViews();
        }
        if (map != null && map.size() > 0) {
            TextView tv;
            Button btn;
            LayoutInflater inflater = getLayoutInflater(null);
            List<String> listKey = getListKeyFromMap(map);
            if (listKey != null) {
                for (final String key : listKey) {
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
                                tvSumGames.setText(map.size());
                            }
                        });
                        llListGames.addView(view);
                    }
                }
            }
        }
    }

    // set value for edittext base value of MatchModel Object
    private void setViewForUi(MatchModel model) {
        if (model.getTa() != null) {
            if (model.getTa().getNa() != null) {
                actTeamA.setText(model.getTa().getNa());
            }
            if (model.getTa().getPt() != null) {
                edtPhotoA.setText(model.getTa().getPt());
            }
        }

        if (model.getTb() != null) {
            if (model.getTb().getNa() != null) {
                actTeamB.setText(model.getTb().getPt());
            }
            if (model.getTb().getNa() != null) {
                edtPhotoB.setText(model.getTb().getPt());
            }
        }

        if (model.getBa() > 0) {
            edtOddsTeamA.setText(String.valueOf(model.getBa()));
        }
        if (model.getBb() > 0) {
            edtOddsTeamB.setText(String.valueOf(model.getBb()));
        }
        if (model.getBo() > 0) {
            edtBo.setText(String.valueOf(model.getBo()));
        }
        edtResultA.setText(String.valueOf(matchModel.getRa()));
        edtResultB.setText(String.valueOf(matchModel.getRb()));
        if (model.getTo() != null) {
            actTournament.setText(model.getTo());
        }
        if (model.getRo() != null) {
            actRound.setText(model.getRo());
        }
        if (model.getTime() > 0) {
            btnAddTime.setText(CommonUtils.convertintDateTimeToString(time));
        }
        if (model.getSt() == Constant.LIVE) {
            rbLive.setChecked(true);
        } else if (model.getSt() == Constant.END) {
            rbEnd.setChecked(true);
        } else if (model.getSt() == Constant.UPCOMING) {
            rbUpcoming.setChecked(true);
        }
        if (model.getLl() != null && model.getLl().size() > 0) {
            generateChanelLive(model.getLl().size(), model.getLl());
        }
        tvSumGames.setText(model.getSum());

    }


    // convert nameplayer and heroes image to heroes name
    private HashMap<String, String> unescapeMap(HashMap<String, String> map) {
        HashMap<String, String> result = new HashMap<>();
        if (map == null && map.isEmpty()) {
            return result;
        }
        for (String key : map.keySet()) {
            result.put(CommonUtils.unescapeKey(key), getHeroNameBaseOnImageValue(nameImageHeroesMap, map.get(key)));
        }
        return result;
    }

    // create data for imageNameHeroesMap;
        /*
        load heroes to map<String, String>
        player can choice heroes
     */
    private void loadHeroesData() {
        nameImageHeroesMap = new HashMap<>();
        String url = "http://www.dota2.com/heroes/";
        showCircleDialogOnly();
        SendRequest.requestGetJsoup(getContext(), url, new SendRequest.StringResponse() {
            @Override
            public void onSuccess(String data) {
                if (data != null) {
                    Elements elementAs = null;
                    Element elementImg = null;
                    String linkImg = null;
                    String[] arrLinkName, arrLinkImg = null;
                    Document document = Jsoup.parse(data);
                    Elements elementHeroIcons = document.select("div.heroIcons");
                    if (elementHeroIcons != null && elementHeroIcons.size() > 0) {
                        for (Element elementHeroIcon : elementHeroIcons) {
                            elementAs = elementHeroIcon.select("a.heroPickerIconLink");
                            if (elementAs != null && elementAs.size() > 0) {
                                for (Element elementA : elementAs) {
                                    // link = "http://www.dota2.com/hero/Earthshaker/"
                                    arrLinkName = elementA.attr("href").split("/");
                                    elementImg = elementA.select("img.heroHoverLarge").first();
                                    if (elementImg != null) {
                                        arrLinkImg = elementImg.attr("src").split("\\?")[0].split("/");
                                        if (arrLinkImg != null) {
                                            linkImg = arrLinkImg[arrLinkImg.length - 1];
                                        }
                                    }
                                    if (arrLinkName != null && arrLinkName.length > 0 && linkImg != null) {
                                        nameImageHeroesMap.put(arrLinkName[arrLinkName.length - 1], linkImg.substring(0, linkImg.length() - 12));
                                    }
                                }
                            }
                        }
                    }
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onFail(String err) {
                hideCircleDialogOnly();
            }
        });
    }

    // get key from value in map
    private String getHeroNameBaseOnImageValue(HashMap<String, String> map, String imgId) {
        if (map.isEmpty()) {
            return Constant.NO_IMAGE;
        }
        for (String key : map.keySet()) {
            if (map.get(key).equals(imgId)) {
                return key;
            }
        }
        return Constant.NO_IMAGE;
    }

    /*
    get list key from map<String, String>
    */
    private List<String> getListKeyFromMap(HashMap<String, GameModel> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (String str : map.keySet()) {
            result.add(str);
        }
        Collections.sort(result);
        return result;
    }

    /*
        1. match upcoming -> save to profession/upcoming
        2. match live -> save to profession/live

        3. match end -> save match to -> profession/match
                        save game to -> profession/games
        4. save match to -> profession/tour
        5. save match to -> profession/team
        6. save player name to profession/suggest/player
        7. save round name to profession/suggest/round
        8. save team name to profession/suggest/team
        9. save tour name to profession/suggest/tour
        // handle video
            1 game have two model video
         id model video : matchId + 1 + (f or h) data : link : videoid + time : 121212
        1. save all game video high to videoh
        2. save all game video full to videof
        3. save all game video full to videopf/playername
        4. save all game video high to videoph/playername
        5. save all game video high to videohh/heroes
        6. save all game video full to videohf/heroes
        7. save all game video high to videophf/playername/heroes
        8. save all game video full to videophh/playername/heroes
     */
    private void executeSave() {
        if (TYPE == Constant.CREATE_DATA) {

        } else {

        }
    }
}
