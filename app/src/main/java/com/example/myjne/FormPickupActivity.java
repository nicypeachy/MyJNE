package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class FormPickupActivity extends AppCompatActivity {

    private Spinner spinnerLokasiAsal, spinnerLokasiTujuan;
    private EditText etNamaPengirim, etNomorTelepon, etJenisBarang;
    private RadioGroup rgEstimasiBerat;
    private TextView tvBiaya;
    private Button btnPesanSekarang;
    private DBhelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpickup);

        // Inisialisasi Views
        spinnerLokasiAsal = findViewById(R.id.spinner_lokasiasal);
        spinnerLokasiTujuan = findViewById(R.id.spinner_lokasitujuan);
        etNamaPengirim = findViewById(R.id.et_namalengkap);
        etNomorTelepon = findViewById(R.id.et_phone_number);
        etJenisBarang = findViewById(R.id.et_namajenisbarang);
        rgEstimasiBerat = findViewById(R.id.rg_estimasi_berat);
        tvBiaya = findViewById(R.id.tv_biaya);
        btnPesanSekarang = findViewById(R.id.btn_pesansekarang);

        // Set default text untuk biaya
        tvBiaya.setText("Rp. 0");

        // Inisialisasi Database Helper
        databaseHelper = new DBhelper(this);

        // Isi data ke Spinner
        populateSpinner(spinnerLokasiAsal);
        populateSpinner(spinnerLokasiTujuan);

        // Listener untuk RadioGroup
        rgEstimasiBerat.setOnCheckedChangeListener((group, checkedId) -> calculatePrice());

        // Listener untuk Spinner
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        };
        spinnerLokasiAsal.setOnItemSelectedListener(spinnerListener);
        spinnerLokasiTujuan.setOnItemSelectedListener(spinnerListener);

        // Listener untuk tombol
        btnPesanSekarang.setOnClickListener(v -> submitOrder());
    }

    private void populateSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lokasi_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void calculatePrice() {
        String lokasiAsal = spinnerLokasiAsal.getSelectedItem().toString();
        String lokasiTujuan = spinnerLokasiTujuan.getSelectedItem().toString();
        int basePrice = 0;
        int additionalPrice = 0;

        // Validasi lokasi
        if (!"Pilih Lokasi".equals(lokasiAsal) && !"Pilih Lokasi".equals(lokasiTujuan)) {
            basePrice = lokasiAsal.equals(lokasiTujuan) ? 10000 : 20000;

            // Validasi RadioGroup dan Hitung harga tambahan
            int selectedId = rgEstimasiBerat.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedButton = findViewById(selectedId);
                String berat = (String) selectedButton.getTag();

                if ("Small".equalsIgnoreCase(berat)) {
                    additionalPrice = 5000;
                } else if ("Medium".equalsIgnoreCase(berat)) {
                    additionalPrice = 15000;
                } else if ("Large".equalsIgnoreCase(berat)) {
                    additionalPrice = 25000;
                }
            }

            // Hitung total harga
            int totalPrice = basePrice + additionalPrice;
            tvBiaya.setText("Rp. " + totalPrice);
        } else {
            tvBiaya.setText("Rp. 0");
        }
    }

    private void submitOrder() {
        String lokasiAsal = spinnerLokasiAsal.getSelectedItem().toString();
        String lokasiTujuan = spinnerLokasiTujuan.getSelectedItem().toString();
        String namaPengirim = etNamaPengirim.getText().toString().trim();
        String nomorTelepon = etNomorTelepon.getText().toString().trim();
        String jenisBarang = etJenisBarang.getText().toString().trim();

        // Validasi input
        int selectedId = rgEstimasiBerat.getCheckedRadioButtonId();
        if ("Pilih Lokasi".equals(lokasiAsal) ||
                "Pilih Lokasi".equals(lokasiTujuan) ||
                namaPengirim.isEmpty() ||
                nomorTelepon.isEmpty() ||
                jenisBarang.isEmpty() ||
                selectedId == -1) {

            Toast.makeText(this, getString(R.string.validation_message), Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil data dari RadioButton yang dipilih
        RadioButton selectedRB = findViewById(selectedId);
        String estimasiBerat = (String) selectedRB.getTag();
        String biaya = tvBiaya.getText().toString();

        // Simpan data ke database
        boolean isInserted = databaseHelper.insertOrder(
                lokasiAsal,
                lokasiTujuan,
                namaPengirim,
                nomorTelepon,
                jenisBarang,
                estimasiBerat,
                biaya
        );

        if (isInserted) {
            Toast.makeText(this, getString(R.string.order_success_message), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoadingActivity.class));
            finish();
        } else {
            Toast.makeText(this, getString(R.string.order_failed_message), Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(FormPickupActivity.this, OrderActivity.class);
        intent.putExtra("tvBiaya", tvBiaya.getText().toString()); // Kirim nilai biaya
        startActivity(intent);

    }
}
