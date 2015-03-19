<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["peopleJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	if($db->doesPeopleAlreadyExists($data[0]->username)) {
		$b["alreadyExists"] = 'yes';
		array_push($a,$b);
	} else {
		$b["alreadyExists"] = 'no';
		
		if($db->insertPeople($data[0]->username,
						$data[0]->password,
						$data[0]->name,
						$data[0]->first_name,
						$data[0]->sexe,
						$data[0]->date_of_birth,
						$data[0]->mail,
						$data[0]->city,
						$data[0]->up_date)) {
			$b["works"] = 'yes';
			array_push($a,$b);
		} else {
			$b["works"] = 'no';
			array_push($a,$b);
		}
	}
	
	echo json_encode($a);
?>