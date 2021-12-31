package file;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadXml {

    private static List<String> codeList=new ArrayList<>();

    public static void main(String[] args) throws Exception{
        String path="C:\\Users\\EDZ\\Desktop\\入库数据_注射用青霉素钠_101_.XML";
//        这几行是固定的，是加载这个文件，转换成对应的对象
            File f = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
//            拿到 <DataList>  里面所有的属性
        NodeList sList = doc.getElementsByTagName("Data");
//          遍历所有 <DataList>
        node(sList);

//        这个就是凑的数组
        System.out.println(codeList);
    }

    //          遍历 指定标签对应的所有节点
    public static void node(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NamedNodeMap a=node.getAttributes();
//            遍历节点所有的属性
            attri(a);

        }
    }

    //            遍历节点所有的属性
    public static void attri(NamedNodeMap a){

        for (int i = 0; i < a.getLength(); i++) {
            System.out.println(a.item(i).getNodeName()+"===="+a.item(i).getNodeValue());
//            如果要加入数组
            codeList.add(a.item(i).getNodeValue());
        }

    }

}
