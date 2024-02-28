## Reti di Petri

Linguaggio formale utile a raccogliere delle specifiche. Esistono diverse estensioni delle reti di Petri "classiche"

### Vitalità di una transizione

Una transizione $t$ può essere:

- di **grado 0** (**morta**): non è abilitata in nessuna marcatura appartenente all'insieme di raggiungibilità, _quindi la transizione non potrà mai scattare_
$$\nexists M' \in R(P/T, M) \ | \ M'[t>$$
- di **grado 1**: esiste almeno una marcatura appartenente all'insieme di raggiungibilità in cui la transizione è abilitata, _quindi si può raggiungere una situazione in cui la transizione può scattare_
$$\exists M' \in R(P/T, M) \ | \ M'[t>$$
- di **grado 2**: per ogni $n$ (numero naturale escluso lo zero), esiste una sequenza di scatti ammissibile a partire da $M$ in cui la transizione scatta $n$ volte, _quindi è possibile far scattare la transizione un numero $n$ di volte_
$$\forall n \in \mathbb{N} \ \textbackslash \{0\}, \exists M[\dots t \dots t^1 \dots t^n>$$
- di **grado 3**: esiste una sequenza di scatti ammissibile a partire da $M$ per cui la transizione scatta infinite volte, _quindi è possibile far scattare la transizione infinite volte_
$$\forall n \in \mathbb{N} \ \textbackslash \{0\}, \exists M[\dots t \dots t^1 \dots t^\infty>$$
- di **grado 4** (**viva**): in qualunque marcatura raggiungibile esiste una sequenza di scatti ammissibile in cui è possibile far scattare la transizione almeno una volta, _quindi la transizione può scatta infinite volte in qualunque situazione ci si trovi_
$$\forall M' \in R(P/T, M), \exists M'' \in R(P/T, M'), M''[t>$$
