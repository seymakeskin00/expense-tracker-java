package ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

public class ChartPanel extends JPanel{
	private Map<String, Double> categoryTotals;

    public void setData(Map<String, Double> data) {
        this.categoryTotals = data;
        repaint();
    }

    /**
     * Kategoriye özel sabit ve göz yormayan renkleri belirleyen metot.
     */
    private Color getColorForCategory(String category) {
        if (category == null) return Color.GRAY;

        switch (category) {
            case "Gıda":
                return new Color(231, 76, 60);  // Mat Kırmızı (Alizarin)
            case "Ulaşım":
                return new Color(52, 152, 219); // Mat Mavi (Peter River)
            case "Eğlence":
                return new Color(155, 89, 182); // Mat Mor (Amethyst)
            case "Fatura":
                return new Color(46, 204, 113); // Mat Yeşil (Emerald)
            case "Giyim":
                return new Color(241, 196, 15); // Mat Sarı/Hardal (Sun Flower)
            case "Diğer":
                return new Color(149, 165, 166); // Mat Gri (Concrete)
            default:
                return new Color(52, 73, 94);   // Bilinmeyenler için Koyu Lacivert
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (categoryTotals == null || categoryTotals.isEmpty()) {
            g.drawString("Görüntülenecek veri yok.", 20, 30);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int graphHeight = height - (2 * padding);
        
        double maxAmount = 0;
        for (double val : categoryTotals.values()) {
            if (val > maxAmount) maxAmount = val;
        }

        // Eğer maxAmount 0 ise (tüm harcamalar silindiyse) bölme hatası almamak için 1 yap
        if (maxAmount == 0) maxAmount = 1;

        int barWidth = (width - (2 * padding)) / categoryTotals.size();
        int x = padding;

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();

            int barHeight = (int) ((amount / maxAmount) * graphHeight);
            int y = height - padding - barHeight;

            // --- RENK SEÇİMİ ---
            // Artık sıradaki rengi değil, kategoriye özel rengi çağırıyoruz
            g.setColor(getColorForCategory(category));
            g.fillRect(x, y, barWidth - 10, barHeight);

            // --- YAZILAR (Siyah) ---
            g.setColor(Color.BLACK);
            
            // Tutarı sütunun üzerine yaz
            g.drawString(String.format("%.1f", amount), x, y - 5);
            
            // Kategori ismini sütunun altına yaz (Uzun isimler sığmazsa diye sadece ilk 7 harf)
            String displayCategory = category.length() > 10 ? category.substring(0, 10) + ".." : category;
            g.drawString(displayCategory, x, height - padding + 15);

            x += barWidth;
        }
    }
}