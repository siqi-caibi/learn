package treeSem;

import org.apache.commons.codec.binary.Base64;
import treeSem.proto.ProtoKeyValue;

import java.nio.charset.StandardCharsets;


public class bg {
    public static void main(String[] args) throws Exception{
        String a="CgASXQoJCPcCGgQxNzA5CgkI-QIaBDMxNzYKCgiVAxoFMTgxMTgKCgieAxoFMTgwOTgKCgifAxoFMTgxMDQKCgihAxoFMTg0MjcKCgijAxoFNTA3MDUKCQikAxoEMzE3Mw";

        byte[] decodedBytes = (new Base64()).decode(a.getBytes(StandardCharsets.UTF_8));
        ProtoKeyValue.OList mapList = ProtoKeyValue.OList.parseFrom(decodedBytes);
        //是否会员 空串
        // 下月生日 空串
        // 会员等级   普通
        // 性别     F
        //  年龄       28-34岁
        //生日月             06
        //用户有效积分          343.33
        // 即将过期积分   否 是
        ProtoKeyValue.KeyValueList attuList=mapList.getAttu(0);
    }
}
