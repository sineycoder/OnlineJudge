/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.22 : Database - onlinejudge
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`onlinejudge` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `onlinejudge`;

/*Table structure for table `backend_config` */

DROP TABLE IF EXISTS `backend_config`;

CREATE TABLE `backend_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL DEFAULT 'JSUT',
  `registerPermission` int(11) NOT NULL DEFAULT '1',
  `footer` text NOT NULL,
  `email` varchar(20) NOT NULL DEFAULT '',
  `emailPass` varchar(20) NOT NULL DEFAULT '',
  `host` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `backend_config` */

insert  into `backend_config`(`id`,`name`,`registerPermission`,`footer`,`email`,`emailPass`,`host`) values (1,'JSUT',0,'','','','smtp.163.com');

/*Table structure for table `web_contest` */

DROP TABLE IF EXISTS `web_contest`;

CREATE TABLE `web_contest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contestName` varchar(30) NOT NULL,
  `contestDescription` text NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `createTime` datetime NOT NULL,
  `contestPassword` varchar(20) DEFAULT '',
  `contestStatus` varchar(10) NOT NULL DEFAULT '等待开始',
  `contestVisible` int(11) NOT NULL DEFAULT '1',
  `contestCreator` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_contest` */

/*Table structure for table `web_language` */

DROP TABLE IF EXISTS `web_language`;

CREATE TABLE `web_language` (
  `languageId` int(11) NOT NULL AUTO_INCREMENT,
  `languageName` varchar(10) NOT NULL,
  PRIMARY KEY (`languageId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `web_language` */

insert  into `web_language`(`languageId`,`languageName`) values (1,'C'),(2,'JAVA'),(3,'C++');

/*Table structure for table `web_notice` */

DROP TABLE IF EXISTS `web_notice`;

CREATE TABLE `web_notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `content` text NOT NULL,
  `isShow` int(11) NOT NULL DEFAULT '1',
  `createUserId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `currentUpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_notice` */

/*Table structure for table `web_problems` */

DROP TABLE IF EXISTS `web_problems`;

CREATE TABLE `web_problems` (
  `problemId` int(11) NOT NULL AUTO_INCREMENT,
  `problemTitle` varchar(50) NOT NULL,
  `problemDescription` text NOT NULL,
  `problemInputDescription` text,
  `problemOutputDescription` text,
  `problemSample` text,
  `problemDifficulty` varchar(10) DEFAULT NULL,
  `problemContestId` int(11) NOT NULL DEFAULT '-1',
  `problemIsShow` int(11) NOT NULL DEFAULT '1',
  `problemType` varchar(1) NOT NULL DEFAULT '0',
  `problemReturn` varchar(1) NOT NULL DEFAULT '0',
  `problemHint` text,
  `problemSource` varchar(200) DEFAULT NULL,
  `problemAuthor` varchar(20) NOT NULL,
  `problemCreateTime` datetime DEFAULT NULL,
  `problemInput` mediumtext NOT NULL,
  `problemOutput` mediumtext NOT NULL,
  PRIMARY KEY (`problemId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

/*Data for the table `web_problems` */

/*Table structure for table `web_problems_language` */

DROP TABLE IF EXISTS `web_problems_language`;

CREATE TABLE `web_problems_language` (
  `problemId` int(11) NOT NULL,
  `languageId` int(11) NOT NULL,
  `problemMemory` int(11) NOT NULL,
  `problemCpuTime` int(11) NOT NULL,
  PRIMARY KEY (`problemId`,`languageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_problems_language` */

/*Table structure for table `web_problems_tag` */

DROP TABLE IF EXISTS `web_problems_tag`;

CREATE TABLE `web_problems_tag` (
  `tagId` int(11) NOT NULL,
  `problemId` int(11) NOT NULL,
  PRIMARY KEY (`tagId`,`problemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_problems_tag` */

/*Table structure for table `web_submit_list` */

DROP TABLE IF EXISTS `web_submit_list`;

CREATE TABLE `web_submit_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `problemId` int(11) NOT NULL,
  `submitDate` datetime NOT NULL,
  `lang` varchar(10) NOT NULL,
  `codes` text NOT NULL,
  `result` varchar(9) CHARACTER SET big5 NOT NULL,
  `cpuTime` int(11) DEFAULT NULL,
  `memorySize` int(11) DEFAULT NULL,
  `output_ans` text,
  `output_user` text,
  `returnInfo` text,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `result` (`result`),
  KEY `submitDate` (`submitDate`),
  KEY `LANGUAGE` (`lang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_submit_list` */

/*Table structure for table `web_tag` */

DROP TABLE IF EXISTS `web_tag`;

CREATE TABLE `web_tag` (
  `tagId` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(20) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_tag` */

/*Table structure for table `web_user` */

DROP TABLE IF EXISTS `web_user`;

CREATE TABLE `web_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(10) DEFAULT NULL,
  `isChange` int(11) NOT NULL DEFAULT '0',
  `userPwd` varchar(40) NOT NULL,
  `userTag` varchar(5) NOT NULL DEFAULT '',
  `userEmail` varchar(50) NOT NULL DEFAULT '',
  `userGrant` varchar(6) NOT NULL DEFAULT '0',
  `infoGender` varchar(6) DEFAULT NULL,
  `infoSchoolNum` varchar(15) DEFAULT NULL,
  `infoSchoolName` varchar(50) DEFAULT NULL,
  `infoBlog` varchar(100) DEFAULT NULL,
  `infoGithub` varchar(100) DEFAULT NULL,
  `infoMotto` varchar(100) DEFAULT NULL,
  `infoPhone` varchar(11) DEFAULT NULL,
  `infoRealName` varchar(20) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `lastLogin` datetime NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `web_user` */

insert  into `web_user`(`userId`,`userName`,`isChange`,`userPwd`,`userTag`,`userEmail`,`userGrant`,`infoGender`,`infoSchoolNum`,`infoSchoolName`,`infoBlog`,`infoGithub`,`infoMotto`,`infoPhone`,`infoRealName`,`createTime`,`lastLogin`) values (1,'root',0,'33a485cb146e1153c69b588c671ab474f2e5b800','神犇','','1','男',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-22 09:37:42','2018-04-26 09:23:58');

/*Table structure for table `web_user_contest` */

DROP TABLE IF EXISTS `web_user_contest`;

CREATE TABLE `web_user_contest` (
  `userId` int(11) NOT NULL DEFAULT '0',
  `contestId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`,`contestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `web_user_contest` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
