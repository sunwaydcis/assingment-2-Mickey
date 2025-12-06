// DataUtils.scala
import scala.io.Source
import scala.util.{Try, Using}
import java.io.File

object DataUtils {

  /**
   * Demonstrates sophisticated collection processing with:
   * - Multiple encoding fallback via List iteration
   * - Functional error handling
   * - Efficient line processing with flatMap
   * - Resource-safe file reading
   */
  def loadHotelData(filePath: String): List[HotelBooking] = {
    println(s"📂 Loading data from: $filePath")

    val file = new File(filePath)
    if (!file.exists()) {
      println(s"❌ File does not exist: $filePath")
      return List.empty
    }

    // List of encodings for fallback strategy
    val encodings = List("UTF-8", "ISO-8859-1", "Windows-1252", "UTF-16")

    // Iterate through encoding options with functional recovery
    for (encoding <- encodings) {
      println(s"   Trying encoding: $encoding")

      // Try + Using pattern for safe resource management
      Try {
        Using(Source.fromFile(filePath, encoding)) { source =>
          // Convert iterator to List for processing
          val lines = source.getLines().toList

          if (lines.isEmpty) {
            println("❌ File is empty")
            return List.empty
          }

          println(s"✅ Successfully read ${lines.size} lines with $encoding encoding")

          // Advanced collection transformation
          // .tail skips header, .flatMap filters invalid rows
          val bookings = lines.tail.flatMap { line =>
            val fields = line.split(",").map(_.trim)

            // Validate field count before processing
            if (fields.length >= 24) {
              // Functional error handling within collection operation
              Try {
                HotelBooking(
                  bookingID = fields(0),
                  dateOfBooking = fields(1),
                  time = fields(2),
                  customerID = fields(3),
                  gender = fields(4),
                  age = safeToInt(fields(5)),
                  originCountry = fields(6),
                  state = fields(7),
                  location = fields(8),
                  destinationCountry = fields(9),
                  destinationCity = fields(10),
                  noOfPeople = safeToInt(fields(11)),
                  checkInDate = fields(12),
                  noOfDays = safeToInt(fields(13)),
                  checkOutDate = fields(14),
                  rooms = safeToInt(fields(15)),
                  hotelName = fields(16),
                  hotelRating = safeToDouble(fields(17)),
                  paymentMode = fields(18),
                  bankName = fields(19),
                  bookingPrice = safeToDouble(fields(20)),
                  discount = fields(21),
                  gst = safeToDouble(fields(22)),
                  profitMargin = safeToDouble(fields(23))
                )
              }.toOption // Converts Try to Option for flatMap filtering
            } else {
              None   // Invalid row length → filtered out by flatMap
            }
          }

          println(s"🎉 Successfully loaded ${bookings.size} booking records")
          return bookings // Early return on success
        }.get
      }.recover {
        case e: Exception =>
          // Graceful error recovery for each encoding attempt
          println(s"   Failed with $encoding: ${e.getMessage.take(50)}")
      }
    }

    println("❌ Could not load data with any encoding")
    List.empty // Return empty collection as fallback
  }

  /**
   * Safe conversion utilities
   * Uses Try for functional error handling
   */
  private def safeToInt(str: String): Int = {
    // Functional error handling with default value
    Try(str.trim.toInt).getOrElse(0)
  }

  private def safeToDouble(str: String): Double = {
    // Replace on String plus Try pattern
    Try(str.trim.replace("%", "").toDouble).getOrElse(0.0)
  }

  /**
   * Demonstrates collection-based file discovery
   * - List of possible paths
   * - foreach with early return pattern
   * - Comprehensive search strategy
   */
  // File finding function - KEEP THIS AS IT WORKS!
  def findDatasetFile(): File = {
    // Ordered list of search paths
    val possiblePaths = List(
      new File("Hotel_Dataset.csv"),
      new File("./Hotel_Dataset.csv"),
      new File("src/main/resources/Hotel_Dataset.csv"),
      new File("src/main/scala/Hotel_Dataset.csv")
    )

    println("\n🔍 Searching for Hotel_Dataset.csv...")
    // Iterate with side effects, early return on success
    possiblePaths.foreach { file =>
      if (file.exists()) {
        println(s"✅ Found: ${file.getAbsolutePath}")
        return file // Early return pattern
      }
    }

    println("\n❌ File not found in any location")
    new File("Hotel_Dataset.csv") // Return default for consistent API
  }
}