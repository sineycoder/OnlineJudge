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
    $.ajax({
        url:'1.txt',
        type:'get',
        success:function(){
            vm = new Vue({
                data:{
                    nowIndex:0,
                    menu:['题库','排名','提交','比赛','小组','帮助','设置','退出'], //后面links和icon都是根据menu进行循环的
                    returnPage:'index.html',                        //右上角返回主界面地址
                    brand:'JSUT',                                   //左上角标题
                    links:['problem.html','#','#','#','#','#','#'],                //每个标签的链接
                    brandLink:'#',                                  //左上角标题链接
                    searchContent:'#',                              //搜索框的内容
                    imgSrc:'../Images/default_portrait.jpg',        //用户图片地址
                    icon:[                                          //导航条的图片
                        ['glyphicon','glyphicon-th-list'],['glyphicon','glyphicon-signal'],
                        ['glyphicon','glyphicon-pencil'],['glyphicon','glyphicon-stats'],
                        ['glyphicon','glyphicon-user'],['glyphicon','glyphicon-question-sign'],
                        ['glyphicon','glyphicon-wrench'],['glyphicon','glyphicon-exclamation-sign']
                    ],
                    problemTabs:[                                   //只能写70个汉子
                        {title:'所有题库1',content:'这里面包含了所有题目，可以尽情挥洒你的水平！1',link:'#1',difficulty:'4',problem:'4'},
                        {title:'所有题库2',content:'这里面包含了所有题目，可以尽情挥洒你的水平！2',link:'#2',difficulty:'4',problem:'6'},
                        {title:'所有题库3',content:'这里面包含了所有题目，可以尽情挥洒你的水平！3',link:'#3',difficulty:'5',problem:'12'},
                        {title:'所有题库4',content:'这里面包含了所有题目，可以尽情挥洒你的水平！4',link:'#4',difficulty:'7',problem:'32'},
                        {title:'所有题库5',content:'这里面包含了所有题目，可以尽情挥洒你的水平！5',link:'#5',difficulty:'8',problem:'23'},
                        {title:'所有题库6',content:'这里面包含了所有题目，可以尽情挥洒你的水平！6',link:'#6',difficulty:'6',problem:'11'},
                        {title:'所有题库7',content:'这里面包含了所有题目，可以尽情挥洒你的水平！7',link:'#7',difficulty:'9',problem:'23'},
                        {title:'所有题库8',content:'这里面包含了所有题目，可以尽情挥洒你的水平！8',link:'#8',difficulty:'10',problem:'12'},
                        {title:'所有题库1',content:'这里面包含了所有题目，可以尽情挥洒你的水平！1',link:'#1',difficulty:'4',problem:'4'},
                        {title:'所有题库2',content:'这里面包含了所有题目，可以尽情挥洒你的水平！2',link:'#2',difficulty:'4',problem:'22'},
                        {title:'所有题库3',content:'这里面包含了所有题目，可以尽情挥洒你的水平！3',link:'#3',difficulty:'5',problem:'1'},
                        {title:'所有题库4',content:'这里面包含了所有题目，可以尽情挥洒你的水平！4',link:'#4',difficulty:'7',problem:'22'},
                        {title:'所有题库5',content:'这里面包含了所有题目，可以尽情挥洒你的水平！5',link:'#5',difficulty:'8',problem:'4'},
                        {title:'所有题库6',content:'这里面包含了所有题目，可以尽情挥洒你的水平！6',link:'#6',difficulty:'6',problem:'4'},
                        {title:'所有题库7',content:'这里面包含了所有题目，可以尽情挥洒你的水平！7',link:'#7',difficulty:'9',problem:'4'},
                        {title:'所有题库8',content:'这里面包含了所有题目，可以尽情挥洒你的水平！8',link:'#8',difficulty:'10',problem:'4'}
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
            }).$mount('#problem');
            $('.load').css({'display':'none'});
            $('#problem').animate({'opacity':'1'},500);
        },
        error:function(){

        }

    });
});
