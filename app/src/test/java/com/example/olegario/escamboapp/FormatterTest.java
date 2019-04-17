package com.example.olegario.escamboapp;

import com.example.olegario.escamboapp.helper.Formatter;

import org.junit.Test;

public class FormatterTest {

    private final Formatter formatter = Formatter.getInstance();

    @Test
    public void formatBirthDate() {
        final String birthdate = "08051996";
        final String birthdateFormatted = formatter.formatBirthDate(birthdate);
        final boolean result = "08/05/1996".equals(birthdateFormatted);
        assert result;
    }

    @Test
    public void formatCPF() {
        final String cpf = "41376181002";
        final String cpfFormatted = formatter.formatCPF(cpf);
        final boolean result = "413.761.810-02".equals(cpfFormatted);
        assert result;
    }

    @Test
    public void formatPhone() {
        final String phone = "66989421035";
        final String phoneFormatted = formatter.formatPhone(phone);
        final boolean result = "(66) 98942-1035".equals(phoneFormatted);
        assert result;
    }
}
