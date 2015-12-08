-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 08-Dez-2015 às 21:16
-- Versão do servidor: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `clinicamaligna`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `acesso`
--

CREATE TABLE IF NOT EXISTS `acesso` (
`Id` int(11) NOT NULL,
  `Login` varchar(50) COLLATE latin1_general_ci DEFAULT NULL,
  `Senha` varchar(10) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `acesso`
--

INSERT INTO `acesso` (`Id`, `Login`, `Senha`) VALUES
(4, '17', 'polonia'),
(23, '23', '5555'),
(28, '16', '5568'),
(31, '25', 'wAhX'),
(47, 'm10', 'orta'),
(48, 'c551-pr', 'orta'),
(49, 'm11', 'RvT9'),
(50, 'c2255225252-sp', 'RvT9'),
(51, 'm12', 'mesme'),
(52, 'c245-sp', 'mesme'),
(53, 'm13', 'TFNW'),
(54, 'c5645', 'TFNW'),
(55, 'm14', 'LlsL'),
(56, 'c52589-rj', 'LlsL'),
(77, '26', '8OHy'),
(78, 'm15', 'dsadsdaddd'),
(79, 'c5258s9-rj', 'dsadsdaddd'),
(85, 'm16', 'WNN9'),
(86, 'c525291911165/sp', 'WNN9'),
(87, 'm17', 'sadas'),
(88, 'cadf-rj', 'sadas'),
(89, 'm18', 'PRONA 5656'),
(90, 'c5656-so', 'PRONA 5656'),
(91, 'm19', '8SxC'),
(92, 'c545-pr', '8SxC'),
(93, 'm20', '8x7o'),
(94, 'cdasds-pr', '8x7o'),
(95, 'm21', 'pzrx'),
(96, 'c85786-sd', 'pzrx'),
(97, 'm22', 'CXiM'),
(98, 'c2565154-mg', 'CXiM'),
(99, 'm23', '5161'),
(100, 'c52589-mr', '5161');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cargos`
--

CREATE TABLE IF NOT EXISTS `cargos` (
`Id` int(11) NOT NULL,
  `Nome` varchar(30) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `cargos`
--

INSERT INTO `cargos` (`Id`, `Nome`) VALUES
(1, 'Administrador de RH'),
(2, 'Recepcionista'),
(3, 'Secretária');

-- --------------------------------------------------------

--
-- Estrutura da tabela `consultas`
--

CREATE TABLE IF NOT EXISTS `consultas` (
`Id` int(11) NOT NULL,
  `Data` date DEFAULT NULL,
  `Inicio` time DEFAULT NULL,
  `Fim` time DEFAULT NULL,
  `IdPaciente` int(11) DEFAULT NULL,
  `IdDisponibilidade` int(11) DEFAULT NULL,
  `Observacoes` varchar(1024) COLLATE latin1_general_ci DEFAULT NULL,
  `PedidosDeExame` varchar(1024) COLLATE latin1_general_ci DEFAULT NULL,
  `Prescricoes` varchar(1024) COLLATE latin1_general_ci DEFAULT NULL,
  `Recomendacoes` varchar(1024) COLLATE latin1_general_ci DEFAULT NULL,
  `ativo` tinyint(3) DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `consultas`
--

INSERT INTO `consultas` (`Id`, `Data`, `Inicio`, `Fim`, `IdPaciente`, `IdDisponibilidade`, `Observacoes`, `PedidosDeExame`, `Prescricoes`, `Recomendacoes`, `ativo`) VALUES
(1, '2015-12-22', '09:00:00', '13:00:00', 4, 33, 'bolo', 'teste', 'remedio', 'tomar liquido alcolico', 0),
(2, '2015-12-25', '12:00:00', '13:00:00', 8, 36, 'teste', 'dsad', 'sdasds', 'dasd', 0),
(4, '2015-12-08', '10:19:00', '18:09:00', 8, 33, NULL, NULL, NULL, NULL, 0),
(9, '2015-12-28', '08:00:00', '13:00:00', 7, 25, NULL, NULL, NULL, NULL, 0),
(11, '2015-12-21', '03:00:00', '13:00:00', 12, 53, NULL, NULL, NULL, NULL, 1),
(13, '2015-12-30', '06:00:00', '21:00:00', 7, 27, NULL, NULL, NULL, NULL, 0),
(15, '2015-12-22', '09:09:00', '16:19:00', 7, 33, NULL, NULL, NULL, NULL, 1),
(16, '2015-12-25', '11:00:00', '13:00:00', 9, 29, NULL, NULL, NULL, NULL, 1),
(17, '2015-12-25', '10:00:00', '11:00:00', 7, 29, NULL, NULL, NULL, NULL, 0),
(18, '2015-12-20', '03:00:00', '05:00:00', 7, 38, NULL, NULL, NULL, NULL, 0),
(19, '2015-12-20', '05:00:00', '06:00:00', 9, 38, NULL, NULL, NULL, NULL, 0),
(20, '2015-12-20', '03:00:00', '06:00:00', 4, 45, NULL, NULL, NULL, NULL, 0),
(21, '2015-12-02', '12:00:00', '13:00:00', 8, 34, 'sdasdasdA', 'SADSAD', 'dASD', 'ASDASD', 1),
(22, '2015-12-02', '13:00:00', '14:00:00', 7, 34, 'Diagnosticada tumor no mindinho;\nVer se é maligno', 'Ultrassom da franja;\r\nexame de fluidos nasais', 'Remedio anti derrapante - 4 gotas ao mes.', 'Beber muito liquido;', 1),
(24, '2015-12-23', '09:00:00', '10:00:00', 7, 118, NULL, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `disponibilidades`
--

CREATE TABLE IF NOT EXISTS `disponibilidades` (
`Id` int(11) NOT NULL,
  `DiaDaSemana` int(11) DEFAULT NULL,
  `Inicio` time DEFAULT NULL,
  `Fim` time DEFAULT NULL,
  `IdProfissional` int(11) DEFAULT NULL,
  `IdEspecialidade` int(11) DEFAULT NULL,
  `ativo` tinyint(3) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `disponibilidades`
--

INSERT INTO `disponibilidades` (`Id`, `DiaDaSemana`, `Inicio`, `Fim`, `IdProfissional`, `IdEspecialidade`, `ativo`) VALUES
(24, 1, '00:00:00', '00:00:00', 10, 3, 0),
(25, 2, '08:00:00', '13:00:00', 10, 3, 1),
(26, 3, '00:00:00', '00:00:00', 10, 3, 0),
(27, 4, '06:00:00', '21:00:00', 10, 5, 1),
(28, 5, '00:00:00', '00:00:00', 10, 3, 0),
(29, 6, '11:00:00', '13:00:00', 10, 3, 1),
(30, 7, '00:00:00', '00:00:00', 10, 3, 0),
(31, 1, '00:00:00', '00:00:00', 11, 2, 0),
(32, 2, '00:00:00', '00:00:00', 11, 2, 0),
(33, 3, '09:09:00', '16:19:00', 11, 30, 1),
(34, 4, '12:00:00', '18:00:00', 11, 17, 1),
(35, 5, '00:00:00', '00:00:00', 11, 2, 0),
(36, 6, '00:12:00', '00:16:00', 11, 38, 1),
(37, 7, '00:00:00', '00:00:00', 11, 2, 0),
(38, 1, '03:00:00', '06:00:00', 12, 15, 0),
(39, 2, '00:00:00', '00:00:00', 12, 3, 0),
(40, 3, '00:00:00', '00:00:00', 12, 3, 0),
(41, 4, '00:00:00', '00:00:00', 12, 3, 0),
(42, 5, '00:00:00', '00:00:00', 12, 3, 0),
(43, 6, '00:00:00', '00:00:00', 12, 3, 0),
(44, 7, '00:00:00', '00:00:00', 12, 3, 0),
(45, 1, '04:00:00', '06:00:00', 13, 5, 1),
(46, 2, '00:00:00', '00:00:00', 13, 3, 0),
(47, 3, '00:00:00', '00:00:00', 13, 3, 0),
(48, 4, '00:00:00', '00:00:00', 13, 3, 0),
(49, 5, '10:00:00', '16:00:00', 13, 15, 1),
(50, 6, '00:00:00', '00:00:00', 13, 3, 0),
(51, 7, '00:00:00', '00:00:00', 13, 3, 0),
(52, 1, '00:00:00', '00:00:00', 14, 3, 0),
(53, 2, '03:00:00', '13:00:00', 14, 3, 1),
(54, 3, '00:00:00', '00:00:00', 14, 3, 0),
(55, 4, '07:00:00', '09:00:00', 14, 3, 1),
(56, 5, '00:00:00', '00:00:00', 14, 3, 0),
(57, 6, '00:00:00', '00:00:00', 14, 3, 0),
(58, 7, '00:00:00', '00:00:00', 14, 3, 0),
(59, 1, '00:00:00', '00:00:00', 15, 3, 0),
(60, 2, '01:00:00', '04:00:00', 15, 3, 1),
(61, 3, '00:00:00', '00:00:00', 15, 3, 0),
(62, 4, '00:00:00', '00:00:00', 15, 3, 0),
(63, 5, '00:00:00', '00:00:00', 15, 3, 0),
(64, 6, '16:00:00', '20:00:00', 15, 3, 1),
(65, 7, '00:00:00', '00:00:00', 15, 3, 0),
(66, 1, '00:00:00', '00:00:00', 16, 66, 0),
(67, 2, '00:00:00', '00:00:00', 16, 66, 0),
(68, 3, '00:00:00', '00:00:00', 16, 66, 0),
(69, 4, '00:00:00', '00:00:00', 16, 66, 0),
(70, 5, '00:00:00', '00:00:00', 16, 66, 0),
(71, 6, '00:00:00', '00:00:00', 16, 66, 0),
(72, 7, '00:00:00', '00:00:00', 16, 66, 0),
(73, 1, '00:00:00', '00:00:00', 17, 1, 0),
(74, 2, '00:00:00', '00:00:00', 17, 1, 0),
(75, 3, '00:00:00', '00:00:00', 17, 1, 0),
(76, 4, '00:00:00', '00:00:00', 17, 1, 0),
(77, 5, '00:00:00', '00:00:00', 17, 1, 0),
(78, 6, '00:00:00', '00:00:00', 17, 1, 0),
(79, 7, '00:00:00', '00:00:00', 17, 1, 0),
(80, 1, '00:00:00', '00:00:00', 18, 6, 0),
(81, 2, '00:00:00', '00:00:00', 18, 6, 1),
(82, 3, '00:00:00', '00:00:00', 18, 6, 1),
(83, 4, '00:00:00', '00:00:00', 18, 6, 0),
(84, 5, '00:00:00', '00:00:00', 18, 6, 0),
(85, 6, '00:00:00', '00:00:00', 18, 6, 0),
(86, 7, '00:00:00', '00:00:00', 18, 6, 0),
(87, 1, '06:00:00', '13:00:00', 19, 33, 1),
(88, 2, '00:00:00', '02:00:00', 19, 18, 1),
(89, 3, '00:00:00', '00:00:00', 19, 6, 0),
(90, 4, '00:00:00', '00:00:00', 19, 6, 0),
(91, 5, '00:00:00', '11:00:00', 19, 39, 1),
(92, 6, '00:00:00', '00:00:00', 19, 6, 0),
(93, 7, '00:00:00', '00:00:00', 19, 6, 0),
(94, 1, '00:00:00', '00:00:00', 20, 67, 0),
(95, 2, '00:00:00', '00:00:00', 20, 67, 0),
(96, 3, '00:00:00', '00:00:00', 20, 67, 0),
(97, 4, '00:00:00', '00:00:00', 20, 67, 0),
(98, 5, '08:00:00', '14:00:00', 20, 67, 1),
(99, 6, '00:00:00', '00:00:00', 20, 67, 0),
(100, 7, '00:00:00', '00:00:00', 20, 67, 0),
(101, 1, '08:00:00', '18:00:00', 21, 1, 1),
(102, 2, '00:00:00', '07:00:00', 21, 1, 0),
(103, 3, '08:00:00', '12:00:00', 21, 1, 1),
(104, 4, '10:00:00', '19:00:00', 21, 1, 1),
(105, 5, '13:00:00', '18:00:00', 21, 1, 1),
(106, 6, '10:00:00', '18:00:00', 21, 1, 1),
(107, 7, '08:00:00', '15:00:00', 21, 1, 1),
(108, 1, '00:00:00', '00:00:00', 22, 1, 0),
(109, 2, '08:00:00', '16:00:00', 22, 1, 1),
(110, 3, '00:00:00', '00:00:00', 22, 1, 0),
(111, 4, '00:00:00', '00:00:00', 22, 1, 0),
(112, 5, '00:00:00', '00:00:00', 22, 1, 0),
(113, 6, '08:00:00', '16:00:00', 22, 1, 1),
(114, 7, '00:00:00', '00:00:00', 22, 1, 0),
(115, 1, '00:00:00', '00:00:00', 23, 5, 0),
(116, 2, '00:00:00', '00:00:00', 23, 5, 0),
(117, 3, '00:00:00', '00:00:00', 23, 14, 0),
(118, 4, '09:00:00', '17:00:00', 23, 5, 1),
(119, 5, '00:00:00', '00:00:00', 23, 5, 0),
(120, 6, '00:00:00', '00:00:00', 23, 5, 0),
(121, 7, '00:00:00', '00:00:00', 23, 5, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `especialidades`
--

CREATE TABLE IF NOT EXISTS `especialidades` (
`Id` int(11) NOT NULL,
  `Nome` varchar(50) COLLATE latin1_general_ci DEFAULT NULL,
  `IdProfissao` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `especialidades`
--

INSERT INTO `especialidades` (`Id`, `Nome`, `IdProfissao`) VALUES
(1, 'Acupuntura', 1),
(2, 'Imunologia', 1),
(3, 'Anestesiologia', 1),
(4, 'Angiologia', 1),
(5, 'Oncologia', 1),
(6, 'Cardiologia', 1),
(7, 'Cirurgia cardiovascular', 1),
(8, 'Cirurgia da mão', 1),
(9, 'Cirurgia de cabeça e pescoço', 1),
(10, 'Cirurgia do aparelho digestivo', 1),
(11, 'Cirurgia geral', 1),
(12, 'Cirurgia pediátrica', 1),
(13, 'Cirurgia plástica', 1),
(14, 'Cirurgia torácica', 1),
(15, 'Cirurgia vascular', 1),
(16, 'Clínica médica', 1),
(17, 'Coloproctologia', 1),
(18, 'Dermatologia', 1),
(19, 'Endocrinologia', 1),
(20, 'Endoscopia', 1),
(21, 'Gastroenterologia', 1),
(22, 'Genética médica', 1),
(23, 'Geriatria', 1),
(24, 'Ginecologia e obstetrícia', 1),
(25, 'Hematopoiese', 1),
(26, 'Homeopatia', 1),
(27, 'Mastologia', 1),
(28, 'Medicina de família e comunidade', 1),
(29, 'Medicina do trabalho', 1),
(30, 'Medicina esportiva', 1),
(31, 'Medicina física e reabilitação', 1),
(32, 'Medicina intensiva', 1),
(33, 'Medicina legal', 1),
(34, 'Medicina nuclear', 1),
(35, 'Saúde pública', 1),
(36, 'Nefrologia', 1),
(37, 'Neurocirurgia', 1),
(38, 'Neurologia', 1),
(39, 'Nutrologia', 1),
(40, 'Oftalmologia', 1),
(41, 'Infectologia', 1),
(42, 'Ortopedia', 1),
(43, 'Otorrinolaringologia', 1),
(44, 'Patologia', 1),
(45, 'Patologia clínica', 1),
(46, 'Pediatria', 1),
(47, 'Pneumologia', 1),
(48, 'Psiquiatria', 1),
(49, 'Radiologia', 1),
(50, 'Radioterapia', 1),
(51, 'Reumatologia', 1),
(52, 'Urologia', 1),
(53, 'Fisioterapia Pediátrica, Neonatológica e Hebeátric', 2),
(54, 'Fisioterapia Gerontológica', 2),
(55, 'Fisioterapia Dermato Funcional', 2),
(56, 'Fisioterapia do Trabalho', 2),
(57, 'Fisioterapia Uroginecofuncional e Obstétrica', 2),
(58, 'Fisioterapia Neurofuncional', 2),
(59, 'Fisioterapia Traumato-Ortopédico-Funcional', 2),
(60, 'Fisioterapia Respiratória', 2),
(61, 'Fisioterapia Orofacial', 2),
(62, 'Fisioterapia Desportiva', 2),
(63, 'Fisioterapia Manipulativa', 2),
(64, 'Fisioterapia Oncofuncional', 2),
(65, 'Fisioterapia em Terapia Intensiva', 2),
(66, 'Fisioterapia em Home Care', 2),
(67, 'Fisioterapia Cardiológica', 2),
(68, 'Fisioterapia Ortóptica', 2),
(69, 'Fisioterapia na Reabilitação Vestibular', 2),
(70, 'Fisioterapia na Reeducação Postural', 2),
(89, 'Audiologia', 3),
(90, 'Linguagem', 3),
(91, 'Motricidade Orofacial', 3),
(92, 'Disfagia', 3),
(93, 'Voz', 3),
(95, 'Educacional', 3),
(96, 'Psicologia Escolar/Educacional', 4),
(97, 'Psicologia Organizacional e do Trabalho', 4),
(98, 'Psicologia de Trânsito', 4),
(99, 'Psicologia Jurídica', 4),
(100, 'Psicologia Clínica', 4),
(101, 'Psicologia Hospitalar', 4),
(102, 'Psicopedagogia', 4),
(103, 'Psicomotricidade', 4),
(104, 'Psicologia Social', 4),
(105, 'Neuropsicologia', 4),
(106, 'Acupuntura terapia', 5),
(107, 'Contextos Hospitalares', 5),
(108, 'Contextos Sociais', 5),
(110, 'Saúde da Família', 5),
(111, 'Saúde Funcional', 5),
(112, 'Saúde Mental', 5),
(113, 'Odontopediatria', 6),
(114, 'Radiologia Odontológica e Imaginologia', 6),
(115, 'Dentística Restauradora', 6),
(116, 'Dentística', 6),
(117, 'Endodontia', 6),
(118, 'Prótese Dentária', 6),
(119, 'Ortodontia e Ortopedia Facial', 6),
(120, 'Ortodontia', 6),
(121, 'Ortopedia Funcional dos Maxilares', 6),
(122, 'Implantodontia', 6),
(123, 'Cirurgia e Traumatologia Buco', 6),
(124, 'Prótese Buco – Maxilo – Facial', 6),
(125, 'Odontologia Legal', 6),
(126, 'Odontologia em Saúde Coletiva', 6),
(127, 'Saúde Coletiva', 6),
(128, 'Estomatologia', 6),
(129, 'Patologia Bucal', 6),
(130, 'Disfunção Têmporo Mandibular e Dor Orofacial', 6),
(131, 'Odontogeriatria', 6),
(132, 'Odontologia para Pacientes com Necessidades Especi', 6),
(133, 'Odontologia do Trabalho', 6),
(134, 'Nutrição', 7);

-- --------------------------------------------------------

--
-- Estrutura da tabela `especializacoes`
--

CREATE TABLE IF NOT EXISTS `especializacoes` (
  `IdProfissional` int(11) DEFAULT NULL,
  `IdEspecialidade` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `especializacoes`
--

INSERT INTO `especializacoes` (`IdProfissional`, `IdEspecialidade`) VALUES
(11, 2),
(11, 17),
(11, 30),
(11, 28),
(11, 34),
(11, 38),
(11, 47),
(11, 51),
(11, 11),
(14, 3),
(13, 3),
(13, 5),
(13, 15),
(12, 3),
(12, 5),
(12, 15),
(16, 66),
(17, 14),
(17, 11),
(10, 3),
(10, 5),
(10, 15),
(19, 6),
(19, 18),
(19, 33),
(19, 39),
(18, 6),
(21, 1),
(21, 3),
(21, 20),
(21, 35),
(21, 48),
(21, 47),
(21, 46),
(21, 45),
(21, 44),
(21, 52),
(21, 51),
(22, 116),
(15, 3),
(15, 5),
(15, 15),
(23, 5),
(23, 14),
(20, 67),
(20, 62);

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionarios`
--

CREATE TABLE IF NOT EXISTS `funcionarios` (
`Id` int(11) NOT NULL,
  `Cargo` int(11) DEFAULT NULL,
  `Nome` varchar(50) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `funcionarios`
--

INSERT INTO `funcionarios` (`Id`, `Cargo`, `Nome`) VALUES
(16, 2, 'Joselita silva'),
(17, 1, 'adolf hitler'),
(23, 2, 'Creuza'),
(25, 1, 'Abigobaldo'),
(26, 2, 'Analita souza');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pacientes`
--

CREATE TABLE IF NOT EXISTS `pacientes` (
`Id` int(11) NOT NULL,
  `Nome` varchar(50) COLLATE latin1_general_ci DEFAULT NULL,
  `Senha` varchar(50) COLLATE latin1_general_ci DEFAULT NULL,
  `CEP` int(11) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `Complemento` varchar(100) COLLATE latin1_general_ci DEFAULT NULL,
  `Disable` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `pacientes`
--

INSERT INTO `pacientes` (`Id`, `Nome`, `Senha`, `CEP`, `Numero`, `Complemento`, `Disable`) VALUES
(4, 'Romario', 'alterado', 123456, 123456, 'a', 0),
(7, 'Ronaldo', NULL, 123, 321, 'b', 1),
(8, 'Rivaldo', NULL, 321654, 321654, 'b', 0),
(9, 'blablabla', NULL, 69696969, 24, 'ui', 0),
(10, 'htrhsrth', NULL, 635465, 5, 't', 0),
(12, 'op', 'op', 123456, 132, 'a', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `profissionaissaude`
--

CREATE TABLE IF NOT EXISTS `profissionaissaude` (
`Id` int(11) NOT NULL,
  `CPF` bigint(12) DEFAULT NULL,
  `IdClasse` varchar(15) COLLATE latin1_general_ci DEFAULT NULL,
  `Nome` varchar(55) COLLATE latin1_general_ci DEFAULT NULL,
  `ativo` tinyint(3) DEFAULT '1' COMMENT 'se está tivo ou não'
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `profissionaissaude`
--

INSERT INTO `profissionaissaude` (`Id`, `CPF`, `IdClasse`, `Nome`, `ativo`) VALUES
(10, 40812695807, '551-pr', 'Garcia da Orta', 1),
(11, 40812695800, '2255225252-sp', 'Osvaldo Cruz ', 1),
(12, 85224656958, '245-sp', 'Franz Anton Mesme', 1),
(13, 85855412658, '55478-sp', 'Carlos Chagas', 1),
(14, 56264566645, '52589-rj', 'Teste da silva', 1),
(15, 40812695802, '5258s9-rj', 'Rodolfino', 1),
(16, 58495275401, '525291911165/sp', 'Drauzio Varela', 1),
(17, 42585465812, 'adf-rj', 'Adolfo Lutz', 1),
(18, 53842859614, '5656-so', 'Enéas Ferreira Carneiro', 1),
(19, 25845695841, '545-pr', 'Bernardino Ramazzini', 1),
(20, 56984575158, 'dasds-pr', 'Juscelino Kubitschek', 1),
(21, 25485614744, '85786-sd', 'Adib Jatene ', 1),
(22, 26845825695, '2565154-mg', 'Vital Brazil', 1),
(23, 25645874511, '52589-mr', 'Moacyr Scliar', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `profissoes`
--

CREATE TABLE IF NOT EXISTS `profissoes` (
`Id` int(11) NOT NULL,
  `Nome` varchar(25) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `profissoes`
--

INSERT INTO `profissoes` (`Id`, `Nome`) VALUES
(1, 'Médico'),
(2, 'Fisioterapeuta'),
(3, 'Fonoaudiólogo'),
(4, 'Psicólogo'),
(5, 'Terapeuta Ocupacional'),
(6, 'Dentista'),
(7, 'Nutricionista');

-- --------------------------------------------------------

--
-- Estrutura da tabela `telefones`
--

CREATE TABLE IF NOT EXISTS `telefones` (
  `Area` int(11) NOT NULL DEFAULT '0',
  `Numero` int(11) NOT NULL DEFAULT '0',
  `IdTipo` int(11) DEFAULT NULL,
  `IdPaciente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `telefones`
--

INSERT INTO `telefones` (`Area`, `Numero`, `IdTipo`, `IdPaciente`) VALUES
(19, 123456789, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tiposdetelefone`
--

CREATE TABLE IF NOT EXISTS `tiposdetelefone` (
`Id` int(11) NOT NULL,
  `Nome` varchar(15) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Extraindo dados da tabela `tiposdetelefone`
--

INSERT INTO `tiposdetelefone` (`Id`, `Nome`) VALUES
(1, 'residencial'),
(2, 'celular'),
(3, 'comercial'),
(4, 'recado');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acesso`
--
ALTER TABLE `acesso`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `cargos`
--
ALTER TABLE `cargos`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `consultas`
--
ALTER TABLE `consultas`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `disponibilidades`
--
ALTER TABLE `disponibilidades`
 ADD PRIMARY KEY (`Id`), ADD KEY `IdProfissional` (`IdProfissional`), ADD KEY `IdEspecialidade` (`IdEspecialidade`);

--
-- Indexes for table `especialidades`
--
ALTER TABLE `especialidades`
 ADD PRIMARY KEY (`Id`), ADD UNIQUE KEY `Nome` (`Nome`), ADD KEY `IdProfissao` (`IdProfissao`);

--
-- Indexes for table `especializacoes`
--
ALTER TABLE `especializacoes`
 ADD KEY `IdProfissional` (`IdProfissional`), ADD KEY `IdEspecialidade` (`IdEspecialidade`);

--
-- Indexes for table `funcionarios`
--
ALTER TABLE `funcionarios`
 ADD PRIMARY KEY (`Id`), ADD KEY `Cargo` (`Cargo`);

--
-- Indexes for table `pacientes`
--
ALTER TABLE `pacientes`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `profissionaissaude`
--
ALTER TABLE `profissionaissaude`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `profissoes`
--
ALTER TABLE `profissoes`
 ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `telefones`
--
ALTER TABLE `telefones`
 ADD PRIMARY KEY (`Area`,`Numero`), ADD KEY `IdPaciente` (`IdPaciente`), ADD KEY `IdTipo` (`IdTipo`);

--
-- Indexes for table `tiposdetelefone`
--
ALTER TABLE `tiposdetelefone`
 ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acesso`
--
ALTER TABLE `acesso`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=101;
--
-- AUTO_INCREMENT for table `cargos`
--
ALTER TABLE `cargos`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `consultas`
--
ALTER TABLE `consultas`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `disponibilidades`
--
ALTER TABLE `disponibilidades`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=122;
--
-- AUTO_INCREMENT for table `especialidades`
--
ALTER TABLE `especialidades`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=135;
--
-- AUTO_INCREMENT for table `funcionarios`
--
ALTER TABLE `funcionarios`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `pacientes`
--
ALTER TABLE `pacientes`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `profissionaissaude`
--
ALTER TABLE `profissionaissaude`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `profissoes`
--
ALTER TABLE `profissoes`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tiposdetelefone`
--
ALTER TABLE `tiposdetelefone`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `disponibilidades`
--
ALTER TABLE `disponibilidades`
ADD CONSTRAINT `Disponibilidades_ibfk_1` FOREIGN KEY (`IdProfissional`) REFERENCES `profissionaissaude` (`Id`),
ADD CONSTRAINT `Disponibilidades_ibfk_2` FOREIGN KEY (`IdEspecialidade`) REFERENCES `especialidades` (`Id`);

--
-- Limitadores para a tabela `especialidades`
--
ALTER TABLE `especialidades`
ADD CONSTRAINT `Especialidades_ibfk_1` FOREIGN KEY (`IdProfissao`) REFERENCES `profissoes` (`Id`);

--
-- Limitadores para a tabela `especializacoes`
--
ALTER TABLE `especializacoes`
ADD CONSTRAINT `Especializacoes_ibfk_1` FOREIGN KEY (`IdProfissional`) REFERENCES `profissionaissaude` (`Id`),
ADD CONSTRAINT `Especializacoes_ibfk_2` FOREIGN KEY (`IdEspecialidade`) REFERENCES `especialidades` (`Id`);

--
-- Limitadores para a tabela `funcionarios`
--
ALTER TABLE `funcionarios`
ADD CONSTRAINT `Funcionarios_ibfk_2` FOREIGN KEY (`Cargo`) REFERENCES `cargos` (`Id`);

--
-- Limitadores para a tabela `telefones`
--
ALTER TABLE `telefones`
ADD CONSTRAINT `Telefones_ibfk_1` FOREIGN KEY (`IdPaciente`) REFERENCES `pacientes` (`Id`),
ADD CONSTRAINT `Telefones_ibfk_2` FOREIGN KEY (`IdTipo`) REFERENCES `tiposdetelefone` (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
