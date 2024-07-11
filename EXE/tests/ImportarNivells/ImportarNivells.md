> El programa s'ha d'iniciar amb test.sh
> Tots els tests s'executen sequencialment

## Importacio Nivell Trivial
- Fitxers de dades:
    - fitxers/1.txt

- Passos:
    - Introduim el nom "Moniato" en el camp Login
    - Premem el boto "Start"
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "1.txt" dins la carpeta "fitxers"
    - Apareix el missatge "Import Successful", premem OK

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "1" i creador "Moniato"
    - Es mostra el Menu Principal en pantalla


## Importacio Nivell Mida 2
- Fitxers de dades:
    - fitxers/2.txt

- Passos:
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "2.txt" dins la carpeta "fitxers"
    - Apareix el missatge "Import Successful", premem OK

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "2" i creador "Moniato", juntament amb el nivell anterior
    - Es mostra el Menu Principal en pantalla


## Importacio Nivell amb Format Incorrecte
- Fitxers de dades:
    - fitxers/5-incorrectformat.txt

- Passos:
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "5-incorrectformat.txt" dins la carpeta "fitxers"
    - Apareix un missatge indicant que el format es incorrecte
    - Premem OK

- Resultats: 
    - No s'ha importat el nivell
    - Reapareix el dialeg de seleccio de nivells


## Importacio Nivell amb Particions Incorrectes
- Fitxers de dades:
    - fitxers/5-incorrectpartitions.txt

- Passos:
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "5-incorrectpartitions.txt" dins la carpeta "fitxers"
    - Apareix un missatge indicant que l'input es mes gran que la mida del tauler
    - Premem OK

- Resultats: 
    - No s'ha importat el nivell
    - Reapareix el dialeg de seleccio de nivells


## Importacio Nivell sense Solucio
- Fitxers de dades:
    - fitxers/5-unsolvable.txt

- Passos:
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "5-unsolvable.txt" dins la carpeta "fitxers"
    - Apareix un missatge indicant que el tauler no te solucio
    - Premem OK

- Resultats: 
    - No s'ha importat el nivell
    - Reapareix el dialeg de seleccio de nivells


## Importacio Nivell Mida 8 amb un altre Usuari
- Fitxers de dades:
    - fitxers/8.txt

- Passos:
    - Premem el boto "Exit"
    - Introduim el nom "Patata" en el camp Login
    - Premem el boto "Start"
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "8.txt" dins la carpeta "fitxers"
    - Apareix el missatge "Import Successful", premem OK

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "8" i creador "Patata", juntament amb el nivells anteriors
    - Es mostra el Menu Principal en pantalla


## Consultar Nivells Importats
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "CONSULT"
    - Premem el boto "LEVELS"

- Resultats: 
    - Es mostren per pantalla els nivells importats en el seguent ordre:
        - `[Size 1] - 1 (Moniato)`
        - `[Size 2] - 2 (Moniato)`
        - `[Size 8] - 3 (Patata)`