> El programa s'ha d'iniciar amb test.sh
> Tots els tests s'executen sequencialment

## Creacio Usuari
- Fitxers de dades:
    - Cap

- Passos:
    - Introduim el nom "Moniato" en el camp Login
    - Premem el boto "Start"
    - Ens porta al menu principal
    - Sortim amb el boto "Exit"

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou usuari amb nom "Moniato"
    - Es mostra el menu "Login" en pantalla


## Creacio Usuari Buit
- Fitxers de dades:
    - Cap

- Passos:
    - No introduim res al camp "Login"
    - Premem el boto "Start"

- Resultats:
    - Apareix un missatge en vermell indicant que el nom d'usuari no pot ser buit


## Creacio Segon Usuari
- Fitxers de dades:
    - Cap

- Passos:
    - Introduim el nom "Patata" en el camp Login
    - Premem el boto "Start"
    - Ens porta al menu principal
    - Sortim amb el boto "Exit"

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou usuari amb nom "Patata" juntament amb l'anterior "Moniato"
    - Es mostra el menu "Login" en pantalla


## Login Usuari
- Fitxers de dades:
    - Cap

- Passos:
    - Introduim el nom "Moniato" en el camp Login
    - Premem el boto "Start"

- Resultats: 
    - A l'arxiu database.json hi ha registrats els mateixos usuaris, "Moniato" i "Patata"
    - Es mostra el menu principal en pantalla


## Consultar Usuaris
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "CONSULT"
    - Premem el boto "USERS"

- Resultats: 
    - Es mostren per pantalla els dos usuaris "Moniato" i "Patata"


## Consultar Molts Usuaris
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "Back"
    - Premem el boto "Exit"
    - Repetim el test "Creacio Usuari" 8 vegades, començant amb el nom "1" i incrementant cada cop
    - Fem login amb el nom "Moniato"
    - Repetim el test "Consultar Usuaris"

- Resultats: 
    - Es mostren per pantalla els usuaris "Moniato", "Patata" i nombres de l'1 al 8