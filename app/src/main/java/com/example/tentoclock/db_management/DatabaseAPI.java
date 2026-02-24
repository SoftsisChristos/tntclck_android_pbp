package com.example.tentoclock.db_management;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tentoclock.class_models.Appointment;
import com.example.tentoclock.class_models.MetrishActive;
import com.example.tentoclock.class_models.MetrishTenta;
import com.example.tentoclock.class_models.Pani;
import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.interfaces.CustomCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseAPI {
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;
    private static DatabaseAPI instance = null;

    // Static method to create a single instance of DatabaseAPI class
    public static synchronized DatabaseAPI getInstance() {
        if (instance == null) {
            instance = new DatabaseAPI();
        }
        return instance;
    }

    // Constructor that creates references to the Firebase realtime database
    private DatabaseAPI() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    // Adds a partner to the database and returns their ID
    public String addPartner(Partner partner) {
        String partnerId = databaseReference.child("partners").push().getKey();
        if (partnerId != null) {
            databaseReference.child("partners").child(partnerId).setValue(partner);
            return partnerId;
        }
        return null;
    }

    // Removes a partner from the database
    public void removePartner(String partnerId) {
        databaseReference.child("partners").child(partnerId).removeValue();
    }

    // Updates a partner in the database
    public void updatePartner(String partnerId, Partner partner) {
        databaseReference.child("partners").child(partnerId).setValue(partner);
    }

    // Retrieves a partner from the database from their ID
    public void getPartner(String partnerId, CustomCallback<Partner> customCallback) {
        databaseReference.child("partners").child(partnerId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ONE PARTNER", "Error getting partner data", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ONE PARTNER", String.valueOf(task.getResult().getValue()));

                    Partner partner = task.getResult().getValue(Partner.class);
                    customCallback.onCallback(partner);
                }
            }
        });
    }

    // Retrieves all partners from the database
    public void getAllPartners(CustomCallback<HashMap<String, Partner>> customCallback) {
        databaseReference.child("partners").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PARTNERS", "Error getting all partners data", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL PARTNERS", String.valueOf(task.getResult().getValue()));

                    HashMap<String, Partner> partners = new HashMap<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Partner partner = snapshot.getValue(Partner.class);
                        partners.put(snapshot.getKey(), partner);
                    }

                    customCallback.onCallback(partners);
                }
            }
        });
    }

    // Searches for partners in the database based on their last name
    public ArrayList<Partner> searchPartners(String lastName, ArrayList<Partner> allPartners) {
        ArrayList<Partner> filteredPartners = new ArrayList<>();
        for (Partner partner : allPartners) {
            if (partner.getLastname().contains(lastName.toUpperCase())) {
                filteredPartners.add(partner);
            }
        }

        return filteredPartners;
    }

    // Searches for partners in the database based on their last name
    public HashMap<String, Partner> searchPartners(String lastName, HashMap<String, Partner> allPartners) {
        HashMap<String, Partner> filteredPartners = new HashMap<>();
        for (String key : allPartners.keySet()) {
            Partner partner = allPartners.get(key);
            if (partner != null && partner.getLastname().contains(lastName.toUpperCase())) {
                filteredPartners.put(key, partner);
            }
        }
        return filteredPartners;
    }


    // Adds a pani to the database and returns its ID (code)
    public String addPani(Pani pani, String pamphlet) {
        databaseReference.child("pania").child(pamphlet).child(pani.getCode()).setValue(pani);
        return pani.getCode();
    }

    // Removes a pani from the database
    public void removePani(String code, String pamphlet) {
        databaseReference.child("pania").child(pamphlet).child(code).removeValue();
    }

    // Updates a pani in the database
    public void updatePani(String oldCode, String oldPamphlet, String newPamphlet, Pani pani) {
        removePani(oldCode, oldPamphlet);
        addPani(pani, newPamphlet);
    }

    // Retrieves a pani from the database from its ID
    public void getPani(String code, String pamphlet, CustomCallback<Pani> customCallback) {
        databaseReference.child("pania").child(pamphlet).child(code).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ONE PANI", "Error getting pani data", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ONE PANI", String.valueOf(task.getResult().getValue()));

                    Pani pani = task.getResult().getValue(Pani.class);
                    customCallback.onCallback(pani);
                }
            }
        });
    }

    // Retrieves all pamphlets from the database
    public void getAllPamphlets(CustomCallback<ArrayList<String>> customCallback) {
        databaseReference.child("pania").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PAMPHLETS", "Error getting all pamphlets", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL PAMPHLETS", String.valueOf(task.getResult().getValue()));

                    ArrayList<String> pamphlets = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String pamphlet = snapshot.getKey();
                        pamphlets.add(pamphlet);
                    }

                    customCallback.onCallback(pamphlets);
                }
            }
        });
    }

    // Retrieves all pania codes from a specific pamphlet from the database (pamphlet -> ArrayList<String>)
    public void getAllPaniaCodeFromPamphlet(String pamphlet, CustomCallback<ArrayList<String>> customCallback) {
        databaseReference.child("pania").child(pamphlet).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PANIA CODES FROM PAMPHLET: " + pamphlet, "Error getting all pania codes from pamphlet", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL PANIA CODES FROM PAMPHLET: " + pamphlet, String.valueOf(task.getResult().getValue()));

                    ArrayList<String> paniaCodes = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String code = snapshot.getKey();
                        paniaCodes.add(code);
                    }

                    customCallback.onCallback(paniaCodes);
                }
            }
        });
    }

    // Retrieves all pania codes from the database (pamphlet -> ArrayList<code>)
    public void getAllPaniaCodes(CustomCallback<HashMap<String, ArrayList<String>>> customCallback) {
        // Getting all pamphlet names
        databaseReference.child("pania").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PANIA CODES", "Error getting all pania codes", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL PANIA CODES", String.valueOf(task.getResult().getValue()));

                    HashMap<String, ArrayList<String>> allPaniaCodes = new HashMap<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String pamphlet = snapshot.getValue(String.class);
                        getAllPaniaCodeFromPamphlet(pamphlet, new CustomCallback<ArrayList<String>>() {
                            @Override
                            public void onCallback(ArrayList<String> paniaCodes) {
                                allPaniaCodes.put(pamphlet, paniaCodes);
                            }
                        });
                    }

                    customCallback.onCallback(allPaniaCodes);
                }
            }
        });
    }

    // Retrieves all pania from a specific pamphlet from the database (pamphlet -> HashMap<String, Pani>)
    public void getAllPaniaFromPamphlet(String pamphlet, CustomCallback<HashMap<String, Pani>> customCallback) {
        databaseReference.child("pania").child(pamphlet).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PANIA FROM PAMPHLET: " + pamphlet, "Error getting all pania data", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL PANIA FROM PAMPHLET: " + pamphlet, String.valueOf(task.getResult().getValue()));

                    HashMap<String, Pani> pania = new HashMap<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String code = snapshot.getKey();
                        Pani pani = snapshot.getValue(Pani.class);
                        pania.put(code, pani);
                    }

                    customCallback.onCallback(pania);
                }
            }
        });
    }

    // Searches for pania on their code
    public HashMap<String, Pani> searchPania(String codePani, HashMap<String, Pani> allPania) {
        HashMap<String, Pani> filteredPania = new HashMap<>();
        for (String code : allPania.keySet()) {
            Pani pani = allPania.get(code);
            if (pani != null && pani.getCode().toUpperCase().contains(codePani.toUpperCase())) {
                filteredPania.put(code, pani);
            }
        }
        return filteredPania;
    }

    // Deprecated methods that may need to be changed
/*
    // Retrieves all pania from the database
    public void getAllPaniaArrayList(CustomCallback<ArrayList<Pani>> customCallback){
        databaseReference.child("pania").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL PANIA (ARRAYLIST)", "Error getting all pania data", task.getException());
                    // TODO: handle error.
                }
                else {
                    Log.d("GETTING ALL PANIA (ARRAYLIST)", String.valueOf(task.getResult().getValue()));

                    ArrayList<Pani> pania = new ArrayList<>();
                    for(DataSnapshot snapshot : task.getResult().getChildren())
                    {
                        Pani pani = snapshot.getValue(Pani.class);
                        pania.add(pani);
                    }

                    customCallback.onCallback(pania);
                }
            }
        });
    }

    // Searches for pania in the database based on their code
    public ArrayList<Pani> searchPania(String code, ArrayList<Pani> allPania) {
        ArrayList<Pani> filteredPania = new ArrayList<>();
        for (Pani pani : allPania) {
            if (pani.getCode().contains(code.toUpperCase())) {
                filteredPania.add(pani);
            }
        }

        return filteredPania;
    }
    // Searches for pania in the database based on their code
    public HashMap<String, Pani> searchPania(String code, HashMap<String, Pani> allPania) {
        HashMap<String, Pani> filteredPania = new HashMap<>();
        for (String key : allPania.keySet()) {
            Pani pani = allPania.get(key);
            if (pani != null && pani.getCode().contains(code.toUpperCase())) {
                filteredPania.put(key, pani);
            }
        }
        return filteredPania;
    }

*/
// Adds a metrisi to the database and returns its ID
    public void addMetrish(MetrishTenta kapoiaMetrish) {
        String metrishID = databaseReference.child("metriseis").push().getKey();
        if (metrishID != null) {
            databaseReference.child("metriseis").child(metrishID).setValue(kapoiaMetrish);
        }
    }

    public void addMetrishActive(MetrishActive kapoiaMetrish) {
        String metrishActiveID = databaseReference.child("metriseisActive").push().getKey();
        if (metrishActiveID != null) {
            databaseReference.child("metriseisActive").child(metrishActiveID).setValue(kapoiaMetrish);
        }
    }

    // βαζει τις active μετρησεις ταξινομημένες κατά id_tentasMeVraxiona στο activity τους
    public void getAllActiveMetriseis(CustomCallback<List<MetrishActive>> cb) {
        databaseReference.child("metriseisActive").orderByChild("id_tentasMeVraxiona").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                // επιστρέφουμε πάντα μια λίστα, έστω άδεια για να αποφύγουμε το null
                cb.onCallback(new ArrayList<>());
                return;
            } else {
                List<MetrishActive> list = new ArrayList<>();
                for (DataSnapshot snap : task.getResult().getChildren()) {
                    MetrishActive m = snap.getValue(MetrishActive.class);
                    m.setFirebaseKey(snap.getKey());
                    list.add(m);
                }
                cb.onCallback(list);
            }
        });
    }

    public void getMetrishTentaByKey(String key, CustomCallback<MetrishTenta> cb) {
        databaseReference.child("metriseis").child(key).get().addOnCompleteListener(t -> {
            if (t.isSuccessful() && t.getResult().exists()) {
                MetrishTenta mt = t.getResult().getValue(MetrishTenta.class);
                mt.setFirebaseKey(key);
                cb.onCallback(mt);
            } else {
                cb.onCallback(null);
            }
        });
    }

    public void updateMetrisi(long id, Map<String, Object> updatedValues, Context context) {

        databaseReference.child("metriseis").orderByChild("id_tentasMeVraxiona").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().updateChildren(updatedValues).addOnSuccessListener(
                                        aVoid -> Toast.makeText(context, "Η εγγραφή ενημερώθηκε με επιτυχία!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(context, "Σφάλμα: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                } else {
                    Toast.makeText(context, "Δε βρέθηκε καταχώρηση με αυτό το id.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Σφάλμα βάσης: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    // Διαγράφει τη μέτρηση με το δοσμένο κλειδί και καλεί το callback με true/false
    public void deleteMeasurement(String key, CustomCallback<Boolean> callback) {

        databaseReference.child("metriseisActive").child(key).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onCallback(true);
            } else {
                callback.onCallback(false);
            }
        });
    }

    public void deleteAnalutikhMetrisi(String key) {
        databaseReference.child("metriseis").child(key).removeValue();
    }

    // Παίρνει atomically τον επόμενο αύξοντα αριθμό από το metriseisCounter
    public void getNextSequentialId(CustomCallback<Long> cb) {
        DatabaseReference counterRef = databaseReference.child("metriseisCounter");
        counterRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Long current = currentData.getValue(Long.class);
                if (current == null) current = 0L;
                long next = current + 1;
                currentData.setValue(next);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError err, boolean committed, @Nullable DataSnapshot snapshot) {
                if (err != null || !committed || snapshot == null) {
                    cb.onCallback(null);
                } else {
                    cb.onCallback(snapshot.getValue(Long.class));
                }
            }
        });
    }


    // Adds an appointment to the database and returns its ID (code)
    public void addAppointment(Appointment kapoioAppointment) {
        String appointmentID = databaseReference.child("appointments").push().getKey();
        if (appointmentID != null) databaseReference.child("appointments").child(appointmentID).setValue(kapoioAppointment);
    }

    // Retrieves all appointments from the database
    public void getAllAppointments(CustomCallback<HashMap<String, Appointment>> customCallback) {
        databaseReference.child("appointments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GETTING ALL APPOINTMENTS", "Error getting all appointments data", task.getException());
                    // TODO: handle error.
                } else {
                    Log.d("GETTING ALL APPOINTMENTS", String.valueOf(task.getResult().getValue()));

                    HashMap<String, Appointment> appointmentsHashMap = new HashMap<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Appointment ma = snapshot.getValue(Appointment.class);
                        appointmentsHashMap.put(snapshot.getKey(), ma);
                    }

                    customCallback.onCallback(appointmentsHashMap);
                }
            }
        });
    }
}
