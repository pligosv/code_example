package com.openlife.view.fragment.dictionary;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.openlife.R;
import com.openlife.model.dictionary.DictionaryTitle;
import com.openlife.model.profile.ProfileModel;
import com.openlife.presenter.contract.DictionaryContract;
import com.openlife.presenter.dictionary.DictionaryPresenter;
import com.openlife.view.adapter.dictionary.DictionaryAdapter;
import com.openlife.view.annotation.Layout;
import com.openlife.view.annotation.Presenter;
import com.openlife.view.fragment.AbstractFragment;
import com.openlife.view.fragment.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@Layout(R.layout.fragment_dictionary)
@Presenter(DictionaryPresenter.class)
public class DictionaryFragment extends AbstractFragment<DictionaryPresenter>
        implements DictionaryContract.View, DictionaryAdapter.ProfileClickListener {

    @BindView(R.id.rv_dictionary_container)
    RecyclerView rvDictionary;

    private DictionaryAdapter adapter;
    private final List<DictionaryTitle> titleList = new ArrayList<>();


    @Override
    protected void initView(View view) {
        super.initView(view);

        LinearLayoutManager la = new LinearLayoutManager(getActivity());
        rvDictionary.setLayoutManager(la);

        adapter = new DictionaryAdapter(getActivity(), titleList);
        adapter.setProfileClickListener(this);
        rvDictionary.setAdapter(adapter);

        getPresenter().loadData();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint(getString(R.string.search_hint));
            ImageView searchPlate = (ImageView) searchView.findViewById(R.id.search_button);
            searchPlate.setColorFilter(R.color.colorBlack);
            ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
            closeButton.setColorFilter(R.color.colorGrey);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }


    @Override
    protected int getNavigationIconResId() {
        return R.drawable.ic_navigation_back_2;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.dictionary_title);
    }

    @Override
    protected int getMenuResId() {
        return R.menu.toolbar;
    }

    @Override
    public void showDictionary(List<DictionaryTitle> dictionaryTitles) {
        titleList.clear();
        titleList.addAll(dictionaryTitles);
        adapter.notifyParentDataSetChanged(true);
    }

    @Override
    public void onProfileClicked() {
        // TODO: 10.03.2017 change data
        ProfileModel model = new ProfileModel("", false,"");
        getTransitManager().switchFragment(ProfileFragment.class, model.toBundle());
    }
}
