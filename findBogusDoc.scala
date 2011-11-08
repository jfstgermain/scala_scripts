import java.io.{ File, FileWriter, BufferedWriter, PrintWriter }
import scala.collection.JavaConverters._
import org.apache.commons.io.FileUtils
import scala.collection.mutable._

object Debug {
  def main(args: Array[String]) {
    val logFile = FileUtils.readLines(new File(args(0))).asScala
    val begins = new HashSet[String]
    val ends = new HashSet[String]
    /*
    val beginRegex = """.*Processing BEGIN:\s*(\d{2}-\d{2}-\d{2}).*""".r
    val endRegex = """.*Processing END:\s*(\d{2}-\d{2}-\d{2}).*""".r
    */
    val beginRegex = """.*Processing BEGIN:\s*(.*)""".r
    val endRegex = """.*Processing END:\s*(.*)""".r

    
    println("Scanning freemarker.log for imcomplete node processing")
    logFile foreach { line =>
      line match {
        case beginRegex(date) => begins += date
        case endRegex(date) => ends += date
        case _ => /* do nothing (skip) */
      }
    }
    begins foreach { date => 
      if (!ends.contains(date)) 
        println("Found: " + date)
    }
  }
}
