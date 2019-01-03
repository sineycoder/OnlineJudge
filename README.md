# OnlineJudge

# Linux环境配置

这里我们以`ubuntu16.04`为例子，其他任何能装docker的OS也都行。<br/><br/>
建议：部署前使用`sudo su`指令升级为`root`用户来操作，避免后序因为权限问题带来的不便。

* 安装环境`apt-get update && apt-get install -y vim curl git`
* 安装docker使用`curl -sSL https://get.daocloud.io/docker | sh` (适用于Ubuntu，Debian,Centos等大部分Linux，会3小时同步一次Docker官方资源,安装时有时较慢，请耐心等待)
* 安装docker-compose:
  1. <code>curl -L https://get.daocloud.io/docker/compose/releases/download/1.21.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose</code>
  2. <code>chmod +x /usr/local/bin/docker-compose</code> 

# 准备部署
请在空间较大的磁盘下执行以下命令<br>
> `git clone https://github.com/SineyCoder/OnlineJudge.git && cd OnlineJudge`

启动部署指令
> `docker-compose up -d`


根据具体的网络情况,将在5-10分钟内即可安装部署完成。安装完成后请使用`docker logs -f oj_core`查看输出的log，如果最后显示出`Undertow started on port(s) 8080`代表启动成功，使用`ctrl+c`退出，访问本主机页面即可。
> **注意：80端口不能被其他软件占用，否则启动失败**

> 请注意，如果注册用户出现（请联系管理员）那么可能是数据库导入失败，此时需要执行`docker exec -it oj_mysql /bin/bash`进入mysql容器，然后执行`mysql -uroot -p`回车后输入密码`398653206`, 然后输入`show databases;`如果没有OnlineJudge数据库，那么，然后把mysql文件夹下的default.sql内容复制下，然后再控制台粘贴，创建所需的库即可使用。（这里我没有开发好，再次致歉）。

# 使用
部署完成后，即可输入ip地址直接访问。默认情况下有一个超级管理员账号，用户名为`root`,密码为`rootroot`,***登录后要及时修改***

