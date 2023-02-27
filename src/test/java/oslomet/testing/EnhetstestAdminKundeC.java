package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeC {

    @InjectMocks
    private AdminKundeController controller;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    // start

    //hentAlle
    @Test
    public void hentAlle_LoggetInn() {

        List<Kunde> kundeliste = new ArrayList<>();
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde enKunde2 = new Kunde("12345678901",
                "Per", "Hansen", "Osloveien 82", "1234",
                "Oslo", "12345678", "HeiHei");

        kundeliste.add(enKunde1);
        kundeliste.add(enKunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kundeliste);

        List<Kunde> resultat = controller.hentAlle();

        assertEquals(kundeliste,resultat);
    }

    @Test
    public void hentAlle_FeilMedLoggetInn() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(null);

        List<Kunde> resultat = controller.hentAlle();

        assertNull(resultat);
    }

    @Test
    public void hentAlle_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Kunde> resultat = controller.hentAlle();

        assertNull(resultat);
    }




    // lagre

    @Test
    public void lagreKunde_LoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        String resultat = controller.lagreKunde(enKunde1);

        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_FeilMedLoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("Feil");

        String resultat = controller.lagreKunde(enKunde1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = controller.lagreKunde(enKunde1);

        assertNull(resultat);
    }


    // endre

    @Test
    public void endre_LoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String resultat = controller.endre(enKunde1);

        assertEquals("OK", resultat);
    }

    @Test
    public void endre_FeilMedLoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        String resultat = controller.endre(enKunde1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        Kunde enKunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = controller.endre(enKunde1);

        assertNull(resultat);
    }



    // slett

    @Test
    public void slett_LoggetInn() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("OK");

        String resultat = controller.slett("01010110523");

        assertEquals("OK", resultat);
    }

    @Test
    public void slett_FeilMedLoggetInn() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("Feil");

        String resultat = controller.slett("01010110523");

        assertEquals("Feil", resultat);
    }

    @Test
    public void slett_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = controller.slett("01010110523");

        assertNull(resultat);
    }

}
