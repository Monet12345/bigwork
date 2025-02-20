package util;

import cn.hutool.core.bean.BeanUtil;
import model.Page;
import model.PageForm;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayUtil {

    /**
     * list:需要转换的list
     * pageForm:分页参数
     * type:需要转换成page中的类型
     * total：总共几个
     */

    public static <S, T> Page<T> listToPage(List<S> list, Long pageNo, Long pageSize, Long total, Class<T> type) {
        Page<T> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(total);
        // 使用 Hutool 的 BeanUtil 复制列表中的每个对象
        List<T> copiedList = list.stream()
                .map(item -> BeanUtil.copyProperties(item, type))
                .collect(Collectors.toList());
        page.setList(copiedList);
        return page;
    }
}
