/**
 * Created by siney on 2017/5/11.
 */
$(function(){
    var vm = new Vue({
        data:{
            icon:[{path:'../Images/icon.jpg'},{width:'120px'},{height:'90px'}], //配置图标
            inputH:['height:45px;','width:100%;height:45px;']                   //输入框的高度
        },
        methods:{
            doo:function(){
                $.ajax({
                    url:'http://localhost:8080/Nginx_Tomcat/problem/find',
                    data:{username:'123',age:'12'},
                    type:'POST',
                    success:function (text,obj,status) {
                    },
                    error:function(e1,e2){
                    }
                });
            },
            signUp:function(){
                $('#login').stop().animate({marginLeft:'-56vw'});
            },
            signIn:function(){
                $('#login').stop().animate({marginLeft:'-28vw'});
            },
            forget:function(){
                $('#login').stop().animate({marginLeft:'0vw'});
            }
        }
    }).$mount('#exterior');

});