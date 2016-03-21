-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 21 mars 2016 kl 02:00
-- Serverversion: 10.1.10-MariaDB
-- PHP-version: 7.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `music2`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `album`
--

CREATE TABLE `album` (
  `pk_album_id` int(11) NOT NULL,
  `album_name` varchar(40) DEFAULT NULL,
  `fk_artist_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `album`
--

INSERT INTO `album` (`pk_album_id`, `album_name`, `fk_artist_id`) VALUES
(1, 'The Number of the Beast', 1),
(2, 'The Final Frontier', 1),
(3, 'Thriller', 2),
(4, 'Bad', 2),
(5, 'True', 3),
(6, 'Sixology', 4),
(7, 'didriks album', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur `artist`
--

CREATE TABLE `artist` (
  `pk_artist_id` int(11) NOT NULL,
  `artist_name` varchar(40) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `artist`
--

INSERT INTO `artist` (`pk_artist_id`, `artist_name`, `country`) VALUES
(1, 'Iron Maiden', 'England'),
(2, 'Michael Jackson', 'USA'),
(3, 'Avicii', 'Sweden'),
(4, 'JJ Lin', 'China'),
(5, 'artist_name', 'country');

-- --------------------------------------------------------

--
-- Tabellstruktur `genre`
--

CREATE TABLE `genre` (
  `pk_genre_id` int(11) NOT NULL,
  `genre_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `genre`
--

INSERT INTO `genre` (`pk_genre_id`, `genre_name`) VALUES
(1, 'Heavy Metal'),
(2, 'Mandopop'),
(3, 'Electro house'),
(4, 'Pop'),
(5, 'Didriks Genre'),
(7, 'thai det fungerar! :)');

-- --------------------------------------------------------

--
-- Tabellstruktur `track`
--

CREATE TABLE `track` (
  `pk_track_id` int(11) NOT NULL,
  `track_name` varchar(40) DEFAULT NULL,
  `track_time` int(11) DEFAULT NULL,
  `fk_artist_id` int(11) DEFAULT NULL,
  `fk_album_id` int(11) DEFAULT NULL,
  `release_date` int(11) DEFAULT NULL,
  `fk_genre_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `track`
--

INSERT INTO `track` (`pk_track_id`, `track_name`, `track_time`, `fk_artist_id`, `fk_album_id`, `release_date`, `fk_genre_id`) VALUES
(1, 'Run to the Hills', 233, 1, 1, 1982, 1),
(2, 'Children of the Damned', 275, 1, 1, 1982, 1),
(3, 'When the Wild Wind Blows', 659, 1, 2, 2010, 1),
(4, 'Billie Jean', 294, 2, 3, 1982, 4),
(5, 'Bad', 247, 2, 4, 1987, 4),
(6, 'Wake Me Up', 249, 3, 5, 2013, 3),
(7, 'Lay Me Down', 300, 3, 5, 2014, 3),
(8, 'Always Online', 226, 4, 6, 2008, 2);

-- --------------------------------------------------------

--
-- Tabellstruktur `trackgenre`
--

CREATE TABLE `trackgenre` (
  `pk_track_genre_id` int(11) NOT NULL,
  `fk_track_id` int(11) DEFAULT NULL,
  `fk_genre_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `album`
--
ALTER TABLE `album`
  ADD PRIMARY KEY (`pk_album_id`);

--
-- Index för tabell `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`pk_artist_id`);

--
-- Index för tabell `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`pk_genre_id`);

--
-- Index för tabell `track`
--
ALTER TABLE `track`
  ADD PRIMARY KEY (`pk_track_id`);

--
-- Index för tabell `trackgenre`
--
ALTER TABLE `trackgenre`
  ADD PRIMARY KEY (`pk_track_genre_id`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `album`
--
ALTER TABLE `album`
  MODIFY `pk_album_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT för tabell `artist`
--
ALTER TABLE `artist`
  MODIFY `pk_artist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT för tabell `genre`
--
ALTER TABLE `genre`
  MODIFY `pk_genre_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT för tabell `track`
--
ALTER TABLE `track`
  MODIFY `pk_track_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT för tabell `trackgenre`
--
ALTER TABLE `trackgenre`
  MODIFY `pk_track_genre_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
