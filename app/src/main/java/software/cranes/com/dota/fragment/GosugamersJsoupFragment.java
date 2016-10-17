package software.cranes.com.dota.fragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import software.cranes.com.dota.model.GosuGamerTeamRankModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class GosugamersJsoupFragment extends BaseFragment implements View.OnClickListener {

    private Button btnGetData, btnGetPlayer, btnSaveTeam, btnSavePlayer, btnSaveTeamSuggest;
    private String url;
    private Map<String, GosuGamerTeamRankModel> teamRankWorldMap;
    //    private List<PlayerModel> listPlayer;
    private Map<String, String> playerMap;
    private Map<String, Map<String, String>> teamPlayerMap;
    private int defaultNumberPageWord = 5;
    private String urlWord = "http://www.gosugamers.net/dota2/rankings?type=team";
    private String urlPlayer = "http://www.gosugamers.net/rankings/show/team/";
    //size 80x80 px
    private String urlPhotoTeam = "http://www.gosugamers.net/uploads/images/teams/";
    // size 180x133 px
    private String urlPhotoPlayer = "http://www.gosugamers.net/uploads/images/players/";
    private String page = "&page=";
    private String sea_local = "S";
    private String euro_local = "E";
    private String china_local = "C";
    private String americas_local = "A";


    public GosugamersJsoupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNumberPage(urlWord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jsoup, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGetData = (Button) view.findViewById(R.id.btnGetData);
        btnGetPlayer = (Button) view.findViewById(R.id.btnGetPlayer);
        btnSaveTeam = (Button) view.findViewById(R.id.btnSaveTeam);
        btnSavePlayer = (Button) view.findViewById(R.id.btnSavePlayer);
        btnSaveTeamSuggest = (Button) view.findViewById(R.id.btnSaveTeamSuggest);
        btnSaveTeamSuggest.setOnClickListener(this);
        btnGetPlayer.setOnClickListener(this);
        btnGetData.setOnClickListener(this);
        btnSaveTeam.setOnClickListener(this);
        btnSavePlayer.setOnClickListener(this);
        teamRankWorldMap = new HashMap<>();
//        listPlayer = new ArrayList<>();
        playerMap = new HashMap<>();
        teamPlayerMap = new HashMap<>();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
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
            case R.id.btnSaveTeamSuggest:
                saveSuggest();
                break;
            case R.id.btnSavePlayer:
                // save to gosu/player
                savePlayer();
                break;
        }
    }

    // number page for load data.
    private void getNumberPage(String url) {
        showCircleDialog();
        SendRequest.requestGetJsoup(getContext(), url, new SendRequest.StringResponse() {
            @Override
            public void onSuccess(String data) {
                if (data != null) {
                    String valueHref, number;
                    Document document = Jsoup.parse(data);
                    Elements elementPaginations = document.select("tr > td.pagination");
                    if (elementPaginations != null && elementPaginations.size() > 0) {
                        Element elementPagination = elementPaginations.get(0);
                        Elements elementSpans = elementPagination.getElementsByTag("span");
                        for (Element element : elementSpans) {
                            if (element.text().equals("Next")) {
                                continue;
                            } else if (element.text().equals("Last")) {
                                Element elementA = element.parent();
                                valueHref = elementA.attr("href");
                                number = valueHref.substring(valueHref.length() - 1, valueHref.length());
                                try {
                                    defaultNumberPageWord = Integer.valueOf(number);
                                } catch (NumberFormatException ex) {
                                    defaultNumberPageWord = 5;
                                }
                            }
                        }
                    } else {
                        defaultNumberPageWord = 5;
                    }
                }
                hideCircleDialog();
            }

            @Override
            public void onFail(String err) {
                hideCircleDialog();
                showWarningDialog(err);
            }
        });
    }

    // get data rank for team
    private void getMapTeamRank() {

        String furl = urlWord + page;
        String urlLoad;
        for (int j = 1; j <= defaultNumberPageWord; j++) {
            urlLoad = furl + j;
            showCircleDialogOnly();
            SendRequest.requestGetJsoup(getContext(), urlLoad, new SendRequest.StringResponse() {
                @Override
                public void onSuccess(String data) {
                    GosuGamerTeamRankModel model;
                    int data_id, ranking;
                    String country, teamName;
                    Document document = Jsoup.parse(data);
                    Elements elementTrRankings = document.select("tbody > tr.ranking-link");
                    if (elementTrRankings != null && elementTrRankings.size() > 0) {
                        for (int i = 0; i < elementTrRankings.size(); i++) {
                            Element elementTr = elementTrRankings.get(i);
                            data_id = Integer.valueOf(elementTr.attr("data-id"));
                            Elements elementTds = elementTr.getElementsByTag("td");
                            ranking = Integer.valueOf(elementTds.get(0).text());
                            Elements elementSpans = elementTds.get(1).getElementsByTag("span");
                            country = elementSpans.get(1).attr("title");
                            teamName = elementSpans.get(2).text();
                            model = new GosuGamerTeamRankModel(data_id, ranking, country, teamName);
                            teamRankWorldMap.put(String.valueOf(ranking), model);
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


    // get link image team and player name
    private void getDataForTeam() {
        if (teamRankWorldMap == null || teamRankWorldMap.size() == 0) {
            return;
        }
        Iterator<String> iterator = teamRankWorldMap.keySet().iterator();
        String furl;
        while (iterator.hasNext()) {
            final String key = iterator.next();
            furl = urlPlayer + teamRankWorldMap.get(key).getData_id();
            showCircleDialogOnly();
            SendRequest.requestGetJsoup(getContext(), furl, new SendRequest.StringResponse() {
                @Override
                public void onSuccess(String data) {
                    GosuGamerTeamRankModel model = teamRankWorldMap.get(key);
                    Document document = Jsoup.parse(data);
                    Elements elementBases = document.select("div.rank-box > div.base");
                    if (elementBases != null && elementBases.size() > 0) {
                        // get id_photo
                        Element elementBase = elementBases.get(0);
                        Elements elementImages = elementBase.select("div.rank-image");
                        if (elementImages != null) {
                            Element elementImage = elementImages.get(0);
                            model.setId_photo(getId_Photo(elementImage.attr("style")));
                        }
                        // rank local
                        Elements elementRankings = elementBase.select("div.rankings > div.ranking");
                        if (elementRankings != null && elementRankings.size() > 1) {
                            Element elementRanking = elementRankings.get(1);
                            Elements elementSpans = elementRanking.getElementsByTag("span");
                            if (elementSpans != null && elementSpans.size() > 1) {
                                model.setLocal_ranking(Integer.valueOf(elementSpans.get(0).text()));
                                String local = elementSpans.get(1).text();
                                if (local != null) {
                                    if (local.startsWith(sea_local)) {
                                        model.setTypeLocal(Constant.SEA_RANK);
                                    } else if (local.startsWith(euro_local)) {
                                        model.setTypeLocal(Constant.EUROPE_RANK);
                                    } else if (local.startsWith(china_local)) {
                                        model.setTypeLocal(Constant.CHINA_RANK);
                                    } else if (local.startsWith(americas_local)) {
                                        model.setTypeLocal(Constant.AMERICAS_RANK);
                                    }
                                }
                            }
                        }
                        // earning
                        Elements elementEarnings = elementBase.select("div > span.earnings-value");
                        if (elementEarnings != null && elementEarnings.size() > 0) {
                            model.setEarnedMoney(elementEarnings.get(0).text());
                        }
                    }
                    // get player team : name - photo_id
                    Element elementRoster = document.getElementById("roster");
                    if (elementRoster != null) {
                        Elements elementAs = elementRoster.getElementsByTag("a");
                        if (elementAs != null && elementAs.size() > 0) {
                            String name, id_photo;
                            Map<String, String> map = new HashMap<String, String>();
                            for (Element elementA : elementAs) {
                                name = elementA.attr("title");
                                id_photo = getId_Photo(elementA.attr("style"));
                                if (id_photo == null) {
                                    playerMap.put(CommonUtils.escapeKey(name), Constant.NO_IMAGE);
                                    map.put(CommonUtils.escapeKey(name), Constant.NO_IMAGE);
                                } else {
                                    playerMap.put(CommonUtils.escapeKey(name), id_photo);
                                    map.put(CommonUtils.escapeKey(name), id_photo);
                                }
                            }
//                            model.setTeamPlayer(teamPlayer);
                            teamPlayerMap.put(CommonUtils.escapeKey(model.getTeamName()), map);
                        }
                    }
                    hideCircleDialogOnly();
                }

                @Override
                public void onFail(String err) {
                    hideCircleDialogOnly();
                    Log.d("TAG", err);
                }
            });
        }
    }

    private void saveTeam() {
        if (teamRankWorldMap == null || teamRankWorldMap.size() == 0) {
            Toast.makeText(getContext(), "no data save", Toast.LENGTH_SHORT).show();
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("gosu/team");
        reference.setValue(teamRankWorldMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (!task.isSuccessful()) {
                    showWarningDialog(task.getException().getMessage());
                } else {
                    Toast.makeText(getContext(), "data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveSuggest() {
        if (teamPlayerMap == null || teamPlayerMap.size() == 0) {
            Toast.makeText(getContext(), "no data save", Toast.LENGTH_SHORT).show();
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("gosu/suggest");
        reference.setValue(teamPlayerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (!task.isSuccessful()) {
                    showWarningDialog(task.getException().getMessage());
                } else {
                    Toast.makeText(getContext(), "data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePlayer() {
        if (playerMap == null || playerMap.size() == 0) {
            Toast.makeText(getContext(), "no data save", Toast.LENGTH_SHORT).show();
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("gosu/player");
        reference.setValue(playerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (!task.isSuccessful()) {
                    showWarningDialog(task.getException().getMessage());
                } else {
                    Toast.makeText(getContext(), "data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getId_Photo(String urlImage) {
        if (urlImage == null) {
            return null;
        }
        String[] spilitUrl = urlImage.split("/");
        String endSpilit = spilitUrl[spilitUrl.length - 1];
        String id_Photo = endSpilit.substring(0, endSpilit.length() - 3);
        if (id_Photo.equals("no-image.png")) {
            return null;
        } else {
            return id_Photo.substring(0, id_Photo.length() - 5);
        }
    }
}
