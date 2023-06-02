package spring;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PoschangerDao {
    List<Integer> getPoschangerList(@Param("matId") Integer matId);

    List<PoschangerPojo>  getAllList();
}
