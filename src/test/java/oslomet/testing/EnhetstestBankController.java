package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;



    // Bank Controller ****************************
    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_feilmedloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(null);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }



    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_FeilmedLoggetInn()  {
        // arrange


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }





    // Mangler /utforBetaling metoden

    @Test
    public void hentTransaksjoner_LoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523"); //personnumer eller kontonummer?

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);

        Konto resultat = bankController.hentTransaksjoner("01010110523","2000-01-01","2100-01-01");

        assertEquals(konto1,resultat);

    }


    @Test
    public void hentTransaksjoner_feilmedLoggin() {

        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523"); //personnumer eller kontonummer?

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("01010110523","2000-01-01","2100-01-01");

        assertNull(resultat);

    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {

        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("01010110523","2000-01-01","2100-01-01");

        assertNull(resultat);

    }



    @Test
    public void hentSaldi_LoggetInn() {

        List<Konto> konti = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        List<Konto> resultat = bankController.hentSaldi();

        assertEquals(konti,resultat);
    }

    @Test
    public void hentSaldi_feilMedloggin() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(null);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }




    @Test
    public void registrerBetaling_LoggetInn() {

        Transaksjon transaksjon = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft","1","105010123456");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        String resultat = bankController.registrerBetaling(transaksjon);
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {

        Transaksjon transaksjon = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft","1","105010123456");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.registrerBetaling(transaksjon);
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_Feil() { // feil cases

        Transaksjon transaksjon = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft","1","105010123456");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Feil");

        String resultat = bankController.registrerBetaling(transaksjon);
        assertEquals("Feil", resultat);
    }

    @Test
    public void hentBetalinger_LoggInn() {
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon trans1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft","1","105010123456");
        Transaksjon trans2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        transaksjoner.add(trans1);
        transaksjoner.add(trans2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(transaksjoner,resultat);
    }

    @Test
    public void hentBetalinger_feilMedLoggInn() {


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }




    @Test
    public void endreKundeInfo_LoggInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String resultat = bankController.endre(enKunde);

        assertEquals("OK",resultat);
    }

    @Test
    public void endreKundeInfo_feilMedLoggInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        String resultat = bankController.endre(enKunde);

        assertEquals("Feil",resultat);
    }


    @Test
    public void endreKundeInfo_IkkeLoggInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(sjekk.loggetInn()).thenReturn(null);


        String resultat = bankController.endre(enKunde);

        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggInn() {
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon trans1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft","1","105010123456");
        Transaksjon trans2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        transaksjoner.add(trans1);
        transaksjoner.add(trans2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.utforBetaling(anyInt())).thenReturn("OK");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertEquals(transaksjoner,resultat);
    }

    @Test
    public void utforBetaling_FeilLoggInn() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.utforBetaling(anyInt())).thenReturn("Feil");

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertNull(resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertNull(resultat);
    }





    // Ferdig BankController *************************************






}

