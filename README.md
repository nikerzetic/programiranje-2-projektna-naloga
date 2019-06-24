# Gomoku - pet v vrsto

### Programiranje 2: Projektna naloga

Projektna naloga pri predmetu Programiranje 2 na Fakulteti za matematiko in fiziko.

Igro se zažene v razredu Gomoku. Vse nadaljnje upravljanje poteka preko grafičnega umesnika. Program kot začetno igro postavi igro *Človek proti računalniku*. Novo igro lahko začnemo s  klikom v meni *Nova igra*, kjer imamo na voljo igre: *Računalnik proti človeku*, *Človek proti računalniku*, *Računalnik proti računalniku* in *Človek proti človeku*.

Igra vsebuje algoritem, ki preračuna račinalnikovo potezo. To stori tako, da preveri vse možne poteze, pri čemer odreže nemogoče veje, po principu *alpha-beta-pruning*. Globina iskanja je 2, kar na prvi potezi pomeni med 624 in 390 000 možnosti.
