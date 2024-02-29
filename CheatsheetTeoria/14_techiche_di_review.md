
Tecniche di review: incentrate sul trovare errori semantici, di incomprensione delle specifiche:
- **bebugging**: inserimento di errori appositamente, per incentivare il team di test a continuare a cercare errori fino a quando non si trovano tutti
- **analisi mutazionale**: _descritta meglio sotto_
- **object oriented testing**: _descritta meglio sotto_
- **testing funzionale**: _descritta meglio sotto_
- **software inspection**: _descritta meglio sotto_
- **modelli statistici**: studio statistico sulla distribuzione di errori nel codice, prevedere gli errori in una porzione di codice
- **debugging**: localizzare e rimuovere anomalie che sono la causa di malfunzionamenti riproducibili, utilizzo di tool come debugger simbolico

### Analisi mutazionale

Vengono generati dei mutati, ovvero dei programmi leggermente modificati rispetto al programma $P$ originale. Su questi mutanti vengono eseguiti i test, se i test non hanno successo (anche i mutanti sono corretti), allora vuol dire che quel test non era abbastanza approfondito

**Criterio di copertura dei mutati**: per ogni mutante $\pi \in \Pi$ esiste almeno un caso di test $t \in T$ la cui esecuzione produca per $\pi$ un risultato diverso da quello di $P$

**Generazione dei mutanti**: gli operatori mutanti sono funzioni che modificano la semantica del programma senza causare errori di compilazione, operano su:
- costanti e variabili
- operatori ed espressioni
- comandi

**Automazione**: dato che l'analisi mutazionale non misura la correttezza del programma di partenza, ma la bontà dei test, non è necessario conoscere il risultato corretto di un certo input, eliminando la necessità di un oracolo (o di hardcodare i risultati), migliorando l'automazione

### Object Oriented Testing

Per via dell'ereditarietà e del collegamento dinamico, testare programmi OOP può essere problematico. I metodi ereditati da una superclasse vanno sempre testati, anche quando sono già stati testati in precedenza.

Criterio di copertura della classe: si rappresentano gli stati raggiungibili attraverso una macchina a stati, con come transizioni i metodi che modificano lo stato
- coprire tutti i nodi (tutti gli stati)
- coprire tutti gli archi (tutti i metodi)

### Testing funzionale

Verifica del comportamento del programma dal punto di vista dell'utente finale, senza considerare il comportamento interno (black box)

- testing delle interfacce: testing dei vari sotto-insiemi del programma che permettono l'interoperabilità dei componenti
	- invocazione di parametri: rilevare passaggi errati di parametri
	- condivisione di memoria
	- metodi sincroni: errori di tempistica tra le chiamate dei componenti
	- passaggio di messaggi
- classi di equivalenza: valori che dato in pasto al programma forniscono un risultato diverso ma nello stesso modo, in modo da testare tutti i comportamenti standard
- test di frontiera: test dei casi limite
- category partition: metodologia che permette di identificare le classi di equivalenza a partire dalle specifiche del problema
- object orientation e testing funzionale: si possono usare strumenti come diagrammi UML

### Code inspection

Tecniche manuali di ispezione del codice da parte di un umano:
- **Fagan code inspection**: obiettivo individuare i difetti SENZA correggerli, seguendo delle checklist
- automazione: strumenti automatici, come
	- controlli di formattazione
	- riferimenti
	- aiuti alla comprensione del codice
	- annotazioni e comunicazioni
	- guida al processi e rinforzo
- pro e contro:
	- si migliora con l'esperienza
	- lo sviluppo non è più incrementale, il codice potrebbe essere cambiato troppo
- gruppi di test autonomi: chi sviluppa un pezzo di codice è la persona meno adatta a testarlo
	- rotazione del personale