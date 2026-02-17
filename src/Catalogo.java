import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Gestisce la collezione di libri della biblioteca.
 *
 * <p>Funzionalità <b>IMPLEMENTATE</b>:
 * <ul>
 *   <li>{@link #aggiungiLibro(Libro)}     – aggiunge un libro al catalogo</li>
 *   <li>{@link #cercaPerIsbn(String)}     – cerca un libro per ISBN</li>
 *   <li>{@link #getTuttiILibri()}         – restituisce tutti i libri</li>
 * </ul>
 *
 * <p>Funzionalità <b>DA IMPLEMENTARE</b> (cerca i commenti TODO):
 * <ul>
 *   <li>{@link #cercaPerAutore(String)}   – branch: {@code feature/cerca-autore}</li>
 *   <li>{@link #getLibriDisponibili()}    – branch: {@code feature/libri-disponibili}</li>
 *   <li>{@link #getLibriInPrestito()}     – branch: {@code feature/libri-in-prestito}</li>
 *   <li>{@link #rimuoviLibro(String)}     – branch: {@code feature/rimuovi-libro}</li>
 * </ul>
 */
public class Catalogo {

    private final List<Libro> libri = new ArrayList<>();

    // ===================================================================
    //  FUNZIONALITÀ IMPLEMENTATE
    // ===================================================================

    /**
     * Aggiunge un libro al catalogo.
     *
     * @param libro il libro da aggiungere (non null)
     * @throws IllegalArgumentException se {@code libro} è null o il suo ISBN è già presente
     */
    public void aggiungiLibro(Libro libro) {
        if (libro == null) throw new IllegalArgumentException("Il libro non può essere null");
        if (cercaPerIsbn(libro.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN già presente: " + libro.getIsbn());
        }
        libri.add(libro);
    }

    /**
     * Cerca un libro tramite ISBN (case-insensitive, ignora spazi).
     *
     * @param isbn codice ISBN da cercare
     * @return {@link Optional} contenente il libro, oppure vuoto se non trovato
     */
    public Optional<Libro> cercaPerIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) return Optional.empty();
        String isbnNorm = isbn.trim();
        return libri.stream()
                    .filter(l -> l.getIsbn().equalsIgnoreCase(isbnNorm))
                    .findFirst();
    }

    /**
     * Restituisce una vista non modificabile di tutti i libri nel catalogo.
     */
    public List<Libro> getTuttiILibri() {
        return Collections.unmodifiableList(libri);
    }

    // ===================================================================
    //  FUNZIONALITÀ DA IMPLEMENTARE
    // ===================================================================

    /**
     * Restituisce tutti i libri di un dato autore (confronto case-insensitive,
     * ricerca per sottostringa: "Eco" trova anche "Umberto Eco").
     *
     * @param autore stringa da cercare nel campo autore
     * @return lista (eventualmente vuota) di libri che corrispondono
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/cerca-autore}.
     * <p>Suggerimento: usa {@code stream().filter(...).toList()} oppure un ciclo for.
     * Ricorda il confronto case-insensitive con {@code toLowerCase()}.
     */
    public List<Libro> cercaPerAutore(String autore) {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("cercaPerAutore() non ancora implementato");
    }

    /**
     * Restituisce la lista dei libri attualmente disponibili per il prestito.
     *
     * @return lista (eventualmente vuota) dei libri con {@code disponibile == true}
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/libri-disponibili}.
     * <p>Suggerimento: filtra {@code libri} con {@code l.isDisponibile()}.
     */
    public List<Libro> getLibriDisponibili() {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("getLibriDisponibili() non ancora implementato");
    }

    /**
     * Restituisce la lista dei libri attualmente in prestito.
     *
     * @return lista (eventualmente vuota) dei libri con {@code disponibile == false}
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/libri-in-prestito}.
     * <p>Suggerimento: è il complementare di {@link #getLibriDisponibili()}.
     */
    public List<Libro> getLibriInPrestito() {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("getLibriInPrestito() non ancora implementato");
    }

    /**
     * Rimuove un libro dal catalogo dato il suo ISBN.
     *
     * @param isbn ISBN del libro da rimuovere
     * @return {@code true} se il libro è stato trovato e rimosso, {@code false} altrimenti
     * @throws IllegalStateException se il libro esiste ma è attualmente in prestito
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/rimuovi-libro}.
     * <p>Suggerimenti:
     * <ol>
     *   <li>Usa {@link #cercaPerIsbn(String)} per trovare il libro.</li>
     *   <li>Se non trovato, ritorna {@code false}.</li>
     *   <li>Se trovato ma {@code !libro.isDisponibile()}, lancia {@link IllegalStateException}.</li>
     *   <li>Altrimenti rimuovilo da {@code libri} e ritorna {@code true}.</li>
     * </ol>
     */
    public boolean rimuoviLibro(String isbn) {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("rimuoviLibro() non ancora implementato");
    }

    // ===================================================================
    //  UTILITY
    // ===================================================================

    /** Numero totale di libri nel catalogo. */
    public int getTotaleLibri() { return libri.size(); }
}
