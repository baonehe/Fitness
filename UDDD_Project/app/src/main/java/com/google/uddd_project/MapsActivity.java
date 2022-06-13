package com.google.uddd_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.uddd_project.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener {
    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private ActivityMapsBinding binding;
    private LocationListener listenerSingle, listenerUpdate;
    private LocationManager managerSingle, managerUpdate;
    private SensorManager sensorManager;
    private Sensor sensor;

    private static double distance = 0;
    private static double totalDistance = 0;
    private static double totalCalories = 0;
    private static double speed = 0;
    private static int step = 0;
    private static boolean isRun = false, isStart = false;
    private static final long MIN_TIME = 1000;
    private static final long MIN_DIST = 1;
    private static final int REQUEST_CODE = 101;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    TextView tvNumStep, tvDistance, tvSpeed;
    Button startBtn, endBtn;
    private boolean locationPermissionGranted;
    private boolean isCounterSensor;
    private Location lastKnownLocation,
            startLocation, //Vi tri bat dau chay
            curLocation; //Vi tri hien tai
    private LatLng curLatLng, //Toa do hien tai
            tempLatLng; //Toa do tam
    private Marker startMarker, endMarker, tempMarker;
    private Polyline polyline;
    private List<Polyline> polylines = new ArrayList<Polyline>();
    private List<LatLng> latLngList = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        if (!sharedPreferences.getString("DistancesKM", "").isEmpty() || !sharedPreferences.getString("Calories", "").isEmpty()) {
            totalDistance = Double.parseDouble(sharedPreferences.getString("DistancesKM", ""));
            totalCalories = Double.parseDouble(sharedPreferences.getString("Calories", ""));
        }
        builder = new AlertDialog.Builder(MapsActivity.this);
        startBtn = findViewById(R.id.startButton);
        endBtn = findViewById(R.id.endButton);

//        tvNumStep = findViewById(R.id.numberOfStepTextView);
        tvDistance = findViewById(R.id.totalDistanceTextView);
        tvSpeed = findViewById(R.id.averagePaceTextView);

        getPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        managerUpdate = (LocationManager) getSystemService(LOCATION_SERVICE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (!locationPermissionGranted) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION}, PackageManager.PERMISSION_GRANTED);
                    }
                }

                if (!isRun) {
                    builder.setMessage("Start Run?").setCancelable(false).setPositiveButton("Start", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            isRun = true;
                            isStart = true;
                            startBtn.setEnabled(false);
                            endBtn.setEnabled(true);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });


                    AlertDialog alert = builder.create();
                    alert.setTitle("Notification");
                    alert.show();
                }

//                listenerSingle = new LocationListener() {
//                    @Override
//                    public void onLocationChanged(@NonNull Location location) {
//                        startLocation = location;
//                    }
//                };
//
//                try {
//                    managerSingle.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listenerSingle, null);
//                } catch (SecurityException e) {
//                    e.printStackTrace();
//                }

//                startLocation = managerSingle.getCurrentLocation(LocationManager.NETWORK_PROVIDER, );
//
//                latLngList.add(new LatLng(startLocation.getLatitude(), startLocation.getLongitude()));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.getLatitude(), startLocation.getLongitude())).title("Start Position"));
//                Toast.makeText(MapsActivity.this, "Start Run", Toast.LENGTH_SHORT).show();
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRun) {
                    builder.setMessage("End Run?").setCancelable(false).setPositiveButton("End", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            isRun = false;
                            isStart = false;
                            if (tempMarker != null) {
                                tempMarker.remove();
                            }
                            tempMarker = mMap.addMarker(new MarkerOptions().position(tempLatLng).title("Destination"));
                            endMarker = tempMarker;
                            totalDistance += distance;
                            totalCalories += (distance * 0.0625);
                            editor.putString("DistancesKM", String.format("%.2f", totalDistance));
                            editor.putString("Calories", String.format("%.3f", totalCalories));
                            editor.commit();
                            startBtn.setEnabled(true);
                            endBtn.setEnabled(false);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Notification");
                    alert.show();
                }
//                managerSingle.removeUpdates(listenerSingle);
            }
        });

        findViewById(R.id.imgReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRun = false;
                isStart = false;
                distance = 0;
                speed = 0;
                tvDistance.setText("0.0");
                tvSpeed.setText("0.0");
                startMarker.remove();
                endMarker.remove();
                for (Polyline line : polylines) {
                    line.remove();
                }
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == sensor) {
            step = (int) sensorEvent.values[0];
//            tvNumStep.setText(step);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        startRun();
    }

    private void startRun() {
        listenerUpdate = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (isRun) {
                    if (isStart) {
                        isStart = false;
                        startLocation = location;

                        latLngList.add(new LatLng(startLocation.getLatitude(), startLocation.getLongitude()));
                        startMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.getLatitude(), startLocation.getLongitude())).title("Start Position"));

                        Toast.makeText(MapsActivity.this, "Start Run", Toast.LENGTH_SHORT).show();
                        curLocation = startLocation;
                    }
                    if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                        isCounterSensor = true;
                    } else {
                        isCounterSensor = false;
                    }
                    curLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    latLngList.add(curLatLng);
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                    polyline = mMap.addPolyline(polylineOptions);
                    polylines.add(polyline);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng, 18));

                    if (tempMarker != null) {
                        tempMarker.remove();
                    }
                    tempLatLng = curLatLng;
                    tempMarker = mMap.addMarker(new MarkerOptions().position(tempLatLng).title("Here"));

                    distance = distance + (Math.round((curLocation.distanceTo(location)) * 100.0) / 100.0);
                    tvDistance.setText(String.format("%.2f m", distance));
                    curLocation = location;

                    speed = (Math.round(location.getSpeed() * 100.0) / 100.0) * 3.6;
                    tvSpeed.setText(String.format("%s km/h", speed));

                }
            }
        };

        try {
            managerUpdate.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, listenerUpdate);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }
}