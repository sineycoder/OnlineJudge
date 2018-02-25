/**
 * Created by siney on 2017/5/11.
 */
var vm;
var im = {
    'x':0,
    'y':0,
    'width':0,
    'height':0
};
function setSize(){
    var height = (document.documentElement.clientHeight || document.body.clientHeight) - 60;
    document.querySelector('.contain').style.height = height+"px";
}
window.onresize = function(){
    setSize();
}
function imageInit(){
    $('#image_').cropper({
        viewMode:3,
        dragMode : "none",
        aspectRatio: 1 / 1,
        background:false,
        autoCropArea:0.75,
        minContainerHeight:150,
        minContainerWidth:150,
        crop: function(e) {
            // Output the result data for cropping image.
            im.x = e.x;
            im.y = e.y;
            im.width = e.width;
            im.height = e.height;
            console.log(im.x);
            console.log(im.y);
            console.log(im.width);
            console.log(im.height);
        }
    });
}

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
            tb_title:["Date","ID","Status","Problem","Time","Memory","Language"],  //table标题
            tb_content:[                                            //table内容
                {'title':'2017新生赛aaaaaa','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'100','href':'aaa'},
                {'title':'2017新生赛bbbbbb','date':'2017-01-21 11:11:11','lock':false,'time':'3 hours','status':'Starting','participation':'20','href':'aaa'},
                {'title':'2017新生赛cccccc','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Waiting','participation':'10','href':'aaa'},
                {'title':'2017新生赛dddddd','date':'2017-01-21 11:11:11','lock':true,'time':'3 hours','status':'Ended','participation':'20','href':'aaa'}
            ],
            options1:[{
                value: 'man',
                label: 'man'
            }, {
                value: 'female',
                label: 'female'
            }],
            options2:[{
                value:'value1',
                label:'value1',
                children:[{
                    value:'value2',
                    label:'value2'
                },{
                    value:'value3',
                    label:'value3'
                }]
            }],
            value:'',
            imageUrl:'',//../Images/default_portrait.jpg TODO 这里是图片url路径
            tab3:{name:'',school:'',date:'',phone:'',QQ:'',gender:'',address:''},
            title:'Ranklist',
            input1:'',
            currentPage3:5,
            /*
             <span class="glyphicon glyphicon-ok" style="color:green;"></span>    pass
             <span class="glyphicon glyphicon-minus" style="color:red;"></span>   not pass

             * */
        },
        methods:{

            onloadBtn(){
                $("#upload").click();
            },
            saveBtn(obj){
                 alert(1);
            },
            handleChange(value) {
                console(this.tab3.address[0]);
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },

        }
    }).$mount("#setting");

    $('#image_').load(function(){
        $('#image_').cropper('destroy');
        imageInit();
    });

    $('#upload').on('change',function(e){

        /*
        * processData:false 因为不需要数据
        * contentType:false因为表单中已经设置了
        * cache:false文件上传不需要缓存
        * */
        var phname = '../Images/2.jpg';
        vm.imageUrl = phname;
        $('#saveBtn').removeAttr('disabled').removeClass('is-disabled');//恢复按钮
        /* $.ajax({
             url: "3.json",
             type: 'POST',
             cache: false,
             data: new FormData($('#uploadForm')[0]),
             processData: false,
             contentType: false,
             beforeSend: function(){
             },
             success : function(data) {
                 alert(data);
                 vm.imageUrl = data;
             },
             error:function(e,status){
                 alert(e);
                 alert(status);
             }
         });*/
    });

    $.ajax({
        url:"2.txt",
        type:"get",
        dataType:'json',
        success:function(res,name,status){
            $('.load').css({'display':'none'});
            $('#setting').animate({'opacity':'1'},1000);

        },
        error:function(excep,text){
            alert(text);
        }
    });


});
