import java.io.{File,FileWriter,BufferedWriter,PrintWriter}
import org.w3c.dom.{Document,Element,NodeList}
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class UpdateArticles (gef_folder = "gef", newFolder = "gef_corrected") {
	implicit def nodelistToList(val nl:org.w3c.dom.NodeList) = {
		var l:List[org.w3c.dom.Node] = Nil
		for (i <- 0 to (nl.getLength - 1)) {
			l = nl.item(i) :: l
		}
		l
	}

	def parseFiles() = {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			val (articles, articleLists) = {
				val both = new File(gef_folder).listFiles filter {_.getName startsWith "contents_"} map {file =>
					val nodes = db.parse(file).getDocumentElement.getElementsByTagName("content")
					val articleNodes = nodes filter { _.attribute("typeName").get.toString == "gef_article"}
					val articleListNodes = nodes filter { _.attribute("typeName").get.toString == "gef_article_list"}
					(articleNodes.toList, articleListNodes.toList)
				}
				val allArticles = both map {_._1} 
				val allArticleLists = both map {_._2} 
				(allArticles, allArticleLists) 
			}

		} catch {
			case _ => System.err.println(_)
		}
	}
		
		println("Updating article references to referenced(outdated/invalid) writers")
		val correctedArticles = articles map {a =>
			var article = a.toString
			try {
				val linkedWriters = a\"element" map {case e:scala.xml.Elem => e.attributes.filter{_.key == "linked"}} flatten
				
				val Contributor = """linked="gef_team_([^"]+)"""".r
				val names = linkedWriters.map {_.toString} map {  case Contributor(name) => Some(name); case _ => None } flatten
				val toUpdate = names filter {dulplicateMap.contains}
				toUpdate.foreach { n =>
					def 
				}
				if (duplicateMap.contains((a\"name").head.text)) { 
			} catch {
				case e:MatchError => System.err.println("MatchError when trying to update contributors references in: %s" format article)
			
			}
		}
		
		println("Done")
}
		
 


	
