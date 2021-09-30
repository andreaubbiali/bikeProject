-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: bikeProject
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bike`
--

DROP TABLE IF EXISTS `bike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bike` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_in_maintenance` tinyint(1) NOT NULL DEFAULT '0',
  `type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bike_FK` (`type_id`),
  CONSTRAINT `bike_FK` FOREIGN KEY (`type_id`) REFERENCES `bike_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike`
--

LOCK TABLES `bike` WRITE;
/*!40000 ALTER TABLE `bike` DISABLE KEYS */;
/*!40000 ALTER TABLE `bike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bike_tariff`
--

DROP TABLE IF EXISTS `bike_tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bike_tariff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time_in_minutes` int NOT NULL,
  `tariff` float NOT NULL,
  `bike_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bike_tariff_FK` (`bike_type_id`),
  CONSTRAINT `bike_tariff_FK` FOREIGN KEY (`bike_type_id`) REFERENCES `bike_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike_tariff`
--

LOCK TABLES `bike_tariff` WRITE;
/*!40000 ALTER TABLE `bike_tariff` DISABLE KEYS */;
INSERT INTO `bike_tariff` VALUES (1,30,0,1),(2,60,0.5,1),(3,90,0.5,1),(4,120,0.5,1),(5,30,0.25,3),(6,60,0.5,3),(7,90,1,3),(8,120,2,3),(9,30,0.25,2),(10,60,0.5,2),(11,90,1,2),(12,120,2,2),(13,180,2,1),(14,240,2,1),(15,300,2,1),(17,360,2,1),(18,420,2,1),(19,480,2,1),(20,540,2,1),(21,600,2,1),(22,660,2,1),(23,720,2,1),(24,780,2,1),(25,840,2,1),(26,900,2,1),(27,960,2,1),(28,1020,2,1),(29,1080,2,1),(30,1140,2,1),(31,1200,2,1),(32,1260,2,1),(33,1320,2,1),(34,1380,2,1),(35,1440,2,1),(36,3,0,3),(37,3,0,2),(38,180,4,2),(39,240,4,2),(40,300,4,2),(41,360,4,2),(42,420,4,2),(43,480,4,2),(44,540,4,2),(45,600,4,2),(46,660,4,2),(47,720,4,2),(48,780,4,2),(49,840,4,2),(50,900,4,2),(51,960,4,2),(52,1020,4,2),(53,1080,4,2),(54,1140,4,2),(55,1200,4,2),(56,1260,4,2),(57,1320,4,2),(58,1380,4,2),(59,1440,4,2),(60,180,4,3),(61,240,4,3),(62,300,4,3),(63,360,4,3),(64,420,4,3),(65,480,4,3),(66,540,4,3),(67,600,4,3),(68,660,4,3),(69,720,4,3),(70,780,4,3),(71,840,4,3),(72,900,4,3),(73,960,4,3),(74,1020,4,3),(75,1080,4,3),(76,1140,4,3),(77,1200,4,3),(78,1260,4,3),(79,1320,4,3),(80,1380,4,3),(81,1440,4,3);
/*!40000 ALTER TABLE `bike_tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bike_type`
--

DROP TABLE IF EXISTS `bike_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bike_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `baby_seat` tinyint(1) NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike_type`
--

LOCK TABLES `bike_type` WRITE;
/*!40000 ALTER TABLE `bike_type` DISABLE KEYS */;
INSERT INTO `bike_type` VALUES (1,0,'NORMAL'),(2,0,'ELECTRIC'),(3,1,'ELECTRICWITHSEAT');
/*!40000 ALTER TABLE `bike_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_card` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` bigint NOT NULL,
  `cvv` int NOT NULL,
  `expire_date` datetime NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `credit_card_FK` (`user_id`),
  CONSTRAINT `credit_card_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `damage`
--

DROP TABLE IF EXISTS `damage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  `rent_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `damage_FK` (`rent_id`),
  CONSTRAINT `damage_FK` FOREIGN KEY (`rent_id`) REFERENCES `rent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `damage`
--

LOCK TABLES `damage` WRITE;
/*!40000 ALTER TABLE `damage` DISABLE KEYS */;
/*!40000 ALTER TABLE `damage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rack`
--

DROP TABLE IF EXISTS `rack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rack` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rack_UN` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rack`
--

LOCK TABLES `rack` WRITE;
/*!40000 ALTER TABLE `rack` DISABLE KEYS */;
/*!40000 ALTER TABLE `rack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rack_position`
--

DROP TABLE IF EXISTS `rack_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rack_position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_broken` tinyint(1) NOT NULL DEFAULT '0',
  `accepted_bike_type_id` int NOT NULL,
  `bike_id` int DEFAULT NULL,
  `rack_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rack_position_FK_2` (`rack_id`),
  KEY `rack_position_FK` (`accepted_bike_type_id`),
  KEY `rack_position_FK_1` (`bike_id`),
  CONSTRAINT `rack_position_FK` FOREIGN KEY (`accepted_bike_type_id`) REFERENCES `bike_type` (`id`),
  CONSTRAINT `rack_position_FK_1` FOREIGN KEY (`bike_id`) REFERENCES `bike` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `rack_position_FK_2` FOREIGN KEY (`rack_id`) REFERENCES `rack` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rack_position`
--

LOCK TABLES `rack_position` WRITE;
/*!40000 ALTER TABLE `rack_position` DISABLE KEYS */;
/*!40000 ALTER TABLE `rack_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rent`
--

DROP TABLE IF EXISTS `rent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rent` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bike_id` int NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `subscription_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rent_FK` (`bike_id`),
  KEY `rent_FK_2` (`subscription_id`),
  CONSTRAINT `rent_FK` FOREIGN KEY (`bike_id`) REFERENCES `bike` (`id`),
  CONSTRAINT `rent_FK_2` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent`
--

LOCK TABLES `rent` WRITE;
/*!40000 ALTER TABLE `rent` DISABLE KEYS */;
/*!40000 ALTER TABLE `rent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subscription_type_id` int NOT NULL,
  `subscription_date` datetime NOT NULL,
  `user_id` int NOT NULL,
  `count_exceeded_time` int NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `subscription_FK` (`subscription_type_id`),
  KEY `subscription_FK_1` (`user_id`),
  CONSTRAINT `subscription_FK` FOREIGN KEY (`subscription_type_id`) REFERENCES `subscription_type` (`id`),
  CONSTRAINT `subscription_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription_type`
--

DROP TABLE IF EXISTS `subscription_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` float NOT NULL,
  `days_duration` int NOT NULL,
  `must_start_in` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_type`
--

LOCK TABLES `subscription_type` WRITE;
/*!40000 ALTER TABLE `subscription_type` DISABLE KEYS */;
INSERT INTO `subscription_type` VALUES (1,'Daily',4.5,1,90),(2,'Annually',36,365,0),(3,'Weekly',9,7,90);
/*!40000 ALTER TABLE `subscription_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) NOT NULL,
  `is_student` tinyint(1) NOT NULL DEFAULT '0',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_UN` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin@gmail.com','Mt+XngT611vPOZP4JroG3sSvAnQfRjEKhuAgv2skh3Y=','SIULdY1XI6VReC3a2ceP7mDQEtpsew',0,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-30 22:10:06
