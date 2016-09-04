package com.example.user.stuqexample.api;

import com.example.user.stuqexample.model.TopUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhaibingjie on 16/8/31.
 */
public interface KanzhihuService {

    @GET("topuser/agree/{page}/{size}")
    Call<List<TopUser>> getAgreeUsers(@Path("page") int page, @Path("size") int size);

    /*topuser
        用途：获取某项指标排名前列的用户列表（分页）
        参数：指标类型、页数（可选，最小为1）、每页条数（可选，最小为1，最大为50）
        返回值：
        count(number)，用户数量
        topuser(array)，用户列表，字段如下：
        id(string)：用户ID
        name(string)：用户名称
        hash(string)：用户hash
        avatar(string)：用户头像url
        signature(string)：用户签名
        value(number)：某项指标的值，注意，这里的字段名不是“value”，而是调用API时传递的参数“指标类型”； 如传递agree，则这里的字段名就是agree。
        如页数为空，则默认取第一页； 如每页条数为空，则默认每页30条；
        如所选范围超出了前500名，则出错并返回空值； 如所选范围刚好落在500名前后，可能返回数量会小于每页条数；
        所有指标类型列表与userdetail2的返回值里detail属性内容一致。
        描述：本API实际是对用户分析所做的按列筛选和分页处理，适用于移动客户端。如果为了完整分析数据，仍然推荐直接使用用户分析或通过其中的json数据文件进行。
        示例：
        取赞同排名前30的用户http://api.kanzhihu.com/topuser/agree/1 
        取关注排名161~200的用户http://api.kanzhihu.com/topuser/follower/5/40
      */
}
