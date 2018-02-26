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
            page:{'pageNow':'10','pageNum':10},
            returnPage:"../Admin/index.html",                        //右上角返回主界面地址
            brand:"JSUT",                                   //左上角标题
            brandLink:"#",                                  //左上角标题链接
            searchContent:"#",                              //搜索框的内容
            imgSrc:"../Images/default_portrait.jpg",        //用户图片地址
            admin:{href:"#",is:true},                        //是否显示后台窗口
            tb_content:[                                            //table内容
                {'title':'2017新生赛aaaaaa','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'100','href':'aaa'},
                {'title':'2017新生赛bbbbbb','date':'2017-01-21 11:11:11','lock':false,'time':'3 hours','status':'Starting','participation':'20','href':'aaa'},
                {'title':'2017新生赛cccccc','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Waiting','participation':'10','href':'aaa'},
                {'title':'2017新生赛dddddd','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'20','href':'aaa'}
            ],
            input1:'',
            currentPage3:5,
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
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },handle(key, keyPath) {
                if(key=='1-2'){
                    window.location.href='../Admin/userConfig.html';
                }
            },
        }
    }).$mount("#contest");
    $.ajax({
        url:"2.txt",
        type:"get",
        dataType:'json',
        success:function(res,name,status){
            $('.load').css({'display':'none'});
            $('#contest').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
