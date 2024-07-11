package domain.operations;

/**
 * This class is the `Operation` interface.
 * 
 * @author Mikel Torres Martinez
 */

public interface Operation {

    /**
     * Performs the specified operation on the given numbers.
     * 
     * @param numbers The numbers to operate on.
     * @return The result of the operation.
     */
    public default int operate(int[] numbers) {
        return 0;
    }


     /**
     * Checks if the partition size (`partitionSize`) is valid for the type of the operation and if it can reach the desired result (`result`).
     * 
     * @param partitionSize The size of the partition.
     * @param boardSize The size of the board.
     * @param result The target result to achieve after applying the operation.
     * @return true if a valid partition fulfilling the condition exists, false otherwise.
     */
    public default boolean isCorrect(int partitionSize, int boardSize, int result) {
        return true;
    }


    /**
     * Checks if it's possible to achieve a specific result (`result`) by filling an array (`numbers`) with values
     * considering a maximum value (`boardSize`).
     * 
     * @param numbers The numbers to check.
     * @param index The current index in numbers to be filled.
     * @param result The target result to achieve.
     * @param boardSize The size of the board. The maximum value to be placed in numbers,
     * @return True if the numbers are valid, false otherwise.
     */
    public default boolean isPossible(int[] numbers, int index, int result, int boardSize) {
        return true;
    }

    /**
     * 
     * Returns an Operation object representing the specified Operations enum value.
     * 
     * @param operations The Operations enum value.
     * @return The corresponding Operation object.
     */
    public static Operation fromEnum(Operations operations) {
        switch(operations) {
            case ADDITION:
                return new Addition();
          
            case SUBTRACTION:
                return new Subtraction();
          
            case PRODUCT:
                return new Product();
          
            case DIVISION:
                return new Division();
          
            case NoOp:
                return new NoOp();
          
            case MODULUS:
                return new Modulus();
          
            default:
                return new Power();
        }
    }
}