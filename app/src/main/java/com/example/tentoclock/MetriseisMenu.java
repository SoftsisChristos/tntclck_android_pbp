package com.example.tentoclock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.class_models.MetrishActive;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.metriseis.TentesMeVraxiones;
import com.example.tentoclock.recycler_adapters.MeasurementItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MetriseisMenu extends AppCompatActivity {

    private MeasurementItemAdapter adapter;
    private List<MetrishActive> measurementItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_metriseis_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.measurements_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data list
        measurementItems = new ArrayList<>();

        // Initialize adapter
        adapter = new MeasurementItemAdapter(measurementItems, new MeasurementItemAdapter.ItemActionCallback() {

            MetrishActive item;

            @Override
            public void onEdit(int pos) {

                item = measurementItems.get(pos);
                long id_metrishs = item.getId_tentasMeVraxiona();

                vresToAntistoixoID_apoTisAnalutikesMetriseis(id_metrishs, detailKey -> {
                    if (detailKey != null) {
                        // εδώ έχεις το σωστό detailKey & Ανοίγουμε το TentesMeVraxiones σε edit mode
                        Intent intent = new Intent(MetriseisMenu.this, TentesMeVraxiones.class);
                        intent.putExtra("editMode", true);
                        intent.putExtra("metrishKey", detailKey);
                        startActivity(intent);
                    } else {
                        Log.e("!!4", "Δεν βρέθηκε detail για displayId=" + id_metrishs);
                    }
                });
            }

                @Override public void onDelete ( int pos){

                    item = measurementItems.get(pos);

                    String activeMetrisi_firebaseKey = item.getFirebaseKey();
                    long id_metrishs = item.getId_tentasMeVraxiona();

                    new AlertDialog.Builder(MetriseisMenu.this).setTitle("Διαγραφή").setMessage("Θέλεις σίγουρα να διαγράψεις αυτή τη μέτρηση;").setPositiveButton("Ναι", (d, i) -> {
                        DatabaseAPI.getInstance().deleteMeasurement(activeMetrisi_firebaseKey, success -> {
                            if (success) {
                                vresToAntistoixoID_apoTisAnalutikesMetriseis(id_metrishs, detailKey -> {
                                    if (detailKey != null) {
                                        // εδώ έχεις το σωστό detailKey
                                        DatabaseAPI.getInstance().deleteAnalutikhMetrisi(detailKey);
                                    } else {
                                        Log.e("!!4", "Δεν βρέθηκε detail για displayId=" + id_metrishs);
                                    }
                                });

                                measurementItems.remove(pos);
                                adapter.notifyItemRemoved(pos);
                            }
                        });
                    }).setNegativeButton("Όχι", null).show();
                }
            });
        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

            // Fetch data from Firebase
            fetchActiveMeasurements();
        }

        private void fetchActiveMeasurements () {
            DatabaseAPI.getInstance().getAllActiveMetriseis(measurements -> {
                measurementItems.clear();
                measurementItems.addAll(measurements);
                adapter.notifyDataSetChanged();
            });
        }

        // 1) Φτιάχνεις ένα interface
        public interface DetailKeyCallback {
            void onKeyFound(@Nullable String detailKey);
        }

        // 2) Τροποποιείς τη μέθοδό σου να παίρνει callback
        private void vresToAntistoixoID_apoTisAnalutikesMetriseis (
        long targetID_Tentas, DetailKeyCallback callback){

            DatabaseReference detailsRef = FirebaseDatabase.getInstance().getReference().child("metriseis");

            detailsRef.orderByChild("id_tentasMeVraxiona").equalTo(targetID_Tentas).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String key = null;
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        key = childSnap.getKey();
                        break; // αν θες μόνο το πρώτο match
                    }
                    Log.d("!!3", "Found detail with key=" + key);
                    // καλούμε τον callback _εδώ_
                    callback.onKeyFound(key);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onKeyFound(null);
                }
            });
            // **ΟΧΙ** return εδώ
        }
    }