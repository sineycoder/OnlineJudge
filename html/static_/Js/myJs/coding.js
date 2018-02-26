/**
 * Created by siney on 2018/2/21.
 */

function setSize(){
    var height = (document.documentElement.clientHeight || document.body.clientHeight) - 60;
    document.querySelector('.contain').style.height = height+"px";
}
window.onresize = function(){
    setSize();
}
var vm;
$(function(){
    setSize();
    vm = new Vue({
        data:{
            returnPage:"../Admin/index.html",                        //右上角返回主界面地址
            brand:"JSUT",                                   //左上角标题
            brandLink:"#",                                  //左上角标题链接
            imgSrc:"../Images/default_portrait.jpg",        //用户图片地址
            admin:{href:"#",is:true},                        //是否显示后台窗口
            problemContent:[
                {title:'Description',content:'Description'},
                {title:'Input Description',content:'Input Description'},
                {title:'Output Description',content:'Output Description'},
                {title:'Sample Input',content:'Sample Input'},
                {title:'Sample Output',content:'Sample Output'},
                {title:'Data Size & Hint',content:'Data Size & Hint'}
            ],
            input1:'',
            leftLink:['problems.html','rank.html','submit.html','contest.html','#','setting.html'],
            /*
             <span class="glyphicon glyphicon-ok" style="color:green;"></span>    pass
             <span class="glyphicon glyphicon-minus" style="color:red;"></span>   not pass

             * */
        },
        methods:{

            handleSelect(key, keyPath) {
                var v = parseInt(key) - 1;
                window.location.href = this.leftLink[v];
            },handle(key, keyPath) {
                if(key=='1-2'){
                    window.location.href='../Admin/userConfig.html';
                }
            },
        }
    }).$mount("#coding");
    $.ajax({
        url:"2.txt",
        type:"get",
        dataType:'json',
        success:function(res,name,status){
            $('.load').css({'display':'none'});
            $('#coding').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
