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
            links:["problem.html","#","#","#","#"],                //每个标签的链接
            brandLink:"#",                                  //左上角标题链接
            searchContent:"#",                              //搜索框的内容
            imgSrc:"../Images/default_portrait.jpg",        //用户图片地址
            admin:{href:"#",is:true},                        //是否显示后台窗口
            input1:'',
            currentPage3:5,
            title:'Submit',
            tableData: [{
                date:'2017-11-22 17:00:00',
                name:'tomcat',
                problem:'sdfasfsadf',
                status:'AC',
                cpu:170,
                memory:17,
                language:'C'
            },{
                date:'2017-11-23 17:00:00',
                name:'tomcat',
                problem:'sdfasfsadf',
                status:'WA',
                cpu:170,
                memory:17,
                language:'JAVA'
            },{
                date:'2017-11-23 17:00:00',
                name:'tomcat',
                problem:'sdfasfsadf',
                status:'TLE',
                cpu:170,
                memory:17,
                language:'C'
            },{
                date:'2017-11-23 17:00:00',
                name:'tomcat',
                problem:'sdfasfsadf',
                status:'SE',
                cpu:170,
                memory:17,
                language:'C'
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
    }).$mount("#submit");
    $.ajax({
        url:"2.txt",
        type:"get",
        dataType:'json',
        success:function(res,name,status){
            $('.load').css({'display':'none'});
            $('#submit').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
