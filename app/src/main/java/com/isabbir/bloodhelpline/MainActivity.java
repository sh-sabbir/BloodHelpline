package com.isabbir.bloodhelpline;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentHome.OnFragmentInteractionListener,FragmentAbout.OnFragmentInteractionListener,FragmentDonors.OnFragmentInteractionListener, BackupData.OnBackupListener {
    private CharSequence topTitle;

    private Context context;

    private DBhandler db;

    private BackupData backupData;

    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        context = this;

        topTitle = "Blood Helpline";

        backupData = new BackupData(context);
        backupData.setOnBackupListener(MainActivity.this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.home, 0);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_backup:
                backupData.exportToSD();
                break;
            case R.id.action_restore:
                backupData.importFromSD();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.home) {
            // Handle the camera action
            fragmentClass = FragmentHome.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();
        }
//        if (id == R.id.home) {
//            topTitle = "Blood Helpline";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.content_home, null);
//            layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            int total_donor = db.getAllDonor();
//            int total_ap = db.getAllDonorByGroup("A+");
//            int total_an = db.getAllDonorByGroup("A-");
//            int total_bp = db.getAllDonorByGroup("B+");
//            int total_bn = db.getAllDonorByGroup("B-");
//            int total_op = db.getAllDonorByGroup("O+");
//            int total_on = db.getAllDonorByGroup("O-");
//            int total_abp = db.getAllDonorByGroup("AB+");
//            int total_abn = db.getAllDonorByGroup("AB-");
//            db.close();
//
//            Button total = (Button) findViewById(R.id.totalDonor);
//            total.setText("Total Blood Donor "+ total_donor);
//
//
//            Button ap = (Button) findViewById(R.id.bAP);
//            ap.setText("A+ "+total_ap);
//            ap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.ap));
//                }
//            });
//
//            Button an = (Button) findViewById(R.id.bAN);
//            an.setText("A- "+total_an);
//            an.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.an));
//                }
//            });
//
//            Button bp = (Button) findViewById(R.id.bBP);
//            bp.setText("B+ "+total_bp);
//            bp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.bp));
//                }
//            });
//
//            Button bn = (Button) findViewById(R.id.bBN);
//            bn.setText("B- "+total_bn);
//            bn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.bn));
//                }
//            });
//
//            Button op = (Button) findViewById(R.id.bOP);
//            op.setText("O+ "+total_op);
//            op.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.op));
//                }
//            });
//
//            Button on = (Button) findViewById(R.id.bON);
//            on.setText("O- "+total_on);
//            on.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.on));
//                }
//            });
//
//            Button abp = (Button) findViewById(R.id.bABP);
//            abp.setText("AB+ "+total_abp);
//            abp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.abp));
//                }
//            });
//
//            Button abn = (Button) findViewById(R.id.bABN);
//            abn.setText("AB- "+total_abn);
//            abn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.abn));
//                }
//            });
//
//
//        }
        else if (id == R.id.ap) {
//            topTitle = "A+ (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "A+";
//            Cursor cursor = db.getDetails(filter);
//
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("bloodGroup", "O+");//put string, int, etc in bundle with a key value
                fragment.setArguments(data);//Finally set argument bundle to fragment

            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();


        } else if (id == R.id.an) {
//            topTitle = "A- (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "A-";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

        } else if (id == R.id.bp) {

//            topTitle = "B+ (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "B+";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

        } else if (id == R.id.bn) {

//            topTitle = "B- (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "B-";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

        } else if (id == R.id.op) {

//            topTitle = "O+ (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "O+";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

        } else if (id == R.id.on) {

//            topTitle = "O- (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "O-";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

        } else if (id == R.id.abp) {

//            topTitle = "AB+ (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "AB+";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();


        } else if (id == R.id.abn) {

//            topTitle = "AB- (ve) Blood Donors";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.ap, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            DBhandler db = new DBhandler(getApplicationContext());
//            //EmployeeDatabase empClick = new EmployeeDatabase(getApplicationContext());
//
//            ListView listCustom = (ListView) findViewById(R.id.superlist);
//            //Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
//            //startManagingCursor(cursor);
//            String filter = "AB-";
//            Cursor cursor = db.getDetails(filter);
//            if(cursor !=null) {
//                listCustom.setAdapter(
//                        new android.support.v4.widget.SimpleCursorAdapter(MainActivity.this, R.layout.list_item_view, cursor, new String[]{
//                                "name", "age", "blood_group"
//                        }, new int[]{R.id.donorName, R.id.donorAge, R.id.donorPhone}, 0));
//
//                listCustom.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Log.d("Clicked item id", " "+ id);
//                        String Doid = Long.toString(id);
//                        Intent intent = new Intent(MainActivity.this,donorDetails.class);
//                        intent.putExtra("DonorID", Doid);
//                        startActivity(intent);
//                    }
//                });
//            }

            fragmentClass = FragmentDonors.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();


        } else if (id == R.id.nav_newDonor) {
//            topTitle = "Add New Donor";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.content_nd, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);
//
//            // Inserting Shop/Rows
//            Log.d("Insert: ", "Inserting ..");
//
//            Button more = (Button) findViewById(R.id.dSave);
//            more.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    //Intent myIntent = new Intent(view.getContext(), agones.class);
//                    //startActivityForResult(myIntent, 0);
//                    final EditText name = (EditText) findViewById(R.id.dName);
//                    final EditText age = (EditText) findViewById(R.id.dAge);
//                    final EditText address = (EditText) findViewById(R.id.dAddress);
//                    final EditText phone = (EditText) findViewById(R.id.dPhone);
//                    final Spinner bloodGroup = (Spinner) findViewById(R.id.dBloodGroup);
//                    final DatePicker lastDonate = (DatePicker) findViewById(R.id.dLastDonate);
//
//                    int   day  = lastDonate.getDayOfMonth();
//                    int   month= lastDonate.getMonth();
//                    int   year = (lastDonate.getYear() - 1900);
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//
//                    final String DoName = name.getText().toString();
//                    final String DoAge = age.getText().toString();
//                    final String DoAddress = address.getText().toString();
//                    final String DoPhone = phone.getText().toString();
//                    final String DoGroup = bloodGroup.getSelectedItem().toString();
//                    final String DoLastDate = sdf.format(new Date(year, month, day));
//
//
//                    DBhandler db = new DBhandler(MainActivity.this);
//                    db.addShop(new Shop(DoName, DoAge, DoAddress, DoPhone, DoGroup, DoLastDate));
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
//                    alertDialog.setTitle("Done");
//                    alertDialog.setMessage("New Donor Info Successfully Added ! ");
//
//                    alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // here you can add functions
//                            name.setText("");
//                            age.setText("");
//                            phone.setText("");
//                            address.setText("");
//                            bloodGroup.setSelection(0);
//
//                        }
//                    });
//
//                    alertDialog.show();  //<-- See This!
//                }
//
//            });

//            fragmentClass = FragmentDonors.class;
//            try {
//                fragment = (Fragment) fragmentClass.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

            Intent newDonor = new Intent(this,ActivityNewDonor.class);
            startActivity(newDonor);


        }
        else if (id == R.id.nav_dev) {
//            topTitle = "About Developer";
//            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_container);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.about_dev, null);
//            mainLayout.removeAllViews();
//            mainLayout.addView(layout);

            fragmentClass = FragmentAbout.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();
        }

        //getSupportActionBar().setTitle(topTitle);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onFinishExport(String error) {
        String notify = error;

        if (error == null) {
            notify = "Export success";
        }
        Toast.makeText(MainActivity.this, notify, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "Please check the number you entered", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        String yo = null;

        if (error == null) {
            yo = "Import success";
            //updateListNote();
        }
        Toast.makeText(context, yo, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}