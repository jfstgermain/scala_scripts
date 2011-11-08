import scala.xml._

//val document = scala.xml.XML.loadFile("test.xml")
val document = scala.xml.parsing.ConstructingParser.fromSource(scala.io.Source.fromFile("../vdocFields.xml"), false).document
val formattedXml = new StringBuilder
val pp = new PrettyPrinter(200, 4)

println(pp.formatNodes(document))
