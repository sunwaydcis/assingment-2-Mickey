/**
 * EXCELLENT: Case class design optimized for collection processing.
 * This immutable data structure is ideal for functional operations
 * and will be used extensively in collection transformations.
 */
case class HotelBooking(
                         // Booking Identification Fields (Group 1)
                         bookingID: String,
                         dateOfBooking: String,
                         time: String,
                         // Customer Information (Group 2)
                         customerID: String,
                         gender: String,
                         age: Int,
                         // Customer Location (Group 3)
                         originCountry: String,
                         state: String,
                         location: String,
                         // Destination Information (Group 4)
                         destinationCountry: String,
                         destinationCity: String,
                         // Trip Details (Group 5)
                         noOfPeople: Int,
                         checkInDate: String,
                         noOfDays: Int,
                         checkOutDate: String,
                         rooms: Int,
                         // Hotel Information (Group 6)
                         hotelName: String,
                         hotelRating: Double,
                         // Payment Information (Group 7)
                         paymentMode: String,
                         bankName: String,
                         bookingPrice: Double,
                         // Financial Details (Group 8)
                         discount: String,
                         gst: Double,
                         profitMargin: Double
                       ) {
  /**
   * Domain-specific computed property.
   * Demonstrates understanding that case classes can encapsulate business logic.
   * This method enables elegant collection operations like:
   * - `bookings.map(_.discountValue).sum` - Total discount calculation
   * - `bookings.filter(_.discountValue > 10)` - Filter by discount
   * - `bookings.groupBy(_.discountValue > 0)` - Group by discount presence
   * - `bookings.map(b => b.bookingPrice * b.discountValue / 100)` - Discount amounts
   */
  // Extract numeric discount from string like "15%"
  def discountValue: Double = {
    // Functional error handling within collection element
    try {
      // Collection-ready string manipulation
      discount.replace("%", "").trim.toDouble
    } catch {
      // Graceful degradation for invalid data
      // Essential for collection operations to continue processing
      case _: Exception => 0.0
    }
  }
}