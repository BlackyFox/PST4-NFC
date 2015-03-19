<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["getPeopleJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	$res = $db->getAllPeople();
	if ($res != false) {
		$no_of_people = mysql_num_rows($res);
		$b["works"] = 'yes';
		$b["nb_of_people"] = (string)$no_of_people;
	} else {
		$no_of_people = 0;
		$b["works"] = 'no';
	}
	
	for ($i = 0 ; $i < $no_of_people ; $i++) {
		$row = mysql_fetch_array($res);
		$b["people{$i}_id"] =  $row["id"];
		$b["people{$i}_username"] =  $row["username"];
		$b["people{$i}_name"] =  $row["name"];
		$b["people{$i}_first_name"] =  $row["first_name"];
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>