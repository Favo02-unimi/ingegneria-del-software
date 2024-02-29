Tecniche per l'analisi delle reti di Petri:
- tecniche dinamiche:
	- albero delle marcature raggiungibili (**grafo di raggiungibilità**)
	- albero di copertura delle marcature raggiungibili (**grafo di copertura**)
- tecniche statiche:
	- identificazione delle **$P$-invarianti** (caratteristiche riguardanti posti)
	- identificazione delle **$T$-invarianti** (caratteristiche riguardanti le transizioni)

### Albero di raggiungibilità

"Visita" della rete, si continuano a visitare la marcature segnate come nuove, ignorando quelle duplicate (già viste in precedenza)

Per poter ottenere un albero completo, è necessario che la rete sia limitata, ottenendo la FSM corrispondente

### Albero di copertura

Copertura: una marcatura $M$ copre una marcatura $M'$ solo se:
$$\forall p \in P, M(p) \geq M'(p)$$
Ricopribile: $M$ si dice ricopribile da $M'$ se:
$$\exists M'' \in R(M') \ | \ M'' \text{ copre } M$$

È possibile creare un albero di copertura anche per reti illimitate, dato che gli stati ricopribili verranno rappresentati dalla variabile $w$
