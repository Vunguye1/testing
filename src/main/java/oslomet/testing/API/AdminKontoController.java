package oslomet.testing.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/adminKonto")
public class AdminKontoController {
    @Autowired
    AdminRepository repository;

    @Autowired
    private Sikkerhet sjekk;

    @GetMapping("/hentAlle") // x
    public List<Konto> hentAlleKonti() {
        String personnummer = sjekk.loggetInn();
        if (personnummer!=null) {
            return repository.hentAlleKonti();
        }
        return null;
    }

    @PostMapping("/registrer") // x
    public String registrerKonto(@RequestBody Konto konto) {
        String personnummer = sjekk.loggetInn();
        if (personnummer!=null) {
            String retur = repository.registrerKonto(konto);
            return retur;
        }
        return "Ikke innlogget";
    }

    @PostMapping("/endre") // x
    public String endreKonto(@RequestBody Konto konto) {
        String personnummer = sjekk.loggetInn();
        if (personnummer!=null) {
            return repository.endreKonto(konto);
        }
        return "Ikke innlogget";
    }

    @GetMapping("/slett")
    public String slettKonto(String kontonummer) {
        String personnummer = sjekk.loggetInn();
        if (personnummer!=null) {
           return repository.slettKonto(kontonummer);
        }
        return "Ikke innlogget";
    }

}
