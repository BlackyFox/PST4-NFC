<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["getReductionsJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	$res = $db->getAllReductions();
	if ($res != false) {
		$no_of_people = mysql_num_rows($res);
		$b["has_reductions"] = 'yes';
		$b["nb_of_reductions"] = (string)$no_of_people;
	} else {
		$no_of_people = 0;
		$b["has_reductions"] = 'no';
	}
	
	for ($i = 0 ; $i < $no_of_people ; $i++) {
		$row = mysql_fetch_array($res);
		$b["reduction{$i}_id"] =  $row["id"];
		$b["reduction{$i}_name"] =  $row["name"];
		$b["reduction{$i}_description"] =  $row["description"];
		$b["reduction{$i}_sexe"] =  $row["sexe"];
		$b["reduction{$i}_age_description"] =  $row["age_relation"];
		$b["reduction{$i}_age_value"] =  $row["age_value"];
		$b["reduction{$i}_nb_points_relation"] =  $row["nb_points_relation"];
		$b["reduction{$i}_nb_points_value"] =  $row["nb_points_value"];
		$b["reduction{$i}_city"] =  $row["city"];
		$b["reduction{$i}_up_date"] =  $row["up_date"];
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>