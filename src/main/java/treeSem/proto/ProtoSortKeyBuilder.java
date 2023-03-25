package treeSem.proto;

import java.util.Map;
import java.util.TreeMap;

public class ProtoSortKeyBuilder {
    private ProtoKeyValue.OList.Builder oListBuilder = ProtoKeyValue.OList.newBuilder();
    private ProtoKeyValue.KeyValueList.Builder tagKvListBuilder = ProtoKeyValue.KeyValueList.newBuilder();
    private ProtoKeyValue.KeyValueList.Builder attrKvListBuilder = ProtoKeyValue.KeyValueList.newBuilder();
    private ProtoKeyValue.KeyValue.Builder tagKvBuilder = ProtoKeyValue.KeyValue.newBuilder();
    private ProtoKeyValue.KeyValue.Builder attrKvBuilder = ProtoKeyValue.KeyValue.newBuilder();
    private TreeMap<Integer, String> tagRawKVBuffer = new TreeMap<>();
    private TreeMap<Integer, String> attrRawKVBuffer = new TreeMap<>();
    private TreeMap<Integer, Integer> tagEncodeKVBuffer = new TreeMap<>();
    private TreeMap<Integer, Integer> attrEncodeKVBuffer = new TreeMap<>();

    public void clear(){
        tagRawKVBuffer.clear();
        tagEncodeKVBuffer.clear();
        attrRawKVBuffer.clear();
        attrEncodeKVBuffer.clear();
    }
    //todo putTag
    public void putTag(Integer key, String value) {
        tagRawKVBuffer.put(key, value);
    }

    public void putTag(Integer key, Integer value) {
        tagEncodeKVBuffer.put(key, value);
    }

    public void put(Integer key, String value) {
        attrRawKVBuffer.put(key, value);
    }

    public void put(Integer key, Integer value) {
        attrEncodeKVBuffer.put(key, value);
    }

    public byte[] toByteArray() {
        oListBuilder.clear();
        tagKvListBuilder.clear();
        attrKvListBuilder.clear();

        //todo tagKvListBuilder
        for (Map.Entry<Integer, String> entry : tagRawKVBuffer.entrySet()) {
            tagKvBuilder.clear();
            tagKvBuilder.setKey(entry.getKey());
            tagKvBuilder.setValue(entry.getValue());
            tagKvListBuilder.addList(tagKvBuilder.build());
        }
        for (Map.Entry<Integer, Integer> entry : tagEncodeKVBuffer.entrySet()) {
            tagKvBuilder.clear();
            tagKvBuilder.setKey(entry.getKey());
            tagKvBuilder.setValueId(entry.getValue());
            tagKvListBuilder.addList(tagKvBuilder.build());
        }

        //attrKvListBuilder
        for (Map.Entry<Integer, String> entry : attrRawKVBuffer.entrySet()) {
            attrKvBuilder.clear();
            attrKvBuilder.setKey(entry.getKey());
            attrKvBuilder.setValue(entry.getValue());
            attrKvListBuilder.addList(attrKvBuilder.build());
        }
        for (Map.Entry<Integer, Integer> entry : attrEncodeKVBuffer.entrySet()) {
            attrKvBuilder.clear();
            attrKvBuilder.setKey(entry.getKey());
            attrKvBuilder.setValueId(entry.getValue());
            attrKvListBuilder.addList(attrKvBuilder.build());
        }

        //todo oListBuilder
        oListBuilder.addTag(tagKvListBuilder);
        oListBuilder.addAttu(attrKvListBuilder);

        return oListBuilder.build().toByteArray();
    }

    @Override
    public String toString() {
        return "tagRawKVBuffer:"+tagRawKVBuffer + ",tagEncodeKVBuffer:"+tagEncodeKVBuffer + ",attrRawKVBuffer:"+attrRawKVBuffer+ ",attrEncodeKVBuffer:" +attrEncodeKVBuffer;
    }
}
