## Iterator

### Obiettivo

Gli oggetti che definiamo possono contenere una **collezione di altri oggetti**, sulla quale è _auspicabile_ poter effettuare particolari operazioni. È molto _probabile_ che vorremmo poter **iterare** sui singoli elementi della **collezione**, senza esporre la rappresentazione interna utilizzata per contenerli.

### Come

Java supporta largamente il pattern iterator, a tal punto che nella **libreria standard** esiste una **interfaccia** generica per gli iteratori `Iterator`, al suo interno sono definiti i metodi:

- `next`
- `has Next`
- `remove`: normalmente non supportato, in quanto permetterebbe di modificare la collezione contenuta nella classe _(reference escaping)_
- `forEachRemaining`: esegue una determinata azione su tutti gli elementi ancora non estratti dall'iteratore.

Esiste anche un'altra interfaccia che l'oggetto iterabile può implementare, ovvero `Iterbale`. Essa richiede solamente la presenza di un metodo `iterator()`, che restituisca l'**iteratore concreto**. Una volta implementata permette di utilizzare il proprio oggetto aggregato all'interno di un costrutto `foreach`.

### Esempio _(deck)_

```java
class Deck implements Iterable<Card> {
	...
}

Deck deck = ...;
for (Card card : deck) {
	...
}
```
