Test driven development:
- tecnica di progettazione che mira a far emergere dal basso il design più semplice
- test-first + baby steps
- feedback istantaneo
- rosso, verde, refactoring

Fondamenti XP (progetto Chrysler, anni '90):
- variabili dello sviluppo di software:
	- **portata**: quantità di feature da realizzare (mutevole)
	- **tempo**: tempo che si può dedicare al progetto
	- **qualità**: qualità del progetto che si vuole ottenere (correttezza e affidabilità), idealmente sarebbe costante (sempre il massimo)
	- **costo**: risorse che si possono impiegare
- non sono indipendenti tra di loro, lavorare su bilanciamento tra di esse (soprattutto costo/tempo)
- il costo è orario, il tempo disponibile non è fisso, quindi l'unica variabile davvero variabile è il tempo
- approccio incrementale, tante piccole consegne

Principi XP:
- feedback rapido
- presumere semplicità (non anticipare il cambiamento)
- accettare il cambiamento (no immutabilità)
- modifica incrementale
- lavoro di qualità

Tecniche XP:
- **planning game**
- **brevi cicli di rilascio**
- **uso di una metafora**
- **presumere la semplicità** (KISS, once and one only)
- **testing** (di accettazione e di unità)
- **refactoring**
- **pair programming**
- **proprietà collettiva**
- **integrazione continua**
- **settimana da 40 ore**
- **cliente sul posto**
- **standard di codifica**
- **they're just rules**

Planning game:
- **planning poker**
	- user story
	- ognuno valuta contemporaneamente la user story
	- i due estremi discutono, facendo convergere
- **team estimation game**
	- user story
	- si dispone una story in una colonna o se ne sposta una (motivando lo spostamento)
	- si sceglie il tempo per una
	- altro round di spostamenti
	- scala il tempo per tutte le colonne

Quando non usare XP:
- ambiente non lo permette: team dislocati in diverse località
- barriere manageriali: team troppo grandi
- barriere tecnologiche: non è possibile integrare tutto continuamente
- troppi stakeholders in contrasto tra loro
- dove consegna incrementale non ha senso

Critiche all'XP:
- sottovalutazione dell'up-front: la progettazione non può essere fatta in modo totalmente incrementale
- sopravvalutazione delle user stories: sono troppo specifiche per sostituire i requisiti
- mancanza delle dipendenza tra user stories
- il TDD può portare ad una visione troppo ristretta
- cross functional team: i team sono troppo disomogenei
