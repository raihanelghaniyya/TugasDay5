package com.example.tugasday5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnProses = findViewById(R.id.btnProses);
        EditText etKodeBarang = findViewById(R.id.etKodebarang);
        EditText etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        EditText etJumlahBarang = findViewById(R.id.etJumlahBarang);
        RadioButton memberGold = findViewById(R.id.memberGold);
        RadioButton memberSilver = findViewById(R.id.memberSilver);
        RadioButton memberBiasa = findViewById(R.id.memberBiasa);

        Detail detail = new Detail();
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaPelanggan = etNamaPelanggan.getText().toString().trim();
                if (namaPelanggan.isEmpty()){
                    etNamaPelanggan.setError("Isi Terlebih Dahulu!");
                    return;
                } else {
                    detail.setNamaPelanggan(namaPelanggan);
                }

                String tipeMember = null;

                if (memberGold.isChecked()) {
                    tipeMember = "Gold";
                } else if (memberSilver.isChecked()) {
                    tipeMember = "Silver";
                } else if (memberBiasa.isChecked()) {
                    tipeMember = "Biasa";
                }

                if (tipeMember == null) {
                    Toast.makeText(MainActivity.this, "Pilih tipe member", Toast.LENGTH_SHORT).show();
                    return;
                }

                detail.setTipeMember(tipeMember);


                String kodeBarang = etKodeBarang.getText().toString().toUpperCase().trim();
                switch (kodeBarang) {
                    case "SGS":
                        detail.setKodeBarang("SGS");
                        detail.setNamaBarang("Samsung Galaxy S20");
                        detail.setHargaBarang(12999999);
                        break;
                    case "AV4":
                        detail.setKodeBarang("AV4");
                        detail.setNamaBarang("ASUS Vivobook 14");
                        detail.setHargaBarang(9150999);
                        break;
                    case "MP3":
                        detail.setKodeBarang("MP3");
                        detail.setNamaBarang("Macbook Pro M3");
                        detail.setHargaBarang(28999999);
                        break;
                    default:
                        if (kodeBarang.isEmpty()) {
                            etKodeBarang.setError("Isi Terlebih Dahulu!");
                            Log.d("MainActivity", "Kode Barang kosong");
                        } else {
                            etKodeBarang.setError("Kode Tidak Valid!");
                            Log.d("MainActivity", "Kode Tidak Valid");
                        }
                        return;
                }


                String jumlah = etJumlahBarang.getText().toString().trim();
                if (jumlah.isEmpty()){
                    etJumlahBarang.setError("Isi Terlebih Dahulu!");
                    return;
                } else {
                    int jumlahBarang = Integer.parseInt(jumlah);
                    detail.setJumlahBarang(jumlahBarang);
                }

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_DATA, detail);
                startActivity(intent);
            }
        });
    }
}