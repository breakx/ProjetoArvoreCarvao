-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 29-Abr-2016 às 00:40
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
  `id_operario_mad` varchar(20) NOT NULL,
  `volume_madeira` float NOT NULL,
  `data_entrada_madeira_forno` varchar(19) NOT NULL,
  `id_operario_carv` varchar(20) NOT NULL,
  `volume_carvao` float DEFAULT NULL,
  `data_saida_carvao_forno` varchar(19) DEFAULT NULL,
  `rend_grav_forno` float DEFAULT NULL,
  PRIMARY KEY (`id_controle_carvao`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `controle_carvao`
--

INSERT INTO `controle_carvao` (`id_controle_carvao`, `id_estoque_p`, `talhao`, `forno`, `id_operario_mad`, `volume_madeira`, `data_entrada_madeira_forno`, `id_operario_carv`, `volume_carvao`, `data_saida_carvao_forno`, `rend_grav_forno`) VALUES
(1, 1670, '1', 'f50', 'op_c.4.upc-8', 50, '27/04/2016', '---', 0, '00-00-0000 00:00:00', 0),
(2, 2960, '1', 'f200', 'op_c.4.upc-8', 200, '28/04/2016', '---', 0, '00/00/0000 00:00:00', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controle_madeira`
--

CREATE TABLE IF NOT EXISTS `controle_madeira` (
  `id_controle_madeira` int(11) NOT NULL AUTO_INCREMENT,
  `id_estoque_p` int(11) NOT NULL,
  `id_operario` varchar(20) NOT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `controle_madeira`
--

INSERT INTO `controle_madeira` (`id_controle_madeira`, `id_estoque_p`, `id_operario`, `talhao`, `data_entrega`, `mad_volume_m_stereo`, `mad_volume_m3`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `altura1_bt`, `altura2_bt`, `altura3_bt`, `comprimento_bt`, `largura_bt`, `peso_bt`) VALUES
(1, 1670, 'op_m.3.upc-8', '1', '27/04/2016 12:59:31', 79.56, 56.8286, 1.8, 1.3, 1.7, 20, 3, 500, 0, 0, 0, 0, 0, 0),
(2, 2960, 'op_m.3.upc-8', '1', '28/04/2016 14:20:31', 865.83, 618.45, 3.5, 2.8, 3.1, 19, 4.5, 600, 0, 0, 0, 0, 0, 0),
(3, 2967, 'op_m.3.upc-8', '2', '28/04/2016 17:06:36', 1247.4, 891, 3.6, 3.5, 3.3, 20, 4.5, 600, 0, 0, 0, 0, 0, 0),
(4, 2960, 'op_m.3.upc-8', '1', '28/04/2016 19:18:16', 521.466, 372.476, 2.6, 2.9, 2.8, 19, 3.9, 600, 0, 0, 0, 0, 0, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2968 ;

--
-- Extraindo dados da tabela `estoque_principal`
--

INSERT INTO `estoque_principal` (`id_estoque_p`, `estado`, `bloco`, `municipio`, `fazenda`, `projeto`, `upc`, `talhao`, `area`, `material_genetico`, `m3_ha`, `talhadia`, `ano_rotacao`, `data_plantio`, `data_rotacao_1`, `data_rotacao_2`, `data_rotacao_3`, `idade_corte1`, `idade_corte2`, `idade_corte3`, `idade_hoje`, `conducao`, `categoria`, `situacao`, `ima`, `mdc_ha`, `densidade_madeira`, `densidade_carvao`, `mad_ton_ha`, `carv_ton_ha`, `id_operario`, `data_estoque`, `vol_mad_estimado`, `vol_mad_transp`, `vol_mad_balanco`, `mdc_estimado`, `mdc_transp`, `mdc_balanco`, `mad_ton_estimado`, `mad_ton_transp`, `mad_ton_balanco`, `carv_ton_estimado`, `carv_ton_transp`, `carv_ton_balanco`, `madeira_praca`, `madeira_forno`, `rend_grav_estimado`, `rend_grav_real`, `fator_empilalhemto`) VALUES
(2960, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', 'I', '8', 1, 31.85, '3487', 300, 1, 2013, '27/03/05', '04/04/14', '00/00/0000', '00/00/0000', 9.043, 0, 0, 2012.43, '-', 'Silvicultura', 'Inventariado', 0, 220.588, 0.476, 0.237, 142.8, 71.1, 'null', '28/04/2016', 9555, 990.926, -8564.07, 7025.73, 0, 0, 4548.18, 471.681, -4076.5, 2264.53, 0, 0, 790.926, 200, 1.36, 0, 1.4),
(2961, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', 'I', '8', 2, 14.02, '3334', 0, 1, 2012, '19/02/05', '28/02/13', '00/00/0000', '00/00/0000', 8, 0, 0, 3.2, 'ok', 'Silvicultura', 'Inventariado', 0, 0, 0.504, 0.24, 0, 0, 'null', '28/04/2016', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2962, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', 'I', '8', 3, 15.1, '3334', 0, 1, 2012, '19/02/05', '22/02/13', '00/00/0000', '00/00/0000', 8, 0, 0, 3.2, 'ok', 'Silvicultura', 'Inventariado', 0, 0, 0.504, 0.24, 0, 0, 'null', '28/04/2016', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2963, 'MG', 'Norte', 'Curvelo', 'Almas e Pratas', 'I', '8', 4, 25.14, '3334', 0, 1, 2013, '27/03/05', '07/06/14', '00/00/0000', '00/00/0000', 9.2, 0, 0, 1.9, 'ok', 'Silvicultura', 'N. Inventariado', 0, 0, 0.504, 0.24, 0, 0, 'null', '28/04/2016', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2964, 'AC', 'Norte', 'Rio Branco', 'Brauna', '2', '8', 1, 5069, '3487', 0, 0, 0, '00/00/0000', '00/00/0000', '00/00/0000', '00/00/0000', 0, 0, 0, 0, '-', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.476, 0.237, 0, 0, 'op_s.1.upc-8', '28/04/2016 15:31:19', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2965, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'I', '1', 1, 500, '33333', 0, 0, 0, '00/00/0000', '00/00/0000', '00/00/0000', '00/00/0000', 0, 0, 0, 0, '-', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.476, 0.233, 0, 0, 'op_s.1.upc-8', '28/04/2016 16:33:51', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2966, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'X', '8', 1, 250, '3333', 0, 0, 0, '00/00/0000', '00/00/0000', '00/00/0000', '00/00/0000', 0, 0, 0, 0, '-', 'Silvicultura', 'Plantio Futuro', 0, 0, 0.476, 0.233, 0, 0, 'op_s.1.upc-8', '28/04/2016 17:03:59', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2967, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'X', '8', 2, 650, '3333', 123, 0, 0, '20/05/2010', '00/00/0000', '00/00/0000', '00/00/0000', 0, 0, 0, 5.942, '-', 'Silvicultura', 'Inventariado', 0, 93.1818, 0.476, 0.233, 58.548, 28.659, 'op_s.1.upc-8', '28/04/2016 17:04:08', 79950, 891, -79059, 60568.2, 0, 0, 38056.2, 424.116, -37632.1, 18628.3, 0, 0, 891, 0, 1.32, 0, 1.4);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Extraindo dados da tabela `fazenda`
--

INSERT INTO `fazenda` (`id_fazenda`, `estado`, `bloco`, `municipio`, `fazenda`, `projeto`) VALUES
(1, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'I'),
(2, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'II'),
(3, 'AL', 'Leste', 'Sergipe', 'Siri', 'I'),
(4, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'III'),
(5, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'IV'),
(6, 'AL', 'Leste', 'Sergipe', 'Siri', 'II'),
(7, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'V'),
(8, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'VI'),
(9, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'VII'),
(10, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'VIII'),
(11, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'IX'),
(12, 'AC', 'Norte', 'Rio Branco', 'Brauna', 'X');

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
(3, 'crgdm', '123', 'op_m', 'Cristiano G.', 8),
(4, 'crgdc', '123', 'op_c', 'Cristiano D.', 8),
(5, 'csgds', '123', 'op_s', 'Cassio G. Duarte', 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
