package spring;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * 使用的包
 *       <dependency>
 *                  <groupId>org.apache.httpcomponents</groupId>
 *                  <artifactId>httpclient</artifactId>
 *                  <version>4.5.3</version>
 *                  </dependency>
 *     <dependency>
 *             <groupId>org.apache.httpcomponents.client5</groupId>
 *             <artifactId>httpclient5</artifactId>
 *             <version>5.1.1</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>cn.hutool</groupId>
 *             <artifactId>hutool-crypto</artifactId>
 *             <version>5.8.5</version>
 *         </dependency>
 *          <dependency>
 *             <groupId>com.alibaba</groupId>
 *             <artifactId>fastjson</artifactId>
 *             <version>1.2.73</version>
 *         </dependency>
 */
public class HttpTest {
  private static final String coM="https://cdp-dev.gmicdp.com.cn/data-api/";
  private static final String url1=coM+"member";
  private static final String url2=coM+"member-trend";
  private static final String url3=coM+"transaction";
  private static final String url4=coM+"transaction-trend";
  private static final String url5=coM+"transaction-channel-order";
  private static final String url6=coM+"transaction-channel-trend";
  private static final String url7=coM+"active-coupon";

  private static final Map<String,String> urlInfo=new HashMap<>();
  static {
    urlInfo.put(url1,"用户数据");
    urlInfo.put(url2,"用户数据趋势");
    urlInfo.put(url3,"交易数据");
    urlInfo.put(url4,"交易数据趋势");
    urlInfo.put(url5,"交易渠道排行");
    urlInfo.put(url6,"交易渠道排行趋势");
    urlInfo.put(url7,"活动券数据");
  }

  public static void main(String[] args) throws Exception {
    //post();
//    get();
    postestroot();
  }





  protected static Map<String, List<String>> urlDecoded(String urlencoded)
      throws UnsupportedEncodingException {
    if (StrUtil.isBlank(urlencoded)) return Collections.emptyMap();

    List<String> splits = StrUtil.split(urlencoded, "&");
    Map<String, List<String>> queryParams = new HashMap<>(splits.size(), 1);

    for (String split : splits) {
      List<String> params = StrUtil.split(split, "=");
      if (params.size() > 2) {
        throw new IllegalArgumentException(String.format("非法的查询参数：%s", split));
      }
      String key = URLDecoder.decode(params.get(0), "utf-8");
      String value = params.size() == 2 ? URLDecoder.decode(params.get(1), "utf-8") : "";

      queryParams.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    // 查询参数值排序
    queryParams.forEach((k, v) -> v.sort(String::compareTo));
    return queryParams;
  }


  public static void postestroot() throws Exception{
    List<String> dateType=new ArrayList<>();
    dateType.add("DAY");
    dateType.add("WEEK");
    dateType.add("MONTH");
    for (String dateT : dateType) {
      postest(dateT);
    }
  }
  public static void postest(String typeD) throws Exception{


    Map<String,Object> bodyMap=new HashMap<>();
    bodyMap.put("type","2");
    bodyMap.put("id","100006");
    bodyMap.put("dateType",typeD);
    bodyMap.put("starTime","2022-12-19 00:00:00");
    bodyMap.put("endTime","2022-12-19 23:59:59");

    Map<String,Object> bodyMap2=new HashMap<>();
    bodyMap2.put("type","2");

    List<String> tags=new ArrayList<String>();
    tags.add("100006");
    tags.add("100007");tags.add("100008");
    bodyMap2.put("id",tags);
    bodyMap2.put("dateType",typeD);
    bodyMap2.put("starTime","2022-12-19 00:00:00");
    bodyMap2.put("endTime","2022-12-19 23:59:59");

    Map<String,Object> bodyMap3=new HashMap<>();
    bodyMap3.put("activityId","1");
    bodyMap3.put("couponId","1");
    bodyMap3.put("starTime","2022-12-19 00:00:00");
    bodyMap3.put("endTime","2022-12-19 23:59:59");

    List<String> urlD=new ArrayList<>();
    urlD.add(url1);urlD.add(url3);urlD.add(url5);
    List<String> urlT=new ArrayList<>();
    urlT.add(url2);urlT.add(url4);urlT.add(url6);


    List<CellMap> cellMaps=new ArrayList<>();
    for (String s : urlD) {
      cellMaps.add(post(s,bodyMap));
    }

    for (String s : urlT) {
     cellMaps.add( post(s,bodyMap2));
    }

    cellMaps.add(post(url7,bodyMap3));

    excel(cellMaps);

  }


  public static CellMap post( String fullUrl,Map<String,Object> bodyMap) throws IOException {
// 请求参数
//    String site = "https://cdp-dev.gmicdp.com.cn/tag-query";
//    String url="/api/tenant/GMI/tags";
    String url=fullUrl.substring(coM.length()-1);
    //String fullUrl=site+url;
// 创建 GET 请求对象
    HttpPost httpPost = new HttpPost(fullUrl);
// 构建对象


// 创建 字符串实体对象
    HttpEntity httpEntity = new StringEntity(JSON.toJSONString(bodyMap),"application/json","utf-8");

    httpPost.setEntity(httpEntity);

    String time=String.valueOf(System.currentTimeMillis());
    String clientId="63f2e74be4b005283afaa60d";


    httpPost.setHeader("X-Version","v3.0");
    httpPost.setHeader("X-ClientId",clientId);
    httpPost.setHeader("X-Timestamp",time);
    httpPost.setHeader("X-Signature",buildSign(Collections.emptyMap(),bodyMap,time,url,clientId));
    // 获取默认配置的 HttpClient
    CloseableHttpClient httpClient = HttpClients.createDefault();
// 发送 POST 请求
    CloseableHttpResponse res=httpClient.execute(httpPost);
    String resStr=EntityUtils.toString(res.getEntity(),"utf-8");

    //System.out.println(resStr);
    StringBuilder header=new StringBuilder();
    for (Header allHeader : httpPost.getAllHeaders()) {
      header.append(allHeader);
    }
    CellMap cellMap=new CellMap(fullUrl,header.toString(),JSONObject.toJSONString(bodyMap),resStr);
    return cellMap;
  }

  private static void excel( List<CellMap> cellMaps)throws Exception{
    if (cellMaps.size()<1){
      return;
    }
    String url="E:\\javaProject\\learn\\test.xlsx";

    Resource resource = new ClassPathResource(url);

    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(url));
    XSSFSheet sheetSource = wb.getSheet("接口测试");


    //sheetSource.removeRow(sheetSource.getRow(1));
    //int rowii = 1;
    int rowii = sheetSource.getLastRowNum()+1;
    int size=rowii+cellMaps.size();
    for (int rowi =rowii; rowi <  size; rowi++) {

      CellMap pojo = cellMaps.get(rowi-rowii );
      Row row1 = sheetSource.createRow(rowi);
      row1.setHeight((short)(70*20));
      // Create a cell and put a value in it.
      Cell cell0 = row1.createCell(0);
      cell0.setCellValue("");
      Cell cell1 = row1.createCell(1);
      cell1.setCellValue("api_test_"+rowi);
      Cell cell2 = row1.createCell(2);
      cell2.setCellValue("测试接口调用");
      Cell cell3 = row1.createCell(3);
      cell3.setCellValue("接口获取指定数据");
      Cell cell4 = row1.createCell(4);
      cell4.setCellValue("p0");
      Cell cell5 = row1.createCell(5);
      cell5.setCellValue(urlInfo.get(pojo.getUrl()));
      Cell cell6 = row1.createCell(6);
      cell6.setCellValue("POST");
      Cell cell7 = row1.createCell(7);
      cell7.setCellValue(pojo.getUrl());
      Cell cell8 = row1.createCell(8);
      cell8.setCellValue(pojo.getHead());
      Cell cell9 = row1.createCell(9);
      cell9.setCellValue("无");
      Cell cell10 = row1.createCell(10);
      cell10.setCellValue(pojo.getRequest());
      Cell cell11 = row1.createCell(11);
      cell11.setCellValue(pojo.getResponse());
      Cell cell12 = row1.createCell(12);
      cell12.setCellValue("pass");
    }


    String url2="E:\\javaProject\\learn\\test.xlsx";
    File file = new File(url2);

    if(!file.exists()) {
      file.createNewFile();
    }
    try (FileOutputStream fileOut = new FileOutputStream(file)) {
      wb.write(fileOut);

    }

  }


  static class CellMap{
    private String url;
    private String head;
    private String request;
    private String response;




    public CellMap(String url, String head, String request, String response) {
      this.url = url;
      this.head = head;
      this.request = request;
      this.response = response;
    }

    public String getUrl() {
      return url;
    }

    public String getHead() {
      return head;
    }

    public String getRequest() {
      return request;
    }

    public String getResponse() {
      return response;
    }
  }

  private static String buildSign(Map<String, List<String>> queryParams,Map<String, Object> bodyParams,String time,String url,String clientId){




    // 拓展参数
    Map<String, String> expandParams = new HashMap<>();
    expandParams.put("secret", "5259e1ed4f5b449ab316d915ae6d7596");
    expandParams.put("timestamp", time);
    expandParams.put("url", url);
    // 参数排序
    Map<String, Object> sortParams = new TreeMap<>(String::compareTo);
    sortParams.putAll(queryParams);
    sortParams.putAll(bodyParams);
    sortParams.putAll(expandParams);

    // 拼接参数字符
    String paramsStr = sortParams.entrySet()
        .stream()
        .map(entry -> {
          Object value = entry.getValue();

          String valueStr;
          if (ClassUtil.isBasicType(value.getClass()) || value instanceof CharSequence) {
            valueStr = value.toString();
          } else {
            valueStr = JSONObject.toJSONString(value);
          }

          return entry.getKey() + "=" + valueStr;
        })
        .collect(Collectors.joining("&"));

    // 生成签名
    String ciphertext = SecureUtil.sha1(paramsStr);
    String plaintext = clientId + " " + ciphertext;
    return Base64.getEncoder().encodeToString(StrUtil.bytes(plaintext));
  }



  public static void get() throws IOException {
// 请求参数
    String site = "https://cdp-dev.gmicdp.com.cn/tag-query";
    String url="/api/tenant/GMI/tags/172";
    String fullUrl=site+url;


    List<NameValuePair> parameters =new ArrayList<>();
    NameValuePair nameValuePair=new BasicNameValuePair("userIdType","WECHAT_UNION_ID");
    NameValuePair nameValuePair2=new BasicNameValuePair("userId","onoRB5tYguiGt1odI1dWpy_EFv7A");
    parameters.add(nameValuePair);parameters.add(nameValuePair2);
    String param=EntityUtils.toString(new UrlEncodedFormEntity(parameters,"utf-8"));

    String time=String.valueOf(System.currentTimeMillis());
    String clientId="63f2e74be4b005283afaa60d";


//    HttpParams httpParams=new BasicHttpParams();
//    httpParams.setParameter("userIdType","WECHAT_UNION_ID");
//    httpParams.setParameter("userId","onoRB5tYguiGt1odI1dWpy_EFv7A");
// 创建 GET 请求对象
    HttpGet httpGet = new HttpGet(fullUrl+"?"+param);
//    httpGet.setParams(httpParams);
    httpGet.setHeader("X-Version","v3.0");
    httpGet.setHeader("X-ClientId",clientId);
    httpGet.setHeader("X-Timestamp",time);

    Map<String, List<String>> query=urlDecoded(param);
    httpGet.setHeader("X-Signature",buildSign(query,Collections.emptyMap(),time,url,clientId));
    // 获取默认配置的 HttpClient
    CloseableHttpClient httpClient = HttpClients.createDefault();
// 发送 POST 请求
    CloseableHttpResponse res=httpClient.execute(httpGet);
    String resStr=EntityUtils.toString(res.getEntity(),"utf-8");
    System.out.println(resStr);

  }
}
