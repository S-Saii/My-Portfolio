-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 18, 2025 at 07:24 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Student`
--

-- --------------------------------------------------------

--
-- Table structure for table `studentinfo`
--

CREATE TABLE `studentinfo` (
  `Student` varchar(10) NOT NULL,
  `First name` varchar(30) NOT NULL,
  `Last name` varchar(30) NOT NULL,
  `Year level` varchar(1) NOT NULL,
  `Course` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentinfo`
--

INSERT INTO `studentinfo` (`Student`, `First name`, `Last name`, `Year level`, `Course`) VALUES
('17-25-6199', 'SOFIA ', 'ALORAN', '1', 'BSCS'),
('17-25-6138', 'MARINEL ', 'ANGLITA', '1', 'BSEMC'),
('17-25-6152', 'BRIAN MIKKO', 'ARAVILLA', '1', 'BSEMC'),
('17-25-5910', 'RAIZA JULIENNE ', 'BABAN', '1', 'BSIS'),
('17-25-6264', 'JOHN MARK ', 'BAPTISTA', '1', 'BSEMC'),
('17-25-6086', 'CHRISTIAN', 'BERNABE', '1', 'BSCS'),
('17-25-5865', 'JOSE DANIEL ', 'CORONADO', '1', 'BSEMC'),
('17-25-5612', 'GABRIEL REY ', 'CREDO', '1', 'BSEMC'),
('17-25-5713', 'DANIELLA ', 'CRISOSTOMO', '1', 'BSEMC'),
('17-25-6256', 'CHRISTIAN LEONARD', 'DE GUZMAN', '1', 'BSCS'),
('17-25-6190', 'ALEXANDRINE', 'DEL ROSARIO', '1', 'BSIS'),
('17-25-6034', 'YUAN MATTHEW ', ' ENRIQUEZ', '1', ' BSEMC'),
('17-25-5718', ' SIDNEI ', 'GORDO', '1', 'BSIS'),
('17-25-5636', 'NEIL ARKIN ', ' GUANZON', '1', 'BSIS'),
('17-25-6058', 'GINO FREUD', 'HOBAYAN', '1', 'BSCS'),
('17-25-6212', 'NIGEL SEAN ', 'LEGATA', '1', 'BSEMC'),
('17-25-5961', 'GUILIAN DEAN', 'NASTOR', '1', 'BSIS'),
('17-25-6145', 'SAMUEL EISYSS ', 'ODOÑO', '1', 'BSIS'),
('17-25-5874', 'JAMES MARIUS ', 'PANADO', '1', 'BSCS'),
('17-25-5978', 'SEI ICHIKO', 'SABIO', '1', 'BSIS'),
('17-25-6254', 'JOSHUA LAUREN', 'VILLARUEL', '1', 'BSEMC'),
('17-25-5865', 'JOSE DANIEL', 'CORONADO', '1', 'BSEMC'),
('17-25-6199', 'Sofia', 'Aloran', '1', 'BSCS'),
('17-25-6138', 'Marinel', 'Anglita', '1', 'BSEMC'),
('17-25-6152', 'Brian Mikko', 'Aravilla', '1', 'BSIS'),
('17-25-6264', 'John Mark', 'Baptista', '1', 'BSEMC'),
('17-25-6086', 'Christian', 'Bernabe', '1', 'BSCS'),
('17-25-5865', 'Jose Daniel', 'Coronado', '1', 'BSEMC');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
