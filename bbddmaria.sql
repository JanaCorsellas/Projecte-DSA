-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versió del servidor:          11.3.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versió:              12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for scaperoom
DROP DATABASE IF EXISTS `scaperoom`;
CREATE DATABASE IF NOT EXISTS `scaperoom` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `scaperoom`;

-- Dumping structure for table scaperoom.item
DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` varchar(50) NOT NULL,
  `color` varchar(50) DEFAULT NULL,
  `preu` int(11) DEFAULT NULL,
  `descripcio` varchar(50) DEFAULT NULL,
  `imatge` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='taula que guarda la llista de productes de la botiga';

-- Dumping data for table scaperoom.item: ~4 rows (approximately)
INSERT INTO `item` (`id`, `color`, `preu`, `descripcio`, `imatge`) VALUES
	('1', 'vermell', 1, 'Guanyes una vida', NULL),
	('2', 'verd', 2, 'Tens més temps', '(NULL)'),
	('3', 'groc', 3, 'Obtens una pista', NULL),
	('4', 'blau', 4, 'Recompensa', NULL);

-- Dumping structure for table scaperoom.motxilla
DROP TABLE IF EXISTS `motxilla`;
CREATE TABLE IF NOT EXISTS `motxilla` (
  `id` varchar(50) NOT NULL,
  `idUsuari` varchar(50) DEFAULT NULL,
  `idItem` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table scaperoom.motxilla: ~0 rows (approximately)

-- Dumping structure for table scaperoom.partida
DROP TABLE IF EXISTS `partida`;
CREATE TABLE IF NOT EXISTS `partida` (
  `id` varchar(50) NOT NULL,
  `idJugador` varchar(50) DEFAULT NULL,
  `timer` time DEFAULT NULL,
  `segonsRestants` time DEFAULT NULL,
  `data` date DEFAULT NULL,
  `vides` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='taula que guarda la llista de partides ';

-- Dumping data for table scaperoom.partida: ~0 rows (approximately)

-- Dumping structure for table scaperoom.sala
DROP TABLE IF EXISTS `sala`;
CREATE TABLE IF NOT EXISTS `sala` (
  `id` varchar(50) NOT NULL,
  `numeroSala` int(11) DEFAULT NULL,
  `estatPorta` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='taula que guarda la llista de sales';

-- Dumping data for table scaperoom.sala: ~4 rows (approximately)
INSERT INTO `sala` (`id`, `numeroSala`, `estatPorta`) VALUES
	('1', 1, NULL),
	('2', 2, NULL),
	('3', 3, NULL),
	('4', 4, NULL);

-- Dumping structure for table scaperoom.usuari
DROP TABLE IF EXISTS `usuari`;
CREATE TABLE IF NOT EXISTS `usuari` (
  `id` varchar(50) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `cognom` varchar(50) DEFAULT NULL,
  `nomusuari` varchar(50) DEFAULT NULL,
  `password1` varchar(50) DEFAULT NULL,
  `password2` varchar(50) DEFAULT NULL,
  `coins` int(11) DEFAULT NULL,
  `clau` tinyint(4) DEFAULT NULL,
  `skin` varchar(50) DEFAULT NULL,
  `puntuacio` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='taula que guarda la llista d''usuaris registrats';

-- Dumping data for table scaperoom.usuari: ~4 rows (approximately)
INSERT INTO `usuari` (`id`, `nom`, `cognom`, `nomusuari`, `password1`, `password2`, `coins`, `clau`, `skin`, `puntuacio`) VALUES
	('1', 'Daniel', 'Perez', 'dperez', '111', '111', 0, NULL, 'verd', NULL),
	('2', 'Carla', 'Otero', 'cotero', '222', '222', 0, NULL, 'verd', NULL),
	('3', 'Andrea', 'Zapata', 'azapata', '333', '333', 0, NULL, 'verd', NULL),
	('4', 'Jana', 'Corsellas', 'jcorsellas', '444', '444', 0, NULL, 'verd', NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
