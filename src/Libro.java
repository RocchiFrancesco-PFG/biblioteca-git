import java.time.LocalDate;

/**
 * Rappresenta un libro nella biblioteca.
 */
public class Libro {

    private final String isbn;
    private final String titolo;
    private final String autore;
    private final int annoPubblicazione;
    private boolean disponibile;
    private String prestitarioCorrente;   // null se il libro è disponibile
    private LocalDate dataRestituzionePrevista;

    public Libro(String isbn, String titolo, String autore, int annoPubblicazione) {
        if (isbn == null || isbn.isBlank())   throw new IllegalArgumentException("ISBN non può essere vuoto");
        if (titolo == null || titolo.isBlank()) throw new IllegalArgumentException("Titolo non può essere vuoto");
        if (autore == null || autore.isBlank()) throw new IllegalArgumentException("Autore non può essere vuoto");

        this.isbn = isbn.trim();
        this.titolo = titolo.trim();
        this.autore = autore.trim();
        this.annoPubblicazione = annoPubblicazione;
        this.disponibile = true;
        this.prestitarioCorrente = null;
        this.dataRestituzionePrevista = null;
    }

    // ------------------------------------------------------------------ getter

    public String getIsbn()               { return isbn; }
    public String getTitolo()             { return titolo; }
    public String getAutore()             { return autore; }
    public int    getAnnoPubblicazione()  { return annoPubblicazione; }
    public boolean isDisponibile()        { return disponibile; }
    public String getPrestitarioCorrente(){ return prestitarioCorrente; }
    public LocalDate getDataRestituzionePrevista() { return dataRestituzionePrevista; }

    // ------------------------------------------------------------------ stato

    /**
     * Segna il libro come prestato all'utente indicato.
     *
     * @param utente  nome dell'utente che prende in prestito il libro
     * @param giorni  numero di giorni entro cui restituire il libro
     * @throws IllegalStateException se il libro non è disponibile
     */
    public void segnaComePrestato(String utente, int giorni) {
        if (!disponibile) {
            throw new IllegalStateException(
                "Il libro \"" + titolo + "\" non è disponibile (in prestito a " + prestitarioCorrente + ")");
        }
        this.disponibile = false;
        this.prestitarioCorrente = utente;
        this.dataRestituzionePrevista = LocalDate.now().plusDays(giorni);
    }

    /**
     * Segna il libro come restituito.
     *
     * @throws IllegalStateException se il libro era già disponibile
     */
    public void segnaComRestituito() {
        if (disponibile) {
            throw new IllegalStateException("Il libro \"" + titolo + "\" era già disponibile");
        }
        this.disponibile = true;
        this.prestitarioCorrente = null;
        this.dataRestituzionePrevista = null;
    }

    // ------------------------------------------------------------------ util

    @Override
    public String toString() {
        String stato = disponibile
            ? "[DISPONIBILE]"
            : "[PRESTATO a " + prestitarioCorrente + " - restituzione entro " + dataRestituzionePrevista + "]";
        return String.format("[%s] \"%s\" di %s (%d) %s",
            isbn, titolo, autore, annoPubblicazione, stato);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        return isbn.equals(((Libro) o).isbn);
    }

    @Override
    public int hashCode() { return isbn.hashCode(); }
}
