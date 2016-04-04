-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 01-Abr-2016 às 19:06
-- Versão do servidor: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `appmadeiracarvao`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

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
  `upc` varchar(20) NOT NULL,
  `talhao` int(11) NOT NULL,
  `area` float NOT NULL,
  `m3_ha` float NOT NULL,
  `material_genetico` int(11) NOT NULL,
  `talhadia` int(11) DEFAULT '0',
  `ano_rotacao` int(11) NOT NULL,
  `data_plantio` varchar(19) NOT NULL,
  `data_rotacao_1` varchar(19) DEFAULT '00-00-0000',
  `data_rotacao_2` varchar(19) DEFAULT '00-00-0000',
  `data_rotacao_3` varchar(19) DEFAULT '00-00-0000',
  `idade` float DEFAULT '0',
  `categoria` varchar(20) DEFAULT '---',
  `situacao` varchar(20) DEFAULT '---',
  `ima` float DEFAULT '0',
  `mdc_ha` float DEFAULT '0',
  `densidade_madeira` float DEFAULT '0',
  `densidade_carvao` float DEFAULT '0',
  `mad_ton_ha` float NOT NULL DEFAULT '0',
  `carv_ton_ha` float NOT NULL DEFAULT '0',
  `id_operario` varchar(10) NOT NULL,
  `data_estoque` varchar(19) NOT NULL,
  `vol_mad_estimado` float DEFAULT '0',
  `vol_mad_real` float DEFAULT '0',
  `vol_mad_balanco` float DEFAULT '0',
  `mdc_estimado` float DEFAULT '0',
  `mdc_real` float NOT NULL DEFAULT '0',
  `mdc_balanco` float NOT NULL DEFAULT '0',
  `mad_ton_estimado` float DEFAULT '0',
  `mad_ton_real` float DEFAULT '0',
  `mad_ton_balanco` float DEFAULT '0',
  `carv_ton_estimado` float DEFAULT '0',
  `carv_ton_real` float DEFAULT '0',
  `carv_ton_balanco` float DEFAULT '0',
  `madeira_praca` float DEFAULT '0',
  `madeira_forno` float DEFAULT '0',
  `mad_ton_tot` float DEFAULT '0',
  `carv_ton_tot` float DEFAULT '0',
  `rend_grav_estimado` float DEFAULT '0',
  `rend_grav_real` float DEFAULT '0',
  `fator_empilalhemto` float DEFAULT '0',
  PRIMARY KEY (`id_estoque_p`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

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
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `login_usuario`, `senha_usuario`, `tipo_usuario`, `nome_usuario`) VALUES
(1, 'crgd', '123456', 'op_s', 'Cristiano G. Duarte'),
(2, 'guilherme', '123', 'op_c', 'Guilherme L.S.'),
(3, 'crgdm', '123', 'op_m', 'Cristiano G.'),
(4, 'crgdc', '123', 'op_c', 'Cristiano D.');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
