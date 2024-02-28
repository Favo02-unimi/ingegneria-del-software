## Definizioni

- **correttezza**: un programma è corretto per ogni dato del dominio
$$\forall d \in D, ok(P, d) \text{, in breve } ok(P,D)$$
- **test**: sottoinsieme del dominio dei dati, più stimolazioni
- **caso di test**: elemento del test, singola stimolazione
- **successo di un test**: se rileva uno o più malfunzionamenti presenti nel programma
$$successo(T, P) \Longleftrightarrow \exists t \in T, \neg ok(P,t)$$
- **test ideale**: il superamento del test implica la correttezza del programma (impossibile da trovare, dato che $T$ dovrebbe essere uguale a $D$)
$$ok(P,T) \Longrightarrow ok(P,D)$$

## Proprietà criteri di selezione

Un criterio di selezione è un criterio con cui viene scelto un test $T$

- **affidabilità**: presi due test in base al criterio, allora entrambi hanno successo o nessuno dei due ha successo
$$affidabile(C, P) \Longleftrightarrow (\forall T_1, T_2 \in C, successo(T_1, P) \Longleftrightarrow successo(T_2, P))$$
- **validità**: se il programma non è corretto, allora esiste un test selezionato in base al criterio che rileva il malfunzionamento
$$valido(C,P) \Longleftrightarrow (\neg ok(P,D) \Longrightarrow \exists T \in C \ | \ successo(T,P))$$

Bisognerebbe trovare un criterio valido e affidabile sempre, ma ciò non è possibile perché equivarrebbe a trovare il test ideale:

$$affidabile(C,P) \land valido(C,P) \land T \in C \land \neg successo(T,P) \Longrightarrow ok(P,D)$$

## Criteri di selezione

Criterio di copertura...
- **...dei comandi**: ogni comando eseguibile del programma è eseguito in corrispondenza di almeno un caso di test $t \in T$, _quindi ogni riga è eseguita almeno una volta_
- **...delle decisioni**: ogni decisione viene resa sia vera che falsa in corrispondenza di almeno un caso di test $t \in T$, _quindi ogni branch di un if viene eseguita almeno una volta_
- **...delle condizioni**: ogni singola condizione viene resa sia vera che falsa in corrispondenza di almeno un caso di test $t \in T$, _quindi ogni singola componente di if viene resa sia vera che falsa (senza necessariamente rendere sia vero che falso l'intero if)_
- **...delle decisioni e condizioni**: ogni decisione vale sia vero che falso e ogni condizione che compare nelle decisioni del programma vale sia vero che falso per diversi casi di test $t \in T$, _quindi ogni componente degli if vale sia vero che falso, ma anche tutto l'if deve valere sia vero che falso_
- **...delle condizioni composte**: ogni possibile composizione delle condizioni singole vale sia vero che falso per diversi casi di test $t \in T$, _quindi tutte le possibili permutazioni delle singole condizioni_
- **...delle condizioni e delle decisioni modificate**: si da importanza nella selezione delle combinazioni al fatto che la modifica di una singola condizione base porti a modificare l'esito della decisione, _quindi si testa sia vero che falso di una condizione base solo quando porta ad una modifica di tutta la decisione_

Criteri di copertura che considerano i cicli:
- **criterio di copertura dei cammini**: ogni cammino del grafo di controllo del programma viene percorso per almeno un caso di $t \in T$
- **criterio di n-copertura dei cicli**: ogni cammino del grafo contenente al massimo un numero di interazioni di ciclo non superiore a n viene percorso per almeno un caso di test $t \in T$
	- **2-copertura dei cicli**: testare le casistiche principali, ovvero zero, una o più iterazioni
