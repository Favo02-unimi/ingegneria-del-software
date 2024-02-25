## Dipendenza

Dipendenza semantica, i cambiamenti di uno potrebbero riflettersi sull'altro.

_Una relazione generica tra due classi (in modo semantico)._

![dipendenza](dipendenza.png)

## Associazione

Rappresenta una famiglia di collegamenti strutturali.

_Una o entrambe le classi hanno una reference all'altra._

![associazione](associazione.png)

## Aggregazione

Contenitore, contiene una o più istanze di un'altra classe.
Variante più specifica dell'associazione, contiene 1 o N istanze di una classe.

![aggregazione](aggregazione.png)
_Lato rombo: contenitore, lato freccia: contenuto, classe delle istanze._

Esempio: un deck contiene tante carte, che però possono esistere da sole.

## Composizione

Forma più forte di aggregazione, dove il contenitore controlla il life cycle dei contenuti.

![composizione](composizione.png)
_Lato rombo: contenitore, lato freccia: contenuto, classe delle istanze._

Esempio: un palazzo è composto da tanti appartamenti, distruggendo il palazzo un appartamento da solo non ha senso di esistere.

## Realizzazione

Implementazione di un'interfaccia.

![realizzazione](realizzazione.png)

## Estensione / Generalizzazione

Estensione di una classe concreta/astratta.

![generalizzazione](generalizzazione.png)