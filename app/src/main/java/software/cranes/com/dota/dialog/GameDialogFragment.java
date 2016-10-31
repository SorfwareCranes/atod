package software.cranes.com.dota.dialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GameModel;


/**
 * Created by GiangNT - PC on 25/10/2016.
 */

public class GameDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private String teamAName, teamBName;
    private String gameId;
    private GameModel gameModel;
    private HandleCreateGame mHandleGame;
    private HashMap<String, GameModel> modelHashMap;
    private TextView tvNameTeamA;
    private TextView tvNameTeamB;
    private Button btnLoadSuggestFromTeam, btnLoadSuggestFromPlayer;
    private AutoCompleteTextView edtNamePlayerA1;
    private AutoCompleteTextView edtNamePlayerA2;
    private AutoCompleteTextView edtNamePlayerA3;
    private AutoCompleteTextView edtNamePlayerA4;
    private AutoCompleteTextView edtNamePlayerA5;
    private AutoCompleteTextView edtNameHeroA1;
    private AutoCompleteTextView edtNameHeroA2;
    private AutoCompleteTextView edtNameHeroA3;
    private AutoCompleteTextView edtNameHeroA4;
    private AutoCompleteTextView edtNameHeroA5;
    private AutoCompleteTextView edtNamePlayerB1;
    private AutoCompleteTextView edtNamePlayerB2;
    private AutoCompleteTextView edtNamePlayerB3;
    private AutoCompleteTextView edtNamePlayerB4;
    private AutoCompleteTextView edtNamePlayerB5;
    private AutoCompleteTextView edtNameHeroB1;
    private AutoCompleteTextView edtNameHeroB2;
    private AutoCompleteTextView edtNameHeroB3;
    private AutoCompleteTextView edtNameHeroB4;
    private AutoCompleteTextView edtNameHeroB5;
    private RadioButton rbAWin;
    private RadioButton rbDraw;
    private RadioButton rbBWin;
    private EditText edtHighlightLink;
    private EditText edtFulltLink;
    private Button btnCancel;
    private Button btnOK;
    private List<String> listHeroes;
    private int TYPE;
    private HashMap<String, String> nameImageHeroesMap;

    public interface HandleCreateGame {
        void executeAddGame(int TYPE, String number, GameModel gameModel);
    }

    public GameDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public GameDialogFragment(int TYPE, String gameId, HashMap<String, GameModel> modelHashMap, String teamAName, String teamBName, HashMap<String, String> nameImageHeroesMap, HandleCreateGame mHandleGame) {
        this.gameId = gameId;
        this.modelHashMap = modelHashMap;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.mHandleGame = mHandleGame;
        this.nameImageHeroesMap = nameImageHeroesMap;
        this.TYPE = TYPE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHeroes = new ArrayList<>();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.game_dialog_layout, null, false);
        setupView(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void setupView(View view) {
        tvNameTeamA = (TextView) view.findViewById(R.id.tvNameTeamA);
        tvNameTeamB = (TextView) view.findViewById(R.id.tvNameTeamB);
        btnLoadSuggestFromTeam = (Button) view.findViewById(R.id.btnLoadSuggestFromTeam);
        btnLoadSuggestFromPlayer = (Button) view.findViewById(R.id.btnLoadSuggestFromPlayer);
        edtNamePlayerA1 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerA1);
        edtNamePlayerA2 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerA2);
        edtNamePlayerA3 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerA3);
        edtNamePlayerA4 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerA4);
        edtNamePlayerA5 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerA5);
        edtNameHeroA1 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroA1);
        edtNameHeroA2 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroA2);
        edtNameHeroA3 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroA3);
        edtNameHeroA4 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroA4);
        edtNameHeroA5 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroA5);
        edtNamePlayerB1 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerB1);
        edtNamePlayerB2 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerB2);
        edtNamePlayerB3 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerB3);
        edtNamePlayerB4 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerB4);
        edtNamePlayerB5 = (AutoCompleteTextView) view.findViewById(R.id.edtNamePlayerB5);
        edtNameHeroB1 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroB1);
        edtNameHeroB2 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroB2);
        edtNameHeroB3 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroB3);
        edtNameHeroB4 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroB4);
        edtNameHeroB5 = (AutoCompleteTextView) view.findViewById(R.id.edtNameHeroB5);
        rbAWin = (RadioButton) view.findViewById(R.id.rbAWin);
        rbDraw = (RadioButton) view.findViewById(R.id.rbDraw);
        rbBWin = (RadioButton) view.findViewById(R.id.rbBWin);
        edtHighlightLink = (EditText) view.findViewById(R.id.edtHighlightLink);
        edtFulltLink = (EditText) view.findViewById(R.id.edtFulltLink);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOK = (Button) view.findViewById(R.id.btnOK);

        btnLoadSuggestFromPlayer.setOnClickListener(this);
        btnLoadSuggestFromTeam.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        setupUi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadSuggestFromTeam:
                loadSuggestPlayerFromTeam(teamAName, Constant.A_WIN);
                loadSuggestPlayerFromTeam(teamBName, Constant.B_WIN);
                break;
            case R.id.btnLoadSuggestFromPlayer:
                loadSuggestAllPlayerName();
                break;
            case R.id.btnCancel:
                this.dismiss();
                break;
            case R.id.btnOK:
                handleClickOk();
                break;
        }
    }

    /*
        set up ui to add data to ui
        type create new
        type load data game exits
      */
    private void setupUi() {
        tvNameTeamA.setText(teamAName);
        tvNameTeamB.setText(teamBName);
        // check game want create have data or load new game
        if (modelHashMap != null && modelHashMap.containsKey(gameId)) {
            gameModel = modelHashMap.get(gameId);
            setData(gameModel, 2);
            // cannot change heroe and player -> you can change result or link youtube
            if (!edtNamePlayerA1.getText().toString().trim().isEmpty()) {
                setNonEditPlayer();
            }
            if (!edtNameHeroA1.getText().toString().trim().isEmpty()) {
                setNonEditHeroes();
            }
        } else {
            gameModel = new GameModel();
            // exit game before
            if (getGameModelFromMap(modelHashMap) != null) {
                setData(getGameModelFromMap(modelHashMap), 1);
                // cannot change player -> you can change result or link youtube annd player
//                setNonEditPlayer();
            }
            // load suggest for
            loadSuggestHeroes();

        }
    }

    /*
        type == 2 : the game have data
        type == 1 : create new game but get data from game exits of match for player
     */
    private void setData(GameModel gameModel, int type) {
        if (type == 2) {
            if (gameModel.getRs() == Constant.DRAW) {
                rbDraw.setChecked(true);
            } else if (gameModel.getRs() == Constant.A_WIN) {
                rbAWin.setChecked(true);
            } else if (gameModel.getRs() == Constant.B_WIN) {
                rbBWin.setChecked(true);
            }
            if (gameModel.getLh() != null && !gameModel.getLh().isEmpty()) {
                edtHighlightLink.setText("https://www.youtube.com/watch?v=" + gameModel.getLh());
            }
            if (gameModel.getLf() != null && !gameModel.getLf().isEmpty()) {
                edtFulltLink.setText("https://www.youtube.com/watch?v=" + gameModel.getLf());
            }
        }
        if (gameModel.getTmA() != null && !gameModel.getTmA().isEmpty()) {
            List<String> listPlayer = getListKeyFromMap(gameModel.getTmA());
            int i = 1;
            for (String str : listPlayer) {
                switch (i) {
                    case 1:
                        edtNamePlayerA1.setText(CommonUtils.unescapeKey(str));
                        if (type == 2) {
                            edtNameHeroA1.setText(gameModel.getTmA().get(str));
                        }
                        break;
                    case 2:
                        edtNamePlayerA2.setText(CommonUtils.unescapeKey(str));
                        if (type == 2) {
                            edtNameHeroA2.setText(gameModel.getTmA().get(str));
                        }
                        break;
                    case 3:
                        edtNamePlayerA3.setText(CommonUtils.unescapeKey(str));
                        if (type == 2) {
                            edtNameHeroA3.setText(gameModel.getTmA().get(str));
                        }
                        break;
                    case 4:
                        edtNamePlayerA4.setText(CommonUtils.unescapeKey(str));
                        if (type == 2) {
                            edtNameHeroA4.setText(gameModel.getTmA().get(str));
                        }
                        break;
                    case 5:
                        edtNamePlayerA5.setText(CommonUtils.unescapeKey(str));
                        if (type == 2) {
                            edtNameHeroA5.setText(gameModel.getTmA().get(str));
                        }
                        break;
                }
                i++;
            }
        }
        if (gameModel.getTmB() != null && !gameModel.getTmB().isEmpty()) {
            List<String> listPlayer = getListKeyFromMap(gameModel.getTmB());
            int i = 1;
            for (String str : listPlayer) {
                switch (i) {
                    case 1:
                        edtNamePlayerB1.setText(str);
                        if (type == 2) {
                            edtNameHeroB1.setText(gameModel.getTmB().get(str));
                        }
                        break;
                    case 2:
                        edtNamePlayerB2.setText(str);
                        if (type == 2) {
                            edtNameHeroB2.setText(gameModel.getTmB().get(str));
                        }
                        break;
                    case 3:
                        edtNamePlayerB3.setText(str);
                        if (type == 2) {
                            edtNameHeroB3.setText(gameModel.getTmB().get(str));
                        }
                        break;
                    case 4:
                        edtNamePlayerB4.setText(str);
                        if (type == 2) {
                            edtNameHeroB4.setText(gameModel.getTmB().get(str));
                        }
                        break;
                    case 5:
                        edtNamePlayerB5.setText(str);
                        if (type == 2) {
                            edtNameHeroB5.setText(gameModel.getTmB().get(str));
                        }
                        break;
                }
                i++;
            }
        }
    }

    /*
    1. validate data input like :
     */
    private void handleClickOk() {
        if (!validateInput()) {
            return;
        }
        showCircleDialogOnly();
        GameModel gameModel = new GameModel();
        if (rbAWin.isChecked()) {
            gameModel.setRs(Constant.A_WIN);
        } else if (rbBWin.isChecked()) {
            gameModel.setRs(Constant.B_WIN);
        } else if (rbDraw.isChecked()) {
            gameModel.setRs(Constant.DRAW);
        }
        gameModel.setTmA(getPlayerHeroesMap(Constant.A_WIN));
        gameModel.setTmB(getPlayerHeroesMap(Constant.B_WIN));
        if (!edtFulltLink.getText().toString().trim().isEmpty()) {
            gameModel.setLf(CommonUtils.extractVideoIdFromUrl(edtFulltLink.getText().toString().trim()));
        }
        if (!edtHighlightLink.getText().toString().trim().isEmpty()) {
            gameModel.setLh(CommonUtils.extractVideoIdFromUrl(edtHighlightLink.getText().toString().trim()));
        }
        hideCircleDialogOnly();
        this.dismiss();
        if (mHandleGame != null) {
            mHandleGame.executeAddGame(TYPE, gameId, gameModel);
        }


    }


    private GameModel getGameModelFromMap(HashMap<String, GameModel> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                return map.get(key);
            }
        }
        return null;
    }

    private void setNonEditPlayer() {
        edtNamePlayerA1.setEnabled(false);
        edtNamePlayerA2.setEnabled(false);
        edtNamePlayerA3.setEnabled(false);
        edtNamePlayerA4.setEnabled(false);
        edtNamePlayerA5.setEnabled(false);
        edtNamePlayerB1.setEnabled(false);
        edtNamePlayerB2.setEnabled(false);
        edtNamePlayerB3.setEnabled(false);
        edtNamePlayerB4.setEnabled(false);
        edtNamePlayerB5.setEnabled(false);
    }

    private void setNonEditHeroes() {
        edtNameHeroA1.setEnabled(false);
        edtNameHeroA2.setEnabled(false);
        edtNameHeroA3.setEnabled(false);
        edtNameHeroA4.setEnabled(false);
        edtNameHeroA5.setEnabled(false);
        edtNameHeroB1.setEnabled(false);
        edtNameHeroB2.setEnabled(false);
        edtNameHeroB3.setEnabled(false);
        edtNameHeroB4.setEnabled(false);
        edtNameHeroB5.setEnabled(false);

    }

    // list heroes name is suggest for choice name
    private void loadSuggestHeroes() {
        showCircleDialogOnly();
        if (nameImageHeroesMap != null && !nameImageHeroesMap.isEmpty()) {
            listHeroes = getListKeyFromMap(nameImageHeroesMap);
            if (listHeroes.size() > 0 && getActivity() != null) {
                AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listHeroes);
                edtNameHeroA1.setAdapter(adapter);
                edtNameHeroA2.setAdapter(adapter);
                edtNameHeroA3.setAdapter(adapter);
                edtNameHeroA4.setAdapter(adapter);
                edtNameHeroA5.setAdapter(adapter);
                edtNameHeroB1.setAdapter(adapter);
                edtNameHeroB2.setAdapter(adapter);
                edtNameHeroB3.setAdapter(adapter);
                edtNameHeroB4.setAdapter(adapter);
                edtNameHeroB5.setAdapter(adapter);
            }
            hideCircleDialogOnly();
        } else {
            FirebaseDatabase.getInstance().getReference("heroes_suggest").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listHeroes = (ArrayList<String>) dataSnapshot.getValue();
                    if (listHeroes != null && getActivity() != null) {
                        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listHeroes);
                        edtNameHeroA1.setAdapter(adapter);
                        edtNameHeroA2.setAdapter(adapter);
                        edtNameHeroA3.setAdapter(adapter);
                        edtNameHeroA4.setAdapter(adapter);
                        edtNameHeroA5.setAdapter(adapter);
                        edtNameHeroB1.setAdapter(adapter);
                        edtNameHeroB2.setAdapter(adapter);
                        edtNameHeroB3.setAdapter(adapter);
                        edtNameHeroB4.setAdapter(adapter);
                        edtNameHeroB5.setAdapter(adapter);
                    }
                    hideCircleDialogOnly();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideCircleDialogOnly();
                    Toast.makeText(getContext(), "No heroes load", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /*
        get data from joindota/suggest_team_player
        type == 1 this name for team A
        type == 2 this name for team B
     */
    private void loadSuggestPlayerFromTeam(String teamName, final int type) {
        showCircleDialogOnly();
        FirebaseDatabase.getInstance().getReference("joindota/suggest_team_player/" + CommonUtils.escapeKey(teamName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                if (map != null && map.size() > 0) {
                    if (map.containsKey("id_photo")) {
                        map.remove("id_photo");
                    }
                    List<String> result = getListKeyFromMap(map);
                    int i = 1;
                    for (String str : result) {
                        switch (i) {
                            case 1:
                                if (type == Constant.A_WIN) {
                                    edtNamePlayerA1.setText(CommonUtils.unescapeKey(str));
                                } else {
                                    edtNamePlayerB1.setText(CommonUtils.unescapeKey(str));
                                }
                                break;
                            case 2:
                                if (type == Constant.A_WIN) {
                                    edtNamePlayerA2.setText(CommonUtils.unescapeKey(str));
                                } else {
                                    edtNamePlayerB2.setText(CommonUtils.unescapeKey(str));
                                }
                                break;
                            case 3:
                                if (type == Constant.A_WIN) {
                                    edtNamePlayerA3.setText(CommonUtils.unescapeKey(str));
                                } else {
                                    edtNamePlayerB3.setText(CommonUtils.unescapeKey(str));
                                }
                                break;
                            case 4:
                                if (type == Constant.A_WIN) {
                                    edtNamePlayerA4.setText(CommonUtils.unescapeKey(str));
                                } else {
                                    edtNamePlayerB4.setText(CommonUtils.unescapeKey(str));
                                }
                                break;
                            case 5:
                                if (type == Constant.A_WIN) {
                                    edtNamePlayerA5.setText(CommonUtils.unescapeKey(str));
                                } else {
                                    edtNamePlayerB5.setText(CommonUtils.unescapeKey(str));
                                }
                                break;
                        }
                        i++;
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

    /*
        load data from joindota/suggest_player
     */
    private void loadSuggestAllPlayerName() {
        showCircleDialogOnly();
        FirebaseDatabase.getInstance().getReference("joindota/suggest_player").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    List<String> listName = (List<String>) dataSnapshot.getValue();
                    if (listName != null && getActivity() != null) {
                        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listName);
                        edtNamePlayerA1.setAdapter(adapter);
                        edtNamePlayerA2.setAdapter(adapter);
                        edtNamePlayerA3.setAdapter(adapter);
                        edtNamePlayerA4.setAdapter(adapter);
                        edtNamePlayerA5.setAdapter(adapter);
                        edtNamePlayerB1.setAdapter(adapter);
                        edtNamePlayerB2.setAdapter(adapter);
                        edtNamePlayerB3.setAdapter(adapter);
                        edtNamePlayerB4.setAdapter(adapter);
                        edtNamePlayerB5.setAdapter(adapter);
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

    /*
        get list key from map<String, String>
     */
    private List<String> getListKeyFromMap(HashMap<String, String> map) {
        List<String> result = new ArrayList<>();
        for (String str : map.keySet()) {
            result.add(str);
        }
        Collections.sort(result);
        return result;
    }

    private boolean validateInput() {
        if (edtNamePlayerA1.getText().toString().trim().isEmpty() || edtNamePlayerA2.getText().toString().trim().isEmpty()
                || edtNamePlayerA3.getText().toString().trim().isEmpty() || edtNamePlayerA4.getText().toString().trim().isEmpty()
                || edtNamePlayerA5.getText().toString().trim().isEmpty() || edtNamePlayerB1.getText().toString().trim().isEmpty()
                || edtNamePlayerB2.getText().toString().trim().isEmpty() || edtNamePlayerB3.getText().toString().trim().isEmpty()
                || edtNamePlayerB4.getText().toString().trim().isEmpty() || edtNamePlayerB5.getText().toString().trim().isEmpty()) {
            edtNamePlayerA1.setError("required");
            return false;
        }
        if (edtNameHeroA1.getText().toString().trim().isEmpty() || edtNameHeroA2.getText().toString().trim().isEmpty()
                || edtNameHeroA3.getText().toString().trim().isEmpty() || edtNameHeroA4.getText().toString().trim().isEmpty()
                || edtNameHeroA5.getText().toString().trim().isEmpty() || edtNameHeroB1.getText().toString().trim().isEmpty()
                || edtNameHeroB1.getText().toString().trim().isEmpty() || edtNameHeroB1.getText().toString().trim().isEmpty()
                || edtNameHeroB1.getText().toString().trim().isEmpty() || edtNameHeroB1.getText().toString().trim().isEmpty()) {
            edtNameHeroA1.setError("required");
            return false;
        }
        if (!edtFulltLink.getText().toString().trim().isEmpty()) {
            if (!CommonUtils.isYoutube(edtFulltLink.getText().toString().trim())) {
                edtFulltLink.setError("required");
                edtFulltLink.setText(Constant.NO_IMAGE);
                return false;
            }
        }
        if (!edtHighlightLink.getText().toString().trim().isEmpty()) {
            if (!CommonUtils.isYoutube(edtHighlightLink.getText().toString().trim())) {
                edtHighlightLink.setError("required");
                edtHighlightLink.setText(Constant.NO_IMAGE);
                return false;
            }
        }
        return true;
    }

    private HashMap<String, String> getPlayerHeroesMap(int type) {
        HashMap<String, String> result = new HashMap<>();
        if (type == Constant.A_WIN) {
            result.put(edtNamePlayerA1.getText().toString().trim(), edtNameHeroA1.getText().toString().trim());
            result.put(edtNamePlayerA2.getText().toString().trim(), edtNameHeroA2.getText().toString().trim());
            result.put(edtNamePlayerA3.getText().toString().trim(), edtNameHeroA3.getText().toString().trim());
            result.put(edtNamePlayerA4.getText().toString().trim(), edtNameHeroA4.getText().toString().trim());
            result.put(edtNamePlayerA5.getText().toString().trim(), edtNameHeroA5.getText().toString().trim());
        } else {
            result.put(edtNamePlayerB1.getText().toString().trim(), edtNameHeroB1.getText().toString().trim());
            result.put(edtNamePlayerB2.getText().toString().trim(), edtNameHeroB2.getText().toString().trim());
            result.put(edtNamePlayerB3.getText().toString().trim(), edtNameHeroB3.getText().toString().trim());
            result.put(edtNamePlayerB4.getText().toString().trim(), edtNameHeroB4.getText().toString().trim());
            result.put(edtNamePlayerB5.getText().toString().trim(), edtNameHeroB5.getText().toString().trim());
        }
        return result;
    }

}