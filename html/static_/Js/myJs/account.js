/**
 * Created by siney on 2017/5/11.
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
            brandLink:'#',
            returnPage:'index.html',                        //右上角返回主界面地址
            brand:'JSUT',                                   //左上角标题
            imgSrc:'../Images/default_portrait.jpg',        //用户图片地址
            admin:{href:'#',is:true},
            activeIndex: '1',
            input1:'',
            motto:'i will be successful man!i will be successful man!',
            notice:[
                {title:'公告',content:'三大法师打发士大夫撒旦飞洒发的发热问题问去玩儿',time:'2018-1-1'},
                {title:'公告',content:'三大法师打发士大夫撒旦飞洒发的发热问题问去玩儿',time:'2018-1-1'},
                {title:'公告',content:'三大法师打发士大夫撒旦飞洒发的发热问题问去玩儿',time:'2018-1-1'},
                {title:'公告',content:'三大法师打发士大夫撒旦飞洒发的发热问题问去玩儿',time:'2018-1-1'}
            ],
            h:['problems.html','rank.html','submit.html','contest.html']
        },

        methods:{
            handleSelect(key, keyPath) {
                // var v = parseInt(key) - 1;
                // window.location.href = this.h[v];
            },

        }
    }).$mount('#account');

    // 与后台交互并初始化页面
    $.ajax({
            url:'1.txt',
            type:'get',
            success:function(res,name,status){
            // alert(res);//内容
            // alert(name);//名称：如success
            // alert(status.status)//状态对象
            $('.load').css({'display':'none'});
            $('#account').animate({'opacity':'1'},1000);
            $(".rightContent .el-collapse-item__header").click();
    },
        error:function(excep,text){//后台交互失败
            alert(excep.status);//异常对象
            alert(text)//异常内容
        }
    });

});
