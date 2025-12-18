package io;

import model.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CsvStorage {
	/**
     * Listeyi CSV dosyasına yazar.
     * Locale.US kullanılarak sayıların "150,00" değil "150.00" olarak yazılması garanti edilir.
     * Böylece CSV yapısı bozulmaz.
     */
    public static void save(List<Transaction> liste, String dosyaYolu) {
        try (FileWriter writer = new FileWriter(dosyaYolu)) {
            for (Transaction t : liste) {
                // Not içindeki virgülleri temizle
                String safeNote = t.getNote() != null ? t.getNote().replace(",", " ") : "";
                
                // Locale.US: Sayıları her zaman nokta ile yazar (Örn: 125.50)
                String line = String.format(Locale.US, "%s,%s,%.2f,%s\n",
                        t.getDate(),
                        t.getCategory(),
                        t.getAmount(),
                        safeNote);
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CSV dosyasını okur.
     */
    public static List<Transaction> load(String dosyaYolu) {
        List<Transaction> list = new ArrayList<>();
        File file = new File(dosyaYolu);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Satırı virgüllere böl
                String[] parts = line.split(",", -1);
                
                if (parts.length >= 3) {
                    try {
                        LocalDate date = LocalDate.parse(parts[0]);
                        String category = parts[1];
                        // Sayıyı okurken nokta formatına uygun parse et
                        double amount = Double.parseDouble(parts[2]);
                        
                        // Not kısmını al (Eğer varsa)
                        String note = (parts.length > 3) ? parts[3] : "";

                        list.add(new Transaction(date, category, amount, note));
                    } catch (Exception e) {
                        // Hatalı satırları atla ama konsola bilgi ver
                        System.err.println("Okuma hatası (Satır atlandı): " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
