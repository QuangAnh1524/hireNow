-- MySQL dump 10.13  Distrib 8.4.0, for Win64 (x86_64)
--
-- Host: localhost    Database: hirenow
-- ------------------------------------------------------
-- Server version	8.4.0

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
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'Hanoi',NULL,NULL,'a company about IT','01.png','Company_01','2025-04-10 07:50:22.200686','buixuanphai@gmail.com'),(2,'Hanoi',NULL,NULL,'a company about MARKETING','02.png','Company_02','2025-04-10 07:53:56.640226','buixuanphai@gmail.com'),(3,'Hanoi','2025-04-09 10:06:44.528672','quang anh','a company about EDUCATION','03.png','Company_03','2025-04-10 07:56:35.383406','buixuanphai@gmail.com'),(5,'TPHCM','2025-04-09 15:26:40.189589','buixuanphai@gmail.com','a company about MUSIC','05.png','company_05',NULL,NULL);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_skill`
--

DROP TABLE IF EXISTS `job_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_skill` (
  `job_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  KEY `FKdh76859joo68p6dbj9erh4pbs` (`skill_id`),
  KEY `FKje4q8ajxb3v5bel11dhbxrb8d` (`job_id`),
  CONSTRAINT `FKdh76859joo68p6dbj9erh4pbs` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKje4q8ajxb3v5bel11dhbxrb8d` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_skill`
--

LOCK TABLES `job_skill` WRITE;
/*!40000 ALTER TABLE `job_skill` DISABLE KEYS */;
INSERT INTO `job_skill` VALUES (1,1),(1,2),(2,3),(2,4),(4,6),(4,7),(5,1),(5,3),(5,5),(5,7),(3,1),(3,5),(3,7);
/*!40000 ALTER TABLE `job_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  `end_date` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `level` enum('FRESHER','INTERN','JUNIOR','MIDDLE','SENIOR') DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `salary` double NOT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtmqcrktb6s7xq8djbs2a2war` (`company_id`),
  CONSTRAINT `FKrtmqcrktb6s7xq8djbs2a2war` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
INSERT INTO `jobs` VALUES (1,'2025-04-16 01:24:42.894414','buixuanphai@gmail.com','Thß╗▒c tß║¡p sinh Java c├│ ─æß╗ïnh h╞░ß╗¢ng ph├ít triß╗ân backend.','2025-08-01 00:00:00.000000',NULL,'INTERN','H├á Nß╗Öi','Java Intern',3,300,'2025-05-01 00:00:00.000000',NULL,NULL,1,_binary ''),(2,'2025-04-16 01:25:32.464060','buixuanphai@gmail.com','Tuyß╗ân fresher frontend sß╗¡ dß╗Ñng ReactJS.','2025-10-20 00:00:00.000000',NULL,'FRESHER','TP. Hß╗ô Ch├¡ Minh','Frontend Fresher',2,600,'2025-04-20 00:00:00.000000',NULL,NULL,2,_binary ''),(3,NULL,'buixuanphai@gmail.com','─É├ú cß║¡p nhß║¡t: Junior backend cß║ºn th├¬m kß╗╣ n─âng vß╗ü Spring Security v├á CI/CD.','2025-12-15 00:00:00.000000',NULL,'JUNIOR','─É├á Nß║╡ng','Junior Backend Developer - Updated',5,1200,'2025-06-01 00:00:00.000000','2025-04-16 01:52:41.088632',NULL,3,_binary ''),(4,'2025-04-16 01:25:51.183602','buixuanphai@gmail.com','Xß╗¡ l├╜ pipeline dß╗» liß╗çu lß╗¢n, ETL, l├ám viß╗çc vß╗¢i BigQuery v├á Spark.','2025-11-15 00:00:00.000000',NULL,'MIDDLE','H├á Nß╗Öi','Middle Data Engineer',1,1500,'2025-05-15 00:00:00.000000',NULL,NULL,1,_binary ''),(5,'2025-04-16 01:25:59.654807','buixuanphai@gmail.com','Fullstack developer vß╗¢i kinh nghiß╗çm React + Spring Boot, kiß║┐n tr├║c microservices.','2025-12-25 00:00:00.000000',NULL,'SENIOR','TP. Hß╗ô Ch├¡ Minh','Senior Fullstack Developer',2,2500,'2025-04-25 00:00:00.000000',NULL,NULL,2,_binary '');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_role` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  KEY `FK6mg4g9rc8u87l0yavf8kjut05` (`permission_id`),
  KEY `FK3vhflqw0lwbwn49xqoivrtugt` (`role_id`),
  CONSTRAINT `FK3vhflqw0lwbwn49xqoivrtugt` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK6mg4g9rc8u87l0yavf8kjut05` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` VALUES (2,5),(2,6),(2,7),(2,12),(2,13),(2,14),(2,15),(2,21),(2,22),(2,23),(2,24),(2,25),(2,32),(2,33),(2,34),(3,3),(3,4),(3,15),(3,21),(3,26),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35),(1,35);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_path` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `method` varchar(255) NOT NULL,
  `module` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'/api/users','2025-04-17 02:28:28.918715','buixuanphai@gmail.com','GET','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Xem ng╞░ß╗¥i d├╣ng',NULL,NULL),(2,'/api/users','2025-04-17 02:28:44.113757','buixuanphai@gmail.com','POST','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Tß║ío ng╞░ß╗¥i d├╣ng',NULL,NULL),(3,'/api/v1/auth/account','2025-04-17 08:09:34.115481','buixuanphai@gmail.com','GET','Quß║ún l├╜ t├ái khoß║ún','Lß║Ñy th├┤ng tin t├ái khoß║ún',NULL,NULL),(4,'/api/v1/auth/logout','2025-04-17 08:26:46.597659','buixuanphai@gmail.com','POST','Quß║ún l├╜ t├ái khoß║ún','─É─âng xuß║Ñt',NULL,NULL),(5,'/api/v1/companies','2025-04-17 08:26:46.636171','buixuanphai@gmail.com','POST','Quß║ún l├╜ c├┤ng ty','Tß║ío c├┤ng ty',NULL,NULL),(6,'/api/v1/companies','2025-04-17 08:26:46.649170','buixuanphai@gmail.com','PUT','Quß║ún l├╜ c├┤ng ty','Cß║¡p nhß║¡t c├┤ng ty',NULL,NULL),(7,'/api/v1/companies/{id}','2025-04-17 08:26:46.662169','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ c├┤ng ty','X├│a c├┤ng ty',NULL,NULL),(8,'/api/v1/permissions','2025-04-17 08:26:46.676173','buixuanphai@gmail.com','POST','Quß║ún l├╜ quyß╗ün','Tß║ío quyß╗ün',NULL,NULL),(9,'/api/v1/permissions','2025-04-17 08:26:46.693184','buixuanphai@gmail.com','PUT','Quß║ún l├╜ quyß╗ün','Cß║¡p nhß║¡t quyß╗ün',NULL,NULL),(10,'/api/v1/permissions/{id}','2025-04-17 08:26:46.704708','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ quyß╗ün','X├│a quyß╗ün',NULL,NULL),(11,'/api/v1/permissions','2025-04-17 08:26:46.718709','buixuanphai@gmail.com','GET','Quß║ún l├╜ quyß╗ün','Lß║Ñy danh s├ích quyß╗ün',NULL,NULL),(12,'/api/v1/jobs','2025-04-17 08:26:46.731706','buixuanphai@gmail.com','POST','Quß║ún l├╜ c├┤ng viß╗çc','Tß║ío c├┤ng viß╗çc',NULL,NULL),(13,'/api/v1/jobs','2025-04-17 08:26:46.745706','buixuanphai@gmail.com','PUT','Quß║ún l├╜ c├┤ng viß╗çc','Cß║¡p nhß║¡t c├┤ng viß╗çc',NULL,NULL),(14,'/api/v1/jobs/{id}','2025-04-17 08:26:46.759839','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ c├┤ng viß╗çc','X├│a c├┤ng viß╗çc',NULL,NULL),(15,'/api/v1/files','2025-04-17 08:26:46.772837','buixuanphai@gmail.com','POST','Quß║ún l├╜ tß╗çp','Tß║úi l├¬n tß╗çp',NULL,NULL),(16,'/api/v1/users','2025-04-17 08:26:46.786852','buixuanphai@gmail.com','GET','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Lß║Ñy danh s├ích ng╞░ß╗¥i d├╣ng',NULL,NULL),(17,'/api/v1/users/{id}','2025-04-17 08:26:46.804664','buixuanphai@gmail.com','GET','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Lß║Ñy ng╞░ß╗¥i d├╣ng theo ID',NULL,NULL),(18,'/api/v1/users','2025-04-17 08:26:46.821101','buixuanphai@gmail.com','POST','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Tß║ío ng╞░ß╗¥i d├╣ng',NULL,NULL),(19,'/api/v1/users','2025-04-17 08:26:46.835099','buixuanphai@gmail.com','PUT','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','Cß║¡p nhß║¡t ng╞░ß╗¥i d├╣ng',NULL,NULL),(20,'/api/v1/users/{id}','2025-04-17 08:26:46.854593','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ ng╞░ß╗¥i d├╣ng','X├│a ng╞░ß╗¥i d├╣ng',NULL,NULL),(21,'/api/v1/resumes','2025-04-17 08:26:46.868592','buixuanphai@gmail.com','POST','Quß║ún l├╜ hß╗ô s╞í','Tß║ío hß╗ô s╞í',NULL,NULL),(22,'/api/v1/resumes','2025-04-17 08:26:46.884606','buixuanphai@gmail.com','PUT','Quß║ún l├╜ hß╗ô s╞í','Cß║¡p nhß║¡t hß╗ô s╞í',NULL,NULL),(23,'/api/v1/resumes/{id}','2025-04-17 08:26:46.897664','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ hß╗ô s╞í','X├│a hß╗ô s╞í',NULL,NULL),(24,'/api/v1/resumes/{id}','2025-04-17 08:26:46.912471','buixuanphai@gmail.com','GET','Quß║ún l├╜ hß╗ô s╞í','Lß║Ñy hß╗ô s╞í theo ID',NULL,NULL),(25,'/api/v1/resumes','2025-04-17 08:26:46.928781','buixuanphai@gmail.com','GET','Quß║ún l├╜ hß╗ô s╞í','Lß║Ñy danh s├ích hß╗ô s╞í',NULL,NULL),(26,'/api/v1/resumes/by-user','2025-04-17 08:26:46.943489','buixuanphai@gmail.com','POST','Quß║ún l├╜ hß╗ô s╞í','Lß║Ñy hß╗ô s╞í theo ng╞░ß╗¥i d├╣ng',NULL,NULL),(27,'/api/v1/roles','2025-04-17 08:26:46.957622','buixuanphai@gmail.com','POST','Quß║ún l├╜ vai tr├▓','Tß║ío vai tr├▓',NULL,NULL),(28,'/api/v1/roles','2025-04-17 08:26:46.970966','buixuanphai@gmail.com','PUT','Quß║ún l├╜ vai tr├▓','Cß║¡p nhß║¡t vai tr├▓',NULL,NULL),(29,'/api/v1/roles/{id}','2025-04-17 08:26:46.983496','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ vai tr├▓','X├│a vai tr├▓',NULL,NULL),(30,'/api/v1/roles/{id}','2025-04-17 08:26:46.996505','buixuanphai@gmail.com','GET','Quß║ún l├╜ vai tr├▓','Lß║Ñy vai tr├▓ theo ID',NULL,NULL),(31,'/api/v1/roles','2025-04-17 08:26:47.009065','buixuanphai@gmail.com','GET','Quß║ún l├╜ vai tr├▓','Lß║Ñy danh s├ích vai tr├▓',NULL,NULL),(32,'/api/v1/skills','2025-04-17 08:26:47.022047','buixuanphai@gmail.com','POST','Quß║ún l├╜ kß╗╣ n─âng','Tß║ío kß╗╣ n─âng',NULL,NULL),(33,'/api/v1/skills','2025-04-17 08:26:47.034045','buixuanphai@gmail.com','PUT','Quß║ún l├╜ kß╗╣ n─âng','Cß║¡p nhß║¡t kß╗╣ n─âng',NULL,NULL),(34,'/api/v1/skills/{id}','2025-04-17 08:26:47.046045','buixuanphai@gmail.com','DELETE','Quß║ún l├╜ kß╗╣ n─âng','X├│a kß╗╣ n─âng',NULL,NULL),(35,'/api/v1/email','2025-04-18 11:24:56.000000',NULL,'GET','EMAIL','Access Email API',NULL,NULL),(36,'/api/v1/email','2025-04-18 11:25:12.000000',NULL,'GET','EMAIL','Access Email API',NULL,NULL);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resumes`
--

DROP TABLE IF EXISTS `resumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resumes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `status` enum('APPROVED','PENDING','REJECTED','REVIEWING') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `job_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjdec9qbp2blbpag6obwf0fmbd` (`job_id`),
  KEY `FK340nuaivxiy99hslr3sdydfvv` (`user_id`),
  CONSTRAINT `FK340nuaivxiy99hslr3sdydfvv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjdec9qbp2blbpag6obwf0fmbd` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resumes`
--

LOCK TABLES `resumes` WRITE;
/*!40000 ALTER TABLE `resumes` DISABLE KEYS */;
INSERT INTO `resumes` VALUES (1,'2025-04-16 07:45:08.014924','buixuanphai@gmail.com','nqa@email.com','APPROVED','2025-04-16 07:47:56.237306',NULL,'cv.pdf',1,2),(2,'2025-04-16 07:46:23.144863','buixuanphai@gmail.com','nqa@email.com','PENDING',NULL,NULL,'cv.pdf',2,2);
/*!40000 ALTER TABLE `resumes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,_binary '','2025-04-17 02:30:07.121053','buixuanphai@gmail.com','Quyß╗ün admin to├án hß╗ç thß╗æng','ADMIN','2025-04-17 08:50:08.098792','buixuanphai@gmail.com'),(2,_binary '','2025-04-17 08:45:17.247809','buixuanphai@gmail.com','Quyß╗ün nh├ón sß╗▒, quß║ún l├╜ c├┤ng viß╗çc v├á hß╗ô s╞í','ROLE_HR',NULL,NULL),(3,_binary '','2025-04-17 08:45:31.759376','buixuanphai@gmail.com','Quyß╗ün ng╞░ß╗¥i d├╣ng th├┤ng th╞░ß╗¥ng','ROLE_USER',NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'2025-04-16 01:17:21.121308','buixuanphai@gmail.com','Java',NULL,NULL),(2,'2025-04-16 01:17:37.682332','buixuanphai@gmail.com','C++',NULL,NULL),(3,'2025-04-16 01:17:51.271449','buixuanphai@gmail.com','React',NULL,NULL),(4,'2025-04-16 01:17:56.627262','buixuanphai@gmail.com','Python',NULL,NULL),(5,'2025-04-16 01:18:01.655071','buixuanphai@gmail.com','VueJs',NULL,NULL),(6,'2025-04-16 01:18:09.012201','buixuanphai@gmail.com','Docker',NULL,NULL),(7,'2025-04-16 01:19:58.664954','buixuanphai@gmail.com','Laravel','2025-04-16 01:21:55.807757','buixuanphai@gmail.com');
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriber_skill`
--

DROP TABLE IF EXISTS `subscriber_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriber_skill` (
  `subscriber_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  KEY `FKly8pe7rx11g3v97b1oq0vjs2r` (`skill_id`),
  KEY `FKjflpvmqmxox8edvsldr12hqjc` (`subscriber_id`),
  CONSTRAINT `FKjflpvmqmxox8edvsldr12hqjc` FOREIGN KEY (`subscriber_id`) REFERENCES `subscribers` (`id`),
  CONSTRAINT `FKly8pe7rx11g3v97b1oq0vjs2r` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber_skill`
--

LOCK TABLES `subscriber_skill` WRITE;
/*!40000 ALTER TABLE `subscriber_skill` DISABLE KEYS */;
INSERT INTO `subscriber_skill` VALUES (1,1),(1,5),(2,1),(2,3),(3,1),(3,5);
/*!40000 ALTER TABLE `subscriber_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribers`
--

DROP TABLE IF EXISTS `subscribers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribers`
--

LOCK TABLES `subscribers` WRITE;
/*!40000 ALTER TABLE `subscribers` DISABLE KEYS */;
INSERT INTO `subscribers` VALUES (1,'2025-04-17 14:20:27.603077','buixuanphai@gmail.com','buixuanphai@gmail.com','B├╣i Xu├ón Ph├íi','2025-04-17 14:35:35.862427',NULL),(2,'2025-04-18 07:48:48.753109','buixuanphai@gmail.com','ninoel2004@gmail.com','Nguyen Quang Anh',NULL,NULL),(3,'2025-04-18 08:14:05.581025','buixuanphai@gmail.com','quanganhjava@gmail.com','Nguyen Quang Anh',NULL,NULL);
/*!40000 ALTER TABLE `subscribers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE','OTHER') DEFAULT NULL,
  `refresh_token` mediumtext,
  `update_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKin8gn4o1hpiwe6qe4ey7ykwq7` (`company_id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKin8gn4o1hpiwe6qe4ey7ykwq7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'nqa@gmail.com','Quang Anh','123456','H├á Nß╗Öi',21,NULL,'buixuanphai@gmail.com','MALE',NULL,NULL,'2025-04-17 08:54:05.864648',NULL,3),(7,'trandan@gmail.com','Tran Dan','12345678',NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'hanmactu@gmail.com','Han Mac Tu','12345678',NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'lamthach@gmail.com','Thß║ích Lam','12345678','H├á Nß╗Öi',28,NULL,'buixuanphai@gmail.com','MALE',NULL,NULL,'2025-04-17 08:51:40.808199',NULL,2),(11,'vancao@gmail.com','Thß║ích Lam','$2a$10$IkJWiYBJml4mbMNCKn6wFe6wOyXQuBbj/u9end5TkxPznA9IlMX5m','H├á Nß╗Öi',35,NULL,'buixuanphai@gmail.com','MALE',NULL,NULL,'2025-04-17 08:52:13.129271',NULL,3),(12,'buixuanphai@gmail.com','B├╣i Xu├ón Ph├íi','$2a$10$iJrWcfkjT7oC1h/ftf5O6ux1ux8rxmHv.iqCNwaUAmRb20Y3X2bkC','H├á Nß╗Öi',30,NULL,'buixuanphai@gmail.com','MALE','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJidWl4dWFucGhhaUBnbWFpbC5jb20iLCJleHAiOjE3NTM2MDMzNzgsImlhdCI6MTc0NDk2MzM3OCwidXNlciI6eyJpZCI6MTIsImVtYWlsIjoiYnVpeHVhbnBoYWlAZ21haWwuY29tIiwibmFtZSI6IkLDuWkgWHXDom4gUGjDoWkifX0.icD-q2kyDd2LpSYB-dFeyeIWNMvzFSDaCruROdIxQG8',NULL,'2025-04-18 08:02:58.639055',NULL,1),(13,'luutronglu@gmail.com','L╞░u Trß╗ìng L╞░','$2a$10$aLtzcs31xElJa7LeE1JFte8WyuFovofciH9bqHHKCyyufPDpMhvcW','H├á Nß╗Öi',21,'2025-04-17 09:22:25.218612','luutronglu@gmail.com','MALE','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdXV0cm9uZ2x1QGdtYWlsLmNvbSIsImV4cCI6MTc1MzUyMjA2OSwiaWF0IjoxNzQ0ODgyMDY5LCJ1c2VyIjp7ImlkIjoxMywiZW1haWwiOiJsdXV0cm9uZ2x1QGdtYWlsLmNvbSIsIm5hbWUiOiJMxrB1IFRy4buNbmcgTMawIn19.4saD1sN4tpRrg_ohRarGOA-ea70TJZdaJhhj4wZtwLg',NULL,'2025-04-17 09:27:49.302730',NULL,3),(14,'user@example.com','John Doe','$2a$10$E1ESk3nJw33LMIIXP2UsoOKFC/KHDSOHNyDt22UYD8Higk8qRfA3u','123 Street',30,'2025-04-17 13:08:42.509756','user@example.com','MALE','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiZXhwIjoxNzUzNTM1NTAwLCJpYXQiOjE3NDQ4OTU1MDAsInVzZXIiOnsiaWQiOjE0LCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1lIjoiSm9obiBEb2UifX0.7SywJuO8TpywhFce80G3TqvJejXJJZ0W4oG-cyTF2ys',NULL,'2025-04-17 13:11:40.499249',1,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-21 20:40:46
