# OptoGo

Aplikacija namenjena lekarima specijalistima u svrhu preporucivanja dijagnoza na osnovu ulaznih simptoma, kao i terapija, procedura i lijekova na osnovu dijagnoza.

## Uputstvo

Klonirati repozitorijum, pokrenuti main metodu koja se nalazi na putanji src/com.optogo/

## Alati i biblioteke

* [JavaFX](https://openjfx.io/) - platforma za razvoj desktop aplikacija.
* [Graphviz](https://www.graphviz.org/) - softver za vizualizaciju grafa
* [Hibernate](https://hibernate.org/) - objekt-relacioni maper
* [H2](https://www.h2database.com/html/main.html) - Java SQL baza podataka
* [JColibri]() - CBR framework
* [UnbBayes](http://unbbayes.sourceforge.net/) - biblioteka za modelovanje Bajesovih mreza

## Demo - objasnjenje
Slucaj prvog pacijenta je primer gde lekar specijalista unosi simptome i odmah donosi dijagnozu bez ikakve predikcije. Takodje bez predikcije unosi i procedure koje je neophodno obaviti i lek koji je potrebno koristiti prilikom lecenja odabrane bolesti.
Rezultati pregleda se uvode u evidenciju pregleda.

Kod drugog pacijenta lekar zapocinje pregled tako sto unosi prikupljene simptome od pacijenta i bira opciju "Predict".
Odabirom opcije "Predict" lekaru se prikazuje dijalog u kome su izlistane bolesti sa procentima koje predstavljaju koliko svaka bolest odgovara unetim simptomima. U ostala dva taba ("Medications" i "Procedures") izlistane su procedure (terapije i pregledi) kao i lekovi koji se inace koriste kod bolesti izlistanih u prvom tabu ("Conditions"). Lekar zatim bira jednu bolest iz liste bolesti i zeli da poboljsa rezultate odabirom opcije "Improve Results". Lekaru se otvara dijalog koji sadrzi simptome koji se inace javljaju uz prethodno preporucene bolesti (traze se simptomi iskljucio za one bolesti koje imaju stepen odgovaranja pocetnim simptomima preko 50%), a koje pacijent nije rekao prilikom anamneze. Lekar u ovom slucaju moze dodatno da ispita pacijenta da li ima jos neke simptome, u slucaju da pacijent nije rekao sve simptome koje ima. Konkretno u ovom primeru, pretpostavlja se da pacijent nije pomenuo 2 simptoma koje doktor naknadno unosi i vrsi ponovnu predikciju. Zatim se azuriraju procentualne vrednosti bolesti, procedura i lekova i lekar se odlucuje za odredjenu bolest i bira koje procedure (terapije i dodatne preglede) je potrebno izvrsiti uz odabranu bolest kao i lekove koje je potrebno koristiti prilikom lecenja te bolesti. 
Dodatno je prikazana i vizualizaija kako je aplikacija dosla do odredjenih zakljucaka. 
Rezultati pregleda se uvode u evidenciju pregleda.

Kod treceg pacijenta vrsi se unos simptoma, predikcija i odabir bolesti, a zatim se vrsi odabir procedura i lekova.
Prilikom izlistavanja preporucenih procedura i lekova, moze se primetiti da su neke od procedura i lekova obojene zelenom bojom.
Elementi obojeni zelenom bojom znace da su se te procedure i lekovi vec koristili kod te bolesti kod drugih pacijenata. (CBR zakljucivanje)

## Autori

* [**Pavle Jankovic**](https://github.com/pavle-j4nk)
* [**Dragan Jovic**](https://github.com/draganjovic96)
* [**Aleksandar Vujasinovic**](https://github.com/vujasinovic)
