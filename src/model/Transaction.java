package model;

import java.time.LocalDate;

public class Transaction {
	// İstenen özellikler (Fields) - Hepsi private
    private LocalDate date;
    private String category;
    private double amount;
    private String note;

    // Parametreli Constructor
    public Transaction(LocalDate date, String category, double amount, String note) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    // Getter Metotları
    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    // toString Metodunun Override Edilmesi
    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
}