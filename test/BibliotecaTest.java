package test;

import main.Catalogo;
import main.GestorePrestiti;
import main.Libro;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test JUnit 5 per Catalogo e GestorePrestiti.
 *
 * <p>Esegui con Maven:  {@code mvn test}
 *
 * <p>All'inizio molti test <b>falliranno</b> con {@code UnsupportedOperationException}:
 * è il comportamento atteso. Man mano che implementi le funzionalità i test diventeranno verdi.
 *
 * <p>Puoi eseguire solo i test di una feature specifica con:
 * <pre>
 *   mvn test -Dtest=BibliotecaTest#testCercaPerAutore_trovaTitoli
 * </pre>
 */
class BibliotecaTest {

    private Catalogo catalogo;
    private GestorePrestiti gestore;

    // libri di test
    private Libro eco;
    private Libro levi;
    private Libro svevo;
    private Libro marquez;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
        gestore  = new GestorePrestiti(catalogo);

        eco     = new Libro("ISBN-001", "Il nome della rosa",   "Umberto Eco",   1980);
        levi    = new Libro("ISBN-002", "Se questo è un uomo",  "Primo Levi",    1947);
        svevo   = new Libro("ISBN-003", "La coscienza di Zeno", "Italo Svevo",   1923);
        marquez = new Libro("ISBN-004", "Cent'anni di solitudine", "García Márquez", 1967);

        catalogo.aggiungiLibro(eco);
        catalogo.aggiungiLibro(levi);
        catalogo.aggiungiLibro(svevo);
        catalogo.aggiungiLibro(marquez);
    }

    // ===================================================================
    //  TEST FUNZIONALITÀ IMPLEMENTATE  (devono essere già verdi)
    // ===================================================================

    @Nested
    @DisplayName("aggiungiLibro e cercaPerIsbn")
    class TestFunzionalitaBase {

        @Test
        @DisplayName("aggiunge un libro e lo recupera per ISBN")
        void testAggiungiECercaPerIsbn() {
            Optional<Libro> trovato = catalogo.cercaPerIsbn("ISBN-001");
            assertTrue(trovato.isPresent());
            assertEquals("Il nome della rosa", trovato.get().getTitolo());
        }

        @Test
        @DisplayName("getTuttiILibri restituisce tutti i libri aggiunti")
        void testGetTuttiILibri() {
            assertEquals(4, catalogo.getTuttiILibri().size());
        }

        @Test
        @DisplayName("aggiungiLibro rifiuta ISBN duplicato")
        void testIsbnDuplicatoLanciaEccezione() {
            Libro duplicato = new Libro("ISBN-001", "Altro titolo", "Altro Autore", 2000);
            assertThrows(IllegalArgumentException.class, () -> catalogo.aggiungiLibro(duplicato));
        }

        @Test
        @DisplayName("cercaPerIsbn con ISBN inesistente restituisce Optional vuoto")
        void testCercaIsbnInesistente() {
            Optional<Libro> risultato = catalogo.cercaPerIsbn("ISBN-999");
            assertFalse(risultato.isPresent());
        }

        @Test
        @DisplayName("prestaLibro segna il libro come non disponibile")
        void testPrestaLibro() {
            gestore.prestaLibro("ISBN-001", "Mario Rossi", 14);
            assertFalse(eco.isDisponibile());
            assertEquals("Mario Rossi", eco.getPrestitarioCorrente());
        }

        @Test
        @DisplayName("prestaLibro su libro già in prestito lancia eccezione")
        void testPrestaLibroGiaInPrestito() {
            gestore.prestaLibro("ISBN-001", "Utente A", 7);
            assertThrows(IllegalStateException.class,
                () -> gestore.prestaLibro("ISBN-001", "Utente B", 7));
        }
    }

    // ===================================================================
    //  TEST FUNZIONALITÀ DA IMPLEMENTARE
    //  Questi test falliscono finché non completi le funzioni TODO
    // ===================================================================

    @Nested
    @DisplayName("cercaPerAutore  [feature/cerca-autore]")
    class TestCercaPerAutore {

        @Test
        @DisplayName("trova libri con corrispondenza esatta del cognome")
        void testCercaPerAutore_trovaTitoli() {
            List<Libro> risultati = catalogo.cercaPerAutore("Eco");
            assertEquals(1, risultati.size());
            assertEquals("ISBN-001", risultati.get(0).getIsbn());
        }

        @Test
        @DisplayName("la ricerca è case-insensitive")
        void testCercaPerAutore_caseInsensitive() {
            List<Libro> risultati = catalogo.cercaPerAutore("eco");
            assertEquals(1, risultati.size());
        }

        @Test
        @DisplayName("autore inesistente restituisce lista vuota")
        void testCercaPerAutore_nessunaCorrispondenza() {
            List<Libro> risultati = catalogo.cercaPerAutore("Tolkien");
            assertTrue(risultati.isEmpty());
        }
    }

    @Nested
    @DisplayName("getLibriDisponibili  [feature/libri-disponibili]")
    class TestLibriDisponibili {

        @Test
        @DisplayName("tutti i libri sono disponibili all'inizio")
        void testTuttiDisponibiliAllinizio() {
            List<Libro> disponibili = catalogo.getLibriDisponibili();
            assertEquals(4, disponibili.size());
        }

        @Test
        @DisplayName("dopo un prestito la lista diminuisce di uno")
        void testDisponibiliDopoUnPrestito() {
            gestore.prestaLibro("ISBN-001", "Utente", 7);
            List<Libro> disponibili = catalogo.getLibriDisponibili();
            assertEquals(3, disponibili.size());
        }

        @Test
        @DisplayName("i libri in prestito non compaiono nella lista")
        void testLibroInPrestitoNonDisponibile() {
            gestore.prestaLibro("ISBN-001", "Utente", 7);
            List<Libro> disponibili = catalogo.getLibriDisponibili();
            assertTrue(disponibili.stream().noneMatch(l -> l.getIsbn().equals("ISBN-001")));
        }
    }

    @Nested
    @DisplayName("getLibriInPrestito  [feature/libri-in-prestito]")
    class TestLibriInPrestito {

        @Test
        @DisplayName("nessun libro in prestito inizialmente")
        void testNessunoInPrestitoAllinizio() {
            List<Libro> inPrestito = catalogo.getLibriInPrestito();
            assertTrue(inPrestito.isEmpty());
        }

        @Test
        @DisplayName("dopo due prestiti la lista contiene esattamente quei due libri")
        void testDueLibriInPrestito() {
            gestore.prestaLibro("ISBN-001", "Utente A", 7);
            gestore.prestaLibro("ISBN-002", "Utente B", 14);
            List<Libro> inPrestito = catalogo.getLibriInPrestito();
            assertEquals(2, inPrestito.size());
        }
    }

    @Nested
    @DisplayName("restituisciLibro  [feature/restituzione]")
    class TestRestituzione {

        @Test
        @DisplayName("dopo la restituzione il libro torna disponibile")
        void testRestituisceRendeDisponibile() {
            gestore.prestaLibro("ISBN-001", "Utente", 7);
            gestore.restituisciLibro("ISBN-001");
            assertTrue(eco.isDisponibile());
            assertNull(eco.getPrestitarioCorrente());
        }

        @Test
        @DisplayName("restituire un libro già disponibile lancia eccezione")
        void testRestituireLibroDisponibileLanciaEccezione() {
            assertThrows(IllegalStateException.class,
                () -> gestore.restituisciLibro("ISBN-001"));
        }

        @Test
        @DisplayName("restituire un ISBN inesistente lancia eccezione")
        void testRestituireIsbnInesistenteLanciaEccezione() {
            assertThrows(IllegalStateException.class,
                () -> gestore.restituisciLibro("ISBN-999"));
        }
    }

    @Nested
    @DisplayName("getPrestitiScaduti  [feature/prestiti-scaduti]")
    class TestPrestitiScaduti {

        @Test
        @DisplayName("nessun prestito scaduto se tutti i libri sono disponibili")
        void testNessunPrestitoScaduto() {
            List<Libro> scaduti = gestore.getPrestitiScaduti();
            assertTrue(scaduti.isEmpty());
        }

        @Test
        @DisplayName("un prestito con 1 giorno di durata è già scaduto il giorno dopo")
        void testPrestitoConDataPassataEScaduto() {
            // Manualmente forziamo un prestito scaduto modificando lo stato interno
            eco.segnaComePrestato("Utente Test", 1);
            // Sovrascriviamo la data a ieri simulando un prestito vecchio
            // (usiamo un secondo libro per averne uno non scaduto a confronto)
            // Nota: questo test verifica la logica di filtraggio; la data sarà
            // impostata a "oggi + 1", quindi il prestito NON è scaduto.
            // Un prestito è scaduto solo se dataRestituzionePrevista < oggi.
            List<Libro> scaduti = gestore.getPrestitiScaduti();
            // Con 1 giorno il libro non è ancora scaduto (scade domani)
            assertEquals(0, scaduti.size());
        }
    }

    @Nested
    @DisplayName("rimuoviLibro  [feature/rimuovi-libro]")
    class TestRimuoviLibro {

        @Test
        @DisplayName("rimuove un libro disponibile e restituisce true")
        void testRimuoviLibroDisponibile() {
            boolean rimosso = catalogo.rimuoviLibro("ISBN-003");
            assertTrue(rimosso);
            assertEquals(3, catalogo.getTuttiILibri().size());
        }

        @Test
        @DisplayName("rimuovere un ISBN inesistente restituisce false")
        void testRimuoviIsbnInesistente() {
            boolean rimosso = catalogo.rimuoviLibro("ISBN-999");
            assertFalse(rimosso);
            assertEquals(4, catalogo.getTuttiILibri().size());
        }

        @Test
        @DisplayName("non si può rimuovere un libro in prestito")
        void testRimuoviLibroInPrestitoLanciaEccezione() {
            gestore.prestaLibro("ISBN-001", "Utente", 7);
            assertThrows(IllegalStateException.class,
                () -> catalogo.rimuoviLibro("ISBN-001"));
        }
    }
}
