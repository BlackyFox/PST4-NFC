<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["addOfferJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	if($db->doesOfferAlreadyExists($data[0]->company_id, $data[0]->reduction_id)) {
		$b["alreadyExists"] = 'yes';
	} else {
		$b["alreadyExists"] = 'no';
		
		if($db->insertOffer($data[0]->company_id, $data[0]->reduction_id, $data[0]->up_date)) {
			$b["works"] = 'yes';
		} else {
			$b["works"] = 'no';
		}
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>