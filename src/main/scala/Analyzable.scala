/**
 * Abstract method declaration defining interface contract.
 * Concrete implementations must provide this method, ensuring consistency.
 *
 * This enables patterns like:
 * - `analyzers.map(_.displayInfo()).foreach(println)`
 * - `val analyzerInfo = analyzer.displayInfo()`
 * - `analyzers.filter(_.displayInfo().contains("critical"))`
 *
 * @return Formatted string representation of analyzer information
 */
/**
 * Trait design for analyzer abstraction and polymorphism.
 * This trait enables consistent interface design across all analyzer implementations.
 */
trait Analyzable {
  def displayInfo(): String
}