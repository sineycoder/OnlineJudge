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
    // 与后台交互并初始化页面
    $.ajax({
            url:'1.txt',
            type:'get',
            success:function(res,name,status){
            // alert(res);//内容
            // alert(name);//名称：如success
            // alert(status.status)//状态对象
            var con='由于时间原因，某些比赛不能继续执行下去，';
            vm = new Vue({
                data:{
                    user:{userName:'siney',userSign:'i will be successful man!i will be successful man!',AC:'36.7',WA:'63.3'},
                    nowIndex:-1,                                    //当前页面角标
                    menu:['题库','排名','提交','比赛','小组','帮助','设置','退出'], //后面links和icon都是根据menu进行循环的
                    returnPage:'index.html',                        //右上角返回主界面地址
                    brand:'JSUT',                                   //左上角标题
                    links:['problem.html','#','#','#','#','#','#'],                //每个标签的链接
                    brandLink:'#',                                  //左上角标题链接
                    searchContent:'#',                              //搜索框的内容
                    imgSrc:'../Images/default_portrait.jpg',        //用户图片地址
                    notice:[
                        {title:'公告1',content:con,time:'2000:11:11'},//公告栏信息
                        {title:'公告1',content:con,time:'2000:11:11'},
                        {title:'公告1',content:con,time:'2000:11:11'},
                        {title:'公告1',content:con,time:'2000:11:11'}
                    ],
                    icon:[                                          //导航条的图片
                        ['glyphicon','glyphicon-th-list'],['glyphicon','glyphicon-signal'],
                        ['glyphicon','glyphicon-pencil'],['glyphicon','glyphicon-stats'],
                        ['glyphicon','glyphicon-user'],['glyphicon','glyphicon-question-sign'],
                        ['glyphicon','glyphicon-wrench'],['glyphicon','glyphicon-exclamation-sign']
                    ],
                    admin:{href:'#',is:true}
                },
                methods:{
                    info_:function(){
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
                                    url:'1.txt',
                                    type:'post',
                                    success:function (text,obj,status) {
                                        swal({title:'退出成功',
                                            text:'result:'+text,
                                            type:'success'
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
            }).$mount('#account');
            //画图ichart.js
            var data = [
                {name : 'AC',value : vm.user.AC,color:'#67FF7B'},
                {name : 'WA',value : vm.user.WA,color:'#FF3044'},
            ];

            new iChart.Pie2D({
                render : 'userChart',
                data: data,
                animation:true,
                shadow:true,
                animation_timing_function:'linear',
                decimalsnum:2,
                background_color:null,
                width : 190,
                height : 140,
                radius:'100%',
                title:'rate'
            }).draw();

            $('.load').css({'display':'none'});
            $('#account').animate({'opacity':'1'},500);
    },
        error:function(excep,text){//后台交互失败
            alert(excep.status);//异常对象
            alert(text)//异常内容
        }
    });

});
