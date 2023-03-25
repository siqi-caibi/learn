package spring;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PoschangerController {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //private String url = "http://ecsb-crb-test.crcloud.com:8070/ecsb/gw/sys/rs/";
    private String url = "https://ecsb-crb.crcloud.com/ecsb/gw/sys/rs/";
    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) throws Exception {
        PoschangerController poschangerController=new PoschangerController();
        poschangerController.setUrl();
    }
    @GetMapping("test1")
    public void setUrl() throws Exception{
        boolean isstop=true;
        int curpage=0;
        while (isstop) {
            //报文  签名密钥
            //String  key="87a85e02389d478db11429526e15e8de";
            String key = "60b2a74c75684a3780f31041d6ed769e";
            Map<String, Object> attr = new TreeMap<>();
            //attr.put("Api_ID", "crc.ssdp.FPMS1.productQuery");
            //attr.put("Api_Version", "1.0");
            //attr.put("App_Sub_ID", "1200000106PI");
            //attr.put("App_Token", "43ba8c7df93942318c765021dc178cc9");
            //attr.put("ENV", "uat");
            //attr.put("Partner_ID", "12000000");
            //attr.put("Sys_ID", "12000001");
            //attr.put("User_Token", "");

            attr.put("Api_ID", "snowbeer.MDM.FPMS1.queryProduct");
            attr.put("Api_Version", "1.0");
            attr.put("App_Sub_ID", "120000230002");
            attr.put("App_Token", "47f962b7fc7140b2bdec69cacaa2a70c");
            attr.put("ENV", "prd");
            attr.put("Partner_ID", "12000000");
            attr.put("Sys_ID", "12000023");
            attr.put("User_Token", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            attr.put("Time_Stamp", sdf.format(new Date()));
            //attr.put("Time_Stamp", "2022-11-09 09:14:05:039");


            Map<String, Object> data = new HashMap<>();
            data.put("invokeSystemName", "ocms");
            data.put("size", 50);
            data.put("pageIndex", curpage);

            attr.put("REQUEST_DATA", JSONObject.toJSONString(data));

            StringBuilder ssdpStrBuild = new StringBuilder();
            for (Map.Entry<String, Object> stringStringEntry : attr.entrySet()) {
                ssdpStrBuild.append("&").append(stringStringEntry.getKey()).append("=").append(
                        stringStringEntry.getValue()
                );
            }
            ssdpStrBuild.append("&").append(key);
            ssdpStrBuild.delete(0, 1);


            String r = "Api_ID=crc.ssdp.FPMS1.productQuery&Api_Version=1.0&App_Sub_ID=1200000106PI&App_Token=43ba8c7df93942318c765021dc178cc9&ENV=uat&Partner_ID=12000000&REQUEST_DATA={\"invokeSystemName\":\"ocms\",\"size\":10,\"pageIndex\":1}&Sys_ID=12000001&Time_Stamp=2022-11-09 09:14:05:039&User_Token=&87a85e02389d478db11429526e15e8de";
            String md5Sign = DigestUtils.md5DigestAsHex(ssdpStrBuild.toString().getBytes()).toUpperCase();


            attr.remove("REQUEST_DATA");

            StringBuilder ssdpStrBuildAttr = new StringBuilder();
            for (Map.Entry<String, Object> stringStringEntry : attr.entrySet()) {
                ssdpStrBuildAttr.append("&").append(stringStringEntry.getKey()).append("=").append(
                        stringStringEntry.getValue()
                );
            }
            ssdpStrBuildAttr.delete(0, 1);

            String b = "Api_ID=crc.ssdp.FPMS1.productQuery&Api_Version=1.0&App_Sub_ID=1200000106PI&App_Token=43ba8c7df93942318c765021dc178cc9&ENV=uat&Partner_ID=12000000&Sign=NO_SIGN&Sys_ID=12000001&Time_Stamp=2022-11-09 09:14:05:039&User_Token=";
            String base64Deep = Base64Utils.encodeToString(ssdpStrBuildAttr.toString().getBytes());


            attr.put("Sign", md5Sign);

            Map<String, Map<String, Object>> requestArr = new TreeMap<>();
            requestArr.put("API_ATTRS", attr);
            requestArr.put("REQUEST_DATA", data);
            Map<String, Map<String, Map<String, Object>>> request = new TreeMap<>();
            request.put("REQUEST", requestArr);

            String urlRequest = url + "?ssdp=" + base64Deep;
            ResponseEntity<JSONObject> res = restTemplate.postForEntity(urlRequest, request, JSONObject.class);
            curpage++;

            JSONObject jsonObject = res.getBody().getJSONObject("data");
            Integer currentPage = jsonObject.getInteger("currentPage");
            Integer totalPage =
                    //2;
                    jsonObject.getInteger("totalPage");
            System.out.println(curpage+"ggg"+totalPage);
            if (currentPage.equals(totalPage)) {
            isstop=false;
            }
            //JSONObject jsonObject=JSONObject.parseObject(a,JSONObject.class);
            File file = new File("E:\\file\\雪花.csv");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);

            JSONArray items =
                    //jsonObject.getJSONObject("data").getJSONArray("items");
                    jsonObject.getJSONArray("items");
            StringBuilder str = new StringBuilder();
            for (Object item : items) {
                LinkedHashMap<String,String> v2=( LinkedHashMap<String,String>)item;
                //JSONObject v = (JSONObject) item;
                JSONObject v = new JSONObject();
                v.putAll(v2);
                for (Object value : v.values()) {
                    String vv = value.toString().replace("\"", "\"\"");
                    str.append("\"").append(vv).append("\"").append(",");
                }
                str.deleteCharAt(str.length() - 1);
                str.append("\n");
                fileWriter.write(str.toString());
                str.delete(0, str.length());
            }
            fileWriter.close();
            //System.out.println(res.getBody());
        }
    }

  /*  @GetMapping("test2")
    public void test2() throws Exception {
        REQUEST request = new REQUEST().init();

        API_ATTRS attrs = request.getAPI_ATTRS();

        Field[] fields = attrs.getClass().getDeclaredFields();

        StringBuilder ssdpStrBuild = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (i != 0) {
                ssdpStrBuild.append("&");
            }
            ssdpStrBuild.append(field.getName()).append("=").append(field.get(attrs));
        }

        System.out.println(ssdpStrBuild);


    }


    private REQUEST REQUEST;

    private static class REQUEST {
        private API_ATTRS API_ATTRS;
        private REQUEST_DATA REQUEST_DATA;

        public PoschangerController.API_ATTRS getAPI_ATTRS() {
            return API_ATTRS;
        }

        public void setAPI_ATTRS(PoschangerController.API_ATTRS API_ATTRS) {
            this.API_ATTRS = API_ATTRS;
        }

        public PoschangerController.REQUEST_DATA getREQUEST_DATA() {
            return REQUEST_DATA;
        }

        public void setREQUEST_DATA(PoschangerController.REQUEST_DATA REQUEST_DATA) {
            this.REQUEST_DATA = REQUEST_DATA;
        }

        public REQUEST init() {
            REQUEST request = new REQUEST();
            request.setAPI_ATTRS(new API_ATTRS());
            request.setREQUEST_DATA(new REQUEST_DATA());
            return request;
        }

    }

    private static class API_ATTRS {
        private String Api_ID = "crc.ssdp.FPMS1.productQuery";
        private String Api_Version = "1.0";
        private String App_Sub_ID = "1200000106PI";
        private String App_Token = "43ba8c7df93942318c765021dc178cc9";
        private String ENV = "uat";
        private String Partner_ID = "12000000";
        private String Sign;
        private String Sys_ID = "12000001";
        private String Time_Stamp;
        private String User_Token;

        public String getApi_ID() {
            return Api_ID;
        }

        public void setApi_ID(String api_ID) {
            Api_ID = api_ID;
        }

        public String getApi_Version() {
            return Api_Version;
        }

        public void setApi_Version(String api_Version) {
            Api_Version = api_Version;
        }

        public String getApp_Sub_ID() {
            return App_Sub_ID;
        }

        public void setApp_Sub_ID(String app_Sub_ID) {
            App_Sub_ID = app_Sub_ID;
        }

        public String getApp_Token() {
            return App_Token;
        }

        public void setApp_Token(String app_Token) {
            App_Token = app_Token;
        }

        public String getENV() {
            return ENV;
        }

        public void setENV(String ENV) {
            this.ENV = ENV;
        }

        public String getPartner_ID() {
            return Partner_ID;
        }

        public void setPartner_ID(String partner_ID) {
            Partner_ID = partner_ID;
        }

        public String getSign() {
            return Sign;
        }

        public void setSign(String sign) {
            Sign = sign;
        }

        public String getSys_ID() {
            return Sys_ID;
        }

        public void setSys_ID(String sys_ID) {
            Sys_ID = sys_ID;
        }

        public String getTime_Stamp() {
            return Time_Stamp;
        }

        public void setTime_Stamp(String time_Stamp) {
            Time_Stamp = time_Stamp;
        }

        public String getUser_Token() {
            return User_Token;
        }

        public void setUser_Token(String user_Token) {
            User_Token = user_Token;
        }
    }

    private static class REQUEST_DATA {

        private String invokeSystemName = "ocms";
        private Integer size = 10;
        private Integer pageIndex = 1;

        public REQUEST_DATA() {
        }
    }*/

    public static String a="{\n" +
            "\t\"msg\": \"成功\",\n" +
            "\t\"code\": 1,\n" +
            "\t\"data\": {\n" +
            "\t\t\"startIndex\": 10,\n" +
            "\t\t\"totalNum\": 22207,\n" +
            "\t\t\"totalPage\": 2221,\n" +
            "\t\t\"isMore\": 1,\n" +
            "\t\t\"pageSize\": 10,\n" +
            "\t\t\"currentPage\": 2,\n" +
            "\t\t\"items\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"mainMaterial\": \"\",\n" +
            "\t\t\t\t\"isCombination\": \"0\",\n" +
            "\t\t\t\t\"createUserId\": \"\",\n" +
            "\t\t\t\t\"packageAbbreviationName\": \"纸箱瓶\",\n" +
            "\t\t\t\t\"twoVarietyName\": \"勇闯天涯冰山版\",\n" +
            "\t\t\t\t\"specBottleNum\": 12,\n" +
            "\t\t\t\t\"taste\": \"A999\",\n" +
            "\t\t\t\t\"marketingCenter\": \"A\",\n" +
            "\t\t\t\t\"craft\": \"02\",\n" +
            "\t\t\t\t\"type\": \"2\",\n" +
            "\t\t\t\t\"productGrade\": \"35\",\n" +
            "\t\t\t\t\"elevenCode\": \"31014050015\",\n" +
            "\t\t\t\t\"financialCodeTip\": \"纸箱瓶\",\n" +
            "\t\t\t\t\"isMembrane\": \"0\",\n" +
            "\t\t\t\t\"mainMaterialName\": \"\",\n" +
            "\t\t\t\t\"innerPackagingExtendInfo\": {\n" +
            "\t\t\t\t\t\"bottleType\": \"雪花统一标准专用白瓶\",\n" +
            "\t\t\t\t\t\"packageUnit\": \"瓶\",\n" +
            "\t\t\t\t\t\"innerPackagingName\": \"500ml雪花统一标准专用白瓶\",\n" +
            "\t\t\t\t\t\"capacityUnit\": \"ml\",\n" +
            "\t\t\t\t\t\"capacity\": \"500\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"brand\": \"01\",\n" +
            "\t\t\t\t\"outerPackaging\": \"110\",\n" +
            "\t\t\t\t\"boxCode\": \"\",\n" +
            "\t\t\t\t\"isQrCode\": \"\",\n" +
            "\t\t\t\t\"brandName\": \"雪花\",\n" +
            "\t\t\t\t\"mainUnit\": \"A001\",\n" +
            "\t\t\t\t\"exportCountryName\": \"\",\n" +
            "\t\t\t\t\"promotionsName\": \"开箱有礼\",\n" +
            "\t\t\t\t\"cleanWaterCode\": \"31014050003162200\",\n" +
            "\t\t\t\t\"tasteName\": \"缺省\",\n" +
            "\t\t\t\t\"featuresCodeDescribe\": \"\",\n" +
            "\t\t\t\t\"oilVariety\": \"A002\",\n" +
            "\t\t\t\t\"isExport\": 0,\n" +
            "\t\t\t\t\"importCountry\": \"\",\n" +
            "\t\t\t\t\"weight\": \"\",\n" +
            "\t\t\t\t\"Packversion\": \"\",\n" +
            "\t\t\t\t\"oilVarietyName\": \"啤酒\",\n" +
            "\t\t\t\t\"financialCodeDesc\": \"雪花冰山勇闯天涯9度500ml统一标准专用白瓶1*12纸箱含瓶开箱有礼\",\n" +
            "\t\t\t\t\"elevenCodeDescribe\": \"雪花冰山勇闯天涯9度500ml统一标准专用白瓶1*12纸箱含瓶\",\n" +
            "\t\t\t\t\"innerPackaging\": \"448\",\n" +
            "\t\t\t\t\"Packversionname\": \"\",\n" +
            "\t\t\t\t\"weightUnitName\": \"KG\",\n" +
            "\t\t\t\t\"volume\": \"\",\n" +
            "\t\t\t\t\"mainUnitName\": \"箱\",\n" +
            "\t\t\t\t\"oneVarietyName\": \"勇闯天涯\",\n" +
            "\t\t\t\t\"netWeight\": 0.5,\n" +
            "\t\t\t\t\"combinations\": [],\n" +
            "\t\t\t\t\"wineBody\": \"A010\",\n" +
            "\t\t\t\t\"wineBodyName\": \"勇闯酒体\",\n" +
            "\t\t\t\t\"name\": \"雪花冰山勇闯天涯9度500ml统一标准专用白瓶1*12纸箱含瓶开箱有礼\",\n" +
            "\t\t\t\t\"financialCode\": \"XHPREJ\",\n" +
            "\t\t\t\t\"region\": \"\",\n" +
            "\t\t\t\t\"volumename \": \"\",\n" +
            "\t\t\t\t\"status\": 1,\n" +
            "\t\t\t\t\"weightUnit\": \"A001\",\n" +
            "\t\t\t\t\"code\": \"31014050015162200\",\n" +
            "\t\t\t\t\"outerPackagingName\": \"1*12纸箱\",\n" +
            "\t\t\t\t\"craftName\": \"普啤\",\n" +
            "\t\t\t\t\"outerPackagingExtendInfo\": {\n" +
            "\t\t\t\t\t\"outerPackagingName\": \"1*12纸箱\",\n" +
            "\t\t\t\t\t\"material\": \"纸箱\",\n" +
            "\t\t\t\t\t\"packageUnit\": \"1*12\",\n" +
            "\t\t\t\t\t\"isPackage\": \"0\",\n" +
            "\t\t\t\t\t\"packageAloneNum\": \"1\",\n" +
            "\t\t\t\t\t\"spec\": \"缺省\",\n" +
            "\t\t\t\t\t\"packageNum\": \"12\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"regionName\": \"\",\n" +
            "\t\t\t\t\"adventStandard\": 120,\n" +
            "\t\t\t\t\"units\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"convertUnit\": \"A001\",\n" +
            "\t\t\t\t\t\t\"targetUnit\": \"A008\",\n" +
            "\t\t\t\t\t\t\"createTime\": \"2022-10-20 15:24:59\",\n" +
            "\t\t\t\t\t\t\"targetUnitName\": \"瓶\",\n" +
            "\t\t\t\t\t\t\"coefficient\": \"12\",\n" +
            "\t\t\t\t\t\t\"convertUnitName\": \"箱\",\n" +
            "\t\t\t\t\t\t\"lastUpdateUser\": \"\",\n" +
            "\t\t\t\t\t\t\"convertNum\": 1,\n" +
            "\t\t\t\t\t\t\"updateTime\": \"2022-10-20 15:24:59\",\n" +
            "\t\t\t\t\t\t\"isModify\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"convertUnit\": \"A001\",\n" +
            "\t\t\t\t\t\t\"targetUnit\": \"A003\",\n" +
            "\t\t\t\t\t\t\"createTime\": \"2022-10-20 15:22:44\",\n" +
            "\t\t\t\t\t\t\"targetUnitName\": \"吨\",\n" +
            "\t\t\t\t\t\t\"coefficient\": \"0.011\",\n" +
            "\t\t\t\t\t\t\"convertUnitName\": \"箱\",\n" +
            "\t\t\t\t\t\t\"lastUpdateUser\": \"\",\n" +
            "\t\t\t\t\t\t\"convertNum\": 1,\n" +
            "\t\t\t\t\t\t\"updateTime\": \"2022-10-20 15:22:44\",\n" +
            "\t\t\t\t\t\t\"isModify\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"convertUnit\": \"A001\",\n" +
            "\t\t\t\t\t\t\"targetUnit\": \"A012\",\n" +
            "\t\t\t\t\t\t\"createTime\": \"2022-10-20 15:22:43\",\n" +
            "\t\t\t\t\t\t\"targetUnitName\": \"KL\",\n" +
            "\t\t\t\t\t\t\"coefficient\": \"0.006\",\n" +
            "\t\t\t\t\t\t\"convertUnitName\": \"箱\",\n" +
            "\t\t\t\t\t\t\"lastUpdateUser\": \"\",\n" +
            "\t\t\t\t\t\t\"convertNum\": 1,\n" +
            "\t\t\t\t\t\t\"updateTime\": \"2022-10-20 15:22:43\",\n" +
            "\t\t\t\t\t\t\"isModify\": false\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"bottleCode\": \"\",\n" +
            "\t\t\t\t\"innerPackagingName\": \"500ml雪花统一标准专用白瓶\",\n" +
            "\t\t\t\t\"productGradeName\": \"中档\",\n" +
            "\t\t\t\t\"packageAbbreviation\": \"A011\",\n" +
            "\t\t\t\t\"relationWhiteboardCode\": \"31014050015000000\",\n" +
            "\t\t\t\t\"twoVariety\": \"A187\",\n" +
            "\t\t\t\t\"actualCapacity\": \"500\",\n" +
            "\t\t\t\t\"isDeleted\": 0,\n" +
            "\t\t\t\t\"weightname \": \"\",\n" +
            "\t\t\t\t\"variety\": \"588\",\n" +
            "\t\t\t\t\"isWithBottle\": \"1\",\n" +
            "\t\t\t\t\"salesChannelsName\": \"\",\n" +
            "\t\t\t\t\"regionalSupply\": \"\",\n" +
            "\t\t\t\t\"varietyName\": \"勇闯天涯冰山版\",\n" +
            "\t\t\t\t\"featuresCode\": \"162200\",\n" +
            "\t\t\t\t\"expirationDate\": \"360\",\n" +
            "\t\t\t\t\"isImport\": 0,\n" +
            "\t\t\t\t\"salesChannels\": \"\",\n" +
            "\t\t\t\t\"marketingCenterName\": \"普通促销\",\n" +
            "\t\t\t\t\"originalGravity\": \"090\",\n" +
            "\t\t\t\t\"lastUpdateUserId\": \"\",\n" +
            "\t\t\t\t\"materialType\": \"产成品\",\n" +
            "\t\t\t\t\"isNationwide\": \"0\",\n" +
            "\t\t\t\t\"relationWhiteboard\": \"雪花冰山勇闯天涯9度500ml统一标准专用白瓶1*12纸箱含瓶\",\n" +
            "\t\t\t\t\"updateTime\": \"2022-10-22 00:01:00\",\n" +
            "\t\t\t\t\"exportCountry\": \"\",\n" +
            "\t\t\t\t\"individualization\": \"\",\n" +
            "\t\t\t\t\"oneVariety\": \"A004\",\n" +
            "\t\t\t\t\"picture\": \"\",\n" +
            "\t\t\t\t\"importCountryName\": \"\",\n" +
            "\t\t\t\t\"promotions\": \"开箱有礼\",\n" +
            "\t\t\t\t\"grossWeight\": 0.916667,\n" +
            "\t\t\t\t\"createTime\": \"2022-10-22 00:01:00\",\n" +
            "\t\t\t\t\"packageCode\": \"\",\n" +
            "\t\t\t\t\"originalGravityName\": \"9度\",\n" +
            "\t\t\t\t\"organizations\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"isEnableBatch\": \"1\",\n" +
            "\t\t\t\t\t\t\"regionName\": \"\",\n" +
            "\t\t\t\t\t\t\"organizationValue\": \"\",\n" +
            "\t\t\t\t\t\t\"ocmsWhiteCodeDescribe\": \"雪花冰山勇闯天涯9度500ml统一标准专用白瓶1*12纸箱含瓶\",\n" +
            "\t\t\t\t\t\t\"type\": \"自产\",\n" +
            "\t\t\t\t\t\t\"organizationNameStr\": \"B02_CB000_华润啤酒物料主组织\",\n" +
            "\t\t\t\t\t\t\"ownOrganizationValue\": \"CBCH0\",\n" +
            "\t\t\t\t\t\t\"ocmsFeaturesCodeDescribe\": \"\",\n" +
            "\t\t\t\t\t\t\"ocmsWhiteType\": \"2\",\n" +
            "\t\t\t\t\t\t\"area\": \"11\",\n" +
            "\t\t\t\t\t\t\"ocmsFeaturesCode\": \"162200\",\n" +
            "\t\t\t\t\t\t\"cleanWaterCode\": \"31014050003162200\",\n" +
            "\t\t\t\t\t\t\"erpCreateTime\": \"\",\n" +
            "\t\t\t\t\t\t\"updateTime\": \"\",\n" +
            "\t\t\t\t\t\t\"isMainOrganization\": \"是\",\n" +
            "\t\t\t\t\t\t\"costAccountName\": \"主营业务成本-商品成本-生产类-原料成本\",\n" +
            "\t\t\t\t\t\t\"saleAccountName\": \"主营业务收入-商品收入-生产类\",\n" +
            "\t\t\t\t\t\t\"ocmsWhiteCode\": \"31014050015\",\n" +
            "\t\t\t\t\t\t\"orgCreateTime\": \"2022-10-08 15:10:11\",\n" +
            "\t\t\t\t\t\t\"orgStatus\": \"有效\",\n" +
            "\t\t\t\t\t\t\"organizationCode\": \"MST\",\n" +
            "\t\t\t\t\t\t\"createTime\": \"\",\n" +
            "\t\t\t\t\t\t\"saleAccount\": \"6001010301\",\n" +
            "\t\t\t\t\t\t\"lastUpdateUser\": \"\",\n" +
            "\t\t\t\t\t\t\"createUser\": \"\",\n" +
            "\t\t\t\t\t\t\"costAccount\": \"6401010301\",\n" +
            "\t\t\t\t\t\t\"region\": \"\",\n" +
            "\t\t\t\t\t\t\"status\": \"1\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t}\n" +
            "\t\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";
}
