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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.SendRequest;


public class HeroesFragment extends BaseFragment implements View.OnClickListener {
    private Button btnGetData, btnSaveHeroes;
    private Map<String, String> mapHeroes;
    // image big : 127x71
    // image small : 59x33
    private String urlImage = "http://cdn.dota2.com/apps/dota2/images/heroes/";

    public HeroesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heroes, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGetData = (Button) view.findViewById(R.id.btnGetData);
        btnSaveHeroes = (Button) view.findViewById(R.id.btnSaveHeroes);
        btnGetData.setOnClickListener(this);
        btnSaveHeroes.setOnClickListener(this);
        mapHeroes = new HashMap<>();
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
                getHeroesData();
                btnSaveHeroes.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSaveHeroes:
                saveHeroesData();
                break;
        }
    }

    private void getHeroesData() {
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
                                            linkImg = arrLinkImg[arrLinkImg.length -1];
                                        }
                                    }
                                    if (arrLinkName != null && arrLinkName.length > 0 && linkImg != null) {
                                        mapHeroes.put(arrLinkName[arrLinkName.length - 1], linkImg.substring(0, linkImg.length()-12));
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

    private void saveHeroesData() {
        if (mapHeroes == null || mapHeroes.size() == 0) {
            return;
        }
        showCircleDialog();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mFirebaseDatabase.getReference("heroes");
        reference.setValue(mapHeroes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideCircleDialog();
                if (!task.isSuccessful()) {
                    showWarningDialog(task.getException().getMessage());
                }
            }
        });
    }
}
