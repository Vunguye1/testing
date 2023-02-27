package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoC {

    @InjectMocks
    private AdminKontoController AdminKontoController;

    @Mock
    private AdminRepository repo;

    @Mock
    private Sikkerhet sjekk;


    // AdminKontoController

    @Test
    public void hentAlleKonti_LoggetInn() {

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.hentAlleKonti()).thenReturn(konti);

        List<Konto> resultat = AdminKontoController.hentAlleKonti();

        assertEquals(konti,resultat);
    }

    @Test
    public void hentAlleKonti_FeilMedLoggetInn() {

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.hentAlleKonti()).thenReturn(null);

        List<Konto> resultat = AdminKontoController.hentAlleKonti();

        assertNull(resultat);
    }

    @Test
    public void hentAlleKonti_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = AdminKontoController.hentAlleKonti();

        assertNull(resultat);
    }


    // register

    @Test
    public void registrer_LoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.registrerKonto(any(Konto.class))).thenReturn("OK");

        String resultat = AdminKontoController.registrerKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrer_FeilMedLoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.registrerKonto(any(Konto.class))).thenReturn("Feil");

        String resultat = AdminKontoController.registrerKonto(konto1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void registrer_IkkeLoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = AdminKontoController.registrerKonto(konto1);

        assertNull(resultat);
    }



    // endre

    @Test
    public void endre_LoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.endreKonto(any(Konto.class))).thenReturn("OK");

        String resultat = AdminKontoController.endreKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void endre_FeilMedLoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.endreKonto(any(Konto.class))).thenReturn("Feil");

        String resultat = AdminKontoController.endreKonto(konto1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = AdminKontoController.endreKonto(konto1);

        assertNull(resultat);
    }


    // slett

    @Test
    public void slett_LoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.slettKonto(anyString())).thenReturn("OK");

        String resultat = AdminKontoController.slettKonto("01010110523");

        assertEquals("OK", resultat);
    }

    @Test
    public void slett_FeilMedLoggetInn() {
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repo.slettKonto(anyString())).thenReturn("Feil");

        String resultat = AdminKontoController.slettKonto("01010110523");

        assertEquals("Feil", resultat);
    }

    @Test
    public void slett_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        String resultat = AdminKontoController.slettKonto("01010110523");

        assertNull(resultat);
    }
}
