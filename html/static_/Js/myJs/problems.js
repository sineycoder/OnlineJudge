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
            input1:'',
            currentPage3:5,
            tableData: [{
                status:'not_pass',
                id:1000,
                problem:'A+B问题',
                tag:'基础题',
                difficulty:'easy',
                pass:33.7
            },{
                status:'pass',
                id:1001,
                problem:'A+B问题',
                tag:'基础题',
                difficulty:'easy',
                pass:23.7
            },{
                status:'pass',
                id:1002,
                problem:'A+B问题',
                tag:'基础题',
                difficulty:'easy',
                pass:5.7
            },{
                status:'',
                id:1003,
                problem:'A+B问题',
                tag:'复杂题',
                difficulty:'easy',
                pass:45.2
            }],
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
            },
            handle(key, keyPath) {
                if(key=='1-2'){
                    window.location.href='../Admin/userConfig.html';
                }
            },
        }
    }).$mount("#problems");

    $.ajax({
        url:"1.json",
        type:"get",
        success:function(res,name,status){

            vm.tb_content=res;
            $('.load').css({'display':'none'});
            $('#problems').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
