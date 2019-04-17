//package com.example.olegario.escamboapp;
//
//import android.util.Log;
//
//import com.example.olegario.escamboapp.helper.DataValidator;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import static org.mockito.Mockito.when;
//
//
//public class DataValidatorTest {
//
//    private final DataValidator dataValidator = Mockito.mock(DataValidator.class);
//
////    @Test
////    public void validateEmailValid() {
////        final String email = "gustavo-olegario@hotmail.com";
////        final boolean result = when(dataValidator.validateEmail(email)).thenReturn(true);
////        assert result;
////    }
//
//    @Test
//    public void validateEmailInvalid() {
//        final String email = "invalid-email.com";
//        final boolean result = !dataValidator.validateEmail(email);
//        assert result;
//    }
//
//    @Test
//    public void validatePasswordValid() {
//        final String password = "12341234";
//        final boolean result = dataValidator.validatePassword(password);
//        assert result;
//    }
//
//    @Test
//    public void validatePasswordInvalid() {
//        final String password = "1234";
//        final boolean result = (dataValidator.validatePassword(password) == false);
//        assert result;
//    }
//}
