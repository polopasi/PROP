package domain.operations;

/**
 * This class defines the different operations that can be performed on packages.
 *
 * @author Mikel Torres Martinez
 */

public enum Operations {

  /**
   * Addition operation.
   */
  ADDITION,

  /**
   * Subtraction operation.
   */
  SUBTRACTION,

  /**
   * Product operation (multiplication).
   */
  PRODUCT,

  /**
   * Division operation.
   */
  DIVISION,

  /**
   * No operation. This can be used to indicate the absence of an operation.
   */
  NoOp,

  /**
   * Modulus operation (remainder after division).
   */
  MODULUS,

  /**
   * Power operation (a raised to the power of b).
   */
  POWER;
}
