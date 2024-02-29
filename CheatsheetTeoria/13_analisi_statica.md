
Analisi statica eseguita dai compilatori:
- **analisi lessicale**: vengono parsati i vari token appartenenti al linguaggio
- **analisi sintattica**: viene controllata la posizione e la composizione dei vari token
- **controllo dei tipi**: vengono individuati i tipi ed eventuali incompatibilità tra di essi
- **analisi del data flow**: rilevare problem relativi all'evoluzione dei valori associati alle variabili

## Analisi del Data Flow

Operazioni che possono essere eseguite su un dato:
- `d` **definizione**: assegnamento di un valore ad una variabile (anche il passaggio di parametri)
- `u` **uso**: lettura contenuto di una variabile, per qualsiasi utilizzo
- `a` **annullamento**: al termine dell'esecuzione di questo comando il valore della variabile non è significativo/affidabile (come fine scope o dichiarazione senza assegnamento)

Regole di flusso:
- l'**uso** di una variabile deve essere sempre preceduto da una **definizione** senza **annullamenti** intermedi
- la **definizione** di una variabile deve essere sempre seguita da un **uso**, prima di un suo **annullamento** o nuova **definizione**
- l'**annullamento** di una variabile deve essere sempre seguito da una **definizione**, prima di un **uso** o altro **annullamento**

Tabella riassuntiva regole: _prima operazione sulla sinistra, seconda operazione in alto_

|       | a     | d     | u     |
| ----- | ----- | ----- | ----- |
| **a** | _err_ |       | _err_ |
| **d** | _err_ | _err_ |       |
| **u** |       |       |       |

Sequenza di operazioni in un cammino $p$ su una variabile $a$: $P(p,a)$

Espressioni regolari per rappresentare sequenze:
- $P([1 \rightarrow], a)$: tutti i cammini che partono dall'istruzione 1 per la variabile a
- $|$: i simboli alla propria destra e sinistra si escludono a vicenda
- $*$: il simbolo precedente può essere ripetuto da 0 a n volte

Qualsiasi programma è esprimibile da: $( u | : d | : a)*$, le espressioni regolari possono esprimere tutti i cammini ma non tutti e soli i cammini, sono oggetti più generali (astrazione pessimistica)

## Criteri di copertura

Terminologia:
- nodo $i$: un comando (riga) del programma
- $def(i)$: insieme delle variabili definite in $i$
- $du(i, x)$: l'insieme dei nodi che potrebbero usare il valore di $x$ definito in $i$, ovvero l'insieme dei nodi $j$ tali che
	- $x \in def(i)$: la variabile $x$ è definita in $i$
	- $x$ è usata nel nodo $j$
	- esiste un cammino da $i$ a $j$ libero da definizioni di $x$

### Criterio di copertura delle definizioni

per ogni nodo $i$ e ogni variabile $x \in def(i)$, il test $T$ include un caso di test $t$ che esegue un cammino libero da definizione da $i$ ad almeno uno degli elementi di $du(i,x)$

$$T \in C_{def} \Longleftrightarrow \forall i \in P, \  \forall x \in def(i), \ \exists j \in du(i, x), \exists t \in T \ \text{che esegue un cammino da $i$ a $j$ senza ulteriori definizioni di $x$}$$

ovvero testare tutte le definizioni assicurandosi che ognuna abbia almeno un uso del valore da loro assegnato

### Criterio di copertura degli usi

per ogni nodo $i$ e ogni variabile $x$ appartenente a $def(i)$, il test $T$ include un caso di test $t$ che esegue un cammino libero da definizioni da $i$ ad ogni elemento di $du(i,x)$

$$T \in C_{path} \Longleftrightarrow \forall i \in P, \ \forall x \in def(i), \ \forall j \in du(i, x), \exists t \in T \ \text{che esegue un cammino da $i$ a $j$ senza ulteriori definizioni di $x$}$$

ovvero testare tutti i potenziali usi di una variabile definita (da una definizione a ogni utilizzo). Questo criterio è più permissivo di quello delle definizioni dato che il $\forall$ è verificato anche se non esistono usi, mente $\exists$ no

### Criterio di copertura dei cammini DU

simile al precedente, ma al posto di testare un cammino da una definizione ad un uso, vengono testati tutti i cammini da una definizione ad un uso.

$$T \in C_{pathDU} \Longleftrightarrow \forall i \in P, \ \forall x \in def(i), \ \forall j \in du(i, x), \ \forall \text{ cammino da $i$ a $j$ senza ulteriori definizioni di $x$} \ \exists t \in T \ \text{che lo esegue}$$

impraticabile per natura combinatoria del criterio