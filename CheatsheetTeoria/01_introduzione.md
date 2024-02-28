## Ingegneria del software

Approccio ingegneristico:
- **target**: obiettivo
- **metric**: misurare qualità (vicinanza al target)
- **method, process, tool**: processi per avvicinarsi all'obiettivo
- **measurements**: misurare metrica (avvicinati o allontanati all'obiettivo)

Problemi: la più grande fonte di problemi sono le **persone** e la **comunicazione**
- tra programmatore e cliente
- tra programmatore e programmatore

## Qualità del software

Requisiti e specifiche:
- **requisiti**: quello che il cliente vuole che il software faccia
- **specifiche**: formalizzazione dei requisiti da parte del programmatore

Qualità: proprietà desiderabili
- **esterne** (lato cliente)
- **interne** (lato sviluppatore)

Qualità del software:
- **funzionare**:
	- **correttezza**: soddisfa requisiti funzionali
	- **affidabilità**: quanto ci si può fidare del funzionamento (importante stabilire range di tollerabilità)
	- **robustezza**: comportamento in circostanze non previste dai requisiti
- **essere bello**:
	- **usabilità**: facile e intuitivo da usare lato utente (dispendioso da verificare)
	- **prestazioni ed efficienza**: efficiente (come usa le risorse internamente) e prestante (quanto è veloce esternamente)
	- **verificabilità**: dimostrare correttezza e performance (leggibilità importante), metodi formali o informali (testing)
- **farmi diventare ricco**:
	- **riusabilità**: scrivere una volta in modo adattabile e usare tante volte
	- **manutenibilità**: modifiche post-rilascio
		- **riparabilità**: risoluzione errori (fix)
		- **evolvibilità**: aggiungere features
	- **perfettibilità**: migliorare senza toccare le funzionalità richieste (refactoring)

**Debito tecnico**: problemi lasciati indietro che andranno risolti

## Qualità del processo

Qualità del processo:
- funzionare (**robustezza**): resistere agli imprevisti (mancanza di personale, ...)
- essere bello (**produttività**): metrica difficile da misurare
- farmi diventare ricco (**tempismo**):
	- **scadenze**: tempi richiesti dal cliente (conviene sviluppo incrementale)
	- **volatilità dei requisiti**: tenere aggiornati i requisiti per evitare di sviluppare cose non più richieste
	- **cogliere l'attimo**: realizzare il prodotto quando il mercato lo richiede

Processo di produzione software:
- riconoscere problematiche:
	- requisiti cambiano spesso
	- produrre software non è solo scrivere codice
	- problemi di comunicazione
	- necessità di essere rigorosi (metodi formali riducono errori di progettazione, ma richiedono tempo)
	- tanti aspetti da considerare (aspect oriented programming)
- attività necessarie:
	- precedenze temporali
	- chi deve fare cosa

Modello **code and fix**: inefficace per progetti complessi e con cliente e sviluppatore non sovrapposti (sono persone diverse)
- scrivere codice
- correggere errori

Fasi ciclo di vita del software:
- **studio di fattibilità**:
	- decidere se far partire il progetto (se ne vale la pena)
	- documento in linguaggio naturale (destinato ai manager)
		- studio di diversi scenari di realizzazione (con dettagli tecnici)
		- stima dei costi, tempi e risorse necessarie
- **analisi e specifica dei requisiti**:
	- documento di specifica (in forma contrattuale)
		- comprendere dominio applicativo del prodotto: dialogo con cliente
		- identificare gli stakeholders: figure interessate al progetto
		- funzionalità richieste: specifiche dal punto di vista del cliente
		- dizionario comune tra cliente e sviluppatore
		- altre qualità richieste dal cliente: specifiche non funzionali
	- piano di test: certificare correttezza lavoro (e farsi pagare)
	- manuale utente o maschere di interazione: vista lato cliente
	- modelli del documento di specifica:
		- modelli descrittivi: rappresentano logicamente il sistema
		- modelli operazionali: rappresentano il sistema tramite modelli eseguibili
- **progettazione** (design):
	- documento di specifica di progetto: architettura software
		- scegliere architettura software (linguaggio, ...)
		- scomporre in moduli o oggetti gli incarichi (object oriented design)
		- identificare patterns: problemi noti già risolti
- **programmazione e test di unità**:
	- sviluppo moduli (indipendenti, black box) e relativi test unitari
- **integrazione e test di sistema**:
	- integrazione moduli: in modo incrementale
	- test di integrazione
	- alpha testing: test completo in condizioni realistiche
- **consegna, installazione e manutenzione**:
	- beta testing: test ad un gruppo ristretto di utenti
	- consegna e deployment: installazione
	- manutenzione:
		- correttiva: risolvere errori
		- adattiva: aggiungere features
		- perfettiva: refactoring senza modificare le features
- altre attività:
	- documentazione: attività trasversale
	- verifica e controllo qualità: review e ispezioni
	- gestione del processo: aspetti "burocratici" e "logistici" del processo
	- gestione delle configurazioni: gestione risorse non appartenenti ad un singolo progetto (librerie condivise, ...)
