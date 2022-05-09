-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: tinyocean
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `acquario`
--

DROP TABLE IF EXISTS `acquario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acquario` (
  `articolo` int NOT NULL,
  `volume` float NOT NULL,
  `materiale` varchar(45) NOT NULL,
  `spessore` float NOT NULL,
  `isAllestito` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`articolo`),
  CONSTRAINT `articolo_acq` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `allestimento`
--

DROP TABLE IF EXISTS `allestimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allestimento` (
  `acquario` int NOT NULL,
  `decorazione` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `quantita` int NOT NULL,
  PRIMARY KEY (`acquario`,`decorazione`,`tipo`),
  KEY `decorazione_idx` (`decorazione`,`tipo`),
  CONSTRAINT `acquario` FOREIGN KEY (`acquario`) REFERENCES `acquario` (`articolo`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `decorazione` FOREIGN KEY (`decorazione`, `tipo`) REFERENCES `decorazione` (`articolo`, `tipo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `articolo`
--

DROP TABLE IF EXISTS `articolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articolo` (
  `id` int NOT NULL,
  `produttore` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descrizione` varchar(145) DEFAULT NULL,
  `prezzo` float NOT NULL DEFAULT '0',
  `stock` int NOT NULL DEFAULT '0',
  `peso` float NOT NULL,
  `altezza` float NOT NULL,
  `larghezza` float NOT NULL,
  `lunghezza` float NOT NULL,
  `saldo` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contenuto`
--

DROP TABLE IF EXISTS `contenuto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contenuto` (
  `numOrdine` bigint NOT NULL,
  `articolo` int NOT NULL,
  `nomeArticolo` varchar(45) NOT NULL,
  `prezzoAcquisto` float NOT NULL DEFAULT '0',
  `iva` int DEFAULT NULL,
  `quantita` int NOT NULL,
  PRIMARY KEY (`numOrdine`,`articolo`),
  KEY `articolo_contenuto_idx` (`articolo`),
  CONSTRAINT `articolo_contenuto` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `numOrdine` FOREIGN KEY (`numOrdine`) REFERENCES `ordine` (`numOrdine`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `decorazione`
--

DROP TABLE IF EXISTS `decorazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `decorazione` (
  `articolo` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`articolo`,`tipo`),
  CONSTRAINT `articolo_dec` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fatturazione`
--

DROP TABLE IF EXISTS `fatturazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fatturazione` (
  `utente` varchar(30) NOT NULL,
  `numCarta` bigint NOT NULL,
  PRIMARY KEY (`utente`,`numCarta`),
  KEY `metodoPagamento_idx` (`numCarta`),
  CONSTRAINT `metodoPagamento` FOREIGN KEY (`numCarta`) REFERENCES `metodo_pagamento` (`numCarta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `utente_fatturazione` FOREIGN KEY (`utente`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fondo`
--

DROP TABLE IF EXISTS `fondo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fondo` (
  `decorazione` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `tipoFondo` varchar(45) NOT NULL,
  PRIMARY KEY (`decorazione`,`tipo`),
  CONSTRAINT `tipo_fondo` FOREIGN KEY (`decorazione`, `tipo`) REFERENCES `decorazione` (`articolo`, `tipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `foto`
--

DROP TABLE IF EXISTS `foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foto` (
  `id` int NOT NULL,
  `sorgente` mediumblob NOT NULL,
  `articolo` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `articolo_foto_idx` (`articolo`),
  CONSTRAINT `articolo_foto` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `illuminazione`
--

DROP TABLE IF EXISTS `illuminazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `illuminazione` (
  `decorazione` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `tecnologia` varchar(45) NOT NULL,
  `luminosita` int NOT NULL,
  `tempColore` int NOT NULL,
  PRIMARY KEY (`decorazione`,`tipo`),
  CONSTRAINT `tipo_illuminazione` FOREIGN KEY (`decorazione`, `tipo`) REFERENCES `decorazione` (`articolo`, `tipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `indirizzo`
--

DROP TABLE IF EXISTS `indirizzo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `indirizzo` (
  `utente` varchar(30) NOT NULL,
  `via` varchar(80) NOT NULL,
  `numCivico` int NOT NULL,
  `cap` int NOT NULL,
  PRIMARY KEY (`utente`,`via`,`cap`,`numCivico`),
  KEY `via_idx` (`via`,`numCivico`,`cap`),
  CONSTRAINT `indirizzoSpedizione` FOREIGN KEY (`via`, `numCivico`, `cap`) REFERENCES `indirizzo_spedizione` (`via`, `numCivico`, `cap`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `utente_indirizzo` FOREIGN KEY (`utente`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `indirizzo_spedizione`
--

DROP TABLE IF EXISTS `indirizzo_spedizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `indirizzo_spedizione` (
  `via` varchar(80) NOT NULL,
  `numCivico` int NOT NULL,
  `cap` int NOT NULL,
  `provincia` varchar(45) NOT NULL,
  `citta` varchar(45) NOT NULL,
  PRIMARY KEY (`via`,`numCivico`,`cap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mangime`
--

DROP TABLE IF EXISTS `mangime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mangime` (
  `articolo` int NOT NULL,
  `composizione` varchar(100) NOT NULL,
  PRIMARY KEY (`articolo`),
  CONSTRAINT `articolo_mang` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manutenzione`
--

DROP TABLE IF EXISTS `manutenzione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manutenzione` (
  `articolo` int NOT NULL,
  `fine` varchar(45) NOT NULL,
  `modello` int DEFAULT NULL,
  `portata` int DEFAULT NULL,
  `potenza` int DEFAULT NULL,
  `tipoFiltro` varchar(45) DEFAULT NULL,
  `tipoTrattamento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`articolo`),
  CONSTRAINT `articolo_manut` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metodo_pagamento`
--

DROP TABLE IF EXISTS `metodo_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metodo_pagamento` (
  `numCarta` bigint NOT NULL,
  `tipo` int NOT NULL,
  `scadenza` date NOT NULL,
  `titolare` varchar(45) NOT NULL,
  `indirizzoFatturazione` varchar(80) NOT NULL,
  `predefinito` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`numCarta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine` (
  `numOrdine` bigint NOT NULL AUTO_INCREMENT,
  `utente` varchar(30) NOT NULL,
  `costoTot` float NOT NULL,
  `dataOrdine` date NOT NULL,
  `dataSpedizione` date DEFAULT NULL,
  `indirizzoSpedizione` varchar(100) NOT NULL,
  `metodoPagamento` varchar(70) NOT NULL,
  PRIMARY KEY (`numOrdine`),
  KEY `utente_ordine_idx` (`utente`),
  CONSTRAINT `utente_ordine` FOREIGN KEY (`utente`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pianta`
--

DROP TABLE IF EXISTS `pianta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pianta` (
  `decorazione` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `piano` varchar(10) NOT NULL,
  `livelloLuce` varchar(10) NOT NULL,
  PRIMARY KEY (`decorazione`,`tipo`),
  CONSTRAINT `tipo_pianta` FOREIGN KEY (`decorazione`, `tipo`) REFERENCES `decorazione` (`articolo`, `tipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recensione`
--

DROP TABLE IF EXISTS `recensione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recensione` (
  `utente` varchar(30) NOT NULL,
  `articolo` int NOT NULL,
  `voto` int NOT NULL,
  `commento` varchar(500) DEFAULT NULL,
  `dataPubblicazione` date NOT NULL,
  PRIMARY KEY (`utente`,`articolo`),
  KEY `articolo_recensione_idx` (`articolo`),
  CONSTRAINT `articolo_recensione` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `utente_recensione` FOREIGN KEY (`utente`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `username` varchar(30) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `numTelefono` varchar(45) DEFAULT NULL,
  `paeseResidenza` varchar(45) NOT NULL,
  `dataNascita` date NOT NULL,
  `admin` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-09 10:36:25
