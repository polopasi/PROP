package domain.operations;

/**
 * This class implements the `Operation` interface to perform exponentiation.
 * 
 * @author Mikel Torres Martinez
 */

class Power implements Operation {

    /**
     * Raises the first element in the numbers array to the power of the second element.
     *
     * @param numbers An array of integers containing the base and exponent.
     * @return The result of the exponentiation operation (base ^ exponent).
     */
    @Override
    public int operate(int[] numbers) {
        if (numbers.length != 2) {
            return -1;
        }
        int base = numbers[0];
        int exp = numbers[1];
        return (int) Math.pow(base, exp);
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
        if(partitionSize != 2) {
            return false;
        }
        return true;
/*         return isPossible(null, 0, result, boardSize); */
    }


    /**
     * Checks if it's possible to achieve a specific target result (`result`) by filling a given array (`numbers`)
     * with values from 1 to a specified maximum value (`size`).
     *
     * This function explore all possible combinations of these values that can be placed in the array.
     * It checks if any combination, when passed to the `operate` function, leads to the desired result.
     *
     * @param numbers Is always null (no array needed).
     * @param index  Is always 0 (no recursion needed).
     * @param result The target result to achieve by filling the array.
     * @param size   The maximum value allowed to be placed in the array elements.
     * @return true if a combination of values exists that achieves the target result, false otherwise.
     */
    @Override
    public boolean isPossible(int[] numbers, int index, int result, int boardSize) {
        for(int base = 1; base <= boardSize; base++) {
            for(int exp = 1; exp <= boardSize; exp++) {
                if(Math.pow(base, exp) == result) {
                    return true;
                }
            }
        }
        return false;
    }
}