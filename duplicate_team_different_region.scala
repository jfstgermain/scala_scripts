//Input file and output files - could be read from arguments.
val gef_folder = "gef" //folder containing all the GEF articles - currently a relative path (subfolder)
val outputFile = "MultipleLocationWriters.xml" //The file to output to. Current just a file within the folder it's run from.

import java.io.File
import scala.xml._
import scala.collection.mutable.Queue
import scala.xml.parsing._

val teamMembers: collection.mutable.Map[String, Seq[String]] = new collection.mutable.HashMap[String, Seq[String]] with collection.mutable.SynchronizedMap[String, Seq[String]]
val files = new File(gef_folder).listFiles filter { f => (f.getName startsWith "contents_gef_all_in_one") || (f.getName startsWith "contents_gef_featured_team") }

files.foreach(file => {
	try {
		println("Currently parsing " + file)
		//val nodes = scala.xml.XML.loadFile(file)
        val nodes = ConstructingParser.fromSource(scala.io.Source.fromFile(file)(scala.io.Codec.ISO8859), false).document.docElem
		val contributorsNodes = (nodes \ "content") filter { _.attribute("typeName").get.toString == "gef_team" }
		contributorsNodes foreach { node =>
			val name = ((node \ "element") filter { _.attribute("key").toString contains "sitepub:title" })(0).text //Relying of team members name being in sitepub:title
			val regions = (node \ "element") filter { _.attribute("key").toString contains "gef_team_region" } map { _.text }

			teamMembers.synchronized {
				if (teamMembers.isDefinedAt(name)) {
					teamMembers(name) ++= regions
				} else {
					teamMembers += ((name, regions))
				}
			}
		}
	} catch {
		case e => println("Unable to get names/regions from " + file.getName + "\n" + e.getMessage())
	}
})

println("Sorting team members by name")
val sortedTeamMembers = teamMembers.toList.sortWith((a, b) => a._1 < b._1)

println("Outputting team members regions")
val output = new java.io.FileWriter(outputFile)
val contributors = new Queue[Node]()

for ((name, regions) <- sortedTeamMembers) {
  if (name != "") {
	contributors += <contributor>
		<name>{ name }</name>
		{
			for (region: String <- regions.toSet) yield {
				val count = (regions filter { _ == region }).size.toString
				<region count={ count }>{ region }</region>
			}
		}
	</contributor>
  }
}

val formattedXml = new StringBuilder
new PrettyPrinter(200, 4).format(<contributors>{ contributors }</contributors>, formattedXml)
output.write(formattedXml.toString())
output.close
