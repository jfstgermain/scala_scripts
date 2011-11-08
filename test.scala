//import org.alfresco.util.ISO9075
import scala.xml._
//println(ISO9075.encode("sdfkjhhjkh .... .<<<<"))

//val toto = <element linked={ "gef_team_JF St-Germain" } contentTextNode="sitemsdotcom_gef_article_writer"/>
/*
def fixArticleWriters(node:Node) : Node = {
          node match {
            case element @ <element/> if (element \ "@contentTextNode" text) == "sitemsdotcom_gef_article_writer" =>
              <element linked={ "gef_team_" + ISO9075.encode((element \ "@linked" text).replace("gef_team_", "")) } contentTextNode="sitemsdotcom_gef_article_writer"/>
            case Elem(prefix, label, attribs, scope, children @ _*) => Elem(prefix,
              label, attribs, scope, children map (fixArticleWriters(_)):_*)
            case other => other
          }
        }

        val a = <content parentId="23">
                      <element linked="gef_team_COCO  ..." contentTextNode="sitemsdotcom_gef_article_writer"/>
                      <element pipo="pupu"/>
                    </content>
*/
val toto = <content><element key="toto">ococ</element><element key="vdocfield-###SITE_NS###:disclosures"><![CDATA[shdfkhskfhskjf]]></element></content>                    
                    
def hasDisclosureText(contentNode :Node) = {
  (contentNode \\ "element").find {
    case element @ <element>{text}</element> if (element \ "@key" text) == "vdocfield-###SITE_NS###:disclosures" && text != "<![CDATA[]]>" => true
      case other => false
    } != None
  } 
println(hasDisclosureText(toto))       

