-- MySQL dump 10.17  Distrib 10.3.14-MariaDB, for osx10.14 (x86_64)
--
-- Host: localhost    Database: mangrove
-- ------------------------------------------------------
-- Server version	10.3.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `mangrove`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mangrove` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mangrove`;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `txnid` varchar(20) NOT NULL,
  `userid` varchar(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `mode` varchar(20) NOT NULL,
  `amount` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`txnid`),
  KEY `fk_id` (`userid`),
  CONSTRAINT `fk_id` FOREIGN KEY (`userid`) REFERENCES `user_data` (`id`),
  CONSTRAINT `chk_Type` CHECK (`type` in ('credit','Debit')),
  CONSTRAINT `chk_status` CHECK (`status` in ('progress','successful','failed'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES ('1','1','progress','online',1000,'credit','2019-04-11 08:24:06'),('2','1','successful','payment gateway',2000,'credit','2019-04-11 08:24:06'),('3','1','failed','paytm',3000,'credit','2019-04-11 08:24:06'),('4','2','successful','online',2000,'credit','2019-04-11 08:24:06'),('5','3','successful','online',1000,'credit','2019-04-11 08:24:06'),('6','4','successful','online',1500,'credit','2019-04-11 08:24:06'),('7','4','successful','online',2500,'credit','2019-04-11 08:24:06'),('8','2','successful','DD',3000,'credit','2019-04-11 08:24:06'),('9','3','successful','DD',1000,'credit','2019-04-11 08:28:45');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_data` (
  `id` varchar(20) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `kycstatus` varchar(20) DEFAULT '0',
  `pubKey` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES ('1','Rahul','Satija','rahul.s@gmail.com','1',NULL),('2','Akshat','Giri','akshat.g@gmail.com','1',NULL),('3','Mahi','Doni','mahi.d@gmail.com','1',NULL),('4','Shubham','Dkony','s.dkiny@gmail.com','1',NULL);
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-11 14:04:56
