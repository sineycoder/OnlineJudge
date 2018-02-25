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
            imgSrc:"../Images/default_portrait.jpg",        //用户图片地址
            admin:{href:"#",is:true},                        //是否显示后台窗口
            tb_title:["Rank","ID","个人签名","AC率","总提交次数"],  //table标题
            tb_content:[                                            //table内容
                ["1","username","easy","45.67%","500"],//最后的为题号
                ["2","username","easy","45.67%","500"],
                ["3","username","easy","45.67%","500"],
                ["4","username","easy","45.67%","500"],
                ["5","username","easy","45.67%","500"]
            ],
            title:'Ranklist',
            input1:'',
            currentPage3:1,
            tableData: [{
                rank:1,
                name:'siney',
                motto:'今天天旦法爽肤水的冯绍峰撒',
                tag:'神犇',
                ac:2.67,
                submit:'222'
            },{
                rank:2,
                name:'siney',
                motto:'今天天气不错',
                tag:'神犇',
                ac:13.67,
                submit:'313'
            },{
                rank:3,
                name:'sineyaaaaaaaaaa',
                motto:'今天天气不错',
                tag:'神犇',
                ac:45.6,
                submit:'211'
            },{
                rank:4,
                name:'siney',
                motto:'今天天气不错',
                tag:'菜鸟',
                ac:22.67,
                submit:'132'
            }]
            /*
             <span class="glyphicon glyphicon-ok" style="color:green;"></span>    pass
             <span class="glyphicon glyphicon-minus" style="color:red;"></span>   not pass

             * */
        },
        methods:{
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            filterDifficulty(value,row){
                return row.difficulty == value;
            },
            filterHandler(value, row, column) {
                const property = column['property'];
                return row[property] === value;
            }
        }
    }).$mount("#rank");
    $.ajax({
        url:"1.json",
        type:"get",
        success:function(res,name,status){


            $('.load').css({'display':'none'});
            $('#rank').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
