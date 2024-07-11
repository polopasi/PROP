# Funciones
-   `getValidated` te devuelve un array de booleanos con el estado de todas las celdas.
    Si esa celda esta bien, es `true`, sino es `false`
    Por ejemplo, si tenemos el siguiente tablero (sin particiones)
    ```
    1 2 3
    3 2 1
    2 2 2
    ```

    Todas las celdas que tienen un 2 tendran "false" en el array, pq estan mal
- `getValidatedPartitions` hace lo mismo pero para las particiones
- 