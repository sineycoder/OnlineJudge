/**
 * Created by siney on 2017/5/11.
 */

window.onresize = function(){
    setSize();
}
function setSize(){
    var height = (document.documentElement.clientHeight || document.body.clientHeight) - 50;
    var width = (document.documentElement.clientWidth || document.body.clientWidth) - 205;
    document.querySelector('.leftNav').style.height = height+"px";
    document.querySelector('.rightContent').style.height = height+"px";
    document.querySelector('.rightContent').style.width = width+"px";
}
var vm;
$(function(){
    setSize();
    vm = new Vue({
        data:{
            page:{'pageNow':'10','pageNum':10},
            nowIndex:3,
            menu:["题库","排名","提交","比赛","帮助",'设置',"退出"], //后面links和icon都是根据menu进行循环的
            returnPage:"../Admin/index.html",                        //右上角返回主界面地址
            brand:"JSUT",                                   //左上角标题
            links:["problem.html","#","#","#","#"],                //每个标签的链接
            brandLink:"#",                                  //左上角标题链接
            searchContent:"#",                              //搜索框的内容
            imgSrc:"../Images/default_portrait.jpg",        //用户图片地址
            icon:[                                          //导航条的图片
                ["glyphicon","glyphicon-th-list"],["glyphicon","glyphicon-signal"],
                ["glyphicon","glyphicon-pencil"],["glyphicon","glyphicon-stats"],
                ["glyphicon","glyphicon-question-sign"],['glyphicon','glyphicon-wrench'],
                ["glyphicon","glyphicon-exclamation-sign"]
            ],
            admin:{href:"#",is:true},                        //是否显示后台窗口
            tb_title:["Date","ID","Status","Problem","Time","Memory","Language"],  //table标题
            tb_content:[                                            //table内容
                {'title':'2017新生赛aaaaaa','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'100','href':'aaa'},
                {'title':'2017新生赛bbbbbb','date':'2017-01-21 11:11:11','lock':false,'time':'3 hours','status':'Starting','participation':'20','href':'aaa'},
                {'title':'2017新生赛cccccc','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Waiting','participation':'10','href':'aaa'},
                {'title':'2017新生赛dddddd','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'20','href':'aaa'}
            ]
            /*
             <span class="glyphicon glyphicon-ok" style="color:green;"></span>    pass
             <span class="glyphicon glyphicon-minus" style="color:red;"></span>   not pass

             * */
        },
        methods:{
            info_:function(){
            },
            enter_:function(info){
                // alert(info);
            },
            exit_:function(){
                swal({title: "确定要退出吗？",
                    text: "如果确认，请点击退出",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "退出",
                    cancelButtonText: "取消",
                    closeOnConfirm: false,
                    closeOnCancel: true,
                },function(isConfirm){
                    if(isConfirm){
                        $.ajax({
                            url:"1.txt",
                            type:"get",
                            success:function (text,obj,status) {
                                swal({title:"退出成功",
                                    text:"result:"+text,
                                    type:"success"
                                },function(){
                                    window.location=vm.returnPage;
                                });
                            },
                            error:function(e1,e2){
                            }
                        });

                    }
                });
            },
            search:function () {
                alert(this.searchContent);
            }
        }
    }).$mount("#contest");
    $.ajax({
        url:"2.txt",
        type:"get",
        dataType:'json',
        success:function(res,name,status){
            $('.load').css({'display':'none'});
            $('#contest').animate({'opacity':'1'},500);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
