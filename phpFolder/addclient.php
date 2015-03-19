<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["addClientJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	if($db->doesClientAlreadyExists($data[0]->people_id, $data[0]->company_id)) {
		$b["alreadyExists"] = 'yes';
	} else {
		$b["alreadyExists"] = 'no';
		
		if($db->insertClient($data[0]->people_id, $data[0]->company_id, (string)0, 0, 0, 0, (string)$data[0]->up_date)) {
			$b["works"] = 'yes';
			$db->updateClient2($data[0]->people_id, $data[0]->company_id, $data[0]->num_client, $data[0]->random_number);
		} else {
			$b["works"] = 'no';
		}
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>