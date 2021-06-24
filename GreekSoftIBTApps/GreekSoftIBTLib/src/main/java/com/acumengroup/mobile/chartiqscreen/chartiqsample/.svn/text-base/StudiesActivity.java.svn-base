package com.acumengroup.mobile.chartiqscreen.chartiqsample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.chartiqscreen.HideKeyboardOnTouchListener;
import com.acumengroup.mobile.chartiqscreen.MainActivity;
import com.acumengroup.mobile.chartiqscreen.Util;
import com.acumengroup.mobile.chartiqscreen.studies.StudiesAdapter;
import com.acumengroup.mobile.chartiqscreen.studies.StudyOptionsActivity;
import com.acumengroup.mobile.chartiqscreen.ui.DividerDecoration;
import com.acumengroup.mobile.chartiqscreen.ui.StickyHeaderDecoration;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.chartiq.sdk.ChartIQ;
import com.acumengroup.chartiq.sdk.Promise;
import com.acumengroup.chartiq.sdk.model.Study;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class StudiesActivity extends AppCompatActivity {
    public static final String STUDIES_LIST = "studiesList";
    public static final String ACTIVE_STUDIES = "activeStudies";
    private static final int RESULT_STUDY_REMOVED = 4;
    private Toolbar toolbar;
    private RecyclerView studiesList;
    private RelativeLayout searchlayout;
    private ArrayList<Study> studies;
    private ArrayList<Study> activeStudies = new ArrayList<>();
    private Set<Study> lastSelection;
    private GreekTextView studiesCount;
    LinearLayout parent;
    private Toolbar selectiontoolbar;
    private View closeSelection;
    private StudiesAdapter studiesAdapter;
    private GreekEditText studySearch;
    private View addButton;
    private View removeButton;
    private GreekTextView clearStudySearchInput;
    private ChartIQ chartIQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_studies);
        parent=findViewById(R.id.activity_studies);
        studiesCount = (GreekTextView) findViewById(R.id.studies_count);
        selectiontoolbar = (Toolbar) findViewById(R.id.selection_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        studiesList = (RecyclerView) findViewById(R.id.studies_list);
        searchlayout = (RelativeLayout) findViewById(R.id.search_layout);
        chartIQ = (ChartIQ) findViewById(R.id.chart);
        chartIQ.getSettings().setJavaScriptEnabled(true);
        chartIQ.getSettings().setDomStorageEnabled(true);
        chartIQ.addJavascriptInterface(chartIQ, "promises");
        chartIQ.loadUrl(MainActivity.chartUrl);


        studiesList.setOnTouchListener(new HideKeyboardOnTouchListener());

        if (getIntent().hasExtra(STUDIES_LIST)) {
            studies = (ArrayList<Study>) getIntent().getExtras().getSerializable(STUDIES_LIST);
            Collections.sort(studies, new Comparator<Study>() {
                @Override
                public int compare(Study o1, Study o2) {
                    if (o1.name.isEmpty()) {
                        if (o2.name.isEmpty()) {
                            return o1.shortName.compareToIgnoreCase(o2.shortName);
                        }
                        return o1.shortName.compareToIgnoreCase(o2.name);
                    } else if (o2.name.isEmpty()) {
                        return o1.name.compareToIgnoreCase(o2.shortName);
                    } else {
                        return o1.name.compareToIgnoreCase(o2.name);
                    }
                }
            });
        }
        if (getIntent().hasExtra(ACTIVE_STUDIES)) {
            activeStudies = (ArrayList<Study>) getIntent().getExtras().getSerializable(ACTIVE_STUDIES);
            Collections.sort(activeStudies, new Comparator<Study>() {
                @Override
                public int compare(Study o1, Study o2) {
                    if (o1.name.isEmpty()) {
                        if (o2.name.isEmpty()) {
                            return o1.shortName.compareToIgnoreCase(o2.shortName);
                        }
                        return o1.shortName.compareToIgnoreCase(o2.name);
                    } else if (o2.name.isEmpty()) {
                        return o1.name.compareToIgnoreCase(o2.shortName);
                    } else {
                        return o1.name.compareToIgnoreCase(o2.name);
                    }
                }
            });
        }
        configureStudiesList(studiesList, activeStudies, studies);

        closeSelection = findViewById(R.id.close_selection);
        closeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
            }
        });

        clearStudySearchInput = (GreekTextView) findViewById(R.id.clear);
        clearStudySearchInput.setVisibility(View.INVISIBLE);

        studySearch = (GreekEditText) findViewById(R.id.study_search_field);
        if(AccountDetails.getThemeFlag(StudiesActivity.this).equalsIgnoreCase("white")){
            parent.setBackgroundColor(getColor(R.color.white));
            studySearch.setBackgroundColor(getColor(R.color.white));
            studySearch.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            clearStudySearchInput.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            studiesCount.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            studiesCount.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            studiesList.setBackgroundColor(getColor(R.color.white));
            searchlayout.setBackgroundColor(getColor(R.color.white));
            selectiontoolbar.setBackgroundColor(getColor(R.color.white));
            toolbar.setBackgroundColor(getColor(R.color.white));
        }
  /*      studySearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    setFilteredStudies();
                    Util.hideKeyboard(studySearch);
                    return true;
                }
                return false;
            }
        });*/
        studySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    clearStudySearchInput.setVisibility(View.VISIBLE);
                } else {
                    clearStudySearchInput.setVisibility(View.INVISIBLE);
                }
                setFilteredStudies();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Study s : lastSelection) {
                    chartIQ.addStudy(s, true);
                }

                chartIQ.getActiveStudies().then(new Promise.Callback<Study[]>() {
                    @Override
                    public void call(Study[] studies) {
                        activeStudies.addAll(lastSelection);
                        studiesAdapter.setActiveStudiesList(activeStudies);
                        clearSelection();
                    }
                });
            }
        });
        removeButton = findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeStudies.removeAll(lastSelection);
                studiesAdapter.getAvailableStudies().addAll(lastSelection);
                clearSelection();
            }
        });
    }

    private void setFilteredStudies() {
        if (studies != null) {
            String text = studySearch.getText().toString();
            ArrayList<Study> filteredStudies = new ArrayList<Study>();
            for (Study study : studies) {
                if (!activeStudies.contains(study) && (study.name.toLowerCase().contains(text) || study.shortName.toLowerCase().contains(text))) {
                    filteredStudies.add(study);
                }
            }
            studiesAdapter.setAvailableStudies(filteredStudies);
            studiesAdapter.notifyDataSetChanged();
        }
    }

    public void clearStudySearch(View view) {
        studySearch.setText("");
        setFilteredStudies();
    }

    private void clearSelection() {
        if (lastSelection != null) {
            lastSelection.clear();
        }
        studiesAdapter.clearSelection();
        selectiontoolbar.setVisibility(View.GONE);
    }

    private void configureStudiesList(final RecyclerView studiesList, final List<Study> activeStudiesList, List<Study> availableStudies) {
        studiesAdapter = new StudiesAdapter(this, activeStudiesList, availableStudies, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Study clickedStudy = studiesAdapter.getItemByPosition((Integer) v.getTag());
                if (activeStudiesList != null && activeStudiesList.size() > 0) {
                    for (final Study s : activeStudiesList) {
                        if ((s.shortName != null && s.shortName.equals(clickedStudy.shortName)) || (s.type != null && (s.type.equals(clickedStudy.shortName) || s.type.equals(clickedStudy.type)))) {
                            String name = s.type != null ? s.type : s.shortName;
                            chartIQ.getStudyOutputParameters(name).then(new Promise.Callback<String>() {
                                String name = s.type != null ? s.type : s.shortName;

                                @Override
                                public void call(final String outputs) {
                                    chartIQ.getStudyInputParameters(name).then(new Promise.Callback<String>() {
                                        @Override
                                        public void call(final String inputs) {
                                            chartIQ.getStudyParameters(name).then(new Promise.Callback<String>() {
                                                @Override
                                                public void call(final String parameters) {
                                                    Intent studyOptionsIntent = new Intent(StudiesActivity.this, StudyOptionsActivity.class);
                                                    studyOptionsIntent.putExtra("study", clickedStudy);
                                                    studyOptionsIntent.putExtra("outputs", outputs);
                                                    studyOptionsIntent.putExtra("inputs", inputs);
                                                    studyOptionsIntent.putExtra("parameters", parameters);
                                                    startActivityForResult(studyOptionsIntent, 0);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            break;
                        }
                    }
                }
            }
        });
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(studiesAdapter);
        final DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.white)
                .build();

        studiesList.setAdapter(studiesAdapter);
        studiesList.addItemDecoration(divider, 0);
        studiesList.addItemDecoration(decoration, 1);
        studiesAdapter.setSelectionChangeListener(new StudiesAdapter.OnSelectionChangeListener() {
            @Override
            public void onSelectionchange(Set<Study> selectedStudies, boolean isActive) {
                lastSelection = selectedStudies;
                if (selectedStudies.size() > 0) {
                    studiesCount.setText(String.valueOf(selectedStudies.size()));
                    selectiontoolbar.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(isActive ? View.VISIBLE : View.GONE);
                    addButton.setVisibility(isActive ? View.GONE : View.VISIBLE);
                } else {
                    selectiontoolbar.setVisibility(View.GONE);
                }
                InputMethodManager imm = (InputMethodManager) StudiesActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(studiesList.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra(ACTIVE_STUDIES, activeStudies));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode) {
            if (RESULT_OK == resultCode) {
                Study updatedStudy = (Study) data.getSerializableExtra("study");
                for (Study s : activeStudies) {
                    if (updatedStudy.name.equals(s.name)) {
                        activeStudies.set(activeStudies.indexOf(s), updatedStudy);
//                        chartIQ.removeStudy(s);
//                        chartIQ.addStudy(updatedStudy);
                    }
                }
            } else if (RESULT_STUDY_REMOVED == resultCode) {
                Study removedStudy = (Study) data.getSerializableExtra("study");
                activeStudies.remove(removedStudy);
                studiesAdapter.setActiveStudiesList(activeStudies);
                studiesAdapter.getAvailableStudies().add(removedStudy);
                clearSelection();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
