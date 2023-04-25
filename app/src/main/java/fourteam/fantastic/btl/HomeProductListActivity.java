package fourteam.fantastic.btl;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeProductListActivity extends AppCompatActivity {

    private BottomNavigationView mbottomNavigationView;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product_list);

        String token = getIntent().getStringExtra("token");

        System.out.println("check token: " + token);


        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHomeList:
                        mviewPager.setCurrentItem(0);
                        break;
                    case R.id.menuWishlist:
                        Toast.makeText(HomeProductListActivity.this, "menuWishlist", Toast.LENGTH_SHORT).show();
                        mviewPager.setCurrentItem(1);
                        break;
                    case R.id.menuOrder:
                        mviewPager.setCurrentItem(2);
                        break;
                    case R.id.menuMyCards:
                        mviewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
//        NavController navController = (NavController) Navigation.findNavController(this, R.id.navHostFragment);
//        NavigationUI.setupWithNavController(navigationView, navController);

        mbottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationBottom);
        mviewPager = (ViewPager) findViewById(R.id.viewPager);
        setUpViewPager();
        mbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHomeList:
                        mviewPager.setCurrentItem(0);
                        break;
                    case R.id.menuWishlist:
                        mviewPager.setCurrentItem(1);
                        break;
                    case R.id.menuOrder:
                        mviewPager.setCurrentItem(2);
                        break;
                    case R.id.menuMyCards:
                        mviewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

//        search
        SearchView searchView = findViewById(R.id.search_view);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit (String query){
                    // Perform the search.
                    // TODO: Implement the search logic here.
                    return false;
                }

                @Override
                public boolean onQueryTextChange (String newText){
                  // Do nothing.
                  return false;
                }
            });
        }

        ImageView imageView = findViewById(R.id.image_view);
        if(imageView != null)
            imageView.setImageResource(R.drawable.ic_image_search);
//        end search
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setUpViewPager(){
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewPager.setAdapter(viewPageAdapter);

        mviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: mbottomNavigationView.getMenu().findItem(R.id.menuHomeList).setChecked(true); break;
                    case 1: mbottomNavigationView.getMenu().findItem(R.id.menuWishlist).setChecked(true); break;
                    case 2: mbottomNavigationView.getMenu().findItem(R.id.menuOrder).setChecked(true); break;
                    case 3: mbottomNavigationView.getMenu().findItem(R.id.menuMyCards).setChecked(true); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}