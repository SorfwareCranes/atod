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
import android.util.Log;
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
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.JsonUtil;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.dialog.CircleDialog;
import software.cranes.com.dota.dialog.DateDialogFragment;
import software.cranes.com.dota.dialog.LoginDialog;
import software.cranes.com.dota.dialog.SuggestDialogFragment;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.DefaultUrl;
import software.cranes.com.dota.model.Items;
import software.cranes.com.dota.model.PubGameModel;
import software.cranes.com.dota.model.Snippet;
import software.cranes.com.dota.model.SnitppetModel;
import software.cranes.com.dota.model.Thumbnails;

import static android.R.attr.type;


/**
 * A simple {@link BaseFragment} subclass.
 */
public class AddPublicMatchFragment extends BaseFragment implements View.OnClickListener {
    private Button btnAddTime;
    private AutoCompleteTextView actPlayer;
    private Button btnChoicePlayer;
    private AutoCompleteTextView actHeroes;
    private EditText edtTitleGame;
    private EditText edtLink;
    private Button btnBack;
    private Button btnDelete;
    private Button btnSave;
    private EditText edtLoadGame;
    private Button btnLoadGame;
    private FirebaseDatabase mFirebaseDatabase;
    private List<String> suggestPlayer;
    private List<String> suggestHeroes;
    private long time;
    private PubGameModel model;
    private int TYPE;
    private String gameId;

    public AddPublicMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        suggestPlayer = new ArrayList<>();
        suggestHeroes = new ArrayList<>();
        if (getArguments() != null && getArguments().get(Constant.DATA) != null) {
            gameId = (String) getArguments().get(Constant.DATA);
            TYPE = Constant.LOAD_DATA;
        }
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
        edtLink = (EditText) view.findViewById(R.id.edtLink);
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
        if (TYPE == Constant.LOAD_DATA) {
            loadGameFollowId(gameId);
        }

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
                setTime();
                break;
            case R.id.btnChoicePlayer:
                setPlayer();
                break;
            case R.id.btnBack:
                getFragmentManager().popBackStack();
                break;
            case R.id.btnDelete:
                handleDeleteGame();
                break;
            case R.id.btnSave:
//                test();
                handleSaveGame();
                break;
            case R.id.btnLoadGame:
                loadGameFollowId(edtLoadGame.getText().toString());
                break;
        }
    }

    private void loadPlayerName() {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/suggest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestPlayer = (ArrayList<String>) dataSnapshot.getValue();
                if (suggestPlayer != null && suggestPlayer.size() > 0) {
                        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestPlayer);
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
                    AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestHeroes);
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



    private void setTime() {
        DateDialogFragment date = new DateDialogFragment(time, new DateDialogFragment.HandleSetTime() {
            @Override
            public void handleTime(long time) {
                AddPublicMatchFragment.this.time = time;
                btnAddTime.setText(CommonUtils.convertIntDateToString(time));
            }
        });
        if (date.getDialog() == null || !date.getDialog().isShowing()) {
            date.show(getFragmentManager(), null);
        }
    }

    private void setPlayer() {
        SuggestDialogFragment suggestDialogFragment = new SuggestDialogFragment(Constant.TYPE_PLAYER, new SuggestDialogFragment.HanldeName() {
            @Override
            public void handleChoice(String str) {
                actPlayer.setText(str);
            }
        });
        if (suggestDialogFragment.getDialog() == null || !suggestDialogFragment.getDialog().isShowing()) {
            suggestDialogFragment.show(getFragmentManager(), null);
        }
    }

    private void handleDeleteGame() {
        if (model == null || model.getId() == null) {
            getFragmentManager().popBackStack();
            return;
        }
        executeDeleteGame(model);
    }

    private void handleSaveGame() {
        showCircleDialogOnly();
        if (!validateInput()) {
            hideCircleDialogOnly();
            return;
        }

        if (type == Constant.CREATE_DATA || (type == Constant.LOAD_DATA && model == null)) {
            // type create new
            model = new PubGameModel(CommonUtils.extractVideoIdFromUrl(edtLink.getText().toString()), actPlayer.getText().toString(), actHeroes.getText().toString(), getTitle(), time);
            // save data
            saveDataToFirebase(model);
        } else {
            // type update
            handleUpdateData();
        }

    }

    /*
     true -> all ok
     false -> have wrong
     */
    private boolean validateInput() {
        if (time == 0) {
            Toast.makeText(getContext(), "Please set Time", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (actPlayer.getText().toString().isEmpty()) {
            actPlayer.setError(Constant.REQUIRED);
            return false;
        }
        if (actHeroes.getText().toString().isEmpty()) {
            actHeroes.setError(Constant.REQUIRED);
            return false;
        }

        if (edtLink.getText().toString().isEmpty()) {
            edtLink.setError(Constant.REQUIRED);
            return false;
        }
        if (!CommonUtils.isYoutube(edtLink.getText().toString())) {
            edtLink.setText(Constant.NO_IMAGE);
            return false;
        }
        return true;
    }

    /*
       load Data follow ID game
     */
    private void loadGameFollowId(String id) {
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/game/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    model = dataSnapshot.getValue(PubGameModel.class);
                    if (model != null) {
                        TYPE = Constant.LOAD_DATA;
                        time = model.getTime();
                        btnAddTime.setText(CommonUtils.convertIntDateToString(time));
                        actPlayer.setText(model.getPlayer());
                        actHeroes.setText(model.getHero());
                        edtTitleGame.setText(model.getTitle());
                        edtLink.setText("https://www.youtube.com/watch?v=" + model.getId());
                    }
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
        1.save model -> "pub/game" id-model
        2.save id to -> "pub/heroes/heroName" : id: model
        3.save id to -> "pub/player/playerName" : id: model
        4.save id to -> "pub/player_heroes/playerName/heroName" : id:model
        5.save playerName -> "pub/suggest"
     */
    private void saveDataToFirebase(PubGameModel model) {
        // 1 save model
        mFirebaseDatabase.getReference("pub/game/" + model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListenerCustom("pub/game/ falied"));
        showCircleDialogOnly();
        // 2.save id to -> "pub/heroes/heroName" : id: model
        PubGameModel model2 = new PubGameModel();
        model2.setTime(model.getTime());
        model2.setTitle(model.getTitle());
        model2.setPlayer(model.getPlayer());
        mFirebaseDatabase.getReference("/pub/heroes/" + model.getHero() + "/" + model.getId()).setValue(model2).addOnCompleteListener(new OnCompleteListenerCustom("/pub/heroes/ falied"));
        showCircleDialogOnly();
        // 3."pub/player/playerName" : id: model
        PubGameModel model3 = new PubGameModel();
        model3.setTime(model.getTime());
        model3.setTitle(model.getTitle());
        model3.setHero(model.getHero());
        mFirebaseDatabase.getReference("/pub/player/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getId()).setValue(model3).addOnCompleteListener(new OnCompleteListenerCustom("/pub/player/ failed"));
        showCircleDialogOnly();
        //.4.save id to -> "pub/player_heroes/playerName/heroName" : id:model
        PubGameModel model4 = new PubGameModel();
        model4.setTime(model.getTime());
        model4.setTitle(model.getTitle());
        mFirebaseDatabase.getReference("pub/player_heroes/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getHero() + "/" + model.getId()).setValue(model4).addOnCompleteListener(new OnCompleteListenerCustom("pub/player_heroes/ failed"));
        showCircleDialogOnly();
        //5.save playerName -> "pub/suggest"
        if (!checkPlayerSuggest(model.getPlayer())) {
            mFirebaseDatabase.getReference("pub/suggest/" + suggestPlayer.size()).setValue(model.getPlayer()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "pub/suggest/ failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Save completed", Toast.LENGTH_SHORT).show();
//                        getFragmentManager().popBackStack();
                    }
                }
            });
        }

    }

    // check name player is haven in suggestPlayer
    private boolean checkPlayerSuggest(String name) {
        boolean result = false;
        if (suggestPlayer != null) {
            for (String str : suggestPlayer) {
                if (name.equals(str)) {
                    result = true;
                }
            }
        } else {
            suggestPlayer = new ArrayList<>();
        }
        return result;
    }

    /*
        1. delete follow id : "pub/game"
        2. delete follow id : "pub/heroes/heroName"
        3. delete follow id : "pub/player/playerName"
        4. delete follow id : "pub/player_hero/playerName/heroName"
     */
    private void executeDeleteGame(PubGameModel model) {
        // 1. delete follow id : "pub/game"
        mFirebaseDatabase.getReference("pub/game/" + model.getId()).removeValue().addOnCompleteListener(new OnCompleteListenerCustom("pub/game/ failed"));
        // 2. delete follow id : "pub/heroes/heroName"
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/heroes/" + model.getHero() + "/" + model.getId()).removeValue().addOnCompleteListener(new OnCompleteListenerCustom("pub/heroes/ failed"));
        // 3. delete follow id : "pub/player/playerName"
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/player/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getId()).removeValue().addOnCompleteListener(new OnCompleteListenerCustom("pub/player/ failed"));
        //4. delete follow id : "pub/player_hero/playerName/heroName"
        showCircleDialogOnly();
        mFirebaseDatabase.getReference("pub/player_heroes/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getHero() + "/" + model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialogOnly();
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), "pub/player_heroes/ failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Delete Ok", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }
        });
    }


    private class OnCompleteListenerCustom implements OnCompleteListener {
        String status;

        public OnCompleteListenerCustom(String status) {
            this.status = status;
        }

        @Override
        public void onComplete(@NonNull Task task) {
            hideCircleDialogOnly();
            if (!task.isSuccessful()) {
                Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
        1.change data follow id: "pub/game"
        delete old data

        2. delete follow id : "pub/heroes/heroName"
        3. delete follow id : "pub/player/playerName"
        4. delete follow id : "pub/player_hero/playerName/heroName"
        write new data
        5.save id to -> "pub/heroes/heroName" : id: model
        6.save id to -> "pub/player/playerName" : id: model
        7.save id to -> "pub/player_heroes/playerName/heroName" : id:model
     */
    private void handleUpdateData() {
        // model -> old data

        // newGame - > new data
        PubGameModel newGame = new PubGameModel(CommonUtils.extractVideoIdFromUrl(edtLink.getText().toString()), actPlayer.getText().toString(), actHeroes.getText().toString(), getTitle(), time);

        // 1. save again "pub/game/"
        mFirebaseDatabase.getReference("pub/game/" + model.getId()).setValue(newGame).addOnCompleteListener(new OnCompleteListenerCustom("pub/game/ failed"));
        showCircleDialogOnly();
        // 2.delete follow id : "pub/heroes/heroName"
        mFirebaseDatabase.getReference("pub/palyer/" + model.getPlayer() + "/" + model.getId()).removeValue();
        // 3. delete follow id : "pub/player/playerName"
        mFirebaseDatabase.getReference("pub/player/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getId()).removeValue();
        // 4. delete follow id : "pub/player_hero/playerName/heroName"
        mFirebaseDatabase.getReference("pub/player_hero/" + CommonUtils.escapeKey(model.getPlayer()) + "/" + model.getHero() + "/" + model.getId()).removeValue();

        // 5.save id to -> "pub/heroes/heroName" : id: model
        PubGameModel newGame5 = new PubGameModel(null, newGame.getPlayer(), null, newGame.getTitle(), newGame.getTime());
        mFirebaseDatabase.getReference("pub/heroes/" + newGame.getHero() + "/" + newGame.getId()).setValue(newGame5);
        // 6.save id to -> "pub/player/playerName" : id: model
        PubGameModel newGame6 = new PubGameModel(null, null, newGame.getHero(), newGame.getTitle(), newGame.getTime());
        mFirebaseDatabase.getReference("pub/player/" + CommonUtils.escapeKey(newGame.getPlayer()) + "/" + newGame.getId()).setValue(newGame6);
        // 7.save id to -> "pub/player_heroes/playerName/heroName" : id:model
        PubGameModel newGame7 = new PubGameModel(null, null, null, newGame.getTitle(), newGame.getTime());
        mFirebaseDatabase.getReference("pub/player_heroes/" + CommonUtils.escapeKey(newGame.getPlayer()) + "/" + newGame.getHero() + "/" + newGame.getId()).setValue(newGame7).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideCircleDialogOnly();
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    private String getTitle() {
        return edtTitleGame.getText().toString().trim().equals(Constant.NO_IMAGE) ? null : edtTitleGame.getText().toString().trim();
    }

    /*
    {
         "items": [
          {
           "snippet": {
            "title": "SingSing Dota 2 ► 75% Buthole",
            "thumbnails": {
             "medium": {
              "url": "https://i.ytimg.com/vi/pFStR6L-U3c/mqdefault.jpg"
             }
            }
           }
          }
         ]
        }
     */
//    private void test() {
//        String videoId = "pNS3ykTurVg";
//        String url = "https://www.googleapis.com/youtube/v3/videos?id=";
//        String key = "&key=AIzaSyC-2J8Rwoe5ppVp6FemxwwqSuEn3ZxDofE&part=snippet&fields=items/snippet(title,thumbnails/default/url)";
//        SendRequest.requestGet(getContext(), url + videoId + key, Constant.TYPE_RESPONSE_OBJECT, Items.class, new SendRequest.HandleResponse() {
//            @Override
//            public void onSuccess(Object data) {
//                if (data instanceof Items) {
//                    Items model = (Items) data;
//                }
//            }
//
//            @Override
//            public void onFail(String err) {
//
//            }
//        });
//
//    }
}