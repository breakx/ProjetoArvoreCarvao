-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 15-Mar-2016 às 21:07
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
  `talhao` varchar(10) NOT NULL,
  `forno` varchar(10) NOT NULL,
  `id_operario_mad` varchar(10) NOT NULL,
  `volume_madeira` float NOT NULL,
  `data_entrada_madeira_forno` varchar(19) NOT NULL,
  `id_operario_carv` varchar(20) NOT NULL,
  `volume_carvao` float DEFAULT NULL,
  `data_saida_carvao_forno` varchar(19) DEFAULT NULL,
  `fator` float DEFAULT NULL,
  PRIMARY KEY (`id_controle_carvao`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Extraindo dados da tabela `controle_carvao`
--

INSERT INTO `controle_carvao` (`id_controle_carvao`, `talhao`, `forno`, `id_operario_mad`, `volume_madeira`, `data_entrada_madeira_forno`, `id_operario_carv`, `volume_carvao`, `data_saida_carvao_forno`, `fator`) VALUES
(4, 't002', 'g100', '12', 152, '04-03-2016 14:53:38', '15', 112, '04-03-2016 15:06:17', NULL),
(5, 't001', 'f100', '12', 500, '05-03-2016 16:24:15', '0', 0, '00-00-0000 00:00:00', NULL),
(6, '3', 'f120', 'op_c.4', 200, '15-03-2016 16:02:19', 'op_c.4', 180, '15-03-2016 16:24:03', 0.9);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_estoque`
--

CREATE TABLE IF NOT EXISTS `controle_estoque` (
  `id_estoque` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque_p` int(11) DEFAULT NULL,
  `talhao` varchar(20) NOT NULL,
  `volume_madeira_max` float NOT NULL,
  `volume_madeira_restante` float NOT NULL,
  `volume_madeira_talhao` float NOT NULL,
  `volume_madeira_praca` float NOT NULL,
  `volume_carvao` float NOT NULL,
  `volume_carvao_forno` float NOT NULL,
  `data_estoque` varchar(19) NOT NULL,
  `id_operario` varchar(20) NOT NULL,
  PRIMARY KEY (`id_estoque`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `controle_estoque`
--

INSERT INTO `controle_estoque` (`id_estoque`, `id_estoque_p`, `talhao`, `volume_madeira_max`, `volume_madeira_restante`, `volume_madeira_talhao`, `volume_madeira_praca`, `volume_carvao`, `volume_carvao_forno`, `data_estoque`, `id_operario`) VALUES
(1, 2, '', 32, 423, 54, 64, 75, 856, '10-03-2016 14:33:36', '155555'),
(2, 1, '', 111, 1222, 1212, 1212, 120, 21121, '11-03-2016 15:43:24', '13'),
(3, 0, '', 1111, 12223, 333, 4, 4444220, 123, '15-03-2016 14:11:54', 'null');

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_madeira`
--

CREATE TABLE IF NOT EXISTS `controle_madeira` (
  `id_controle_madeira` int(11) NOT NULL AUTO_INCREMENT,
  `id_operario` varchar(10) NOT NULL,
  `talhao` varchar(10) NOT NULL,
  `saida_volume_talhao` float NOT NULL,
  `data_talhao` varchar(19) NOT NULL,
  `altura1_t` float NOT NULL,
  `altura2_t` float NOT NULL,
  `altura3_t` float NOT NULL,
  `comprimento_t` float NOT NULL,
  `largura_t` float NOT NULL,
  `peso_t` float NOT NULL,
  `entrada_volume_praca` float DEFAULT '0',
  `data_praca` varchar(19) DEFAULT '00-00-000 00:00:00',
  `altura1_p` float DEFAULT '0',
  `altura2_p` float DEFAULT '0',
  `altura3_p` float DEFAULT '0',
  `comprimento_p` float DEFAULT '0',
  `largura_p` float DEFAULT '0',
  `peso_p` float DEFAULT NULL,
  `fator` float DEFAULT '0',
  PRIMARY KEY (`id_controle_madeira`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Extraindo dados da tabela `controle_madeira`
--

INSERT INTO `controle_madeira` (`id_controle_madeira`, `id_operario`, `talhao`, `saida_volume_talhao`, `data_talhao`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `entrada_volume_praca`, `data_praca`, `altura1_p`, `altura2_p`, `altura3_p`, `comprimento_p`, `largura_p`, `peso_p`, `fator`) VALUES
(12, '12', 't001', 60, '04-03-2016 12:11:33', 1, 2, 3, 1, 2, 0, 1500, '04-03-2016 12:34:23', 4, 5, 6, 3, 1, 0, 0),
(13, 'op_m.3', 't001', 8, '15-03-2016 08:13:52', 1, 2, 3, 4, 1, 0, 16, '15-03-2016 09:18:35', 1, 2, 3, 4, 2, 2223, 0.5),
(14, '12', 't001', 102.6, '2016-03-02 00:00:00', 1.6, 1.8, 1.7, 15.6, 14.6, 0, 0, '00-00-000 00:00:00', 0, 0, 0, 0, 0, 2000, 0),
(15, '12', 't001', 102.6, '2016-03-02 00:00:00', 1.6, 1.8, 1.7, 15.6, 14.6, 2000, 0, '00-00-000 00:00:00', 0, 0, 0, 0, 0, 0, 0),
(16, 'op_m.3', 't001', 9.66, '15-03-2016 09:19:16', 2, 3, 1, 2.3, 2.1, 0, 20.286, '15-03-2016 09:20:00', 2, 3, 2.1, 2.1, 2.3, 1333, 0.476191),
(17, 'op_m.3', 't001', 129.067, '15-03-2016 09:22:55', 2, 2, 2, 22, 2.2, 2222, 193.6, '15-03-2016 09:23:31', 2, 3, 2, 22, 2.2, 2223, 0.666668),
(18, 'op_m.3', 't001', 62.4, '15-03-2016 14:28:32', 1, 2, 3, 12, 2.6, 20033, 0, '00-00-0000 00:00:00', 0, 0, 0, 0, 0, 0, 0),
(19, 'op_m.3', '3', 52, '15-03-2016 14:35:42', 2, 3, 2, 5, 2.6, 23051, 0, '00-00-0000 00:00:00', 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `estoque_principal`
--

CREATE TABLE IF NOT EXISTS `estoque_principal` (
  `id_estoque_p` int(11) NOT NULL AUTO_INCREMENT,
  `estado` int(11) NOT NULL,
  `upc` varchar(50) NOT NULL,
  `bloco` int(11) NOT NULL,
  `municipio` varchar(20) NOT NULL,
  `fazenda` varchar(20) NOT NULL,
  `projeto` varchar(20) NOT NULL,
  `ano_rotacao` int(11) NOT NULL,
  `talhao` int(11) NOT NULL,
  `area` float NOT NULL,
  `m3_ha` float NOT NULL,
  `data_plantio` varchar(19) NOT NULL,
  `material_genetico` int(11) NOT NULL,
  `talhadia` int(11) DEFAULT '0',
  `data_rotacao_1` varchar(19) DEFAULT '00-00-0000 00:00:00',
  `data_rotacao_2` varchar(19) DEFAULT '00-00-0000 00:00:00',
  `idade` float DEFAULT '0',
  `categoria` varchar(20) DEFAULT '---',
  `situacao` varchar(20) DEFAULT '---',
  `ima` float DEFAULT '0',
  `mdc_ha` float DEFAULT '0',
  `mdc` float DEFAULT '0',
  `densidade_carvao` float DEFAULT '0',
  `densidade_madeira` float DEFAULT '0',
  `id_operario` varchar(10) NOT NULL DEFAULT '0.0',
  `data_estoque` varchar(19) NOT NULL DEFAULT '00-00-0000 00:00:00',
  `volume_estimado` float NOT NULL DEFAULT '0',
  `madeira_talhao` float NOT NULL DEFAULT '0',
  `madeira_praca` float NOT NULL DEFAULT '0',
  `madeira_forno` float NOT NULL DEFAULT '0',
  `mad_ton_tot` float NOT NULL DEFAULT '0',
  `carv_ton_tot` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_estoque_p`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Extraindo dados da tabela `estoque_principal`
--

INSERT INTO `estoque_principal` (`id_estoque_p`, `estado`, `upc`, `bloco`, `municipio`, `fazenda`, `projeto`, `ano_rotacao`, `talhao`, `area`, `m3_ha`, `data_plantio`, `material_genetico`, `talhadia`, `data_rotacao_1`, `data_rotacao_2`, `idade`, `categoria`, `situacao`, `ima`, `mdc_ha`, `mdc`, `densidade_carvao`, `densidade_madeira`, `id_operario`, `data_estoque`, `volume_estimado`, `madeira_talhao`, `madeira_praca`, `madeira_forno`, `mad_ton_tot`, `carv_ton_tot`) VALUES
(11, 13, 'ds123', 2, 'coimbra', 'grama', 'pj1', 2016, 3, 123.6, 8.6, '20-02-2016', 1234, 0, '00-00-0000 00:00:00', '00-00-0000 00:00:00', 0, '---', '---', 0, 0, 0, 0, 0, 'null', '15-03-2016 14:04:23', 1062.96, 0, 500.56, 0, 0, 0),
(12, 4, '23', 1, 'viçosa', 'faz1', 'proij2', 2016, 2, 69.6, 3.9, '20-02-2016', 1231, 0, '00-00-0000 00:00:00', '00-00-0000 00:00:00', 0, '---', '---', 0, 0, 0, 0, 0, 'op_s.1', '15-03-2016 14:23:28', 271.44, 0, 0, 0, 0, 0);

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
