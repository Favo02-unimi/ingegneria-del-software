## Reti di Petri

Linguaggio formale utile a raccogliere delle specifiche, utili in contesti in cui la sicurezza è fondamentale e per descrivere sistemi concorrenti.

Esistono diverse estensioni delle reti di Petri "classiche", come quelle temporizzate

### Definizioni

Grafo bipartito, composto da posti e transizioni sui vertici e flussi sugli archi. Ad ogni posto è assegnato un certo numero di token, il posizionamento dei token ne determina il suo stato complessivo.

Una quintupla $<P, T, F, W, M_0>$:
- $P$: posti
- $T$: transizioni
- $F$: flussi
- $W$: funzione peso
- $M_0$: marcatura iniziale

Proprietà:
- $P \cap T = \emptyset$
- $P \cup T \neq \emptyset$
- $F \subseteq (P \times T) \cup (T \times P)$
- $W: F \rightarrow \mathbb{N} \textbackslash 0$
- $M_0 : P \rightarrow \mathbb{N}$

Definizioni:
- **preset**: $Pre(a) = d \in (P \cup T) <d,a> \in F$: insieme degli elementi in ingresso ad un certo elemento $a$
- **postset**: $Post(a) = d \in (P \cup T) <a, d> \in F$: insieme degli elementi in uscita da un certo elemento $a$
- **transizione abilitata**: $M [ t >$ quando $\forall p \in Pre(t), M(p) \geq W(<p,t>)$
- scatto: porta da marcatura $M$ a marcatura $M'$: $M[t> M'$

Relazioni:
- **sequenza** (tra due transizioni): due transizioni possono scattare strettamente una dopo l'altra
$$M[t_1> \land \ \neg M[t_2> \land \ M[t_1 t_2>$$
- **conflitto strutturale** (tra due transizioni): hanno posti in ingresso in comune
$$Pre(t_1) \cap Pre(t_2) \neq \emptyset$$
- **conflitto effettivo** (tra due transizioni): sono entrambe abilitate, ma il posto in ingresso in comune non ha abbastanza gettone per farle scattare entrambe
$$M[t_1> \land \ M[t_2> \ \land \exists p \in Pre(t_1) \cap Pre(t_2), M(p) < W(<p,t_1>) + W(<p, t_2>)$$
- **concorrenza strutturale** (tra due transizioni): non hanno posti in ingresso in comune
$$Pre(t_1) \cap Pre(t_2) = \emptyset$$
- **concorrenza effettiva** (tra due transizioni): tutti i posti che hanno in comune hanno un numero di token sufficiente a farle scattare entrambe
$$M[t_1> \land \ M[t_2> \land \ \forall p \in Pre(t_1) \cap Pre(t_2), M(p) \geq W(<p,t_1>) + W(<p,t_2>)$$

### Insieme di raggiungibilità

Insieme di raggiungibilità di una rete $P/T$ a partire da una marcatura $M$ è il più piccolo insieme di marcature tale che:
- $M \in R(P/T, M)$
- $M' \in R(P/T, M) \land \exists t \in T, M'[t> M'' \Longrightarrow M'' \in R(P/T, M)$

### Limitatezza

Non esistono posti che, a partire dalla marcatura iniziale, possono raggiungere un numero infinito di token
$$\exists k \in \mathbb{N}, \forall M' \in R(P/T, M), \forall p \in P, M'(P) \leq k$$

### Vitalità di una transizione

Una transizione $t$ in una marcatura $M$ può essere:

- di **grado 0** (**morta**): non è abilitata in nessuna marcatura appartenente all'insieme di raggiungibilità, _quindi la transizione non potrà mai scattare_
$$\nexists M' \in R(P/T, M) \ | \ M'[t>$$
- di **grado 1**: esiste almeno una marcatura appartenente all'insieme di raggiungibilità in cui la transizione è abilitata, _quindi si può raggiungere una situazione in cui la transizione può scattare_
$$\exists M' \in R(P/T, M) \ | \ M'[t>$$
- di **grado 2**: per ogni $n$ (numero naturale escluso lo zero), esiste una sequenza di scatti ammissibile a partire da $M$ in cui la transizione scatta $n$ volte, _quindi è possibile far scattare la transizione un numero $n$ di volte_
$$\forall n \in \mathbb{N} \ \textbackslash \{0\}, \exists M[\dots t^1 \dots t^2 \dots t^n>$$
- di **grado 3**: esiste una sequenza di scatti ammissibile a partire da $M$ per cui la transizione scatta infinite volte, _quindi è possibile far scattare la transizione infinite volte_
$$\forall n \in \mathbb{N} \ \textbackslash \{0\}, \exists M[\dots t^1 \dots t^2 \dots t^\infty>$$
- di **grado 4** (**viva**): in qualunque marcatura raggiungibile esiste una sequenza di scatti ammissibile in cui è possibile far scattare la transizione almeno una volta, _quindi la transizione può scatta infinite volte in qualunque situazione ci si trovi_
$$\forall M' \in R(P/T, M), \exists M'' \in R(P/T, M'), M''[t>$$

### Capacità dei posti (posto complementare)

È possibile definire un dialetto delle reti di Petri tale che alcuni posti hanno una capacità limitata. È anche possibile trasformare questa rete in una rete classica, aggiungendo dei posti complementari. Questa proprietà vale solo per le reti pure, ovvero che per ogni transizione hanno preset e postset disgiunti

Un posto complementare è un posto avente in uscita vero ognuna delle transizioni del posto considerato un arco di ugual peso ma di direzione opposta

### Archi inibitori

Un arco inibitore collega un posto ad una transizione che ha bisogno che NON siano presenti gettoni nel posto per renderla abilitata

### Eliminazione pesi sugli archi

Per ogni rete avente pesi sugli archi, è possibile crearne una equivalente senza pesi sugli archi (tutti gli archi di peso 1). Queste reti possono essere viste come reti $C/E$, dove ogni posto è una condizione che può essere vera o falsa. Ogni rete limitata può venir trasformata in una rete $C/E$

### Conservatività

Una rete è **strettamente conservativa** quando per qualsiasi marcatura raggiungibile, la somma dei gettoni è sempre la stessa:
$$\forall M' \in R(P/T, M), \sum_{p \in P} M(p) = \sum_{p \in P} M'(p)$$
Una rete è conservativa quando la sommatoria dei gettoni di ogni posto, moltiplicati per il peso di quel posto dato dalla funzione $H$ è uguale in ogni marcatura raggiungibile:
$$\forall M' \in R(P/T, M), \sum_{p \in P}H(p)M(p) = \sum_{p \in P} H(p)M'(p)$$
Una rete conservativa è anche limitata, ma non è detto il viceversa

### Stato base di una rete

Una marcatura $M'$ si dice stato base di una rete se qualsiasi sia lo stato attuale è sempre possibile raggiungere la marcatura $M'$