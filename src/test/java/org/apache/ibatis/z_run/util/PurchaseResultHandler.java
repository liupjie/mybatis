package org.apache.ibatis.z_run.util;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.z_run.pojo.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MySQ
 * @date 2021/8/11 23:14
 */
// 自定义结果处理器，实现org.apache.ibatis.session.ResultHandler接口
public class PurchaseResultHandler implements ResultHandler {

    List<Purchase> purchases;

    public PurchaseResultHandler() {
        super();
        this.purchases = new ArrayList<>();
    }

    //实现接口中的方法，并在这里处理查询返回的数据
    @Override
    public void handleResult(ResultContext resultContext) {
        Purchase purchase = (Purchase) resultContext.getResultObject();
        purchases.add(purchase);
    }

    //通过get方法获取结果集
    public List<Purchase> getPurchases() {
        return purchases;
    }
}
