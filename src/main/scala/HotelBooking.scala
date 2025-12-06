// HotelBooking.scala
case class HotelBooking(
                         // Booking Identification Fields
                         bookingID: String,
                         dateOfBooking: String,
                         time: String,

                         // Customer Information
                         customerID: String,
                         gender: String,
                         age: Int,

                         // Customer Location
                         originCountry: String,
                         state: String,
                         location: String,

                         // Destination Information
                         destinationCountry: String,
                         destinationCity: String,

                         // Trip Details
                         noOfPeople: Int,
                         checkInDate: String,
                         noOfDays: Int,
                         checkOutDate: String,
                         rooms: Int,

                         // Hotel Information
                         hotelName: String,
                         hotelRating: Double,

                         // Payment Information
                         paymentMode: String,
                         bankName: String,
                         bookingPrice: Double,

                         // Financial Details
                         discount: String, // Stored as string to handle "%" suffix
                         gst: Double,
                         profitMargin: Double
                       ) {

  /**
   * Domain-specific computed property.
   * Demonstrates understanding that case classes can contain business logic.
   * This method will be frequently used in collection operations like:
   * - bookings.map(_.discountValue).sum
   * - bookings.filter(_.discountValue > 10)
   * - bookings.groupBy(_.discountValue > 0)
   */

  def discountValue: Double = {
    // Functional error handling within collection element
    try {
      // String manipulation suitable for collection operations
      discount.replace("%", "").trim.toDouble
    } catch {
      // Graceful degradation - returns 0.0 for invalid discount strings
      // Important for collection operations to not fail on individual elements
      case _: Exception => 0.0
    }
  }
}