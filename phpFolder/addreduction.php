<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["addReductionJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	if($db->doesReductionAlreadyExists($data[0]->name)) {
		$b["alreadyExists"] = 'yes';
	} else {
		$b["alreadyExists"] = 'no';
		
		if($db->insertReduction($data[0]->name, $data[0]->description, $data[0]->sexe, $data[0]->age_relation, $data[0]->age_value, $data[0]->nb_points_relation, $data[0]->nb_points_value, $data[0]->city, $data[0]->up_date)) {
			$b["works"] = 'yes';
		} else {
			$b["works"] = 'no';
		}
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>