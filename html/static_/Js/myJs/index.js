/**
 * Created by siney on 2017/5/11.
 */
$(function(){
    //index.html初始化
    var vm = new Vue({
        data:{
            menu:['题目','待定','关于'],                    //navigator title
            links:['problems.html','#','#'],                            //navigator link
            title:'welcome to JSUT online judge',           //the theme
            brand:'JSUT OJ',                              //the navigator brand -- on the right
            brandLink:'#',
            act1:true,                                     //first page animation active
            Sindex:-1                                       //now page index
        },
        methods:{
            enter(){
                window.location.href = 'account.html';
            }
        }
    }).$mount('#index');
    //初始化fullpage
    $("#fullpage").fullpage({
        sectionsColor:['#080','#865'],//背景的颜色设置，我们图片优先级更高
        navigationTooltips:['第一个','第二个'],//导航上显示的文字
        anchors:['page1','page2'],//添加锚链接，在地址栏可以清晰看到,
        continuousVertical:true,                   //是否垂直循环
        navigation:true,
        afterLoad:function(anchorLink,index){
            if(index==1){
                vm.act1=true;
            }
        },
        onLeave:function(index){
            if(index==1){
                vm.act1=false;
            }
        }
    });
});
