package com.example.tugasday5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity{

    public static final String KEY_DATA = "key_data";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvSelamat = findViewById(R.id.tvSelamat);
        TextView tvTipeMember = findViewById(R.id.tvTipeMember);
        TextView tvKodeBarang = findViewById(R.id.tvKodeBarang);
        TextView tvNamaBarang = findViewById(R.id.tvNamaBarang);
        TextView tvHargaBarang = findViewById(R.id.tvHargaBarang);
        TextView tvJumlahbarang = findViewById(R.id.tvJumlahBarang);
        TextView tvTotalHarga = findViewById(R.id.tvTotalHarga);
        TextView tvDiskonHarga = findViewById(R.id.tvDiskonHarga);
        TextView tvDiskonMember = findViewById(R.id.tvDiskonMember);
        TextView tvJumlahbayar = findViewById(R.id.tvJumlahBayar);

        Button btnShare = findViewById(R.id.btnShare);

        Detail detail;
        if (Build.VERSION.SDK_INT >= 33){
            detail = getIntent().getParcelableExtra(KEY_DATA, Detail.class);
        } else {
            detail = getIntent().getParcelableExtra(KEY_DATA);
        }

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        tvSelamat.setText(getText(R.string.selamatDatang) + " " + detail.getNamaPelanggan());
        tvTipeMember.setText(getText(R.string.tipeMember) + " " + detail.getTipeMember());
        tvKodeBarang.setText(getText(R.string.kodeBarang) + " " + detail.getKodeBarang());
        tvNamaBarang.setText(getText(R.string.namaBarang) + " " + detail.getNamaBarang());

        int hargaBarang = detail.getHargaBarang();
        int jumlahBarang = detail.getJumlahBarang();
        tvHargaBarang.setText(getText(R.string.hargaBarang) + " "  + formatRupiah.format((double)hargaBarang));
        tvJumlahbarang.setText(getText(R.string.jumlahBarang) + " " + jumlahBarang);

        int totalHarga = hargaBarang * jumlahBarang;

        tvTotalHarga.setText(getText(R.string.totalHargaBarang) + " " + formatRupiah.format((double)totalHarga));

        int diskon10juta = 0;
        if (totalHarga > 10000000){
            diskon10juta = 100000;
        }

        double diskonMember = 0;
        switch (detail.getTipeMember()) {
            case "Gold":
                diskonMember = 0.1 * totalHarga;
                break;
            case "Silver":
                diskonMember = 0.05 * totalHarga;
                break;
            case "Biasa":
                diskonMember = 0.01 * totalHarga;
                break;
            default:
                // Tidak ada diskon untuk tipe member lain
                break;
        }
        totalHarga -= diskonMember;


        totalHarga -= diskon10juta;
        tvDiskonHarga.setText(getText(R.string.diskonHarga) + " " + formatRupiah.format((double)diskon10juta));
        tvDiskonMember.setText(getText(R.string.diskonMember) + " " + formatRupiah.format(diskonMember));
        tvJumlahbayar.setText(getText(R.string.jumlahBayar) + " " + formatRupiah.format((double)totalHarga));

        btnShare.setOnClickListener(click -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareMessage = tvSelamat.getText() +
                    "\n"+ tvTipeMember.getText() +
                    "\n" + tvKodeBarang.getText() +
                    "\n" + tvNamaBarang.getText() +
                    "\n" + tvHargaBarang.getText() +
                    "\n" + tvDiskonHarga.getText() +
                    "\n" + tvDiskonMember.getText() +
                    "\n" + tvJumlahbayar.getText();
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Bagikan melalui"));
        });
    }

}