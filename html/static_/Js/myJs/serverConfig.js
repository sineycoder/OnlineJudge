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
            returnPage:'index.html',
            brand:'JSUT',
            admin:{is:true},
            activeIndex: '1',
            input10:'',
            currentPage3:5,
            input1:'',
            tableData3: [{
                id:1,
                name:'siney',
                createTime:'2018-1-21 11:11:11',
                lastLogin:'2017-1-21 11:11:11',
                realName:'NiZhenxian',
                email:'asf@qq.com',
                grant:'superAdmin'
            }],
            multipleSelection: [],
            loading: true,
            web:{
                title:'Website Config',
                name:'',
                registerPermission:true,
                submissionPermission:false,
                footer:''
            },
            server:{
                title:'Server Config',
                servers:[{
                    status:'Normal',
                    hostname:'abcdefg',
                    cpu:4,
                    cpuUsage:35.7,
                    memoryUsage:35.9,
                    disabled:false,
                    judgingTask:10,
                    judgedTask:15,
                    createTime:'2018-1-1 11:11:11',
                    ip:'192.168.1.1',
                    token:'abcdefg'
                },{
                    status:'Normal',
                    hostname:'abcdefg',
                    cpu:4,
                    cpuUsage:35.7,
                    memoryUsage:35.9,
                    disabled:false,
                    judgingTask:10,
                    judgedTask:15,
                    createTime:'2018-1-1 11:11:11',
                    ip:'192.168.1.1',
                    token:'abcdefg'
                }]
            },
            smtp:{
                title:'SMTP Config',
                email:'',
                password:''
            },
            addJudgeServer:{
                active:false,
                ip:'',
                token:''
            },
            adminLink:[['serverConfig.html','userConfig.html']],
        },
        methods:{
            handleSelect(key, keyPath) {
                var v1 = parseInt(key.split('-')[0])-1;
                var v2 = parseInt(key.split('-')[1])-1;
                // var v='';
                // if(key=='1-1')v = 'serverConfig.html';
                // else if(key=='1-2')v = 'userConfig.html';
                window.location.href=this.adminLink[v1][v2];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.multipleSelection.forEach(obj => {
                    console.log(obj.date+" "+obj.address);
                })
                console.log(this.multipleSelection);
            },
            //删除行
            deleteRow(index, rows) {
                console.log(rows[index].id);
                console.log(index);
                this.open2(index,rows);
            },
            //分页
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            open2(index,rows) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    rows.splice(index, 1);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            open3() {
                this.$prompt('请输入邮箱', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                    inputErrorMessage: '邮箱格式不正确'
                }).then(({ value }) => {
                    this.$message({
                        type: 'success',
                        message: '你的邮箱是: ' + value
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            sure(){
                this.addJudgeServer.active = false;
                this.$message({
                    type: 'success',
                    message: 'Add Success'
                });
                this.$message({
                    type: 'error',
                    message: 'Add Error'
                });
            }
        }
    }).$mount("#serverConfig");

    $.ajax({
       url:'1.txt',
       type:'get',
       success:function(status,data){
           //取消loading画面
           $('.load').css({'display':'none'});
        $('#serverConfig').animate({'opacity':'1'},1000);
       },
       error:function(){

       }

   });


});