package com.example.tentoclock.metriseis;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tentoclock.MetrishPreview;
import com.example.tentoclock.R;
import com.example.tentoclock.class_models.MetrishTenta;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.interfaces.CustomCallback;
import com.example.tentoclock.interfaces.FragmentCloseListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TentesMeVraxiones extends AppCompatActivity implements FragmentCloseListener {

    DatabaseAPI databaseAPI;

    View view30, diaxoristiko10;

    final double vraxionesScaling = 0.25;

    boolean diakoptisFlag, tilexeiristhrioFlag, manivelaFlag = false;
    boolean mhk, yps, f20, xeir, extras, NMs, vrax, kodPan, smrt, huft, colors = true;

    double balconyLengthNum, balconyHeightNum, axisLength, paniLength, antivaroLength, vraxionesLength, katevasmaLength;
    double motorOffset = 0.06;
    double manualOffset = 0.10;
    double selectedOffset = motorOffset; // Αρχική τιμή, ας πούμε με μοτέρ

    EditText balcony_length, balcony_height;

    TextView tv1, tv2, tv3, tv4, tv5, tv_labelManivela, tv_moteria;
    TextView tv_fabricName, tv_fabricSeries;

    RadioGroup rg_mechanism, rg_EidosVraxiona, rg_merosVraxiona, rg_smartHome, rg_hufta;
    RadioButton rb_auto, rb_manual, rb_smartPalio, rb_smartKainourgio, rb_f48, rb_f70;
    RadioButton rb_isios, rb_loksos, rb_VareosTypou;
    RadioButton rb_VraxMesa, rb_VraxEksw;

    ChipGroup chipG_eidosAksona, chipG_automaticOptions, chipG_Colors, chipG_moteria;
    Chip chip48, chip60, chip70, chip85;
    Chip chip_withSwitch, chip_withThlexeiristirio, chip_withHandle;
    Chip chip_moter30, chip_moter50, chip_moter80, chip_moter100, chip_moter120;

    Spinner sp_crankLength, sp_mikosVraxiona, sp_antivaro, sp_glossa, sp_pamphlet;

    AutoCompleteTextView act_fabricCodeInput;
    CardView cv_fabricDetailCard;

    CheckBox chBox_diploKouzineto, chBox_oneInchKouz, chBox_vaseisToixou, chBox_vaseisMakries, chBox_polikanalo, chBox_smartControl, chBox_hufta;

    LinearLayout extraServicesHeader, extraServicesContainer;
    ImageView arrowIcon;

    Button btn_prosthikh;

    String chipText_eidosAksona, selectedCode;
    String chip_eidosAksona;

    // string μεταβλητες για την δημιουργια της συνοψης πριν την τελικη προσθηκη/υποβολη πες το οπως θες
    String mhkos, ypsos, aksonas, pani, antivaro, vraxiones_s, katevasma_s = "";
    String mhxanismos, autoExtras, autoExtras_s, manivela_s, motorPower_s, antivaro_s, glossa_s, vraxionasMhkos_s, eidosVraxiona_s, merosVraxiona_s;
    String pamphlet_s, kodikosPaniou_s, onomaPaniou_s, seiraPaniou_s, perigrafhSeiras_s, epilegmenoXroma_s;
    String diploKouzineto_s, oneInchKouz_s, vaseisToixou_s, vaseisMakries_s, polikanalo_s, smartControl_s, houfta_s;
    String ableToSubmitt;
    List<String> metrhtes_s;
    String local_autoMhxanismos, filadio;
    int diploKouzineto, oneInchKouzineto, vaseisToixou, vaseisMakries, polikanalo, smartControl, houfta;
    int[] intArray2;

    ArrayList<String> lista_meKodikousPaniwn;
    final String[] pamphlet = {"Calbari"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentes_me_vraxiones);

        // Για τις διαστάσεις
        balcony_length = findViewById(R.id.balcony_length);
        balcony_height = findViewById(R.id.height_from_ceiling);
        tv1 = findViewById(R.id.axonas_length);
        tv2 = findViewById(R.id.pani_length);
        tv3 = findViewById(R.id.vraxiones);
        tv4 = findViewById(R.id.antivaro_length);
        tv5 = findViewById(R.id.katevasma);

        // Για την διάμετρο άξονα
        chipG_eidosAksona = findViewById(R.id.axis_type_chipgroup);
        chip48 = findViewById(R.id.chip_48);
        chip60 = findViewById(R.id.chip_60);
        chip70 = findViewById(R.id.chip_70);
        chip85 = findViewById(R.id.chip_85);

        // Για τον χειρισμό
        rg_mechanism = findViewById(R.id.radioGroupMechanism);
        rb_auto = findViewById(R.id.radioButtonMotor);
        rb_manual = findViewById(R.id.radioButtonManual);

        // Για τις επιλογές του μοτέρ πχ με διακοπτη, με τηλεχειριστηριο, με τη μανιβελα
        chipG_automaticOptions = findViewById(R.id.chipGroupAutomatic);
        chip_withSwitch = findViewById(R.id.chipSwitch);
        chip_withThlexeiristirio = findViewById(R.id.chipTilexeiristhrio);
        chip_withHandle = findViewById(R.id.chipManivela);

        // Για το μήκος μανιβέλας
        tv_labelManivela = findViewById(R.id.label_crank_length);
        sp_crankLength = findViewById(R.id.crank_length_spinner);

        // Για το μοτέρ
        tv_moteria = findViewById(R.id.tv_moter);
        chipG_moteria = findViewById(R.id.chipgroup_moter);
        chip_moter30 = findViewById(R.id.chip_moter30);
        chip_moter50 = findViewById(R.id.chip_moter50);
        chip_moter80 = findViewById(R.id.chip_moter80);
        chip_moter100 = findViewById(R.id.chip_moter100);
        chip_moter120 = findViewById(R.id.chip_moter120);

        // Για το είδος του αντίβαρου
        sp_antivaro = findViewById(R.id.spinner_antivara);

        // Για τη γλώσσα
        sp_glossa = findViewById(R.id.spinner_glosses);

        // Για τον βραχίονα
        sp_mikosVraxiona = findViewById(R.id.spinner_vraxiones);
        rg_EidosVraxiona = findViewById(R.id.rg_eidosVraxiona);
        rb_isios = findViewById(R.id.rb_isios);
        rb_loksos = findViewById(R.id.rb_loksos);
        rb_VareosTypou = findViewById(R.id.rb_vraxionasVaris);
        rg_merosVraxiona = findViewById(R.id.radioGroup_merosVraxiona);
        rb_VraxMesa = findViewById(R.id.rb_mesa);
        rb_VraxEksw = findViewById(R.id.rb_eksw);

        // Για τα πανιά
        sp_pamphlet = findViewById(R.id.pamphlets);
        act_fabricCodeInput = findViewById(R.id.fabricCodeInput);
        cv_fabricDetailCard = findViewById(R.id.fabricDetailCard);
        tv_fabricName = findViewById(R.id.fabricName);
        tv_fabricSeries = findViewById(R.id.fabricBrand);
        chipG_Colors = findViewById(R.id.chipGroupColors);

        diaxoristiko10 = findViewById(R.id.diaxoristiko10);
        rb_smartPalio = findViewById(R.id.radioButton_smartPalio);
        rb_smartKainourgio = findViewById(R.id.radioButton_smartKainourgio);
        rb_f48 = findViewById(R.id.radioButton_f48);
        rb_f70 = findViewById(R.id.radioButton_f70);

        // ta checkboxes stis extra ypiresies
        chBox_diploKouzineto = findViewById(R.id.checkbox_diploKouzineto);
        chBox_oneInchKouz = findViewById(R.id.checkbox_oneInchKouzineto);
        chBox_vaseisToixou = findViewById(R.id.checkbox_vaseisToixou);
        chBox_vaseisMakries = findViewById(R.id.checkbox_vaseisMakries);
        chBox_polikanalo = findViewById(R.id.checkbox_poliKanalo);
        chBox_hufta = findViewById(R.id.hufta);
        rg_hufta = findViewById(R.id.radioGroup_hufta);
        chBox_smartControl = findViewById(R.id.checkBox_smart);
        rg_smartHome = findViewById(R.id.rg_smartHome);

        // για την υλοποιηση του extra services fragment
        extraServicesHeader = findViewById(R.id.extra_services_header);
        extraServicesContainer = findViewById(R.id.extraYphresies_linearLayout);
        arrowIcon = findViewById(R.id.arrow_icon);

        btn_prosthikh = findViewById(R.id.button_prosthiki);

        // Για το back-end
        databaseAPI = DatabaseAPI.getInstance();

        // Για να χάνεται το focus όταν κανεις touch αλλού
        View rootView = findViewById(R.id.rootLayout);
        rootView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    // Hide the keyboard as well
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
            }
            // Call performClick for accessibility
            v.performClick();
            return false;
        });

        boolean isEdit = getIntent().getBooleanExtra("editMode", false);
        final int[] id_tentas_edited = new int[1];
        if (isEdit) {
            String key = getIntent().getStringExtra("metrishKey");
            DatabaseAPI.getInstance().getMetrishTentaByKey(key, mt -> {
                if (mt != null) {
                    id_tentas_edited[0] = mt.getId_tentasMeVraxiona().intValue();
                    populateFields(mt);
                    btn_prosthikh.setText("Ενημέρωση");
                    handler_ylopoihshKoumpiouProsthikh("update", id_tentas_edited[0]);
                } else {
                    Toast.makeText(this, "Σφάλμα φόρτωσης", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            btn_prosthikh.setText("Προσθήκη");
        }


        // Για να παρεις τις διαστασεις απο τα πεδια μηκος και υψος
        handler_balconyLength_OnFocusChanged();
        handler_balconyLength_OnTextChanged();
        handler_balconyHeight_OnFocusChanged();
        handler_balconyHeight_OnTextChanged();

        // διαφοροι χειρισμοι που πρεπει να γινουν και για να ειναι πιο συμμαζεμενα τα αφησα ετσι
        handler_eidosXeirismou();
        setCorrectVraxionaLength();
        handler_gemiseToCardviewMeTaInfoTouPaniou();
        handler_ylopoihshExtraServices();

        // εδω προσθετω τους μετρητες με τα "τεμαχια" υπηρεσιων
        inflateThnYphresia("Χούφτα", extraServicesContainer, "yes");
        inflateThnYphresia("Βάσεις Για Μπράτσα", extraServicesContainer, "no");
        inflateThnYphresia("Μαλακός Βραχίονας", extraServicesContainer, "no");
        inflateThnYphresia("Αισθητήρας Ήλιου Αέρα (Ανεμούριος)", extraServicesContainer, "no");
        inflateThnYphresia("Αισθητήρας Αέρα (Αντικραδασμικό/Vibrator)", extraServicesContainer, "no");
        inflateThnYphresia("Ρυθμιζόμενη Λάμα", extraServicesContainer, "no");
        inflateThnYphresia("Ηλιακό Πάνελ", extraServicesContainer, "no");
        inflateThnYphresia("Έξτρα Τηλεκοντρόλ", extraServicesContainer, "no");
        inflateThnYphresia("Μετατροπή", extraServicesContainer, "no");

        if(!isEdit)
            handler_ylopoihshKoumpiouProsthikh("initial add", null);
    }

    private void pareToEpilegmenoPragmaApoToSpinner(String value_fromFB, int stringArrayXML, int type, Spinner spinner_id) {

        String toFind = value_fromFB;

        // για τη μανιβέλα
        if (type == 0) {
            spinner_id.setVisibility(View.VISIBLE);
            // κρατάω μόνο το 2.40 (ψηφία + dot)
            String trimmed = value_fromFB.replaceAll("[^\\d.]+", "");

            // συνθέτω πάλι με το μ για να ταιριάξει στο array
            toFind = trimmed + "μ";
        }

        // ο adapter με τα μήκη
        String[] options = getResources().getStringArray(stringArrayXML);

        // βρίσκω θέση στο array
        int pos = Arrays.asList(options).indexOf(toFind);
        if (pos >= 0) {
            spinner_id.setSelection(pos);
        }
    }

    private void populateFields(MetrishTenta mt) {

        balcony_length.setText(String.valueOf(mt.getMhkos()));
        balcony_height.setText(String.valueOf(mt.getIpsos()));

        switch (mt.getDiametrosAksona()) {
            case "Φ48":
                chip48.setChecked(true);
                break;
            case "Φ60":
                chip60.setChecked(true);
                break;
            case "Φ70":
                chip70.setChecked(true);
                break;
            case "Φ85":
                chip85.setChecked(true);
                break;
        }

        String manivela_fromFB = mt.getManivela();
        if (mt.getMhxanismos().equals("Μοτερ")) {
            rb_auto.setChecked(true);
            if (mt.getAutoMhxanismos().equals("Με Τηλεχειριστήριο"))
                chip_withThlexeiristirio.setChecked(true);
            else
                chip_withSwitch.setChecked(true);

            if (manivela_fromFB != null && !manivela_fromFB.isEmpty()) {
                chip_withHandle.setSelected(true);
                pareToEpilegmenoPragmaApoToSpinner(manivela_fromFB, R.array.mhkh_manivelas, 0, sp_crankLength);
            } else {
                // manivela==null ή empty → disable flow
                chip_withHandle.setSelected(false);
                sp_crankLength.setVisibility(View.GONE);
                // (εδώ δεν καλείς τίποτα που να κάνει NPE)
            }

            chipG_moteria.setVisibility(View.VISIBLE);
            switch (mt.getMoter()) {
                case "Ισχύς Μοτέρ: 30nm":
                    chip_moter30.setChecked(true);
                    break;
                case "Ισχύς Μοτέρ: 50nm":
                    chip_moter50.setChecked(true);
                    break;
                case "Ισχύς Μοτέρ: 80nm":
                    chip_moter80.setChecked(true);
                    break;
                case "Ισχύς Μοτέρ: 100nm":
                    chip_moter100.setChecked(true);
                    break;
                case "Ισχύς Μοτέρ: 120nm":
                    chip_moter120.setChecked(true);
                    break;
            }
        } else {
            rb_manual.setChecked(true);
            pareToEpilegmenoPragmaApoToSpinner(manivela_fromFB, R.array.mhkh_manivelas, 0, sp_crankLength);
            chipG_moteria.setVisibility(View.GONE);
        }

        String antivaro_fromFB = mt.getAntivaro();
        pareToEpilegmenoPragmaApoToSpinner(antivaro_fromFB, R.array.eidh_antivarwn, 1, sp_antivaro);

        String glossa_fromFB = mt.getGlossa();
        pareToEpilegmenoPragmaApoToSpinner(glossa_fromFB, R.array.glosses, 2, sp_glossa);

        String mikosVraxiona_fromFB = mt.getMhkosVraxiona();
        pareToEpilegmenoPragmaApoToSpinner(mikosVraxiona_fromFB, R.array.vraxiones, 3, sp_mikosVraxiona);

        String eidosVraxiona_fromFB = mt.getEidosVraxiona();
        switch (eidosVraxiona_fromFB) {
            case "Είδος Βραχίονα: ΙΣΙΟΣ":
                rb_isios.setChecked(true);
                break;
            case "Είδος Βραχίονα: ΛΟΞΟΣ":
                rb_loksos.setChecked(true);
                break;
            case "Είδος Βραχίονα: ΒΑΡΕΩΣ ΤΥΠΟΥ":
                rb_VareosTypou.setChecked(true);
                break;
        }

        String merosVraxiona_fromFB = mt.getMerosVraxiona();
        switch (merosVraxiona_fromFB) {
            case "Τοποθέτηση Βραχίονα από: ΕΞΩ":
                rb_VraxEksw.setChecked(true);
                break;
            case "Τοποθέτηση Βραχίονα από: ΜΕΣΑ":
                rb_VraxMesa.setChecked(true);
                break;
        }

        // ένα καλό trick είναι να κάνεις μισό κλικ «post» για να είναι έτοιμο το view
        // αλλιώς καλείς το setSelection(...) πριν φορτώσει ο adapter του spinner, οπότε αγνοείται.
        String filadio_fromFB = mt.getFiladio();
        if (filadio_fromFB.equals("Calbari"))
            sp_pamphlet.post(() -> sp_pamphlet.setSelection(0));
        else
            sp_pamphlet.post(() -> sp_pamphlet.setSelection(1));

        String kodikosPaniou_fromFB = mt.getKodikosPaniou();
        String selectedColor = mt.getXromaPaniou();
        act_fabricCodeInput.setText(kodikosPaniou_fromFB);
        act_fabricCodeInput.post(() -> {
            selectPani(kodikosPaniou_fromFB, filadio_fromFB, "yes", selectedColor);
            cv_fabricDetailCard.setVisibility(View.VISIBLE);
        });

        int diploKouzineto_fromFB = mt.getDiploKouzineto();
        if (diploKouzineto_fromFB == 1)
            chBox_diploKouzineto.setChecked(true);

        int oneInchKouzineto_fromFB = mt.getOneInchKouzineto();
        if (oneInchKouzineto_fromFB == 1)
            chBox_oneInchKouz.setChecked(true);

        int vaseisToixou_fromFB = mt.getVaseisToixou();
        if (vaseisToixou_fromFB == 1)
            chBox_vaseisToixou.setChecked(true);

        int vaseisMakries_fromFB = mt.getVaseisMakries();
        if (vaseisMakries_fromFB == 1)
            chBox_vaseisMakries.setChecked(true);

        int polikanalo_fromFB = mt.getPolikanalo();
        if (polikanalo_fromFB == 1)
            chBox_polikanalo.setChecked(true);

        int houftaType_fromFB = mt.getHoufta();
        if (houftaType_fromFB == 2) {
            rb_f70.setEnabled(true);
            rb_f48.setEnabled(true);
            rb_f70.setChecked(true);
            chBox_hufta.setChecked(true);
        } else if (houftaType_fromFB == 1) {
            rb_f70.setEnabled(true);
            rb_f48.setEnabled(true);
            rb_f48.setChecked(true);
            chBox_hufta.setChecked(true);
        } else if (houftaType_fromFB == 0) {
            rg_hufta.clearCheck();
            rb_f70.setEnabled(false);
            rb_f48.setEnabled(false);
            chBox_hufta.setChecked(false);
        }

        int smartControlType_fromFB = mt.getSmartControl();
        if (smartControlType_fromFB == 2) {
            rb_smartKainourgio.setEnabled(true);
            rb_smartPalio.setEnabled(true);
            rb_smartKainourgio.setChecked(true);
            chBox_smartControl.setChecked(true);
        } else if (smartControlType_fromFB == 1) {
            rb_smartKainourgio.setEnabled(true);
            rb_smartPalio.setEnabled(true);
            rb_smartPalio.setChecked(true);
            chBox_smartControl.setChecked(true);
        } else if (smartControlType_fromFB == 0) {
            rg_smartHome.clearCheck();
            rb_smartKainourgio.setEnabled(false);
            rb_smartPalio.setEnabled(false);
            chBox_smartControl.setChecked(false);
        }

        int vaseisGiaBratsa_fromFB = mt.getVaseis();
        View serviceLayout_vaseis = extraServicesContainer.getChildAt(8);
        TextView quantityView_vaseis = serviceLayout_vaseis.findViewById(R.id.service_quantity);
        quantityView_vaseis.setText(String.valueOf(vaseisGiaBratsa_fromFB));

        int malakoiVraxiones_fromFB = mt.getMalakoi();
        View serviceLayout_malakoiVraxiones = extraServicesContainer.getChildAt(9);
        TextView quantityView_malakoiVraxiones = serviceLayout_malakoiVraxiones.findViewById(R.id.service_quantity);
        quantityView_malakoiVraxiones.setText(String.valueOf(malakoiVraxiones_fromFB));

        int aisthitHliou_fromFB = mt.getAisthitHliou();
        View serviceLayout_aisthitHliou = extraServicesContainer.getChildAt(10);
        TextView quantityView_aisthitHliou = serviceLayout_aisthitHliou.findViewById(R.id.service_quantity);
        quantityView_aisthitHliou.setText(String.valueOf(aisthitHliou_fromFB));

        int aisthitAera_fromFB = mt.getAisthitAera();
        View serviceLayout_aisthitAera = extraServicesContainer.getChildAt(11);
        TextView quantityView_aisthitAera = serviceLayout_aisthitAera.findViewById(R.id.service_quantity);
        quantityView_aisthitAera.setText(String.valueOf(aisthitAera_fromFB));

        int rithmLama_fromFB = mt.getLames();
        View serviceLayout_rithmLama = extraServicesContainer.getChildAt(12);
        TextView quantityView_rithmLama = serviceLayout_rithmLama.findViewById(R.id.service_quantity);
        quantityView_rithmLama.setText(String.valueOf(rithmLama_fromFB));

        int hliakoPanel_fromFB = mt.getPanels();
        View serviceLayout_hliakoPanel = extraServicesContainer.getChildAt(13);
        TextView quantityView_hliakoPanel = serviceLayout_hliakoPanel.findViewById(R.id.service_quantity);
        quantityView_hliakoPanel.setText(String.valueOf(hliakoPanel_fromFB));

        int extraTilek_fromFB = mt.getThlekontrols();
        View serviceLayout_extraTilek = extraServicesContainer.getChildAt(14);
        TextView quantityView_extraTilek = serviceLayout_extraTilek.findViewById(R.id.service_quantity);
        quantityView_extraTilek.setText(String.valueOf(extraTilek_fromFB));

        int metatropes_fromFB = mt.getMetatropes();
        View serviceLayout_metatropes = extraServicesContainer.getChildAt(15);
        TextView quantityView_metatropes = serviceLayout_metatropes.findViewById(R.id.service_quantity);
        quantityView_metatropes.setText(String.valueOf(metatropes_fromFB));

    }

    private void handler_ylopoihshKoumpiouProsthikh(String troposProsthikis_toDB, Integer id_tentas) {

        btn_prosthikh.setOnClickListener(v -> {

            view30 = TentesMeVraxiones.this.findViewById(R.id.rootLayout2);
            setViewAndChildrenEnabled(view30, false);

            //ΜΗΚΟΣ
            mhkos = balcony_length.getText().toString();
            aksonas = String.format(Locale.US, "%.2f", axisLength);
            pani = String.format(Locale.US, "%.2f", paniLength);
            antivaro = String.format(Locale.US, "%.2f", antivaroLength);

            //ΥΨΟΣ
            ypsos = balcony_height.getText().toString();
            vraxiones_s = String.format(Locale.US, "%.2f", vraxionesLength);
            katevasma_s = String.format(Locale.US, "%.2f", katevasmaLength);

            //ΑΞΟΝΑΣ
            chip_eidosAksona = TentesMeVraxiones.this.getSelected_ChipAksona();

            //ΧΕΙΡΙΣΜΟΣ
            if (rb_auto.isChecked()) {
                autoExtras_s = TentesMeVraxiones.this.getSelected_MotorExtras();

                if (chip_withHandle.isChecked())
                    manivela_s = "Μήκος Μανιβέλας: " + sp_crankLength.getSelectedItem().toString();
                else
                    manivela_s = null;

                if (chip_withSwitch.isChecked())
                    local_autoMhxanismos = "Με Διακόπτη";
                else if (chip_withThlexeiristirio.isChecked())
                    local_autoMhxanismos = "Με Τηλεχειριστήριο";

                motorPower_s = TentesMeVraxiones.this.getSelected_MotorPower();
            } else {
                autoExtras_s = " ";
                manivela_s = "Μήκος Μανιβέλας: " + sp_crankLength.getSelectedItem().toString();
                motorPower_s = " ";
            }

            //ΑΝΤΙΒΑΡΟ, ΓΛΩΣΣΑ, ΒΡΑΧΙΟΝΑΣ_ΜΗΚΟΣ
            antivaro_s = sp_antivaro.getSelectedItem().toString();
            glossa_s = sp_glossa.getSelectedItem().toString();
            vraxionasMhkos_s = sp_mikosVraxiona.getSelectedItem().toString();

            // ΕΙΔΟΣ & ΜΕΡΟΣ ΒΡΑΧΙΟΝΑ
            eidosVraxiona_s = TentesMeVraxiones.this.getSelected_EidosVraxiona();
            merosVraxiona_s = TentesMeVraxiones.this.getSelected_MerosVraxiona();

            // ΠΑΝΙΑ
            // τα υλοποιω αυτα μεσα στην μεθοδο selectPani()
            filadio = sp_pamphlet.getSelectedItem().toString();
            int selectedChipId = chipG_Colors.getCheckedChipId();
            Chip selectedChip = findViewById(selectedChipId);
            epilegmenoXroma_s = selectedChip.getText().toString();

            // ΕΞΤΡΑ ΥΠΗΡΕΣΙΕΣ - CHECKBOXES
            diploKouzineto_s = chBox_diploKouzineto.isChecked() ? chBox_diploKouzineto.getText().toString() : "";
            oneInchKouz_s = chBox_oneInchKouz.isChecked() ? chBox_oneInchKouz.getText().toString() : "";
            vaseisToixou_s = chBox_vaseisToixou.isChecked() ? chBox_vaseisToixou.getText().toString() : "";
            vaseisMakries_s = chBox_vaseisMakries.isChecked() ? chBox_vaseisMakries.getText().toString() : "";
            polikanalo_s = chBox_polikanalo.isChecked() ? chBox_polikanalo.getText().toString() : "";
            smartControl_s = chBox_smartControl.isChecked()
                    ? (rg_smartHome.getCheckedRadioButtonId() == R.id.radioButton_smartPalio
                    ? "Smart Control: Παλιο"
                    : "Smart Control: Καινουριο")
                    : "";

            houfta_s = chBox_hufta.isChecked()
                    ? (rg_hufta.getCheckedRadioButtonId() == R.id.radioButton_f48
                    ? "Χούφτα: F48"
                    : "Χούφτα: F70")
                    : "";
            des_poies_extraYpiresies_einai_epilegmenes();


            // ΕΞΤΡΑ ΥΠΗΡΕΣΙΕΣ - COUNTERS
            metrhtes_s = TentesMeVraxiones.this.getSelected_timesApoMetrhtes();


            // Δημιουργούμε ένα κείμενο σύνοψης με τις επιλογές του χρήστη
            String summaryText = TentesMeVraxiones.this.generateSummary(mhkos, aksonas, pani, antivaro, ypsos, vraxiones_s, katevasma_s,
                    chip_eidosAksona, mhxanismos, autoExtras_s, manivela_s, motorPower_s, antivaro_s, glossa_s, vraxionasMhkos_s, eidosVraxiona_s, merosVraxiona_s,
                    pamphlet_s, kodikosPaniou_s, onomaPaniou_s, seiraPaniou_s, perigrafhSeiras_s, epilegmenoXroma_s,
                    diploKouzineto_s, oneInchKouz_s, vaseisToixou_s, vaseisMakries_s, polikanalo_s, smartControl_s, houfta_s, metrhtes_s);

            mhk = !balcony_length.getText().toString().trim().isEmpty();
            yps = !balcony_height.getText().toString().trim().isEmpty();
            f20 = !chipG_eidosAksona.getCheckedChipIds().isEmpty();
            xeir = !(rg_mechanism.getCheckedRadioButtonId() == -1);
            if (rb_auto.isChecked())
                extras = !chipG_automaticOptions.getCheckedChipIds().isEmpty();
            else
                extras = true;
            NMs = !chipG_moteria.getCheckedChipIds().isEmpty();
            vrax = !(rg_EidosVraxiona.getCheckedRadioButtonId() == -1);
            kodPan = !act_fabricCodeInput.getText().toString().trim().isEmpty();
            colors = !chipG_Colors.getCheckedChipIds().isEmpty();
            if (chBox_smartControl.isChecked())
                smrt = !(rg_smartHome.getCheckedRadioButtonId() == -1);
            else
                smrt = true;
            if (chBox_hufta.isChecked())
                huft = !(rg_hufta.getCheckedRadioButtonId() == -1);
            else {
                huft = true;
            }


            if (mhk && yps && f20 && xeir && extras && NMs && vrax && kodPan && smrt && huft && colors)
                ableToSubmitt = "true";
            else {
                ableToSubmitt = "false";
                Toast.makeText(TentesMeVraxiones.this, "Κάτι Δεν Έχεις Συμπληρώσει", Toast.LENGTH_SHORT).show();
            }

            Log.d("mhk", "mhk: " + mhk);
            Log.d("yps", "yps: " + yps);
            Log.d("f20", "f20: " + f20);
            Log.d("rb_auto.isChecked()", "rb_auto.isChecked(): " + rb_auto.isChecked());
            Log.d("ext", "ext: " + extras);
            Log.d("NMs", "NMs: " + NMs);
            Log.d("vrax", "vrax: " + vrax);
            Log.d("kodPan", "kodPan: " + kodPan);
            Log.d("smrt", "smrt: " + smrt);
            Log.d("huft", "huft: " + huft);
            Log.d("colors", "colors: " + colors);

            String[] stringArray = new String[]{mhkos, ypsos, chip_eidosAksona, mhxanismos, manivela_s, motorPower_s, local_autoMhxanismos, antivaro_s,
                    glossa_s, vraxionasMhkos_s, eidosVraxiona_s, merosVraxiona_s, filadio, kodikosPaniou_s, epilegmenoXroma_s};
            int[] intArray = new int[]{diploKouzineto, oneInchKouzineto, vaseisToixou, vaseisMakries, polikanalo, smartControl, houfta};

            // Δημιουργία του Fragment και προσθήκη του στο Activity
            MetrishPreview fragmentSummary = new MetrishPreview();

            // Μεταφέρουμε τα δεδομένα στη σύνοψη
            Bundle args = new Bundle();
            args.putString("summary_text", summaryText);
            args.putString("ableToSubmit", ableToSubmitt);
            args.putStringArray("key_string_array", stringArray);
            args.putIntArray("key_int_array", intArray);
            args.putIntArray("key_int_array2", intArray2);
            args.putString("troposProsthikis", troposProsthikis_toDB);
            args.putInt("id_tentas", id_tentas);
            fragmentSummary.setArguments(args);
            FrameLayout frame = TentesMeVraxiones.this.findViewById(R.id.fragment_container);
            frame.setVisibility(View.VISIBLE);

            // Προσθήκη του Fragment
            TentesMeVraxiones.this.getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragmentSummary)
                    .addToBackStack(null) // Επιτρέπει επιστροφή με το κουμπί "Πίσω"
                    .commit();
        });
    }

    private String generateSummary(String mhkos, String aksonas, String pani, String antivaro, String ypsos, String vraxiones_s, String katevasma_s,
                                   String chip_eidosAksona, String xeirismos, String autoExtras_ss, String manivela_s, String motorPower_ss, String antivaro_ss,
                                   String glossa_ss, String vraxionasMhkos_ss, String eidosVraxiona_ss, String merosVraxiona_ss,
                                   String pamphlet_ss, String kodikosPaniou_ss, String onomaPaniou_ss, String seiraPaniou_ss, String perigrafhSeiras_ss, String epilegmenoXroma_ss,
                                   String diploKouzineto_ss, String oneInchKouz_ss, String vaseisToixou_ss, String vaseisMakries_ss, String polikanalo_ss,
                                   String smartControl_ss, String houfta_ss,
                                   List<String> metrhtes_ss) {

        StringBuilder summary = new StringBuilder();

        summary.append("\n----ΑΞΟΝΑΣ----\n");
        summary.append("Διάμετρος Άξονα: ").append(chip_eidosAksona).append("\n");
        summary.append("Μήκος Άξονα: ").append(aksonas).append("μ \n");

        summary.append("\n----ΧΕΙΡΙΣΜΟΣ----\n");
        summary.append("Είδος Μηχ: ").append(xeirismos).append(" ");
        summary.append(autoExtras_ss);
        if (manivela_s != null)
            summary.append(manivela_s).append(" \n");
        summary.append(motorPower_ss).append(" \n");

        summary.append("\n----ΠΑΝΙ----\n");
        //summary.append("Μήκος Μπαλκονιού: ").append(mhkos).append("μ \n");
        summary.append("Μήκος Πανιού: ").append(pani).append("μ \n");
        //summary.append("Ύψος από Ταβάνι: ").append(ypsos).append("μ \n");
        //summary.append("Βραχίονες: ").append(vraxiones_s).append("μ \n");
        summary.append("Κατέβασμα Πανιού: ").append(katevasma_s).append("μ \n");

        summary.append("\n----ΠΑΝΙΑ----\n");
        summary.append("Προμηθευτής: ").append(pamphlet_ss).append(" \n");
        summary.append("Κωδικός Πανιού: ").append(kodikosPaniou_ss).append(" \n");
        summary.append("Όνομα Πανιού: ").append(onomaPaniou_ss).append(" \n");
        summary.append("Σειρά Πανιού: ").append(seiraPaniou_ss).append(" \n");
        summary.append("Περιγραφή Σειράς: ").append(perigrafhSeiras_ss).append(" \n");
        summary.append("Επιλεγμένο Χρώμα: ").append(epilegmenoXroma_ss).append(" \n");

        summary.append("\n----ΓΛΩΣΣΑ----\n");
        summary.append("Μορφή Γλώσσας: ").append(glossa_ss).append(" \n");

        summary.append("\n----ΒΡΑΧΙΟΝΕΣ----\n");
        summary.append("Μήκος Βραχίονα: ").append(vraxionasMhkos_ss).append(" \n");
        summary.append(eidosVraxiona_ss).append(" \n");
        summary.append(merosVraxiona_ss).append(" \n");

        summary.append("\n----ΑΝΤΙΒΑΡΟ----\n");
        summary.append("Μορφή Αντιβάρου: ").append(antivaro_ss).append(" \n");
        summary.append("Αντίβαρο Μήκος: ").append(antivaro).append("μ \n");

        summary.append("\n----ΕΞΤΡΑ ΥΠΗΡΕΣΙΕΣ----\n");
        summary.append(chBox_diploKouzineto.isChecked() ? diploKouzineto_ss + "\n" : "");
        summary.append(chBox_oneInchKouz.isChecked() ? oneInchKouz_ss + "\n" : "");
        summary.append(chBox_vaseisToixou.isChecked() ? vaseisToixou_ss + "\n" : "");
        summary.append(chBox_vaseisMakries.isChecked() ? vaseisMakries_ss + "\n" : "");
        summary.append(chBox_polikanalo.isChecked() ? polikanalo_ss + "\n" : "");
        summary.append(smartControl_ss).append(" \n");
        summary.append(houfta_ss).append(" \n");

        summary.append("\n----ΕΞΤΡΑ ΥΠΗΡΕΣΙΕΣ - ΜΕ ΤΕΜΑΧΙΑ----\n");
        summary.append(metrhtes_ss).append(" \n");

        return summary.toString();
    }

    private void handler_balconyLength_OnFocusChanged() {

        balcony_length.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Όταν χάνει το focus
                String input = balcony_length.getText().toString().trim();
                try {
                    if (!input.isEmpty() && !input.equals(".")) {
                        double value = Double.parseDouble(input);
                        String formattedText = String.format(Locale.US, "%.2f", value);
                        balcony_length.setText(formattedText);
                    }
                    selectMoteri(axisLength, katevasmaLength);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handler_balconyLength_OnTextChanged() {

        balcony_length.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && !s.toString().equals(".")) {
                    balconyLengthNum = Double.parseDouble(s.toString());
                    // Υπολογισμός με βάση το επιλεγμένο offset
                    axisLength = balconyLengthNum - selectedOffset;
                    paniLength = axisLength - 0.04;
                    antivaroLength = paniLength + 0.03;
                    tv1.setText(String.format(Locale.US, "Άξονας: %.2f", axisLength));
                    tv2.setText(String.format(Locale.US, "Μήκος Πανιού: %.2f", paniLength));
                    tv4.setText(String.format(Locale.US, "Αντίβαρο Μήκος: %.2f", antivaroLength));
                    selectMoteri(axisLength, katevasmaLength);
                }
            }
        });
    }

    private void handler_balconyHeight_OnFocusChanged() {

        balcony_height.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Όταν χάνει το focus
                String input = balcony_height.getText().toString().trim();
                try {
                    if (!input.isEmpty() && !input.equals(".")) {
                        balconyHeightNum = Double.parseDouble(input);
                        String formattedText = String.format(Locale.US, "%.2f", balconyHeightNum);
                        balcony_height.setText(formattedText);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void handler_balconyHeight_OnTextChanged() {

        balcony_height.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && !s.toString().equals(".")) {
                    try {
                        double height = Double.parseDouble(s.toString());
                        vraxionesLength = Math.ceil(height / vraxionesScaling) * vraxionesScaling;
                        katevasmaLength = vraxionesLength + 0.60;
                        tv5.setText(String.format(Locale.US, "Βραχίονες: %.2f", vraxionesLength));
                        tv3.setText(String.format(Locale.US, "Κατέβασμα: %.2f", katevasmaLength));
                        selectVraxiona();
                        selectMoteri(axisLength, katevasmaLength);
                    } catch (NumberFormatException e) {
                        Toast.makeText(TentesMeVraxiones.this, "προβλημα! Πες τον κρι!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void handler_eidosXeirismou() {
        // Listener για το αμα πατησεις μοτερ ή μανιβελα, χειρισμος του radio group αυτου δλδ
        rg_mechanism.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonMotor) {
                    chipG_automaticOptions.setVisibility(View.VISIBLE);
                    selectedOffset = motorOffset;
                    updateAxisLength(balcony_length.getText().toString());
                    sp_crankLength.setVisibility(View.GONE);
                    tv_labelManivela.setVisibility(View.GONE);
                    diaxoristiko10.setVisibility(View.VISIBLE);
                    tv_moteria.setVisibility(View.VISIBLE);
                    chipG_moteria.setVisibility(View.VISIBLE);
                    selectMoteri(axisLength, katevasmaLength);
                    mhxanismos = "Μοτερ";
                } else if (checkedId == R.id.radioButtonManual) {
                    chipG_automaticOptions.setVisibility(View.GONE);
                    selectedOffset = manualOffset;
                    updateAxisLength(balcony_length.getText().toString());
                    sp_crankLength.setVisibility(View.VISIBLE);
                    tv_labelManivela.setVisibility(View.VISIBLE);
                    chipG_automaticOptions.clearCheck();
                    diaxoristiko10.setVisibility(View.GONE);
                    tv_moteria.setVisibility(View.GONE);
                    chipG_moteria.setVisibility(View.GONE);
                    mhxanismos = "Χειροκίνητος (με μανιβελα)";
                }
            }
        });

        chip_withSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("ChipSelection", "With Switch: " + isChecked);
                diakoptisFlag = isChecked;
                autoExtras = "";
                if (isChecked)
                    chip_withThlexeiristirio.setChecked(false);
            }
        });
        chip_withThlexeiristirio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("ChipSelection", "With Thlexeiristhrio: " + isChecked);
                tilexeiristhrioFlag = isChecked;
                autoExtras = "";
                if (isChecked)
                    chip_withSwitch.setChecked(false);
            }
        });
        chip_withHandle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("ChipSelection", "With Handle: " + isChecked);
                manivelaFlag = isChecked;
                autoExtras = "";
                if (isChecked) {
                    sp_crankLength.setVisibility(View.VISIBLE);
                    tv_labelManivela.setVisibility(View.VISIBLE);
                } else {
                    sp_crankLength.setVisibility(View.GONE);
                    tv_labelManivela.setVisibility(View.GONE);
                }
            }
        });

        // Set the default selection to 1.60μ
        sp_crankLength.setSelection(1);
    }

    private void handler_gemiseToCardviewMeTaInfoTouPaniou() {

        Context context = this;

        // Υποθέτοντας ότι έχεις μια λίστα με τα πανιά (κωδικοί και λεπτομέρειες)
        lista_meKodikousPaniwn = new ArrayList<>();

        databaseAPI.getAllPamphlets(new CustomCallback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> pamphlets) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, pamphlets);
                sp_pamphlet.setAdapter(spinnerArrayAdapter);

                pamphlet[0] = sp_pamphlet.getSelectedItem().toString();

                lista_meKodikousPaniwn.clear();
                // YOU NEED TO SELECT THE PAMPHLET FIRST
                databaseAPI.getAllPaniaCodeFromPamphlet(pamphlet[0], new CustomCallback<ArrayList<String>>() {
                    @Override
                    public void onCallback(ArrayList<String> paniaCodes) {
                        lista_meKodikousPaniwn.addAll(paniaCodes);
                    }
                });

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, lista_meKodikousPaniwn);
                act_fabricCodeInput.setAdapter(adapter);

                act_fabricCodeInput.setOnItemClickListener((parent1, view1, position1, id1) -> {
                    selectedCode = (String) parent1.getItemAtPosition(position1);
                    selectPani(selectedCode, pamphlet[0], "no", null);
                    cv_fabricDetailCard.setVisibility(View.VISIBLE);  // Εμφανίζουμε την κάρτα με τις λεπτομέρειες του πανιού
                });
            }
        });

        sp_pamphlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pamphlet[0] = sp_pamphlet.getSelectedItem().toString();

                lista_meKodikousPaniwn.clear();
                // YOU NEED TO SELECT THE PAMPHLET FIRST
                databaseAPI.getAllPaniaCodeFromPamphlet(pamphlet[0], new CustomCallback<ArrayList<String>>() {
                    @Override
                    public void onCallback(ArrayList<String> paniaCodes) {
                        lista_meKodikousPaniwn.addAll(paniaCodes);
                    }
                });


                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, lista_meKodikousPaniwn);
                act_fabricCodeInput.setAdapter(adapter);


                act_fabricCodeInput.setOnItemClickListener((parent1, view1, position1, id1) -> {
                    selectedCode = (String) parent1.getItemAtPosition(position1);
                    selectPani(selectedCode, pamphlet[0], "no", null);
                    cv_fabricDetailCard.setVisibility(View.VISIBLE);  // Εμφανίζουμε την κάρτα με τις λεπτομέρειες του πανιού
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                pamphlet[0] = sp_pamphlet.getSelectedItem().toString();

                lista_meKodikousPaniwn.clear();
                // YOU NEED TO SELECT THE PAMPHLET FIRST
                databaseAPI.getAllPaniaCodeFromPamphlet(pamphlet[0], new CustomCallback<ArrayList<String>>() {
                    @Override
                    public void onCallback(ArrayList<String> paniaCodes) {
                        lista_meKodikousPaniwn.addAll(paniaCodes);
                    }
                });


                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, lista_meKodikousPaniwn);
                act_fabricCodeInput.setAdapter(adapter);

                act_fabricCodeInput.setOnItemClickListener((parent1, view1, position1, id1) -> {
                    selectedCode = (String) parent1.getItemAtPosition(position1);
                    selectPani(selectedCode, pamphlet[0], "no", null);
                    cv_fabricDetailCard.setVisibility(View.VISIBLE);  // Εμφανίζουμε την κάρτα με τις λεπτομέρειες του πανιού
                });
            }

        });
    }

    private void handler_ylopoihshExtraServices() {
        extraServicesHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extraServicesContainer.getVisibility() == View.GONE) {

                    extraServicesContainer.setVisibility(View.VISIBLE);
                    arrowIcon.setImageResource(R.drawable.baseline_arrow_up);

                    View serviceLayout_houfta = extraServicesContainer.getChildAt(7);

                    if (chBox_hufta.isChecked())
                        serviceLayout_houfta.setVisibility(View.VISIBLE);
                    else
                        serviceLayout_houfta.setVisibility(View.GONE);

                    chBox_hufta.setOnCheckedChangeListener((buttonView, isChecked) -> {

                        TextView quantityView = serviceLayout_houfta.findViewById(R.id.service_quantity);

                        if (isChecked) {
                            rg_hufta.setEnabled(true);
                            for (int i = 0; i < rg_hufta.getChildCount(); i++) {
                                rg_hufta.getChildAt(i).setEnabled(true);
                                quantityView.setText("1");
                                serviceLayout_houfta.setVisibility(View.VISIBLE);
                            }
                        } else {
                            rg_hufta.setEnabled(false);
                            for (int i = 0; i < rg_hufta.getChildCount(); i++) {
                                rg_hufta.getChildAt(i).setEnabled(false);
                                rg_hufta.clearCheck();
                                quantityView.setText("0");
                                serviceLayout_houfta.setVisibility(View.GONE);
                            }
                        }
                    });

                    chBox_smartControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            rg_smartHome.setEnabled(true);
                            for (int i = 0; i < rg_smartHome.getChildCount(); i++) {
                                rg_smartHome.getChildAt(i).setEnabled(true);
                            }
                        } else {
                            rg_smartHome.setEnabled(false);
                            for (int i = 0; i < rg_smartHome.getChildCount(); i++) {
                                rg_smartHome.getChildAt(i).setEnabled(false);
                                rg_smartHome.clearCheck();
                            }
                        }
                    });

                } else {
                    extraServicesContainer.setVisibility(View.GONE);
                    arrowIcon.setImageResource(R.drawable.baseline_arrow_down);
                }
            }
        });
    }

    private String getSelected_MotorExtras() {
        if (diakoptisFlag && tilexeiristhrioFlag && manivelaFlag)
            autoExtras = "Με διακόπτη, Με Τηλεχειριστήριο, Με Μανιβέλα \n";
        else if (diakoptisFlag && tilexeiristhrioFlag && !manivelaFlag)
            autoExtras = "Με διακόπτη, Με Τηλεχειριστήριο \n";
        else if (diakoptisFlag && !tilexeiristhrioFlag && manivelaFlag)
            autoExtras = "Με διακόπτη, Με Μανιβέλα \n";
        else if (!diakoptisFlag && tilexeiristhrioFlag && manivelaFlag)
            autoExtras = "Με Τηλεχειριστήριο, Με Μανιβέλα \n";
        else if (!diakoptisFlag && !tilexeiristhrioFlag && manivelaFlag)
            autoExtras = "Με Μανιβέλα \n";
        else if (!diakoptisFlag && tilexeiristhrioFlag && !manivelaFlag)
            autoExtras = "Με Τηλεχειριστήριο \n";
        else if (diakoptisFlag && !tilexeiristhrioFlag && !manivelaFlag)
            autoExtras = "Με Διακόπτη \n";
        else
            autoExtras = " ";

        return autoExtras;
    }

    private String getSelected_ChipAksona() {

        chipG_eidosAksona.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                // Η λίστα με τα IDs των τσεκαρισμένων Chips
                if (!checkedIds.isEmpty()) {
                    // Παράδειγμα: Λήψη του πρώτου τσεκαρισμένου Chip. Ή όποιο άλλο θέλεις να διαχειριστείς
                    int selectedChipId = checkedIds.get(0);
                    Chip selectedChip = group.findViewById(selectedChipId);
                    String chipText = selectedChip.getText().toString();
                    Log.d("ChipGroup", "Selected Chip: " + chipText);
                } else {
                    chipText_eidosAksona = "";
                    Log.d("ChipGroup", "No Chip selected");
                }
            }
        });

        // Βρες το ID του επιλεγμένου Chip
        int selectedChipId = chipG_eidosAksona.getCheckedChipId();

        if (selectedChipId != -1) {
            // Βρες το ίδιο το Chip με βάση το ID
            Chip selectedChip = findViewById(selectedChipId);

            // Πάρε το κείμενο του Chip
            chipText_eidosAksona = selectedChip.getText().toString();

        } else {
            // Αν κανένα Chip δεν είναι επιλεγμένο
            Toast.makeText(getApplicationContext(), "Δεν έχεις επιλέξει διάμετρο άξονα!", Toast.LENGTH_SHORT).show();
        }

        return chipText_eidosAksona;
    }

    private String getSelected_MotorPower() {

        String selectedPower = "";
        Chip chip30, chip50, chip80, chip100, chip120;

        chip30 = (Chip) chipG_moteria.getChildAt(0);
        chip50 = (Chip) chipG_moteria.getChildAt(1);
        chip80 = (Chip) chipG_moteria.getChildAt(2);
        chip100 = (Chip) chipG_moteria.getChildAt(3);
        chip120 = (Chip) chipG_moteria.getChildAt(4);

        if (chip30.isChecked())
            selectedPower = "Ισχύς Μοτέρ: 30nm";
        else if (chip50.isChecked())
            selectedPower = "Ισχύς Μοτέρ: 50nm";
        else if (chip80.isChecked())
            selectedPower = "Ισχύς Μοτέρ: 80nm";
        else if (chip100.isChecked())
            selectedPower = "Ισχύς Μοτέρ: 100nm";
        else if (chip120.isChecked())
            selectedPower = "Ισχύς Μοτέρ: 120nm";

        return selectedPower;
    }

    private String getSelected_EidosVraxiona() {

        String selectedEidos = "";
        RadioButton isio, lokso, vareos;

        isio = (RadioButton) rg_EidosVraxiona.getChildAt(0);
        lokso = (RadioButton) rg_EidosVraxiona.getChildAt(1);
        vareos = (RadioButton) rg_EidosVraxiona.getChildAt(2);

        if (isio.isChecked())
            selectedEidos = "Είδος Βραχίονα: ΙΣΙΟΣ";
        else if (lokso.isChecked())
            selectedEidos = "Είδος Βραχίονα: ΛΟΞΟΣ";
        else if (vareos.isChecked())
            selectedEidos = "Είδος Βραχίονα: ΒΑΡΕΩΣ ΤΥΠΟΥ";

        return selectedEidos;
    }

    private String getSelected_MerosVraxiona() {

        String selectedMeros = "";
        RadioButton mesa, ekso;

        mesa = (RadioButton) rg_merosVraxiona.getChildAt(0);
        ekso = (RadioButton) rg_merosVraxiona.getChildAt(1);

        if (mesa.isChecked())
            selectedMeros = "Τοποθέτηση Βραχίονα από: ΜΕΣΑ";
        else if (ekso.isChecked())
            selectedMeros = "Τοποθέτηση Βραχίονα από: ΕΞΩ";

        return selectedMeros;
    }

    private List<String> getSelected_timesApoMetrhtes() {

        List<String> selectedServices = new ArrayList<>();
        intArray2 = new int[9];

        // Επανάληψη σε κάθε Layout μέσα στο container
        for (int i = 7; i < extraServicesContainer.getChildCount(); i++) {

            View serviceLayout = extraServicesContainer.getChildAt(i);

            // Βρες τα στοιχεία του Layout
            TextView serviceNameView = serviceLayout.findViewById(R.id.service_name);
            TextView quantityView = serviceLayout.findViewById(R.id.service_quantity);

            // Πάρε τα δεδομένα
            String serviceName = serviceNameView.getText().toString();
            int quantity = Integer.parseInt(quantityView.getText().toString());

            intArray2[i - 7] = quantity;

            // Πρόσθεσε το service αν έχει επιλεγεί (π.χ., αν quantity > 0)
            if (quantity > 0)
                selectedServices.add(serviceName + ": " + quantity + "τμχ");
        }

        // Εμφάνιση ή αποστολή των δεδομένων
        return selectedServices;
    }

    private void des_poies_extraYpiresies_einai_epilegmenes() {

        if (chBox_diploKouzineto.isChecked())
            diploKouzineto = 1;
        else
            diploKouzineto = 0;

        if (chBox_oneInchKouz.isChecked())
            oneInchKouzineto = 1;
        else
            oneInchKouzineto = 0;

        if (chBox_vaseisToixou.isChecked())
            vaseisToixou = 1;
        else
            vaseisToixou = 0;

        if (chBox_vaseisMakries.isChecked())
            vaseisMakries = 1;
        else
            vaseisMakries = 0;

        if (chBox_polikanalo.isChecked())
            polikanalo = 1;
        else
            polikanalo = 0;

        if (!chBox_smartControl.isChecked())
            smartControl = 0;
        else if (rb_smartPalio.isChecked())
            smartControl = 1;
        else if (rb_smartKainourgio.isChecked()) {
            smartControl = 2;
        }

        if (!chBox_hufta.isChecked())
            houfta = 0;
        else if (rb_f48.isChecked())
            houfta = 1;
        else if (rb_f70.isChecked()) {
            houfta = 2;
        }

    }

    private void inflateThnYphresia(String serviceName, LinearLayout container, String meHoufta) {

        // Κάνε inflate το services_layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View serviceLayout = inflater.inflate(R.layout.yphresia_me_temaxia, null);

        // Πρόσθεσε το inflated layout στο container
        container.addView(serviceLayout);

        // Βρες το services_container μέσα στο serviceLayout
        LinearLayout tempLayout = serviceLayout.findViewById(R.id.yphresiaMeTemaxia);

        // Ρύθμισε το service counter
        if (meHoufta.equals("yes"))
            setupServiceCounter(tempLayout, serviceName, "yes");
        else
            setupServiceCounter(tempLayout, serviceName, "no");
    }

    private void setupServiceCounter(LinearLayout aLayout, String aName, String meHoufta) {

        TextView serviceNameView = aLayout.findViewById(R.id.service_name);
        Button decrementButton = aLayout.findViewById(R.id.button_decrement);
        TextView quantityView = aLayout.findViewById(R.id.service_quantity);
        Button incrementButton = aLayout.findViewById(R.id.button_increment);

        serviceNameView.setText(aName);

        // Set default quantity to 0
        if (meHoufta.equals("yes"))
            quantityView.setText("1");
        else
            quantityView.setText("0");

        // Increment button logic
        incrementButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityView.getText().toString());
            quantityView.setText(String.valueOf(currentQuantity + 1));
        });

        // Decrement button logic
        decrementButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityView.getText().toString());
            if (currentQuantity > 0) {
                quantityView.setText(String.valueOf(currentQuantity - 1));
            }
        });
    }

    private void updateAxisLength(String lengthText) {
        if (!lengthText.isEmpty()) {
            double balconyLength = Double.parseDouble(lengthText);
            axisLength = balconyLength - selectedOffset;
            double paniLength = axisLength - 0.04;
            double antivaroLength = paniLength + 0.03;
            tv1.setText(String.format(Locale.US, "Άξονας: %.2f", axisLength));
            tv2.setText(String.format(Locale.US, "Μήκος Πανιού: %.2f", paniLength));
            tv4.setText(String.format(Locale.US, "Αντίβαρο Μήκος: %.2f", antivaroLength));
        }
    }

    private void selectMoteri(double aksonas, double katevasma) {

        if (aksonas < 5.50 && katevasma < 2.00) {
            chipG_moteria.clearCheck();
            chip_moter50.setChecked(true);
        } else if (aksonas < 5.50 && katevasma < 2.50) {
            chipG_moteria.clearCheck();
            chip_moter80.setChecked(true);
        } else if (aksonas >= 5.50 && aksonas < 6.00 && katevasma >= 2.25 && katevasma < 2.75) {
            chipG_moteria.clearCheck();
            chip_moter80.setChecked(true);
        } else if (aksonas < 5.50 && katevasma >= 2.75 && katevasma < 3.50) {
            chipG_moteria.clearCheck();
            chip_moter100.setChecked(true);
        } else if (aksonas >= 5.50 && aksonas < 6.00 && katevasma >= 2.75 && katevasma < 3.50) {
            chipG_moteria.clearCheck();
            chip_moter120.setChecked(true);
        } else if (aksonas >= 6.00 && katevasma >= 2.00 && katevasma < 2.25) {
            chipG_moteria.clearCheck();
            chip_moter120.setChecked(true);
        } else if (aksonas >= 6.00 && katevasma >= 2.25) {
            chipG_moteria.clearCheck();
            chip_moter120.setChecked(true);
        }
    }

    private void selectVraxiona() {
        Spinner spinner_vraxiones = findViewById(R.id.spinner_vraxiones);
        // Find the index of the matching item in the Spinner
        int itemCount = spinner_vraxiones.getCount();
        for (int i = 0; i < itemCount; i++) {
            // Convert Spinner item to double and check if it matches the rounded value
            if (Double.parseDouble(spinner_vraxiones.getItemAtPosition(i).toString()) == vraxionesLength) {
                spinner_vraxiones.setSelection(i); // Select the matching item
                break;
            }
        }
    }

    private void setCorrectVraxionaLength() {
        sp_mikosVraxiona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLength = parent.getItemAtPosition(position).toString();

                try {
                    double length = Double.parseDouble(selectedLength);

                    // Έλεγχος αν η τιμή είναι πάνω από 3.00
                    if (length >= 3.00) {
                        rb_VareosTypou.setEnabled(true);
                        rb_VareosTypou.setChecked(true);
                    } else {
                        rb_VareosTypou.setEnabled(false);
                        rg_EidosVraxiona.clearCheck();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Διαχείριση σφάλματος αν η τιμή δεν είναι αριθμός
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectPani(String kodikosPaniou, String pamphlet, String editMode, String selectedColor) {
        databaseAPI.getPani(kodikosPaniou, pamphlet, pani -> {
            tv_fabricName.setText(pani.getName());

            if (editMode.equals("no"))
                addXromataPaniwn(pani.getColors(), pani.getSeries(), "no", null);
            else
                addXromataPaniwn(pani.getColors(), pani.getSeries(), "yes", selectedColor);

            pamphlet_s = pamphlet;
            kodikosPaniou_s = pani.getCode();
            onomaPaniou_s = pani.getName();
            seiraPaniou_s = pani.getSeries();
            perigrafhSeiras_s = pani.getDescription();
        });
    }

    private void addXromataPaniwn(ArrayList<String> availableColors, String seiraPaniou, String editMode, String selectedColor) {

        tv_fabricSeries.setText("Σειρά Πανιού: " + seiraPaniou);
        chipG_Colors.removeAllViews();

        for (String color : availableColors) {
            Chip chip = new Chip(this);
            chip.setText(color);
            chip.setCheckable(true); // επιλέξιμο
            chipG_Colors.addView(chip);
        }

        if (editMode.equals("no"))
            chipG_Colors.check(chipG_Colors.getChildAt(0).getId()); // Εάν θέλεις το πρώτο chip να είναι προεπιλεγμένο:
        else {
            Log.d("!", "chipG_Colors.getChildCount(): " + chipG_Colors.getChildCount());
            for (int i = 0; i < chipG_Colors.getChildCount(); i++) {
                View child = chipG_Colors.getChildAt(i);
                if (child instanceof Chip) {
                    Chip chip = (Chip) child;
                    // συγκρίνω το κείμενο του chip με το selectedColor
                    if (chip.getText().toString().equals(selectedColor)) {
                        // επιλέγω αυτό το chip
                        chipG_Colors.check(chip.getId());
                        break;  // βγήκαμε, δεν χρειάζεται άλλο
                    }
                }
            }
        }
    }

    public static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    @Override
    public void onFragmentClosed() {
        if (view30 != null) {
            setViewAndChildrenEnabled(view30, true);
        }
    }
}