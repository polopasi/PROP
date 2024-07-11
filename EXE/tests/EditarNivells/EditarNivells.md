> El programa s'ha d'iniciar amb el programa test.sh
> Tots els tests s'executen sequencialment


## Importar nivells per editar
- Fitxers de dades:
    - fitxers/1.txt
    - fitxers/2.txt
    - fitxers/8.txt

- Passos:
    - Introduim el nom "Moniato" en el camp Login
    - Premem el boto "Start"
    - Premem el boto "IMPORT LEVEL"
    - Seleccionem l'arxiu "1.txt" dins la carpeta "fitxers"
    - Apareix el missatge "Import Successful", premem OK
    - Repetim el proces de "IMPORT LEVEL" i seleccionem els arxius "2.txt" i "8.txt"

- Resultats: 
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "1" i creador "Moniato"
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "2" i creador "Moniato"
    - A l'arxiu database.json hi ha registrat un nou nivell amb mida "8" i creador "Moniato"
    - Es mostra el Menu Principal en pantalla

## Edicio dels nivells importats

- Fitxers de dades:

- Passos:
    - Premem el boto "EDIT".
    - Seleccionem `[Size 1] - 1 (Moniato)`
    - Premem "Exit"
    - Seleccionem `[Size 2] - 2 (Moniato)`
    - Premem "Exit"
    - Seleccionem `[Size 8] - 3 (Moniato)`
    - Premem "Exit"

- Resultats: 
    - Es mostren per pantalla els nivells importats en el seguent ordre:
        - `[Size 1] - 1 (Moniato)`
        - `[Size 2] - 2 (Moniato)`
        - `[Size 8] - 3 (Moniato)`
    -S'obre l'editor de `[Size 1] - 1 (Moniato)`
    -S'obre l'editor de `[Size 2] - 2 (Moniato)`
    -S'obre l'editor de `[Size 8] - 3 (Moniato)`

## Creació d'un nivell nou i comprobació de solució

- Fitxers de dades:

- Passos:
    - Premem el boto "EDIT"
    - Premem el boto "CREATE NEW LEVEL"
    - Eliminem dues files/columnes escrivint 2 en la casella de Board Size 
    - Seleccionem les particions de cada cel·la:
        0 1
        2 2
     i omplim les operacions de les particions amb:
        1 2
        3+
    - Cliquem "Check if Has Solution"      

- Resultats: 

    - Inicialment, es mostra un tauler de mida 4, amb les cel·les buides i partició -1  
    - Obtenim un missatge "The current Board can be solved"

    
## Tauler incorrecte
  
- Fitxers de dades:

- Passos:  
    -Escrivim Board Size = 3
    -Assignem les següents particions:
        0 1 1
        2 2 3
        2 2 2
    -Assignem les operacions:
        Partició 0: 1
        Partició 1: 2-
        Partició 2: 9+
        Partició 3: 3
    -Premem el boto "Check if has solution" 
    -Premem el boto "Get Current Solution"
    -Premem "Get Level Solution"
    -Premem "Save Level"

- Resultats: 
    - Obtenim el missatge "The current board has no solution. Try changing your approach"
    - Obtenim el missatge "The current board has no solution. Try changing your approach"
    - Obtenim el missatge "The current board has no solution"
    - Obtenim el missatge "The Level has no solution, it cannot be saved"

## Tauler correcte

- Fitxers de dades:

- Passos:
    - Cambiem l'operacio de la particio 0 (cel·la [0, 0] a 2)
    - Premem "Check if Has Solution"
    - Premem "Get Current Solution"
    - Premem "Check if Solved"
    - Premem "Save Level"

- Resultats:
    - Obtenim el missatge "The current board can be solved"
    - S'escriuen les cel·les amb els seguents valors:
        2 3 1
        1 2 3
        3 1 2
    - Obtenim el missatge "The Kenken is solved!"     
    - Obtenim el missatge "The level has been saved succesfully"
    
## Exportar nivell

- Fitxers de dades:

- Passos:
    - Premem "Export Level"
    - Seleccionem Desktop o escritori i escrivim el nom "editedLevel" i premem "Save"
    - Premem "OK" al missatge "The level has been exported succesfully to {PATH ESCOLLIT}"
    
- Resultats: 
    - S'ha exportar el nivell creat.

    
## Generar nou nivell   

- Fitxers de dades:

- Passos:    
    - Premem "Generate new Level (Destructive)"
    - Premem "Clear Cells"
    - Premem "Remove all Partitions"
        
- Resultats: 
    - Es genera un nivell aleatori amb les caselles emplenades
    - Es netegen les cel·les del kenken i els valors s'eliminen
    - Totes les caselles passen a tenir particions -1

## Geneacio simple  

- Fitxers de dades:

- Passos:    
    
    - Cambiem els seguents valors:
        Board Size : 8
        Min Partition Size : 1
        Max Partition Size : 8
        Merge Probability : 50
    
    - Marquem nomes les caselles:
        Addition
        Subtraction
        Modulus
        Power
    
    - Premem "Generate new Level (Destructive)"
            
- Resultats: 
    - Es genera un nou kenken amb només les operacions indicades i el tamany de les particions com a màxim de mida 8
    
## Generacio amb Merge Probability = 100

- Fitxers de dades:

- Passos:    
    - Cambiem els seguents valors:
        Board Size : 8
        Min Partition Size : 1
        Max Partition Size : 9
        Merge Probability : 100
    
    - Marquem nomes les caselles:
        Addition
        Subtraction
        Division
        Modulus
        Power
        
    - Premem "Generate new Level (Destructive)"
            
- Resultats: 
    - Es genera un nou kenken amb només sumes
    
## Generacio amb Merge Probability = 0

- Fitxers de dades:

- Passos:   
    - Cambiem els seguents valors:
        Board Size : 8
        Min Partition Size : 1
        Max Partition Size : 9
        Merge Probability : 0
    
    - Marquem totes les caselles d'operacions
    
    - Premem "Generate new Level (Destructive)"

- Resultats:
    - Es genera un nou kenken amb només particions de mida 1.

    
## Generacio amb Min Partition Size = Max Partition Size    
    
- Fitxers de dades:

- Passos:  
    - Cambiem els seguents valors:
        Board Size : 8
        Min Partition Size : 9
        Max Partition Size : 9
        Merge Probability : 50
    - Marquem totes les caselles d'operacions
    - Premem "Generate new Level (Destructive)"

- Resultalts:
    - Es genera un nou kenken amb només sumes, productes i una casella fixa. 
      Totes les particions son de tamany 9.
    
## Generacio amb cadascuna de les operacions individualment.
        
- Fitxers de dades:

- Passos: 
    - Per les seguents proves la configuració és:
        Board Size : 6
        Min Partition Size : 1
        Max Partition Size : 10
        Merge Probability : 50
    
    - Marquem nomes Addition i premem "Generate new Level (Destructive)" 
    - Marquem nomes Subtraction i premem "Generate new Level (Destructive)"
    - Marquem nomes Product i premem "Generate new Level (Destructive)"
    - Marquem nomes Division i premem "Generate new Level (Destructive)"
        - Pot ser que aparegui el missatge "The Board could not be Generated".
          En aquest cas torna a intentar.
    - Marquem nomes Modulus i premem "Generate new Level (Destructive)"
    - Marquem nomes Power i premem "Generate new Level (Destructive)"

- Resultats:
    - Es genera un kenken amb nomes sumes i algunes caselles fixes
    - Es genera un kenken amb nomes restes i algunes caselles fixes
    - Es genera un kenken amb nomes multiplicacions i algunes caselles fixes
    - Es genera un kenken amb nomes divisions i algunes caselles fixes
    - Es genera un kenken amb nomes moduls i algunes caselles fixes
    - Es genera un kenken amb nomes exponents i algunes caselles fixes
    
## Intentar generar un tauler impossible
           
- Fitxers de dades:

- Passos: 
    - Cambiem els seguents valors:
        Board Size : 3
        Min Partition Size : 1
        Max Partition Size : 4
        Merge Probability : 100
    
    - No marquem cap operacio.
    - Premem Generate new Level

- Resultats:
    - Obtenim el missatge d'error: The board could not be generated. Try changing some parameters or generating again.
    
## Consultar els nivells creats
           
- Fitxers de dades:

- Passos: 
    - Des del menu principal, premem "CONSULT"
    - Premem "LEVELS".
    
- Resultats:
    - Es mostren per pantalla els nivells importats en el seguent ordre:
        - `[Size 1] - 1 (Moniato)`
        - `[Size 2] - 2 (Moniato)`
        - `[Size 3] - 3 (Moniato)`
        - `[Size 8] - 4 (Moniato)`
    

## Generar amb l'actual com a base
    
- Fitxers de dades:

- Passos:
    - Desde el menu principal, premem "EDIT"
    - Seleccionem `[Size 3] - 3 (Moniato)`
    - Premem "Generate with Current as Base"

- Resultats:     
    - S'omplen les cel·les per resoldre el kenken
    

## Generar amb l'actual com a base erroni

- Fitxers de dades:

- Passos:
    - En `[Size 3] - 3 (Moniato)`, premem "Clear Cells"
    - Cambia la particio de la casella [2][3], de forma que les particions siguin:
        0 1 1
        2 2 1
        2 2 2
    - Premem "Generate with Current as Base"

- Resultats:     
    - Es mostra un missatge d'error "A Level cannot be generated with the current state as base, some restrictions are incompatible"

## Generar amb l'actual com a base d'un nivell no completat
    
- Fitxers de dades:

- Passos:
    - Desde el menu principal, premem "EDIT"
    - Seleccionem `[Size 3] - 3 (Moniato)`
    - Cambia les particions de cada casella a:
        0 1 1
        4 2 3
        4 2 2
    - Canvia les operacions de les particions 2 i 4:
        Particio 2: 0
        Particio 4: 0
    - Premem "Generate with Current as Base"

- Resultats:     
    - Es genera i completa el kenken correctament