package com.example.olegario.escamboapp.helper;

public final class Formatter {

    private static final Formatter INSTANCE = new Formatter();

    public Formatter() {}

    public static Formatter getInstance() {
        return INSTANCE;
    }

    public String formatBirthDate(String birthdate) {
        // Follows the format DD/MM/YYYY
        return birthdate.substring(0, 2) + "/" +
               birthdate.substring(2, 4) + "/" +
               birthdate.substring(4, birthdate.length());
    }

    public String formatPhone(String phone) {
        // Follows the format (##) #####-####
        return "(" + phone.substring(0, 2) + ")" +
                " " + phone.substring(2, 7) + "-" +
                phone.substring(7, phone.length());
    }

    public String formatCPF(String cpf) {
        // Follows the format ###.###.###-##
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, cpf.length());
    }
}
