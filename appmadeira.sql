-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 25-Abr-2016 às 22:17
-- Versão do servidor: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `appmadeira`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `auditoria`
--

CREATE TABLE IF NOT EXISTS `auditoria` (
  `id_controle_geral` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque` int(11) NOT NULL,
  `id_supervisor` varchar(10) NOT NULL,
  `id_operario` varchar(10) NOT NULL,
  `info_alterada` varchar(100) NOT NULL,
  `data_alteracao` timestamp NOT NULL,
  PRIMARY KEY (`id_controle_geral`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_carvao`
--

CREATE TABLE IF NOT EXISTS `controle_carvao` (
  `id_controle_carvao` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque_p` int(11) NOT NULL,
  `talhao` varchar(10) NOT NULL,
  `forno` varchar(10) NOT NULL,
  `id_operario_mad` varchar(10) NOT NULL,
  `volume_madeira` float NOT NULL,
  `data_entrada_madeira_forno` varchar(19) NOT NULL,
  `id_operario_carv` varchar(20) NOT NULL,
  `volume_carvao` float DEFAULT NULL,
  `data_saida_carvao_forno` varchar(19) DEFAULT NULL,
  `rend_grav_forno` float DEFAULT NULL,
  PRIMARY KEY (`id_controle_carvao`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `controle_carvao`
--

INSERT INTO `controle_carvao` (`id_controle_carvao`, `id_estoque_p`, `talhao`, `forno`, `id_operario_mad`, `volume_madeira`, `data_entrada_madeira_forno`, `id_operario_carv`, `volume_carvao`, `data_saida_carvao_forno`, `rend_grav_forno`) VALUES
(1, 1, '1', '0', 'op_c.4', 0, '04-04-2016 09:37:14', '---', 0, '00-00-0000 00:00:00', NULL),
(2, 11, '1', 'f100', 'op_c.4', 100, '04-04-2016 10:28:44', 'op_c.4', 79, '04-04-2016 10:30:53', 0.79),
(3, 11, '1', 'f30', 'op_c.4', 30, '04-04-2016 10:48:59', 'op_c.4', 23, '04-04-2016 10:55:11', 0.766667),
(4, 0, '', '', '', 0, '', '', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_madeira`
--

CREATE TABLE IF NOT EXISTS `controle_madeira` (
  `id_controle_madeira` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque_p` int(11) NOT NULL,
  `id_operario` varchar(10) NOT NULL,
  `talhao` varchar(10) NOT NULL,
  `data_entrega` varchar(19) NOT NULL,
  `mad_volume_m_stereo` float NOT NULL,
  `mad_volume_m3` float NOT NULL,
  `altura1_t` float NOT NULL,
  `altura2_t` float NOT NULL,
  `altura3_t` float NOT NULL,
  `comprimento_t` float NOT NULL,
  `largura_t` float NOT NULL,
  `peso_t` float NOT NULL,
  `altura1_bt` float DEFAULT '0',
  `altura2_bt` float DEFAULT '0',
  `altura3_bt` float DEFAULT '0',
  `comprimento_bt` float DEFAULT '0',
  `largura_bt` float DEFAULT '0',
  `peso_bt` float DEFAULT '0',
  PRIMARY KEY (`id_controle_madeira`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `controle_madeira`
--

INSERT INTO `controle_madeira` (`id_controle_madeira`, `id_estoque_p`, `id_operario`, `talhao`, `data_entrega`, `mad_volume_m_stereo`, `mad_volume_m3`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `altura1_bt`, `altura2_bt`, `altura3_bt`, `comprimento_bt`, `largura_bt`, `peso_bt`) VALUES
(1, 11, 'op_m.3', '1', '04-04-2016 10:27:39', 488.858, 349.184, 3.1, 3.3, 2.8, 16, 3.2, 500, 0, 0, 0, 0, 0, 0),
(2, 13, 'op_m.3', '1', '04-04-2016 12:58:03', 289.275, 206.625, 1.9, 2.5, 2.9, 18, 3.5, 600, 0, 0, 0, 0, 0, 0),
(3, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `estoque_principal`
--

CREATE TABLE IF NOT EXISTS `estoque_principal` (
  `id_estoque_p` int(11) NOT NULL AUTO_INCREMENT,
  `estado` varchar(20) NOT NULL,
  `bloco` varchar(20) NOT NULL,
  `municipio` varchar(20) NOT NULL,
  `fazenda` varchar(20) NOT NULL,
  `projeto` varchar(10) NOT NULL,
  `upc` varchar(3) NOT NULL,
  `talhao` int(11) NOT NULL,
  `area` float NOT NULL,
  `material_genetico` varchar(20) NOT NULL,
  `m3_ha` float DEFAULT '0',
  `talhadia` int(11) DEFAULT '0',
  `ano_rotacao` int(11) DEFAULT '0',
  `data_plantio` varchar(19) DEFAULT '00-00-0000',
  `data_rotacao_1` varchar(19) DEFAULT '00-00-0000',
  `data_rotacao_2` varchar(19) DEFAULT '00-00-0000',
  `data_rotacao_3` varchar(19) DEFAULT '00-00-0000',
  `idade_corte1` float DEFAULT '0',
  `idade_corte2` float DEFAULT '0',
  `idade_corte3` float DEFAULT '0',
  `idade_hoje` float DEFAULT '0',
  `conducao` varchar(10) DEFAULT '---',
  `categoria` varchar(20) DEFAULT '---',
  `situacao` varchar(20) DEFAULT '---',
  `ima` float DEFAULT '0',
  `mdc_ha` float DEFAULT '0',
  `densidade_madeira` float DEFAULT '0',
  `densidade_carvao` float DEFAULT '0',
  `mad_ton_ha` float DEFAULT '0',
  `carv_ton_ha` float DEFAULT '0',
  `id_operario` varchar(10) NOT NULL,
  `data_estoque` varchar(19) NOT NULL,
  `vol_mad_estimado` float DEFAULT '0',
  `vol_mad_transp` float DEFAULT '0',
  `vol_mad_balanco` float DEFAULT '0',
  `mdc_estimado` float DEFAULT '0',
  `mdc_transp` float DEFAULT '0',
  `mdc_balanco` float DEFAULT '0',
  `mad_ton_estimado` float DEFAULT '0',
  `mad_ton_transp` float DEFAULT '0',
  `mad_ton_balanco` float DEFAULT '0',
  `carv_ton_estimado` float DEFAULT '0',
  `carv_ton_transp` float DEFAULT '0',
  `carv_ton_balanco` float DEFAULT '0',
  `madeira_praca` float DEFAULT '0',
  `madeira_forno` float DEFAULT '0',
  `rend_grav_estimado` float DEFAULT '0',
  `rend_grav_real` float DEFAULT '0',
  `fator_empilalhemto` float DEFAULT '0',
  PRIMARY KEY (`id_estoque_p`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

--
-- Extraindo dados da tabela `estoque_principal`
--

INSERT INTO `estoque_principal` (`id_estoque_p`, `estado`, `bloco`, `municipio`, `fazenda`, `projeto`, `upc`, `talhao`, `area`, `material_genetico`, `m3_ha`, `talhadia`, `ano_rotacao`, `data_plantio`, `data_rotacao_1`, `data_rotacao_2`, `data_rotacao_3`, `idade_corte1`, `idade_corte2`, `idade_corte3`, `idade_hoje`, `conducao`, `categoria`, `situacao`, `ima`, `mdc_ha`, `densidade_madeira`, `densidade_carvao`, `mad_ton_ha`, `carv_ton_ha`, `id_operario`, `data_estoque`, `vol_mad_estimado`, `vol_mad_transp`, `vol_mad_balanco`, `mdc_estimado`, `mdc_transp`, `mdc_balanco`, `mad_ton_estimado`, `mad_ton_transp`, `mad_ton_balanco`, `carv_ton_estimado`, `carv_ton_transp`, `carv_ton_balanco`, `madeira_praca`, `madeira_forno`, `rend_grav_estimado`, `rend_grav_real`, `fator_empilalhemto`) VALUES
(1, 'MG', 'Norte', 'Viçosa', 'UFV', '49', '8', 1, 0, '3334', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:11:01', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.32, 0),
(2, 'MG', 'Norte', 'Viçosa', 'UFV', '49', '8', 2, 0, '3487', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Colheita', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:15:51', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.31, 0),
(3, 'MG', 'Norte', 'Viçosa', 'UFV', '49', '9', 3, 0, '3486', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:20:13', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.33, 0),
(4, 'MG', 'Sul', 'Coimbra', 'Grama', '10', '8', 1, 0, '3676', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:57:00', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.34, 0),
(5, 'MG', 'Sul', 'Coimbra', 'Grama', '10', '8', 2, 0, '3676', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Colheita', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:57:06', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.35, 0),
(6, 'MG', 'Sul', 'Coimbra', 'UFV', '10', '8', 3, 0, '3016', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:57:10', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.35, 0),
(7, 'MG', 'Sul', 'Coimbra', 'Grama', '10', '8', 4, 0, '3486', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 08:57:13', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.33, 0),
(8, 'MG', 'Sul', 'Coimbra', 'Grama', '8', '9', 1, 0, '3486', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 09:02:41', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.33, 0),
(9, 'MG', 'Sul', 'Coimbra', 'Grama', '8', '9', 2, 0, '3336', 0, 0, 0, '00-00-0000', '00-00-0000', '', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 09:02:46', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.32, 0),
(10, 'MG', 'Sul', 'Coimbra', 'Grama', '10', '9', 5, 0, '3335', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0, 0, 0, 0, 'op_s.1', '04-04-2016 09:05:40', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.34, 0),
(11, 'MG', 'Sul', 'Coimbra', 'Grama', '7', '18', 1, 35.9, '3016', 183, 0, 2000, '20-03-2001', '22-01-2005', '12-06-2009', '02-04-2015', 3.854, 4.389, 5.81, 15.095, '-', 'Silvicultura', 'Inventariado', 0, 135.556, 0.452, 0.231, 82.716, 42.273, 'op_s.1', '04-04-2016 09:13:45', 6569.7, 349.184, -6220.52, 4866.44, 102, -4764.44, 2969.5, 157.831, -2811.67, 1517.6, 23.562, -1494.04, 219.184, 0, 1.35, 0, 1.4),
(12, 'MG', 'Sul', 'Coimbra', 'Grama', '5', '3', 1, 0, '3334', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.504, 0.24, 0, 0, 'op_s.1', '04-04-2016 12:37:09', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.31, 0),
(13, 'MG', 'Oeste', 'São Geraldo', 'Serra', '2', '2', 1, 44.6, '3335', 158.9, 0, 2002, '15-03-2005', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 11.065, '', 'Silvicultura', 'Inventariado', 0, 105.933, 0.477, 0.232, 75.7953, 36.8648, 'op_s.1', '04-04-2016 12:49:39', 7086.94, 206.625, -6880.31, 4724.63, 0, 0, 3380.47, 98.5601, -3281.91, 1644.17, 0, 0, 206.625, 0, 1.5, 1.36, 1.4),
(14, 'MG', 'Oeste', 'São Geraldo', 'Serra', '2', '2', 2, 0, '3336', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.459, 0.224, 0, 0, 'op_s.1', '04-04-2016 12:49:51', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.33, 0),
(15, 'MG', 'Oeste', 'São Geraldo', 'UFV', '2', '9', 3, 0, '3336', 0, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.459, 0.224, 0, 0, 'op_s.1', '04-04-2016 12:54:46', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.36, 0),
(16, 'MG', 'Oeste', 'São Geraldo', 'Serra', '2', '8', 4, 44.1, '3335', 250, 0, 2010, '16-09-2011', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 4.564, '', 'Silvicultura', 'Inventariado', 0, 187.97, 0.477, 0.232, 119.25, 58, 'op_s.1', '04-04-2016 12:55:38', 11025, 0, 0, 8289.47, 0, 0, 5258.92, 0, 0, 2557.8, 0, 0, 0, 0, 1.33, 1.37, 1.4),
(17, 'MG', 'Leste', 'São Geraldo', 'Grama', '1', 'UTM', 0, 0, '3676', 0, 0, 0, '', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'UTM', 0, 0, 0, 0, 0, 0, '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.31, 0),
(18, 'MG', 'Leste', 'São Geraldo', 'Grama', 'null', '1', 1, 31.85, '3487', 200, 0, 0, '00-00-0000', '00-00-0000', '00-00-0000', '00-00-0000', 0, 0, 0, 0, '', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.476, 0.237, 0, 0, 'null', '15-04-2016 16:12:11', 6370, 0, 0, 4550, 0, -4550, 3031.7, 0, -3031.7, 1078.3, 0, -1078.3, 0, 0, 0, 1.35, 0),
(19, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', '1', '8', 1, 31.85, '3487', 200, 1, 2015, '16/03/16', '10/10/12', '30/12/99', '30/12/99', 0, 0, 0, 3.5, '', 'Colheita', 'Exaurida', 0, 142.86, 0.476, 0.237, 95.2, 33.9, 'null', '15-04-2016 16:20:57', 6370, 6370, 0, 4550, 0, -4550, 3031.7, 0, -3031.7, 1078.3, 0, -1078.3, 0, 0, 0, 1.38, 0),
(20, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', '1', '8', 1, 31.85, '3487', 200, 1, 2015, '16/03/16', '10/10/12', '30/12/99', '30/12/99', 0, 0, 0, 3.5, '', 'Colheita', 'Exaurida', 0, 142.86, 0.476, 0.237, 95.2, 33.9, 'null', '15-04-2016 16:25:35', 6370, 6370, 0, 4550, 0, -4550, 3031.7, 0, -3031.7, 1078.3, 0, -1078.3, 0, 0, 0, 1.34, 0),
(21, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', '1', '8', 1, 31.85, '3487', 200, 1, 2015, '16/03/16', '10/10/12', '30/12/99', '30/12/99', 0, 0, 0, 3.5, '', 'Colheita', 'Exaurida', 0, 142.86, 0.476, 0.237, 95.2, 33.9, 'null', '15-04-2016 16:26:19', 6370, 6370, 0, 4550, 0, -4550, 3031.7, 0, -3031.7, 1078.3, 0, -1078.3, 0, 0, 0, 1.35, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `fazenda`
--

CREATE TABLE IF NOT EXISTS `fazenda` (
  `id_fazenda` int(11) NOT NULL AUTO_INCREMENT,
  `cod_estado` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `cod_bloco` int(11) NOT NULL,
  `bloco` varchar(20) NOT NULL,
  `municipio` varchar(20) NOT NULL,
  `fazenda` varchar(20) NOT NULL,
  `projeto` int(11) NOT NULL,
  PRIMARY KEY (`id_fazenda`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Extraindo dados da tabela `fazenda`
--

INSERT INTO `fazenda` (`id_fazenda`, `cod_estado`, `estado`, `cod_bloco`, `bloco`, `municipio`, `fazenda`, `projeto`) VALUES
(1, 4, 'AM', 1, 'Norte', 'Manaus', 'Rio', 1),
(2, 5, 'BA', 1, 'Norte', 'Salvador', 'Praia', 1),
(3, 13, 'MG', 1, 'Norte', 'Viçosa', 'UFV', 1),
(4, 12, 'MS', 1, 'Norte', 'Dourados', 'Pantanal', 1),
(5, 1, 'AC', 1, 'Norte', 'Acre', 'Soh', 1),
(6, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 1),
(7, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 2),
(9, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 3),
(10, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 4),
(11, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 5),
(12, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 6),
(13, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 7),
(14, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 8),
(15, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 9),
(16, 13, 'MG', 2, 'Sul', 'Coimbra', 'Grama', 10),
(17, 13, 'MG', 4, 'Oeste', 'São Geraldo', 'Serra', 1),
(18, 13, 'MG', 4, 'Oeste', 'São Geraldo', 'Serra', 2),
(19, 0, '', 0, '', '', '', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `login_usuario` varchar(10) NOT NULL,
  `senha_usuario` varchar(20) NOT NULL,
  `tipo_usuario` varchar(10) NOT NULL,
  `nome_usuario` varchar(50) NOT NULL,
  `upc_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `login_usuario`, `senha_usuario`, `tipo_usuario`, `nome_usuario`, `upc_usuario`) VALUES
(1, 'crgds', '123', 'op_s', 'Cristiano G. Duarte', 8),
(2, 'guilherme', '123', 'op_c', 'Guilherme L.S.', 0),
(3, 'crgdm', '123', 'op_m', 'Cristiano G.', 0),
(4, 'crgdc', '123', 'op_c', 'Cristiano D.', 0),
(5, 'csgds', '123', 'op_s', 'Cassio G. Duarte', 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
