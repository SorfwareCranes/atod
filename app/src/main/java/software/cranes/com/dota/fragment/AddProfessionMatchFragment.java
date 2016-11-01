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
import java.util.Map;
import java.util.Objects;

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
import software.cranes.com.dota.model.MatchTeamModel;
import software.cranes.com.dota.model.TeamModel;
import software.cranes.com.dota.model.TourModel;
import software.cranes.com.dota.model.VideoModel;

import static com.google.android.gms.internal.zzams.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProfessionMatchFragment extends BaseFragment implements View.OnClickListener {
    private EditText edtMatchId;
    private Button btnLoadGame;
    private AutoCompleteTextView actTeamA, actTeamB, actTournament, actRound, actLocal;
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
    private Button btnReset;
    private EditText edtPhotoA, edtPhotoB;
    private Button btnLoadPhoto;
    private String matchId;
    private int TYPE = Constant.CREATE_DATA;
    private FirebaseDatabase mFirebaseDatabase;
    private List<String> suggestTeam, suggestListTeam;
    private SuggestDialogFragment suggestDialogFragment;
    private long time;
    private HashMap<String, GameModel> gameModelMapOld, gameModelMapNew, gameModelMapSave;
    private MatchModel matchModel;
    private HashMap<String, String> nameImageHeroesMap;
    private TextView tvSumGames;
    private List<String> listTour, listRo, listPl, listLo;

    public AddProfessionMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            matchId = getArguments().getString(Constant.DATA);
            if (matchId != null && !matchId.equals(Constant.NO_IMAGE)) {
                TYPE = Constant.LOAD_DATA;
            }
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();

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
        btnReset = (Button) view.findViewById(R.id.btnReset);
        actLocal = (AutoCompleteTextView) view.findViewById(R.id.actLocal);

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
        btnReset.setOnClickListener(this);
        setupUi();
    }

    /*
        load data for actTeamA, actTeamB, actTournament, actRound;
        if type = load_data ->
            actTournament and actRound not load
     */
    private void setupUi() {
        // for actTournament
        loadDataAutoCompleteText(actTournament, "pro/suggest/tour", 1);
        // for actLocal
        loadDataAutoCompleteText(actLocal, "pro/suggest/local", 3);
        // for actRound
        loadDataAutoCompleteText(actRound, "pro/suggest/round", 2);

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
        loadSuggestPlayer();

    }

    private void setupForLoadMatch() {
        loadModelFollowId(matchId);

//        if (actTeamA.getText().toString().isEmpty() || actTeamA.getText().toString().equals(Constant.TBD) || actTeamB.getText().toString().isEmpty() || actTeamB.getText().toString().equals(Constant.TBD)) {
//            actTeamA.setEnabled(true);
//            actTeamB.setEnabled(true);
//            loadSuggestTeam();
//        } else {
//            actTeamA.setEnabled(false);
//            actTeamB.setEnabled(false);
//        }

        // from matchId -> load data for MatchModel
        // from MatchModel -> load data for gameModelMapOld
        // from gameModelMapOld -> uncapse playerName, convert photo_heroes to heroName
    }

    private void loadModelFollowId(String matchId) {
        gameModelMapOld = new HashMap<>();
        gameModelMapNew = new HashMap<>();
        showCircleDialog();
        FirebaseDatabase.getInstance().getReference("pro/match/" + matchId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    matchModel = dataSnapshot.getValue(MatchModel.class);
                    if (matchModel != null) {
                        setViewForUi(matchModel);
                        TYPE = Constant.LOAD_DATA;
                        if (matchModel.getSum() > 0) {
                            for (int i = 1; i <= matchModel.getSum(); i++) {
                                showCircleDialogOnly();
                                final int finalI = i;
                                FirebaseDatabase.getInstance().getReference("pro/games").child(matchModel.getId() + String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            GameModel model = dataSnapshot.getValue(GameModel.class);
                                            if (model != null) {
                                                model.setTmA(unescapeMap(model.getTmA()));
                                                model.setTmB(unescapeMap(model.getTmB()));
                                                gameModelMapOld.put(matchModel.getId() + String.valueOf(finalI), model);
                                                gameModelMapNew.put(matchModel.getId() + String.valueOf(finalI), model);
                                                if (finalI == matchModel.getSum()) {
                                                    addGamesToUi(gameModelMapNew);
                                                }
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
    private void loadDataAutoCompleteText(final AutoCompleteTextView actText, String pathData, final int type) {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference(pathData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> teamList = (List<String>) dataSnapshot.getValue();
                if (teamList != null && getActivity() != null) {
                    if (type == 1) {
                        listTour = teamList;
                    } else if (type == 2) {
                        listRo = teamList;
                    } else if (type == 3) {
                        listLo = teamList;
                    }
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
                getFragmentManager().popBackStack();
                break;
            case R.id.btnDelete:
                deleteMatch();
//                executeDelete();
                break;
            case R.id.btnSave:
                executeSaveNewCode();
//                executeSave();
                break;
            case R.id.btnReset:
                resetUi();
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
        mFirebaseDatabase.getReference("pro/suggest/team").addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void loadSuggestPlayer() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pro/suggest/pl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideCircleDialogOnly();
                if (dataSnapshot != null) {
                    listPl = (List<String>) dataSnapshot.getValue();
                }
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
        if (rbUpcoming.isChecked()) {
            Toast.makeText(getContext(), "upcoming can have a game", Toast.LENGTH_SHORT).show();
            return;
        }
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
                                tvSumGames.setText(String.valueOf(map.size()));
                            }
                        });
                        llListGames.addView(view);
                    }
                }
            }
            tvSumGames.setText(String.valueOf(map.size()));
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
                actTeamB.setText(model.getTb().getNa());
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
        if (model.getLo() != null) {
            actLocal.setText(model.getLo());
        }
        if (model.getTime() > 0) {
            time = model.getTime();
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
        tvSumGames.setText(String.valueOf(model.getSum()));

    }


    // convert nameplayer and heroes image to heroes name
    private HashMap<String, String> unescapeMap(HashMap<String, String> map) {
        HashMap<String, String> result = new HashMap<>();
        if (map == null || map.isEmpty()) {
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
        1. match upcoming -> save to pro/upcoming
        2. match live -> save to pro/live

        3. match end -> save match to -> pro/match
                        save game to -> pro/games
        4. save match to -> pro/tour
        5. save match to -> pro/team
        6. save player name to pro/suggest/player
        7. save round name to pro/suggest/round
        8. save team name to pro/suggest/team
        9. save tour name to pro/suggest/tour
        // handle video
            1 game have two model video
         id model video : matchId + 1 + (f or h) data : link : videoid + time : 121212
        1. save all game video high to vih
        2. save all game video full to vif
        3. save all game video full to vipf/playername
        4. save all game video high to viph/playername
        5. save all game video high to vihh/heroes
        6. save all game video full to vihf/heroes
        7. save all game video high to viphf/playername/heroes
        8. save all game video full to viphh/playername/heroes
     */
//    private void executeSave() {
//        if (!validateInputData()) {
//            return;
//        }
//        DatabaseReference reference;
//        OnCustomCompleteListener listener = new OnCustomCompleteListener();
//        String matchNewId;
//        if (TYPE == Constant.CREATE_DATA) {
//
//            // save for upcoming
//            if (rbUpcoming.isChecked()) {
//                showCircleDialogOnly();
//                // create upcoming model -> save to tour, upcoming
//                // match model no have games -> sum = 0
//                reference = mFirebaseDatabase.getReference("pro/upcoming");
//                matchNewId = reference.push().getKey();
//                // create model
//                matchModel = createMatchModel(Constant.UPCOMING, matchNewId);
//                // save suggest team, tournament, ro
//                if (!matchModel.getTa().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTa().getNa(), suggestTeam, 1);
//                }
//                if (!matchModel.getTb().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTb().getNa(), suggestTeam, 1);
//                }
//                // save suggest tour
//                saveSuggest(matchModel.getTo(), listTour, 3);
//                // save suggest ro
//                saveSuggest(matchModel.getRo(), listRo, 4);
//                // save to "pro/upcoming"
//                reference.child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//                // save to "pro/tour"
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(new OnEndCompleteListiner());
////                // save to "pro/match"
////                showCircleDialogOnly();
////                mFirebaseDatabase.getReference("pro/match").child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//
//            } else if (rbLive.isChecked()) {
//                showCircleDialogOnly();
//                reference = mFirebaseDatabase.getReference("pro/live");
//                // create model
//                matchNewId = reference.push().getKey();
//                matchModel = createMatchModel(Constant.LIVE, matchNewId);
//                // save suggest team, tournament, ro
//                if (!matchModel.getTa().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTa().getNa(), suggestTeam, 1);
//                }
//                if (!matchModel.getTb().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTb().getNa(), suggestTeam, 1);
//                }
//                // save suggest tour
//                saveSuggest(matchModel.getTo(), listTour, 3);
//                // save suggest ro
//                saveSuggest(matchModel.getRo(), listRo, 4);
//                // save suggest player
//                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
//                    GameModel game = gameModelMapSave.get(matchModel.getId() + 1);
//                    if (game != null) {
//                        HashMap<String, String> mapA = game.getTmA();
//                        HashMap<String, String> mapB = game.getTmB();
//
//                        if (mapA != null && mapA.size() > 0 && mapB != null && mapB.size() > 0) {
//                            for (String str : mapA.keySet()) {
//                                saveSuggest(str, listPl, 2);
//                            }
//                            for (String str : mapB.keySet()) {
//                                saveSuggest(str, listPl, 2);
//                            }
//                        }
//
//                    }
//                }
//                // save games
//                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
//                    DatabaseReference ref = mFirebaseDatabase.getReference("pro/games");
//                    GameModel gameModelSave;
//                    for (String gameId : gameModelMapSave.keySet()) {
//                        gameModelSave = gameModelMapSave.get(gameId);
//                        ref.child(gameId).setValue(convertNameAndHeroes(gameModelSave));
//                        // save video
//                        saveVideoFollowGameModel(gameModelSave, gameId);
//                    }
//                }
//                // save matchModel to live
//                reference.child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//                // save matchModel to tour
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(new OnEndCompleteListiner());
//
//            } else if (rbEnd.isChecked()) {
//                if (!validateInputTeamName()) {
//                    actTeamA.setError("requried");
//                    return;
//                }
//                reference = mFirebaseDatabase.getReference("pro/match");
//                matchNewId = reference.push().getKey();
//                matchModel = createMatchModel(Constant.END, matchNewId);
//
//                showCircleDialogOnly();
//                // save suggest team, tournament, ro
//                if (!matchModel.getTa().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTa().getNa(), suggestTeam, 1);
//                }
//                if (!matchModel.getTb().getNa().equals(Constant.TBD)) {
//                    saveSuggest(matchModel.getTb().getNa(), suggestTeam, 1);
//                }
//                // save suggest tour
//                saveSuggest(matchModel.getTo(), listTour, 3);
//                // save suggest ro
//                saveSuggest(matchModel.getRo(), listRo, 4);
//                // save suggest player
//                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
//                    GameModel game = gameModelMapSave.get(matchModel.getId() + 1);
//                    if (game != null) {
//                        HashMap<String, String> mapA = game.getTmA();
//                        HashMap<String, String> mapB = game.getTmB();
//
//                        if (mapA != null && mapA.size() > 0 && mapB != null && mapB.size() > 0) {
//                            for (String str : mapA.keySet()) {
//                                saveSuggest(str, listPl, 2);
//                            }
//                            for (String str : mapB.keySet()) {
//                                saveSuggest(str, listPl, 2);
//                            }
//                        }
//
//                    }
//                }
//                // save games
//                if (gameModelMapSave != null && !gameModelMapSave.isEmpty()) {
//                    DatabaseReference ref = mFirebaseDatabase.getReference("pro/games");
//                    GameModel gameModelSave;
//                    for (String gameId : gameModelMapSave.keySet()) {
//                        gameModelSave = gameModelMapSave.get(gameId);
//                        ref.child(gameId).setValue(convertNameAndHeroes(gameModelSave));
//                        // save video
//                        saveVideoFollowGameModel(gameModelSave, gameId);
//                    }
//                }
//                // save to match
//                reference.child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//                // save to team
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTa().getNa())).child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTb().getNa())).child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(listener);
//                // save to tour
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).setValue(matchModel).addOnCompleteListener(new OnEndCompleteListiner());
//            }
//        } else if (TYPE == Constant.LOAD_DATA) {
//            MatchModel matchModelSave;
//
//            if (rbUpcoming.isChecked()) {
//                showCircleDialogOnly();
//                // create upcoming model -> save to tour, upcoming
//                // match model no have games -> sum = 0
//                reference = mFirebaseDatabase.getReference("pro/upcoming");
//                // create model
//                matchModelSave = createMatchModel(Constant.UPCOMING, null);
//                if (matchModel.getSt() == Constant.LIVE || matchModel.getSt() == Constant.END) {
//                    if (matchModel.getSt() == Constant.LIVE) {
//                        mFirebaseDatabase.getReference("pro/live").child(matchModel.getId()).removeValue();
//                    } else {
//                        mFirebaseDatabase.getReference("pro/match").child(matchModel.getId()).removeValue();
//                        if (!matchModel.getTa().getNa().equals(Constant.TBD)) {
//                            mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTa().getNa())).child(matchModel.getId()).removeValue();
//                        }
//                        if (!matchModel.getTb().getNa().equals(Constant.TBD)) {
//                            mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTb().getNa())).child(matchModel.getId()).removeValue();
//                        }
//                    }
//                    if (gameModelMapOld != null && gameModelMapOld.size() > 0) {
//                        for (String gameId : gameModelMapOld.keySet()) {
//                            mFirebaseDatabase.getReference("pro/games").child(gameId).removeValue();
//                            GameModel game = gameModelMapOld.get(gameId);
//                            deleteVideoFollowGameModel(game, gameId);
//                        }
//                    }
//                }
//                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
//                    mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).removeValue();
//                }
//                // save to "pro/upcoming"
//                reference.child(matchModel.getId()).setValue(matchModelSave).addOnCompleteListener(listener);
//                // save to "pro/tour"
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModelSave.getTo())).child(matchModel.getId()).setValue(matchModelSave).addOnCompleteListener(new OnEndCompleteListiner());
//            } else if (rbLive.isChecked()) {
//                showCircleDialogOnly();
//                reference = mFirebaseDatabase.getReference("pro/live");
//                // create model
//                matchModelSave = createMatchModel(Constant.LIVE, null);
//                if (matchModel.getSt() == Constant.UPCOMING) {
//                    mFirebaseDatabase.getReference("pro/upcoming").child(matchModel.getId()).removeValue();
//                } else if (matchModel.getSt() == Constant.END || matchModel.getSt() == Constant.LIVE) {
//                    if (matchModel.getSt() == Constant.END) {
//                        mFirebaseDatabase.getReference("pro/match").child(matchModel.getId()).removeValue();
//                        mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTa().getNa())).child(matchModel.getId()).removeValue();
//                        mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTb().getNa())).child(matchModel.getId()).removeValue();
//                    }
//                    if (matchModel.getSum() > matchModelSave.getSum()) {
//                        for (int i = matchModel.getSum(); i > matchModelSave.getSum(); i--) {
//                            GameModel game = gameModelMapOld.get(matchModel.getId() + i);
//                            if (game != null) {
//                                mFirebaseDatabase.getReference("pro/games").child(matchModel.getId() + i).removeValue();
//                                deleteVideoFollowGameModel(game, matchModel.getId() + i);
//                            }
//                        }
//                    }
//                }
//                // save games
//                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
//                    DatabaseReference ref = mFirebaseDatabase.getReference("pro/games");
//                    GameModel gameModelSave;
//                    for (String gameId : gameModelMapSave.keySet()) {
//                        gameModelSave = gameModelMapSave.get(gameId);
//                        ref.child(gameId).setValue(convertNameAndHeroes(gameModelSave));
//                        // save video
//                        saveVideoFollowGameModel(gameModelSave, gameId);
//                    }
//                }
//                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
//                    mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).removeValue();
//                }
//                // save matchModel to live
//                reference.child(matchModel.getId()).setValue(matchModelSave).addOnCompleteListener(listener);
//                // save matchModel to tour
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModelSave.getTo())).child(matchModel.getId()).setValue(matchModelSave).addOnCompleteListener(new OnEndCompleteListiner());
//            } else if (rbEnd.isChecked()) {
//                if (!validateInputTeamName()) {
//                    actTeamA.setError("requried");
//                    return;
//                }
//                showCircleDialogOnly();
//                reference = mFirebaseDatabase.getReference("pro/match");
//                matchModelSave = createMatchModel(Constant.END, null);
//                if (matchModel.getSt() == Constant.UPCOMING) {
//                    mFirebaseDatabase.getReference("pro/upcoming").child(matchModel.getId()).removeValue();
//                } else if (matchModel.getSt() == Constant.LIVE || matchModel.getSt() == Constant.END) {
//                    if (matchModel.getSt() == Constant.LIVE) {
//                        mFirebaseDatabase.getReference("pro/live").child(matchModel.getId()).removeValue();
//                    } else {
//                        if (!matchModel.getTa().getNa().equals(matchModelSave.getTa().getNa())) {
//                            mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTa().getNa())).child(matchModel.getId()).removeValue();
//                        }
//                        if (!matchModel.getTb().getNa().equals(matchModelSave.getTb().getNa())) {
//                            mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTb().getNa())).child(matchModel.getId()).removeValue();
//                        }
//                    }
//                    if (matchModel.getSum() > matchModelSave.getSum()) {
//                        for (int i = matchModel.getSum(); i > matchModelSave.getSum(); i--) {
//                            GameModel game = gameModelMapOld.get(matchModel.getId() + i);
//                            if (game != null) {
//                                mFirebaseDatabase.getReference("pro/games").child(matchModel.getId() + i).removeValue();
//                                deleteVideoFollowGameModel(game, matchModel.getId() + i);
//                            }
//                        }
//                    }
//                }
//                // save games
//                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
//                    DatabaseReference ref = mFirebaseDatabase.getReference("pro/games");
//                    GameModel gameModelSave;
//                    for (String gameId : gameModelMapSave.keySet()) {
//                        gameModelSave = gameModelMapSave.get(gameId);
//                        ref.child(gameId).setValue(convertNameAndHeroes(gameModelSave));
//                        // save video
//                        saveVideoFollowGameModel(gameModelSave, gameId);
//                    }
//                }
//                // save to match
//                reference.child(matchModelSave.getId()).setValue(matchModelSave).addOnCompleteListener(listener);
//                // save to team
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModelSave.getTa().getNa())).child(matchModelSave.getId()).setValue(matchModelSave).addOnCompleteListener(listener);
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModelSave.getTb().getNa())).child(matchModelSave.getId()).setValue(matchModelSave).addOnCompleteListener(listener);
//                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
//                    mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).removeValue();
//                }
//                // save to tour
//                showCircleDialogOnly();
//                mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModelSave.getTo())).child(matchModelSave.getId()).setValue(matchModelSave).addOnCompleteListener(new OnEndCompleteListiner());
//            }
//
//        }
//    }

    //    private void executeDelete() {
//        if (TYPE == Constant.LOAD_DATA && matchModel != null) {
//            showCircleDialogOnly();
//            if (matchModel.getSt() == Constant.LIVE) {
//                mFirebaseDatabase.getReference("pro/upcoming").child(matchModel.getId()).removeValue();
//
//            } else if (matchModel.getSt() == Constant.END || matchModel.getSt() == Constant.LIVE) {
//                if (matchModel.getSt() == Constant.LIVE) {
//                    mFirebaseDatabase.getReference("pro/live").child(matchModel.getId()).removeValue();
//                }
//                if (matchModel.getSt() == Constant.END) {
//                    //  delete match
//                    mFirebaseDatabase.getReference("pro/match").child(matchModel.getId()).removeValue();
//                    // delete team
//                    mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTa().getNa())).child(matchModel.getId()).removeValue();
//                    mFirebaseDatabase.getReference("pro/team").child(CommonUtils.escapeKey(matchModel.getTb().getNa())).child(matchModel.getId()).removeValue();
//                }
//                if (gameModelMapOld != null && !gameModelMapOld.isEmpty()) {
//                    for (String gameId : gameModelMapOld.keySet()) {
//                        mFirebaseDatabase.getReference("pro/games").child(gameId).removeValue();
//                        GameModel game = gameModelMapOld.get(gameId);
//                        deleteVideoFollowGameModel(game, gameId);
//                    }
//                }
//            }
//
//            mFirebaseDatabase.getReference("pro/tour").child(CommonUtils.escapeKey(matchModel.getTo())).child(matchModel.getId()).removeValue().addOnCompleteListener(new OnEndCompleteListiner());
//        }
//    }
//
    private void deleteMatch() {
        if (TYPE == Constant.LOAD_DATA && matchModel != null) {
            showCircleDialogOnly();
            Map<String, Object> mapData = new HashMap<>();
            if (matchModel.getSt() == Constant.LIVE) {
                mapData.put(new StringBuilder("pro/upcoming/").append(matchModel.getId()).toString(), null);
            } else if (matchModel.getSt() == Constant.END || matchModel.getSt() == Constant.LIVE) {
                if (matchModel.getSt() == Constant.LIVE) {
                    mapData.put(new StringBuilder("pro/live/").append(matchModel.getId()).toString(), null);
                }
                if (matchModel.getSt() == Constant.END) {
                    //  delete match
                    mapData.put(new StringBuilder("pro/match/").append(matchModel.getId()).toString(), null);
                    // delete team
                    mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTa().getNa())).append("/").append(matchModel.getId()).toString(), null);
                    mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTb().getNa())).append("/").append(matchModel.getId()).toString(), null);
                }
                if (gameModelMapOld != null && !gameModelMapOld.isEmpty()) {
                    for (String gameId : gameModelMapOld.keySet()) {
                        mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), null);
                        GameModel game = gameModelMapOld.get(gameId);
                        deleteVideoForMatch(game, gameId, mapData);
                    }
                }
            }
            mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append("/").append(matchModel.getId()).toString(), null);
            mFirebaseDatabase.getReference().updateChildren(mapData, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    hideCircleDialogOnly();
                    if (databaseError != null) {
                        showWarningDialog(databaseError.getMessage());
                    } else {
                        Toast.makeText(getContext(), "DELETE OK", Toast.LENGTH_SHORT).show();
                        resetUi();
                    }
                }
            });
        }
    }

    // create new MatchModel from UI
    private MatchModel createMatchModel(int type, String matchNewId) {
        MatchModel model = new MatchModel();
        //1
        model.setBa(Integer.valueOf(edtOddsTeamA.getText().toString()));
        //2
        model.setBb(Integer.valueOf(edtOddsTeamB.getText().toString()));
        //3
        model.setBo(Integer.valueOf(edtBo.getText().toString()));
        //4
        model.setTo(actTournament.getText().toString().trim());
        //5
        model.setRo(actRound.getText().toString().trim());
        //6
        model.setLo(actLocal.getText().toString().trim());
        //8
        model.setSt(type);
        //9
        model.setTime(time);
        TeamModel teamAModel = new TeamModel(actTeamA.getText().toString(), edtPhotoA.getText().toString().equals(Constant.NO_IMAGE) ? null : edtPhotoA.getText().toString());
        TeamModel teamBModel = new TeamModel(actTeamB.getText().toString(), edtPhotoB.getText().toString().equals(Constant.NO_IMAGE) ? null : edtPhotoB.getText().toString());
        //10
        model.setTa(teamAModel);
        //11
        model.setTb(teamBModel);
        if (type == Constant.UPCOMING) {
            model.setSum(0);
            //12
            if (TYPE == Constant.CREATE_DATA) {
                model.setId(matchNewId);
            } else {
                model.setId(matchModel.getId());
            }

            return model;
        } else if (type == Constant.LIVE || type == Constant.END) {
            //13
            if (!tvSumGames.getText().toString().equals(Constant.NO_IMAGE)) {
                model.setSum(Integer.valueOf(tvSumGames.getText().toString()));
            }
            //14
            if (TYPE == Constant.CREATE_DATA) {
                model.setId(matchNewId);
            } else {
                model.setId(matchModel.getId());
            }
            //15
            model.setRa(Integer.valueOf(edtResultA.getText().toString()));
            //16
            model.setRb(Integer.valueOf(edtResultB.getText().toString()));
            if (llChanelLive.getChildCount() > 0 || type == Constant.LIVE) {
                List<LiveChanelModel> list = new ArrayList<>();
                LiveChanelModel liveModel;
                EditText edtLiveLink, edtLanguageLive;
                String link;
                for (int i = 0; i < llChanelLive.getChildCount(); i++) {
                    edtLiveLink = (EditText) llChanelLive.getChildAt(i).findViewById(R.id.edtLiveLink);
                    link = edtLiveLink.getText().toString().trim();
                    edtLanguageLive = (EditText) llChanelLive.getChildAt(i).findViewById(R.id.edtLanguageLive);
                    if (CommonUtils.isYoutube(link) && CommonUtils.extractVideoIdFromUrl(link) != null) {
                        liveModel = new LiveChanelModel();
                        liveModel.setLv(CommonUtils.extractVideoIdFromUrl(link));
                        liveModel.setLa(edtLanguageLive.getText().toString().trim());
                        list.add(liveModel);
                    }
                }
                //17
                model.setLl(list);
            }
            if (model.getSum() > 0 && gameModelMapNew != null && gameModelMapNew.size() > 0) {
                gameModelMapSave = new HashMap<>();
                TextView tv;
                for (int i = 0; i < llListGames.getChildCount(); i++) {
                    tv = (TextView) llListGames.getChildAt(i).findViewById(R.id.tvGameId);
                    GameModel game = gameModelMapNew.get(tv.getText().toString());
                    if (game != null) {
                        gameModelMapSave.put(model.getId() + (i + 1), game);
                    }
                }
            }
            return model;
        }
        return model;

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

        if (actTeamA.getText().toString().trim().equals(actTeamB.getText().toString().trim()) && !actTeamA.getText().toString().trim().equals(Constant.TBD)) {
            actTeamA.setError(requried);
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

//    // type == 1 -> save suggest team
//    // type == 2 -> save suggest player
//    // type == 3 -> save suggest tourn
//    // type == 4 -> save suggest ro
//    private void saveSuggest(String s, List<String> list, int type) {
//        String path;
//        switch (type) {
//            case 1:
//                path = "pro/suggest/team";
//                break;
//            case 2:
//                path = "pro/suggest/pl";
//                break;
//            case 3:
//                path = "pro/suggest/tour";
//                break;
//            default:
//                path = "pro/suggest/round";
//                break;
//        }
//        if (list == null) {
//            list = new ArrayList<>();
//            list.add(s);
//            mFirebaseDatabase.getReference(path).setValue(list);
//            return;
//        }
//        if (!list.contains(s)) {
//            mFirebaseDatabase.getReference(path + "/" + list.size()).setValue(s);
//            list.add(s);
//            return;
//        }
//    }

    private GameModel convertNameAndHeroes(GameModel model) {
        if (model == null) {
            return null;
        }
        GameModel result = new GameModel();
        result.setLh(model.getLh());
        result.setLf(model.getLf());
        result.setRs(model.getRs());

        if (model.getTmA() != null && nameImageHeroesMap != null) {
            HashMap<String, String> resultA = new HashMap<>();
            for (String key : model.getTmA().keySet()) {
                resultA.put(CommonUtils.escapeKey(key), nameImageHeroesMap.get(model.getTmA().get(key)));
            }
            result.setTmA(resultA);
        }
        if (model.getTmB() != null && nameImageHeroesMap != null) {
            HashMap<String, String> resultB = new HashMap<>();
            for (String key : model.getTmB().keySet()) {
                resultB.put(CommonUtils.escapeKey(key), nameImageHeroesMap.get(model.getTmB().get(key)));
            }
            result.setTmB(resultB);
        }
        return result;
    }

    // 1. save to vif
    // 2. save to vipf
    // 3. save to vihf
    // 4. save to viphf
    // 5. save to vih
    // 6. save to viph
    // 7. save to vihh
    // 8. save to viphh
//    private void saveVideoFollowGameModel(GameModel game, String gameId) {
//        VideoModel videoModel;
//        if (game.getLf() != null && !game.getLf().equals(Constant.NO_IMAGE)) {
//            String videoid = gameId + "f";
//            videoModel = new VideoModel(game.getLf(), time);
//            // save to vif
//            mFirebaseDatabase.getReference("vif").child(videoid).setValue(videoModel);
//            // save to vipf
//            // save to vihf
//            // save to viphf
//            for (String name : game.getTmA().keySet()) {
//                mFirebaseDatabase.getReference("vipf").child(CommonUtils.escapeKey(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("vihf").child(game.getTmA().get(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("viphf").child(CommonUtils.escapeKey(name)).child(game.getTmA().get(name)).child(videoid).setValue(videoModel);
//            }
//            for (String name : game.getTmB().keySet()) {
//                mFirebaseDatabase.getReference("vipf").child(CommonUtils.escapeKey(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("vihf").child(game.getTmB().get(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("viphf").child(CommonUtils.escapeKey(name)).child(game.getTmB().get(name)).child(videoid).setValue(videoModel);
//            }
//
//        }
//        if (game.getLh() != null && !game.getLh().equals(Constant.NO_IMAGE)) {
//            String videoid = gameId + "h";
//            videoModel = new VideoModel(game.getLh(), time);
//            // save to vif
//            mFirebaseDatabase.getReference("vih").child(videoid).setValue(videoModel);
//            // save to vipf
//            // save to vihf
//            // save to viphf
//            for (String name : game.getTmA().keySet()) {
//                mFirebaseDatabase.getReference("viph").child(CommonUtils.escapeKey(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("vihh").child(game.getTmA().get(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("viphh").child(CommonUtils.escapeKey(name)).child(game.getTmA().get(name)).child(videoid).setValue(videoModel);
//            }
//            for (String name : game.getTmB().keySet()) {
//                mFirebaseDatabase.getReference("viph").child(CommonUtils.escapeKey(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("vihh").child(game.getTmB().get(name)).child(videoid).setValue(videoModel);
//                mFirebaseDatabase.getReference("viphh").child(CommonUtils.escapeKey(name)).child(game.getTmB().get(name)).child(videoid).setValue(videoModel);
//            }
//        }
//    }

    // delte all video
//    private void deleteVideoFollowGameModel(GameModel game, String gameId) {
//        String videoid;
//        if (game.getLf() != null && !game.getLf().equals(Constant.NO_IMAGE)) {
//            videoid = gameId + "f";
//            // save to vif
//            mFirebaseDatabase.getReference("vif").child(videoid).removeValue();
//            // save to vipf
//            // save to vihf
//            // save to viphf
//            for (String name : game.getTmA().keySet()) {
//                mFirebaseDatabase.getReference("vipf").child(CommonUtils.escapeKey(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("vihf").child(game.getTmA().get(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("viphf").child(CommonUtils.escapeKey(name)).child(game.getTmA().get(name)).child(videoid).removeValue();
//            }
//            for (String name : game.getTmB().keySet()) {
//                mFirebaseDatabase.getReference("vipf").child(CommonUtils.escapeKey(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("vihf").child(game.getTmB().get(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("viphf").child(CommonUtils.escapeKey(name)).child(game.getTmB().get(name)).child(videoid).removeValue();
//            }
//
//        }
//        if (game.getLh() != null && !game.getLh().equals(Constant.NO_IMAGE)) {
//            videoid = gameId + "h";
//            // save to vif
//            mFirebaseDatabase.getReference("vih").child(videoid).removeValue();
//            // save to vipf
//            // save to vihf
//            // save to viphf
//            for (String name : game.getTmA().keySet()) {
//                mFirebaseDatabase.getReference("viph").child(CommonUtils.escapeKey(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("vihh").child(game.getTmA().get(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("viphh").child(CommonUtils.escapeKey(name)).child(game.getTmA().get(name)).child(videoid).removeValue();
//            }
//            for (String name : game.getTmB().keySet()) {
//                mFirebaseDatabase.getReference("viph").child(CommonUtils.escapeKey(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("vihh").child(game.getTmB().get(name)).child(videoid).removeValue();
//                mFirebaseDatabase.getReference("viphh").child(CommonUtils.escapeKey(name)).child(game.getTmB().get(name)).child(videoid).removeValue();
//            }
//        }
//    }

    private boolean validateInputTeamName() {
        if (actTeamA.getText().toString().trim().equals(Constant.TBD) || actTeamB.getText().toString().trim().equals(Constant.TBD)) {
            return false;
        } else return true;
    }

    private void resetUi() {
        edtMatchId.setText(Constant.NO_IMAGE);
        edtMatchId.setEnabled(true);
        actTeamA.setText(Constant.NO_IMAGE);
        actTeamA.setEnabled(true);
        actTeamB.setText(Constant.NO_IMAGE);
        actTeamB.setEnabled(true);
        edtResultA.setText("0");
        edtResultB.setText("0");
        edtOddsTeamA.setText(Constant.NO_IMAGE);
        edtOddsTeamB.setText(Constant.NO_IMAGE);
        edtPhotoA.setText("Photo Id");
        edtPhotoB.setText("Photo Id");
        actRound.setText(Constant.NO_IMAGE);
        actLocal.setText(Constant.NO_IMAGE);
        actTournament.setText(Constant.NO_IMAGE);
        edtBo.setText(Constant.NO_IMAGE);
        btnAddTime.setText("TIME");
        tvSumGames.setText("0");
        edtGameData.setText(Constant.NO_IMAGE);
        llChanelLive.removeAllViews();
        llListGames.removeAllViews();

        actTeamA.setEnabled(true);
        edtNumberChanel.setText(Constant.NO_IMAGE);
        gameModelMapOld = null;
        gameModelMapNew = null;
        matchId = null;
        TYPE = Constant.CREATE_DATA;
        time = 0l;
        matchModel = null;
    }

//    private class OnCustomCompleteListener implements OnCompleteListener<Void> {
//
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            hideCircleDialogOnly();
//            if (!task.isSuccessful()) {
//                Toast.makeText(getContext(), "Save failed: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private class OnEndCompleteListiner implements OnCompleteListener<Void> {
//
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            hideCircleDialogOnly();
//            if (!task.isSuccessful()) {
//                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getContext(), "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
//                resetUi();
//            }
//        }
//    }

    private void executeSaveNewCode() {
        if (!validateInputData()) {
            return;
        }
        String matchNewId;
        DatabaseReference reference;
        String slash = "/";
        Map<String, Object> mapData = new HashMap<>();
        if (TYPE == Constant.CREATE_DATA) {
            // save for upcoming
            if (rbUpcoming.isChecked()) {
                showCircleDialogOnly();
                // create upcoming model -> save to tour, upcoming
                // match model no have games -> sum = 0
                reference = mFirebaseDatabase.getReference("pro/upcoming");
                matchNewId = reference.push().getKey();
                // create model
                matchModel = createMatchModel(Constant.UPCOMING, matchNewId);
                // save suggest for team, tourn, round
                saveSuggestForMatch(matchModel, mapData);
                // save to "pro/upcoming"
                mapData.put(new StringBuilder("pro/upcoming/").append(matchModel.getId()).toString(), matchModel);
                // save to "pro/tour"
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModel));

            } else if (rbLive.isChecked()) {
                showCircleDialogOnly();
                reference = mFirebaseDatabase.getReference("pro/live");
                matchNewId = reference.push().getKey();
                // create model
                matchModel = createMatchModel(Constant.LIVE, matchNewId);
                // save suggest for team, tourn, round, player
                saveSuggestForMatch(matchModel, mapData);
                // save games
                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
                    GameModel gameModelSave;
                    for (String gameId : gameModelMapSave.keySet()) {
                        gameModelSave = gameModelMapSave.get(gameId);
                        mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), convertNameAndHeroes(gameModelSave));
                        // save video
                        saveVideoForMatch(gameModelSave, gameId, mapData);
                    }
                }
                // save matchModel to live
                mapData.put(new StringBuilder("pro/live/").append(matchModel.getId()).toString(), matchModel);
                // save matchModel to tour
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModel));
            } else if (rbEnd.isChecked()) {
                if (!validateInputTeamName()) {
                    actTeamA.setError("requried");
                    return;
                }
                showCircleDialogOnly();
                reference = mFirebaseDatabase.getReference("pro/match");
                matchNewId = reference.push().getKey();
                matchModel = createMatchModel(Constant.END, matchNewId);
                // save suggest for team, tourn, round, player
                saveSuggestForMatch(matchModel, mapData);
                // save games
                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
                    GameModel gameModelSave;
                    for (String gameId : gameModelMapSave.keySet()) {
                        gameModelSave = gameModelMapSave.get(gameId);
                        mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), convertNameAndHeroes(gameModelSave));
                        // save video
                        saveVideoForMatch(gameModelSave, gameId, mapData);
                    }
                }
                // save to match
                mapData.put(new StringBuilder("pro/match/").append(matchModel.getId()).toString(), matchModel);
                // save to team
                mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTa().getNa())).append(slash).append(matchModel.getId()).toString(), matchModel);
                mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTb().getNa())).append(slash).append(matchModel.getId()).toString(), matchModel);
                // save to tour
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModel));
            }
        } else if (TYPE == Constant.LOAD_DATA) {
            MatchModel matchModelSave;
            if (rbUpcoming.isChecked()) {
                showCircleDialogOnly();
                // create upcoming model -> save to tour, upcoming
                // match model no have games -> sum = 0
                // create model
                matchModelSave = createMatchModel(Constant.UPCOMING, null);
                if (matchModel.getSt() == Constant.LIVE || matchModel.getSt() == Constant.END) {
                    if (matchModel.getSt() == Constant.LIVE) {
                        mapData.put(new StringBuilder("pro/live/").append(matchModel.getId()).toString(), null);
                    } else {
                        mapData.put(new StringBuilder("pro/match/").append(matchModel.getId()).toString(), null);
                        if (!matchModel.getTa().getNa().equals(Constant.TBD)) {
                            mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTa().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                        }
                        if (!matchModel.getTb().getNa().equals(Constant.TBD)) {
                            mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTb().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                        }
                    }
                    if (gameModelMapOld != null && gameModelMapOld.size() > 0) {
                        for (String gameId : gameModelMapOld.keySet()) {
                            mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), null);
                            GameModel game = gameModelMapOld.get(gameId);
                            deleteVideoForMatch(game, gameId, mapData);
                        }
                    }
                }
                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
                    mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), null);
                }
                // save to "pro/upcoming"
                mapData.put(new StringBuilder("pro/upcoming/").append(matchModel.getId()).toString(), matchModelSave);
                // save to "pro/tour"
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModelSave.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModelSave));
            } else if (rbLive.isChecked()) {
                showCircleDialogOnly();
                // create model
                matchModelSave = createMatchModel(Constant.LIVE, null);
                if (matchModel.getSt() == Constant.UPCOMING) {
                    mapData.put(new StringBuilder("pro/upcoming/").append(matchModel.getId()).toString(), null);
                } else if (matchModel.getSt() == Constant.END || matchModel.getSt() == Constant.LIVE) {
                    if (matchModel.getSt() == Constant.END) {
                        mapData.put(new StringBuilder("pro/match/").append(matchModel.getId()).toString(), null);
                        mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTa().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                        mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModel.getTb().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                    }
                    if (matchModel.getSum() > matchModelSave.getSum()) {
                        for (int i = matchModel.getSum(); i > matchModelSave.getSum(); i--) {
                            GameModel game = gameModelMapOld.get(matchModel.getId() + i);
                            if (game != null) {
                                mapData.put(new StringBuilder("pro/games/").append(matchModel.getId()).append(i).toString(), null);
                                deleteVideoForMatch(game, matchModel.getId() + i, mapData);
                            }
                        }
                    }
                }
                // save games
                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
                    GameModel gameModelSave;
                    for (String gameId : gameModelMapSave.keySet()) {
                        gameModelSave = gameModelMapSave.get(gameId);
                        mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), convertNameAndHeroes(gameModelSave));
                        // save video
                        saveVideoForMatch(gameModelSave, gameId, mapData);
                    }
                }
                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
                    mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), null);
                }
                // save matchModel to live
                mapData.put(new StringBuilder("pro/live/").append(matchModel.getId()).toString(), matchModelSave);
                // save matchModel to tour
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModelSave.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModelSave));
            } else if (rbEnd.isChecked()) {
                if (!validateInputTeamName()) {
                    actTeamA.setError("requried");
                    return;
                }
                showCircleDialogOnly();
                matchModelSave = createMatchModel(Constant.END, null);
                if (matchModel.getSt() == Constant.UPCOMING) {
                    mapData.put(new StringBuilder("pro/upcoming/").append(matchModel.getId()).toString(), null);
                } else if (matchModel.getSt() == Constant.LIVE || matchModel.getSt() == Constant.END) {
                    if (matchModel.getSt() == Constant.LIVE) {
                        mapData.put(new StringBuilder("pro/live/").append(matchModel.getId()).toString(), null);
                    } else {
                        if (!matchModel.getTa().getNa().equals(matchModelSave.getTa().getNa())) {
                            mapData.put(new StringBuilder("pro/team").append(CommonUtils.escapeKey(matchModel.getTa().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                        }
                        if (!matchModel.getTb().getNa().equals(matchModelSave.getTb().getNa())) {
                            mapData.put(new StringBuilder("pro/team").append(CommonUtils.escapeKey(matchModel.getTb().getNa())).append(slash).append(matchModel.getId()).toString(), null);
                        }
                    }
                    if (matchModel.getSum() > matchModelSave.getSum()) {
                        for (int i = matchModel.getSum(); i > matchModelSave.getSum(); i--) {
                            GameModel game = gameModelMapOld.get(matchModel.getId() + i);
                            if (game != null) {
                                mapData.put(new StringBuilder("pro/games/").append(matchModel.getId()).append(i).toString(), null);
                                deleteVideoForMatch(game, matchModel.getId() + i, mapData);
                            }
                        }
                    }
                }
                // save games
                if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
                    GameModel gameModelSave;
                    for (String gameId : gameModelMapSave.keySet()) {
                        gameModelSave = gameModelMapSave.get(gameId);
                        mapData.put(new StringBuilder("pro/games/").append(gameId).toString(), convertNameAndHeroes(gameModelSave));
                        // save video
                        deleteVideoForMatch(gameModelSave, gameId, mapData);
                    }
                }
                // save to match
                mapData.put(new StringBuilder("pro/match/").append(matchModelSave.getId()).toString(), matchModelSave);
                // save to team
                mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModelSave.getTa().getNa())).append(slash).append(matchModelSave.getId()).toString(), createMatchTeamModel(matchModelSave));
                mapData.put(new StringBuilder("pro/team/").append(CommonUtils.escapeKey(matchModelSave.getTb().getNa())).append(slash).append(matchModelSave.getId()).toString(), createMatchTeamModel(matchModelSave));
                if (!matchModel.getTo().equals(matchModelSave.getTo())) {
                    mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModel.getTo())).append(slash).append(matchModel.getId()).toString(), null);
                }
                mapData.put(new StringBuilder("pro/tour/").append(CommonUtils.escapeKey(matchModelSave.getTo())).append(slash).append(matchModel.getId()).toString(), createTourModel(matchModelSave));
            }

        }
        hideCircleDialogOnly();
        if (!mapData.isEmpty()) {
            showCircleDialogOnly();
            mFirebaseDatabase.getReference().updateChildren(mapData, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    hideCircleDialogOnly();
                    if (databaseError == null) {
                        Toast.makeText(getContext(), "SAVE OK !!!", Toast.LENGTH_SHORT).show();
                        resetUi();
                    } else {
                        showWarningDialog(databaseError.getMessage());
                    }
                }
            });
        }
    }

    private void saveSuggestForMatch(MatchModel model, Map<String, Object> mapData) {
        // save suggest team, tournament, ro
        if (suggestTeam == null) {
            suggestTeam = new ArrayList<>();
        }
        if (!model.getTa().getNa().equals(Constant.TBD) && !suggestTeam.contains(model.getTa().getNa())) {
            mapData.put(new StringBuilder("pro/suggest/team/").append(suggestTeam.size()).toString(), model.getTa().getNa());
            suggestTeam.add(model.getTa().getNa());
        }
        if (!model.getTb().getNa().equals(Constant.TBD) && !suggestTeam.contains(model.getTb().getNa())) {
            mapData.put(new StringBuilder("pro/suggest/team/").append(suggestTeam.size()).toString(), model.getTb().getNa());
            suggestTeam.add(model.getTb().getNa());
        }

        // save suggest tour
        if (listTour == null) {
            listTour = new ArrayList<>();
        }
        if (!model.getTo().equals(Constant.NO_IMAGE) && !listTour.contains(model.getTo())) {
            mapData.put(new StringBuilder("pro/suggest/tour/").append(listTour.size()).toString(), model.getTo());
            listTour.add(model.getTo());
        }

        // save suggest ro
        if (listRo == null) {
            listRo = new ArrayList<>();
        }
        if (!model.getRo().equals(Constant.NO_IMAGE) && !listRo.contains(model.getRo())) {
            mapData.put(new StringBuilder("pro/suggest/round/").append(listRo.size()).toString(), model.getRo());
            listLo.add(model.getRo());
        }
        // save suggest lo
        if (listLo == null) {
            listLo = new ArrayList<>();
        }
        if (!model.getLo().equals(Constant.NO_IMAGE) && !listLo.contains(model.getLo())) {
            mapData.put(new StringBuilder("pro/suggest/local/").append(listLo.size()).toString(), model.getLo());
            listLo.add(model.getLo());
        }
        // save suggest player
        if (listPl == null) {
            listPl = new ArrayList<>();
        }
        if (gameModelMapNew != null && !gameModelMapSave.isEmpty()) {
            GameModel game = gameModelMapSave.get(matchModel.getId() + 1);
            if (game != null) {
                HashMap<String, String> mapA = game.getTmA();
                HashMap<String, String> mapB = game.getTmB();

                if (mapA != null && mapA.size() > 0 && mapB != null && mapB.size() > 0) {
                    for (String str : mapA.keySet()) {
                        if (!listPl.contains(str)) {
                            mapData.put(new StringBuilder("pro/suggest/pl/").append(listPl.size()).toString(), str);
                            listPl.add(str);
                        }
                    }
                    for (String str : mapB.keySet()) {
                        if (!listPl.contains(str)) {
                            mapData.put(new StringBuilder("pro/suggest/pl/").append(listPl.size()).toString(), str);
                            listPl.add(str);
                        }
                    }
                }

            }
        }
    }

    private void saveVideoForMatch(GameModel game, String gameId, Map<String, Object> map) {
        VideoModel videoModel;
        String slash = "/";

        if (game.getLf() != null && !game.getLf().equals(Constant.NO_IMAGE)) {
            videoModel = new VideoModel(game.getLf(), time);
            // save to vif
            map.put(new StringBuilder("vif/").append(gameId).toString(), videoModel);
            // save to vipf
            // save to vihf
            // save to viphf
            for (String name : game.getTmA().keySet()) {
                map.put(new StringBuilder("vipf/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("vihf/").append(game.getTmA().get(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("viphf/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmA().get(name)).append(slash).append(gameId).toString(), videoModel);
            }
            for (String name : game.getTmB().keySet()) {
                map.put(new StringBuilder("vipf/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("vihf/").append(game.getTmB().get(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("viphf/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmB().get(name)).append(slash).append(gameId).toString(), videoModel);
            }

        }
        if (game.getLh() != null && !game.getLh().equals(Constant.NO_IMAGE)) {
            videoModel = new VideoModel(game.getLh(), time);
            // save to vif
            map.put(new StringBuilder("vih/").append(gameId).toString(), videoModel);
            // save to vipf
            // save to vihf
            // save to viphf
            for (String name : game.getTmA().keySet()) {
                map.put(new StringBuilder("viph/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("vihh/").append(game.getTmA().get(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("viphh/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmA().get(name)).append(slash).append(gameId).toString(), videoModel);
            }
            for (String name : game.getTmB().keySet()) {
                map.put(new StringBuilder("viph/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("vihh/").append(game.getTmB().get(name)).append(slash).append(gameId).toString(), videoModel);
                map.put(new StringBuilder("viphh/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmB().get(name)).append(slash).append(gameId).toString(), videoModel);
            }
        }
    }

    // delte all video
    private void deleteVideoForMatch(GameModel game, String gameId, Map<String, Object> map) {
        String slash = "/";
        if (game.getLf() != null && !game.getLf().equals(Constant.NO_IMAGE)) {
            // save to vif
            map.put(new StringBuilder("vif/").append(gameId).toString(), null);
            // save to vipf
            // save to vihf
            // save to viphf
            for (String name : game.getTmA().keySet()) {
                map.put(new StringBuilder("vipf/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("vihf/").append(game.getTmA().get(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("viphf/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmA().get(name)).append(slash).append(gameId).toString(), null);
            }
            for (String name : game.getTmB().keySet()) {
                map.put(new StringBuilder("vipf/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("vihf/").append(game.getTmB().get(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("viphf/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmB().get(name)).append(slash).append(gameId).toString(), null);
            }

        }
        if (game.getLh() != null && !game.getLh().equals(Constant.NO_IMAGE)) {

            // save to vif
            map.put(new StringBuilder("vih/").append(gameId).toString(), null);
            // save to vipf
            // save to vihf
            // save to viphf
            for (String name : game.getTmA().keySet()) {
                map.put(new StringBuilder("viph/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("vihh/").append(game.getTmA().get(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("viphh/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmA().get(name)).append(slash).append(gameId).toString(), null);
            }
            for (String name : game.getTmB().keySet()) {
                map.put(new StringBuilder("viph/").append(CommonUtils.escapeKey(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("vihh/").append(game.getTmB().get(name)).append(slash).append(gameId).toString(), null);
                map.put(new StringBuilder("viphh/").append(CommonUtils.escapeKey(name)).append(slash).append(game.getTmB().get(name)).append(slash).append(gameId).toString(), null);
            }
        }
    }

    private MatchTeamModel createMatchTeamModel(MatchModel matchModel) {
        if (matchModel == null) {
            return null;
        }
        MatchTeamModel result = new MatchTeamModel();
        result.setRa(matchModel.getRa());
        result.setRb(matchModel.getRb());
        result.setSum(matchModel.getSum());
        result.setTime(matchModel.getTime());
        result.setTa(matchModel.getTa());
        result.setTb(matchModel.getTb());
        return result;
    }

    private TourModel createTourModel(MatchModel matchModel) {
        if (matchModel == null) {
            return null;
        }
        TourModel result = new TourModel();
        result.setTime(matchModel.getTime());
        result.setRa(matchModel.getRa());
        result.setRb(matchModel.getRb());
        result.setLo(matchModel.getLo());
        result.setRo(matchModel.getRo());
        result.setSt(matchModel.getSt());
        result.setSum(matchModel.getSum());
        result.setTeamA(matchModel.getTa());
        result.setTeamB(matchModel.getTb());
        return result;
    }
}
