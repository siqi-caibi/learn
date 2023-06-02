package spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PoschangerService {
    @Autowired
    PoschangerDao poschangerDao;


    //不停从数据库中查
    public void test(Integer matId) {
        List<Integer> currentMatidList = poschangerDao.getPoschangerList(matId);
        if (currentMatidList==null||currentMatidList.size()==0){
            return;
        }
        //组装结果展示字符串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(matId).append("->的父级是->");
        for (int i = 0; i < currentMatidList.size(); i++) {
            stringBuilder.append(currentMatidList.get(i));
            if (i < currentMatidList.size() - 1) {
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());
        //把结果递归
        for (Integer currentMatId : currentMatidList) {
            test(currentMatId);
        }

    }


}
