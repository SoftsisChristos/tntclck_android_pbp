package com.example.tentoclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tentoclock.db_management.DatabaseAPI;

import java.util.ArrayList;
import java.util.List;

public class Metriseis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metriseis);

        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<DataModel> mList = new ArrayList<>();

        //list1
        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("ΤΕΝΤΕΣ ΜΕ ΒΡΑΧΙΟΝΕΣ");
        nestedList1.add("ΤΕΝΤΕΣ ΜΕ ΑΝΤΙΡΙΔΕΣ");
        nestedList1.add("ΤΕΝΤΑ ΚΑΘΕΤΗ");
        nestedList1.add("ΤΕΝΤΑ ΚΑΘΕΤΗ BOX");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Book");
        nestedList2.add("Pen");
        nestedList2.add("Office Chair");
        nestedList2.add("Pencil");
        nestedList2.add("Eraser");
        nestedList2.add("NoteBook");
        nestedList2.add("Map");
        nestedList2.add("Office Table");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Decorates");
        nestedList3.add("Tea Table");
        nestedList3.add("Wall Paint");
        nestedList3.add("Furniture");
        nestedList3.add("Bedsits");
        nestedList3.add("Certain");
        nestedList3.add("Nam-keen and Snacks");
        nestedList3.add("Honey and Spreads");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("ΑΝΤΙΑΝΕΜΙΚΕΣ ΜΕΜΒΡΑΝΕΣ BOX");
        nestedList4.add("ΑΝΕΜΟΘΡΑΥΣΤΕΣ ΠΤΥΣΣΟΜΕΝΟΙ");
        nestedList4.add("ΑΝΤΙΑΝΕΜΙΚΕΣ ΜΕΜΒΡΑΝΕΣ ΚΑΘΕΤΕΣ");
        nestedList4.add("ΑΝΕΜΟΘΡΑΥΣΤΕΣ ΣΤΑΘΕΡΟΙ");


        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("Jams and Honey");
        nestedList5.add("Pickles and Chutneys");
        nestedList5.add("Ready-made Meals");
        nestedList5.add("Chyawanprash and Health Foods");
        nestedList5.add("Pasta and Soups");
        nestedList5.add("Sauces and Ketchup");
        nestedList5.add("Nam-keen and Snacks");
        nestedList5.add("Honey and Spreads");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("ΑΛΟΥΜΙΝΙΟΥ ΠΤΥΣΣΟΜΕΝΕΣ");
        nestedList6.add("ΑΛΟΥΜΙΝΙΟΥ ΚΡΕΜΑΣΤΕΣ");
        nestedList6.add("ΑΛΟΥΜΙΝΙΟΥ ΒΙΟΚΛΙΜΑΤΙΚΗ");
        nestedList6.add("ΠΛΕΚΤΗ");



        List<String> nestedList7 = new ArrayList<>();
        nestedList7.add("Decorates");
        nestedList7.add("Tea Table");
        nestedList7.add("Wall Paint");
        nestedList7.add("Furniture");
        nestedList7.add("Bedsits");
        nestedList7.add("Certain");
        nestedList7.add("Nam-keen and Snacks");
        nestedList7.add("Honey and Spreads");

        List<String> nestedList8 = new ArrayList<>();
        nestedList7.add("ΣΙΤΑ ΑΝΟΙΓΟΜΕΝΗ ΜΕ ΕΡΠΙΣΤΡΙΑ");
        nestedList7.add("ΣΙΤΑ ΑΝΟΙΓΟΜΕΝΗ ΜΕ ΟΔΗΓΟ");
        nestedList7.add("ΣΙΤΑ ΣΥΡΟΜΕΝΗ");
        nestedList7.add("ΣΙΤΑ ΠΑΡΑΘΥΡΟΥ");
        nestedList7.add("ΣΙΤΑ ΠΟΡΤΑ");


        mList.add(new DataModel(nestedList1, "ΤΕΝΤΕΣ"));
        mList.add(new DataModel(nestedList2, "ΚΑΠΟΤΙΝΕΣ"));
        mList.add(new DataModel(nestedList3, "ΡΟΛΟΚΟΥΡΤΙΝΕΣ"));
        mList.add(new DataModel(nestedList4, "ΠΡΟΣΤΑΣΙΑ ΑΠΟ ΑΝΕΜΟ"));
        mList.add(new DataModel(nestedList5, "ΓΚΙΛΟΤΙΝΕΣ"));
        mList.add(new DataModel(nestedList6, "ΠΕΡΓΚΟΛΕΣ"));
        mList.add(new DataModel(nestedList7, "ΜΕΤΑΛΛΙΚΕΣ ΚΑΤΑΣΚΕΥΕΣ"));
        mList.add(new DataModel(nestedList8, "ΕΝΤΟΜΟΑΠΩΘΗΤΙΚΕΣ ΣΙΤΕΣ"));

        ItemAdapter adapter = new ItemAdapter(this, mList);
        recyclerView.setAdapter(adapter);
    }
}