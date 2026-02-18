/**
 * Punto di ingresso dell'applicazione Biblioteca.
 *
 * <p>Esegui con:
 * <pre>
 *   javac -d out src/main/java/biblioteca/*.java
 *   java  -cp out biblioteca.Main
 * </pre>
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Benvenuti nella biblioteca del Prof. Rocchi!");

        // --- Costruzione catalogo demo --------------------------------
        Catalogo catalogo = new Catalogo();
        GestorePrestiti gestore = new GestorePrestiti(catalogo);

        catalogo.aggiungiLibro(new Libro("978-88-06-22715-0", "Il nome della rosa",      "Umberto Eco",        1980));
        catalogo.aggiungiLibro(new Libro("978-88-07-88225-4", "Se questo è un uomo",     "Primo Levi",         1947));
        catalogo.aggiungiLibro(new Libro("978-88-04-68068-5", "La coscienza di Zeno",    "Italo Svevo",        1923));
        catalogo.aggiungiLibro(new Libro("978-88-17-10963-2", "Cent'anni di solitudine", "Gabriel G. Márquez", 1967));
        catalogo.aggiungiLibro(new Libro("978-88-06-18866-2", "Il Gattopardo",           "Giuseppe T. di Lampedusa", 1958));

        // --- Demo funzionalità implementate --------------------------
        System.out.println("=== CATALOGO COMPLETO ===");
        catalogo.getTuttiILibri().forEach(System.out::println);

        System.out.println("\n=== CERCA PER ISBN ===");
        catalogo.cercaPerIsbn("978-88-06-22715-0")
                .ifPresentOrElse(
                    l -> System.out.println("Trovato: " + l),
                    () -> System.out.println("Non trovato"));

        System.out.println("\n=== PRESTITO ===");
        gestore.prestaLibro("978-88-06-22715-0", "Mario Rossi", 14);
        gestore.prestaLibro("978-88-07-88225-4", "Giulia Bianchi", 7);

        System.out.println("\n=== CATALOGO DOPO I PRESTITI ===");
        catalogo.getTuttiILibri().forEach(System.out::println);

        // --- Demo funzionalità DA IMPLEMENTARE -----------------------
        System.out.println("\n=== FUNZIONALITÀ DA IMPLEMENTARE ===");
        eseguiConGestione("cercaPerAutore(\"Eco\")",
            () -> catalogo.cercaPerAutore("Eco").forEach(System.out::println));

        eseguiConGestione("getLibriDisponibili()",
            () -> catalogo.getLibriDisponibili().forEach(System.out::println));

        eseguiConGestione("getLibriInPrestito()",
            () -> catalogo.getLibriInPrestito().forEach(System.out::println));

        eseguiConGestione("restituisciLibro(\"978-88-06-22715-0\")",
            () -> gestore.restituisciLibro("978-88-06-22715-0"));

        eseguiConGestione("getPrestitiScaduti()",
            () -> gestore.getPrestitiScaduti().forEach(System.out::println));

        eseguiConGestione("rimuoviLibro(\"978-88-04-68068-5\")",
            () -> System.out.println("Rimosso: " + catalogo.rimuoviLibro("978-88-04-68068-5")));
    }

    /** Esegue un'azione e gestisce l'eccezione se non è ancora implementata. */
    private static void eseguiConGestione(String nome, Runnable azione) {
        System.out.print("\n→ " + nome + ": ");
        try {
            azione.run();
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  " + e.getMessage());
        }
    }
}
