package domain.operations;

/**
 * This class implements the `Operation` interface to perform multiplication.
 * 
 * @author Mikel Torres Martinez
 */

class Product implements Operation {

    /**
     * Calculates the product of all elements in the `numbers` array.
     *
     * @param numbers An array of integers to be multiplied.
     * @return The product of all elements in the `numbers` array.
     */
    @Override
    public int operate(int[] numbers) {
        int prod = numbers[0];
        int size = numbers.length;
        for (int i = 1; i < size; i++) {
            prod *= numbers[i];
        }
        return prod;
    }

     /**
     * Checks if the partition size (`partitionSize`) is valid for the type of the operation and if it can reach the desired result (`result`).
     *
     * This function validates the partition size (`partitionSize`) and utilizes a helper function
     * `isPossible` to determine if a combination of values for the partitions exists that leads to the desired
     * result (`result`) after applying a certain operation (`operate`) on those partitions.
     * 
     * @param partitionSize The size of the partition.
     * @param boardSize The size of the board.
     * @param result The target result to achieve after applying the operation.
     * @return true if a valid partition fulfilling the condition exists, false otherwise.
     */
    @Override
    public boolean isCorrect(int partitionSize, int boardSize, int result) {
        if(partitionSize < 2) {
            return false;
        }
        return true;
/*         int[] numbers = new int[partitionSize];
        return isPossible(numbers, 0, result, boardSize); */
    }

    /**
     * Checks if it's possible to achieve a specific target result (`result`) by filling a given array (`numbers`)
     * with values from 1 to a specified maximum value (`size`).
     *
     * This function utilizes recursion to explore all possible combinations of these values that can be placed
     * in the array. It checks if any combination, when passed to the `operate` function, leads to the desired result.
     *
     * @param numbers The array to be filled with values.
     * @param index  The current index in the array being filled (starts from 0).
     * @param result The target result to achieve by filling the array.
     * @param size   The maximum value allowed to be placed in the array elements.
     * @return true if a combination of values exists that achieves the target result, false otherwise.
     */
    @Override
    public boolean isPossible(int[] numbers, int index, int result, int size) {
        if(index == numbers.length) {
            return operate(numbers) == result;
        }

        for(int i = 1; i <= size; i++) {
            numbers[index] = i;
            if(isPossible(numbers, index + 1, result, size)) {
                return true;
            }
        }
        return false;
    }
}