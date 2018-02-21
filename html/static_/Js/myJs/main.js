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
       success:function(status,data){
           vm = new Vue({
               data:{
                   nowIndex:0,
                   title:['系统管理','公告管理','题目管理','比赛管理','小组管理'],
                   menu:[['服务器管理','用户管理'],['公告设置'],['题目列表','新建题目'],['比赛信息','新建比赛'],['组列表','查看小组']], //后面links和icon都是根据menu进行循环的
                   links:[['#','#'],['#','#'],['#'],['#','#'],['#','#'],['#','#']],      //每个menu对应的网址
                   userlinks:'#',                                                              //个人信息的网址
                   brandLink:'#',                                                              //Logo的网址
                   searchContent:'#',                                                                 //搜索框 内容
                   returnPage:'index.html',
                   nums:[0,2,3,5,7],  //每个子标签的编号{{nums[index1]+index2}}
                   brand:'JSUT',
                   icon:[
                       [['glyphicon','glyphicon-briefcase'],['glyphicon','glyphicon-user']],
                       [['glyphicon','glyphicon-tag']],
                       [['glyphicon','glyphicon-th-list'],['glyphicon','glyphicon-hand-right']],
                       [['glyphicon','glyphicon-th-list'],['glyphicon','glyphicon-hand-right']],
                       [['glyphicon','glyphicon-th-list'],['glyphicon','glyphicon-eye-open']]
                   ]
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
                                   type:'get',
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
           }).$mount('#admin');

           //取消loading画面
           $('.load').css({'display':'none'});
           $('#admin').animate({'opacity':'1'},500);
       },
       error:function(){

       }

   });


});