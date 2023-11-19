### Iterator
<span style=color:green>Obbiettivo</span> := Gli oggetti che definiamo, possono contenere una collezione di altri oggetti, sulla quale è auspicabile poter effettuare particolari operazioni. é molto probabile che vorremmo <b><u>poter iterare sui singoli elementi della collezione, senza esporre la rappresentazione interna utilizzata per contenerli.</u></b>

<span style=color:cyan>Come</span> := Java supporta largamente il pattern iterator, a tal punto che nella libreria standard esiste una interfaccia generica per gli iteratori <span style=color:red>Iterator E</span>, al suo interno sono definiti i metodi:
 - <b><u>Next</u></b>
 - <b><u>Has Next</u></b>
 - <b><u>Remove</u></b> = normalmente non supportato, in quanti permetterebbe di modificare la collezione contenuta nella classe.
 - <b><u>ForEachRemaining()</u></b> = esegue una determinata azione su tutti gli elementi ancora non estratti dall'iteratore.

Esiste anche un altra interfaccia che l'oggetto iterabile può implementare, ovvero <span style=color:red>iterbale</span> := Essa richiede solamente la presenza di un metodo `iterator()`, che restituisca l'iteratore concreto. Una volta implementata permette di utilizzare il proprio oggetto aggregato all'interno di un costrutto foreach.

Esempio:
``` java
for (Card card : deck){
	System.out.println(card.getSuit());
}
```
