package domain.operations;

/**
 * This class implements the `Operation` interface as a no-operation (NoOp).
 * It simply returns the first element in the input array.
 * 
 * @author Mikel Torres Martinez
 */

class NoOp implements Operation {

    /**
     * Returns the first element in the `numbers` array.
     *
     * @param numbers An array of integers. The array must have length == 1.
     * @return The first element in the `numbers` array.
     */
    @Override
    public int operate(int[] numbers) {
        return numbers[0];
    }

    /**
     * Checks if the partition size (`partitionSize`) is valid and if it can reach the desired result (`result`).
     *
     * It simply validates if the desired result is achievable within the board size constraint (`result <= boardSize`).
     *
     * @param partitionSize The size of the partition.
     * @param boardSize The size of the board.
     * @param result The target result to achieve after applying the operation.
     * @return true if the result is achievable within the board size, false otherwise.
     */
    @Override
    public boolean isCorrect(int partitionSize, int boardSize, int result) {
        if(partitionSize != 1 || result > boardSize) {
            return false;
        }
        return isPossible(null, 0, result, boardSize);
    }

    /**
     * Checks if the expected result is in the range of boardSize
     *
     * @param numbers Is null (no array needed).
     * @param index  Is always 0 (no recursion needed).
     * @param result The target result to achieve by filling the array.
     * @param size   The maximum value allowed to be placed in the array elements.
     * @return true if a combination of values exists that achieves the target result, false otherwise.
     */
    @Override
    public boolean isPossible(int[] numbers, int index, int result, int boardSize) {
        return result <= boardSize;
    }
}