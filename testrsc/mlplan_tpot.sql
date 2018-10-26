-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 19. Apr 2018 um 12:31
-- Server-Version: 5.7.21-0ubuntu0.17.10.1-log
-- PHP-Version: 7.1.15-0ubuntu0.17.10.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `mlplan_tpot`
--

-- --------------------------------------------------------

--
-- Stellvertreter-Struktur des Views `mlj_TPOT_1h_resultSummary`
-- (Siehe unten für die tatsächliche Ansicht)
--
CREATE TABLE `mlj_TPOT_1h_resultSummary` (
`dataset` varchar(255)
,`errorRate` double(19,2)
,`standardDeviation` double(19,2)
,`n` bigint(21)
);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `result_warmstart`
--

CREATE TABLE `result_warmstart` (
  `id` int(10) UNSIGNED NOT NULL,
  `timeout` int(10) NOT NULL,
  `dataset` varchar(255) COLLATE utf8_bin NOT NULL,
  `seed` int(10) NOT NULL,
  `errorRate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `result_warmstart`
--

INSERT INTO `result_warmstart` (`id`, `timeout`, `dataset`, `seed`, `errorRate`) VALUES
(0, 3600, 'abalone', 1, 0.720130932897),
(0, 3600, 'abalone', 2, 0.751227495908),
(0, 3600, 'abalone', 3, 0.7381342062189999),
(0, 3600, 'abalone', 4, 0.737315875614),
(0, 3600, 'abalone', 5, 0.7160392798690001),
(0, 3600, 'abalone', 6, 0.7324058919800001),
(0, 3600, 'abalone', 7, 0.7225859247140001),
(0, 3600, 'abalone', 8, 0.735679214403),
(0, 3600, 'abalone', 9, 0.747135842881),
(0, 3600, 'abalone', 10, 0.7209492635020001),
(0, 3600, 'abalone', 11, 0.728314238953),
(0, 3600, 'abalone', 12, 0.73977086743),
(0, 3600, 'abalone', 13, 0.708674304419),
(0, 3600, 'abalone', 14, 0.729132569558),
(0, 3600, 'abalone', 15, 0.740589198036),
(0, 3600, 'abalone', 16, 0.723404255319),
(0, 3600, 'abalone', 17, 0.733224222586),
(0, 3600, 'abalone', 18, 0.736497545008),
(0, 3600, 'abalone', 19, 0.731587561375),
(0, 3600, 'car', 0, 0.005847953215999957),
(0, 3600, 'car', 1, 0.007797270955000046),
(0, 3600, 'car', 2, 0),
(0, 3600, 'car', 3, 0.0019493177389999783),
(0, 3600, 'car', 5, 0),
(0, 3600, 'car', 8, 0.0019493177389999783),
(0, 3600, 'car', 9, 0.0038986354779999566),
(0, 3600, 'car', 12, 0.007797270955000046),
(0, 3600, 'car', 14, 0),
(0, 3600, 'car', 15, 0),
(0, 3600, 'car', 16, 0.005847953215999957),
(0, 3600, 'car', 17, 0.0019493177389999783),
(0, 3600, 'car', 19, 0),
(0, 3600, 'credit-g', 1, 0.271812080537),
(0, 3600, 'credit-g', 3, 0.19463087248300004),
(0, 3600, 'credit-g', 9, 0.21812080536900003),
(0, 3600, 'credit-g', 16, 0.32214765100700005),
(0, 3600, 'krvskp', 2, 0.0073221757320000025),
(0, 3600, 'krvskp', 3, 0.0073221757320000025),
(0, 3600, 'krvskp', 4, 0.010460251046000013),
(0, 3600, 'krvskp', 7, 0.004184100417999992),
(0, 3600, 'krvskp', 12, 0.005230125522999951),
(0, 3600, 'krvskp', 17, 0.009414225941000054),
(0, 3600, 'madelon', 19, 1),
(0, 3600, 'secom', 3, 0.06423982869400002),
(0, 3600, 'semeion', 0, 0.06263498920099997),
(0, 3600, 'semeion', 3, 0.04967602591800002),
(0, 3600, 'semeion', 5, 0.03887688984899995),
(0, 3600, 'semeion', 6, 0.056155507559000006),
(0, 3600, 'semeion', 8, 0.04103671706300005),
(0, 3600, 'semeion', 10, 0.058315334772999994),
(0, 3600, 'semeion', 12, 0.053995680346),
(0, 3600, 'semeion', 17, 0.08207343412500001),
(0, 3600, 'semeion', 18, 0.06911447084199995),
(0, 3600, 'shuttle', 1, 0.00011500862600000161),
(0, 3600, 'shuttle', 2, 0.000057504313000000806),
(0, 3600, 'shuttle', 5, 0),
(0, 3600, 'shuttle', 6, 0.00023001725100002535),
(0, 3600, 'shuttle', 9, 0.000057504313000000806),
(0, 3600, 'shuttle', 10, 0.000057504313000000806),
(0, 3600, 'shuttle', 11, 0.00040253019000002777),
(0, 3600, 'shuttle', 13, 0.00034502587700002696),
(0, 3600, 'shuttle', 14, 0.000057504313000000806),
(0, 3600, 'shuttle', 15, 0.00011500862600000161),
(0, 3600, 'shuttle', 18, 0.00028752156400002615),
(0, 3600, 'shuttle', 19, 0.000057504313000000806),
(0, 3600, 'waveform', 0, 0.12307692307700002),
(0, 3600, 'waveform', 2, 0.14381270902999999),
(0, 3600, 'waveform', 4, 0.12441471571899998),
(0, 3600, 'waveform', 6, 0.12909698996700003),
(0, 3600, 'waveform', 7, 0.13244147157200004),
(0, 3600, 'waveform', 10, 0.13110367892999997),
(0, 3600, 'waveform', 11, 0.12775919732399998),
(0, 3600, 'waveform', 14, 0.130434782609),
(0, 3600, 'waveform', 15, 0.13511705685599995),
(0, 3600, 'waveform', 16, 0.130434782609),
(0, 3600, 'waveform', 17, 0.130434782609),
(0, 3600, 'waveform', 19, 0.13177257525099995),
(0, 3600, 'winequality', 1, 0.323066392882),
(0, 3600, 'winequality', 2, 0.317590691307),
(0, 3600, 'winequality', 3, 0.33333333333299997),
(0, 3600, 'winequality', 4, 0.315537303217),
(0, 3600, 'winequality', 5, 0.33196440794000004),
(0, 3600, 'winequality', 6, 0.341546885695),
(0, 3600, 'winequality', 8, 0.30390143737200004),
(0, 3600, 'winequality', 9, 0.30732375085600006),
(0, 3600, 'winequality', 11, 0.32991101984899995),
(0, 3600, 'winequality', 12, 0.32032854209399997),
(0, 3600, 'winequality', 14, 0.32785763175899996),
(0, 3600, 'winequality', 16, 0.33880903490799996),
(0, 3600, 'winequality', 17, 0.32032854209399997),
(0, 3600, 'winequality', 18, 0.319644079398),
(0, 3600, 'yeast', 1, 0.45370370370400004),
(0, 3600, 'yeast', 2, 0.37268518518500005),
(0, 3600, 'yeast', 4, 0.37037037037),
(0, 3600, 'yeast', 5, 0.363425925926),
(0, 3600, 'yeast', 8, 0.400462962963),
(0, 3600, 'yeast', 10, 0.386574074074),
(0, 3600, 'yeast', 15, 0.37268518518500005),
(0, 3600, 'yeast', 17, 0.37731481481499995),
(0, 3600, 'yeast', 18, 0.388888888889),
(0, 3600, 'vowel', 0, 0.013986013985999968),
(0, 3600, 'vowel', 1, 0.02097902097899995),
(0, 3600, 'vowel', 4, 0.013986013985999968),
(0, 3600, 'vowel', 8, 0.013986013985999968),
(0, 3600, 'vowel', 9, 0.017482517483000004),
(0, 3600, 'vowel', 10, 0.024475524475999988),
(0, 3600, 'vowel', 13, 0.03146853146899997),
(0, 3600, 'vowel', 15, 0.024475524475999988),
(0, 3600, 'page-blocks', 0, 0.02815177478599995),
(0, 3600, 'page-blocks', 2, 0.020807833536999998),
(0, 3600, 'page-blocks', 3, 0.025091799266000026),
(0, 3600, 'page-blocks', 6, 0.028763769889999957),
(0, 3600, 'page-blocks', 8, 0.02264381884900002),
(0, 3600, 'page-blocks', 9, 0.020807833536999998),
(0, 3600, 'page-blocks', 13, 0.02447980416200002),
(0, 3600, 'page-blocks', 14, 0.02815177478599995),
(0, 3600, 'page-blocks', 18, 0.02019583843299999),
(0, 3600, 'page-blocks', 19, 0.02998776009799997),
(0, 3600, 'glass', 1, 0.29090909090899997),
(0, 3600, 'glass', 2, 0.309090909091),
(0, 3600, 'glass', 5, 0.32727272727300005),
(0, 3600, 'glass', 6, 0.18181818181800002),
(0, 3600, 'glass', 7, 0.16363636363599998),
(0, 3600, 'glass', 8, 0.18181818181800002),
(0, 3600, 'glass', 9, 0.29090909090899997),
(0, 3600, 'glass', 10, 0.218181818182),
(0, 3600, 'glass', 14, 0.254545454545),
(0, 3600, 'glass', 17, 0.19999999999999996),
(0, 3600, 'glass', 19, 0.23636363636400004),
(0, 3600, 'iris', 0, 0.02380952381000001),
(0, 3600, 'iris', 2, 0),
(0, 3600, 'ionosphere', 7, 0.04901960784299997),
(0, 3600, 'ionosphere', 19, 0.07843137254900001),
(0, 3600, 'segment', 1, 0.011661807580000016),
(0, 3600, 'segment', 4, 0.02478134110800001),
(0, 3600, 'segment', 5, 0.020408163264999946),
(0, 3600, 'segment', 7, 0.013119533527999994),
(0, 3600, 'segment', 8, 0.02623906705500001),
(0, 3600, 'segment', 9, 0.014577259474999993),
(0, 3600, 'segment', 10, 0.030612244897999963),
(0, 3600, 'segment', 16, 0.02623906705500001),
(0, 3600, 'segment', 19, 0.011661807580000016);

-- --------------------------------------------------------

--
-- Struktur des Views `mlj_TPOT_1h_resultSummary`
--
DROP TABLE IF EXISTS `mlj_TPOT_1h_resultSummary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`wever`@`%` SQL SECURITY DEFINER VIEW `mlj_TPOT_1h_resultSummary`  AS  select `result_warmstart`.`dataset` AS `dataset`,round((avg(`result_warmstart`.`errorRate`) * 100),2) AS `errorRate`,round((std(`result_warmstart`.`errorRate`) * 100),2) AS `standardDeviation`,count(0) AS `n` from `result_warmstart` where ((`result_warmstart`.`timeout` = 3600) and (`result_warmstart`.`dataset` in ('abalone','amazon','car','cifar10','cifar10small','convex','credit-g','dexter','dorothea','gisette','krvskp','madelon','mnist','mnistrotationbackimagenew','secom','semeion','shuttle','waveform','winequality','yeast'))) group by `result_warmstart`.`dataset` order by `result_warmstart`.`dataset` ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
