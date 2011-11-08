import java.io.{ File, FileWriter, BufferedWriter, PrintWriter }
import scala.xml._
import scala.xml.parsing._

object Pull {
  def main(args: Array[String]) {
    print("Processing...")
    val processingChars = List("/","|","\\","-")
    var formattedXml = new StringBuilder
    val prettyPrinter = new PrettyPrinter(200, 4)
    val destFile = new File("pulled_content", "contents_gef_pulled_articles_" + args(0).replace("/","-") + ".xml")
    destFile.getParentFile().mkdirs()
    val articleListFile = new FileWriter(destFile)

    // Read from article lists to pull parent node
    var articleListDoc = (ConstructingParser.fromSource(scala.io.Source.fromFile(new File("gef_corrected/contents_gef_step3_allArticleLists.xml")), false)
                                           .document.docElem \ "content") filter { _.attribute("key").get.toString == "gef-issue-"+args(0) }

    articleListFile.write("<contents>\n")
    articleListFile.write(prettyPrinter.formatNodes(articleListDoc)+"\n")

    var i = 0
    val files = (new File("gef_corrected").listFiles filter { _.getName startsWith "contents_gef_step4_allArticles_page" })
    files.foreach { file =>
      print(processingChars(i%4))
      // Read from articles to pull corresponding children
      articleListFile.write(prettyPrinter.formatNodes(((ConstructingParser.fromSource(scala.io.Source.fromFile(file), false)
        .document.docElem \ "content") filter { _.attribute("parent").get.toString == "gef-issue-"+args(0) })))
      i += 1
      print("\b")
    }                                     
    articleListFile.write("\n</contents>")

    articleListFile.close
    println("\nCompleted!")
  }
}
