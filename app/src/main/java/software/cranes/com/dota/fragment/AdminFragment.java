package software.cranes.com.dota.fragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GosuGamerTeamRankModel;


public class AdminFragment extends BaseFragment implements View.OnClickListener {
    private Button btnGetTeamRank;
    private Button btnGetTeamPlayer;
    private Button btnSave;
    private Button btnAddProfessionMatch;
    private Button btnAddAmateurMatch;
    private Button btnLoadHeroes, btnSaveHeroes;
    private Map<String, GosuGamerTeamRankModel> teamRankWorldMap;
    private Map<String, String> playerMap;
    private Map<String, Map<String, String>> teamPlayerMap;
    private Map<String, String> mapHeroes;
    private int defaultNumberPageWord = 5;
    private String urlWord = "http://www.gosugamers.net/dota2/rankings?type=team";
    private String urlPlayer = "http://www.gosugamers.net/rankings/show/team/";
    private String page = "&page=";
    private String sea_local = "S";
    private String euro_local = "E";
    private String china_local = "C";
    private String americas_local = "A";

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamRankWorldMap = new HashMap<>();
        teamPlayerMap = new HashMap<>();
        playerMap = new HashMap<>();
        mapHeroes = new HashMap<>();
        getNumberPage(urlWord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        btnGetTeamRank = (Button) view.findViewById(R.id.btnGetTeamRank);
        btnGetTeamPlayer = (Button) view.findViewById(R.id.btnGetTeamPlayer);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnAddProfessionMatch = (Button) view.findViewById(R.id.btnAddProfessionMatch);
        btnAddAmateurMatch = (Button) view.findViewById(R.id.btnAddAmateurMatch);
        btnLoadHeroes = (Button) view.findViewById(R.id.btnLoadHeroes);
        btnSaveHeroes = (Button) view.findViewById(R.id.btnSaveHeroes);

        btnGetTeamRank.setOnClickListener(this);
        btnGetTeamPlayer.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnAddProfessionMatch.setOnClickListener(this);
        btnAddAmateurMatch.setOnClickListener(this);
        btnLoadHeroes.setOnClickListener(this);
        btnSaveHeroes.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetTeamRank:
                // get map team follow ranker
                getMapTeamRank();
                btnGetTeamPlayer.setVisibility(View.VISIBLE);
                break;
            case R.id.btnGetTeamPlayer:
                // get data for team : ex player, local
                getPlayerForTeam();
                btnSave.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSave:
                saveData();
                break;
            case R.id.btnLoadHeroes:
                loadHeroesData();
                btnSaveHeroes.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSaveHeroes:
                saveHeroes();
                break;
            case R.id.btnAddProfessionMatch:
                replaceFragment(R.id.aboutFrame, new AddProfessionFragment(), true);
                break;
            case R.id.btnAddAmateurMatch:
                replaceFragment(R.id.aboutFrame, new AddPublicMatchFragment(), true);
                break;
            default:
                break;
        }
    }

    // get number page for load data
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
                            if (element.text().equals("Last")) {
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

    // get all Team in link http://www.gosugamers.net/dota2/rankings?type=team&page=1
    private void getMapTeamRank() {
        String furl = new StringBuilder(urlWord).append(page).toString();
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
                    Elements elementTds, elementSpans;
                    Document document = Jsoup.parse(data);
                    Elements elementTrRankings = document.select("tbody > tr.ranking-link");
                    if (elementTrRankings != null && elementTrRankings.size() > 0) {
                        for (Element elementTrRanking : elementTrRankings) {
                            data_id = Integer.valueOf(elementTrRanking.attr("data-id"));
                            elementTds = elementTrRanking.getElementsByTag("td");
                            ranking = Integer.valueOf(elementTds.get(0).text());
                            elementSpans = elementTds.get(1).getElementsByTag("span");
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

    // get Team Player for Team
    private void getPlayerForTeam() {
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
                            Map<String, String> map = new HashMap<>();
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
                            teamPlayerMap.put(CommonUtils.escapeKey(model.getTeamName()), map);
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
    }
    /*
        save team : https://fir-dota.firebaseio.com/gosu/team
        save player : https://fir-dota.firebaseio.com/gosu/player
        save suggest : https://fir-dota.firebaseio.com/gosu/suggest_team_player
        save suggest : https://fir-dota.firebaseio.com/gosu/suggest_team
        save suggest : https://fir-dota.firebaseio.com/gosu/suggest_player
     */

    private void saveData() {
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        // save team : https://fir-dota.firebaseio.com/gosu/team
        if (teamRankWorldMap != null && teamRankWorldMap.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("gosu/team");
            reference.setValue(teamRankWorldMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "gosu/team failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "gosu/team saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // save player : https://fir-dota.firebaseio.com/gosu/player
        if (playerMap != null && playerMap.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("gosu/player");
            reference.setValue(playerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "gosu/player failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "gosu/player saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // save https://fir-dota.firebaseio.com/gosu/suggest_team_player
        if (teamPlayerMap != null && teamPlayerMap.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("gosu/suggest_team_player");
            reference.setValue(teamPlayerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "gosu/suggest_team_player failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "gosu/suggest_team_player saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // save suggest : https://fir-dota.firebaseio.com/gosu/suggest_team
        if (teamRankWorldMap != null && teamRankWorldMap.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("gosu/suggest_team");
            reference.setValue(getListTeam(teamRankWorldMap)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "gosu/suggest_team failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "gosu/suggest_team saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // save suggest : https://fir-dota.firebaseio.com/gosu/suggest_player
        if (playerMap != null && playerMap.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("gosu/suggest_player");
            reference.setValue(getListPlayer(playerMap)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "gosu/suggest_player failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "gosu/suggest_player saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
        load heroes to map<String, String>
        player can choice heroes
     */
    private void loadHeroesData() {
        String url = "http://www.dota2.com/heroes/";
        showCircleDialog();
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
                                        mapHeroes.put(arrLinkName[arrLinkName.length - 1], linkImg.substring(0, linkImg.length() - 12));
                                    }
                                }
                            }
                        }
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

    /*
       Map<String, String> save to https://fir-dota.firebaseio.com/heroes
     */
    private void saveHeroes() {
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        if (mapHeroes != null && mapHeroes.size() != 0) {
            showCircleDialogOnly();
            DatabaseReference reference = mFirebaseDatabase.getReference("heroes");
            reference.setValue(mapHeroes).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (mapHeroes != null && mapHeroes.size() != 0) {
            showCircleDialogOnly();
            mFirebaseDatabase.getReference("heroes_suggest").setValue(getListHeroes(mapHeroes)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideCircleDialogOnly();
                    if (! task.isSuccessful()) {
                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
        get id for image from urlImage
        if no
     */
    private String getId_Photo(String urlImage) {
        if (urlImage == null) {
            return null;
        }
        String[] spilitUrl = urlImage.split("/");
        String endSpilit = spilitUrl[spilitUrl.length - 1];
        if (endSpilit == null || endSpilit.length() < 8) {
            return null;
        }
        String id_Photo = endSpilit.substring(0, endSpilit.length() - 8);
        if (id_Photo.startsWith("n")) {
            return null;
        } else {
            return id_Photo;
        }
    }

    private List<String> getListTeam(Map<String, GosuGamerTeamRankModel> map) {
        List<String> list = new ArrayList<>();
        for (GosuGamerTeamRankModel model : map.values()) {
            if (model != null) {
                list.add(model.getTeamName());
            }
        }
        return list;
    }

    private List<String> getListPlayer(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            list.add(CommonUtils.unescapeKey(iterator.next()));
        }
        return list;
    }

    private List<String> getListHeroes(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}

