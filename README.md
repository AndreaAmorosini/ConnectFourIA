# ConnectFourIA
Implementazione del gioco Connect Four (Forza quattro) in java con modulo di IA tramite algoritmo Minimax con potatura Alpha e Beta.

ChangeLog

29/01/2021 
Implementazione iniziale dei metodi per l'ìa a supporto dell'algoritmo minimax

30/01/2021 
Implementazione dei metodi Minimax (contenente l'algoritmo vero e proprio del minimax con potatura alpha e beta) e scorePosition per l'assegnazione di uno score alla situazione attuale della board.
Inoltre aggiunta della libreria javatuple per la gestione delle coppie di valori.

31/01/2021
Implementazione iniziale del main per lo svolgimento del gioco e del metodo printBoard per la visualizzazione della tabella di gioco.
Aggiustata l'implementazione per la copia della tabella di gioco in posizione temporanea per i calcoli di minimax

01/02/2021
Fix del metodo isValidLocation che non considerava le righe oltre l'ultima, aggiunta di uno snippet per ricominciare la partita senza dover riavviare il programma e prova finale del comportamento dell'IA nel gioco.

08/02/2021
Aggiunta la funzionalità per impostare le dimensioni della griglia di gioco,
aggiunto il controllo sull'input utente.

11/02/2021
Implementazione della mossa iniziale consistente nel posizionare la pedina del bot IA nella colonna centrale della griglia per avere più possibilità di combinazioni.
