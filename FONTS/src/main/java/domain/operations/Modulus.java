package domain.operations;

/**
 * This class implements the `Operation` interface to perform the modulo operation.
 * 
 * @author Mikel Torres Martinez
 */

class Modulus implements Operation {
    
    /**
     * Calculates the modulo of the first element (`dividend`) by the second element (`divisor`) in the `numbers` array.
     * The array must have exactly two elements.
     *
     * @param numbers An array of integers containing the dividend and divisor. The array must have exactly two elements.
     * @return The remainder of the division (`dividend % divisor`).
     */
    @Override
    public int operate(int[] numbers) {
        if (numbers.length != 2) {
            return -1;
        }
        int dividend = numbers[0];
        int divisor = numbers[1];
        if (divisor == 0) {
            return -1;
        }
        return dividend % divisor;
    }

    /**
     * Checks if the partition size (`partitionSize`) is valid for the type of the operation and if it can reach the desired result (`result`).
     * 
     * @param partitionSize The size of the partition.
     * @param boardSize The size of the board.
     * @param result The target result to achieve after applying the operation.
     * @return true if a valid partition fulfilling the condition exists, false otherwise.
     */
    @Override
    public boolean isCorrect(int partitionSize, int boardSize, int result) {
        if(partitionSize != 2 || result > boardSize) {
            return false;
        }
        return true;

/*         int[] numbers = new int[2];
        return isPossible(numbers, 0, result, boardSize); */
    }

    /**
     * Checks if it's possible to achieve a specific target result (`result`) by filling a given array (`numbers`)
     * with values from 1 to a specified maximum value (`size`).
     *
     * This function explore all possible combinations of these values that can be placed in the array.
     * It checks if any combination, when passed to the `operate` function, leads to the desired result.
     *
     * @param numbers The array to be filled with values.
     * @param index  Is always 0 (no recursion needed).
     * @param result The target result to achieve by filling the array.
     * @param size   The maximum value allowed to be placed in the array elements.
     * @return true if a combination of values exists that achieves the target result, false otherwise.
     */
    @Override
    public boolean isPossible(int[] numbers, int index, int result, int boardSize) {
        for(int i = boardSize; i > boardSize/2; i--){
            for(int j = 1; j <= boardSize/2; j++) {
                numbers[0] = i;
                numbers[1] = j;
                if(operate(numbers) != -1) {
                    return true;
                }
            }
        }
        return false;
    }
}