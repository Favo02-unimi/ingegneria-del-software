## Reti Time Basic

sono una sestupla: $<P, T, \Theta, F, tf, m_0>$:
- $P$, $T$, $F$: insieme dei posti, delle transizioni e dei flussi
- $\Theta$: dominio temporale, insieme numerico che contiene le rappresentazioni del tempo
- $tf$: funzione che associa ad ogni transizione una funzione temporale $tf_t$ che data in input una tupla abilitante $en$, restituisce un insieme di tempi di scatto possibili $\subseteq \Theta$
- $m_0$: marcatura iniziale, ogni posto viene associato un insieme di coppie token, timestamp

### Semantica temporale debole (WTS)

Una transizione non è costretta a scattare anche se abilitata

Assiomi:
- **Monotinicità rispetto alla marcatura iniziale**: tutti i tempi di scatto di una sequenza devono essere non minori di uno qualunque dei timestamp dei gettoni della marcatura iniziale
- **Divergenza del tempo**: non è possibile avere un numero infinito di scatti in un intervallo di tempo finito

### Semantica temporale monotonica debole (MWTS)

Gli scatti devono essere in una sequenza non decrescente, in modo che il tempo non possa tornare indietro

Assiomi:
- **Monotonicità dei tempi di scatto di una sequenza**: tutti i tempi di scatto di una sequenza devono essere ordinati nella sequenza in maniera non decrescente

Reti WTS = Reti MWTS, entrambe le sequenze produrranno la stessa marcatura finale

### Semantica temporale forte (STS)

Imposizione che una transizione deve scattare ad un suo possibile tempo di scatto a meno che non venga disabilitata prima del proprio massimo tempo di scatto ammissibile

Assiomi:
- **Marcatura forte iniziale**: il massimo tempo di scatto di tutte le transizioni abilitate nella marcatura iniziale dev'essere maggiore o uguale del massimo timestamp associato ad un gettone in tale marcatura
- **Sequenza di scatti forte**: una sequenza di scatti ammissibile in semantica MWTS che parta da una marcatura forte iniziale è una sequenza di scatti forte se per ogni scatto il tempo di scatto della transizione non è maggiore del massimo tempo di scatto di un'altra transizione abilitata

### Semantica temporale mista

Non tutte le transizioni rispettano una semantica, vengono mischiate transizioni in semantica debole con transizioni in semantica forte
