<?php
session_start();

if (isset($_SESSION['counter'])) {
	$_SESSION['counter']++;
} else {
	$_SESSION['counter'] = 1;
}


$page = 1;
if (isset($_GET['page']) && $_GET['page']) {
	$page = $_GET['page'];
}

$limit = 5;
if (isset($_GET['limit']) && $_GET['limit']) {
	$limit = $_GET['limit'];
}


$data = array(
	'1' => array(
		array(
			'movie_id' => 1,
			'name' => 'The Terminator',
			'year' => 1984
		),
		array(
			'movie_id' => 2,
			'name' => 'Terminator 2 : Judgment Day',
			'year' => 1991
		),
		array(
			'movie_id' => 3,
			'name' => 'Terminator 3 : Rise of the Machine',
			'year' => 2003
		),
	),
	'2' => array(
		array(
			'movie_id' => 4,
			'name' => 'Terminator Salvation',
			'year' => 2009
		),
		array(
			'movie_id' => 5,
			'name' => 'Terminator Genisys',
			'year' => 2015
		),
		array(
			'movie_id' => 6,
			'name' => 'Terminator : Dark Fate',
			'year' => 2019
		),
	),
);

header('Content-Type: application/json');
echo json_encode([
	'code' => 1,
	'message' => 'data retrieve',
	'page' => $page,
	'limit' => $limit,
	'data' => $data[$page],
	'debugData' => json_encode($_COOKIE).json_encode(getallheaders()),
	'session' => 'counter : '.(isset($_SESSION['counter']) ? $_SESSION['counter'] : null),
]);

?>