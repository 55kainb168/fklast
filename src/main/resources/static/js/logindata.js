$(function () {
    //查询用户信息
    //没登录可以直接摆出药品和供应商，登录后才可以四个都有
    $.get("user/findOne", {}, function (data) {
        //{uid:1,name:'李四'}
        if (data != null) {
            var msg = " 欢 迎 回 来 ，" + data.cname;
            $("#span_username").html(msg).css("color", "red");
        }
    });
    //查询分类数据
    $.get("video/page/", {}, function (data) {
        var lis = '<li class="nav-active"><a href="index.html">首页</a></li>';
        //遍历数组,拼接字符串(<li>)
        for (var i = 0; i < data.length; i++) {
            var li = '<li><a href="menu_list' + i + '.html?cid=' + data[i].cid + '">' + data[i].c_name + '</a></li>';
            lis += li;
        }
        lis += '<li><a href="faq.html"></a></li>';
        // 将lis字符串，设置到ul的html内容中
        $("#category").html(lis);
    });
    //给搜索按钮绑定一个单击事件，获取搜索输入框的内容
    $("#search-button").click(function () {
        var mname = $("#search_input").val();
        //跳转路径 拼接上mname=xxx
        location.href = "menu_list" + (parseInt($("#caonima").val()) - 1) + ".html?cid=" + $("#caonima").val() + "&mname=" + mname;
    })
});