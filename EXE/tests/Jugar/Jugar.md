> El programa s'ha d'iniciar amb test.sh
> Tots els tests s'executen sequencialment

## Importar i Mostrar un Nivell de Mida 3
- Fitxers de dades:
    - fitxers/1.txt

- Passos:
    - Introduim el nom "Moniato" en el camp Login
    - Premem el boto "Start"
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "3.txt" dins la carpeta "fitxers"
    - Apareix el missatge "Import Successful", premem OK
    - Premem el boto "PLAY"
    - Seleccionem el primer nivell `[Size 3 - 1 (Moniato)]`

- Resultats: 
    - Es mostra una vista amb el tauler de mida 3


## Botons de Jugar
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "Check if Solved"
    - Apareix un missatge indicant que encara no esta solucionat

    - Premem OK
    - Premem el boto "Check if Has Solution"
    - Apareix un missatge indicant que te solucio

    - Premem OK
    - Escrivim un 2 a la primera casella
    - Premem el boto "Get Current Solution"
    - Apareix un missatge indicant que el tauler actual no te solucio

    - Premem OK
    - Escrivim un 1 a la primera casella i a la ultima
    - Premem el boto "Get Current Solution"
    - S'emplenen les cel·les seguint:
        1 3 2
        2 1 3
        3 2 1

    - Esborrem els valors de totes les cel·les fins que quedi el tauler en blanc
    - Premem el boto "Get Level Solution"
    - S'emplenen les cel·les seguint el mateix patro d'abans

    - Premem "Export Level"
    - El guardem a "fitxers"
    - Comprovem que es igual que "3.txt" pero emplenat

    - Premem "Save Game"
    - Apareix un missatge indicant que s'ha guardat correctament
    - Premem OK
  
    - Premem "Exit"

- Resultats:
    - Cada missatge indicat com a "apareix un missatge"
    - Es mostra el menu principal
    - Al fitxer "database.json" hi trobem una partida guardada amb nom "Moniato" i del nivell amb id = 0


## Continuar Partida
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "PLAY"
    - Apareix un missatge indicant que tenim una partida guardada
    - Premem "No"
    - Premem "Back"
    
    - Premem el boto "PLAY"
    - Apareix un missatge indicant que tenim una partida guardada
    - Premem "Yes"

- Resultats: 
    - Es mostra una vista amb el tauler de mida 3 i les cel·les que haviem emplenat en el test "Botons de Jugar" 


## Registrar i Consultar Ranking
- Fitxers de dades:
    - Cap

- Passos:
    - Premem el boto "Check if Solved"
    - Apareix un missatge indicant que l'hem resolt en un numero elevat de segons
    - Premem "OK"
    - Apareix el Menu Principal
    
    - Premem el boto "CONSULT"
    - Premem el boto "LEVELS"
    - Premem el primer element

- Resultats: 
    - Es mostra una vista amb el ranking del nivell, on apareix l'usuari "Moniato" amb un temps elevat