-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: j7a308.p.ssafy.io    Database: sonmogaji
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `signee`
--

DROP TABLE IF EXISTS `signee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signee` (
  `signee_id` bigint NOT NULL,
  `member_address` varchar(255) DEFAULT NULL,
  `tx_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`signee_id`),
  KEY `FKi3f2i6kkwsrq1kcli862ao2oi` (`member_address`),
  KEY `FKrw9gxvsmqf7j1xx8ysaj8u4tf` (`tx_address`),
  CONSTRAINT `FKi3f2i6kkwsrq1kcli862ao2oi` FOREIGN KEY (`member_address`) REFERENCES `member` (`member_address`),
  CONSTRAINT `FKrw9gxvsmqf7j1xx8ysaj8u4tf` FOREIGN KEY (`tx_address`) REFERENCES `transaction` (`tx_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signee`
--

LOCK TABLES `signee` WRITE;
/*!40000 ALTER TABLE `signee` DISABLE KEYS */;
INSERT INTO `signee` VALUES (1,'0x7dF3bdDbDc10cEe86e7Db3C98414c7a2907AC6ac','0xhjkl');
/*!40000 ALTER TABLE `signee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-07 11:43:28
