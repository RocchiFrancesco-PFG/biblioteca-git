# Biblioteca – Esercitazione Git Remote

> Progetto base per l'esercitazione su `clone`, `push`, `pull` e `sync` con repository remoti.

---

## Descrizione

**Biblioteca** è un'applicazione Java per la gestione di un catalogo libri con prestiti.
Alcune funzionalità sono già implementate; il tuo compito è completare le restanti
lavorando con il repository remoto su GitHub.

### Funzionalità implementate ✅

| Classe | Metodo | Cosa fa |
|---|---|---|
| `Catalogo` | `aggiungiLibro()` | Aggiunge un libro al catalogo |
| `Catalogo` | `cercaPerIsbn()` | Cerca un libro per ISBN |
| `Catalogo` | `getTuttiILibri()` | Restituisce tutti i libri |
| `GestorePrestiti` | `prestaLibro()` | Registra un prestito |

### Funzionalità da implementare 🔧

| Classe | Metodo | Branch suggerito |
|---|---|---|
| `Catalogo` | `cercaPerAutore()` | `feature/cerca-autore` |
| `Catalogo` | `getLibriDisponibili()` | `feature/libri-disponibili` |
| `Catalogo` | `getLibriInPrestito()` | `feature/libri-in-prestito` |
| `Catalogo` | `rimuoviLibro()` | `feature/rimuovi-libro` |
| `GestorePrestiti` | `restituisciLibro()` | `feature/restituzione` |
| `GestorePrestiti` | `getPrestitiScaduti()` | `feature/prestiti-scaduti` |

---

## Requisiti

- Java 17 o superiore

---

## Flusso di lavoro Git

### 1. Clona il repository
```bash
git clone <URL_del_repository>
cd biblioteca-git
```

### 2. Esplora il codice e i test
Esegui il main e prova ad eseguire i metodi di test.

### 3. Scegli una funzionalità e crea un branch
```bash
git switch -c feature/cerca-autore
```

### 4. Implementa il metodo
Apri `Catalogo.java` o `GestorePrestiti.java`, cerca il commento `TODO`
e sostituisci il `throw new UnsupportedOperationException(...)` con il codice reale,
seguendo i suggerimenti nel Javadoc.

### 5. Commit e push
```bash
git add src/
git commit -m "feat: implementa cercaPerAutore"
git push origin feature/cerca-autore
```

### 6. Sincronizzati prima del merge
```bash
git fetch origin
git log --oneline --all --graph
git switch main
git pull origin main
```

---

## Domande di riflessione

1. Cosa succede se due studenti modificano lo stesso metodo in modo diverso?
2. Qual è la differenza tra `git fetch` e `git pull`?
3. Perché è buona pratica fare `pull` prima di fare `push`?
4. Come si risolve un conflitto di merge in IntelliJ / VS Code?
