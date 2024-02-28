Modelli di ciclo di vita del software: famiglie di processi di sviluppo (ognuna con diversa gestione delle fasi)

Modelli:
- **prescrittivi**: forniscono indicazioni precise da seguire
- **descrittivi**: colgono aspetti e caratteristiche di processi, ma non obbligano a seguirle in modo rigoroso

### Modelli sequenziali

passaggi in ordine prestabilito e step sequenziali (uno dopo l'altro)
- **modello a cascata**:
	- progressione lineare forzata (non si può tornare indietro)
	- ogni fase produce output (**semilavorato**), document-based (ogni fase prende in input il documento della fase precedente)
	- fasi:
		- requisiti
		- progetto (design)
		- codifica (implementation)
		- testing
		- prodotto
	- pro:
		- document-based
		- buona separazione dei compiti
		- facile pianificare i tempi
	- contro:
		- non prevede fase di manutenzione (tra le varie fasi), non si può tornare indietro
		- troppa rigidità (congelamento dei sottoprodotti)
		- monoliticità: tutta la pianificazione è orientata ad un singolo rilascio
- **modello a V** (a denti di pesce cane):
	- variante del modello a cascata, estesa la fase di testing
	- nuove attività alla fine di ogni fase di sviluppo:
		- **verifica** del semilavorato rispetto alle specifiche della fase precedente
		- **convalida** del semilavorato con le necessità del cliente

### Modelli iterativi

possibilità di ripetere alcune fasi più di una volta, ripetendole fino a quando non si ottiene un prodotto soddisfacente
- **modello a cascata con singola retroazione**:
	- permette un unico salto indietro (tornare alla fase precedente)
	- difficile pianificare il lavoro e monitorare l'avanzamento
- **modello prototipale**:
	- prototipi usa e getta (throw away): lo scopo non è consegnare, ma ricevere del feedback
		- pubblici: per capire meglio i requisiti del cliente (rischio di voler consegnare un prototipo, il quale è illeggibile e non manutenibile)
		- privati (spike): per esplorare nuovi strumenti, linguaggi e scelte tecniche

### Modelli incrementali

particolari modelli iterativi in cui nelle iterazioni è inclusa anche la consegna: tanti rilasci che costruiscono incrementalmente il programma finito.

l'iterazione comprende tutte le fasi, anche quelle di specifica e realizzazione (a differenza del modello iterativo che itera solo sullo sviluppo)

- **modello a fontana**:
	- permette di tornare alla fase iniziale in qualsiasi momento
	- non si butta tutto via, ma si risolve partendo dalla radice e passando per tutte le fasi
	- primo modello in cui sono incluse azioni dopo la consegna: manutenzione ed evoluzione (iterazione della consegna, quindi incrementale)
	- difficile stimare tempi di sviluppo
- **modelli trasformazionali**:
	- controllare tutti i passi in modo formale (estremizzare il numero di incrementi)
	- sequenza di passi di trasformazioni, dai requisiti (linguaggio informale) al prodotto finale
	- tutti i passi devono essere dimostrabili (prova formale di correttezza) e devono fornire un prototipo (diverso dal finale per efficienza e completezza) ma già "perfetto" (non migliorabile) per quel passo
- **_metamodello_ a spirale**:
	- metamodello: rappresentare e discutere altri modelli (framework)
	- attenzione posta sui rischi (risk based), sulle cose che possono andare male
	- studio di fattibilità ad ogni iterazione che necessiti di una decisione (non solo all'inizio)
		- determinazione di obiettivi, alternative e vincoli
		- valutazione alternative e identificazione rischi
		- sviluppo e verifica
		- pianificazione prossima iterazione
	- scegliere via iterativa o incrementale in base alle esigenze del progetto (costi aumentano ad ogni iterazione attorno alla spirale)
- **variante win-win modello a spirale**:
	- i rischi ad ogni fase non sono solo i rischi tecnologici (dal punto di vista sviluppatore) ma anche quelli contrattuali col cliente
	- trovare equilibrio (condizione win-win) tra cliente e sviluppatore
- **modello COTS (component off the shelf)**:
	- basato sulla riusabilità
	- disponibilità interna o sul mercato di moduli preesistenti da integrare tra loro
		- analisi dei requisiti
		- analisi dei componenti
		- modifica dei requisiti: richiesta di modifica dei requisiti per adattarsi ai componenti trovati
		- progetto del sistema col riuso dei componenti
		- sviluppo e integrazione
		- verifica del sistema

problemi modelli incrementali:
- complicato lavoro di planning
- stato di avanzamento poco visibile
- revisionare tutto ogni iterazione: overhead dovuto a troppe iterazioni (o troppe iterazioni in parallelo)
- modello pinball: l'ordine delle attività è spesso casuale (come in un flipper), il processo non è misurabile

### Metodologie Agili

modelli più prescrittivi (meno rigorosi ma più indicativi)

manifesto (Fowler e collaboratori):
- gli individui e la collaborazione tra individui è più importante di processi e strumenti
- il software che funziona è più importante della documentazione ben fatta
- la collaborazione con il cliente è più importante del contratto
- rispondere al cambiamento è più importante che seguire un piano

metodologie agili:
- **lean software**:
	- ridurre al minimo gli sprechi: rimuovere tutti i prodotti non consegnati al cliente: testing, prototipi, ...
	- riutilizzo di componenti
	- posticipare le decisioni il più possibile lasciando possibilità aperte
- **kanban**:
	- minimizzare il lavoro in corso: concentrarsi su una sola cosa alla volta (ridurre context switch)
	- organizzazione attività:
		- backlog: richieste del cliente
		- da fare: attività da fare in questa iterazione (mantenere una sola card per sviluppatore in questa sezione)
		- in esecuzione
		- in testing
		- fatte
- **scrum**:
	- l'intero team si focalizza sull'iterazione in maniera organizzata (ognuno deve sapere precisamente cosa deve fare)
	- fissare i requisiti e mantenerli stabili per tutta l'iterazione (nessuno spreco dovuto al cambio dei requisiti)
	- iterazioni brevi (2-4 settimane)
- **crystal**:
	- comunicazione osmotica: la conoscenza (e le responsabilità) vengono viste come se appartenessero all'intero team e non al singolo
	- processo più robusto, ma applicabile solo a team piccoli
- **extreme programming**:
	- incrementa quindi semplifica
	- sviluppo guidato dai test
