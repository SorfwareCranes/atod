package software.cranes.com.dota.fragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.JoindotaTeamRankModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinDotaFragment extends BaseFragment implements View.OnClickListener {
    private Button btnGetData, btnGetPlayer, btnSaveTeam, btnSavePlayer;
    private String urlTeam = "http://www.joindota.com/en/edb/teams&page=";
    private String urlPlayer = "http://www.joindota.com/en/edb/team/";
    // urlPhoto size 100px X 100px
    private String urlPhotoTeam = "https://cdn0.gamesports.net/edb_team_logos/";
    // urlPhoto size 100x125
    private String urlPhotoPlayer = "https://cdn0.gamesports.net/edb_player_images/";
    private Map<String, JoindotaTeamRankModel> mapTeamRankWord;
    // name-id_photo
    private Map<String, String> mapPlayer;
    public JoinDotaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_dota, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGetData = (Button) view.findViewById(R.id.btnGetData);
        btnGetPlayer = (Button) view.findViewById(R.id.btnGetPlayer);
        btnSaveTeam = (Button) view.findViewById(R.id.btnSaveTeam);
        btnSavePlayer = (Button) view.findViewById(R.id.btnSavePlayer);
        btnGetPlayer.setOnClickListener(this);
        btnGetData.setOnClickListener(this);
        btnSaveTeam.setOnClickListener(this);
        btnSavePlayer.setOnClickListener(this);
        mapTeamRankWord = new HashMap<>();
        mapPlayer = new HashMap<>();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetData:
                // get map team follow ranker
                getMapTeamRank();
                btnGetPlayer.setVisibility(View.VISIBLE);
                break;
            case R.id.btnGetPlayer:
                // get data for team : ex player, local
                getDataForTeam();
                btnSaveTeam.setVisibility(View.VISIBLE);
                btnSavePlayer.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSaveTeam:
                // save to gosu/team
                saveTeam();
                break;
            case R.id.btnSavePlayer:
                // save to gosu/player
                savePlayer();
                break;
        }
    }

    private void getMapTeamRank() {
        int defaultLoadTeamPage = 6;
        String urlData;
        for (int page = 1; page <= defaultLoadTeamPage; page++) {
            urlData = urlTeam + String.valueOf(page);
            showCircleDialogOnly();
            SendRequest.requestGetJsoup(getContext(), urlData, new SendRequest.StringResponse() {
                @Override
                public void onSuccess(String data) {
                    JoindotaTeamRankModel model;
                    String[] arrLink;
                    Elements elementSpans, elementImgs;
                    String rank, src;
                    Document document = Jsoup.parse(data);
                    Elements elementAs = document.select("a.item_small");
                    if (elementAs != null && elementAs.size() > 0) {
                        for (Element elementa : elementAs) {
                            model = new JoindotaTeamRankModel();
                            arrLink = elementa.attr("href").split("/");
                            model.setData_id(arrLink[arrLink.length - 1]);
                            elementSpans = elementa.getElementsByTag("span");
                            if (elementSpans != null && elementSpans.size() > 1) {
                                rank = elementSpans.get(0).text();
                                if (rank != null) {
//                                    model.setRank(Integer.valueOf(rank.substring(0, rank.length() - 1)));
                                }
                                model.setName(elementSpans.get(1).text());
                                elementImgs = elementSpans.get(1).getElementsByTag("img");
                                if (elementImgs != null && elementImgs.size() > 0) {
                                    src = elementImgs.get(0).attr("src").split("\\?")[0].substring(43);
                                    if (!src.equals("dota/edb_team_default.jpg")) {
                                        model.setId_photo(src);
                                    }
                                }
                            }
//                            mapTeamRankWord.put(String.valueOf(model.getRank()), model);
                        }
                    }
                    hideCircleDialogOnly();
                }

                @Override
                public void onFail(String err) {
                    hideCircleDialogOnly();
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getDataForTeam() {
        if (mapTeamRankWord == null || mapTeamRankWord.size() == 0) {
            return;
        }
        showCircleDialogOnly();
        Iterator<String> iterator = mapTeamRankWord.keySet().iterator();
        String url;
        while (iterator.hasNext()) {
            final String key = iterator.next();
            url = urlPlayer + mapTeamRankWord.get(key).getData_id();
            SendRequest.requestGetJsoup(getContext(), url, new SendRequest.StringResponse() {
                @Override
                public void onSuccess(String data) {
                    if (data != null) {
                        Document document = Jsoup.parse(data);
                        Elements elementEdbs = document.select("div.edb_enemies:has(div.edb_player_small)");
                        if (elementEdbs != null && elementEdbs.size() > 0) {
                            int i = 1;
                            Map<String, String> map = new HashMap<String, String>();
                            Elements elementTexts, elementImgs;
                            Element elementText, elementImg;
                            String[] text2;
                            String name;
                            String id_photo = Constant.NO_IMAGE;
                            for (Element elementEdb : elementEdbs) {
                                elementTexts = elementEdb.select("div.text");
                                if (elementTexts != null && elementTexts.size() > 0) {
                                    elementText = elementTexts.first();
                                    text2 = elementText.text().split(" ");
                                    if (text2[text2.length - 1] != null && (text2[text2.length - 1].equalsIgnoreCase("Stand-In") || text2[text2.length - 1].equalsIgnoreCase("Manager"))) {
                                        continue;
                                    } else {
//                                        model = new PlayerModel();
                                        name = elementText.getElementsByTag("a").first().text();
                                        elementImgs = elementEdb.getElementsByTag("img");
                                        if (elementImgs != null && elementImgs.size() > 0) {
                                            elementImg = elementImgs.get(0);
                                            id_photo = elementImg.attr("src").split("\\?")[0].substring(46);
                                            if (id_photo.startsWith("a")) {
                                                id_photo = Constant.NO_IMAGE;
                                            }
                                        }
                                        map.put(String.valueOf(i), name);
                                        mapPlayer.put(CommonUtils.escapeKey(name), id_photo);
                                        i++;
                                    }

                                }
                            }
                            mapTeamRankWord.get(key).setTeamPlayer(map);
                        }
                    }
                    hideCircleDialogOnly();
                }

                @Override
                public void onFail(String err) {
                    hideCircleDialogOnly();
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveTeam() {
        if (mapTeamRankWord == null ||  mapTeamRankWord.size() == 0) {
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("joindota/team");
        reference.setValue(mapTeamRankWord).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "save ok", Toast.LENGTH_SHORT).show();
                } else {
                    showWarningDialog(task.getException().getMessage());
                }
            }
        });
    }

    private void savePlayer() {
        if (mapPlayer == null || mapPlayer.size() == 0) {
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("joindota/player");
        reference.setValue(mapPlayer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "save ok", Toast.LENGTH_SHORT).show();
                } else {
                    showWarningDialog(task.getException().getMessage());
                }
            }
        });
    }
}
