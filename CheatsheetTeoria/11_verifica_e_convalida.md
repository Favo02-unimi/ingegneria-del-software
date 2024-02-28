Verifica e convalida:
- verifica: confronto del software con le specifiche formali prodotte dall'analista
- convalida: confronto del software con i requisiti informali posti dal committente

Errori:
- malfunzionamento: scostamento dal corretto funzionamento del programma, dal punto di vista esterno (senza guardare il codice)
- difetto/anomalia: punto del codice che causa il malfunzionamento, condizione necessaria (ma non sufficiente) per la verifica di un malfunzionamento
- sbaglio: errore umano, che causa un difetto

## Tecniche e metodi

- tecniche statiche: basate sull'analisi degli elementi sintattici del codice (senza eseguirlo)
- tecniche dinamiche: basate sull'esecuzione del programma

Confronto tra tecniche:
- estrema semplificazione delle proprietà (**lato statico**): si dimostra la correttezza semplificando le proprietà (ad esempio quando si vuole dimostrare che un puntatore viene usato nel modo corretto si controlla solo che non sia null)
- estrema inaccuratezza pessimistica: se non è possibile dimostrare l'assenza di un problema assumo che sia presente
- estrema inaccuratezza ottimistica (**lato dinamico**): se non è possibile dimostrare la presenza di un problema assumo che non sia presente

Metodi:
- metodi formali: cercano di dimostrare l'assenza
	- analisi del data flow
	- dimostrazione di correttezza delle specifiche logiche
- testing:
	- white box: si ha accesso al codice
	- black box: non si ha accesso al codice
	- gray box: non si ha accesso al codice ma si ha un'idea ad alto livello della struttura
- debugging: dato un malfunzionamento riproducibile, il debugging permette di localizzare le anomalie che lo causano
