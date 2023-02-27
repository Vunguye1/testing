package oslomet.testing;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;


    // Kall på session.setAttribute når man skal sjekke session i if setninger

    @Before
    public void initSession() {
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    @Test
    public void test_sjekkLoggInn() {
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");

        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");

        assertEquals("OK",resultat);
    }

    @Test
    public void test_FeilsjekkLoggInn() {
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil");

        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");

        assertEquals("Feil i personnummer eller passord",resultat);
    }

    @Test
    public void test_loggUt() {

        sikkerhetsController.loggUt();
        assertEquals(null,session.getValue("Innlogget"));


    }

    @Test
    public void test_LoggInnAdmin() {

        session.setAttribute("Innlogget", "Admin");

        String resultat = sikkerhetsController.loggInnAdmin("Admin","Admin");

        assertEquals("Logget inn",resultat);
    }

    @Test
    public void test_FeilLoggInnAdmin() {

        session.setAttribute("Innlogget", null);

        String resultat = sikkerhetsController.loggInnAdmin("null","null");

        assertEquals("Ikke logget inn",resultat);
    }

    @Test
    public void test_loggetInn() {

        session.setAttribute("Innlogget","12345678901");

        String resultat = sikkerhetsController.loggetInn();

        assertEquals("12345678901", resultat);
    }

    @Test
    public void test_FeilloggetInn() {

        session.setAttribute("Innlogget",null);

        String resultat = sikkerhetsController.loggetInn();

        assertNull(resultat);
    }

}
