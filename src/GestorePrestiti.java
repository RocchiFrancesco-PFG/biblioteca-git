import java.util.List;

/**
 * Gestisce le operazioni di prestito e restituzione dei libri.
 *
 * <p>Funzionalità <b>IMPLEMENTATE</b>:
 * <ul>
 *   <li>{@link #prestaLibro(String, String, int)} – registra un prestito</li>
 * </ul>
 *
 * <p>Funzionalità <b>DA IMPLEMENTARE</b> (cerca i commenti TODO):
 * <ul>
 *   <li>{@link #restituisciLibro(String)}  – branch: {@code feature/restituzione}</li>
 *   <li>{@link #getPrestitiScaduti()}      – branch: {@code feature/prestiti-scaduti}</li>
 * </ul>
 */
public class GestorePrestiti {

    private final Catalogo catalogo;

    public GestorePrestiti(Catalogo catalogo) {
        if (catalogo == null) throw new IllegalArgumentException("Catalogo non può essere null");
        this.catalogo = catalogo;
    }

    // ===================================================================
    //  FUNZIONALITÀ IMPLEMENTATE
    // ===================================================================

    /**
     * Registra il prestito di un libro a un utente.
     *
     * @param isbn   ISBN del libro da prestare
     * @param utente nome dell'utente
     * @param giorni durata del prestito in giorni (deve essere > 0)
     * @throws IllegalArgumentException se ISBN o utente sono vuoti, o giorni <= 0
     * @throws IllegalStateException    se il libro non esiste o non è disponibile
     */
    public void prestaLibro(String isbn, String utente, int giorni) {
        validaParametriPrestito(isbn, utente, giorni);

        Libro libro = trovaoFallisci(isbn);
        libro.segnaComePrestato(utente, giorni);

        System.out.printf("✅ Prestito registrato: \"%s\" → %s (restituzione entro %s)%n",
            libro.getTitolo(), utente, libro.getDataRestituzionePrevista());
    }

    // ===================================================================
    //  FUNZIONALITÀ DA IMPLEMENTARE
    // ===================================================================

    /**
     * Registra la restituzione di un libro.
     *
     * @param isbn ISBN del libro restituito
     * @throws IllegalArgumentException se ISBN è vuoto
     * @throws IllegalStateException    se il libro non esiste o era già disponibile
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/restituzione}.
     * <p>Suggerimenti:
     * <ol>
     *   <li>Valida che {@code isbn} non sia null/vuoto (vedi {@link #validaIsbn(String)}).</li>
     *   <li>Usa {@link #trovaoFallisci(String)} per recuperare il libro.</li>
     *   <li>Chiama {@link Libro#segnaComRestituito()} sul libro trovato.</li>
     *   <li>Stampa un messaggio di conferma.</li>
     * </ol>
     */
    public void restituisciLibro(String isbn) {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("restituisciLibro() non ancora implementato");
    }

    /**
     * Restituisce la lista dei libri il cui termine di restituzione è già scaduto
     * (data di restituzione prevista precedente a oggi).
     *
     * @return lista (eventualmente vuota) di libri in ritardo
     *
     * <p><b>TODO</b> – Implementa questo metodo nel branch {@code feature/prestiti-scaduti}.
     * <p>Suggerimenti:
     * <ol>
     *   <li>Recupera tutti i libri con {@code catalogo.getTuttiILibri()}.</li>
     *   <li>Filtra quelli che hanno {@code !isDisponibile()} e
     *       {@code getDataRestituzionePrevista().isBefore(LocalDate.now())}.</li>
     *   <li>Ritorna la lista filtrata.</li>
     * </ol>
     */
    public List<Libro> getPrestitiScaduti() {
        // TODO: sostituisci questa riga con l'implementazione reale
        throw new UnsupportedOperationException("getPrestitiScaduti() non ancora implementato");
    }

    // ===================================================================
    //  METODI HELPER (già implementati, usali nei TODO)
    // ===================================================================

    private void validaParametriPrestito(String isbn, String utente, int giorni) {
        validaIsbn(isbn);
        if (utente == null || utente.isBlank())
            throw new IllegalArgumentException("Il nome dell'utente non può essere vuoto");
        if (giorni <= 0)
            throw new IllegalArgumentException("La durata del prestito deve essere > 0 giorni");
    }

    /** Lancia {@link IllegalArgumentException} se l'ISBN è null o vuoto. */
    private void validaIsbn(String isbn) {
        if (isbn == null || isbn.isBlank())
            throw new IllegalArgumentException("ISBN non può essere vuoto");
    }

    /**
     * Cerca il libro per ISBN e, se non trovato, lancia {@link IllegalStateException}.
     *
     * @param isbn ISBN del libro da cercare
     * @return il libro trovato
     */
    private Libro trovaoFallisci(String isbn) {
        return catalogo.cercaPerIsbn(isbn)
            .orElseThrow(() -> new IllegalStateException("Nessun libro con ISBN: " + isbn));
    }
}
