-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 06-Maio-2016 às 19:38
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
  `id_operario` varchar(20) NOT NULL,
  `upc_c` int(11) NOT NULL,
  `talhao` int(11) NOT NULL,
  `forno` varchar(10) NOT NULL,
  `volume_madeira` float NOT NULL,
  `data_entrada_madeira_forno` varchar(19) NOT NULL,
  `volume_carvao` float DEFAULT NULL,
  `data_saida_carvao_forno` varchar(19) DEFAULT NULL,
  `rend_grav_forno` float DEFAULT NULL,
  PRIMARY KEY (`id_controle_carvao`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Extraindo dados da tabela `controle_carvao`
--

INSERT INTO `controle_carvao` (`id_controle_carvao`, `id_estoque_p`, `id_operario`, `upc_c`, `talhao`, `forno`, `volume_madeira`, `data_entrada_madeira_forno`, `volume_carvao`, `data_saida_carvao_forno`, `rend_grav_forno`) VALUES
(2, 106, 'op_c.4.upc-8', 8, 1, 'f100', 100, '28/04/2016', 88, '05/05/2016', 0.88),
(3, 106, 'op_c.3.upc-8', 9, 2, 'f500', 500, '05/05/2016', 0, '00/00/0000 00:00:00', 0),
(4, 106, 'op_scv.4.upc-8', 8, 1, 'f300', 300, '06/05/2016', 272, '06/05/2016', 0.906667),
(5, 108, 'op_scv.4.upc-8', 8, 1, 'f150', 150, '06/05/2016', 0, '00/00/0000 00:00:00', 0),
(6, 108, 'op_scv.4.upc-8', 8, 1, 'f250', 250, '06/05/2016', 233, '06/05/2016', 0.932);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_madeira`
--

CREATE TABLE IF NOT EXISTS `controle_madeira` (
  `id_controle_madeira` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque_p` int(11) NOT NULL,
  `id_operario` varchar(20) NOT NULL,
  `upc_m` int(11) NOT NULL,
  `talhao` int(11) NOT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Extraindo dados da tabela `controle_madeira`
--

INSERT INTO `controle_madeira` (`id_controle_madeira`, `id_estoque_p`, `id_operario`, `upc_m`, `talhao`, `data_entrega`, `mad_volume_m_stereo`, `mad_volume_m3`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `altura1_bt`, `altura2_bt`, `altura3_bt`, `comprimento_bt`, `largura_bt`, `peso_bt`) VALUES
(9, 105, 'op_m.3.upc-8', 0, 1, '03/05/2016 13:40:43', 1474.2, 1053, 3.9, 3.5, 3.6, 20, 4.5, 600, 0, 0, 0, 0, 0, 0),
(10, 105, 'op_m.3.upc-8', 0, 1, '03/05/2016 13:43:07', 1316.09, 940.064, 3.1, 3.2, 3.4, 14, 3.9, 500, 3.5, 3.2, 3.3, 15, 3.8, 500),
(11, 105, 'op_m.2.upc-8', 0, 1, '03/05/2016 13:45:36', 160, 114.286, 2, 2, 2, 20, 3, 300, 0, 0, 0, 0, 0, 0),
(12, 106, 'op_m.3.upc-8', 0, 1, '05/05/2016 11:53:34', 1128.96, 806.4, 5.6, 3.2, 3.5, 15, 3.6, 200.2, 0, 0, 0, 0, 0, 0),
(13, 106, 'op_m.3.upc-8', 0, 1, '05/05/2016 11:54:41', 843.033, 602.167, 3.5, 2, 2.3, 10, 3.5, 600, 3, 3.5, 2.6, 20, 3.6, 500),
(14, 106, 'op_m.3.upc-8', 0, 1, '05/05/2016 12:26:33', 737.8, 527, 3.5, 3.4, 3.1, 30, 2, 600, 0, 0, 0, 0, 0, 0),
(15, 106, 'op_smd.3.upc-8', 0, 1, '06/05/2016 14:05:24', 540, 385.714, 3, 3, 3, 20, 3, 300, 0, 0, 0, 0, 0, 0),
(16, 106, 'op_smd.3.upc-8', 0, 1, '06/05/2016 14:09:50', 2753.41, 1966.72, 4.5, 3.6, 4.1, 18, 3.1, 600, 5, 4.6, 4.4, 15, 3, 700),
(17, 108, 'op_smd.3.upc-8', 0, 1, '06/05/2016 14:14:13', 2166.67, 1547.62, 5, 5, 5, 13, 4, 600, 0, 0, 0, 0, 0, 0);

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
  `id_operario` varchar(20) NOT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=109 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fazenda`
--

CREATE TABLE IF NOT EXISTS `fazenda` (
  `id_fazenda` int(11) NOT NULL AUTO_INCREMENT,
  `estado` varchar(50) NOT NULL,
  `bloco` varchar(20) NOT NULL,
  `municipio` varchar(20) NOT NULL,
  `fazenda` varchar(20) NOT NULL,
  `projeto` varchar(4) NOT NULL,
  PRIMARY KEY (`id_fazenda`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=131 ;

--
-- Extraindo dados da tabela `fazenda`
--

INSERT INTO `fazenda` (`id_fazenda`, `estado`, `bloco`, `municipio`, `fazenda`, `projeto`) VALUES
(129, 'AC', 'Norte', 'Rio Branco', 'Cara', 'I'),
(130, 'MG', 'Leste', 'Coimbra', 'Grama', 'I');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `login_usuario`, `senha_usuario`, `tipo_usuario`, `nome_usuario`, `upc_usuario`) VALUES
(1, 'crgds', '123', 'op_scv', 'Cristiano G. Duarte', 8),
(2, 'guilherme', '123', 'op_dir', 'Guilherme L.S.', 8),
(3, 'crgdm', '123', 'op_smd', 'Cristiano G.', 8),
(4, 'crgdc', '123', 'op_scv', 'Cristiano D.', 8),
(5, 'csgds', '123', 'op_scv', 'Cassio G. Duarte', 2),
(6, 'crgdd', '123', 'op_dir', 'Cristiano G.D.', 0),
(7, 'glsd', '123', 'op_dir', 'Guilherme L.S.', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
