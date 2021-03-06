package alphadelete.aguaconsciente.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import alphadelete.aguaconsciente.R;
import alphadelete.aguaconsciente.dao.TypeDS;
import alphadelete.aguaconsciente.models.TypeItem;
import alphadelete.aguaconsciente.ui.adapters.TypeItemArrayViewAdapter;

public class MainActivity extends BaseActivity {
    public final static String EXTRA_MESSAGE = "alphadelete.aguaconsciente.MESSAGE";
    private TypeDS typeDatasource;
    private TypeItemArrayViewAdapter ca;
    private List<TypeItem> allTimers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Activity based on XML layout
        setContentView(R.layout.activity_main);

        // Override super method after the super
        super.onCreate(savedInstanceState);

        // Set toolbar as Actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Set the MainActivity title
        this.setTitle(R.string.app_name);

        // Define RecyclerView LayoutManager
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,2);
        recList.setLayoutManager(llm);

        // Get Timers list from Database
        allTimers = listTimers();

        if (allTimers.size() > 0) {
            // Set and Fill ListView
            ca = new TypeItemArrayViewAdapter(this, allTimers);
            recList.setAdapter(ca);
        }
    }

    private List<TypeItem> listTimers(){
        List<TypeItem> _allTimers;

        // Open connection to database
        typeDatasource = new TypeDS(this);
        typeDatasource.open();

        //allTimers.clear();
        _allTimers = typeDatasource.getType();

        // Close connection to timer database
        typeDatasource.close();

        return _allTimers;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close connection to timer database
        typeDatasource.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Clear and Refresh the grid
        allTimers.clear();
        allTimers.addAll(listTimers());
        ca.notifyDataSetChanged();
    }


}
